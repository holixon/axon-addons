import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.embeddedKotlinVersion

object Versions {

  val axon = "4.2"

  val springBoot = "2.2.0.RELEASE"

  val jgivenAddons = "0.6.0"


  val mapdb = "3.0.7"
  val klogging = "1.6.26"

  object Build {
    val kotlin = embeddedKotlinVersion
    val java = JavaVersion.VERSION_1_8.toString()
  }

  object Test {
    val jgiven = "0.18.2"
    val junit5 = "5.5.2"
    val logback = "1.2.3"
  }


}
