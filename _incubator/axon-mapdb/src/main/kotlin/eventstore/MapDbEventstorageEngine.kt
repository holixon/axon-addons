package io.toolisticon.addons.axon.mapdb.eventstore

import org.axonframework.eventhandling.*
import org.axonframework.eventsourcing.eventstore.DomainEventStream
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.mapdb.BTreeMap
import org.mapdb.DB
import java.time.Instant
import java.util.*
import java.util.concurrent.ConcurrentNavigableMap
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.IntStream
import java.util.stream.Stream
import kotlin.streams.toList


@Suppress("unchecked")
class MapDbEventStorageEngine(private val db: DB) : EventStorageEngine {

  val events: ConcurrentNavigableMap<TrackingToken, TrackedEventMessage<*>>
  val snapshots: ConcurrentNavigableMap<String, DomainEventMessage<*>>

  init {
    this.events = db.treeMap(name = "events").createOrOpen() as BTreeMap<TrackingToken, TrackedEventMessage<*>>
    this.snapshots = db.treeMap("snapshots").createOrOpen() as BTreeMap<String, DomainEventMessage<*>>
  }

  override fun appendEvents(events: List<EventMessage<*>>) {
    synchronized(this.events) {
      val trackingToken = nextTrackingToken()

      this.events.putAll(
        IntStream.range(0, events.size)
          .mapToObj<TrackedEventMessage<*>> { EventUtils.asTrackedEventMessage(events[it], trackingToken.offsetBy(it)) }
          .toList()
          .associate { it.trackingToken() to it }
      )
      db.commit()
    }
  }

  override fun storeSnapshot(snapshot: DomainEventMessage<*>) {
    snapshots[snapshot.aggregateIdentifier] = snapshot
  }

  override fun readEvents(trackingToken: TrackingToken?, mayBlock: Boolean): Stream<out TrackedEventMessage<*>> {
    return if (trackingToken == null)
      events.values.stream()
    else
      events.tailMap(trackingToken, false).values.stream()
  }

  override fun readEvents(aggregateIdentifier: String, firstSequenceNumber: Long): DomainEventStream {
    val sequenceNumber = AtomicReference<Long>()
    val stream = events.values.stream().filter { event -> event is DomainEventMessage<*> }
      .map { it as DomainEventMessage<*> }
      .filter { aggregateIdentifier == it.aggregateIdentifier && it.sequenceNumber >= firstSequenceNumber }
      .peek { sequenceNumber.set(it.sequenceNumber) }
    return DomainEventStream.of(stream) { sequenceNumber.get() }
  }

  override fun readSnapshot(aggregateIdentifier: String): Optional<DomainEventMessage<*>> {
    return Optional.ofNullable(snapshots[aggregateIdentifier])
  }

  override fun createTailToken(): TrackingToken? {
    if (events.size == 0) {
      return null
    }
    val firstToken = events.firstKey() as GlobalSequenceTrackingToken
    return GlobalSequenceTrackingToken(firstToken.globalIndex - 1)
  }

  override fun createHeadToken(): TrackingToken? {
    return if (events.size == 0)
      null
    else
      events.lastKey()
  }

  override fun createTokenAt(dateTime: Instant): TrackingToken? = events.values
    .stream()
    .filter { event -> event.timestamp == dateTime || event.timestamp.isAfter(dateTime) }
    .min(Comparator.comparingLong { e ->
      (e.trackingToken() as GlobalSequenceTrackingToken)
        .globalIndex
    })
    .map { it.trackingToken() }
    .map { tt -> tt as GlobalSequenceTrackingToken }
    .map { tt -> GlobalSequenceTrackingToken(tt.globalIndex - 1) }
    .orElse(null)

  /**
   * Returns the tracking token to use for the next event to be stored.
   *
   * @return the tracking token for the next event
   */
  private fun nextTrackingToken(): GlobalSequenceTrackingToken {
    return if (events.isEmpty())
      GlobalSequenceTrackingToken(0)
    else
      (events.lastKey() as GlobalSequenceTrackingToken).next()
  }
}
