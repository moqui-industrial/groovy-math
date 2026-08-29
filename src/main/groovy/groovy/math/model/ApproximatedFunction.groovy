/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ApproximatedFunction
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
@EqualsAndHashCode(includes = ['approximatedFunctionId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class ApproximatedFunction implements Serializable {
    private static final long serialVersionUID = 1L

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** interpolationEnumId */
    String interpolationEnumId

    /** purposeEnumId */
    String purposeEnumId

    /** vectorSpaceEnumId */
    String vectorSpaceEnumId

    /** codomainTypeEnumId */
    String codomainTypeEnumId

    /** parametrizationDegree */
    Long parametrizationDegree

    /** dataStorageEnumId */
    String dataStorageEnumId

    /** sampleTensorId */
    String sampleTensorId

    /** modeledTransformationId */
    String modeledTransformationId

    /** maxRelativeError */
    BigDecimal maxRelativeError

    /** errorMatrixId */
    String errorMatrixId

    /** name */
    String name

    /** description */
    String description

    /** fromDate */
    java.sql.Timestamp fromDate

    /** thruDate */
    java.sql.Timestamp thruDate

    Tensor sampleTensor

    Transformation modeledTransform

    Matrix errorMatrix

    List<ApproximatedFunctionSample> approxFuncSamples = new ArrayList<>()

    ParametricPath parametricPath

    ApproximatedFunction() {}

    ApproximatedFunction(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('interpolationEnumId')) this.interpolationEnumId = args.get('interpolationEnumId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('vectorSpaceEnumId')) this.vectorSpaceEnumId = args.get('vectorSpaceEnumId')?.toString()
            if (args.containsKey('codomainTypeEnumId')) this.codomainTypeEnumId = args.get('codomainTypeEnumId')?.toString()
            if (args.containsKey('parametrizationDegree')) this.parametrizationDegree = args.get('parametrizationDegree') != null ? ((Number) args.get('parametrizationDegree')).longValue() : null
            if (args.containsKey('dataStorageEnumId')) this.dataStorageEnumId = args.get('dataStorageEnumId')?.toString()
            if (args.containsKey('sampleTensorId')) this.sampleTensorId = args.get('sampleTensorId')?.toString()
            if (args.containsKey('modeledTransformationId')) this.modeledTransformationId = args.get('modeledTransformationId')?.toString()
            if (args.containsKey('maxRelativeError')) this.maxRelativeError = args.get('maxRelativeError') != null ? (args.get('maxRelativeError') instanceof BigDecimal ? (BigDecimal) args.get('maxRelativeError') : new BigDecimal(args.get('maxRelativeError').toString())) : null
            if (args.containsKey('errorMatrixId')) this.errorMatrixId = args.get('errorMatrixId')?.toString()
            if (args.containsKey('name')) this.name = args.get('name')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('fromDate')) this.fromDate = (java.sql.Timestamp) args.get('fromDate')
            if (args.containsKey('thruDate')) this.thruDate = (java.sql.Timestamp) args.get('thruDate')
        }
    }

    ApproximatedFunction approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    ApproximatedFunction interpolationEnumId(String value) {
        this.interpolationEnumId = value
        return this;
    }

    ApproximatedFunction purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    ApproximatedFunction vectorSpaceEnumId(String value) {
        this.vectorSpaceEnumId = value
        return this;
    }

    ApproximatedFunction codomainTypeEnumId(String value) {
        this.codomainTypeEnumId = value
        return this;
    }

    ApproximatedFunction parametrizationDegree(Long value) {
        this.parametrizationDegree = value
        return this;
    }

    ApproximatedFunction dataStorageEnumId(String value) {
        this.dataStorageEnumId = value
        return this;
    }

    ApproximatedFunction sampleTensorId(String value) {
        this.sampleTensorId = value
        return this;
    }

    ApproximatedFunction modeledTransformationId(String value) {
        this.modeledTransformationId = value
        return this;
    }

    ApproximatedFunction maxRelativeError(BigDecimal value) {
        this.maxRelativeError = value
        return this;
    }

    ApproximatedFunction errorMatrixId(String value) {
        this.errorMatrixId = value
        return this;
    }

    ApproximatedFunction name(String value) {
        this.name = value
        return this;
    }

    ApproximatedFunction description(String value) {
        this.description = value
        return this;
    }

    ApproximatedFunction fromDate(java.sql.Timestamp value) {
        this.fromDate = value
        return this;
    }

    ApproximatedFunction thruDate(java.sql.Timestamp value) {
        this.thruDate = value
        return this;
    }

    ApproximatedFunction sampleTensor(Tensor item) {
        this.sampleTensor = item;
        return this;
    }

    ApproximatedFunction modeledTransform(Transformation item) {
        this.modeledTransform = item;
        return this;
    }

    ApproximatedFunction errorMatrix(Matrix item) {
        this.errorMatrix = item;
        return this;
    }

    ApproximatedFunction approxFuncSamples(List<ApproximatedFunctionSample> list) {
        this.approxFuncSamples = list;
        return this;
    }

    ApproximatedFunction parametricPath(ParametricPath item) {
        this.parametricPath = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.interpolationEnumId != null) map.put('interpolationEnumId', this.interpolationEnumId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.vectorSpaceEnumId != null) map.put('vectorSpaceEnumId', this.vectorSpaceEnumId);
        if (this.codomainTypeEnumId != null) map.put('codomainTypeEnumId', this.codomainTypeEnumId);
        if (this.parametrizationDegree != null) map.put('parametrizationDegree', this.parametrizationDegree);
        if (this.dataStorageEnumId != null) map.put('dataStorageEnumId', this.dataStorageEnumId);
        if (this.sampleTensorId != null) map.put('sampleTensorId', this.sampleTensorId);
        if (this.modeledTransformationId != null) map.put('modeledTransformationId', this.modeledTransformationId);
        if (this.maxRelativeError != null) map.put('maxRelativeError', this.maxRelativeError);
        if (this.errorMatrixId != null) map.put('errorMatrixId', this.errorMatrixId);
        if (this.name != null) map.put('name', this.name);
        if (this.description != null) map.put('description', this.description);
        if (this.fromDate != null) map.put('fromDate', this.fromDate);
        if (this.thruDate != null) map.put('thruDate', this.thruDate);
        return map;
    }
}