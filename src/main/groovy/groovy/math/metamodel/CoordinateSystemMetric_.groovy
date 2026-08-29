/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.CoordinateSystemMetric
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.CoordinateSystemMetric

@CompileStatic
class CoordinateSystemMetric_ {
    public static final String ENTITY_NAME = 'CoordinateSystemMetric'
    public static final String FULL_NAME = 'moqui.math.CoordinateSystemMetric'

    public static final Attribute<CoordinateSystemMetric, String> coordinateSystemId = new Attribute<>('coordinateSystemId', CoordinateSystemMetric.class, String.class, true, true)
    public static final Attribute<CoordinateSystemMetric, String> gramMatrixId = new Attribute<>('gramMatrixId', CoordinateSystemMetric.class, String.class, false, true)
}
