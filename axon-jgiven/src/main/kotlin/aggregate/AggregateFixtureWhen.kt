package io.toolisticon.addons.axon.jgiven.aggregate

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.*
import io.toolisticon.addons.jgiven.JGivenKotlinStage
import org.axonframework.test.aggregate.ResultValidator
import org.axonframework.test.aggregate.TestExecutor

@JGivenKotlinStage
class AggregateFixtureWhen<T> : Stage<AggregateFixtureWhen<T>>() {

  @ExpectedScenarioState(required = true)
  lateinit var testExecutor: TestExecutor<T>

  @ProvidedScenarioState
  lateinit var resultValidator: ResultValidator<T>

  @As("command:")
  fun command(@Quoted cmd: Any) = execute { testExecutor.`when`(cmd) }

  @As("command: \$cmd, metadata: \$metadata")
  fun command(@Quoted cmd: Any, @Table metadata: Map<String, *>) = execute { testExecutor.`when`(cmd, metadata) }

  private fun execute(block: () -> ResultValidator<T>) = self().apply { resultValidator = block.invoke() }!!

}
