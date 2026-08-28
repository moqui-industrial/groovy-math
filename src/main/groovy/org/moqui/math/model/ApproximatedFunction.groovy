/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ApproximatedFunction
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
@EqualsAndHashCode(includes = ['approximatedFunctionId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ApproximatedFunction implements Serializable {
    private static final long serialVersionUID = 1L

    String approximatedFunctionId
    String interpolationEnumId
    String purposeEnumId
    String vectorSpaceEnumId // Required
    String codomainTypeEnumId // Required
    Long parametrizationDegree
    String dataStorageEnumId // Required
    String sampleTensorId
    String modeledTransformationId
    BigDecimal maxRelativeError
    String errorMatrixId
    String name
    String description
    java.sql.Timestamp fromDate
    java.sql.Timestamp thruDate

    // --- Relationships (In-Memory Navigation) ---
    Object interpolation
    Object purpose
    Object vectorSpace
    Object storageType
    Tensor sampleTensor
    Transformation modeledTransform
    Matrix errorMatrix
    List<ApproximatedFunctionSample> approxFuncSamples = []
    ParametricPath parametricPath

    ApproximatedFunction() { }

    ApproximatedFunction(String approximatedFunctionId) {
        this.approximatedFunctionId = Objects.requireNonNull(approximatedFunctionId, "ApproximatedFunction.approximatedFunctionId cannot be null")
    }

    ApproximatedFunction(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('interpolationEnumId')) this.interpolationEnumId = args.get('interpolationEnumId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('vectorSpaceEnumId')) this.vectorSpaceEnumId = args.get('vectorSpaceEnumId') as String
            if (args.containsKey('codomainTypeEnumId')) this.codomainTypeEnumId = args.get('codomainTypeEnumId') as String
            if (args.containsKey('parametrizationDegree')) this.parametrizationDegree = args.get('parametrizationDegree') as Long
            if (args.containsKey('dataStorageEnumId')) this.dataStorageEnumId = args.get('dataStorageEnumId') as String
            if (args.containsKey('sampleTensorId')) this.sampleTensorId = args.get('sampleTensorId') as String
            if (args.containsKey('modeledTransformationId')) this.modeledTransformationId = args.get('modeledTransformationId') as String
            if (args.containsKey('maxRelativeError')) this.maxRelativeError = args.get('maxRelativeError') as BigDecimal
            if (args.containsKey('errorMatrixId')) this.errorMatrixId = args.get('errorMatrixId') as String
            if (args.containsKey('name')) this.name = args.get('name') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('fromDate')) this.fromDate = args.get('fromDate') as java.sql.Timestamp
            if (args.containsKey('thruDate')) this.thruDate = args.get('thruDate') as java.sql.Timestamp
            if (args.containsKey('interpolation')) this.interpolation = args.get('interpolation') as Object
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('vectorSpace')) this.vectorSpace = args.get('vectorSpace') as Object
            if (args.containsKey('storageType')) this.storageType = args.get('storageType') as Object
            if (args.containsKey('sampleTensor')) this.sampleTensor = args.get('sampleTensor') as Tensor
            if (args.containsKey('modeledTransform')) this.modeledTransform = args.get('modeledTransform') as Transformation
            if (args.containsKey('errorMatrix')) this.errorMatrix = args.get('errorMatrix') as Matrix
            if (args.containsKey('approxFuncSamples')) this.approxFuncSamples = args.get('approxFuncSamples') as List<ApproximatedFunctionSample>
            if (args.containsKey('parametricPath')) this.parametricPath = args.get('parametricPath') as ParametricPath
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.vectorSpaceEnumId == null) throw new IllegalStateException("Required property missing: ApproximatedFunction.vectorSpaceEnumId")
        if (this.codomainTypeEnumId == null) throw new IllegalStateException("Required property missing: ApproximatedFunction.codomainTypeEnumId")
        if (this.dataStorageEnumId == null) throw new IllegalStateException("Required property missing: ApproximatedFunction.dataStorageEnumId")
    }

    /**
     * Gradle-style closure configurator
     */
    ApproximatedFunction configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ApproximatedFunction) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Tensor sampleTensor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Tensor) Closure<?> action) {
        if (this.sampleTensor == null) this.sampleTensor = new Tensor()
        this.sampleTensor.configure(action)
        this.sampleTensor
    }

    Transformation modeledTransform(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Transformation) Closure<?> action) {
        if (this.modeledTransform == null) this.modeledTransform = new Transformation()
        this.modeledTransform.configure(action)
        this.modeledTransform
    }

    Matrix errorMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.errorMatrix == null) this.errorMatrix = new Matrix()
        this.errorMatrix.configure(action)
        this.errorMatrix
    }

    ApproximatedFunctionSample approxFuncSamples(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ApproximatedFunctionSample) Closure<?> action) {
        ApproximatedFunctionSample item = new ApproximatedFunctionSample()
        item.configure(action)
        if (this.approxFuncSamples == null) this.approxFuncSamples = []
        this.approxFuncSamples.add(item)
        item
    }

    ParametricPath parametricPath(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPath) Closure<?> action) {
        if (this.parametricPath == null) this.parametricPath = new ParametricPath()
        this.parametricPath.configure(action)
        this.parametricPath
    }
}
