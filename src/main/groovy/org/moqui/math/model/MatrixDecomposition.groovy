/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MatrixDecomposition
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
class MatrixDecomposition implements Serializable {
    private static final long serialVersionUID = 1L

    String transformationId
    String leftMatrixId
    String diagMatrixId
    String rightMatrixId
    Long rankApproximation
    BigDecimal explainedVariance
    BigDecimal fitError

    // --- Relationships (In-Memory Navigation) ---
    Transformation transformation
    Matrix leftMatrix
    Matrix diagMatrix
    Matrix rightMatrix

    MatrixDecomposition() { }

    MatrixDecomposition(String transformationId) {
        this.transformationId = Objects.requireNonNull(transformationId, "MatrixDecomposition.transformationId cannot be null")
    }

    MatrixDecomposition(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('leftMatrixId')) this.leftMatrixId = args.get('leftMatrixId') as String
            if (args.containsKey('diagMatrixId')) this.diagMatrixId = args.get('diagMatrixId') as String
            if (args.containsKey('rightMatrixId')) this.rightMatrixId = args.get('rightMatrixId') as String
            if (args.containsKey('rankApproximation')) this.rankApproximation = args.get('rankApproximation') as Long
            if (args.containsKey('explainedVariance')) this.explainedVariance = args.get('explainedVariance') as BigDecimal
            if (args.containsKey('fitError')) this.fitError = args.get('fitError') as BigDecimal
            if (args.containsKey('transformation')) this.transformation = args.get('transformation') as Transformation
            if (args.containsKey('leftMatrix')) this.leftMatrix = args.get('leftMatrix') as Matrix
            if (args.containsKey('diagMatrix')) this.diagMatrix = args.get('diagMatrix') as Matrix
            if (args.containsKey('rightMatrix')) this.rightMatrix = args.get('rightMatrix') as Matrix
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
    MatrixDecomposition configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MatrixDecomposition) Closure<?> action) {
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

    Matrix leftMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.leftMatrix == null) this.leftMatrix = new Matrix()
        this.leftMatrix.configure(action)
        this.leftMatrix
    }

    Matrix diagMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.diagMatrix == null) this.diagMatrix = new Matrix()
        this.diagMatrix.configure(action)
        this.diagMatrix
    }

    Matrix rightMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.rightMatrix == null) this.rightMatrix = new Matrix()
        this.rightMatrix.configure(action)
        this.rightMatrix
    }
}
