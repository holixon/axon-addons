plugins {
  base
  idea
}

allprojects {
  group = "io.toolisticon.addons.axon"

  apply {
    from("${rootProject.rootDir}/gradle/repositories.gradle.kts")
  }
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

//  setOverwrite(true)
//githubRelease {
//  setOwner("toolisticon")
//  setToken(propertySafe("github.token"))
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
