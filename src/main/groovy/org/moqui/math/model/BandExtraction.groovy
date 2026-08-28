/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.BandExtraction
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
class BandExtraction implements Serializable {
    private static final long serialVersionUID = 1L

    String transformationId
    Long lowerBand
    Long upperBand

    // --- Relationships (In-Memory Navigation) ---
    Transformation transformation

    BandExtraction() { }

    BandExtraction(String transformationId) {
        this.transformationId = Objects.requireNonNull(transformationId, "BandExtraction.transformationId cannot be null")
    }

    BandExtraction(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('lowerBand')) this.lowerBand = args.get('lowerBand') as Long
            if (args.containsKey('upperBand')) this.upperBand = args.get('upperBand') as Long
            if (args.containsKey('transformation')) this.transformation = args.get('transformation') as Transformation
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
    }

    /**
     * Gradle-style closure configurator
     */
    BandExtraction configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = BandExtraction) Closure<?> action) {
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
