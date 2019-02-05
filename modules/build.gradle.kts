
plugins {
    // Apply the java-library plugin to add support for Java Library
    kotlin("jvm") version Versions.kotlin apply false
}

subprojects {

    dependencies {

    }

}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}
