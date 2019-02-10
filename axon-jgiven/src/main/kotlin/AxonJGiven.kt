package io.toolisticon.addons.axon.jgiven

import com.tngtech.jgiven.base.ScenarioTestBase
import com.tngtech.jgiven.junit5.ScenarioTest
import io.toolisticon.addons.axon.jgiven.aggregate.AggregateFixtureGiven
import io.toolisticon.addons.axon.jgiven.aggregate.AggregateFixtureThen
import io.toolisticon.addons.axon.jgiven.aggregate.AggregateFixtureWhen

/**
 * Defines a ScenarioTest based on AggregateFixtures.
 *
 * @param T type of aggregate
 */
typealias AggregateFixtureScenarioTest<T> = ScenarioTest<AggregateFixtureGiven<T>, AggregateFixtureWhen<T>, AggregateFixtureThen<T>>


val <G : Any, W : Any, T : Any> ScenarioTestBase<G, W, T>.GIVEN: G get() = given()
val <G : Any, W : Any, T : Any> ScenarioTestBase<G, W, T>.WHEN: W get() = `when`()
val <G : Any, W : Any, T : Any> ScenarioTestBase<G, W, T>.THEN: T get() = then()


/**
 * Marker annotation for all-open compiler plugin.
 */
internal annotation class AxonStage
