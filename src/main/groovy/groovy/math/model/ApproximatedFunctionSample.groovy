/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ApproximatedFunctionSample
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['approximatedFunctionId', 'approximatedFunctionSampleId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class ApproximatedFunctionSample implements Serializable {
    private static final long serialVersionUID = 1L

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** approximatedFunctionSampleId */
    String approximatedFunctionSampleId

    /** sampleTypeEnumId */
    String sampleTypeEnumId

    /** sampleCode */
    String sampleCode

    /** sampleName */
    String sampleName

    /** sampleAlias */
    String sampleAlias

    /** sampleLabel */
    String sampleLabel

    /** sequenceNum */
    Long sequenceNum

    /** sampleVectorId */
    String sampleVectorId

    /** sampleMatrixId */
    String sampleMatrixId

    ApproximatedFunction approxFunc

    Vector sampleVector

    List<ApproximatedFunctionDerivative> derivatives = new ArrayList<>()

    ParametricPathPoint parametricPathPoint

    ApproximatedFunctionSample() {}

    ApproximatedFunctionSample(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('approximatedFunctionSampleId')) this.approximatedFunctionSampleId = args.get('approximatedFunctionSampleId')?.toString()
            if (args.containsKey('sampleTypeEnumId')) this.sampleTypeEnumId = args.get('sampleTypeEnumId')?.toString()
            if (args.containsKey('sampleCode')) this.sampleCode = args.get('sampleCode')?.toString()
            if (args.containsKey('sampleName')) this.sampleName = args.get('sampleName')?.toString()
            if (args.containsKey('sampleAlias')) this.sampleAlias = args.get('sampleAlias')?.toString()
            if (args.containsKey('sampleLabel')) this.sampleLabel = args.get('sampleLabel')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('sampleVectorId')) this.sampleVectorId = args.get('sampleVectorId')?.toString()
            if (args.containsKey('sampleMatrixId')) this.sampleMatrixId = args.get('sampleMatrixId')?.toString()
        }
    }

    ApproximatedFunctionSample approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    ApproximatedFunctionSample approximatedFunctionSampleId(String value) {
        this.approximatedFunctionSampleId = value
        return this;
    }

    ApproximatedFunctionSample sampleTypeEnumId(String value) {
        this.sampleTypeEnumId = value
        return this;
    }

    ApproximatedFunctionSample sampleCode(String value) {
        this.sampleCode = value
        return this;
    }

    ApproximatedFunctionSample sampleName(String value) {
        this.sampleName = value
        return this;
    }

    ApproximatedFunctionSample sampleAlias(String value) {
        this.sampleAlias = value
        return this;
    }

    ApproximatedFunctionSample sampleLabel(String value) {
        this.sampleLabel = value
        return this;
    }

    ApproximatedFunctionSample sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    ApproximatedFunctionSample sampleVectorId(String value) {
        this.sampleVectorId = value
        return this;
    }

    ApproximatedFunctionSample sampleMatrixId(String value) {
        this.sampleMatrixId = value
        return this;
    }

    ApproximatedFunctionSample approxFunc(ApproximatedFunction item) {
        this.approxFunc = item;
        return this;
    }

    ApproximatedFunctionSample sampleVector(Vector item) {
        this.sampleVector = item;
        return this;
    }

    ApproximatedFunctionSample derivatives(List<ApproximatedFunctionDerivative> list) {
        this.derivatives = list;
        return this;
    }

    ApproximatedFunctionSample parametricPathPoint(ParametricPathPoint item) {
        this.parametricPathPoint = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.approximatedFunctionSampleId != null) map.put('approximatedFunctionSampleId', this.approximatedFunctionSampleId);
        if (this.sampleTypeEnumId != null) map.put('sampleTypeEnumId', this.sampleTypeEnumId);
        if (this.sampleCode != null) map.put('sampleCode', this.sampleCode);
        if (this.sampleName != null) map.put('sampleName', this.sampleName);
        if (this.sampleAlias != null) map.put('sampleAlias', this.sampleAlias);
        if (this.sampleLabel != null) map.put('sampleLabel', this.sampleLabel);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.sampleVectorId != null) map.put('sampleVectorId', this.sampleVectorId);
        if (this.sampleMatrixId != null) map.put('sampleMatrixId', this.sampleMatrixId);
        return map;
    }
}