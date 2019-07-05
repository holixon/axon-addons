plugins {
  base
  idea
  `maven-publish`

  kotlin("jvm") version Versions.kotlin apply false
}

allprojects {
  group = "io.toolisticon.addons.axon"
  version = "0.1.0-SNAPSHOT"

  apply {
    from("${rootProject.rootDir}/gradle/repositories.gradle.kts")
  }
}

//
//dependencies {
//    // Use the Kotlin JDK 8 standard library.
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//
//    // Use the Kotlin test library.
//    testImplementation("org.jetbrains.kotlin:kotlin-test")
//
//    // Use the Kotlin JUnit integration.
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
//}


dependencies {
  // Make the root project archives configuration depend on every sub-project
  subprojects.forEach {
    archives(it)
  }
}
