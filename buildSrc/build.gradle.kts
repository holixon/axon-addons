plugins {
  `java-gradle-plugin`
  `kotlin-dsl`
  `kotlin-dsl-precompiled-script-plugins`
}

apply {
  from("../gradle/repositories.gradle.kts")
  from("src/main/kotlin/toolisticon/dependencies.txt")
}

kotlinDslPluginOptions {
  experimentalWarning.set(false)
}
