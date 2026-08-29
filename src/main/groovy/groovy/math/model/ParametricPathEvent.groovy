/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParametricPathEvent
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['parametricPathEventId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class ParametricPathEvent implements Serializable {
    private static final long serialVersionUID = 1L

    /** parametricPathEventId */
    String parametricPathEventId

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** eventTypeEnumId */
    String eventTypeEnumId

    /** fromDate */
    java.sql.Timestamp fromDate

    /** thruDate */
    java.sql.Timestamp thruDate

    /** eventName */
    String eventName

    /** eventPayload */
    String eventPayload

    /** sequenceNum */
    Long sequenceNum

    /** triggerPoint */
    BigDecimal triggerPoint

    /** isTriggerable */
    String isTriggerable

    /** userPermissionId */
    String userPermissionId

    ParametricPath parametricPath

    ParametricPathEvent() {}

    ParametricPathEvent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('parametricPathEventId')) this.parametricPathEventId = args.get('parametricPathEventId')?.toString()
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('eventTypeEnumId')) this.eventTypeEnumId = args.get('eventTypeEnumId')?.toString()
            if (args.containsKey('fromDate')) this.fromDate = (java.sql.Timestamp) args.get('fromDate')
            if (args.containsKey('thruDate')) this.thruDate = (java.sql.Timestamp) args.get('thruDate')
            if (args.containsKey('eventName')) this.eventName = args.get('eventName')?.toString()
            if (args.containsKey('eventPayload')) this.eventPayload = args.get('eventPayload')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('triggerPoint')) this.triggerPoint = args.get('triggerPoint') != null ? (args.get('triggerPoint') instanceof BigDecimal ? (BigDecimal) args.get('triggerPoint') : new BigDecimal(args.get('triggerPoint').toString())) : null
            if (args.containsKey('isTriggerable')) this.isTriggerable = args.get('isTriggerable')?.toString()
            if (args.containsKey('userPermissionId')) this.userPermissionId = args.get('userPermissionId')?.toString()
        }
    }

    ParametricPathEvent parametricPathEventId(String value) {
        this.parametricPathEventId = value
        return this;
    }

    ParametricPathEvent approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    ParametricPathEvent eventTypeEnumId(String value) {
        this.eventTypeEnumId = value
        return this;
    }

    ParametricPathEvent fromDate(java.sql.Timestamp value) {
        this.fromDate = value
        return this;
    }

    ParametricPathEvent thruDate(java.sql.Timestamp value) {
        this.thruDate = value
        return this;
    }

    ParametricPathEvent eventName(String value) {
        this.eventName = value
        return this;
    }

    ParametricPathEvent eventPayload(String value) {
        this.eventPayload = value
        return this;
    }

    ParametricPathEvent sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    ParametricPathEvent triggerPoint(BigDecimal value) {
        this.triggerPoint = value
        return this;
    }

    ParametricPathEvent isTriggerable(String value) {
        this.isTriggerable = value
        return this;
    }

    ParametricPathEvent userPermissionId(String value) {
        this.userPermissionId = value
        return this;
    }

    ParametricPathEvent parametricPath(ParametricPath item) {
        this.parametricPath = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.parametricPathEventId != null) map.put('parametricPathEventId', this.parametricPathEventId);
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.eventTypeEnumId != null) map.put('eventTypeEnumId', this.eventTypeEnumId);
        if (this.fromDate != null) map.put('fromDate', this.fromDate);
        if (this.thruDate != null) map.put('thruDate', this.thruDate);
        if (this.eventName != null) map.put('eventName', this.eventName);
        if (this.eventPayload != null) map.put('eventPayload', this.eventPayload);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.triggerPoint != null) map.put('triggerPoint', this.triggerPoint);
        if (this.isTriggerable != null) map.put('isTriggerable', this.isTriggerable);
        if (this.userPermissionId != null) map.put('userPermissionId', this.userPermissionId);
        return map;
    }
}