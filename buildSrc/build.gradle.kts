import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `java-gradle-plugin`
  `kotlin-dsl`
}

apply {
  from("../gradle/repositories.gradle.kts")
}

kotlinDslPluginOptions {
  experimentalWarning.set(false)
}

dependencies {
  fun kotlin(module:String) = "org.jetbrains.kotlin:kotlin-$module:$embeddedKotlinVersion"

  implementation(gradleApi())

  implementation(kotlin("gradle-plugin"))
  implementation(kotlin("allopen"))

  implementation("com.tngtech.jgiven:jgiven-gradle-plugin:0.18.2")

  implementation("org.jetbrains.dokka:dokka-gradle-plugin:0.10.0")
  implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
  }
}
