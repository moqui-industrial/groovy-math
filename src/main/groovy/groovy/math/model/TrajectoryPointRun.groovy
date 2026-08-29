/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TrajectoryPointRun
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['trajectoryPointRunId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class TrajectoryPointRun implements Serializable {
    private static final long serialVersionUID = 1L

    /** trajectoryPointRunId */
    String trajectoryPointRunId

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** approximatedFunctionSampleId */
    String approximatedFunctionSampleId

    /** actualPointVectorId */
    String actualPointVectorId

    /** estimatedTimeOffsetMillis */
    BigDecimal estimatedTimeOffsetMillis

    /** actualTimeOffsetMillis */
    BigDecimal actualTimeOffsetMillis

    /** timeDeviationMillis */
    BigDecimal timeDeviationMillis

    /** plannedBreakDuration */
    BigDecimal plannedBreakDuration

    /** actualBreakDuration */
    BigDecimal actualBreakDuration

    /** breakDeviation */
    BigDecimal breakDeviation

    /** positionError */
    BigDecimal positionError

    /** velocityError */
    BigDecimal velocityError

    /** accelerationError */
    BigDecimal accelerationError

    /** jerkError */
    BigDecimal jerkError

    /** snapError */
    BigDecimal snapError

    /** compositeError */
    BigDecimal compositeError

    /** positionRelativeError */
    BigDecimal positionRelativeError

    /** velocityRelativeError */
    BigDecimal velocityRelativeError

    /** accelerationRelativeError */
    BigDecimal accelerationRelativeError

    /** hasPointExecutionFailure */
    String hasPointExecutionFailure

    /** hasPointExecutionDeviation */
    String hasPointExecutionDeviation

    /** executionStatusEnumId */
    String executionStatusEnumId

    /** errorCauseEnumId */
    String errorCauseEnumId

    /** vibrationLevel */
    BigDecimal vibrationLevel

    /** maxPowerDraw */
    BigDecimal maxPowerDraw

    /** maxTemperature */
    BigDecimal maxTemperature

    TrajectoryPoint trajectoryPoint

    Vector actualPointVector

    TrajectoryPointRun() {}

    TrajectoryPointRun(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('trajectoryPointRunId')) this.trajectoryPointRunId = args.get('trajectoryPointRunId')?.toString()
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('approximatedFunctionSampleId')) this.approximatedFunctionSampleId = args.get('approximatedFunctionSampleId')?.toString()
            if (args.containsKey('actualPointVectorId')) this.actualPointVectorId = args.get('actualPointVectorId')?.toString()
            if (args.containsKey('estimatedTimeOffsetMillis')) this.estimatedTimeOffsetMillis = args.get('estimatedTimeOffsetMillis') != null ? (args.get('estimatedTimeOffsetMillis') instanceof BigDecimal ? (BigDecimal) args.get('estimatedTimeOffsetMillis') : new BigDecimal(args.get('estimatedTimeOffsetMillis').toString())) : null
            if (args.containsKey('actualTimeOffsetMillis')) this.actualTimeOffsetMillis = args.get('actualTimeOffsetMillis') != null ? (args.get('actualTimeOffsetMillis') instanceof BigDecimal ? (BigDecimal) args.get('actualTimeOffsetMillis') : new BigDecimal(args.get('actualTimeOffsetMillis').toString())) : null
            if (args.containsKey('timeDeviationMillis')) this.timeDeviationMillis = args.get('timeDeviationMillis') != null ? (args.get('timeDeviationMillis') instanceof BigDecimal ? (BigDecimal) args.get('timeDeviationMillis') : new BigDecimal(args.get('timeDeviationMillis').toString())) : null
            if (args.containsKey('plannedBreakDuration')) this.plannedBreakDuration = args.get('plannedBreakDuration') != null ? (args.get('plannedBreakDuration') instanceof BigDecimal ? (BigDecimal) args.get('plannedBreakDuration') : new BigDecimal(args.get('plannedBreakDuration').toString())) : null
            if (args.containsKey('actualBreakDuration')) this.actualBreakDuration = args.get('actualBreakDuration') != null ? (args.get('actualBreakDuration') instanceof BigDecimal ? (BigDecimal) args.get('actualBreakDuration') : new BigDecimal(args.get('actualBreakDuration').toString())) : null
            if (args.containsKey('breakDeviation')) this.breakDeviation = args.get('breakDeviation') != null ? (args.get('breakDeviation') instanceof BigDecimal ? (BigDecimal) args.get('breakDeviation') : new BigDecimal(args.get('breakDeviation').toString())) : null
            if (args.containsKey('positionError')) this.positionError = args.get('positionError') != null ? (args.get('positionError') instanceof BigDecimal ? (BigDecimal) args.get('positionError') : new BigDecimal(args.get('positionError').toString())) : null
            if (args.containsKey('velocityError')) this.velocityError = args.get('velocityError') != null ? (args.get('velocityError') instanceof BigDecimal ? (BigDecimal) args.get('velocityError') : new BigDecimal(args.get('velocityError').toString())) : null
            if (args.containsKey('accelerationError')) this.accelerationError = args.get('accelerationError') != null ? (args.get('accelerationError') instanceof BigDecimal ? (BigDecimal) args.get('accelerationError') : new BigDecimal(args.get('accelerationError').toString())) : null
            if (args.containsKey('jerkError')) this.jerkError = args.get('jerkError') != null ? (args.get('jerkError') instanceof BigDecimal ? (BigDecimal) args.get('jerkError') : new BigDecimal(args.get('jerkError').toString())) : null
            if (args.containsKey('snapError')) this.snapError = args.get('snapError') != null ? (args.get('snapError') instanceof BigDecimal ? (BigDecimal) args.get('snapError') : new BigDecimal(args.get('snapError').toString())) : null
            if (args.containsKey('compositeError')) this.compositeError = args.get('compositeError') != null ? (args.get('compositeError') instanceof BigDecimal ? (BigDecimal) args.get('compositeError') : new BigDecimal(args.get('compositeError').toString())) : null
            if (args.containsKey('positionRelativeError')) this.positionRelativeError = args.get('positionRelativeError') != null ? (args.get('positionRelativeError') instanceof BigDecimal ? (BigDecimal) args.get('positionRelativeError') : new BigDecimal(args.get('positionRelativeError').toString())) : null
            if (args.containsKey('velocityRelativeError')) this.velocityRelativeError = args.get('velocityRelativeError') != null ? (args.get('velocityRelativeError') instanceof BigDecimal ? (BigDecimal) args.get('velocityRelativeError') : new BigDecimal(args.get('velocityRelativeError').toString())) : null
            if (args.containsKey('accelerationRelativeError')) this.accelerationRelativeError = args.get('accelerationRelativeError') != null ? (args.get('accelerationRelativeError') instanceof BigDecimal ? (BigDecimal) args.get('accelerationRelativeError') : new BigDecimal(args.get('accelerationRelativeError').toString())) : null
            if (args.containsKey('hasPointExecutionFailure')) this.hasPointExecutionFailure = args.get('hasPointExecutionFailure')?.toString()
            if (args.containsKey('hasPointExecutionDeviation')) this.hasPointExecutionDeviation = args.get('hasPointExecutionDeviation')?.toString()
            if (args.containsKey('executionStatusEnumId')) this.executionStatusEnumId = args.get('executionStatusEnumId')?.toString()
            if (args.containsKey('errorCauseEnumId')) this.errorCauseEnumId = args.get('errorCauseEnumId')?.toString()
            if (args.containsKey('vibrationLevel')) this.vibrationLevel = args.get('vibrationLevel') != null ? (args.get('vibrationLevel') instanceof BigDecimal ? (BigDecimal) args.get('vibrationLevel') : new BigDecimal(args.get('vibrationLevel').toString())) : null
            if (args.containsKey('maxPowerDraw')) this.maxPowerDraw = args.get('maxPowerDraw') != null ? (args.get('maxPowerDraw') instanceof BigDecimal ? (BigDecimal) args.get('maxPowerDraw') : new BigDecimal(args.get('maxPowerDraw').toString())) : null
            if (args.containsKey('maxTemperature')) this.maxTemperature = args.get('maxTemperature') != null ? (args.get('maxTemperature') instanceof BigDecimal ? (BigDecimal) args.get('maxTemperature') : new BigDecimal(args.get('maxTemperature').toString())) : null
        }
    }

    TrajectoryPointRun trajectoryPointRunId(String value) {
        this.trajectoryPointRunId = value
        return this;
    }

    TrajectoryPointRun approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    TrajectoryPointRun approximatedFunctionSampleId(String value) {
        this.approximatedFunctionSampleId = value
        return this;
    }

    TrajectoryPointRun actualPointVectorId(String value) {
        this.actualPointVectorId = value
        return this;
    }

    TrajectoryPointRun estimatedTimeOffsetMillis(BigDecimal value) {
        this.estimatedTimeOffsetMillis = value
        return this;
    }

    TrajectoryPointRun actualTimeOffsetMillis(BigDecimal value) {
        this.actualTimeOffsetMillis = value
        return this;
    }

    TrajectoryPointRun timeDeviationMillis(BigDecimal value) {
        this.timeDeviationMillis = value
        return this;
    }

    TrajectoryPointRun plannedBreakDuration(BigDecimal value) {
        this.plannedBreakDuration = value
        return this;
    }

    TrajectoryPointRun actualBreakDuration(BigDecimal value) {
        this.actualBreakDuration = value
        return this;
    }

    TrajectoryPointRun breakDeviation(BigDecimal value) {
        this.breakDeviation = value
        return this;
    }

    TrajectoryPointRun positionError(BigDecimal value) {
        this.positionError = value
        return this;
    }

    TrajectoryPointRun velocityError(BigDecimal value) {
        this.velocityError = value
        return this;
    }

    TrajectoryPointRun accelerationError(BigDecimal value) {
        this.accelerationError = value
        return this;
    }

    TrajectoryPointRun jerkError(BigDecimal value) {
        this.jerkError = value
        return this;
    }

    TrajectoryPointRun snapError(BigDecimal value) {
        this.snapError = value
        return this;
    }

    TrajectoryPointRun compositeError(BigDecimal value) {
        this.compositeError = value
        return this;
    }

    TrajectoryPointRun positionRelativeError(BigDecimal value) {
        this.positionRelativeError = value
        return this;
    }

    TrajectoryPointRun velocityRelativeError(BigDecimal value) {
        this.velocityRelativeError = value
        return this;
    }

    TrajectoryPointRun accelerationRelativeError(BigDecimal value) {
        this.accelerationRelativeError = value
        return this;
    }

    TrajectoryPointRun hasPointExecutionFailure(String value) {
        this.hasPointExecutionFailure = value
        return this;
    }

    TrajectoryPointRun hasPointExecutionDeviation(String value) {
        this.hasPointExecutionDeviation = value
        return this;
    }

    TrajectoryPointRun executionStatusEnumId(String value) {
        this.executionStatusEnumId = value
        return this;
    }

    TrajectoryPointRun errorCauseEnumId(String value) {
        this.errorCauseEnumId = value
        return this;
    }

    TrajectoryPointRun vibrationLevel(BigDecimal value) {
        this.vibrationLevel = value
        return this;
    }

    TrajectoryPointRun maxPowerDraw(BigDecimal value) {
        this.maxPowerDraw = value
        return this;
    }

    TrajectoryPointRun maxTemperature(BigDecimal value) {
        this.maxTemperature = value
        return this;
    }

    TrajectoryPointRun trajectoryPoint(TrajectoryPoint item) {
        this.trajectoryPoint = item;
        return this;
    }

    TrajectoryPointRun actualPointVector(Vector item) {
        this.actualPointVector = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.trajectoryPointRunId != null) map.put('trajectoryPointRunId', this.trajectoryPointRunId);
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.approximatedFunctionSampleId != null) map.put('approximatedFunctionSampleId', this.approximatedFunctionSampleId);
        if (this.actualPointVectorId != null) map.put('actualPointVectorId', this.actualPointVectorId);
        if (this.estimatedTimeOffsetMillis != null) map.put('estimatedTimeOffsetMillis', this.estimatedTimeOffsetMillis);
        if (this.actualTimeOffsetMillis != null) map.put('actualTimeOffsetMillis', this.actualTimeOffsetMillis);
        if (this.timeDeviationMillis != null) map.put('timeDeviationMillis', this.timeDeviationMillis);
        if (this.plannedBreakDuration != null) map.put('plannedBreakDuration', this.plannedBreakDuration);
        if (this.actualBreakDuration != null) map.put('actualBreakDuration', this.actualBreakDuration);
        if (this.breakDeviation != null) map.put('breakDeviation', this.breakDeviation);
        if (this.positionError != null) map.put('positionError', this.positionError);
        if (this.velocityError != null) map.put('velocityError', this.velocityError);
        if (this.accelerationError != null) map.put('accelerationError', this.accelerationError);
        if (this.jerkError != null) map.put('jerkError', this.jerkError);
        if (this.snapError != null) map.put('snapError', this.snapError);
        if (this.compositeError != null) map.put('compositeError', this.compositeError);
        if (this.positionRelativeError != null) map.put('positionRelativeError', this.positionRelativeError);
        if (this.velocityRelativeError != null) map.put('velocityRelativeError', this.velocityRelativeError);
        if (this.accelerationRelativeError != null) map.put('accelerationRelativeError', this.accelerationRelativeError);
        if (this.hasPointExecutionFailure != null) map.put('hasPointExecutionFailure', this.hasPointExecutionFailure);
        if (this.hasPointExecutionDeviation != null) map.put('hasPointExecutionDeviation', this.hasPointExecutionDeviation);
        if (this.executionStatusEnumId != null) map.put('executionStatusEnumId', this.executionStatusEnumId);
        if (this.errorCauseEnumId != null) map.put('errorCauseEnumId', this.errorCauseEnumId);
        if (this.vibrationLevel != null) map.put('vibrationLevel', this.vibrationLevel);
        if (this.maxPowerDraw != null) map.put('maxPowerDraw', this.maxPowerDraw);
        if (this.maxTemperature != null) map.put('maxTemperature', this.maxTemperature);
        return map;
    }
}