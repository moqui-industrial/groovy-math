/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.TrajectoryPointRun
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.TrajectoryPointRun

@CompileStatic
class TrajectoryPointRun_ {
    public static final String ENTITY_NAME = 'TrajectoryPointRun'
    public static final String FULL_NAME = 'moqui.math.TrajectoryPointRun'

    public static final Attribute<TrajectoryPointRun, String> trajectoryPointRunId = new Attribute<>('trajectoryPointRunId', TrajectoryPointRun.class, String.class, true, true)
    public static final Attribute<TrajectoryPointRun, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', TrajectoryPointRun.class, String.class, false, true)
    public static final Attribute<TrajectoryPointRun, String> approximatedFunctionSampleId = new Attribute<>('approximatedFunctionSampleId', TrajectoryPointRun.class, String.class, false, true)
    public static final Attribute<TrajectoryPointRun, String> actualPointVectorId = new Attribute<>('actualPointVectorId', TrajectoryPointRun.class, String.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> estimatedTimeOffsetMillis = new Attribute<>('estimatedTimeOffsetMillis', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> actualTimeOffsetMillis = new Attribute<>('actualTimeOffsetMillis', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> timeDeviationMillis = new Attribute<>('timeDeviationMillis', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> plannedBreakDuration = new Attribute<>('plannedBreakDuration', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> actualBreakDuration = new Attribute<>('actualBreakDuration', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> breakDeviation = new Attribute<>('breakDeviation', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> positionError = new Attribute<>('positionError', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> velocityError = new Attribute<>('velocityError', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> accelerationError = new Attribute<>('accelerationError', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> jerkError = new Attribute<>('jerkError', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> snapError = new Attribute<>('snapError', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> compositeError = new Attribute<>('compositeError', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> positionRelativeError = new Attribute<>('positionRelativeError', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> velocityRelativeError = new Attribute<>('velocityRelativeError', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> accelerationRelativeError = new Attribute<>('accelerationRelativeError', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, String> hasPointExecutionFailure = new Attribute<>('hasPointExecutionFailure', TrajectoryPointRun.class, String.class, false, false)
    public static final Attribute<TrajectoryPointRun, String> hasPointExecutionDeviation = new Attribute<>('hasPointExecutionDeviation', TrajectoryPointRun.class, String.class, false, false)
    public static final Attribute<TrajectoryPointRun, String> executionStatusEnumId = new Attribute<>('executionStatusEnumId', TrajectoryPointRun.class, String.class, false, false)
    public static final Attribute<TrajectoryPointRun, String> errorCauseEnumId = new Attribute<>('errorCauseEnumId', TrajectoryPointRun.class, String.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> vibrationLevel = new Attribute<>('vibrationLevel', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> maxPowerDraw = new Attribute<>('maxPowerDraw', TrajectoryPointRun.class, BigDecimal.class, false, false)
    public static final Attribute<TrajectoryPointRun, BigDecimal> maxTemperature = new Attribute<>('maxTemperature', TrajectoryPointRun.class, BigDecimal.class, false, false)
}
