/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorDecompositionFactor
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
@EqualsAndHashCode(includes = ['transformationId', 'modeIndex'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class TensorDecompositionFactor implements Serializable {
    private static final long serialVersionUID = 1L

    String transformationId
    Long modeIndex
    String factorMatrixId

    // --- Relationships (In-Memory Navigation) ---
    TensorDecomposition decomposition
    Matrix factorMatrix

    TensorDecompositionFactor() { }

    TensorDecompositionFactor(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('modeIndex')) this.modeIndex = args.get('modeIndex') as Long
            if (args.containsKey('factorMatrixId')) this.factorMatrixId = args.get('factorMatrixId') as String
            if (args.containsKey('decomposition')) this.decomposition = args.get('decomposition') as TensorDecomposition
            if (args.containsKey('factorMatrix')) this.factorMatrix = args.get('factorMatrix') as Matrix
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
    TensorDecompositionFactor configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TensorDecompositionFactor) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    TensorDecomposition decomposition(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TensorDecomposition) Closure<?> action) {
        if (this.decomposition == null) this.decomposition = new TensorDecomposition()
        this.decomposition.configure(action)
        this.decomposition
    }

    Matrix factorMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.factorMatrix == null) this.factorMatrix = new Matrix()
        this.factorMatrix.configure(action)
        this.factorMatrix
    }
}
