package io.toolisticon.axon.addons.jgiven

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import com.tngtech.jgiven.annotation.Quoted
import com.tngtech.jgiven.annotation.Table
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.TestExecutor


//@AxonStage
open class AggregateFixtureGiven<T> : Stage<AggregateFixtureGiven<T>>() {

  @ExpectedScenarioState(required = true)
  lateinit var fixture: AggregateTestFixture<T>

  @ProvidedScenarioState
  lateinit var testExecutor: TestExecutor<T>

  open fun no_prior_activity(): AggregateFixtureGiven<T> = execute { fixture.givenNoPriorActivity() }

  open fun command(@Quoted command: Any) = this.commands(command)
  open fun commands(@Quoted @Table vararg commands : Any)  = execute {
    if (!::testExecutor.isInitialized)
      fixture.givenCommands(*commands)
    else
      testExecutor.andGivenCommands(*commands)
  }

  open fun event(@Quoted event: Any) = this.events(event)
  open fun events(@Quoted @Table vararg events: Any) = execute {
    if (!::testExecutor.isInitialized)
      fixture.given(*events)
    else
      testExecutor.andGiven(*events)
  }

  private fun execute(block : () -> TestExecutor<T>) = self().apply {
     testExecutor = block.invoke()
  }


}
