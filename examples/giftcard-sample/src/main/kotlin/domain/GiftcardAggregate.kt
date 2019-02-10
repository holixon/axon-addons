package io.toolisticon.addons.axon.examples.giftcard.domain

import io.toolisticon.addons.axon.examples.giftcard.api.GiftcardCommand.IssueCommand
import io.toolisticon.addons.axon.examples.giftcard.api.GiftcardCommand.RedeemCommand
import io.toolisticon.addons.axon.examples.giftcard.api.GiftcardEvent.IssuedEvent
import io.toolisticon.addons.axon.examples.giftcard.api.GiftcardEvent.RedeemedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier

import org.axonframework.modelling.command.AggregateLifecycle.apply

@Suppress("unused")
data class GiftcardAggregate(

  @AggregateIdentifier
  var id: String? = null,

  var balance: Int = 0
) {
  companion object {

    @JvmStatic
    @CommandHandler
    fun create(cmd: IssueCommand) = GiftcardAggregate().apply {
      require(cmd.initialBalance > 0) { "initial balance has to be > 0, $cmd" }

      apply(IssuedEvent(cmd.id, cmd.initialBalance))
    }
  }

  @EventSourcingHandler
  fun on(evt: IssuedEvent) {
    this.id = evt.id
    this.balance = evt.initialBalance
  }

  @CommandHandler
  fun handle(cmd: RedeemCommand) {
    require(cmd.amount > 0) {"amount has to be > 0: $cmd"}
    require(balance >= cmd.amount) {"card $id has insufficient balance: $balance: $cmd"}

    apply(RedeemedEvent(cmd.id, cmd.amount))
  }

  @EventSourcingHandler
  fun on(evt: RedeemedEvent) {
    this.balance -= evt.amount
  }

}
