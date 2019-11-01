import _buildsrc.axonframework


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

        api(project(":axon-jgiven"))
    }
}
