/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MathModelEvent
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MathModelEvent

@CompileStatic
class MathModelEvent_ {
    public static final String ENTITY_NAME = 'MathModelEvent'
    public static final String FULL_NAME = 'moqui.math.MathModelEvent'

    public static final Attribute<MathModelEvent, String> mathModelEventId = new Attribute<>('mathModelEventId', MathModelEvent.class, String.class, true, true)
    public static final Attribute<MathModelEvent, String> mathModelRunId = new Attribute<>('mathModelRunId', MathModelEvent.class, String.class, false, true)
    public static final Attribute<MathModelEvent, String> eventTypeEnumId = new Attribute<>('eventTypeEnumId', MathModelEvent.class, String.class, false, false)
    public static final Attribute<MathModelEvent, java.sql.Timestamp> eventTimestamp = new Attribute<>('eventTimestamp', MathModelEvent.class, java.sql.Timestamp.class, false, true)
    public static final Attribute<MathModelEvent, String> eventName = new Attribute<>('eventName', MathModelEvent.class, String.class, false, false)
    public static final Attribute<MathModelEvent, String> eventPayload = new Attribute<>('eventPayload', MathModelEvent.class, String.class, false, false)
    public static final Attribute<MathModelEvent, Long> sequenceNum = new Attribute<>('sequenceNum', MathModelEvent.class, Long.class, false, false)
}
