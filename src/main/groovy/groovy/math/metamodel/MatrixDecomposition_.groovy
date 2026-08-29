/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MatrixDecomposition
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MatrixDecomposition

@CompileStatic
class MatrixDecomposition_ {
    public static final String ENTITY_NAME = 'MatrixDecomposition'
    public static final String FULL_NAME = 'moqui.math.MatrixDecomposition'

    public static final Attribute<MatrixDecomposition, String> transformationId = new Attribute<>('transformationId', MatrixDecomposition.class, String.class, true, true)
    public static final Attribute<MatrixDecomposition, String> leftMatrixId = new Attribute<>('leftMatrixId', MatrixDecomposition.class, String.class, false, false)
    public static final Attribute<MatrixDecomposition, String> diagMatrixId = new Attribute<>('diagMatrixId', MatrixDecomposition.class, String.class, false, false)
    public static final Attribute<MatrixDecomposition, String> rightMatrixId = new Attribute<>('rightMatrixId', MatrixDecomposition.class, String.class, false, false)
    public static final Attribute<MatrixDecomposition, Long> rankApproximation = new Attribute<>('rankApproximation', MatrixDecomposition.class, Long.class, false, false)
    public static final Attribute<MatrixDecomposition, BigDecimal> explainedVariance = new Attribute<>('explainedVariance', MatrixDecomposition.class, BigDecimal.class, false, false)
    public static final Attribute<MatrixDecomposition, BigDecimal> fitError = new Attribute<>('fitError', MatrixDecomposition.class, BigDecimal.class, false, false)
}
