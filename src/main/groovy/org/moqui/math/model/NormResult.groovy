/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.NormResult
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
class NormResult implements Serializable {
    private static final long serialVersionUID = 1L

    String transformationId
    String domainEnumId // Required
    String orderEnumId // Required
    String reductionDimensions
    String keepDimensions
    BigDecimal normValue // Required

    // --- Relationships (In-Memory Navigation) ---
    Transformation transformation
    Object domain
    Object order

    NormResult() { }

    NormResult(String transformationId) {
        this.transformationId = Objects.requireNonNull(transformationId, "NormResult.transformationId cannot be null")
    }

    NormResult(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('domainEnumId')) this.domainEnumId = args.get('domainEnumId') as String
            if (args.containsKey('orderEnumId')) this.orderEnumId = args.get('orderEnumId') as String
            if (args.containsKey('reductionDimensions')) this.reductionDimensions = args.get('reductionDimensions') as String
            if (args.containsKey('keepDimensions')) this.keepDimensions = args.get('keepDimensions') as String
            if (args.containsKey('normValue')) this.normValue = args.get('normValue') as BigDecimal
            if (args.containsKey('transformation')) this.transformation = args.get('transformation') as Transformation
            if (args.containsKey('domain')) this.domain = args.get('domain') as Object
            if (args.containsKey('order')) this.order = args.get('order') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.domainEnumId == null) throw new IllegalStateException("Required property missing: NormResult.domainEnumId")
        if (this.orderEnumId == null) throw new IllegalStateException("Required property missing: NormResult.orderEnumId")
        if (this.normValue == null) throw new IllegalStateException("Required property missing: NormResult.normValue")
    }

    /**
     * Gradle-style closure configurator
     */
    NormResult configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = NormResult) Closure<?> action) {
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
