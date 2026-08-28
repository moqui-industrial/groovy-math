/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.CoordinateSystemTransformation
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
class CoordinateSystemTransformation implements Serializable {
    private static final long serialVersionUID = 1L

    String transformationId
    String sourceCoordinateSystemId // Required
    String targetCoordinateSystemId // Required
    String matrixId // Required

    // --- Relationships (In-Memory Navigation) ---
    Transformation transformation
    CoordinateSystem sourceCoordSystem
    CoordinateSystem targetCoordSystem
    Matrix matrix

    CoordinateSystemTransformation() { }

    CoordinateSystemTransformation(String transformationId) {
        this.transformationId = Objects.requireNonNull(transformationId, "CoordinateSystemTransformation.transformationId cannot be null")
    }

    CoordinateSystemTransformation(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('sourceCoordinateSystemId')) this.sourceCoordinateSystemId = args.get('sourceCoordinateSystemId') as String
            if (args.containsKey('targetCoordinateSystemId')) this.targetCoordinateSystemId = args.get('targetCoordinateSystemId') as String
            if (args.containsKey('matrixId')) this.matrixId = args.get('matrixId') as String
            if (args.containsKey('transformation')) this.transformation = args.get('transformation') as Transformation
            if (args.containsKey('sourceCoordSystem')) this.sourceCoordSystem = args.get('sourceCoordSystem') as CoordinateSystem
            if (args.containsKey('targetCoordSystem')) this.targetCoordSystem = args.get('targetCoordSystem') as CoordinateSystem
            if (args.containsKey('matrix')) this.matrix = args.get('matrix') as Matrix
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.sourceCoordinateSystemId == null) throw new IllegalStateException("Required property missing: CoordinateSystemTransformation.sourceCoordinateSystemId")
        if (this.targetCoordinateSystemId == null) throw new IllegalStateException("Required property missing: CoordinateSystemTransformation.targetCoordinateSystemId")
        if (this.matrixId == null) throw new IllegalStateException("Required property missing: CoordinateSystemTransformation.matrixId")
    }

    /**
     * Gradle-style closure configurator
     */
    CoordinateSystemTransformation configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystemTransformation) Closure<?> action) {
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

    CoordinateSystem sourceCoordSystem(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystem) Closure<?> action) {
        if (this.sourceCoordSystem == null) this.sourceCoordSystem = new CoordinateSystem()
        this.sourceCoordSystem.configure(action)
        this.sourceCoordSystem
    }

    CoordinateSystem targetCoordSystem(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystem) Closure<?> action) {
        if (this.targetCoordSystem == null) this.targetCoordSystem = new CoordinateSystem()
        this.targetCoordSystem.configure(action)
        this.targetCoordSystem
    }

    Matrix matrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.matrix == null) this.matrix = new Matrix()
        this.matrix.configure(action)
        this.matrix
    }
}
