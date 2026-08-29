/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.BlockMatrixExtraction
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.BlockMatrixExtraction

@CompileStatic
class BlockMatrixExtraction_ {
    public static final String ENTITY_NAME = 'BlockMatrixExtraction'
    public static final String FULL_NAME = 'moqui.math.BlockMatrixExtraction'

    public static final Attribute<BlockMatrixExtraction, String> transformationId = new Attribute<>('transformationId', BlockMatrixExtraction.class, String.class, true, true)
    public static final Attribute<BlockMatrixExtraction, String> blockLabel = new Attribute<>('blockLabel', BlockMatrixExtraction.class, String.class, false, false)
    public static final Attribute<BlockMatrixExtraction, Long> startRowBlock = new Attribute<>('startRowBlock', BlockMatrixExtraction.class, Long.class, false, false)
    public static final Attribute<BlockMatrixExtraction, Long> endRowBlock = new Attribute<>('endRowBlock', BlockMatrixExtraction.class, Long.class, false, false)
    public static final Attribute<BlockMatrixExtraction, Long> startColBlock = new Attribute<>('startColBlock', BlockMatrixExtraction.class, Long.class, false, false)
    public static final Attribute<BlockMatrixExtraction, Long> endColBlock = new Attribute<>('endColBlock', BlockMatrixExtraction.class, Long.class, false, false)
}
