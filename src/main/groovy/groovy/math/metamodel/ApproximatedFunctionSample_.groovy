/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ApproximatedFunctionSample
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.ApproximatedFunctionSample

@CompileStatic
class ApproximatedFunctionSample_ {
    public static final String ENTITY_NAME = 'ApproximatedFunctionSample'
    public static final String FULL_NAME = 'moqui.math.ApproximatedFunctionSample'

    public static final Attribute<ApproximatedFunctionSample, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', ApproximatedFunctionSample.class, String.class, true, true)
    public static final Attribute<ApproximatedFunctionSample, String> approximatedFunctionSampleId = new Attribute<>('approximatedFunctionSampleId', ApproximatedFunctionSample.class, String.class, true, true)
    public static final Attribute<ApproximatedFunctionSample, String> sampleTypeEnumId = new Attribute<>('sampleTypeEnumId', ApproximatedFunctionSample.class, String.class, false, false)
    public static final Attribute<ApproximatedFunctionSample, String> sampleCode = new Attribute<>('sampleCode', ApproximatedFunctionSample.class, String.class, false, true)
    public static final Attribute<ApproximatedFunctionSample, String> sampleName = new Attribute<>('sampleName', ApproximatedFunctionSample.class, String.class, false, false)
    public static final Attribute<ApproximatedFunctionSample, String> sampleAlias = new Attribute<>('sampleAlias', ApproximatedFunctionSample.class, String.class, false, false)
    public static final Attribute<ApproximatedFunctionSample, String> sampleLabel = new Attribute<>('sampleLabel', ApproximatedFunctionSample.class, String.class, false, false)
    public static final Attribute<ApproximatedFunctionSample, Long> sequenceNum = new Attribute<>('sequenceNum', ApproximatedFunctionSample.class, Long.class, false, true)
    public static final Attribute<ApproximatedFunctionSample, String> sampleVectorId = new Attribute<>('sampleVectorId', ApproximatedFunctionSample.class, String.class, false, false)
    public static final Attribute<ApproximatedFunctionSample, String> sampleMatrixId = new Attribute<>('sampleMatrixId', ApproximatedFunctionSample.class, String.class, false, false)
}
