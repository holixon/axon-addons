package io.toolisticon.addons.axon.kotlin.config

import org.axonframework.config.AggregateConfigurer
import org.axonframework.config.Configurer
import org.axonframework.config.DefaultConfigurer
import org.axonframework.eventsourcing.eventstore.EventStore
import kotlin.reflect.KClass


class AxonConfigurer( val configurer: Configurer = DefaultConfigurer.defaultConfiguration()) {

  fun configureAggregate(type: KClass<*>): AxonConfigurer = apply {
    configurer.configureAggregate(AggregateConfigurer.defaultConfiguration(type.java))
  }

  fun configureEventStore(eventStore: EventStore): AxonConfigurer = apply {
    configurer.configureEventStore { eventStore }
  }

//  fun registerProjection(projection: (Configuration) -> Any) = apply {
//    configurer.eventProcessing {
//      epc -> epc.registerEventHandler { projection }
//    }
//    configurer.registerQueryHandler { projection }
//  }

  fun <T:Any> registerComponent(type: KClass<T>, component: T) = apply {
    configurer.registerComponent(type.java) { component }
  }

  fun build() = configurer.buildConfiguration()!!


}
