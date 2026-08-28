/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ApproximatedFunctionDerivative
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
@EqualsAndHashCode(includes = ['approximatedFunctionDerivativeId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ApproximatedFunctionDerivative implements Serializable {
    private static final long serialVersionUID = 1L

    String approximatedFunctionDerivativeId
    String approximatedFunctionId // Required
    String approximatedFunctionSampleId // Required
    String purposeEnumId
    Long derivativeOrder
    String derivativeMatrixId
    String derivativeTensorId
    String derivativeVectorId
    String errorVectorId

    // --- Relationships (In-Memory Navigation) ---
    ApproximatedFunctionSample approxFuncSample
    Object purpose
    Matrix derivativeMatrix
    Tensor derivativeTensor
    Vector derivativeVector
    Vector errorVector

    ApproximatedFunctionDerivative() { }

    ApproximatedFunctionDerivative(String approximatedFunctionDerivativeId) {
        this.approximatedFunctionDerivativeId = Objects.requireNonNull(approximatedFunctionDerivativeId, "ApproximatedFunctionDerivative.approximatedFunctionDerivativeId cannot be null")
    }

    ApproximatedFunctionDerivative(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionDerivativeId')) this.approximatedFunctionDerivativeId = args.get('approximatedFunctionDerivativeId') as String
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('approximatedFunctionSampleId')) this.approximatedFunctionSampleId = args.get('approximatedFunctionSampleId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('derivativeOrder')) this.derivativeOrder = args.get('derivativeOrder') as Long
            if (args.containsKey('derivativeMatrixId')) this.derivativeMatrixId = args.get('derivativeMatrixId') as String
            if (args.containsKey('derivativeTensorId')) this.derivativeTensorId = args.get('derivativeTensorId') as String
            if (args.containsKey('derivativeVectorId')) this.derivativeVectorId = args.get('derivativeVectorId') as String
            if (args.containsKey('errorVectorId')) this.errorVectorId = args.get('errorVectorId') as String
            if (args.containsKey('approxFuncSample')) this.approxFuncSample = args.get('approxFuncSample') as ApproximatedFunctionSample
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('derivativeMatrix')) this.derivativeMatrix = args.get('derivativeMatrix') as Matrix
            if (args.containsKey('derivativeTensor')) this.derivativeTensor = args.get('derivativeTensor') as Tensor
            if (args.containsKey('derivativeVector')) this.derivativeVector = args.get('derivativeVector') as Vector
            if (args.containsKey('errorVector')) this.errorVector = args.get('errorVector') as Vector
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.approximatedFunctionId == null) throw new IllegalStateException("Required property missing: ApproximatedFunctionDerivative.approximatedFunctionId")
        if (this.approximatedFunctionSampleId == null) throw new IllegalStateException("Required property missing: ApproximatedFunctionDerivative.approximatedFunctionSampleId")
    }

    /**
     * Gradle-style closure configurator
     */
    ApproximatedFunctionDerivative configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ApproximatedFunctionDerivative) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    ApproximatedFunctionSample approxFuncSample(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ApproximatedFunctionSample) Closure<?> action) {
        if (this.approxFuncSample == null) this.approxFuncSample = new ApproximatedFunctionSample()
        this.approxFuncSample.configure(action)
        this.approxFuncSample
    }

    Matrix derivativeMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.derivativeMatrix == null) this.derivativeMatrix = new Matrix()
        this.derivativeMatrix.configure(action)
        this.derivativeMatrix
    }

    Tensor derivativeTensor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Tensor) Closure<?> action) {
        if (this.derivativeTensor == null) this.derivativeTensor = new Tensor()
        this.derivativeTensor.configure(action)
        this.derivativeTensor
    }

    Vector derivativeVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.derivativeVector == null) this.derivativeVector = new Vector()
        this.derivativeVector.configure(action)
        this.derivativeVector
    }

    Vector errorVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.errorVector == null) this.errorVector = new Vector()
        this.errorVector.configure(action)
        this.errorVector
    }
}
