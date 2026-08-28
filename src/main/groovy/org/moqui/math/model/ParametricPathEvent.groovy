/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParametricPathEvent
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
@EqualsAndHashCode(includes = ['parametricPathEventId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ParametricPathEvent implements Serializable {
    private static final long serialVersionUID = 1L

    String parametricPathEventId
    String approximatedFunctionId // Required
    String eventTypeEnumId
    java.sql.Timestamp fromDate
    java.sql.Timestamp thruDate
    String eventName
    String eventPayload
    Long sequenceNum
    BigDecimal triggerPoint
    String isTriggerable
    String userPermissionId

    // --- Relationships (In-Memory Navigation) ---
    ParametricPath parametricPath
    Object type

    ParametricPathEvent() { }

    ParametricPathEvent(String parametricPathEventId) {
        this.parametricPathEventId = Objects.requireNonNull(parametricPathEventId, "ParametricPathEvent.parametricPathEventId cannot be null")
    }

    ParametricPathEvent(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('parametricPathEventId')) this.parametricPathEventId = args.get('parametricPathEventId') as String
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('eventTypeEnumId')) this.eventTypeEnumId = args.get('eventTypeEnumId') as String
            if (args.containsKey('fromDate')) this.fromDate = args.get('fromDate') as java.sql.Timestamp
            if (args.containsKey('thruDate')) this.thruDate = args.get('thruDate') as java.sql.Timestamp
            if (args.containsKey('eventName')) this.eventName = args.get('eventName') as String
            if (args.containsKey('eventPayload')) this.eventPayload = args.get('eventPayload') as String
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') as Long
            if (args.containsKey('triggerPoint')) this.triggerPoint = args.get('triggerPoint') as BigDecimal
            if (args.containsKey('isTriggerable')) this.isTriggerable = args.get('isTriggerable') as String
            if (args.containsKey('userPermissionId')) this.userPermissionId = args.get('userPermissionId') as String
            if (args.containsKey('parametricPath')) this.parametricPath = args.get('parametricPath') as ParametricPath
            if (args.containsKey('type')) this.type = args.get('type') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.approximatedFunctionId == null) throw new IllegalStateException("Required property missing: ParametricPathEvent.approximatedFunctionId")
    }

    /**
     * Gradle-style closure configurator
     */
    ParametricPathEvent configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPathEvent) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    ParametricPath parametricPath(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPath) Closure<?> action) {
        if (this.parametricPath == null) this.parametricPath = new ParametricPath()
        this.parametricPath.configure(action)
        this.parametricPath
    }
}
