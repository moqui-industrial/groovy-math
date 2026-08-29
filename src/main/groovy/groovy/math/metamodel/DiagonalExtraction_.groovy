/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.DiagonalExtraction
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.DiagonalExtraction

@CompileStatic
class DiagonalExtraction_ {
    public static final String ENTITY_NAME = 'DiagonalExtraction'
    public static final String FULL_NAME = 'moqui.math.DiagonalExtraction'

    public static final Attribute<DiagonalExtraction, String> transformationId = new Attribute<>('transformationId', DiagonalExtraction.class, String.class, true, true)
    public static final Attribute<DiagonalExtraction, Long> axis1 = new Attribute<>('axis1', DiagonalExtraction.class, Long.class, false, true)
    public static final Attribute<DiagonalExtraction, Long> axis2 = new Attribute<>('axis2', DiagonalExtraction.class, Long.class, false, true)
    public static final Attribute<DiagonalExtraction, Long> axisOffset = new Attribute<>('axisOffset', DiagonalExtraction.class, Long.class, false, false)
}
