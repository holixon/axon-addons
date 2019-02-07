package _buildsrc

import org.gradle.api.artifacts.dsl.DependencyHandler


fun DependencyHandler.axon(module: String): Any = "org.axonframework:axon-$module:${Versions.axon}"
fun DependencyHandler.junit5(module: String): Any = "org.junit.jupiter:junit-jupiter-$module:${Versions.junit5}"


