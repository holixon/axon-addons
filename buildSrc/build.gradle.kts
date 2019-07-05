plugins {
  `kotlin-dsl`
}

apply {
  // repos set in /gradle
  from("../gradle/repositories.gradle.kts")
}

kotlinDslPluginOptions {
  experimentalWarning.set(false)
}
