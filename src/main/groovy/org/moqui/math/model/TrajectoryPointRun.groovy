/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TrajectoryPointRun
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['trajectoryPointRunId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class TrajectoryPointRun implements Serializable {
    private static final long serialVersionUID = 1L

    String trajectoryPointRunId
    String approximatedFunctionId // Required
    String approximatedFunctionSampleId // Required
    String actualPointVectorId
    BigDecimal estimatedTimeOffsetMillis
    BigDecimal actualTimeOffsetMillis
    BigDecimal timeDeviationMillis
    BigDecimal plannedBreakDuration
    BigDecimal actualBreakDuration
    BigDecimal breakDeviation
    BigDecimal positionError
    BigDecimal velocityError
    BigDecimal accelerationError
    BigDecimal jerkError
    BigDecimal snapError
    BigDecimal compositeError
    BigDecimal positionRelativeError
    BigDecimal velocityRelativeError
    BigDecimal accelerationRelativeError
    String hasPointExecutionFailure
    String hasPointExecutionDeviation
    String executionStatusEnumId
    String errorCauseEnumId
    BigDecimal vibrationLevel
    BigDecimal maxPowerDraw
    BigDecimal maxTemperature

    // --- Relationships (In-Memory Navigation) ---
    TrajectoryPoint trajectoryPoint
    Vector actualPointVector
    Object executionStatus
    Object errorCause

    TrajectoryPointRun() { }

    TrajectoryPointRun(String trajectoryPointRunId) {
        this.trajectoryPointRunId = Objects.requireNonNull(trajectoryPointRunId, "TrajectoryPointRun.trajectoryPointRunId cannot be null")
    }

    TrajectoryPointRun(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('trajectoryPointRunId')) this.trajectoryPointRunId = args.get('trajectoryPointRunId') as String
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('approximatedFunctionSampleId')) this.approximatedFunctionSampleId = args.get('approximatedFunctionSampleId') as String
            if (args.containsKey('actualPointVectorId')) this.actualPointVectorId = args.get('actualPointVectorId') as String
            if (args.containsKey('estimatedTimeOffsetMillis')) this.estimatedTimeOffsetMillis = args.get('estimatedTimeOffsetMillis') as BigDecimal
            if (args.containsKey('actualTimeOffsetMillis')) this.actualTimeOffsetMillis = args.get('actualTimeOffsetMillis') as BigDecimal
            if (args.containsKey('timeDeviationMillis')) this.timeDeviationMillis = args.get('timeDeviationMillis') as BigDecimal
            if (args.containsKey('plannedBreakDuration')) this.plannedBreakDuration = args.get('plannedBreakDuration') as BigDecimal
            if (args.containsKey('actualBreakDuration')) this.actualBreakDuration = args.get('actualBreakDuration') as BigDecimal
            if (args.containsKey('breakDeviation')) this.breakDeviation = args.get('breakDeviation') as BigDecimal
            if (args.containsKey('positionError')) this.positionError = args.get('positionError') as BigDecimal
            if (args.containsKey('velocityError')) this.velocityError = args.get('velocityError') as BigDecimal
            if (args.containsKey('accelerationError')) this.accelerationError = args.get('accelerationError') as BigDecimal
            if (args.containsKey('jerkError')) this.jerkError = args.get('jerkError') as BigDecimal
            if (args.containsKey('snapError')) this.snapError = args.get('snapError') as BigDecimal
            if (args.containsKey('compositeError')) this.compositeError = args.get('compositeError') as BigDecimal
            if (args.containsKey('positionRelativeError')) this.positionRelativeError = args.get('positionRelativeError') as BigDecimal
            if (args.containsKey('velocityRelativeError')) this.velocityRelativeError = args.get('velocityRelativeError') as BigDecimal
            if (args.containsKey('accelerationRelativeError')) this.accelerationRelativeError = args.get('accelerationRelativeError') as BigDecimal
            if (args.containsKey('hasPointExecutionFailure')) this.hasPointExecutionFailure = args.get('hasPointExecutionFailure') as String
            if (args.containsKey('hasPointExecutionDeviation')) this.hasPointExecutionDeviation = args.get('hasPointExecutionDeviation') as String
            if (args.containsKey('executionStatusEnumId')) this.executionStatusEnumId = args.get('executionStatusEnumId') as String
            if (args.containsKey('errorCauseEnumId')) this.errorCauseEnumId = args.get('errorCauseEnumId') as String
            if (args.containsKey('vibrationLevel')) this.vibrationLevel = args.get('vibrationLevel') as BigDecimal
            if (args.containsKey('maxPowerDraw')) this.maxPowerDraw = args.get('maxPowerDraw') as BigDecimal
            if (args.containsKey('maxTemperature')) this.maxTemperature = args.get('maxTemperature') as BigDecimal
            if (args.containsKey('trajectoryPoint')) this.trajectoryPoint = args.get('trajectoryPoint') as TrajectoryPoint
            if (args.containsKey('actualPointVector')) this.actualPointVector = args.get('actualPointVector') as Vector
            if (args.containsKey('executionStatus')) this.executionStatus = args.get('executionStatus') as Object
            if (args.containsKey('errorCause')) this.errorCause = args.get('errorCause') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.approximatedFunctionId == null) throw new IllegalStateException("Required property missing: TrajectoryPointRun.approximatedFunctionId")
        if (this.approximatedFunctionSampleId == null) throw new IllegalStateException("Required property missing: TrajectoryPointRun.approximatedFunctionSampleId")
    }

    /**
     * Gradle-style closure configurator
     */
    TrajectoryPointRun configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TrajectoryPointRun) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    TrajectoryPoint trajectoryPoint(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TrajectoryPoint) Closure<?> action) {
        if (this.trajectoryPoint == null) this.trajectoryPoint = new TrajectoryPoint()
        this.trajectoryPoint.configure(action)
        this.trajectoryPoint
    }

    Vector actualPointVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.actualPointVector == null) this.actualPointVector = new Vector()
        this.actualPointVector.configure(action)
        this.actualPointVector
    }
}
