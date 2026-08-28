/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.CoordinateSystemBaseVector
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
@EqualsAndHashCode(includes = ['coordinateSystemId', 'vectorId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class CoordinateSystemBaseVector implements Serializable {
    private static final long serialVersionUID = 1L

    String coordinateSystemId
    String vectorId
    String vectorPurposeEnumId
    Long baseIndex // Required
    String dimensionEnumId
    String axisName

    // --- Relationships (In-Memory Navigation) ---
    CoordinateSystem coordSystem
    Vector vector
    Object purpose
    Object dimension

    CoordinateSystemBaseVector() { }

    CoordinateSystemBaseVector(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId') as String
            if (args.containsKey('vectorId')) this.vectorId = args.get('vectorId') as String
            if (args.containsKey('vectorPurposeEnumId')) this.vectorPurposeEnumId = args.get('vectorPurposeEnumId') as String
            if (args.containsKey('baseIndex')) this.baseIndex = args.get('baseIndex') as Long
            if (args.containsKey('dimensionEnumId')) this.dimensionEnumId = args.get('dimensionEnumId') as String
            if (args.containsKey('axisName')) this.axisName = args.get('axisName') as String
            if (args.containsKey('coordSystem')) this.coordSystem = args.get('coordSystem') as CoordinateSystem
            if (args.containsKey('vector')) this.vector = args.get('vector') as Vector
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('dimension')) this.dimension = args.get('dimension') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.baseIndex == null) throw new IllegalStateException("Required property missing: CoordinateSystemBaseVector.baseIndex")
    }

    /**
     * Gradle-style closure configurator
     */
    CoordinateSystemBaseVector configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystemBaseVector) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    CoordinateSystem coordSystem(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystem) Closure<?> action) {
        if (this.coordSystem == null) this.coordSystem = new CoordinateSystem()
        this.coordSystem.configure(action)
        this.coordSystem
    }

    Vector vector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.vector == null) this.vector = new Vector()
        this.vector.configure(action)
        this.vector
    }
}
