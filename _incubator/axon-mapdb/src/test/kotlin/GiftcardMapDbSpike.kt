package io.toolisticon.addons.axon.mapdb

import io.toolisticon.addons.axon.examples.giftcard.api.GiftcardCommand.*
import io.toolisticon.addons.axon.examples.giftcard.api.GiftcardQuery.*
import io.toolisticon.addons.axon.examples.giftcard.domain.GiftcardAggregate
import io.toolisticon.addons.axon.examples.giftcard.projection.GiftcardSummary
import io.toolisticon.addons.axon.mapdb.eventstore.MapDbEventStorageEngine
import org.axonframework.config.DefaultConfigurer
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.mapdb.DBMaker



fun main() {
  val tmp = createTempFile("axon", "db")

  val db = DBMaker.fileDB(tmp.absoluteFile).closeOnJvmShutdown().make()
  val storageEngine = MapDbEventStorageEngine(db)
  val eventStore = EmbeddedEventStore.builder()
    .storageEngine(storageEngine)
    .build()

  val axonConfiguration = DefaultConfigurer.defaultConfiguration()
    .configureAggregate(GiftcardAggregate::class.java)
    .configureEventStore { eventStore }
    .buildConfiguration()

  val commandGateway by lazy {
    axonConfiguration.commandGateway()
  }

  val queryGateway by lazy {
    axonConfiguration.queryGateway()
  }

  axonConfiguration.start()

  commandGateway.sendAndWait<Any>(IssueCommand("1", 100))

  val summaries = queryGateway.query(FindAll, ResponseTypes.multipleInstancesOf(GiftcardSummary::class.java)).join()

  println("summaries: $summaries")

  axonConfiguration.shutdown()
}
