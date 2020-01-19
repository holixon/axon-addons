package setup

import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class AbstractKotlinLibrary(
  val name : String,
  val description: String,
  val publish : Boolean
) : Plugin<Project> {

  override fun apply(project: Project) = with(project) {

  }
}
