/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Parameter
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
@EqualsAndHashCode(includes = ['parameterId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class Parameter implements Serializable {
    private static final long serialVersionUID = 1L

    /** parameterId */
    String parameterId

    /** parameterDefId */
    String parameterDefId

    /** parameterAlias */
    String parameterAlias

    /** sequenceNum */
    Long sequenceNum

    /** parameterUomId */
    String parameterUomId

    /** numericValue */
    BigDecimal numericValue

    /** symbolicValue */
    String symbolicValue

    /** parameterEnumId */
    String parameterEnumId

    ParameterDef parameterDef

    Parameter() {}

    Parameter(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('parameterId')) this.parameterId = args.get('parameterId')?.toString()
            if (args.containsKey('parameterDefId')) this.parameterDefId = args.get('parameterDefId')?.toString()
            if (args.containsKey('parameterAlias')) this.parameterAlias = args.get('parameterAlias')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('parameterUomId')) this.parameterUomId = args.get('parameterUomId')?.toString()
            if (args.containsKey('numericValue')) this.numericValue = args.get('numericValue') != null ? (args.get('numericValue') instanceof BigDecimal ? (BigDecimal) args.get('numericValue') : new BigDecimal(args.get('numericValue').toString())) : null
            if (args.containsKey('symbolicValue')) this.symbolicValue = args.get('symbolicValue')?.toString()
            if (args.containsKey('parameterEnumId')) this.parameterEnumId = args.get('parameterEnumId')?.toString()
        }
    }

    Parameter parameterId(String value) {
        this.parameterId = value
        return this;
    }

    Parameter parameterDefId(String value) {
        this.parameterDefId = value
        return this;
    }

    Parameter parameterAlias(String value) {
        this.parameterAlias = value
        return this;
    }

    Parameter sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    Parameter parameterUomId(String value) {
        this.parameterUomId = value
        return this;
    }

    Parameter numericValue(BigDecimal value) {
        this.numericValue = value
        return this;
    }

    Parameter symbolicValue(String value) {
        this.symbolicValue = value
        return this;
    }

    Parameter parameterEnumId(String value) {
        this.parameterEnumId = value
        return this;
    }

    Parameter parameterDef(ParameterDef item) {
        this.parameterDef = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.parameterId != null) map.put('parameterId', this.parameterId);
        if (this.parameterDefId != null) map.put('parameterDefId', this.parameterDefId);
        if (this.parameterAlias != null) map.put('parameterAlias', this.parameterAlias);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.parameterUomId != null) map.put('parameterUomId', this.parameterUomId);
        if (this.numericValue != null) map.put('numericValue', this.numericValue);
        if (this.symbolicValue != null) map.put('symbolicValue', this.symbolicValue);
        if (this.parameterEnumId != null) map.put('parameterEnumId', this.parameterEnumId);
        return map;
    }
}