/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ParametricPath
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.ParametricPath

@CompileStatic
class ParametricPath_ {
    public static final String ENTITY_NAME = 'ParametricPath'
    public static final String FULL_NAME = 'moqui.math.ParametricPath'

    public static final Attribute<ParametricPath, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', ParametricPath.class, String.class, true, true)
    public static final Attribute<ParametricPath, String> parentPathId = new Attribute<>('parentPathId', ParametricPath.class, String.class, false, false)
    public static final Attribute<ParametricPath, String> profileEnumId = new Attribute<>('profileEnumId', ParametricPath.class, String.class, false, false)
    public static final Attribute<ParametricPath, String> isClosed = new Attribute<>('isClosed', ParametricPath.class, String.class, false, false)
    public static final Attribute<ParametricPath, String> coordinateSystemId = new Attribute<>('coordinateSystemId', ParametricPath.class, String.class, false, false)
    public static final Attribute<ParametricPath, String> coordTransformationId = new Attribute<>('coordTransformationId', ParametricPath.class, String.class, false, false)
    public static final Attribute<ParametricPath, String> compositionMethodEnumId = new Attribute<>('compositionMethodEnumId', ParametricPath.class, String.class, false, false)
    public static final Attribute<ParametricPath, Long> compositionSequenceNum = new Attribute<>('compositionSequenceNum', ParametricPath.class, Long.class, false, false)
    public static final Attribute<ParametricPath, BigDecimal> totalLength = new Attribute<>('totalLength', ParametricPath.class, BigDecimal.class, false, false)
    public static final Attribute<ParametricPath, String> boundingBoxMinVectorId = new Attribute<>('boundingBoxMinVectorId', ParametricPath.class, String.class, false, false)
    public static final Attribute<ParametricPath, String> boundingBoxMaxVectorId = new Attribute<>('boundingBoxMaxVectorId', ParametricPath.class, String.class, false, false)
}
