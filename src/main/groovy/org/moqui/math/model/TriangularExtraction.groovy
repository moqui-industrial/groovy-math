/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TriangularExtraction
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
class TriangularExtraction implements Serializable {
    private static final long serialVersionUID = 1L

    String transformationId
    String extractionTypeEnumId // Required
    Long extractionOffset

    // --- Relationships (In-Memory Navigation) ---
    Transformation transformation
    Object type

    TriangularExtraction() { }

    TriangularExtraction(String transformationId) {
        this.transformationId = Objects.requireNonNull(transformationId, "TriangularExtraction.transformationId cannot be null")
    }

    TriangularExtraction(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('extractionTypeEnumId')) this.extractionTypeEnumId = args.get('extractionTypeEnumId') as String
            if (args.containsKey('extractionOffset')) this.extractionOffset = args.get('extractionOffset') as Long
            if (args.containsKey('transformation')) this.transformation = args.get('transformation') as Transformation
            if (args.containsKey('type')) this.type = args.get('type') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.extractionTypeEnumId == null) throw new IllegalStateException("Required property missing: TriangularExtraction.extractionTypeEnumId")
    }

    /**
     * Gradle-style closure configurator
     */
    TriangularExtraction configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TriangularExtraction) Closure<?> action) {
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
