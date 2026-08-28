/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ApproximatedFunctionSample
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['approximatedFunctionId', 'approximatedFunctionSampleId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ApproximatedFunctionSample implements Serializable {
    private static final long serialVersionUID = 1L

    String approximatedFunctionId
    String approximatedFunctionSampleId
    String sampleTypeEnumId
    String sampleCode // Required
    String sampleName
    String sampleAlias
    String sampleLabel
    Long sequenceNum // Required
    String sampleVectorId
    String sampleMatrixId

    // --- Relationships (In-Memory Navigation) ---
    ApproximatedFunction approxFunc
    Object type
    Vector sampleVector
    List<ApproximatedFunctionDerivative> derivatives = []
    ParametricPathPoint parametricPathPoint

    ApproximatedFunctionSample() { }

    ApproximatedFunctionSample(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('approximatedFunctionSampleId')) this.approximatedFunctionSampleId = args.get('approximatedFunctionSampleId') as String
            if (args.containsKey('sampleTypeEnumId')) this.sampleTypeEnumId = args.get('sampleTypeEnumId') as String
            if (args.containsKey('sampleCode')) this.sampleCode = args.get('sampleCode') as String
            if (args.containsKey('sampleName')) this.sampleName = args.get('sampleName') as String
            if (args.containsKey('sampleAlias')) this.sampleAlias = args.get('sampleAlias') as String
            if (args.containsKey('sampleLabel')) this.sampleLabel = args.get('sampleLabel') as String
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') as Long
            if (args.containsKey('sampleVectorId')) this.sampleVectorId = args.get('sampleVectorId') as String
            if (args.containsKey('sampleMatrixId')) this.sampleMatrixId = args.get('sampleMatrixId') as String
            if (args.containsKey('approxFunc')) this.approxFunc = args.get('approxFunc') as ApproximatedFunction
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('sampleVector')) this.sampleVector = args.get('sampleVector') as Vector
            if (args.containsKey('derivatives')) this.derivatives = args.get('derivatives') as List<ApproximatedFunctionDerivative>
            if (args.containsKey('parametricPathPoint')) this.parametricPathPoint = args.get('parametricPathPoint') as ParametricPathPoint
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.sampleCode == null) throw new IllegalStateException("Required property missing: ApproximatedFunctionSample.sampleCode")
        if (this.sequenceNum == null) throw new IllegalStateException("Required property missing: ApproximatedFunctionSample.sequenceNum")
    }

    /**
     * Gradle-style closure configurator
     */
    ApproximatedFunctionSample configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ApproximatedFunctionSample) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    ApproximatedFunction approxFunc(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ApproximatedFunction) Closure<?> action) {
        if (this.approxFunc == null) this.approxFunc = new ApproximatedFunction()
        this.approxFunc.configure(action)
        this.approxFunc
    }

    Vector sampleVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.sampleVector == null) this.sampleVector = new Vector()
        this.sampleVector.configure(action)
        this.sampleVector
    }

    ApproximatedFunctionDerivative derivatives(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ApproximatedFunctionDerivative) Closure<?> action) {
        ApproximatedFunctionDerivative item = new ApproximatedFunctionDerivative()
        item.configure(action)
        if (this.derivatives == null) this.derivatives = []
        this.derivatives.add(item)
        item
    }

    ParametricPathPoint parametricPathPoint(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPathPoint) Closure<?> action) {
        if (this.parametricPathPoint == null) this.parametricPathPoint = new ParametricPathPoint()
        this.parametricPathPoint.configure(action)
        this.parametricPathPoint
    }
}
