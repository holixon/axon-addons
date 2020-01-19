package io.toolisticon.addons.axon.jgiven.aggregate

import com.tngtech.jgiven.junit5.ScenarioTest

abstract class  AggregateFixtureScenarioTest<T> : ScenarioTest<AggregateFixtureGiven<T>, AggregateFixtureWhen<T>, AggregateFixtureThen<T>>()
