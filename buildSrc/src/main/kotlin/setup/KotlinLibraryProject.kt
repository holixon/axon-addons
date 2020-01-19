package setup

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.PublishToMavenLocal
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*
import org.jetbrains.dokka.gradle.DokkaTask
import setup.ProjectSetup.addBintray
import setup.ProjectSetup.kotlinBase


class KotlinLibraryProject : Plugin<Project> {
  override fun apply(project: Project) = with(project) {

    kotlinBase()
    apply(plugin = "org.jetbrains.dokka")

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

//          pom.withXml(
//            name = "${rootProject.name}/${project.name}",
//            description = this@KotlinLibrary.description
//          )
        }
      }
    }
    addBintray()
  }
}
