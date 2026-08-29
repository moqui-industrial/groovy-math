/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorAxis
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
@EqualsAndHashCode(includes = ['tensorId', 'axisIndex'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class TensorAxis implements Serializable {
    private static final long serialVersionUID = 1L

    /** tensorId */
    String tensorId

    /** axisIndex */
    Long axisIndex

    /** axisSize */
    Long axisSize

    /** axisStride */
    Long axisStride

    /** axisTypeEnumId */
    String axisTypeEnumId

    /** purposeEnumId */
    String purposeEnumId

    /** refEntityName */
    String refEntityName

    /** refPkPrimaryValue */
    String refPkPrimaryValue

    /** refPkSecondaryValue */
    String refPkSecondaryValue

    /** label */
    String label

    /** domainMin */
    BigDecimal domainMin

    /** domainMax */
    BigDecimal domainMax

    /** uomId */
    String uomId

    Tensor tensor

    TensorAxis() {}

    TensorAxis(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('tensorId')) this.tensorId = args.get('tensorId')?.toString()
            if (args.containsKey('axisIndex')) this.axisIndex = args.get('axisIndex') != null ? ((Number) args.get('axisIndex')).longValue() : null
            if (args.containsKey('axisSize')) this.axisSize = args.get('axisSize') != null ? ((Number) args.get('axisSize')).longValue() : null
            if (args.containsKey('axisStride')) this.axisStride = args.get('axisStride') != null ? ((Number) args.get('axisStride')).longValue() : null
            if (args.containsKey('axisTypeEnumId')) this.axisTypeEnumId = args.get('axisTypeEnumId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('refEntityName')) this.refEntityName = args.get('refEntityName')?.toString()
            if (args.containsKey('refPkPrimaryValue')) this.refPkPrimaryValue = args.get('refPkPrimaryValue')?.toString()
            if (args.containsKey('refPkSecondaryValue')) this.refPkSecondaryValue = args.get('refPkSecondaryValue')?.toString()
            if (args.containsKey('label')) this.label = args.get('label')?.toString()
            if (args.containsKey('domainMin')) this.domainMin = args.get('domainMin') != null ? (args.get('domainMin') instanceof BigDecimal ? (BigDecimal) args.get('domainMin') : new BigDecimal(args.get('domainMin').toString())) : null
            if (args.containsKey('domainMax')) this.domainMax = args.get('domainMax') != null ? (args.get('domainMax') instanceof BigDecimal ? (BigDecimal) args.get('domainMax') : new BigDecimal(args.get('domainMax').toString())) : null
            if (args.containsKey('uomId')) this.uomId = args.get('uomId')?.toString()
        }
    }

    TensorAxis tensorId(String value) {
        this.tensorId = value
        return this;
    }

    TensorAxis axisIndex(Long value) {
        this.axisIndex = value
        return this;
    }

    TensorAxis axisSize(Long value) {
        this.axisSize = value
        return this;
    }

    TensorAxis axisStride(Long value) {
        this.axisStride = value
        return this;
    }

    TensorAxis axisTypeEnumId(String value) {
        this.axisTypeEnumId = value
        return this;
    }

    TensorAxis purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    TensorAxis refEntityName(String value) {
        this.refEntityName = value
        return this;
    }

    TensorAxis refPkPrimaryValue(String value) {
        this.refPkPrimaryValue = value
        return this;
    }

    TensorAxis refPkSecondaryValue(String value) {
        this.refPkSecondaryValue = value
        return this;
    }

    TensorAxis label(String value) {
        this.label = value
        return this;
    }

    TensorAxis domainMin(BigDecimal value) {
        this.domainMin = value
        return this;
    }

    TensorAxis domainMax(BigDecimal value) {
        this.domainMax = value
        return this;
    }

    TensorAxis uomId(String value) {
        this.uomId = value
        return this;
    }

    TensorAxis tensor(Tensor item) {
        this.tensor = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.tensorId != null) map.put('tensorId', this.tensorId);
        if (this.axisIndex != null) map.put('axisIndex', this.axisIndex);
        if (this.axisSize != null) map.put('axisSize', this.axisSize);
        if (this.axisStride != null) map.put('axisStride', this.axisStride);
        if (this.axisTypeEnumId != null) map.put('axisTypeEnumId', this.axisTypeEnumId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.refEntityName != null) map.put('refEntityName', this.refEntityName);
        if (this.refPkPrimaryValue != null) map.put('refPkPrimaryValue', this.refPkPrimaryValue);
        if (this.refPkSecondaryValue != null) map.put('refPkSecondaryValue', this.refPkSecondaryValue);
        if (this.label != null) map.put('label', this.label);
        if (this.domainMin != null) map.put('domainMin', this.domainMin);
        if (this.domainMax != null) map.put('domainMax', this.domainMax);
        if (this.uomId != null) map.put('uomId', this.uomId);
        return map;
    }
}