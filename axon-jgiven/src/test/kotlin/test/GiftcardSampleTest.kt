package io.toolisticon.axon.addons.jgiven.test

import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.Test

class GiftcardSampleTest {

  private val fixture = AggregateTestFixture(GiftcardAggregate::class.java)


  @Test
  internal fun `issue cmd creates new aggregate`() {
    fixture.givenNoPriorActivity()
      .`when`(GiftcardCommand.IssueCommand("1", 100))
      .expectEvents(GiftcardEvent.IssuedEvent("1", 100))

  }
}
