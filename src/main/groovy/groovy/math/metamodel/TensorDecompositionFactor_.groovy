/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.TensorDecompositionFactor
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.TensorDecompositionFactor

@CompileStatic
class TensorDecompositionFactor_ {
    public static final String ENTITY_NAME = 'TensorDecompositionFactor'
    public static final String FULL_NAME = 'moqui.math.TensorDecompositionFactor'

    public static final Attribute<TensorDecompositionFactor, String> transformationId = new Attribute<>('transformationId', TensorDecompositionFactor.class, String.class, true, true)
    public static final Attribute<TensorDecompositionFactor, Long> modeIndex = new Attribute<>('modeIndex', TensorDecompositionFactor.class, Long.class, true, true)
    public static final Attribute<TensorDecompositionFactor, String> factorMatrixId = new Attribute<>('factorMatrixId', TensorDecompositionFactor.class, String.class, false, false)
}
