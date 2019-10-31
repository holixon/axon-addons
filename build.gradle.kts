plugins {
  base
  idea
  `maven-publish`
  `build-scan`

  kotlin("jvm") version Versions.kotlin apply false
  id("com.github.breadmoirai.github-release") version Versions.Plugins.githubRelease
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
    jdkName = Versions.java
    vcs = "Git"
  }
}

githubRelease {
  setOwner("toolisticon")
  setToken(getPropertySafe("github.token"))
  setOverwrite(true)
  setPrerelease((project.version as String).endsWith("-SNAPSHOT"))
  setVersion(project.version)
}


buildScan {
  termsOfServiceUrl = "https://gradle.com/terms-of-service"
  termsOfServiceAgree = "yes"
  publishAlways()
}


fun getPropertySafe(propertyName: String, fallbackToEmpty: Boolean = true): String {
  val rawValue = properties[propertyName]
  return if (rawValue == null) {
    if (fallbackToEmpty) {
      return ""
    }
    throw IllegalStateException("Property $propertyName MUST be initialized.")
  } else {
    if (rawValue is String) {
      rawValue
    } else {
      rawValue.toString()
    }
  }
}
