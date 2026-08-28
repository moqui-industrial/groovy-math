/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelEvent
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['mathModelEventId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MathModelEvent implements Serializable {
    private static final long serialVersionUID = 1L

    String mathModelEventId
    String mathModelRunId // Required
    String eventTypeEnumId
    java.sql.Timestamp eventTimestamp // Required
    String eventName
    String eventPayload
    Long sequenceNum

    // --- Relationships (In-Memory Navigation) ---
    MathModelRun run
    Object type

    MathModelEvent() { }

    MathModelEvent(String mathModelEventId) {
        this.mathModelEventId = Objects.requireNonNull(mathModelEventId, "MathModelEvent.mathModelEventId cannot be null")
    }

    MathModelEvent(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('mathModelEventId')) this.mathModelEventId = args.get('mathModelEventId') as String
            if (args.containsKey('mathModelRunId')) this.mathModelRunId = args.get('mathModelRunId') as String
            if (args.containsKey('eventTypeEnumId')) this.eventTypeEnumId = args.get('eventTypeEnumId') as String
            if (args.containsKey('eventTimestamp')) this.eventTimestamp = args.get('eventTimestamp') as java.sql.Timestamp
            if (args.containsKey('eventName')) this.eventName = args.get('eventName') as String
            if (args.containsKey('eventPayload')) this.eventPayload = args.get('eventPayload') as String
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') as Long
            if (args.containsKey('run')) this.run = args.get('run') as MathModelRun
            if (args.containsKey('type')) this.type = args.get('type') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.mathModelRunId == null) throw new IllegalStateException("Required property missing: MathModelEvent.mathModelRunId")
        if (this.eventTimestamp == null) throw new IllegalStateException("Required property missing: MathModelEvent.eventTimestamp")
    }

    /**
     * Gradle-style closure configurator
     */
    MathModelEvent configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelEvent) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MathModelRun run(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelRun) Closure<?> action) {
        if (this.run == null) this.run = new MathModelRun()
        this.run.configure(action)
        this.run
    }
}
