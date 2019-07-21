import _buildsrc.axon
import _buildsrc.junit5
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
  kotlin("jvm")
  `java-library`
  id("com.tngtech.jgiven.gradle-plugin") version Versions.jgiven

  id("com.jfrog.bintray") version Versions.Plugins.bintray
  id("org.jetbrains.dokka") version Versions.Plugins.dokka

  `maven-publish`


  id("org.jetbrains.kotlin.plugin.allopen") version Versions.kotlin
}

dependencies {
  api("io.toolisticon.addons.jgiven:jgiven-kotlin:${Versions.jgivenAddons}")
  api("com.tngtech.jgiven:jgiven-junit5:${Versions.jgiven}")
  api(axon("test"))

  implementation(kotlin("stdlib-jdk8"))
  implementation("org.hamcrest:hamcrest-core:2.1")

  testImplementation("javax.inject:javax.inject:1")
  testImplementation(junit5("api"))
  testRuntimeOnly(junit5("engine"))
  testImplementation(project(":examples:giftcard-sample"))
  testImplementation("org.slf4j:slf4j-simple:1.7.25")
}

allOpen {
  annotation("io.toolisticon.addons.jgiven.JGivenKotlinStage")
}


tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = "1.8"
    }
  }

  withType<Test> {
    useJUnitPlatform()
  }

  withType<DokkaTask> {
    reportUndocumented = true
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
    enabled = JavaVersion.current().isJava8
  }
  publishToMavenLocal {
    dependsOn(build)
  }
}

  
val dokkaJar by tasks.creating(Jar::class) {
  group = JavaBasePlugin.DOCUMENTATION_GROUP
  description = "Assembles Kotlin docs with Dokka"
  archiveClassifier.set("javadoc")
  from(tasks.dokka)
  dependsOn(tasks.dokka)
}

val sourcesJar by tasks.creating(Jar::class) {
  archiveClassifier.set("sources")
  from(sourceSets.getByName("main").allSource)
}


publishing {
  publications {
    create<MavenPublication>(project.name) {
      artifactId = project.name
      from(components["java"])
      artifact(dokkaJar)
      artifact(sourcesJar)

      pom.withXml(name = "${rootProject.name}/${project.name}", description = "Addons for jgiven test tool")
    }
  }
}

bintray {
  user = properties["bintrayUser"] as String
  key = properties["bintrayKey"] as String
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
