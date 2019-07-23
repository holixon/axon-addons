package toolisticon

import Versions
import com.gradle.scan.plugin.BuildScanExtension
import org.gradle.plugins.ide.idea.model.IdeaModel

plugins {
  id("org.gradle.base")
  id("org.gradle.idea")
  id("com.gradle.build-scan")
}

with(rootProject.extensions) {

  getByType<BuildScanExtension>().apply {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
    publishAlways()
  }

  getByType<IdeaModel>().apply {

    project {
      jdkName = Versions.java
      vcs = "Git"
    }
    module {
      excludeDirs = mutableSetOf(File("gradle"))
    }
  }

}


