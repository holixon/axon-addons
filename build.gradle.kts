import toolisticon.ProjectData_gradle.ProjectDataExtensions.toolisticonProjectData

plugins {
  id("toolisticon.base")
  kotlin("jvm") version Versions.kotlin apply false

  `maven-publish`

}

allprojects {
  apply {
    plugin("toolisticon.projectData")
  }

  group = toolisticonProjectData.pom.groupId
  version = toolisticonProjectData.pom.version

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

