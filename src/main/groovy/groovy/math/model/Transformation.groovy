/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Transformation
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
@EqualsAndHashCode(includes = ['transformationId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Transformation implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** parentTransformationId */
    String parentTransformationId

    /** transformationTypeEnumId */
    String transformationTypeEnumId

    /** purposeEnumId */
    String purposeEnumId

    /** name */
    String name

    /** symbol */
    String symbol

    /** description */
    String description

    /** lastApplicationDate */
    java.sql.Timestamp lastApplicationDate

    /** resultVectorId */
    String resultVectorId

    /** resultMatrixId */
    String resultMatrixId

    /** resultTensorId */
    String resultTensorId

    /** resultFunctionId */
    String resultFunctionId

    /** resultParameterId */
    String resultParameterId

    Transformation parent

    Vector resultVector

    Matrix resultMatrix

    Tensor resultTensor

    ApproximatedFunction resultFunc

    Parameter resultParameter

    List<TransformationOperand> operands = new ArrayList<>()

    Transformation() {}

    Transformation(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('parentTransformationId')) this.parentTransformationId = args.get('parentTransformationId')?.toString()
            if (args.containsKey('transformationTypeEnumId')) this.transformationTypeEnumId = args.get('transformationTypeEnumId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('name')) this.name = args.get('name')?.toString()
            if (args.containsKey('symbol')) this.symbol = args.get('symbol')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('lastApplicationDate')) this.lastApplicationDate = (java.sql.Timestamp) args.get('lastApplicationDate')
            if (args.containsKey('resultVectorId')) this.resultVectorId = args.get('resultVectorId')?.toString()
            if (args.containsKey('resultMatrixId')) this.resultMatrixId = args.get('resultMatrixId')?.toString()
            if (args.containsKey('resultTensorId')) this.resultTensorId = args.get('resultTensorId')?.toString()
            if (args.containsKey('resultFunctionId')) this.resultFunctionId = args.get('resultFunctionId')?.toString()
            if (args.containsKey('resultParameterId')) this.resultParameterId = args.get('resultParameterId')?.toString()
        }
    }

    Transformation transformationId(String value) {
        this.transformationId = value
        return this;
    }

    Transformation parentTransformationId(String value) {
        this.parentTransformationId = value
        return this;
    }

    Transformation transformationTypeEnumId(String value) {
        this.transformationTypeEnumId = value
        return this;
    }

    Transformation purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    Transformation name(String value) {
        this.name = value
        return this;
    }

    Transformation symbol(String value) {
        this.symbol = value
        return this;
    }

    Transformation description(String value) {
        this.description = value
        return this;
    }

    Transformation lastApplicationDate(java.sql.Timestamp value) {
        this.lastApplicationDate = value
        return this;
    }

    Transformation resultVectorId(String value) {
        this.resultVectorId = value
        return this;
    }

    Transformation resultMatrixId(String value) {
        this.resultMatrixId = value
        return this;
    }

    Transformation resultTensorId(String value) {
        this.resultTensorId = value
        return this;
    }

    Transformation resultFunctionId(String value) {
        this.resultFunctionId = value
        return this;
    }

    Transformation resultParameterId(String value) {
        this.resultParameterId = value
        return this;
    }

    Transformation parent(Transformation item) {
        this.parent = item;
        return this;
    }

    Transformation resultVector(Vector item) {
        this.resultVector = item;
        return this;
    }

    Transformation resultMatrix(Matrix item) {
        this.resultMatrix = item;
        return this;
    }

    Transformation resultTensor(Tensor item) {
        this.resultTensor = item;
        return this;
    }

    Transformation resultFunc(ApproximatedFunction item) {
        this.resultFunc = item;
        return this;
    }

    Transformation resultParameter(Parameter item) {
        this.resultParameter = item;
        return this;
    }

    Transformation operands(List<TransformationOperand> list) {
        this.operands = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.parentTransformationId != null) map.put('parentTransformationId', this.parentTransformationId);
        if (this.transformationTypeEnumId != null) map.put('transformationTypeEnumId', this.transformationTypeEnumId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.name != null) map.put('name', this.name);
        if (this.symbol != null) map.put('symbol', this.symbol);
        if (this.description != null) map.put('description', this.description);
        if (this.lastApplicationDate != null) map.put('lastApplicationDate', this.lastApplicationDate);
        if (this.resultVectorId != null) map.put('resultVectorId', this.resultVectorId);
        if (this.resultMatrixId != null) map.put('resultMatrixId', this.resultMatrixId);
        if (this.resultTensorId != null) map.put('resultTensorId', this.resultTensorId);
        if (this.resultFunctionId != null) map.put('resultFunctionId', this.resultFunctionId);
        if (this.resultParameterId != null) map.put('resultParameterId', this.resultParameterId);
        return map;
    }
}