import org.gradle.kotlin.dsl.embeddedKotlinVersion

object Versions {

  val release = mapOf(
    "axon-jgiven" to "0.1.0-SNAPSHOT",
    "axon-kotlin" to "0.1.0-SNAPSHOT"
  )

  val bintray = "1.8.4"
  val kotlin = embeddedKotlinVersion
  val axon = "4.1"

  val mapdb = "3.0.7"
  val klogging = "1.6.22"

  val jgiven = "0.17.1"
  val junit5 = "5.3.2"
}
