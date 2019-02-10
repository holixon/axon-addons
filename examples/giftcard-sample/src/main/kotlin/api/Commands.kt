package io.toolisticon.addons.axon.examples.giftcard.api

import org.axonframework.modelling.command.TargetAggregateIdentifier


sealed class GiftcardCommand(@TargetAggregateIdentifier open val id: String) {

  data class IssueCommand(override val id: String, val initialBalance: Int) : GiftcardCommand(id)

  data class RedeemCommand(override val id: String, val amount: Int) : GiftcardCommand(id)

}
