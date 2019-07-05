plugins {

  // activate kotlin dsl for build.gradle for whole project
  `kotlin-dsl`
}

apply {
  // repos set in /gradle
  from("../gradle/repositories.gradle.kts")
}


kotlinDslPluginOptions {
  experimentalWarning.set(false)
}
