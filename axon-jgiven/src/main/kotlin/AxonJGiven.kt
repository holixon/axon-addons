package io.toolisticon.addons.axon.jgiven

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.base.ScenarioTestBase
import com.tngtech.jgiven.junit5.ScenarioTest
import io.toolisticon.addons.axon.jgiven.aggregate.AggregateFixtureGiven
import io.toolisticon.addons.axon.jgiven.aggregate.AggregateFixtureThen
import io.toolisticon.addons.axon.jgiven.aggregate.AggregateFixtureWhen
import io.toolisticon.addons.axon.jgiven.saga.SagaFixtureGiven
import io.toolisticon.addons.axon.jgiven.saga.SagaFixtureThen
import io.toolisticon.addons.axon.jgiven.saga.SagaFixtureWhen

/**
 * Defines a ScenarioTest based on AggregateFixtures.
 *
 * @param T type of aggregate
 */
typealias AggregateFixtureScenarioTest<T> = ScenarioTest<AggregateFixtureGiven<T>, AggregateFixtureWhen<T>, AggregateFixtureThen<T>>

/**
 * Defines ScenarioTest based on SagaTestFixture.
 *
 * @param T type of saga
 */
typealias SagaFixtureScenarioTest<T> = ScenarioTest<SagaFixtureGiven<T>, SagaFixtureWhen<T>, SagaFixtureThen<T>>


val <G : Stage<G>, W : Stage<W>, T : Stage<T>> ScenarioTestBase<G, W, T>.GIVEN: G get() = given()
val <G : Stage<G>, W : Stage<W>, T : Stage<T>> ScenarioTestBase<G, W, T>.WHEN: W get() = `when`()
val <G : Stage<G>, W : Stage<W>, T : Stage<T>> ScenarioTestBase<G, W, T>.THEN: T get() = then()

val <X : Stage<X>> Stage<X>.AND: X get() = and()
val <X : Stage<X>> Stage<X>.WITH: X get() = with()
val <X : Stage<X>> Stage<X>.BUT: X get() = but()

/**
 * Marker annotation for all-open compiler plugin.
 */
internal annotation class AxonStage
