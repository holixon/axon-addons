package io.toolisticon.addons.axon.jgiven.aggregate

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.*
import io.toolisticon.addons.axon.jgiven.AxonStage
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.TestExecutor


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
  fun commands(@Quoted @Table vararg commands : Any)  = execute {
    if (!::testExecutor.isInitialized)
      fixture.givenCommands(*commands)
    else
      testExecutor.andGivenCommands(*commands)
  }

  @As("event:")
  fun event(@Quoted event: Any) = this.events(event)

  @As("events:")
  fun events(@Quoted @Table vararg events: Any) = execute {
    if (!::testExecutor.isInitialized)
      fixture.given(*events)
    else
      testExecutor.andGiven(*events)
  }

  private fun execute(block : () -> TestExecutor<T>) = self().apply {
     testExecutor = block.invoke()
  }!!


}
