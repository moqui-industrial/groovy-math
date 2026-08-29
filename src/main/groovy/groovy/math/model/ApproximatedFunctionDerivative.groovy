/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ApproximatedFunctionDerivative
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
@EqualsAndHashCode(includes = ['approximatedFunctionDerivativeId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class ApproximatedFunctionDerivative implements Serializable {
    private static final long serialVersionUID = 1L

    /** approximatedFunctionDerivativeId */
    String approximatedFunctionDerivativeId

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** approximatedFunctionSampleId */
    String approximatedFunctionSampleId

    /** purposeEnumId */
    String purposeEnumId

    /** derivativeOrder */
    Long derivativeOrder

    /** derivativeMatrixId */
    String derivativeMatrixId

    /** derivativeTensorId */
    String derivativeTensorId

    /** derivativeVectorId */
    String derivativeVectorId

    /** errorVectorId */
    String errorVectorId

    ApproximatedFunctionSample approxFuncSample

    Matrix derivativeMatrix

    Tensor derivativeTensor

    Vector derivativeVector

    Vector errorVector

    ApproximatedFunctionDerivative() {}

    ApproximatedFunctionDerivative(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionDerivativeId')) this.approximatedFunctionDerivativeId = args.get('approximatedFunctionDerivativeId')?.toString()
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('approximatedFunctionSampleId')) this.approximatedFunctionSampleId = args.get('approximatedFunctionSampleId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('derivativeOrder')) this.derivativeOrder = args.get('derivativeOrder') != null ? ((Number) args.get('derivativeOrder')).longValue() : null
            if (args.containsKey('derivativeMatrixId')) this.derivativeMatrixId = args.get('derivativeMatrixId')?.toString()
            if (args.containsKey('derivativeTensorId')) this.derivativeTensorId = args.get('derivativeTensorId')?.toString()
            if (args.containsKey('derivativeVectorId')) this.derivativeVectorId = args.get('derivativeVectorId')?.toString()
            if (args.containsKey('errorVectorId')) this.errorVectorId = args.get('errorVectorId')?.toString()
        }
    }

    ApproximatedFunctionDerivative approximatedFunctionDerivativeId(String value) {
        this.approximatedFunctionDerivativeId = value
        return this;
    }

    ApproximatedFunctionDerivative approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    ApproximatedFunctionDerivative approximatedFunctionSampleId(String value) {
        this.approximatedFunctionSampleId = value
        return this;
    }

    ApproximatedFunctionDerivative purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    ApproximatedFunctionDerivative derivativeOrder(Long value) {
        this.derivativeOrder = value
        return this;
    }

    ApproximatedFunctionDerivative derivativeMatrixId(String value) {
        this.derivativeMatrixId = value
        return this;
    }

    ApproximatedFunctionDerivative derivativeTensorId(String value) {
        this.derivativeTensorId = value
        return this;
    }

    ApproximatedFunctionDerivative derivativeVectorId(String value) {
        this.derivativeVectorId = value
        return this;
    }

    ApproximatedFunctionDerivative errorVectorId(String value) {
        this.errorVectorId = value
        return this;
    }

    ApproximatedFunctionDerivative approxFuncSample(ApproximatedFunctionSample item) {
        this.approxFuncSample = item;
        return this;
    }

    ApproximatedFunctionDerivative derivativeMatrix(Matrix item) {
        this.derivativeMatrix = item;
        return this;
    }

    ApproximatedFunctionDerivative derivativeTensor(Tensor item) {
        this.derivativeTensor = item;
        return this;
    }

    ApproximatedFunctionDerivative derivativeVector(Vector item) {
        this.derivativeVector = item;
        return this;
    }

    ApproximatedFunctionDerivative errorVector(Vector item) {
        this.errorVector = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.approximatedFunctionDerivativeId != null) map.put('approximatedFunctionDerivativeId', this.approximatedFunctionDerivativeId);
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.approximatedFunctionSampleId != null) map.put('approximatedFunctionSampleId', this.approximatedFunctionSampleId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.derivativeOrder != null) map.put('derivativeOrder', this.derivativeOrder);
        if (this.derivativeMatrixId != null) map.put('derivativeMatrixId', this.derivativeMatrixId);
        if (this.derivativeTensorId != null) map.put('derivativeTensorId', this.derivativeTensorId);
        if (this.derivativeVectorId != null) map.put('derivativeVectorId', this.derivativeVectorId);
        if (this.errorVectorId != null) map.put('errorVectorId', this.errorVectorId);
        return map;
    }
}