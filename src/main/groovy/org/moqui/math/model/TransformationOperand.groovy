/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TransformationOperand
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
@EqualsAndHashCode(includes = ['transformationId', 'operandIndex'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class TransformationOperand implements Serializable {
    private static final long serialVersionUID = 1L

    String transformationId
    Long operandIndex
    String operandTypeEnumId
    String operandVectorId
    String operandMatrixId
    String operandTensorId
    String operandEnumId
    String operandTransformationId
    String operandParameterId

    // --- Relationships (In-Memory Navigation) ---
    Transformation transformation
    Object type
    Vector operandVector
    Matrix operandMatrix
    Tensor operandTensor
    Object operandEnum
    Transformation operandTransformation
    Parameter operandParameter

    TransformationOperand() { }

    TransformationOperand(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('operandIndex')) this.operandIndex = args.get('operandIndex') as Long
            if (args.containsKey('operandTypeEnumId')) this.operandTypeEnumId = args.get('operandTypeEnumId') as String
            if (args.containsKey('operandVectorId')) this.operandVectorId = args.get('operandVectorId') as String
            if (args.containsKey('operandMatrixId')) this.operandMatrixId = args.get('operandMatrixId') as String
            if (args.containsKey('operandTensorId')) this.operandTensorId = args.get('operandTensorId') as String
            if (args.containsKey('operandEnumId')) this.operandEnumId = args.get('operandEnumId') as String
            if (args.containsKey('operandTransformationId')) this.operandTransformationId = args.get('operandTransformationId') as String
            if (args.containsKey('operandParameterId')) this.operandParameterId = args.get('operandParameterId') as String
            if (args.containsKey('transformation')) this.transformation = args.get('transformation') as Transformation
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('operandVector')) this.operandVector = args.get('operandVector') as Vector
            if (args.containsKey('operandMatrix')) this.operandMatrix = args.get('operandMatrix') as Matrix
            if (args.containsKey('operandTensor')) this.operandTensor = args.get('operandTensor') as Tensor
            if (args.containsKey('operandEnum')) this.operandEnum = args.get('operandEnum') as Object
            if (args.containsKey('operandTransformation')) this.operandTransformation = args.get('operandTransformation') as Transformation
            if (args.containsKey('operandParameter')) this.operandParameter = args.get('operandParameter') as Parameter
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
    TransformationOperand configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TransformationOperand) Closure<?> action) {
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

    Vector operandVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.operandVector == null) this.operandVector = new Vector()
        this.operandVector.configure(action)
        this.operandVector
    }

    Matrix operandMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.operandMatrix == null) this.operandMatrix = new Matrix()
        this.operandMatrix.configure(action)
        this.operandMatrix
    }

    Tensor operandTensor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Tensor) Closure<?> action) {
        if (this.operandTensor == null) this.operandTensor = new Tensor()
        this.operandTensor.configure(action)
        this.operandTensor
    }

    Transformation operandTransformation(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Transformation) Closure<?> action) {
        if (this.operandTransformation == null) this.operandTransformation = new Transformation()
        this.operandTransformation.configure(action)
        this.operandTransformation
    }

    Parameter operandParameter(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Parameter) Closure<?> action) {
        if (this.operandParameter == null) this.operandParameter = new Parameter()
        this.operandParameter.configure(action)
        this.operandParameter
    }
}
