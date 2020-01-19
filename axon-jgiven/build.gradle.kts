import _buildsrc.axonframework
import _buildsrc.junit5
import setup.KotlinLibraryProject

plugins {
  kotlin("jvm")
  kotlin("plugin.allopen")
  //id(Plugins.jgiven)
}


allOpen {
  annotation("io.toolisticon.addons.jgiven.JGivenKotlinStage")
}

dependencies {
  api("io.toolisticon.addons.jgiven:jgiven-kotlin:${Versions.jgivenAddons}")
  api("com.tngtech.jgiven:jgiven-junit5:${Versions.Test.jgiven}")
  api(axonframework("test"))

  implementation(kotlin("stdlib-jdk8"))
  testImplementation(junit5("api"))
  testRuntimeOnly(junit5("engine"))
  testImplementation("ch.qos.logback:logback-core:${Versions.Test.logback}")

  implementation("org.hamcrest:hamcrest-core:2.1")
  testImplementation("javax.inject:javax.inject:1")
  testImplementation(project(":giftcard-sample"))
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = Versions.Build.java
    }
  }

  withType<Test> {
    useJUnitPlatform()
  }
}
