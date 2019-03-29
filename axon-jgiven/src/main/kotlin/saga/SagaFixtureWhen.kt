package io.toolisticon.addons.axon.jgiven.saga

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.*
import io.toolisticon.addons.axon.jgiven.AxonStage
import org.axonframework.test.saga.FixtureExecutionResult
import org.axonframework.test.saga.WhenState
import java.time.Duration
import java.time.Instant

@AxonStage
class SagaFixtureWhen<T> : Stage<SagaFixtureWhen<T>>(){

  @ExpectedScenarioState(required = true)
  lateinit var whenState : WhenState

  @ProvidedScenarioState
  lateinit var thenState : FixtureExecutionResult


  @As("when aggregate $ publishes event: $")
  fun aggregatePublishes(@Quoted aggregateIdentifier: String, event: Any ): SagaFixtureWhen<T> = self().apply{
    thenState = whenState.whenAggregate(aggregateIdentifier).publishes(event)
  }

  fun timeAdvancesTo(date: Instant) = self().apply {
    thenState = whenState.whenTimeAdvancesTo(date)
  }


  fun publishing(event: Any): SagaFixtureWhen<T> = self().apply {
    thenState = whenState.whenPublishingA(event)
  }

  fun timeElapses(duration : Duration) = self().apply {
    thenState = whenState.whenTimeElapses(duration)
  }


}
