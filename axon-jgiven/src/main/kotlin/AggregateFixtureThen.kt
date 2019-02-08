package io.toolisticon.axon.addons.jgiven

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.As
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import com.tngtech.jgiven.annotation.Hidden
import com.tngtech.jgiven.annotation.Quoted
import org.axonframework.test.aggregate.ResultValidator
import org.hamcrest.MatcherAssert


//@AxonStage
open class AggregateFixtureThen<T> : Stage<AggregateFixtureThen<T>>() {

  @ExpectedScenarioState(required = true)
  lateinit var resultValidator: ResultValidator<T>

  open fun expect_event(@Quoted event: Any) = this.expect_events(event)

  open fun expect_events(@Quoted vararg events: Any) = resultValidator { resultValidator.expectEvents(*events) }

  @As("expect state: $=$")
  open fun <E : Any> expect_state(@Quoted field:String, @Quoted expected: E, @Hidden accessor: (T) -> E ) = self().apply {
    resultValidator.expectState {
      val e = accessor(it)
      MatcherAssert.assertThat("state failed, expected $field=$expected, but was=$e", e == expected)
     // assert( e == expected ) { "expected state: '$name' not met, was: $e, expected: $expected " } }
  }}

  private fun resultValidator(block : () -> ResultValidator<T>) = self().apply { resultValidator = block.invoke() }
}
