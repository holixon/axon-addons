import _buildsrc.axonframework
import _buildsrc.propertySafe
import setup.ProjectSetup.projectModule
import java.util.*

plugins {
  kotlin("plugin.allopen") version Versions.Build.kotlin
  id("com.tngtech.jgiven.gradle-plugin") version Versions.Test.jgiven
}


allOpen {
  annotation("io.toolisticon.addons.jgiven.JGivenKotlinStage")
}

dependencies {
  api("io.toolisticon.addons.jgiven:jgiven-kotlin:${Versions.jgivenAddons}")
  api("com.tngtech.jgiven:jgiven-junit5:${Versions.Test.jgiven}")
  api(axonframework("test"))

  implementation("org.hamcrest:hamcrest-core:2.1")
  testImplementation("javax.inject:javax.inject:1")
  testImplementation(projectModule("giftcard-sample"))
}

