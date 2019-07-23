package toolisticon

import com.github.breadmoirai.githubreleaseplugin.GithubReleaseExtension
import toolisticon.ProjectData_gradle.ProjectDataExtensions.toolisticonProjectData

plugins {
  id("org.jetbrains.dokka")
  id("com.github.breadmoirai.github-release")
}

val projectData = project.toolisticonProjectData

with(extensions) {

  getByType<GithubReleaseExtension>().apply {
    setOwner(projectData.github.organization)
    setRepo(projectData.github.repository)
    setToken(projectData.github.token)
    setOverwrite(projectData.version.isSnapShot)
    setPrerelease(projectData.version.isSnapShot)
    setVersion(projectData.version.value)
  }
}
