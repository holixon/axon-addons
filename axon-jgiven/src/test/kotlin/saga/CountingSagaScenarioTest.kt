package io.toolisticon.addons.axon.jgiven.saga

import com.tngtech.jgiven.annotation.ProvidedScenarioState
import io.toolisticon.addons.axon.jgiven.SagaFixtureScenarioTest
import io.toolisticon.addons.jgiven.GIVEN
import io.toolisticon.addons.jgiven.THEN
import io.toolisticon.addons.jgiven.WHEN
import org.axonframework.test.saga.SagaTestFixture
import org.junit.jupiter.api.Test

class CountingSagaScenarioTest : SagaFixtureScenarioTest<CountingSaga>() {

  companion object {
    const val aggregateId = "1"
    val START = SagaEvent.Started(aggregateId)
    val STOP = SagaEvent.Stopped(aggregateId)
    val COUNT = SagaEvent.Incremented(aggregateId, 1)
  }

  @ProvidedScenarioState
  private val fixture : SagaTestFixture<CountingSaga> = SagaTestFixture(CountingSaga::class.java).apply {
    withTransienceCheckDisabled()
  }

  @Test
  internal fun `no saga started`() {
    GIVEN
      .noPriorActivity()

    THEN
      .expectNoActiveSagas()
  }

  @Test
  internal fun `saga starts on event`() {
    GIVEN
      .noPriorActivity()

    WHEN
      .aggregatePublishes(aggregateId, START)

    THEN
      .expectActiveSagas(1)
  }

  @Test
  internal fun `can finish saga`() {
    GIVEN
      .aggregatePublishedEvent(aggregateId, START)

    THEN
      .expectActiveSagas(1)

    WHEN
      .aggregatePublishes(aggregateId, STOP)

    THEN
      .expectNoActiveSagas()
  }

  @Test
  internal fun `publish more then one event`() {
    GIVEN
      .aggregatePublishedEvents(aggregateId, START, COUNT)

    THEN
      .expectActiveSagas(1)
  }

  @Test
  internal fun `expect commands`() {
    GIVEN
      .aggregatePublishedEvent(aggregateId, START)

    WHEN
      .aggregatePublishes(aggregateId, COUNT)

    THEN
      .expectDispatchedCommand(SagaCommand.IncrementAggregate(aggregateId))
  }
}
