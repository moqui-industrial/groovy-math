/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.CoordinateSystemTransformation
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.CoordinateSystemTransformation

@CompileStatic
class CoordinateSystemTransformation_ {
    public static final String ENTITY_NAME = 'CoordinateSystemTransformation'
    public static final String FULL_NAME = 'moqui.math.CoordinateSystemTransformation'

    public static final Attribute<CoordinateSystemTransformation, String> transformationId = new Attribute<>('transformationId', CoordinateSystemTransformation.class, String.class, true, true)
    public static final Attribute<CoordinateSystemTransformation, String> sourceCoordinateSystemId = new Attribute<>('sourceCoordinateSystemId', CoordinateSystemTransformation.class, String.class, false, true)
    public static final Attribute<CoordinateSystemTransformation, String> targetCoordinateSystemId = new Attribute<>('targetCoordinateSystemId', CoordinateSystemTransformation.class, String.class, false, true)
    public static final Attribute<CoordinateSystemTransformation, String> matrixId = new Attribute<>('matrixId', CoordinateSystemTransformation.class, String.class, false, true)
}
