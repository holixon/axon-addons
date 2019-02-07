import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import _buildsrc.*

plugins {
  kotlin("jvm") version Versions.kotlin
  `java-library`
}

apply {
  // repos set in /gradle
  from("../gradle/repositories.gradle.kts")
}

dependencies {
  api("com.tngtech.jgiven:jgiven-junit5:${Versions.jgiven}")

  implementation(kotlin("stdlib-jdk8"))
  implementation(axon("test"))

  testImplementation(junit5("api"))
  testRuntimeOnly(junit5("engine"))
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
}

