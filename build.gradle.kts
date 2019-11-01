import setup.ProjectSetup

plugins {
  base
  idea
  `build-scan`

  //id("org.jetbrains.dokka") version Versions.Gradle.dokka apply false


//  `maven-publish`
//  id("com.github.breadmoirai.github-release") version Versions.Gradle.githubRelease
}

allprojects {
  group = "io.toolisticon.addons.axon"

  apply {
    from("${rootProject.rootDir}/gradle/repositories.gradle.kts")
  }
}

subprojects {
  val projectModule = ProjectSetup.modules[this.name] ?: return@subprojects

  projectModule.configure(this) // TODO: receiver
}

dependencies {
  // Make the root project archives configuration depend on every sub-project
  subprojects.forEach {
    archives(it)
  }
}

idea {
  project {
    jdkName = Versions.Build.java
    vcs = "Git"
  }
}

//githubRelease {
//  setOwner("toolisticon")
//  setToken(propertySafe("github.token"))
//  setOverwrite(true)
//  setPrerelease((project.version as String).endsWith("-SNAPSHOT"))
//  setVersion(project.version)
//}
//
//
//buildScan {
//  termsOfServiceUrl = "https://gradle.com/terms-of-service"
//  termsOfServiceAgree = "yes"
//  publishAlways()
//}
