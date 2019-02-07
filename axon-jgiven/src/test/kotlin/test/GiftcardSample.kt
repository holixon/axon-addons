package io.toolisticon.axon.addons.jgiven.test

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.modelling.command.TargetAggregateIdentifier


data class GiftcardAggregate(

  @AggregateIdentifier
  var id: String? = null,

  var balance: Int = 0
) {
  companion object {

    @JvmStatic
    @CommandHandler
    fun create(cmd: GiftcardCommand.IssueCommand) = GiftcardAggregate().apply {
      require(cmd.initialBalance > 0) { "initial balance has to be > 0, $cmd" }

      apply(GiftcardEvent.IssuedEvent(cmd.id, cmd.initialBalance))
    }
  }

  @EventSourcingHandler
  fun on(evt: GiftcardEvent.IssuedEvent) {
    this.id = evt.id
    this.balance = evt.initialBalance
  }

  @CommandHandler
  fun handle(cmd: GiftcardCommand.RedeemCommand) {
    require(cmd.amount > 0) {"amount has to be > 0: $cmd"}
    require(balance >= cmd.amount) {"card $id has insufficient balance: $balance: $cmd"}

    apply(GiftcardEvent.RedeemedEvent(cmd.id, cmd.amount))
  }

  @EventSourcingHandler
  fun on(evt: GiftcardEvent.RedeemedEvent) {
    this.balance -= evt.amount
  }

}

sealed class GiftcardCommand(@TargetAggregateIdentifier open val id: String) {

  data class IssueCommand(override val id: String, val initialBalance: Int) : GiftcardCommand(id)

  data class RedeemCommand(override val id: String, val amount: Int) : GiftcardCommand(id)

}

sealed class GiftcardEvent(open val id: String) {

  data class IssuedEvent(override val id: String, val initialBalance: Int) : GiftcardEvent(id)
  data class RedeemedEvent(override val id: String, val amount: Int) : GiftcardEvent(id)

}
