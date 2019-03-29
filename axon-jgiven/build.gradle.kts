import _buildsrc.axon
import _buildsrc.junit5
import _buildsrc.releaseVersion
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version Versions.kotlin
  id("org.jetbrains.kotlin.plugin.allopen") version Versions.kotlin
  `java-library`
  id("com.tngtech.jgiven.gradle-plugin") version Versions.jgiven
  id("com.jfrog.bintray") version Versions.bintray

  id("org.jetbrains.dokka") version "0.9.17"

  `maven-publish`
}

releaseVersion()

apply {
  // repos set in /gradle
  from("../gradle/repositories.gradle.kts")
}

dependencies {


  implementation(kotlin("stdlib-jdk8"))


  implementation(axon("test"))
  implementation("com.tngtech.jgiven:jgiven-junit5:${Versions.jgiven}")
  implementation("org.hamcrest:hamcrest-core:2.1")

  testImplementation("javax.inject:javax.inject:1")


  testImplementation(junit5("api"))
  testRuntimeOnly(junit5("engine"))

  testImplementation(project(":examples:giftcard-sample"))
  testImplementation("org.slf4j:slf4j-simple:1.7.25")
}

allOpen {
  annotation("io.toolisticon.addons.axon.jgiven.AxonStage")
}

val sourcesJar by tasks.registering(Jar::class) {
  classifier = "sources"
  from(sourceSets.main.get().allSource)
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
    // suppresses undocumented classes but not dokka warnings
    // https://github.com/Kotlin/dokka/issues/229 && https://github.com/Kotlin/dokka/issues/319
    reportUndocumented = false
    outputFormat = "javadoc"
    outputDirectory = "$buildDir/javadoc"
    // Java 8 is only version supported both by Oracle/OpenJDK and Dokka itself
    // https://github.com/Kotlin/dokka/issues/294
    enabled = JavaVersion.current().isJava8
  }

//  register<Jar>("sourcesJar") {
//    from(sourceSets.main.get().allJava)
//    archiveClassifier.set("sources")
//  }
//
//  register<Jar>("javadocJar") {
//    from(tasks.javadoc)
//    archiveClassifier.set("javadoc")
//  }
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      from(components["java"])
    }
  }
}

