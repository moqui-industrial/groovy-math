/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.VectorComponent
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
@EqualsAndHashCode(includes = ['vectorComponentId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class VectorComponent implements Serializable {
    private static final long serialVersionUID = 1L

    String vectorComponentId
    String vectorId // Required
    Long dimensionIndex // Required
    String parentComponentId
    String componentTypeEnumId
    BigDecimal projection
    BigDecimal rejection
    BigDecimal realValue
    BigDecimal imaginaryValue
    String symbolicValue

    // --- Relationships (In-Memory Navigation) ---
    Vector vector
    VectorComponent parent
    Object type

    VectorComponent() { }

    VectorComponent(String vectorComponentId) {
        this.vectorComponentId = Objects.requireNonNull(vectorComponentId, "VectorComponent.vectorComponentId cannot be null")
    }

    VectorComponent(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('vectorComponentId')) this.vectorComponentId = args.get('vectorComponentId') as String
            if (args.containsKey('vectorId')) this.vectorId = args.get('vectorId') as String
            if (args.containsKey('dimensionIndex')) this.dimensionIndex = args.get('dimensionIndex') as Long
            if (args.containsKey('parentComponentId')) this.parentComponentId = args.get('parentComponentId') as String
            if (args.containsKey('componentTypeEnumId')) this.componentTypeEnumId = args.get('componentTypeEnumId') as String
            if (args.containsKey('projection')) this.projection = args.get('projection') as BigDecimal
            if (args.containsKey('rejection')) this.rejection = args.get('rejection') as BigDecimal
            if (args.containsKey('realValue')) this.realValue = args.get('realValue') as BigDecimal
            if (args.containsKey('imaginaryValue')) this.imaginaryValue = args.get('imaginaryValue') as BigDecimal
            if (args.containsKey('symbolicValue')) this.symbolicValue = args.get('symbolicValue') as String
            if (args.containsKey('vector')) this.vector = args.get('vector') as Vector
            if (args.containsKey('parent')) this.parent = args.get('parent') as VectorComponent
            if (args.containsKey('type')) this.type = args.get('type') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.vectorId == null) throw new IllegalStateException("Required property missing: VectorComponent.vectorId")
        if (this.dimensionIndex == null) throw new IllegalStateException("Required property missing: VectorComponent.dimensionIndex")
    }

    /**
     * Gradle-style closure configurator
     */
    VectorComponent configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = VectorComponent) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Vector vector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.vector == null) this.vector = new Vector()
        this.vector.configure(action)
        this.vector
    }

    VectorComponent parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = VectorComponent) Closure<?> action) {
        if (this.parent == null) this.parent = new VectorComponent()
        this.parent.configure(action)
        this.parent
    }
}
