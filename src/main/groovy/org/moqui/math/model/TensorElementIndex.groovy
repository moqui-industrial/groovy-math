/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorElementIndex
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
@EqualsAndHashCode(includes = ['tensorElementId', 'axisIndex'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class TensorElementIndex implements Serializable {
    private static final long serialVersionUID = 1L

    String tensorElementId
    Long axisIndex
    Long dimensionValue // Required

    // --- Relationships (In-Memory Navigation) ---
    TensorElement element

    TensorElementIndex() { }

    TensorElementIndex(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('tensorElementId')) this.tensorElementId = args.get('tensorElementId') as String
            if (args.containsKey('axisIndex')) this.axisIndex = args.get('axisIndex') as Long
            if (args.containsKey('dimensionValue')) this.dimensionValue = args.get('dimensionValue') as Long
            if (args.containsKey('element')) this.element = args.get('element') as TensorElement
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.dimensionValue == null) throw new IllegalStateException("Required property missing: TensorElementIndex.dimensionValue")
    }

    /**
     * Gradle-style closure configurator
     */
    TensorElementIndex configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TensorElementIndex) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    TensorElement element(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TensorElement) Closure<?> action) {
        if (this.element == null) this.element = new TensorElement()
        this.element.configure(action)
        this.element
    }
}
