/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorElement
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
@EqualsAndHashCode(includes = ['tensorElementId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class TensorElement implements Serializable {
    private static final long serialVersionUID = 1L

    /** tensorElementId */
    String tensorElementId

    /** tensorId */
    String tensorId

    /** parentElementId */
    String parentElementId

    /** elementTypeEnumId */
    String elementTypeEnumId

    /** linearIndex */
    Long linearIndex

    /** indicesJson */
    String indicesJson

    /** realValue */
    BigDecimal realValue

    /** imaginaryValue */
    BigDecimal imaginaryValue

    /** symbolicValue */
    String symbolicValue

    Tensor tensor

    TensorElement parent

    TensorElement() {}

    TensorElement(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('tensorElementId')) this.tensorElementId = args.get('tensorElementId')?.toString()
            if (args.containsKey('tensorId')) this.tensorId = args.get('tensorId')?.toString()
            if (args.containsKey('parentElementId')) this.parentElementId = args.get('parentElementId')?.toString()
            if (args.containsKey('elementTypeEnumId')) this.elementTypeEnumId = args.get('elementTypeEnumId')?.toString()
            if (args.containsKey('linearIndex')) this.linearIndex = args.get('linearIndex') != null ? ((Number) args.get('linearIndex')).longValue() : null
            if (args.containsKey('indicesJson')) this.indicesJson = args.get('indicesJson')?.toString()
            if (args.containsKey('realValue')) this.realValue = args.get('realValue') != null ? (args.get('realValue') instanceof BigDecimal ? (BigDecimal) args.get('realValue') : new BigDecimal(args.get('realValue').toString())) : null
            if (args.containsKey('imaginaryValue')) this.imaginaryValue = args.get('imaginaryValue') != null ? (args.get('imaginaryValue') instanceof BigDecimal ? (BigDecimal) args.get('imaginaryValue') : new BigDecimal(args.get('imaginaryValue').toString())) : null
            if (args.containsKey('symbolicValue')) this.symbolicValue = args.get('symbolicValue')?.toString()
        }
    }

    TensorElement tensorElementId(String value) {
        this.tensorElementId = value
        return this;
    }

    TensorElement tensorId(String value) {
        this.tensorId = value
        return this;
    }

    TensorElement parentElementId(String value) {
        this.parentElementId = value
        return this;
    }

    TensorElement elementTypeEnumId(String value) {
        this.elementTypeEnumId = value
        return this;
    }

    TensorElement linearIndex(Long value) {
        this.linearIndex = value
        return this;
    }

    TensorElement indicesJson(String value) {
        this.indicesJson = value
        return this;
    }

    TensorElement realValue(BigDecimal value) {
        this.realValue = value
        return this;
    }

    TensorElement imaginaryValue(BigDecimal value) {
        this.imaginaryValue = value
        return this;
    }

    TensorElement symbolicValue(String value) {
        this.symbolicValue = value
        return this;
    }

    TensorElement tensor(Tensor item) {
        this.tensor = item;
        return this;
    }

    TensorElement parent(TensorElement item) {
        this.parent = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.tensorElementId != null) map.put('tensorElementId', this.tensorElementId);
        if (this.tensorId != null) map.put('tensorId', this.tensorId);
        if (this.parentElementId != null) map.put('parentElementId', this.parentElementId);
        if (this.elementTypeEnumId != null) map.put('elementTypeEnumId', this.elementTypeEnumId);
        if (this.linearIndex != null) map.put('linearIndex', this.linearIndex);
        if (this.indicesJson != null) map.put('indicesJson', this.indicesJson);
        if (this.realValue != null) map.put('realValue', this.realValue);
        if (this.imaginaryValue != null) map.put('imaginaryValue', this.imaginaryValue);
        if (this.symbolicValue != null) map.put('symbolicValue', this.symbolicValue);
        return map;
    }
}