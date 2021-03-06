@file:Suppress("unused")
package io.toolisticon.addons.axon.jgiven.saga

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.As
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import com.tngtech.jgiven.annotation.Table
import io.toolisticon.addons.jgiven.JGivenKotlinStage
import org.axonframework.deadline.DeadlineMessage
import org.axonframework.eventhandling.EventMessage
import org.axonframework.test.saga.FixtureExecutionResult
import org.hamcrest.Matcher
import java.time.Duration
import java.time.Instant


@JGivenKotlinStage
class SagaFixtureThen<T> : Stage<SagaFixtureThen<T>>() {

  @ExpectedScenarioState(required = true)
  lateinit var thenState: FixtureExecutionResult

  /**
   * Expect the given number of Sagas to be active (i.e. ready to respond to incoming events.
   *
   * @param expected the expected number of active events in the repository
   * @return the FixtureExecutionResult for method chaining
   */
  @As("expect $ active sagas")
  fun expectActiveSagas(expected: Int): SagaFixtureThen<T> = self().apply {
    thenState.expectActiveSagas(expected)
  }

  fun expectNoActiveSagas(): SagaFixtureThen<T> = expectActiveSagas(0)

  fun expectDispatchedCommand(command: Any) = expectDispatchedCommands(command)

  /**
   * Asserts that the given commands have been dispatched in exactly the order given. The command objects are
   * compared using the equals method. Only commands as a result of the event in the "when" stage of the fixture are
   * compared.
   *
   * @param commands The expected commands
   * @return the FixtureExecutionResult for method chaining
   */
  fun expectDispatchedCommands(@Table vararg commands: Any) = self().apply {
    thenState = thenState.expectDispatchedCommands(*commands)
  }


  /**
   * Asserts that at least one of the active sagas is associated with the given `associationKey` and
   * `associationValue`.
   */
  fun expectAssociationWith(associationKey: String, associationValue: Any) = self().apply {
    thenState.expectAssociationWith(associationKey, associationValue)
  }


  /**
   * Asserts that at none of the active sagas is associated with the given `associationKey` and
   * `associationValue`.
   */
  fun expectNoAssociationWith(associationKey: String, associationValue: Any) = self().apply {
    thenState.expectNoAssociationWith(associationKey, associationValue)
  }


  /**
   * Asserts that an event matching the given `matcher` has been scheduled to be published after the given
   * `duration`.
   */
  fun expectScheduledEventMatching(duration: Duration, matcher: Matcher<in EventMessage<*>>) = self().apply {
    thenState.expectScheduledEventMatching(duration, matcher)
  }

  /**
   * Asserts that a deadline scheduled after given `duration` matches the given `matcher`.
   */
  fun expectScheduledDeadlineMatching(duration: Duration, matcher: Matcher<in DeadlineMessage<*>>) = self().apply {
    thenState.expectScheduledDeadlineMatching(duration, matcher)
  }

  /**
   * Asserts that an event equal to the given ApplicationEvent has been scheduled for publication after the given
   * `duration`.
   *
   * Note that the source attribute of the application event is ignored when comparing events. Events are compared
   * using an "equals" check on all fields in the events.
   *
   * @param duration The time to wait before the event should be published
   * @param event    The expected event
   */
  fun expectScheduledEvent(duration: Duration, event: Any) = self().apply {
    thenState.expectScheduledEvent(duration, event)
  }

  /**
   * Asserts that a deadline equal to the given `deadline` has been scheduled after the given `duration`.
   *
   *
   * Note that the source attribute of the deadline is ignored when comparing deadlines. Deadlines are compared using
   * an "equals" check on all fields in the deadlines.
   *
   * @param duration The time to wait before the deadline should be met
   * @param deadline The expected deadline
   */
  fun expectScheduledDeadline(duration: Duration, deadline: Any) = self().apply {
    thenState.expectScheduledDeadline(duration, deadline)
  }

  /**
   * Asserts that an event of the given `eventType` has been scheduled for publication after the given
   * `duration`.
   *
   * @param duration  The time to wait before the event should be published
   * @param eventType The type of the expected event
   */
  fun expectScheduledEventOfType(duration: Duration, eventType: Class<*>) = self().apply {
    thenState.expectScheduledEventOfType(duration, eventType)
  }

  /**
   * Asserts that a deadline of the given `deadlineType` has been scheduled after the given `duration`.
   *
   * @param duration     The time to wait before the deadline is met
   * @param deadlineType The type of the expected deadline
   */
  fun expectScheduledDeadlineOfType(duration: Duration, deadlineType: Class<*>) = self().apply {
    thenState.expectScheduledDeadlineOfType(duration, deadlineType)
  }

  /**
   * Asserts that an event matching the given `matcher` has been scheduled to be published at the given
   * `scheduledTime`.
   *
   *
   * If the `scheduledTime` is calculated based on the "current time", use the [ ][org.axonframework.test.saga.FixtureConfiguration.currentTime] to get the time to use as "current time".
   *
   * @param scheduledTime The time at which the event should be published
   * @param matcher       A matcher defining the event expected to be published
   */
  fun expectScheduledEventMatching(scheduledTime: Instant, matcher: Matcher<in EventMessage<*>>) = self().apply {
    thenState.expectScheduledEventMatching(scheduledTime, matcher)
  }

  /**
   * Asserts that a deadline matching the given `matcher` has been scheduled at the given `scheduledTime`.
   *
   *
   * If the `scheduledTime` is calculated based on the "current time", use the [ ][FixtureConfiguration.currentTime] to get the time to use as "current time".
   *
   * @param scheduledTime The time at which the deadline should be met
   * @param matcher       The matcher defining the deadline expected
   */
  fun expectScheduledDeadlineMatching(scheduledTime: Instant, matcher: Matcher<in DeadlineMessage<*>>) = self().apply {
    thenState.expectScheduledDeadlineMatching(scheduledTime, matcher)
  }

  /**
   * Asserts that an event equal to the given ApplicationEvent has been scheduled for publication at the given
   * `scheduledTime`.
   *
   *
   * If the `scheduledTime` is calculated based on the "current time", use the [ ][org.axonframework.test.saga.FixtureConfiguration.currentTime] to get the time to use as "current time".
   *
   *
   * Note that the source attribute of the application event is ignored when comparing events. Events are compared
   * using an "equals" check on all fields in the events.
   *
   * @param scheduledTime The time at which the event should be published
   * @param event         The expected event
   */
  fun expectScheduledEvent(scheduledTime: Instant, event: Any) = self().apply {
    thenState.expectScheduledEvent(scheduledTime, event)
  }

  /**
   * Asserts that a deadline equal to the given `deadline` has been scheduled at the given `scheduledTime`.
   *
   *
   * If the `scheduledTime` is calculated based on the "current time", use the [ ][FixtureConfiguration.currentTime] to get the time to use as "current time".
   *
   *
   * Note that the source attribute of the deadline is ignored when comparing deadlines. Deadlines are compared using
   * an "equals" check on all fields in the deadlines.
   *
   * @param scheduledTime The time at which the deadline is scheduled
   * @param deadline      The expected deadline
   */
  fun expectScheduledDeadline(scheduledTime: Instant, deadline: Any) = self().apply {
    thenState.expectScheduledDeadline(scheduledTime, deadline)
  }

  /**
   * Asserts that an event of the given `eventType` has been scheduled for publication at the given
   * `scheduledTime`.
   *
   *
   * If the `scheduledTime` is calculated based on the "current time", use the [ ][org.axonframework.test.saga.FixtureConfiguration.currentTime] to get the time to use as "current time".
   *
   * @param scheduledTime The time at which the event should be published
   * @param eventType     The type of the expected event
   */
  fun expectScheduledEventOfType(scheduledTime: Instant, eventType: Class<*>) = self().apply {
    thenState.expectScheduledEventOfType(scheduledTime, eventType)
  }

  /**
   * Asserts that a deadline of the given `deadlineType` has been scheduled at the given `scheduledTime`.
   *
   * @param scheduledTime The time at which the deadline is scheduled
   * @param deadlineType  The type of the expected deadline
   */
  fun expectScheduledDeadlineOfType(scheduledTime: Instant, deadlineType: Class<*>) = self().apply {
    thenState.expectScheduledDeadlineOfType(scheduledTime, deadlineType)
  }

//  TODO: fix matcher generics
//  /**
//   * Asserts that the sagas dispatched commands as defined by the given `matcher`. Only commands as a
//   * result of the event in the "when" stage of the fixture are matched.
//   *
//   * @param matcher The matcher that describes the expected list of commands
//   * @return the FixtureExecutionResult for method chaining
//   */
//  fun expectDispatchedCommandsMatching(matcher: Matcher<out List<in CommandMessage<*>>>) = self().apply{ TODO("implement")}

  /**
   * Asserts that the sagas did not dispatch any commands. Only commands as a result of the event in the "when" stage
   * of ths fixture are recorded.
   *
   * @return the FixtureExecutionResult for method chaining
   */
  fun expectNoDispatchedCommands() = self().apply {
    thenState.expectNoDispatchedCommands()
  }

  /**
   * Assert that no events are scheduled for publication. This means that either no events were scheduled at all, all
   * schedules have been cancelled or all scheduled events have been published already.
   *
   * @return the FixtureExecutionResult for method chaining
   */
  fun expectNoScheduledEvents() = self().apply {
    thenState.expectNoScheduledEvents()
  }

  /**
   * Asserts that no deadlines are scheduled. This means that either no deadlines were scheduled at all, all schedules
   * have been cancelled or all scheduled deadlines have been met already.
   *
   * @return the FixtureExecutionResult for method chaining
   */
  fun expectNoScheduledDeadlines() = self().apply { thenState.expectNoScheduledDeadlines() }

  // TODO: fix matcher generics
//  /**
//   * Assert that the saga published events on the EventBus as defined by the given `matcher`. Only events
//   * published in the "when" stage of the tests are matched.
//   *
//   * @param matcher The matcher that defines the expected list of published events.
//   * @return the FixtureExecutionResult for method chaining
//   */
//  fun expectPublishedEventsMatching(matcher: Matcher<out List<in EventMessage<*>>>) = self().apply { TODO("implement") }

  // TODO: fix matcher generics
//  /**
//   * Asserts that deadlines match given `matcher` have been met (which have passed in time) on this saga.
//   *
//   * @param matcher The matcher that defines the expected list of deadlines
//   * @return the FixtureExecutionResult for method chaining
//   */
//  fun expectDeadlinesMetMatching(matcher: Matcher<out List<in DeadlineMessage<*>>>) = self().apply { TODO("implement") }

  /**
   * Assert that the saga published events on the EventBus in the exact sequence of the given `expected`
   * events. Events are compared comparing their type and fields using equals. Sequence number, aggregate identifier
   * (for domain events) and source (for application events) are ignored in the comparison.
   *
   * @param expected The sequence of events expected to be published by the Saga
   * @return the FixtureExecutionResult for method chaining
   */
  fun expectPublishedEvents(vararg expected: Any) = self().apply {
    thenState.expectPublishedEvents(*expected)
  }

  /**
   * Asserts that given `expected` deadlines have been met (which have passed in time). Deadlines are compared
   * comparing their type and fields using "equals".
   *
   * @param expected The sequence of deadlines expected to be met
   * @return the FixtureExecutionResult for method chaining
   */
  fun expectDeadlinesMet(vararg expected: Any) = self().apply {
    thenState.expectDeadlinesMet(*expected)
  }
}
