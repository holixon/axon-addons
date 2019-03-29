package io.toolisticon.addons.axon.jgiven.saga

import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga

class CountingSaga {

  var counter = 0

  @StartSaga
  @SagaEventHandler(associationProperty = SagaEvent.associationProperty)
  fun on(event: SagaEvent.Start) {

  }

  @SagaEventHandler(associationProperty = SagaEvent.associationProperty)
  fun on(event: SagaEvent.Increment) {
    counter += event.num
  }

  @EndSaga
  @SagaEventHandler(associationProperty = SagaEvent.associationProperty)
  fun on(event: SagaEvent.Stop) {}
}


sealed class SagaEvent(open val id: String) {
  companion object {
    const val associationProperty = "id"
  }

  data class Start(override val id: String) : SagaEvent(id)

  data class Increment(override val id: String, val num: Int) : SagaEvent(id)

  data class Stop(override val id: String) : SagaEvent(id)
}
