package io.toolisticon.addons.axon.kotlin

import org.axonframework.config.Configuration
import org.axonframework.config.Configurer


fun Configurer.registerEventAndQueryHandler(handlerSupplier : (Configuration) -> Any) {

  lateinit var handler : Any

  eventProcessing { epc ->  handlerSupplier }

}
