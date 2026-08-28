/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParametricPathPoint
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
@EqualsAndHashCode(includes = ['approximatedFunctionId', 'approximatedFunctionSampleId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ParametricPathPoint implements Serializable {
    private static final long serialVersionUID = 1L

    String approximatedFunctionId
    String approximatedFunctionSampleId
    String isCriticalPoint
    BigDecimal tolerance
    BigDecimal arcLength
    BigDecimal weight

    // --- Relationships (In-Memory Navigation) ---
    ParametricPath parametricPath
    ApproximatedFunctionSample sample

    ParametricPathPoint() { }

    ParametricPathPoint(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('approximatedFunctionSampleId')) this.approximatedFunctionSampleId = args.get('approximatedFunctionSampleId') as String
            if (args.containsKey('isCriticalPoint')) this.isCriticalPoint = args.get('isCriticalPoint') as String
            if (args.containsKey('tolerance')) this.tolerance = args.get('tolerance') as BigDecimal
            if (args.containsKey('arcLength')) this.arcLength = args.get('arcLength') as BigDecimal
            if (args.containsKey('weight')) this.weight = args.get('weight') as BigDecimal
            if (args.containsKey('parametricPath')) this.parametricPath = args.get('parametricPath') as ParametricPath
            if (args.containsKey('sample')) this.sample = args.get('sample') as ApproximatedFunctionSample
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
    ParametricPathPoint configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPathPoint) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    ParametricPath parametricPath(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPath) Closure<?> action) {
        if (this.parametricPath == null) this.parametricPath = new ParametricPath()
        this.parametricPath.configure(action)
        this.parametricPath
    }

    ApproximatedFunctionSample sample(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ApproximatedFunctionSample) Closure<?> action) {
        if (this.sample == null) this.sample = new ApproximatedFunctionSample()
        this.sample.configure(action)
        this.sample
    }
}
