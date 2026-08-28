/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorDecomposition
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
class TensorDecomposition implements Serializable {
    private static final long serialVersionUID = 1L

    String transformationId
    String decompositionMethodEnumId // Required
    String coreTensorId
    String sourceTensorId // Required
    Long targetRank
    BigDecimal fitError
    BigDecimal explainedVariance
    String description

    // --- Relationships (In-Memory Navigation) ---
    Transformation transformation
    Object decompMethod
    Tensor coreTensor
    Tensor sourceTensor

    TensorDecomposition() { }

    TensorDecomposition(String transformationId) {
        this.transformationId = Objects.requireNonNull(transformationId, "TensorDecomposition.transformationId cannot be null")
    }

    TensorDecomposition(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('decompositionMethodEnumId')) this.decompositionMethodEnumId = args.get('decompositionMethodEnumId') as String
            if (args.containsKey('coreTensorId')) this.coreTensorId = args.get('coreTensorId') as String
            if (args.containsKey('sourceTensorId')) this.sourceTensorId = args.get('sourceTensorId') as String
            if (args.containsKey('targetRank')) this.targetRank = args.get('targetRank') as Long
            if (args.containsKey('fitError')) this.fitError = args.get('fitError') as BigDecimal
            if (args.containsKey('explainedVariance')) this.explainedVariance = args.get('explainedVariance') as BigDecimal
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('transformation')) this.transformation = args.get('transformation') as Transformation
            if (args.containsKey('decompMethod')) this.decompMethod = args.get('decompMethod') as Object
            if (args.containsKey('coreTensor')) this.coreTensor = args.get('coreTensor') as Tensor
            if (args.containsKey('sourceTensor')) this.sourceTensor = args.get('sourceTensor') as Tensor
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.decompositionMethodEnumId == null) throw new IllegalStateException("Required property missing: TensorDecomposition.decompositionMethodEnumId")
        if (this.sourceTensorId == null) throw new IllegalStateException("Required property missing: TensorDecomposition.sourceTensorId")
    }

    /**
     * Gradle-style closure configurator
     */
    TensorDecomposition configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TensorDecomposition) Closure<?> action) {
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

    Tensor coreTensor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Tensor) Closure<?> action) {
        if (this.coreTensor == null) this.coreTensor = new Tensor()
        this.coreTensor.configure(action)
        this.coreTensor
    }

    Tensor sourceTensor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Tensor) Closure<?> action) {
        if (this.sourceTensor == null) this.sourceTensor = new Tensor()
        this.sourceTensor.configure(action)
        this.sourceTensor
    }
}
