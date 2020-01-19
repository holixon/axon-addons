package io.toolisticon.addons.axon.jgiven.saga

import com.tngtech.jgiven.junit5.ScenarioTest

abstract class SagaFixtureScenarioTest<T> : ScenarioTest<SagaFixtureGiven<T>, SagaFixtureWhen<T>, SagaFixtureThen<T>>()
