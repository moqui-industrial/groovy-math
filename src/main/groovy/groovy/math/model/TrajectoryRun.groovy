/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TrajectoryRun
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
@EqualsAndHashCode(includes = ['trajectoryRunId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class TrajectoryRun implements Serializable {
    private static final long serialVersionUID = 1L

    /** trajectoryRunId */
    String trajectoryRunId

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** lastTrajectoryPointId */
    String lastTrajectoryPointId

    /** lastSuccessfulTrajectoryPointId */
    String lastSuccessfulTrajectoryPointId

    /** estimatedStartDateTime */
    java.sql.Timestamp estimatedStartDateTime

    /** estimatedCompletionDateTime */
    java.sql.Timestamp estimatedCompletionDateTime

    /** estimatedDurationMillis */
    BigDecimal estimatedDurationMillis

    /** totalTimeMillisAllowed */
    BigDecimal totalTimeMillisAllowed

    /** actualStartDateTime */
    java.sql.Timestamp actualStartDateTime

    /** actualCompletionDateTime */
    java.sql.Timestamp actualCompletionDateTime

    /** actualDurationMillis */
    BigDecimal actualDurationMillis

    /** latency */
    BigDecimal latency

    /** jitter */
    BigDecimal jitter

    /** totalDisplacement */
    BigDecimal totalDisplacement

    /** minVelocity */
    BigDecimal minVelocity

    /** maxVelocity */
    BigDecimal maxVelocity

    /** avgVelocity */
    BigDecimal avgVelocity

    /** minAcceleration */
    BigDecimal minAcceleration

    /** maxAcceleration */
    BigDecimal maxAcceleration

    /** avgAcceleration */
    BigDecimal avgAcceleration

    /** minJerk */
    BigDecimal minJerk

    /** maxJerk */
    BigDecimal maxJerk

    /** avgJerk */
    BigDecimal avgJerk

    /** minSnap */
    BigDecimal minSnap

    /** maxSnap */
    BigDecimal maxSnap

    /** avgSnap */
    BigDecimal avgSnap

    /** minPositionError */
    BigDecimal minPositionError

    /** maxPositionError */
    BigDecimal maxPositionError

    /** avgPositionError */
    BigDecimal avgPositionError

    /** minVelocityError */
    BigDecimal minVelocityError

    /** maxVelocityError */
    BigDecimal maxVelocityError

    /** avgVelocityError */
    BigDecimal avgVelocityError

    /** minAccelerationError */
    BigDecimal minAccelerationError

    /** maxAccelerationError */
    BigDecimal maxAccelerationError

    /** avgAccelerationError */
    BigDecimal avgAccelerationError

    /** minJerkError */
    BigDecimal minJerkError

    /** maxJerkError */
    BigDecimal maxJerkError

    /** avgJerkError */
    BigDecimal avgJerkError

    /** minSnapError */
    BigDecimal minSnapError

    /** maxSnapError */
    BigDecimal maxSnapError

    /** avgSnapError */
    BigDecimal avgSnapError

    /** avgCompositeError */
    BigDecimal avgCompositeError

    /** pointSuccessRate */
    BigDecimal pointSuccessRate

    /** reliability */
    BigDecimal reliability

    /** hasError */
    String hasError

    /** errors */
    String errors

    /** isSlowRun */
    String isSlowRun

    /** isDelayed */
    String isDelayed

    /** settlingTime */
    BigDecimal settlingTime

    /** riseTime */
    BigDecimal riseTime

    /** peakTime */
    BigDecimal peakTime

    /** steadyStateError */
    BigDecimal steadyStateError

    /** overshootPercentage */
    BigDecimal overshootPercentage

    /** integralAbsoluteError */
    BigDecimal integralAbsoluteError

    /** integralSquaredError */
    BigDecimal integralSquaredError

    /** integralTimeAbsoluteError */
    BigDecimal integralTimeAbsoluteError

    /** integralTimeSquaredError */
    BigDecimal integralTimeSquaredError

    /** accuracy */
    BigDecimal accuracy

    /** pathDeviationIndex */
    BigDecimal pathDeviationIndex

    /** circularityError */
    BigDecimal circularityError

    /** linearityError */
    BigDecimal linearityError

    /** naturalFrequency */
    BigDecimal naturalFrequency

    /** dampingRatio */
    BigDecimal dampingRatio

    /** powerSpectralDensity */
    BigDecimal powerSpectralDensity

    /** harmonicDistortion */
    BigDecimal harmonicDistortion

    /** vibrationLevel */
    BigDecimal vibrationLevel

    /** inertia */
    BigDecimal inertia

    /** friction */
    BigDecimal friction

    /** resonance */
    BigDecimal resonance

    /** cogging */
    BigDecimal cogging

    /** energyConsumption */
    BigDecimal energyConsumption

    /** controlEffort */
    BigDecimal controlEffort

    /** efficiency */
    BigDecimal efficiency

    /** maxPowerDraw */
    BigDecimal maxPowerDraw

    /** maxTemperature */
    BigDecimal maxTemperature

    Trajectory trajectory

    TrajectoryPoint lastTrajectoryPoint

    TrajectoryPoint lastSuccessfulTrajectoryPoint

    TrajectoryRun() {}

    TrajectoryRun(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('trajectoryRunId')) this.trajectoryRunId = args.get('trajectoryRunId')?.toString()
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('lastTrajectoryPointId')) this.lastTrajectoryPointId = args.get('lastTrajectoryPointId')?.toString()
            if (args.containsKey('lastSuccessfulTrajectoryPointId')) this.lastSuccessfulTrajectoryPointId = args.get('lastSuccessfulTrajectoryPointId')?.toString()
            if (args.containsKey('estimatedStartDateTime')) this.estimatedStartDateTime = (java.sql.Timestamp) args.get('estimatedStartDateTime')
            if (args.containsKey('estimatedCompletionDateTime')) this.estimatedCompletionDateTime = (java.sql.Timestamp) args.get('estimatedCompletionDateTime')
            if (args.containsKey('estimatedDurationMillis')) this.estimatedDurationMillis = args.get('estimatedDurationMillis') != null ? (args.get('estimatedDurationMillis') instanceof BigDecimal ? (BigDecimal) args.get('estimatedDurationMillis') : new BigDecimal(args.get('estimatedDurationMillis').toString())) : null
            if (args.containsKey('totalTimeMillisAllowed')) this.totalTimeMillisAllowed = args.get('totalTimeMillisAllowed') != null ? (args.get('totalTimeMillisAllowed') instanceof BigDecimal ? (BigDecimal) args.get('totalTimeMillisAllowed') : new BigDecimal(args.get('totalTimeMillisAllowed').toString())) : null
            if (args.containsKey('actualStartDateTime')) this.actualStartDateTime = (java.sql.Timestamp) args.get('actualStartDateTime')
            if (args.containsKey('actualCompletionDateTime')) this.actualCompletionDateTime = (java.sql.Timestamp) args.get('actualCompletionDateTime')
            if (args.containsKey('actualDurationMillis')) this.actualDurationMillis = args.get('actualDurationMillis') != null ? (args.get('actualDurationMillis') instanceof BigDecimal ? (BigDecimal) args.get('actualDurationMillis') : new BigDecimal(args.get('actualDurationMillis').toString())) : null
            if (args.containsKey('latency')) this.latency = args.get('latency') != null ? (args.get('latency') instanceof BigDecimal ? (BigDecimal) args.get('latency') : new BigDecimal(args.get('latency').toString())) : null
            if (args.containsKey('jitter')) this.jitter = args.get('jitter') != null ? (args.get('jitter') instanceof BigDecimal ? (BigDecimal) args.get('jitter') : new BigDecimal(args.get('jitter').toString())) : null
            if (args.containsKey('totalDisplacement')) this.totalDisplacement = args.get('totalDisplacement') != null ? (args.get('totalDisplacement') instanceof BigDecimal ? (BigDecimal) args.get('totalDisplacement') : new BigDecimal(args.get('totalDisplacement').toString())) : null
            if (args.containsKey('minVelocity')) this.minVelocity = args.get('minVelocity') != null ? (args.get('minVelocity') instanceof BigDecimal ? (BigDecimal) args.get('minVelocity') : new BigDecimal(args.get('minVelocity').toString())) : null
            if (args.containsKey('maxVelocity')) this.maxVelocity = args.get('maxVelocity') != null ? (args.get('maxVelocity') instanceof BigDecimal ? (BigDecimal) args.get('maxVelocity') : new BigDecimal(args.get('maxVelocity').toString())) : null
            if (args.containsKey('avgVelocity')) this.avgVelocity = args.get('avgVelocity') != null ? (args.get('avgVelocity') instanceof BigDecimal ? (BigDecimal) args.get('avgVelocity') : new BigDecimal(args.get('avgVelocity').toString())) : null
            if (args.containsKey('minAcceleration')) this.minAcceleration = args.get('minAcceleration') != null ? (args.get('minAcceleration') instanceof BigDecimal ? (BigDecimal) args.get('minAcceleration') : new BigDecimal(args.get('minAcceleration').toString())) : null
            if (args.containsKey('maxAcceleration')) this.maxAcceleration = args.get('maxAcceleration') != null ? (args.get('maxAcceleration') instanceof BigDecimal ? (BigDecimal) args.get('maxAcceleration') : new BigDecimal(args.get('maxAcceleration').toString())) : null
            if (args.containsKey('avgAcceleration')) this.avgAcceleration = args.get('avgAcceleration') != null ? (args.get('avgAcceleration') instanceof BigDecimal ? (BigDecimal) args.get('avgAcceleration') : new BigDecimal(args.get('avgAcceleration').toString())) : null
            if (args.containsKey('minJerk')) this.minJerk = args.get('minJerk') != null ? (args.get('minJerk') instanceof BigDecimal ? (BigDecimal) args.get('minJerk') : new BigDecimal(args.get('minJerk').toString())) : null
            if (args.containsKey('maxJerk')) this.maxJerk = args.get('maxJerk') != null ? (args.get('maxJerk') instanceof BigDecimal ? (BigDecimal) args.get('maxJerk') : new BigDecimal(args.get('maxJerk').toString())) : null
            if (args.containsKey('avgJerk')) this.avgJerk = args.get('avgJerk') != null ? (args.get('avgJerk') instanceof BigDecimal ? (BigDecimal) args.get('avgJerk') : new BigDecimal(args.get('avgJerk').toString())) : null
            if (args.containsKey('minSnap')) this.minSnap = args.get('minSnap') != null ? (args.get('minSnap') instanceof BigDecimal ? (BigDecimal) args.get('minSnap') : new BigDecimal(args.get('minSnap').toString())) : null
            if (args.containsKey('maxSnap')) this.maxSnap = args.get('maxSnap') != null ? (args.get('maxSnap') instanceof BigDecimal ? (BigDecimal) args.get('maxSnap') : new BigDecimal(args.get('maxSnap').toString())) : null
            if (args.containsKey('avgSnap')) this.avgSnap = args.get('avgSnap') != null ? (args.get('avgSnap') instanceof BigDecimal ? (BigDecimal) args.get('avgSnap') : new BigDecimal(args.get('avgSnap').toString())) : null
            if (args.containsKey('minPositionError')) this.minPositionError = args.get('minPositionError') != null ? (args.get('minPositionError') instanceof BigDecimal ? (BigDecimal) args.get('minPositionError') : new BigDecimal(args.get('minPositionError').toString())) : null
            if (args.containsKey('maxPositionError')) this.maxPositionError = args.get('maxPositionError') != null ? (args.get('maxPositionError') instanceof BigDecimal ? (BigDecimal) args.get('maxPositionError') : new BigDecimal(args.get('maxPositionError').toString())) : null
            if (args.containsKey('avgPositionError')) this.avgPositionError = args.get('avgPositionError') != null ? (args.get('avgPositionError') instanceof BigDecimal ? (BigDecimal) args.get('avgPositionError') : new BigDecimal(args.get('avgPositionError').toString())) : null
            if (args.containsKey('minVelocityError')) this.minVelocityError = args.get('minVelocityError') != null ? (args.get('minVelocityError') instanceof BigDecimal ? (BigDecimal) args.get('minVelocityError') : new BigDecimal(args.get('minVelocityError').toString())) : null
            if (args.containsKey('maxVelocityError')) this.maxVelocityError = args.get('maxVelocityError') != null ? (args.get('maxVelocityError') instanceof BigDecimal ? (BigDecimal) args.get('maxVelocityError') : new BigDecimal(args.get('maxVelocityError').toString())) : null
            if (args.containsKey('avgVelocityError')) this.avgVelocityError = args.get('avgVelocityError') != null ? (args.get('avgVelocityError') instanceof BigDecimal ? (BigDecimal) args.get('avgVelocityError') : new BigDecimal(args.get('avgVelocityError').toString())) : null
            if (args.containsKey('minAccelerationError')) this.minAccelerationError = args.get('minAccelerationError') != null ? (args.get('minAccelerationError') instanceof BigDecimal ? (BigDecimal) args.get('minAccelerationError') : new BigDecimal(args.get('minAccelerationError').toString())) : null
            if (args.containsKey('maxAccelerationError')) this.maxAccelerationError = args.get('maxAccelerationError') != null ? (args.get('maxAccelerationError') instanceof BigDecimal ? (BigDecimal) args.get('maxAccelerationError') : new BigDecimal(args.get('maxAccelerationError').toString())) : null
            if (args.containsKey('avgAccelerationError')) this.avgAccelerationError = args.get('avgAccelerationError') != null ? (args.get('avgAccelerationError') instanceof BigDecimal ? (BigDecimal) args.get('avgAccelerationError') : new BigDecimal(args.get('avgAccelerationError').toString())) : null
            if (args.containsKey('minJerkError')) this.minJerkError = args.get('minJerkError') != null ? (args.get('minJerkError') instanceof BigDecimal ? (BigDecimal) args.get('minJerkError') : new BigDecimal(args.get('minJerkError').toString())) : null
            if (args.containsKey('maxJerkError')) this.maxJerkError = args.get('maxJerkError') != null ? (args.get('maxJerkError') instanceof BigDecimal ? (BigDecimal) args.get('maxJerkError') : new BigDecimal(args.get('maxJerkError').toString())) : null
            if (args.containsKey('avgJerkError')) this.avgJerkError = args.get('avgJerkError') != null ? (args.get('avgJerkError') instanceof BigDecimal ? (BigDecimal) args.get('avgJerkError') : new BigDecimal(args.get('avgJerkError').toString())) : null
            if (args.containsKey('minSnapError')) this.minSnapError = args.get('minSnapError') != null ? (args.get('minSnapError') instanceof BigDecimal ? (BigDecimal) args.get('minSnapError') : new BigDecimal(args.get('minSnapError').toString())) : null
            if (args.containsKey('maxSnapError')) this.maxSnapError = args.get('maxSnapError') != null ? (args.get('maxSnapError') instanceof BigDecimal ? (BigDecimal) args.get('maxSnapError') : new BigDecimal(args.get('maxSnapError').toString())) : null
            if (args.containsKey('avgSnapError')) this.avgSnapError = args.get('avgSnapError') != null ? (args.get('avgSnapError') instanceof BigDecimal ? (BigDecimal) args.get('avgSnapError') : new BigDecimal(args.get('avgSnapError').toString())) : null
            if (args.containsKey('avgCompositeError')) this.avgCompositeError = args.get('avgCompositeError') != null ? (args.get('avgCompositeError') instanceof BigDecimal ? (BigDecimal) args.get('avgCompositeError') : new BigDecimal(args.get('avgCompositeError').toString())) : null
            if (args.containsKey('pointSuccessRate')) this.pointSuccessRate = args.get('pointSuccessRate') != null ? (args.get('pointSuccessRate') instanceof BigDecimal ? (BigDecimal) args.get('pointSuccessRate') : new BigDecimal(args.get('pointSuccessRate').toString())) : null
            if (args.containsKey('reliability')) this.reliability = args.get('reliability') != null ? (args.get('reliability') instanceof BigDecimal ? (BigDecimal) args.get('reliability') : new BigDecimal(args.get('reliability').toString())) : null
            if (args.containsKey('hasError')) this.hasError = args.get('hasError')?.toString()
            if (args.containsKey('errors')) this.errors = args.get('errors')?.toString()
            if (args.containsKey('isSlowRun')) this.isSlowRun = args.get('isSlowRun')?.toString()
            if (args.containsKey('isDelayed')) this.isDelayed = args.get('isDelayed')?.toString()
            if (args.containsKey('settlingTime')) this.settlingTime = args.get('settlingTime') != null ? (args.get('settlingTime') instanceof BigDecimal ? (BigDecimal) args.get('settlingTime') : new BigDecimal(args.get('settlingTime').toString())) : null
            if (args.containsKey('riseTime')) this.riseTime = args.get('riseTime') != null ? (args.get('riseTime') instanceof BigDecimal ? (BigDecimal) args.get('riseTime') : new BigDecimal(args.get('riseTime').toString())) : null
            if (args.containsKey('peakTime')) this.peakTime = args.get('peakTime') != null ? (args.get('peakTime') instanceof BigDecimal ? (BigDecimal) args.get('peakTime') : new BigDecimal(args.get('peakTime').toString())) : null
            if (args.containsKey('steadyStateError')) this.steadyStateError = args.get('steadyStateError') != null ? (args.get('steadyStateError') instanceof BigDecimal ? (BigDecimal) args.get('steadyStateError') : new BigDecimal(args.get('steadyStateError').toString())) : null
            if (args.containsKey('overshootPercentage')) this.overshootPercentage = args.get('overshootPercentage') != null ? (args.get('overshootPercentage') instanceof BigDecimal ? (BigDecimal) args.get('overshootPercentage') : new BigDecimal(args.get('overshootPercentage').toString())) : null
            if (args.containsKey('integralAbsoluteError')) this.integralAbsoluteError = args.get('integralAbsoluteError') != null ? (args.get('integralAbsoluteError') instanceof BigDecimal ? (BigDecimal) args.get('integralAbsoluteError') : new BigDecimal(args.get('integralAbsoluteError').toString())) : null
            if (args.containsKey('integralSquaredError')) this.integralSquaredError = args.get('integralSquaredError') != null ? (args.get('integralSquaredError') instanceof BigDecimal ? (BigDecimal) args.get('integralSquaredError') : new BigDecimal(args.get('integralSquaredError').toString())) : null
            if (args.containsKey('integralTimeAbsoluteError')) this.integralTimeAbsoluteError = args.get('integralTimeAbsoluteError') != null ? (args.get('integralTimeAbsoluteError') instanceof BigDecimal ? (BigDecimal) args.get('integralTimeAbsoluteError') : new BigDecimal(args.get('integralTimeAbsoluteError').toString())) : null
            if (args.containsKey('integralTimeSquaredError')) this.integralTimeSquaredError = args.get('integralTimeSquaredError') != null ? (args.get('integralTimeSquaredError') instanceof BigDecimal ? (BigDecimal) args.get('integralTimeSquaredError') : new BigDecimal(args.get('integralTimeSquaredError').toString())) : null
            if (args.containsKey('accuracy')) this.accuracy = args.get('accuracy') != null ? (args.get('accuracy') instanceof BigDecimal ? (BigDecimal) args.get('accuracy') : new BigDecimal(args.get('accuracy').toString())) : null
            if (args.containsKey('pathDeviationIndex')) this.pathDeviationIndex = args.get('pathDeviationIndex') != null ? (args.get('pathDeviationIndex') instanceof BigDecimal ? (BigDecimal) args.get('pathDeviationIndex') : new BigDecimal(args.get('pathDeviationIndex').toString())) : null
            if (args.containsKey('circularityError')) this.circularityError = args.get('circularityError') != null ? (args.get('circularityError') instanceof BigDecimal ? (BigDecimal) args.get('circularityError') : new BigDecimal(args.get('circularityError').toString())) : null
            if (args.containsKey('linearityError')) this.linearityError = args.get('linearityError') != null ? (args.get('linearityError') instanceof BigDecimal ? (BigDecimal) args.get('linearityError') : new BigDecimal(args.get('linearityError').toString())) : null
            if (args.containsKey('naturalFrequency')) this.naturalFrequency = args.get('naturalFrequency') != null ? (args.get('naturalFrequency') instanceof BigDecimal ? (BigDecimal) args.get('naturalFrequency') : new BigDecimal(args.get('naturalFrequency').toString())) : null
            if (args.containsKey('dampingRatio')) this.dampingRatio = args.get('dampingRatio') != null ? (args.get('dampingRatio') instanceof BigDecimal ? (BigDecimal) args.get('dampingRatio') : new BigDecimal(args.get('dampingRatio').toString())) : null
            if (args.containsKey('powerSpectralDensity')) this.powerSpectralDensity = args.get('powerSpectralDensity') != null ? (args.get('powerSpectralDensity') instanceof BigDecimal ? (BigDecimal) args.get('powerSpectralDensity') : new BigDecimal(args.get('powerSpectralDensity').toString())) : null
            if (args.containsKey('harmonicDistortion')) this.harmonicDistortion = args.get('harmonicDistortion') != null ? (args.get('harmonicDistortion') instanceof BigDecimal ? (BigDecimal) args.get('harmonicDistortion') : new BigDecimal(args.get('harmonicDistortion').toString())) : null
            if (args.containsKey('vibrationLevel')) this.vibrationLevel = args.get('vibrationLevel') != null ? (args.get('vibrationLevel') instanceof BigDecimal ? (BigDecimal) args.get('vibrationLevel') : new BigDecimal(args.get('vibrationLevel').toString())) : null
            if (args.containsKey('inertia')) this.inertia = args.get('inertia') != null ? (args.get('inertia') instanceof BigDecimal ? (BigDecimal) args.get('inertia') : new BigDecimal(args.get('inertia').toString())) : null
            if (args.containsKey('friction')) this.friction = args.get('friction') != null ? (args.get('friction') instanceof BigDecimal ? (BigDecimal) args.get('friction') : new BigDecimal(args.get('friction').toString())) : null
            if (args.containsKey('resonance')) this.resonance = args.get('resonance') != null ? (args.get('resonance') instanceof BigDecimal ? (BigDecimal) args.get('resonance') : new BigDecimal(args.get('resonance').toString())) : null
            if (args.containsKey('cogging')) this.cogging = args.get('cogging') != null ? (args.get('cogging') instanceof BigDecimal ? (BigDecimal) args.get('cogging') : new BigDecimal(args.get('cogging').toString())) : null
            if (args.containsKey('energyConsumption')) this.energyConsumption = args.get('energyConsumption') != null ? (args.get('energyConsumption') instanceof BigDecimal ? (BigDecimal) args.get('energyConsumption') : new BigDecimal(args.get('energyConsumption').toString())) : null
            if (args.containsKey('controlEffort')) this.controlEffort = args.get('controlEffort') != null ? (args.get('controlEffort') instanceof BigDecimal ? (BigDecimal) args.get('controlEffort') : new BigDecimal(args.get('controlEffort').toString())) : null
            if (args.containsKey('efficiency')) this.efficiency = args.get('efficiency') != null ? (args.get('efficiency') instanceof BigDecimal ? (BigDecimal) args.get('efficiency') : new BigDecimal(args.get('efficiency').toString())) : null
            if (args.containsKey('maxPowerDraw')) this.maxPowerDraw = args.get('maxPowerDraw') != null ? (args.get('maxPowerDraw') instanceof BigDecimal ? (BigDecimal) args.get('maxPowerDraw') : new BigDecimal(args.get('maxPowerDraw').toString())) : null
            if (args.containsKey('maxTemperature')) this.maxTemperature = args.get('maxTemperature') != null ? (args.get('maxTemperature') instanceof BigDecimal ? (BigDecimal) args.get('maxTemperature') : new BigDecimal(args.get('maxTemperature').toString())) : null
        }
    }

    TrajectoryRun trajectoryRunId(String value) {
        this.trajectoryRunId = value
        return this;
    }

    TrajectoryRun approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    TrajectoryRun lastTrajectoryPointId(String value) {
        this.lastTrajectoryPointId = value
        return this;
    }

    TrajectoryRun lastSuccessfulTrajectoryPointId(String value) {
        this.lastSuccessfulTrajectoryPointId = value
        return this;
    }

    TrajectoryRun estimatedStartDateTime(java.sql.Timestamp value) {
        this.estimatedStartDateTime = value
        return this;
    }

    TrajectoryRun estimatedCompletionDateTime(java.sql.Timestamp value) {
        this.estimatedCompletionDateTime = value
        return this;
    }

    TrajectoryRun estimatedDurationMillis(BigDecimal value) {
        this.estimatedDurationMillis = value
        return this;
    }

    TrajectoryRun totalTimeMillisAllowed(BigDecimal value) {
        this.totalTimeMillisAllowed = value
        return this;
    }

    TrajectoryRun actualStartDateTime(java.sql.Timestamp value) {
        this.actualStartDateTime = value
        return this;
    }

    TrajectoryRun actualCompletionDateTime(java.sql.Timestamp value) {
        this.actualCompletionDateTime = value
        return this;
    }

    TrajectoryRun actualDurationMillis(BigDecimal value) {
        this.actualDurationMillis = value
        return this;
    }

    TrajectoryRun latency(BigDecimal value) {
        this.latency = value
        return this;
    }

    TrajectoryRun jitter(BigDecimal value) {
        this.jitter = value
        return this;
    }

    TrajectoryRun totalDisplacement(BigDecimal value) {
        this.totalDisplacement = value
        return this;
    }

    TrajectoryRun minVelocity(BigDecimal value) {
        this.minVelocity = value
        return this;
    }

    TrajectoryRun maxVelocity(BigDecimal value) {
        this.maxVelocity = value
        return this;
    }

    TrajectoryRun avgVelocity(BigDecimal value) {
        this.avgVelocity = value
        return this;
    }

    TrajectoryRun minAcceleration(BigDecimal value) {
        this.minAcceleration = value
        return this;
    }

    TrajectoryRun maxAcceleration(BigDecimal value) {
        this.maxAcceleration = value
        return this;
    }

    TrajectoryRun avgAcceleration(BigDecimal value) {
        this.avgAcceleration = value
        return this;
    }

    TrajectoryRun minJerk(BigDecimal value) {
        this.minJerk = value
        return this;
    }

    TrajectoryRun maxJerk(BigDecimal value) {
        this.maxJerk = value
        return this;
    }

    TrajectoryRun avgJerk(BigDecimal value) {
        this.avgJerk = value
        return this;
    }

    TrajectoryRun minSnap(BigDecimal value) {
        this.minSnap = value
        return this;
    }

    TrajectoryRun maxSnap(BigDecimal value) {
        this.maxSnap = value
        return this;
    }

    TrajectoryRun avgSnap(BigDecimal value) {
        this.avgSnap = value
        return this;
    }

    TrajectoryRun minPositionError(BigDecimal value) {
        this.minPositionError = value
        return this;
    }

    TrajectoryRun maxPositionError(BigDecimal value) {
        this.maxPositionError = value
        return this;
    }

    TrajectoryRun avgPositionError(BigDecimal value) {
        this.avgPositionError = value
        return this;
    }

    TrajectoryRun minVelocityError(BigDecimal value) {
        this.minVelocityError = value
        return this;
    }

    TrajectoryRun maxVelocityError(BigDecimal value) {
        this.maxVelocityError = value
        return this;
    }

    TrajectoryRun avgVelocityError(BigDecimal value) {
        this.avgVelocityError = value
        return this;
    }

    TrajectoryRun minAccelerationError(BigDecimal value) {
        this.minAccelerationError = value
        return this;
    }

    TrajectoryRun maxAccelerationError(BigDecimal value) {
        this.maxAccelerationError = value
        return this;
    }

    TrajectoryRun avgAccelerationError(BigDecimal value) {
        this.avgAccelerationError = value
        return this;
    }

    TrajectoryRun minJerkError(BigDecimal value) {
        this.minJerkError = value
        return this;
    }

    TrajectoryRun maxJerkError(BigDecimal value) {
        this.maxJerkError = value
        return this;
    }

    TrajectoryRun avgJerkError(BigDecimal value) {
        this.avgJerkError = value
        return this;
    }

    TrajectoryRun minSnapError(BigDecimal value) {
        this.minSnapError = value
        return this;
    }

    TrajectoryRun maxSnapError(BigDecimal value) {
        this.maxSnapError = value
        return this;
    }

    TrajectoryRun avgSnapError(BigDecimal value) {
        this.avgSnapError = value
        return this;
    }

    TrajectoryRun avgCompositeError(BigDecimal value) {
        this.avgCompositeError = value
        return this;
    }

    TrajectoryRun pointSuccessRate(BigDecimal value) {
        this.pointSuccessRate = value
        return this;
    }

    TrajectoryRun reliability(BigDecimal value) {
        this.reliability = value
        return this;
    }

    TrajectoryRun hasError(String value) {
        this.hasError = value
        return this;
    }

    TrajectoryRun errors(String value) {
        this.errors = value
        return this;
    }

    TrajectoryRun isSlowRun(String value) {
        this.isSlowRun = value
        return this;
    }

    TrajectoryRun isDelayed(String value) {
        this.isDelayed = value
        return this;
    }

    TrajectoryRun settlingTime(BigDecimal value) {
        this.settlingTime = value
        return this;
    }

    TrajectoryRun riseTime(BigDecimal value) {
        this.riseTime = value
        return this;
    }

    TrajectoryRun peakTime(BigDecimal value) {
        this.peakTime = value
        return this;
    }

    TrajectoryRun steadyStateError(BigDecimal value) {
        this.steadyStateError = value
        return this;
    }

    TrajectoryRun overshootPercentage(BigDecimal value) {
        this.overshootPercentage = value
        return this;
    }

    TrajectoryRun integralAbsoluteError(BigDecimal value) {
        this.integralAbsoluteError = value
        return this;
    }

    TrajectoryRun integralSquaredError(BigDecimal value) {
        this.integralSquaredError = value
        return this;
    }

    TrajectoryRun integralTimeAbsoluteError(BigDecimal value) {
        this.integralTimeAbsoluteError = value
        return this;
    }

    TrajectoryRun integralTimeSquaredError(BigDecimal value) {
        this.integralTimeSquaredError = value
        return this;
    }

    TrajectoryRun accuracy(BigDecimal value) {
        this.accuracy = value
        return this;
    }

    TrajectoryRun pathDeviationIndex(BigDecimal value) {
        this.pathDeviationIndex = value
        return this;
    }

    TrajectoryRun circularityError(BigDecimal value) {
        this.circularityError = value
        return this;
    }

    TrajectoryRun linearityError(BigDecimal value) {
        this.linearityError = value
        return this;
    }

    TrajectoryRun naturalFrequency(BigDecimal value) {
        this.naturalFrequency = value
        return this;
    }

    TrajectoryRun dampingRatio(BigDecimal value) {
        this.dampingRatio = value
        return this;
    }

    TrajectoryRun powerSpectralDensity(BigDecimal value) {
        this.powerSpectralDensity = value
        return this;
    }

    TrajectoryRun harmonicDistortion(BigDecimal value) {
        this.harmonicDistortion = value
        return this;
    }

    TrajectoryRun vibrationLevel(BigDecimal value) {
        this.vibrationLevel = value
        return this;
    }

    TrajectoryRun inertia(BigDecimal value) {
        this.inertia = value
        return this;
    }

    TrajectoryRun friction(BigDecimal value) {
        this.friction = value
        return this;
    }

    TrajectoryRun resonance(BigDecimal value) {
        this.resonance = value
        return this;
    }

    TrajectoryRun cogging(BigDecimal value) {
        this.cogging = value
        return this;
    }

    TrajectoryRun energyConsumption(BigDecimal value) {
        this.energyConsumption = value
        return this;
    }

    TrajectoryRun controlEffort(BigDecimal value) {
        this.controlEffort = value
        return this;
    }

    TrajectoryRun efficiency(BigDecimal value) {
        this.efficiency = value
        return this;
    }

    TrajectoryRun maxPowerDraw(BigDecimal value) {
        this.maxPowerDraw = value
        return this;
    }

    TrajectoryRun maxTemperature(BigDecimal value) {
        this.maxTemperature = value
        return this;
    }

    TrajectoryRun trajectory(Trajectory item) {
        this.trajectory = item;
        return this;
    }

    TrajectoryRun lastTrajectoryPoint(TrajectoryPoint item) {
        this.lastTrajectoryPoint = item;
        return this;
    }

    TrajectoryRun lastSuccessfulTrajectoryPoint(TrajectoryPoint item) {
        this.lastSuccessfulTrajectoryPoint = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.trajectoryRunId != null) map.put('trajectoryRunId', this.trajectoryRunId);
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.lastTrajectoryPointId != null) map.put('lastTrajectoryPointId', this.lastTrajectoryPointId);
        if (this.lastSuccessfulTrajectoryPointId != null) map.put('lastSuccessfulTrajectoryPointId', this.lastSuccessfulTrajectoryPointId);
        if (this.estimatedStartDateTime != null) map.put('estimatedStartDateTime', this.estimatedStartDateTime);
        if (this.estimatedCompletionDateTime != null) map.put('estimatedCompletionDateTime', this.estimatedCompletionDateTime);
        if (this.estimatedDurationMillis != null) map.put('estimatedDurationMillis', this.estimatedDurationMillis);
        if (this.totalTimeMillisAllowed != null) map.put('totalTimeMillisAllowed', this.totalTimeMillisAllowed);
        if (this.actualStartDateTime != null) map.put('actualStartDateTime', this.actualStartDateTime);
        if (this.actualCompletionDateTime != null) map.put('actualCompletionDateTime', this.actualCompletionDateTime);
        if (this.actualDurationMillis != null) map.put('actualDurationMillis', this.actualDurationMillis);
        if (this.latency != null) map.put('latency', this.latency);
        if (this.jitter != null) map.put('jitter', this.jitter);
        if (this.totalDisplacement != null) map.put('totalDisplacement', this.totalDisplacement);
        if (this.minVelocity != null) map.put('minVelocity', this.minVelocity);
        if (this.maxVelocity != null) map.put('maxVelocity', this.maxVelocity);
        if (this.avgVelocity != null) map.put('avgVelocity', this.avgVelocity);
        if (this.minAcceleration != null) map.put('minAcceleration', this.minAcceleration);
        if (this.maxAcceleration != null) map.put('maxAcceleration', this.maxAcceleration);
        if (this.avgAcceleration != null) map.put('avgAcceleration', this.avgAcceleration);
        if (this.minJerk != null) map.put('minJerk', this.minJerk);
        if (this.maxJerk != null) map.put('maxJerk', this.maxJerk);
        if (this.avgJerk != null) map.put('avgJerk', this.avgJerk);
        if (this.minSnap != null) map.put('minSnap', this.minSnap);
        if (this.maxSnap != null) map.put('maxSnap', this.maxSnap);
        if (this.avgSnap != null) map.put('avgSnap', this.avgSnap);
        if (this.minPositionError != null) map.put('minPositionError', this.minPositionError);
        if (this.maxPositionError != null) map.put('maxPositionError', this.maxPositionError);
        if (this.avgPositionError != null) map.put('avgPositionError', this.avgPositionError);
        if (this.minVelocityError != null) map.put('minVelocityError', this.minVelocityError);
        if (this.maxVelocityError != null) map.put('maxVelocityError', this.maxVelocityError);
        if (this.avgVelocityError != null) map.put('avgVelocityError', this.avgVelocityError);
        if (this.minAccelerationError != null) map.put('minAccelerationError', this.minAccelerationError);
        if (this.maxAccelerationError != null) map.put('maxAccelerationError', this.maxAccelerationError);
        if (this.avgAccelerationError != null) map.put('avgAccelerationError', this.avgAccelerationError);
        if (this.minJerkError != null) map.put('minJerkError', this.minJerkError);
        if (this.maxJerkError != null) map.put('maxJerkError', this.maxJerkError);
        if (this.avgJerkError != null) map.put('avgJerkError', this.avgJerkError);
        if (this.minSnapError != null) map.put('minSnapError', this.minSnapError);
        if (this.maxSnapError != null) map.put('maxSnapError', this.maxSnapError);
        if (this.avgSnapError != null) map.put('avgSnapError', this.avgSnapError);
        if (this.avgCompositeError != null) map.put('avgCompositeError', this.avgCompositeError);
        if (this.pointSuccessRate != null) map.put('pointSuccessRate', this.pointSuccessRate);
        if (this.reliability != null) map.put('reliability', this.reliability);
        if (this.hasError != null) map.put('hasError', this.hasError);
        if (this.errors != null) map.put('errors', this.errors);
        if (this.isSlowRun != null) map.put('isSlowRun', this.isSlowRun);
        if (this.isDelayed != null) map.put('isDelayed', this.isDelayed);
        if (this.settlingTime != null) map.put('settlingTime', this.settlingTime);
        if (this.riseTime != null) map.put('riseTime', this.riseTime);
        if (this.peakTime != null) map.put('peakTime', this.peakTime);
        if (this.steadyStateError != null) map.put('steadyStateError', this.steadyStateError);
        if (this.overshootPercentage != null) map.put('overshootPercentage', this.overshootPercentage);
        if (this.integralAbsoluteError != null) map.put('integralAbsoluteError', this.integralAbsoluteError);
        if (this.integralSquaredError != null) map.put('integralSquaredError', this.integralSquaredError);
        if (this.integralTimeAbsoluteError != null) map.put('integralTimeAbsoluteError', this.integralTimeAbsoluteError);
        if (this.integralTimeSquaredError != null) map.put('integralTimeSquaredError', this.integralTimeSquaredError);
        if (this.accuracy != null) map.put('accuracy', this.accuracy);
        if (this.pathDeviationIndex != null) map.put('pathDeviationIndex', this.pathDeviationIndex);
        if (this.circularityError != null) map.put('circularityError', this.circularityError);
        if (this.linearityError != null) map.put('linearityError', this.linearityError);
        if (this.naturalFrequency != null) map.put('naturalFrequency', this.naturalFrequency);
        if (this.dampingRatio != null) map.put('dampingRatio', this.dampingRatio);
        if (this.powerSpectralDensity != null) map.put('powerSpectralDensity', this.powerSpectralDensity);
        if (this.harmonicDistortion != null) map.put('harmonicDistortion', this.harmonicDistortion);
        if (this.vibrationLevel != null) map.put('vibrationLevel', this.vibrationLevel);
        if (this.inertia != null) map.put('inertia', this.inertia);
        if (this.friction != null) map.put('friction', this.friction);
        if (this.resonance != null) map.put('resonance', this.resonance);
        if (this.cogging != null) map.put('cogging', this.cogging);
        if (this.energyConsumption != null) map.put('energyConsumption', this.energyConsumption);
        if (this.controlEffort != null) map.put('controlEffort', this.controlEffort);
        if (this.efficiency != null) map.put('efficiency', this.efficiency);
        if (this.maxPowerDraw != null) map.put('maxPowerDraw', this.maxPowerDraw);
        if (this.maxTemperature != null) map.put('maxTemperature', this.maxTemperature);
        return map;
    }
}