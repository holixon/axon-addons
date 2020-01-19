import java.util.*

repositories {
  mavenLocal()
  mavenCentral()
  jcenter()

  gradlePluginPortal()
}

configurations.all {
  with(resolutionStrategy) {
    cacheChangingModulesFor(0, TimeUnit.SECONDS)
    cacheDynamicVersionsFor(0, TimeUnit.SECONDS)
  }
}
