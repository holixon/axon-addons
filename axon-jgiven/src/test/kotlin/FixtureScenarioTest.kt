package io.toolisticon.axon.addons.jgiven

import com.tngtech.jgiven.annotation.ProvidedScenarioState
import io.toolisticon.axon.addons.jgiven.test.GiftcardAggregate
import io.toolisticon.axon.addons.jgiven.test.GiftcardCommand.*
import io.toolisticon.axon.addons.jgiven.test.GiftcardEvent.*
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.Test


 class GiftcardSampleScenarioTest : AggregateFixtureScenarioTest<GiftcardAggregate>() {

  @ProvidedScenarioState
  val fixture: AggregateTestFixture<GiftcardAggregate> = AggregateTestFixture(GiftcardAggregate::class.java)

  @Test
  fun `issue command creates aggregate`() {
    GIVEN
      .no_prior_activity()

    WHEN
      .command(IssueCommand("1",100))

    THEN
      .expect_event(IssuedEvent("1",100))
  }

  @Test
  fun `redeem 50`() {
    GIVEN
      .event(IssuedEvent("1",100))

    WHEN
      .command(RedeemCommand("1", 50))

    THEN
      .expect_event(RedeemedEvent("1", 50))
  }

   @Test
   fun `redeem twice by commands`() {
     GIVEN
       .command(IssueCommand("1", 100))
       .and()
       .command(RedeemCommand("1", 40))

     WHEN
       .command(RedeemCommand("1",40))

     THEN
       .expect_event(RedeemedEvent("1", 40))
       .and()
       .expect_state("balance", 20 ) { it.balance }
   }


   @Test
   fun `redeem by vararg commands`() {
     GIVEN
       .command(IssueCommand("1", 100))
       .and()
       .commands(RedeemCommand("1", 40), RedeemCommand("1", 30))

     WHEN
       .command(RedeemCommand("1",20))

     THEN
       .expect_state("balance", 10 ) { it.balance }
   }
 }
