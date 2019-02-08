import _buildsrc.axon
import _buildsrc.junit5
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version Versions.kotlin
  //id("org.jetbrains.kotlin.plugin.allopen") version Versions.kotlin
  `java-library`
  id("com.tngtech.jgiven.gradle-plugin") version Versions.jgiven
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
  implementation("org.hamcrest:hamcrest-core:2.1")

  //testCompileOnly("junit:junit:4.12")
  //testRuntimeOnly("org.junit.vintage:junit-vintage-engine:${Versions.junit5}")

  testImplementation("org.slf4j:slf4j-simple:1.7.25")
}

//allOpen {
//  annotation("io.toolisticon.axon.addons.jgiven.AxonStage")
//}

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

