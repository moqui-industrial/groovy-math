/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ParametricPathPoint
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.ParametricPathPoint

@CompileStatic
class ParametricPathPoint_ {
    public static final String ENTITY_NAME = 'ParametricPathPoint'
    public static final String FULL_NAME = 'moqui.math.ParametricPathPoint'

    public static final Attribute<ParametricPathPoint, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', ParametricPathPoint.class, String.class, true, true)
    public static final Attribute<ParametricPathPoint, String> approximatedFunctionSampleId = new Attribute<>('approximatedFunctionSampleId', ParametricPathPoint.class, String.class, true, true)
    public static final Attribute<ParametricPathPoint, String> isCriticalPoint = new Attribute<>('isCriticalPoint', ParametricPathPoint.class, String.class, false, false)
    public static final Attribute<ParametricPathPoint, BigDecimal> tolerance = new Attribute<>('tolerance', ParametricPathPoint.class, BigDecimal.class, false, false)
    public static final Attribute<ParametricPathPoint, BigDecimal> arcLength = new Attribute<>('arcLength', ParametricPathPoint.class, BigDecimal.class, false, false)
    public static final Attribute<ParametricPathPoint, BigDecimal> weight = new Attribute<>('weight', ParametricPathPoint.class, BigDecimal.class, false, false)
}
