package io.toolisticon.addons.axon.jgiven.aggregate

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.*
import io.toolisticon.addons.axon.jgiven.AxonStage
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.TestExecutor
import java.time.Duration
import java.time.Instant
import java.util.function.Supplier


@AxonStage
class AggregateFixtureGiven<T> : Stage<AggregateFixtureGiven<T>>() {

  @ExpectedScenarioState(required = true)
  lateinit var fixture: AggregateTestFixture<T>

  @ProvidedScenarioState
  lateinit var testExecutor: TestExecutor<T>

  @As("no prior activity")
  fun noPriorActivity(): AggregateFixtureGiven<T> = execute { fixture.givenNoPriorActivity() }

  @As("command:")
  fun command(@Quoted command: Any) = this.commands(command)

  @As("commands:")
  fun commands(@Quoted @Table vararg commands: Any) = this.commands(commands.toList())

  @As("commands:")
  fun commands(@Quoted @Table commands: List<Any>) = execute {
    if (!::testExecutor.isInitialized)
      fixture.givenCommands(commands)
    else
      testExecutor.andGivenCommands(commands)
  }

  @As("event:")
  fun event(@Quoted event: Any) = this.events(event)

  @As("events:")
  fun events(@Quoted @Table vararg events: Any) = this.commands(events.toList())

  @As("events:")
  fun events(@Quoted @Table events: List<Any>) = execute {
    if (!::testExecutor.isInitialized)
      fixture.given(events)
    else
      testExecutor.andGiven(events)
  }

  fun currentTime(instant: Instant) = execute {
    if (!::testExecutor.isInitialized)
      fixture.givenCurrentTime(instant)
    else
      testExecutor.andGivenCurrentTime(instant)
  }

  fun timeAdvancesTo(instant: Instant) {
    if (!::testExecutor.isInitialized)
      fixture.andThenTimeAdvancesTo(instant)
    else
      testExecutor.andThenTimeAdvancesTo(instant)
  }

  fun timeElapses(duration: Duration) {
    if (!::testExecutor.isInitialized)
      fixture.andThenTimeElapses(duration)
    else
      testExecutor.andThenTimeElapses(duration)
  }

  fun state(aggregate: Supplier<T>) {
    fixture.givenState(aggregate)
  }
  
  fun state(aggregate: () -> T) {
    fixture.givenState(aggregate)
  }

  private fun execute(block: () -> TestExecutor<T>) = self().apply {
    testExecutor = block.invoke()
  }!!

}
