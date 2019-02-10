package io.toolisticon.addons.axon.jgiven.aggregate

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.*
import io.toolisticon.addons.axon.jgiven.AxonStage
import org.axonframework.test.aggregate.ResultValidator
import org.hamcrest.MatcherAssert


@AxonStage
class AggregateFixtureThen<T> : Stage<AggregateFixtureThen<T>>() {

  @ExpectedScenarioState(required = true)
  lateinit var resultValidator: ResultValidator<T>

  @As("expect event:")
  fun expectEvent(@Quoted event: Any) = this.expectEvents(event)

  @As("expect events:")
  fun expectEvents(@Quoted @Table vararg events: Any) = resultValidator { resultValidator.expectEvents(*events) }

  @As("expect state: $=$")
  fun <E : Any> expectState(@Quoted field:String, @Quoted expected: E, @Hidden accessor: (T) -> E ) = self().apply {
    resultValidator.expectState {
      val e = accessor(it)
      MatcherAssert.assertThat("state failed, expected $field=$expected, but was=$e", e == expected)
     // assert( e == expected ) { "expected state: '$name' not met, was: $e, expected: $expected " } }
  }}!!

  @As("expect state: $")
  fun expectState(@Quoted expected: T) = self().apply {
    resultValidator.expectState {
      MatcherAssert.assertThat("state failed, expected '$expected', but was=$it", it == expected)
    }
  }!!

  private fun resultValidator(block : () -> ResultValidator<T>) = self().apply { resultValidator = block.invoke() }!!
}
