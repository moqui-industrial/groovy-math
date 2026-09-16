/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelDefPipeline
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['mathModelDefId', 'stepSeqId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class MathModelDefPipeline implements Serializable {
    private static final long serialVersionUID = 1L

    /** mathModelDefId */
    String mathModelDefId

    /** stepSeqId */
    String stepSeqId

    /** stepName */
    String stepName

    /** sequenceNum */
    Long sequenceNum

    /** transformationId */
    String transformationId

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** solvingMethodEnumId */
    String solvingMethodEnumId

    /** interpolationEnumId */
    String interpolationEnumId

    /** basisFunctionEnumId */
    String basisFunctionEnumId

    /** basisOrder */
    Long basisOrder

    MathModelDef modelDef

    Transformation transformation

    ApproximatedFunction approxFunc

    MathModelDefPipeline() {}

    MathModelDefPipeline(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('mathModelDefId')) this.mathModelDefId = args.get('mathModelDefId')?.toString()
            if (args.containsKey('stepSeqId')) this.stepSeqId = args.get('stepSeqId')?.toString()
            if (args.containsKey('stepName')) this.stepName = args.get('stepName')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('solvingMethodEnumId')) this.solvingMethodEnumId = args.get('solvingMethodEnumId')?.toString()
            if (args.containsKey('interpolationEnumId')) this.interpolationEnumId = args.get('interpolationEnumId')?.toString()
            if (args.containsKey('basisFunctionEnumId')) this.basisFunctionEnumId = args.get('basisFunctionEnumId')?.toString()
            if (args.containsKey('basisOrder')) this.basisOrder = args.get('basisOrder') != null ? ((Number) args.get('basisOrder')).longValue() : null
        }
    }

    MathModelDefPipeline mathModelDefId(String value) {
        this.mathModelDefId = value
        return this;
    }

    MathModelDefPipeline stepSeqId(String value) {
        this.stepSeqId = value
        return this;
    }

    MathModelDefPipeline stepName(String value) {
        this.stepName = value
        return this;
    }

    MathModelDefPipeline sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    MathModelDefPipeline transformationId(String value) {
        this.transformationId = value
        return this;
    }

    MathModelDefPipeline approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    MathModelDefPipeline solvingMethodEnumId(String value) {
        this.solvingMethodEnumId = value
        return this;
    }

    MathModelDefPipeline interpolationEnumId(String value) {
        this.interpolationEnumId = value
        return this;
    }

    MathModelDefPipeline basisFunctionEnumId(String value) {
        this.basisFunctionEnumId = value
        return this;
    }

    MathModelDefPipeline basisOrder(Long value) {
        this.basisOrder = value
        return this;
    }

    MathModelDefPipeline modelDef(MathModelDef item) {
        this.modelDef = item;
        return this;
    }

    MathModelDefPipeline transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    MathModelDefPipeline approxFunc(ApproximatedFunction item) {
        this.approxFunc = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.mathModelDefId != null) map.put('mathModelDefId', this.mathModelDefId);
        if (this.stepSeqId != null) map.put('stepSeqId', this.stepSeqId);
        if (this.stepName != null) map.put('stepName', this.stepName);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.solvingMethodEnumId != null) map.put('solvingMethodEnumId', this.solvingMethodEnumId);
        if (this.interpolationEnumId != null) map.put('interpolationEnumId', this.interpolationEnumId);
        if (this.basisFunctionEnumId != null) map.put('basisFunctionEnumId', this.basisFunctionEnumId);
        if (this.basisOrder != null) map.put('basisOrder', this.basisOrder);
        return map;
    }
}