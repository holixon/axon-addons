package io.toolisticon.addons.axon.examples.giftcard.api


sealed class GiftcardEvent(open val id: String) {

  data class IssuedEvent(override val id: String, val initialBalance: Int) : GiftcardEvent(id)
  data class RedeemedEvent(override val id: String, val amount: Int) : GiftcardEvent(id)

}
