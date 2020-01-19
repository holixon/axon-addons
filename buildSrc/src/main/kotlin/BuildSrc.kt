@file:Suppress("PackageDirectoryMismatch", "unused")
package _buildsrc

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.axonframework(module: String): Any = "org.axonframework:axon-$module:${Versions.axon}"
fun DependencyHandler.junit5(module: String): Any = "org.junit.jupiter:junit-jupiter-$module:${Versions.Test.junit5}"


fun Project.propertySafe(propertyName: String, fallbackToEmpty: Boolean = true): String {
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
