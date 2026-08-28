/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorElement
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
@EqualsAndHashCode(includes = ['tensorElementId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class TensorElement implements Serializable {
    private static final long serialVersionUID = 1L

    String tensorElementId
    String tensorId // Required
    String parentElementId
    String elementTypeEnumId
    Long linearIndex // Required
    String indicesJson
    BigDecimal realValue
    BigDecimal imaginaryValue
    String symbolicValue

    // --- Relationships (In-Memory Navigation) ---
    Tensor tensor
    TensorElement parent
    Object type

    TensorElement() { }

    TensorElement(String tensorElementId) {
        this.tensorElementId = Objects.requireNonNull(tensorElementId, "TensorElement.tensorElementId cannot be null")
    }

    TensorElement(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('tensorElementId')) this.tensorElementId = args.get('tensorElementId') as String
            if (args.containsKey('tensorId')) this.tensorId = args.get('tensorId') as String
            if (args.containsKey('parentElementId')) this.parentElementId = args.get('parentElementId') as String
            if (args.containsKey('elementTypeEnumId')) this.elementTypeEnumId = args.get('elementTypeEnumId') as String
            if (args.containsKey('linearIndex')) this.linearIndex = args.get('linearIndex') as Long
            if (args.containsKey('indicesJson')) this.indicesJson = args.get('indicesJson') as String
            if (args.containsKey('realValue')) this.realValue = args.get('realValue') as BigDecimal
            if (args.containsKey('imaginaryValue')) this.imaginaryValue = args.get('imaginaryValue') as BigDecimal
            if (args.containsKey('symbolicValue')) this.symbolicValue = args.get('symbolicValue') as String
            if (args.containsKey('tensor')) this.tensor = args.get('tensor') as Tensor
            if (args.containsKey('parent')) this.parent = args.get('parent') as TensorElement
            if (args.containsKey('type')) this.type = args.get('type') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.tensorId == null) throw new IllegalStateException("Required property missing: TensorElement.tensorId")
        if (this.linearIndex == null) throw new IllegalStateException("Required property missing: TensorElement.linearIndex")
    }

    /**
     * Gradle-style closure configurator
     */
    TensorElement configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TensorElement) Closure<?> action) {
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

    TensorElement parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TensorElement) Closure<?> action) {
        if (this.parent == null) this.parent = new TensorElement()
        this.parent.configure(action)
        this.parent
    }
}
