/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelEvent
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
@EqualsAndHashCode(includes = ['mathModelEventId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class MathModelEvent implements Serializable {
    private static final long serialVersionUID = 1L

    /** mathModelEventId */
    String mathModelEventId

    /** mathModelRunId */
    String mathModelRunId

    /** eventTypeEnumId */
    String eventTypeEnumId

    /** eventTimestamp */
    java.sql.Timestamp eventTimestamp

    /** eventName */
    String eventName

    /** eventPayload */
    String eventPayload

    /** sequenceNum */
    Long sequenceNum

    MathModelRun run

    MathModelEvent() {}

    MathModelEvent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('mathModelEventId')) this.mathModelEventId = args.get('mathModelEventId')?.toString()
            if (args.containsKey('mathModelRunId')) this.mathModelRunId = args.get('mathModelRunId')?.toString()
            if (args.containsKey('eventTypeEnumId')) this.eventTypeEnumId = args.get('eventTypeEnumId')?.toString()
            if (args.containsKey('eventTimestamp')) this.eventTimestamp = (java.sql.Timestamp) args.get('eventTimestamp')
            if (args.containsKey('eventName')) this.eventName = args.get('eventName')?.toString()
            if (args.containsKey('eventPayload')) this.eventPayload = args.get('eventPayload')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
        }
    }

    MathModelEvent mathModelEventId(String value) {
        this.mathModelEventId = value
        return this;
    }

    MathModelEvent mathModelRunId(String value) {
        this.mathModelRunId = value
        return this;
    }

    MathModelEvent eventTypeEnumId(String value) {
        this.eventTypeEnumId = value
        return this;
    }

    MathModelEvent eventTimestamp(java.sql.Timestamp value) {
        this.eventTimestamp = value
        return this;
    }

    MathModelEvent eventName(String value) {
        this.eventName = value
        return this;
    }

    MathModelEvent eventPayload(String value) {
        this.eventPayload = value
        return this;
    }

    MathModelEvent sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    MathModelEvent run(MathModelRun item) {
        this.run = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.mathModelEventId != null) map.put('mathModelEventId', this.mathModelEventId);
        if (this.mathModelRunId != null) map.put('mathModelRunId', this.mathModelRunId);
        if (this.eventTypeEnumId != null) map.put('eventTypeEnumId', this.eventTypeEnumId);
        if (this.eventTimestamp != null) map.put('eventTimestamp', this.eventTimestamp);
        if (this.eventName != null) map.put('eventName', this.eventName);
        if (this.eventPayload != null) map.put('eventPayload', this.eventPayload);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        return map;
    }
}