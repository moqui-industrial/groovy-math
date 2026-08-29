/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.TrajectoryPoint
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.TrajectoryPoint

@CompileStatic
class TrajectoryPoint_ {
    public static final String ENTITY_NAME = 'TrajectoryPoint'
    public static final String FULL_NAME = 'moqui.math.TrajectoryPoint'

    public static final Attribute<TrajectoryPoint, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', TrajectoryPoint.class, String.class, true, true)
    public static final Attribute<TrajectoryPoint, String> approximatedFunctionSampleId = new Attribute<>('approximatedFunctionSampleId', TrajectoryPoint.class, String.class, true, true)
    public static final Attribute<TrajectoryPoint, String> isBreakPoint = new Attribute<>('isBreakPoint', TrajectoryPoint.class, String.class, false, false)
    public static final Attribute<TrajectoryPoint, BigDecimal> breakDuration = new Attribute<>('breakDuration', TrajectoryPoint.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPoint, String> breakReason = new Attribute<>('breakReason', TrajectoryPoint.class, String.class, false, false)
    public static final Attribute<TrajectoryPoint, String> blendingEnumId = new Attribute<>('blendingEnumId', TrajectoryPoint.class, String.class, false, false)
    public static final Attribute<TrajectoryPoint, BigDecimal> pointTimeOffsetMillis = new Attribute<>('pointTimeOffsetMillis', TrajectoryPoint.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPoint, String> velocityVectorId = new Attribute<>('velocityVectorId', TrajectoryPoint.class, String.class, false, false)
    public static final Attribute<TrajectoryPoint, String> accelerationVectorId = new Attribute<>('accelerationVectorId', TrajectoryPoint.class, String.class, false, false)
    public static final Attribute<TrajectoryPoint, String> jerkVectorId = new Attribute<>('jerkVectorId', TrajectoryPoint.class, String.class, false, false)
    public static final Attribute<TrajectoryPoint, String> snapVectorId = new Attribute<>('snapVectorId', TrajectoryPoint.class, String.class, false, false)
}
