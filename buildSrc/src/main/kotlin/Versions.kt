import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.embeddedKotlinVersion

object Versions {

  val kotlin = embeddedKotlinVersion
  val java = JavaVersion.VERSION_1_8.toString()
  val axon = "4.2"

  val springBoot = "2.2.0.RELEASE"

  val jgivenAddons = "0.5.1"

  object Plugins {
    val githubRelease = "2.2.9"
    val dokka = "0.9.17"
    val bintray = "1.8.4"
  }

  object Test {
    val jgiven = "0.18.2"
    val junit5 = "5.5.2"
  }


  val mapdb = "3.0.7"
  val klogging = "1.6.26"

  @Deprecated("use Test.jgiven")
  val jgiven = "0.17.1"
  @Deprecated("use Test.junit5")
  val junit5 = "5.4.2"
}
