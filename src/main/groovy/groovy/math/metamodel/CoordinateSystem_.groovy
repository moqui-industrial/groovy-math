/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.CoordinateSystem
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.CoordinateSystem

@CompileStatic
class CoordinateSystem_ {
    public static final String ENTITY_NAME = 'CoordinateSystem'
    public static final String FULL_NAME = 'moqui.math.CoordinateSystem'

    public static final Attribute<CoordinateSystem, String> coordinateSystemId = new Attribute<>('coordinateSystemId', CoordinateSystem.class, String.class, true, true)
    public static final Attribute<CoordinateSystem, String> parentSystemId = new Attribute<>('parentSystemId', CoordinateSystem.class, String.class, false, false)
    public static final Attribute<CoordinateSystem, String> transformationToParentSystemId = new Attribute<>('transformationToParentSystemId', CoordinateSystem.class, String.class, false, false)
    public static final Attribute<CoordinateSystem, String> vectorSpaceEnumId = new Attribute<>('vectorSpaceEnumId', CoordinateSystem.class, String.class, false, true)
    public static final Attribute<CoordinateSystem, String> coordinateSystemTypeEnumId = new Attribute<>('coordinateSystemTypeEnumId', CoordinateSystem.class, String.class, false, false)
    public static final Attribute<CoordinateSystem, String> purposeEnumId = new Attribute<>('purposeEnumId', CoordinateSystem.class, String.class, false, false)
    public static final Attribute<CoordinateSystem, String> name = new Attribute<>('name', CoordinateSystem.class, String.class, false, true)
    public static final Attribute<CoordinateSystem, String> symbol = new Attribute<>('symbol', CoordinateSystem.class, String.class, false, false)
    public static final Attribute<CoordinateSystem, String> description = new Attribute<>('description', CoordinateSystem.class, String.class, false, false)
    public static final Attribute<CoordinateSystem, String> originVectorId = new Attribute<>('originVectorId', CoordinateSystem.class, String.class, false, false)
}
