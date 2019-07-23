import toolisticon.ProjectData_gradle.ProjectDataExtensions.toolisticonProjectData

plugins {
  id("toolisticon.base")
  kotlin("jvm") version Versions.kotlin apply false

  `maven-publish`


  id("com.github.breadmoirai.github-release") 

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


githubRelease {
  setOwner("toolisticon")
  setToken(properties["github.token"] as String)
  setOverwrite(true)
  setPrerelease((project.version as String).endsWith("-SNAPSHOT"))
  setVersion(project.version)
}

