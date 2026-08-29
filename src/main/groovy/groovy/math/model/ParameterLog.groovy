/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParameterLog
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
@EqualsAndHashCode(includes = ['parameterLogId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class ParameterLog implements Serializable {
    private static final long serialVersionUID = 1L

    /** parameterLogId */
    String parameterLogId

    /** parameterId */
    String parameterId

    /** sequenceNum */
    Long sequenceNum

    /** observedDate */
    java.sql.Timestamp observedDate

    /** numericValue */
    BigDecimal numericValue

    /** symbolicValue */
    String symbolicValue

    /** parameterEnumId */
    String parameterEnumId

    Parameter parameter

    ParameterLog() {}

    ParameterLog(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('parameterLogId')) this.parameterLogId = args.get('parameterLogId')?.toString()
            if (args.containsKey('parameterId')) this.parameterId = args.get('parameterId')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('observedDate')) this.observedDate = (java.sql.Timestamp) args.get('observedDate')
            if (args.containsKey('numericValue')) this.numericValue = args.get('numericValue') != null ? (args.get('numericValue') instanceof BigDecimal ? (BigDecimal) args.get('numericValue') : new BigDecimal(args.get('numericValue').toString())) : null
            if (args.containsKey('symbolicValue')) this.symbolicValue = args.get('symbolicValue')?.toString()
            if (args.containsKey('parameterEnumId')) this.parameterEnumId = args.get('parameterEnumId')?.toString()
        }
    }

    ParameterLog parameterLogId(String value) {
        this.parameterLogId = value
        return this;
    }

    ParameterLog parameterId(String value) {
        this.parameterId = value
        return this;
    }

    ParameterLog sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    ParameterLog observedDate(java.sql.Timestamp value) {
        this.observedDate = value
        return this;
    }

    ParameterLog numericValue(BigDecimal value) {
        this.numericValue = value
        return this;
    }

    ParameterLog symbolicValue(String value) {
        this.symbolicValue = value
        return this;
    }

    ParameterLog parameterEnumId(String value) {
        this.parameterEnumId = value
        return this;
    }

    ParameterLog parameter(Parameter item) {
        this.parameter = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.parameterLogId != null) map.put('parameterLogId', this.parameterLogId);
        if (this.parameterId != null) map.put('parameterId', this.parameterId);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.observedDate != null) map.put('observedDate', this.observedDate);
        if (this.numericValue != null) map.put('numericValue', this.numericValue);
        if (this.symbolicValue != null) map.put('symbolicValue', this.symbolicValue);
        if (this.parameterEnumId != null) map.put('parameterEnumId', this.parameterEnumId);
        return map;
    }
}