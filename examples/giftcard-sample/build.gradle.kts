import _buildsrc.axonframework

dependencies {
  implementation(axonframework("configuration"))

  testImplementation(axonframework("test"))
  testImplementation("org.hamcrest:hamcrest-core:2.1")
}
