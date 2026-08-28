/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.DiagonalExtraction
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
@EqualsAndHashCode(includes = ['transformationId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class DiagonalExtraction implements Serializable {
    private static final long serialVersionUID = 1L

    String transformationId
    Long axis1 // Required
    Long axis2 // Required
    Long axisOffset

    // --- Relationships (In-Memory Navigation) ---
    Transformation transformation

    DiagonalExtraction() { }

    DiagonalExtraction(String transformationId) {
        this.transformationId = Objects.requireNonNull(transformationId, "DiagonalExtraction.transformationId cannot be null")
    }

    DiagonalExtraction(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('axis1')) this.axis1 = args.get('axis1') as Long
            if (args.containsKey('axis2')) this.axis2 = args.get('axis2') as Long
            if (args.containsKey('axisOffset')) this.axisOffset = args.get('axisOffset') as Long
            if (args.containsKey('transformation')) this.transformation = args.get('transformation') as Transformation
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.axis1 == null) throw new IllegalStateException("Required property missing: DiagonalExtraction.axis1")
        if (this.axis2 == null) throw new IllegalStateException("Required property missing: DiagonalExtraction.axis2")
    }

    /**
     * Gradle-style closure configurator
     */
    DiagonalExtraction configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = DiagonalExtraction) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Transformation transformation(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Transformation) Closure<?> action) {
        if (this.transformation == null) this.transformation = new Transformation()
        this.transformation.configure(action)
        this.transformation
    }
}
