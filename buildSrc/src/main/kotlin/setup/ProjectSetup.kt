package setup

import Github
import _buildsrc.junit5
import _buildsrc.propertySafe
import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.PublishToMavenLocal
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.*
import org.gradle.kotlin.dsl.support.delegates.SettingsDelegate
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import withXml
import java.util.*

object ProjectSetup {
  val modules = mutableMapOf<String, ProjectModule>()

  fun SettingsDelegate.includeModules(vararg modules: ProjectModule) = modules.forEach { this.includeModule(it) }

  fun SettingsDelegate.includeModule(module: ProjectModule) {
    register(module)
    this.include(module.projectPath)
  }

  fun DependencyHandler.projectModule(name: String): ProjectDependency {
    val projectPath = this@ProjectSetup.modules[name]?.projectPath ?: throw IllegalArgumentException("projectModule('$name') not found")
    return this.project(projectPath)
  }

  fun register(module: ProjectModule) = modules.put(module.name, module)


  abstract class ProjectModule(
    open val name: String,
    open val projectPath: String = ":$name",
    open val publish: Boolean
  ) {
    abstract fun configure(project: Project)
  }

  class Bom(override val name: String) : ProjectModule(name = name, projectPath = ":$name", publish = true) {
    override fun configure(project: Project) = with(project) {
      apply(plugin = "java-platform")

      if (publish) {
        apply(plugin = "maven-publish")

        configure<PublishingExtension> {
          publications {
            create<MavenPublication>(name) {
              from(components["javaPlatform"])
            }
          }
        }

        addBintray()
      }
    }
  }

  class KotlinLibrary(override val name: String, override val publish: Boolean = true, val description: String) : ProjectModule(name = name, projectPath = ":$name", publish = publish) {
    override fun configure(project: Project) = with(project) {
      kotlinBase()
      apply(plugin = "org.jetbrains.dokka")

      if (publish) {
        apply(plugin = "maven-publish")
        apply(plugin = "java-library")


        tasks {
          withType<DokkaTask> {
            outputFormat = "html"
            outputDirectory = "$buildDir/javadoc"
            enabled = JavaVersion.current().isJava8

            configuration {
              reportUndocumented = false
            }
          }

          register<Jar>("javadocJar") {
            group = JavaBasePlugin.DOCUMENTATION_GROUP
            description = "Assembles Kotlin docs with Dokka"
            archiveClassifier.set("javadoc")
            from(tasks["dokka"])
            dependsOn(tasks["dokka"])
          }

          register<Jar>("sourcesJar") {
            archiveClassifier.set("sources")
            from(this@with.the<SourceSetContainer>()["main"].allSource)
          }

          withType<PublishToMavenLocal> {
            dependsOn(tasks["build"])
          }
        }

        configure<PublishingExtension> {
          publications {
            create<MavenPublication>(project.name) {
              artifactId = project.name
              from(components["java"])
              artifact(tasks["javadocJar"])
              artifact(tasks["sourcesJar"])

              pom.withXml(
                name = "${rootProject.name}/${project.name}",
                description = this@KotlinLibrary.description
              )
            }
          }
        }
        addBintray()
      }
    }
  }

  class ExampleKotlinLibrary(override val name: String, root: String = "examples") : ProjectModule(name, ":$root:$name", false) {
    override fun configure(project: Project) = with(project) {
      kotlinBase()

    }
  }

  fun Project.kotlinBase() {
    this.apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
      "implementation"(kotlin("stdlib-jdk8"))
      "testImplementation"(junit5("api"))
      "testRuntimeOnly"(junit5("engine"))
      "testImplementation"("ch.qos.logback:logback-core:${Versions.Test.logback}")
    }

    tasks {
      withType<KotlinCompile> {
        kotlinOptions {
          jvmTarget = Versions.Build.java
        }
      }

      withType<Test> {
        useJUnitPlatform()
      }
    }
  }

  fun Project.addBintray() {
    apply(plugin = "com.jfrog.bintray")

    configure<BintrayExtension> {
      user = propertySafe("bintrayUser")
      key = propertySafe("bintrayKey")
      publish = true

      setPublications(project.name)

      pkg.apply {
        userOrg = Github.organization
        repo = "maven"
        name = rootProject.name
        setLicenses("Apache-2.0")
        setLabels("Kotlin", "jgiven", "addon")
        vcsUrl = Github.pomScmUrl
        websiteUrl = Github.pomUrl
        issueTrackerUrl = Github.pomIssueUrl
        githubRepo = Github.path
        githubReleaseNotesFile = Github.readme

        version.apply {
          name = project.version as String
          vcsTag = project.version as String
          desc = Github.pomDesc
          released = Date().toString()
        }
      }
    }
  }
}
