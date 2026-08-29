/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.TriangularExtraction
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.TriangularExtraction

@CompileStatic
class TriangularExtraction_ {
    public static final String ENTITY_NAME = 'TriangularExtraction'
    public static final String FULL_NAME = 'moqui.math.TriangularExtraction'

    public static final Attribute<TriangularExtraction, String> transformationId = new Attribute<>('transformationId', TriangularExtraction.class, String.class, true, true)
    public static final Attribute<TriangularExtraction, String> extractionTypeEnumId = new Attribute<>('extractionTypeEnumId', TriangularExtraction.class, String.class, false, true)
    public static final Attribute<TriangularExtraction, Long> extractionOffset = new Attribute<>('extractionOffset', TriangularExtraction.class, Long.class, false, false)
}
