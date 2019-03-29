package io.toolisticon.addons.axon.jgiven.saga

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import javax.inject.Inject

class CountingSaga {

  @Inject
  @Transient private lateinit var commandGateway: CommandGateway


  var counter = 0

  @StartSaga
  @SagaEventHandler(associationProperty = SagaEvent.associationProperty)
  fun on(event: SagaEvent.Started) {

  }

  @SagaEventHandler(associationProperty = SagaEvent.associationProperty)
  fun on(event: SagaEvent.Incremented) {
    counter += event.num

    commandGateway.send<Any>(SagaCommand.IncrementAggregate(event.id))
  }

  @EndSaga
  @SagaEventHandler(associationProperty = SagaEvent.associationProperty)
  fun on(event: SagaEvent.Stopped) {}

}


sealed class SagaEvent(open val id: String) {
  companion object {
    const val associationProperty = "id"
  }

  data class Started(override val id: String) : SagaEvent(id)

  data class Incremented(override val id: String, val num: Int) : SagaEvent(id)

  data class Stopped(override val id: String) : SagaEvent(id)
}

sealed class SagaCommand(open val id: String) {

  data class IncrementAggregate(override val id: String) : SagaCommand(id)

}
