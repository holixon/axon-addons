package io.toolisticon.axon.addons.jgiven

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import com.tngtech.jgiven.annotation.Quoted
import org.axonframework.test.aggregate.ResultValidator
import org.axonframework.test.aggregate.TestExecutor

//@AxonStage
open class AggregateFixtureWhen<T> : Stage<AggregateFixtureWhen<T>>() {

  @ExpectedScenarioState(required = true)
  lateinit var testExecutor: TestExecutor<T>

  @ProvidedScenarioState
  lateinit var resultValidator: ResultValidator<T>

  open fun command(@Quoted cmd: Any) = execute { testExecutor.`when`(cmd) }

  private fun execute(block : () -> ResultValidator<T>) = self().apply { resultValidator = block.invoke() }

}
