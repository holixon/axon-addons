import _buildsrc.axon
import _buildsrc.junit5
import _buildsrc.releaseVersion
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  //id("org.jetbrains.kotlin.plugin.allopen") version Versions.kotlin
  `java-library`

  id("org.jetbrains.dokka") version "0.9.17"

  `maven-publish`
}


releaseVersion()

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))

  implementation(axon("configuration"))

  testImplementation(junit5("api"))
  testImplementation(axon("test"))
  testRuntimeOnly(junit5("engine"))
  testImplementation("org.hamcrest:hamcrest-core:2.1")

  testImplementation("org.slf4j:slf4j-simple:1.7.25")
}



tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = "1.8"
    }
  }

  withType<Test> {
    useJUnitPlatform {}
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
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      from(components["java"])
    }
  }
}

