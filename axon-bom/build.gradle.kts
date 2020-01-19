import _buildsrc.axonframework
import setup.ProjectSetup.addBintray

//apply<BomProject>()

plugins {
  id("java-platform")
  id("maven-publish")
}

version = "4.2.1-0-SNAPSHOT"

dependencies {
  constraints {
    api(axonframework("configuration"))
    api(axonframework("eventsourcing"))
    api(axonframework("messaging"))
    api(axonframework("modelling"))
    api(axonframework("server-connector"))
    api(axonframework("spring"))
    api(axonframework("spring-boot-autoconfigure"))
    api(axonframework("spring-boot-starter"))
    api(axonframework("test"))

    // api(project(":axon-jgiven"))
  }
}

configure<PublishingExtension> {
  publications {
    create<MavenPublication>(name) {
      from(components["javaPlatform"])
    }
  }
}

addBintray()

