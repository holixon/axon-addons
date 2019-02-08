package io.toolisticon.axon.addons.jgiven

import com.tngtech.jgiven.junit5.ScenarioTest


/**
 * Extend this to write fixture based aggregate tests.
 *
 * @param <T> type of Aggregate
 */
abstract class AggregateFixtureScenarioTest<T> : ScenarioTest<
  AggregateFixtureGiven<T>,
  AggregateFixtureWhen<T>,
  AggregateFixtureThen<T>>() {


  val GIVEN: AggregateFixtureGiven<T>
    get() = given()

  val WHEN: AggregateFixtureWhen<T>
    get() = `when`()

  val THEN: AggregateFixtureThen<T>
    get() = then()

}
