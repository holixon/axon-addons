plugins {
  `java-platform`
  `maven-publish`
}

dependencies {
  constraints {
    api(project(":axon-jgiven"))
  }
}

tasks {
  publishToMavenLocal {
    dependsOn(build)
  }
}

publishing {
  publications {
    create<MavenPublication>(project.name) {
      artifactId = project.name
      from(components["javaPlatform"])

      pom.withXml(name = "${rootProject.name}/${project.name}", description = "BOM for axon addons")
    }
  }
}
