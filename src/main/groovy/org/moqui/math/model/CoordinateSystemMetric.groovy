/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.CoordinateSystemMetric
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
@EqualsAndHashCode(includes = ['coordinateSystemId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class CoordinateSystemMetric implements Serializable {
    private static final long serialVersionUID = 1L

    String coordinateSystemId
    String gramMatrixId // Required

    // --- Relationships (In-Memory Navigation) ---
    CoordinateSystem coordinateSystem
    Matrix g

    CoordinateSystemMetric() { }

    CoordinateSystemMetric(String coordinateSystemId) {
        this.coordinateSystemId = Objects.requireNonNull(coordinateSystemId, "CoordinateSystemMetric.coordinateSystemId cannot be null")
    }

    CoordinateSystemMetric(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId') as String
            if (args.containsKey('gramMatrixId')) this.gramMatrixId = args.get('gramMatrixId') as String
            if (args.containsKey('coordinateSystem')) this.coordinateSystem = args.get('coordinateSystem') as CoordinateSystem
            if (args.containsKey('g')) this.g = args.get('g') as Matrix
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.gramMatrixId == null) throw new IllegalStateException("Required property missing: CoordinateSystemMetric.gramMatrixId")
    }

    /**
     * Gradle-style closure configurator
     */
    CoordinateSystemMetric configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystemMetric) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    CoordinateSystem coordinateSystem(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystem) Closure<?> action) {
        if (this.coordinateSystem == null) this.coordinateSystem = new CoordinateSystem()
        this.coordinateSystem.configure(action)
        this.coordinateSystem
    }

    Matrix g(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.g == null) this.g = new Matrix()
        this.g.configure(action)
        this.g
    }
}
