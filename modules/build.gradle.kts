import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the java-library plugin to add support for Java Library
    kotlin("jvm") version Versions.kotlin apply false
}

subprojects {

    dependencies {

    }

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

