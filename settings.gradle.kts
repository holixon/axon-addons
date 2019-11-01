import setup.ProjectSetup.Bom
import setup.ProjectSetup.ExampleKotlinLibrary
import setup.ProjectSetup.KotlinLibrary
import setup.ProjectSetup.includeModules

rootProject.name = "axon-addons"

includeModules(
  Bom(name = "axon-bom"),
  KotlinLibrary(name = "axon-jgiven", description = "Addons for jgiven test tool"),
  ExampleKotlinLibrary(name = "giftcard-sample")
)
