/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TransformationOperand
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['transformationId', 'operandIndex'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class TransformationOperand implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** operandIndex */
    Long operandIndex

    /** operandTypeEnumId */
    String operandTypeEnumId

    /** operandVectorId */
    String operandVectorId

    /** operandMatrixId */
    String operandMatrixId

    /** operandTensorId */
    String operandTensorId

    /** operandEnumId */
    String operandEnumId

    /** operandTransformationId */
    String operandTransformationId

    /** operandParameterId */
    String operandParameterId

    Transformation transformation

    Vector operandVector

    Matrix operandMatrix

    Tensor operandTensor

    Transformation operandTransformation

    Parameter operandParameter

    TransformationOperand() {}

    TransformationOperand(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('operandIndex')) this.operandIndex = args.get('operandIndex') != null ? ((Number) args.get('operandIndex')).longValue() : null
            if (args.containsKey('operandTypeEnumId')) this.operandTypeEnumId = args.get('operandTypeEnumId')?.toString()
            if (args.containsKey('operandVectorId')) this.operandVectorId = args.get('operandVectorId')?.toString()
            if (args.containsKey('operandMatrixId')) this.operandMatrixId = args.get('operandMatrixId')?.toString()
            if (args.containsKey('operandTensorId')) this.operandTensorId = args.get('operandTensorId')?.toString()
            if (args.containsKey('operandEnumId')) this.operandEnumId = args.get('operandEnumId')?.toString()
            if (args.containsKey('operandTransformationId')) this.operandTransformationId = args.get('operandTransformationId')?.toString()
            if (args.containsKey('operandParameterId')) this.operandParameterId = args.get('operandParameterId')?.toString()
        }
    }

    TransformationOperand transformationId(String value) {
        this.transformationId = value
        return this;
    }

    TransformationOperand operandIndex(Long value) {
        this.operandIndex = value
        return this;
    }

    TransformationOperand operandTypeEnumId(String value) {
        this.operandTypeEnumId = value
        return this;
    }

    TransformationOperand operandVectorId(String value) {
        this.operandVectorId = value
        return this;
    }

    TransformationOperand operandMatrixId(String value) {
        this.operandMatrixId = value
        return this;
    }

    TransformationOperand operandTensorId(String value) {
        this.operandTensorId = value
        return this;
    }

    TransformationOperand operandEnumId(String value) {
        this.operandEnumId = value
        return this;
    }

    TransformationOperand operandTransformationId(String value) {
        this.operandTransformationId = value
        return this;
    }

    TransformationOperand operandParameterId(String value) {
        this.operandParameterId = value
        return this;
    }

    TransformationOperand transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    TransformationOperand operandVector(Vector item) {
        this.operandVector = item;
        return this;
    }

    TransformationOperand operandMatrix(Matrix item) {
        this.operandMatrix = item;
        return this;
    }

    TransformationOperand operandTensor(Tensor item) {
        this.operandTensor = item;
        return this;
    }

    TransformationOperand operandTransformation(Transformation item) {
        this.operandTransformation = item;
        return this;
    }

    TransformationOperand operandParameter(Parameter item) {
        this.operandParameter = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.operandIndex != null) map.put('operandIndex', this.operandIndex);
        if (this.operandTypeEnumId != null) map.put('operandTypeEnumId', this.operandTypeEnumId);
        if (this.operandVectorId != null) map.put('operandVectorId', this.operandVectorId);
        if (this.operandMatrixId != null) map.put('operandMatrixId', this.operandMatrixId);
        if (this.operandTensorId != null) map.put('operandTensorId', this.operandTensorId);
        if (this.operandEnumId != null) map.put('operandEnumId', this.operandEnumId);
        if (this.operandTransformationId != null) map.put('operandTransformationId', this.operandTransformationId);
        if (this.operandParameterId != null) map.put('operandParameterId', this.operandParameterId);
        return map;
    }
}