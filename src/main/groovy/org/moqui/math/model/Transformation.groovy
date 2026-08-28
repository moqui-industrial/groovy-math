/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Transformation
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
class Transformation implements Serializable {
    private static final long serialVersionUID = 1L

    String transformationId
    String parentTransformationId
    String transformationTypeEnumId // Required
    String purposeEnumId
    String name
    String symbol
    String description
    java.sql.Timestamp lastApplicationDate
    String resultVectorId
    String resultMatrixId
    String resultTensorId
    String resultFunctionId
    String resultParameterId

    // --- Relationships (In-Memory Navigation) ---
    Transformation parent
    Object type
    Object purpose
    Vector resultVector
    Matrix resultMatrix
    Tensor resultTensor
    ApproximatedFunction resultFunc
    Parameter resultParameter
    List<TransformationOperand> operands = []

    Transformation() { }

    Transformation(String transformationId) {
        this.transformationId = Objects.requireNonNull(transformationId, "Transformation.transformationId cannot be null")
    }

    Transformation(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('parentTransformationId')) this.parentTransformationId = args.get('parentTransformationId') as String
            if (args.containsKey('transformationTypeEnumId')) this.transformationTypeEnumId = args.get('transformationTypeEnumId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('name')) this.name = args.get('name') as String
            if (args.containsKey('symbol')) this.symbol = args.get('symbol') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('lastApplicationDate')) this.lastApplicationDate = args.get('lastApplicationDate') as java.sql.Timestamp
            if (args.containsKey('resultVectorId')) this.resultVectorId = args.get('resultVectorId') as String
            if (args.containsKey('resultMatrixId')) this.resultMatrixId = args.get('resultMatrixId') as String
            if (args.containsKey('resultTensorId')) this.resultTensorId = args.get('resultTensorId') as String
            if (args.containsKey('resultFunctionId')) this.resultFunctionId = args.get('resultFunctionId') as String
            if (args.containsKey('resultParameterId')) this.resultParameterId = args.get('resultParameterId') as String
            if (args.containsKey('parent')) this.parent = args.get('parent') as Transformation
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('resultVector')) this.resultVector = args.get('resultVector') as Vector
            if (args.containsKey('resultMatrix')) this.resultMatrix = args.get('resultMatrix') as Matrix
            if (args.containsKey('resultTensor')) this.resultTensor = args.get('resultTensor') as Tensor
            if (args.containsKey('resultFunc')) this.resultFunc = args.get('resultFunc') as ApproximatedFunction
            if (args.containsKey('resultParameter')) this.resultParameter = args.get('resultParameter') as Parameter
            if (args.containsKey('operands')) this.operands = args.get('operands') as List<TransformationOperand>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.transformationTypeEnumId == null) throw new IllegalStateException("Required property missing: Transformation.transformationTypeEnumId")
    }

    /**
     * Gradle-style closure configurator
     */
    Transformation configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Transformation) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Transformation parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Transformation) Closure<?> action) {
        if (this.parent == null) this.parent = new Transformation()
        this.parent.configure(action)
        this.parent
    }

    Vector resultVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.resultVector == null) this.resultVector = new Vector()
        this.resultVector.configure(action)
        this.resultVector
    }

    Matrix resultMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.resultMatrix == null) this.resultMatrix = new Matrix()
        this.resultMatrix.configure(action)
        this.resultMatrix
    }

    Tensor resultTensor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Tensor) Closure<?> action) {
        if (this.resultTensor == null) this.resultTensor = new Tensor()
        this.resultTensor.configure(action)
        this.resultTensor
    }

    ApproximatedFunction resultFunc(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ApproximatedFunction) Closure<?> action) {
        if (this.resultFunc == null) this.resultFunc = new ApproximatedFunction()
        this.resultFunc.configure(action)
        this.resultFunc
    }

    Parameter resultParameter(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Parameter) Closure<?> action) {
        if (this.resultParameter == null) this.resultParameter = new Parameter()
        this.resultParameter.configure(action)
        this.resultParameter
    }

    TransformationOperand operands(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TransformationOperand) Closure<?> action) {
        TransformationOperand item = new TransformationOperand()
        item.configure(action)
        if (this.operands == null) this.operands = []
        this.operands.add(item)
        item
    }
}
