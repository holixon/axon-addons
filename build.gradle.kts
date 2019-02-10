plugins {
  base
  idea
  `maven-publish`
}

apply {
  // repos set in /gradle
  from("gradle/repositories.gradle.kts")
}

allprojects {
  group = "io.toolisticon.addons.axon"
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
