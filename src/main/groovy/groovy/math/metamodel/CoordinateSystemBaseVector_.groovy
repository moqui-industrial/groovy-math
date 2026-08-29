/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.CoordinateSystemBaseVector
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.CoordinateSystemBaseVector

@CompileStatic
class CoordinateSystemBaseVector_ {
    public static final String ENTITY_NAME = 'CoordinateSystemBaseVector'
    public static final String FULL_NAME = 'moqui.math.CoordinateSystemBaseVector'

    public static final Attribute<CoordinateSystemBaseVector, String> coordinateSystemId = new Attribute<>('coordinateSystemId', CoordinateSystemBaseVector.class, String.class, true, true)
    public static final Attribute<CoordinateSystemBaseVector, String> vectorId = new Attribute<>('vectorId', CoordinateSystemBaseVector.class, String.class, true, true)
    public static final Attribute<CoordinateSystemBaseVector, String> vectorPurposeEnumId = new Attribute<>('vectorPurposeEnumId', CoordinateSystemBaseVector.class, String.class, false, false)
    public static final Attribute<CoordinateSystemBaseVector, Long> baseIndex = new Attribute<>('baseIndex', CoordinateSystemBaseVector.class, Long.class, false, true)
    public static final Attribute<CoordinateSystemBaseVector, String> dimensionEnumId = new Attribute<>('dimensionEnumId', CoordinateSystemBaseVector.class, String.class, false, false)
    public static final Attribute<CoordinateSystemBaseVector, String> axisName = new Attribute<>('axisName', CoordinateSystemBaseVector.class, String.class, false, false)
}
