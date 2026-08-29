/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.TensorDecomposition
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.TensorDecomposition

@CompileStatic
class TensorDecomposition_ {
    public static final String ENTITY_NAME = 'TensorDecomposition'
    public static final String FULL_NAME = 'moqui.math.TensorDecomposition'

    public static final Attribute<TensorDecomposition, String> transformationId = new Attribute<>('transformationId', TensorDecomposition.class, String.class, true, true)
    public static final Attribute<TensorDecomposition, String> decompositionMethodEnumId = new Attribute<>('decompositionMethodEnumId', TensorDecomposition.class, String.class, false, true)
    public static final Attribute<TensorDecomposition, String> coreTensorId = new Attribute<>('coreTensorId', TensorDecomposition.class, String.class, false, false)
    public static final Attribute<TensorDecomposition, String> sourceTensorId = new Attribute<>('sourceTensorId', TensorDecomposition.class, String.class, false, true)
    public static final Attribute<TensorDecomposition, Long> targetRank = new Attribute<>('targetRank', TensorDecomposition.class, Long.class, false, false)
    public static final Attribute<TensorDecomposition, BigDecimal> fitError = new Attribute<>('fitError', TensorDecomposition.class, BigDecimal.class, false, false)
    public static final Attribute<TensorDecomposition, BigDecimal> explainedVariance = new Attribute<>('explainedVariance', TensorDecomposition.class, BigDecimal.class, false, false)
    public static final Attribute<TensorDecomposition, String> description = new Attribute<>('description', TensorDecomposition.class, String.class, false, false)
}
