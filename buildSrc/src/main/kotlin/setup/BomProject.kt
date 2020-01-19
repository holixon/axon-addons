package setup

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import setup.ProjectSetup.addBintray

class BomProject : Plugin<Project> {
  override fun apply(project: Project) = with(project) {

    apply(plugin = "java-platform")
    apply(plugin = "maven-publish")

    configure<PublishingExtension> {
      publications {
        create<MavenPublication>(name) {
          from(components["javaPlatform"])
        }
      }
    }

    addBintray()
  }
}
