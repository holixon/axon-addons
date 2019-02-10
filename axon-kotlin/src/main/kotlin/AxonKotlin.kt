package io.toolisticon.addons.axon.kotlin

import org.axonframework.commandhandling.CommandCallback
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.serialization.SimpleSerializedType
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster
import kotlin.reflect.KClass

// empty


fun <C> CommandGateway.send(cmd: C) = this.send<Any>(cmd)

//
///**
// * Simplifies sending commands with callback to the CommandGateway.
// */
//inline fun <C, R> CommandGateway.send(command: C,
//                                      crossinline success: (CommandMessage<out C>, R) -> Unit = { _, _: R -> },
//                                      crossinline failure: (CommandMessage<out C>, Throwable) -> Unit = { _, _: Throwable -> }
//) = this.send(command, object : CommandCallback<C, R> {
//  override fun onSuccess(commandMessage: CommandMessage<out C>, result: R) {
//    success(commandMessage, result)
//  }
//
//  override fun onFailure(commandMessage: CommandMessage<out C>, cause: Throwable) {
//    failure(commandMessage, cause)
//  }
//})


/**
 * Tuple of oldRevision (nullable) and newRevision.
 */
typealias Revisions = Pair<String?, String>

/**
 * Creates a singleEventUpcaster for given type and revisions and calls [IntermediateEventRepresentation#upcastPayload] using the [converter].
 */
fun <T : Any> singleEventUpcaster(eventType: KClass<*>,
                                  storageType: KClass<T>,
                                  revisions: Revisions,
                                  converter: (T) -> T): SingleEventUpcaster = object : SingleEventUpcaster() {

  override fun canUpcast(ir: IntermediateEventRepresentation): Boolean = SimpleSerializedType(eventType.qualifiedName, revisions.first) == ir.type

  override fun doUpcast(ir: IntermediateEventRepresentation): IntermediateEventRepresentation =
    ir.upcastPayload(
      SimpleSerializedType(eventType.qualifiedName, revisions.second),
      storageType.java,
      converter)
}
