/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ParametricPathEvent
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.ParametricPathEvent

@CompileStatic
class ParametricPathEvent_ {
    public static final String ENTITY_NAME = 'ParametricPathEvent'
    public static final String FULL_NAME = 'moqui.math.ParametricPathEvent'

    public static final Attribute<ParametricPathEvent, String> parametricPathEventId = new Attribute<>('parametricPathEventId', ParametricPathEvent.class, String.class, true, true)
    public static final Attribute<ParametricPathEvent, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', ParametricPathEvent.class, String.class, false, true)
    public static final Attribute<ParametricPathEvent, String> eventTypeEnumId = new Attribute<>('eventTypeEnumId', ParametricPathEvent.class, String.class, false, false)
    public static final Attribute<ParametricPathEvent, java.sql.Timestamp> fromDate = new Attribute<>('fromDate', ParametricPathEvent.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<ParametricPathEvent, java.sql.Timestamp> thruDate = new Attribute<>('thruDate', ParametricPathEvent.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<ParametricPathEvent, String> eventName = new Attribute<>('eventName', ParametricPathEvent.class, String.class, false, false)
    public static final Attribute<ParametricPathEvent, String> eventPayload = new Attribute<>('eventPayload', ParametricPathEvent.class, String.class, false, false)
    public static final Attribute<ParametricPathEvent, Long> sequenceNum = new Attribute<>('sequenceNum', ParametricPathEvent.class, Long.class, false, false)
    public static final Attribute<ParametricPathEvent, BigDecimal> triggerPoint = new Attribute<>('triggerPoint', ParametricPathEvent.class, BigDecimal.class, false, false)
    public static final Attribute<ParametricPathEvent, String> isTriggerable = new Attribute<>('isTriggerable', ParametricPathEvent.class, String.class, false, false)
    public static final Attribute<ParametricPathEvent, String> userPermissionId = new Attribute<>('userPermissionId', ParametricPathEvent.class, String.class, false, false)
}
