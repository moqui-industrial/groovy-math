/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.Vector
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Vector

@CompileStatic
class Vector_ {
    public static final String ENTITY_NAME = 'Vector'
    public static final String FULL_NAME = 'moqui.math.Vector'

    public static final Attribute<Vector, String> vectorId = new Attribute<>('vectorId', Vector.class, String.class, true, true)
    public static final Attribute<Vector, String> parentVectorId = new Attribute<>('parentVectorId', Vector.class, String.class, false, false)
    public static final Attribute<Vector, String> vectorTypeEnumId = new Attribute<>('vectorTypeEnumId', Vector.class, String.class, false, false)
    public static final Attribute<Vector, String> purposeEnumId = new Attribute<>('purposeEnumId', Vector.class, String.class, false, false)
    public static final Attribute<Vector, String> vectorSpaceEnumId = new Attribute<>('vectorSpaceEnumId', Vector.class, String.class, false, false)
    public static final Attribute<Vector, String> coordinateSystemId = new Attribute<>('coordinateSystemId', Vector.class, String.class, false, false)
    public static final Attribute<Vector, String> name = new Attribute<>('name', Vector.class, String.class, false, false)
    public static final Attribute<Vector, String> symbol = new Attribute<>('symbol', Vector.class, String.class, false, false)
    public static final Attribute<Vector, String> description = new Attribute<>('description', Vector.class, String.class, false, false)
    public static final Attribute<Vector, Long> dimension = new Attribute<>('dimension', Vector.class, Long.class, false, false)
    public static final Attribute<Vector, BigDecimal> magnitude = new Attribute<>('magnitude', Vector.class, BigDecimal.class, false, false)
    public static final Attribute<Vector, String> componentArray = new Attribute<>('componentArray', Vector.class, String.class, false, false)
    public static final Attribute<Vector, byte[]> componentBlob = new Attribute<>('componentBlob', Vector.class, byte[].class, false, false)
}
