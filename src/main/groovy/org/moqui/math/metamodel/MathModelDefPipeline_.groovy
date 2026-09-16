/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MathModelDefPipeline
 * JPA Criteria-style Metamodel Descriptor
 */
package org.moqui.math.metamodel

import groovy.transform.CompileStatic
import org.moqui.math.model.MathModelDefPipeline

@CompileStatic
class MathModelDefPipeline_ {
    public static final String ENTITY_NAME = 'MathModelDefPipeline'
    public static final String FULL_NAME = 'moqui.math.MathModelDefPipeline'

    public static final Attribute<MathModelDefPipeline, String> mathModelDefId = new Attribute<>('mathModelDefId', MathModelDefPipeline.class, String.class, true, true)
    public static final Attribute<MathModelDefPipeline, String> stepSeqId = new Attribute<>('stepSeqId', MathModelDefPipeline.class, String.class, true, true)
    public static final Attribute<MathModelDefPipeline, String> stepName = new Attribute<>('stepName', MathModelDefPipeline.class, String.class, false, false)
    public static final Attribute<MathModelDefPipeline, Long> sequenceNum = new Attribute<>('sequenceNum', MathModelDefPipeline.class, Long.class, false, true)
    public static final Attribute<MathModelDefPipeline, String> transformationId = new Attribute<>('transformationId', MathModelDefPipeline.class, String.class, false, false)
    public static final Attribute<MathModelDefPipeline, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', MathModelDefPipeline.class, String.class, false, false)
    public static final Attribute<MathModelDefPipeline, String> solvingMethodEnumId = new Attribute<>('solvingMethodEnumId', MathModelDefPipeline.class, String.class, false, false)
    public static final Attribute<MathModelDefPipeline, String> interpolationEnumId = new Attribute<>('interpolationEnumId', MathModelDefPipeline.class, String.class, false, false)
    public static final Attribute<MathModelDefPipeline, String> basisFunctionEnumId = new Attribute<>('basisFunctionEnumId', MathModelDefPipeline.class, String.class, false, false)
    public static final Attribute<MathModelDefPipeline, Long> basisOrder = new Attribute<>('basisOrder', MathModelDefPipeline.class, Long.class, false, false)
}
