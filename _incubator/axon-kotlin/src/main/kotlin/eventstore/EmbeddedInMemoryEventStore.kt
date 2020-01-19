package io.toolisticon.addons.axon.kotlin.eventstore

import org.axonframework.eventhandling.GlobalSequenceTrackingToken
import org.axonframework.eventhandling.TrackedEventMessage
import org.axonframework.eventhandling.TrackingToken
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import java.util.*
import kotlin.streams.asSequence

/**
 * Uses in memory NavigatableMap to store events, but unlike the InMemoryEventStorageEngine
 * allows get() access to the events and
 */
class EmbeddedInMemoryEventStore(
  storageEngine: InMemoryEventStorageEngine = InMemoryEventStorageEngine()
) : EmbeddedEventStore(EmbeddedEventStore
  .builder()
  .storageEngine(storageEngine)
) {

  @Suppress("UNCHECKED_CAST")
  val events: NavigableMap<TrackingToken, TrackedEventMessage<*>> by lazy {
    InMemoryEventStorageEngine::class.java.getDeclaredField("events")
      .apply { isAccessible = true }
      .get(storageEngine()) as NavigableMap<TrackingToken, TrackedEventMessage<*>>
  }

  override fun toString(): String = events.let {
    it.navigableKeySet().stream()
      .map { events[it] }
      .map { m ->
        String.format("\n\t[%03d] - %s",
          (m!!.trackingToken()!! as GlobalSequenceTrackingToken).globalIndex,
          m.payload
        )
      }.asSequence().joinToString()
  }
}
