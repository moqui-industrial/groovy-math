/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.VectorComponent
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['vectorComponentId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class VectorComponent implements Serializable {
    private static final long serialVersionUID = 1L

    /** vectorComponentId */
    String vectorComponentId

    /** vectorId */
    String vectorId

    /** dimensionIndex */
    Long dimensionIndex

    /** parentComponentId */
    String parentComponentId

    /** componentTypeEnumId */
    String componentTypeEnumId

    /** projection */
    BigDecimal projection

    /** rejection */
    BigDecimal rejection

    /** realValue */
    BigDecimal realValue

    /** imaginaryValue */
    BigDecimal imaginaryValue

    /** symbolicValue */
    String symbolicValue

    Vector vector

    VectorComponent parent

    VectorComponent() {}

    VectorComponent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('vectorComponentId')) this.vectorComponentId = args.get('vectorComponentId')?.toString()
            if (args.containsKey('vectorId')) this.vectorId = args.get('vectorId')?.toString()
            if (args.containsKey('dimensionIndex')) this.dimensionIndex = args.get('dimensionIndex') != null ? ((Number) args.get('dimensionIndex')).longValue() : null
            if (args.containsKey('parentComponentId')) this.parentComponentId = args.get('parentComponentId')?.toString()
            if (args.containsKey('componentTypeEnumId')) this.componentTypeEnumId = args.get('componentTypeEnumId')?.toString()
            if (args.containsKey('projection')) this.projection = args.get('projection') != null ? (args.get('projection') instanceof BigDecimal ? (BigDecimal) args.get('projection') : new BigDecimal(args.get('projection').toString())) : null
            if (args.containsKey('rejection')) this.rejection = args.get('rejection') != null ? (args.get('rejection') instanceof BigDecimal ? (BigDecimal) args.get('rejection') : new BigDecimal(args.get('rejection').toString())) : null
            if (args.containsKey('realValue')) this.realValue = args.get('realValue') != null ? (args.get('realValue') instanceof BigDecimal ? (BigDecimal) args.get('realValue') : new BigDecimal(args.get('realValue').toString())) : null
            if (args.containsKey('imaginaryValue')) this.imaginaryValue = args.get('imaginaryValue') != null ? (args.get('imaginaryValue') instanceof BigDecimal ? (BigDecimal) args.get('imaginaryValue') : new BigDecimal(args.get('imaginaryValue').toString())) : null
            if (args.containsKey('symbolicValue')) this.symbolicValue = args.get('symbolicValue')?.toString()
        }
    }

    VectorComponent vectorComponentId(String value) {
        this.vectorComponentId = value
        return this;
    }

    VectorComponent vectorId(String value) {
        this.vectorId = value
        return this;
    }

    VectorComponent dimensionIndex(Long value) {
        this.dimensionIndex = value
        return this;
    }

    VectorComponent parentComponentId(String value) {
        this.parentComponentId = value
        return this;
    }

    VectorComponent componentTypeEnumId(String value) {
        this.componentTypeEnumId = value
        return this;
    }

    VectorComponent projection(BigDecimal value) {
        this.projection = value
        return this;
    }

    VectorComponent rejection(BigDecimal value) {
        this.rejection = value
        return this;
    }

    VectorComponent realValue(BigDecimal value) {
        this.realValue = value
        return this;
    }

    VectorComponent imaginaryValue(BigDecimal value) {
        this.imaginaryValue = value
        return this;
    }

    VectorComponent symbolicValue(String value) {
        this.symbolicValue = value
        return this;
    }

    VectorComponent vector(Vector item) {
        this.vector = item;
        return this;
    }

    VectorComponent parent(VectorComponent item) {
        this.parent = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.vectorComponentId != null) map.put('vectorComponentId', this.vectorComponentId);
        if (this.vectorId != null) map.put('vectorId', this.vectorId);
        if (this.dimensionIndex != null) map.put('dimensionIndex', this.dimensionIndex);
        if (this.parentComponentId != null) map.put('parentComponentId', this.parentComponentId);
        if (this.componentTypeEnumId != null) map.put('componentTypeEnumId', this.componentTypeEnumId);
        if (this.projection != null) map.put('projection', this.projection);
        if (this.rejection != null) map.put('rejection', this.rejection);
        if (this.realValue != null) map.put('realValue', this.realValue);
        if (this.imaginaryValue != null) map.put('imaginaryValue', this.imaginaryValue);
        if (this.symbolicValue != null) map.put('symbolicValue', this.symbolicValue);
        return map;
    }
}