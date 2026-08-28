/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorAxis
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['tensorId', 'axisIndex'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class TensorAxis implements Serializable {
    private static final long serialVersionUID = 1L

    String tensorId
    Long axisIndex
    Long axisSize // Required
    Long axisStride // Required
    String axisTypeEnumId // Required
    String purposeEnumId // Required
    String refEntityName
    String refPkPrimaryValue
    String refPkSecondaryValue
    String label
    BigDecimal domainMin
    BigDecimal domainMax
    String uomId

    // --- Relationships (In-Memory Navigation) ---
    Tensor tensor
    Object type
    Object purpose
    Object uom

    TensorAxis() { }

    TensorAxis(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('tensorId')) this.tensorId = args.get('tensorId') as String
            if (args.containsKey('axisIndex')) this.axisIndex = args.get('axisIndex') as Long
            if (args.containsKey('axisSize')) this.axisSize = args.get('axisSize') as Long
            if (args.containsKey('axisStride')) this.axisStride = args.get('axisStride') as Long
            if (args.containsKey('axisTypeEnumId')) this.axisTypeEnumId = args.get('axisTypeEnumId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('refEntityName')) this.refEntityName = args.get('refEntityName') as String
            if (args.containsKey('refPkPrimaryValue')) this.refPkPrimaryValue = args.get('refPkPrimaryValue') as String
            if (args.containsKey('refPkSecondaryValue')) this.refPkSecondaryValue = args.get('refPkSecondaryValue') as String
            if (args.containsKey('label')) this.label = args.get('label') as String
            if (args.containsKey('domainMin')) this.domainMin = args.get('domainMin') as BigDecimal
            if (args.containsKey('domainMax')) this.domainMax = args.get('domainMax') as BigDecimal
            if (args.containsKey('uomId')) this.uomId = args.get('uomId') as String
            if (args.containsKey('tensor')) this.tensor = args.get('tensor') as Tensor
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('uom')) this.uom = args.get('uom') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.axisSize == null) throw new IllegalStateException("Required property missing: TensorAxis.axisSize")
        if (this.axisStride == null) throw new IllegalStateException("Required property missing: TensorAxis.axisStride")
        if (this.axisTypeEnumId == null) throw new IllegalStateException("Required property missing: TensorAxis.axisTypeEnumId")
        if (this.purposeEnumId == null) throw new IllegalStateException("Required property missing: TensorAxis.purposeEnumId")
    }

    /**
     * Gradle-style closure configurator
     */
    TensorAxis configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TensorAxis) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Tensor tensor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Tensor) Closure<?> action) {
        if (this.tensor == null) this.tensor = new Tensor()
        this.tensor.configure(action)
        this.tensor
    }
}
