package io.toolisticon.addons.axon.jgiven

import com.tngtech.jgiven.annotation.ProvidedScenarioState
import io.toolisticon.addons.axon.examples.giftcard.api.GiftcardCommand.IssueCommand
import io.toolisticon.addons.axon.examples.giftcard.api.GiftcardCommand.RedeemCommand
import io.toolisticon.addons.axon.examples.giftcard.api.GiftcardEvent.IssuedEvent
import io.toolisticon.addons.axon.examples.giftcard.api.GiftcardEvent.RedeemedEvent
import io.toolisticon.addons.axon.examples.giftcard.domain.GiftcardAggregate
import io.toolisticon.addons.jgiven.AND
import io.toolisticon.addons.jgiven.GIVEN
import io.toolisticon.addons.jgiven.THEN
import io.toolisticon.addons.jgiven.WHEN
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.Test

class GiftcardSampleScenarioTest : AggregateFixtureScenarioTest<GiftcardAggregate>() {

  @ProvidedScenarioState
  val fixture: AggregateTestFixture<GiftcardAggregate> = AggregateTestFixture(GiftcardAggregate::class.java)

  @Test
  fun `issue command creates aggregate`() {
    GIVEN
      .noPriorActivity()

    WHEN
      .command(IssueCommand("1", 100))

    THEN
      .expectEvent(IssuedEvent("1", 100))
      .AND
      .expectState(GiftcardAggregate("1", 100))
  }

  @Test
  fun `redeem 50`() {
    GIVEN
      .event(IssuedEvent("1", 100))

    WHEN
      .command(RedeemCommand("1", 50))

    THEN
      .expectEvent(RedeemedEvent("1", 50))
  }

  @Test
  fun `redeem twice by commands`() {
    GIVEN
      .command(IssueCommand("1", 100))
      .AND
      .command(RedeemCommand("1", 40))

    WHEN
      .command(RedeemCommand("1", 40))

    THEN
      .expectEvent(RedeemedEvent("1", 40))
      .AND
      .expectState("balance", 20) { it.balance }
  }


  @Test
  fun `redeem by vararg commands`() {
    GIVEN
      .command(IssueCommand("1", 100))
      .AND
      .commands(RedeemCommand("1", 40), RedeemCommand("1", 30))

    WHEN
      .command(RedeemCommand("1", 20))

    THEN
      .expectState("balance", 10) { it.balance }
  }
}
