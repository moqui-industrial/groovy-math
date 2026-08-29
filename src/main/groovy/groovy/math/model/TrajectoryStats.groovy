/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TrajectoryStats
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
@EqualsAndHashCode(includes = ['trajectoryStatsId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class TrajectoryStats implements Serializable {
    private static final long serialVersionUID = 1L

    /** trajectoryStatsId */
    String trajectoryStatsId

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** statsTypeEnumId */
    String statsTypeEnumId

    /** statsName */
    String statsName

    /** description */
    String description

    /** minDurationMillis */
    BigDecimal minDurationMillis

    /** maxDurationMillis */
    BigDecimal maxDurationMillis

    /** avgDurationMillis */
    BigDecimal avgDurationMillis

    /** totalSquaredDurationMillis */
    BigDecimal totalSquaredDurationMillis

    /** minLatency */
    BigDecimal minLatency

    /** maxLatency */
    BigDecimal maxLatency

    /** avgLatency */
    BigDecimal avgLatency

    /** totalSquaredLatency */
    BigDecimal totalSquaredLatency

    /** minJitter */
    BigDecimal minJitter

    /** maxJitter */
    BigDecimal maxJitter

    /** avgJitter */
    BigDecimal avgJitter

    /** totalSquaredJitter */
    BigDecimal totalSquaredJitter

    /** minTotalDisplacement */
    BigDecimal minTotalDisplacement

    /** maxTotalDisplacement */
    BigDecimal maxTotalDisplacement

    /** avgTotalDisplacement */
    BigDecimal avgTotalDisplacement

    /** totalSquaredDisplacement */
    BigDecimal totalSquaredDisplacement

    /** minVelocity */
    BigDecimal minVelocity

    /** maxVelocity */
    BigDecimal maxVelocity

    /** avgVelocity */
    BigDecimal avgVelocity

    /** totalSquaredVelocity */
    BigDecimal totalSquaredVelocity

    /** minAcceleration */
    BigDecimal minAcceleration

    /** maxAcceleration */
    BigDecimal maxAcceleration

    /** avgAcceleration */
    BigDecimal avgAcceleration

    /** totalSquaredAcceleration */
    BigDecimal totalSquaredAcceleration

    /** minJerk */
    BigDecimal minJerk

    /** maxJerk */
    BigDecimal maxJerk

    /** avgJerk */
    BigDecimal avgJerk

    /** totalSquaredJerk */
    BigDecimal totalSquaredJerk

    /** minSnap */
    BigDecimal minSnap

    /** maxSnap */
    BigDecimal maxSnap

    /** avgSnap */
    BigDecimal avgSnap

    /** totalSquaredSnap */
    BigDecimal totalSquaredSnap

    /** totalRuns */
    Long totalRuns

    /** totalSuccessRuns */
    Long totalSuccessRuns

    /** averageSuccessRate */
    BigDecimal averageSuccessRate

    /** errorCount */
    Long errorCount

    /** avgPositionError */
    BigDecimal avgPositionError

    /** avgVelocityError */
    BigDecimal avgVelocityError

    /** avgAccelerationError */
    BigDecimal avgAccelerationError

    /** avgJerkError */
    BigDecimal avgJerkError

    /** avgSnapError */
    BigDecimal avgSnapError

    /** avgCompositeError */
    BigDecimal avgCompositeError

    /** meanTimeBetweenFailures */
    BigDecimal meanTimeBetweenFailures

    /** meanTimeToRepair */
    BigDecimal meanTimeToRepair

    /** availability */
    BigDecimal availability

    /** probabilityFailureDemand */
    BigDecimal probabilityFailureDemand

    /** failureRate */
    BigDecimal failureRate

    /** riskPriorityNumber */
    Long riskPriorityNumber

    /** accuracy */
    BigDecimal accuracy

    /** precision */
    BigDecimal precision

    /** repeatability */
    BigDecimal repeatability

    /** repeatabilityStdDev */
    BigDecimal repeatabilityStdDev

    /** slowRepetitionCount */
    Long slowRepetitionCount

    /** avgEnergyConsumption */
    BigDecimal avgEnergyConsumption

    /** avgControlEffort */
    BigDecimal avgControlEffort

    /** minEfficiency */
    BigDecimal minEfficiency

    /** maxEfficiency */
    BigDecimal maxEfficiency

    /** avgEfficiency */
    BigDecimal avgEfficiency

    /** totalSquaredEfficiency */
    BigDecimal totalSquaredEfficiency

    /** maxPowerDraw */
    BigDecimal maxPowerDraw

    /** maxTemperature */
    BigDecimal maxTemperature

    /** naturalFrequency */
    BigDecimal naturalFrequency

    /** avgDampingRatio */
    BigDecimal avgDampingRatio

    /** avgPowerSpectralDensity */
    BigDecimal avgPowerSpectralDensity

    /** avgHarmonicDistortion */
    BigDecimal avgHarmonicDistortion

    /** avgVibrationLevel */
    BigDecimal avgVibrationLevel

    /** minInertia */
    BigDecimal minInertia

    /** maxInertia */
    BigDecimal maxInertia

    /** avgInertia */
    BigDecimal avgInertia

    /** totalSquaredInertia */
    BigDecimal totalSquaredInertia

    /** minFriction */
    BigDecimal minFriction

    /** maxFriction */
    BigDecimal maxFriction

    /** avgFriction */
    BigDecimal avgFriction

    /** totalSquaredFriction */
    BigDecimal totalSquaredFriction

    /** minResonance */
    BigDecimal minResonance

    /** maxResonance */
    BigDecimal maxResonance

    /** avgResonance */
    BigDecimal avgResonance

    /** totalSquaredResonance */
    BigDecimal totalSquaredResonance

    /** minCogging */
    BigDecimal minCogging

    /** maxCogging */
    BigDecimal maxCogging

    /** avgCogging */
    BigDecimal avgCogging

    /** totalSquaredCogging */
    BigDecimal totalSquaredCogging

    Trajectory trajectory

    TrajectoryStats() {}

    TrajectoryStats(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('trajectoryStatsId')) this.trajectoryStatsId = args.get('trajectoryStatsId')?.toString()
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('statsTypeEnumId')) this.statsTypeEnumId = args.get('statsTypeEnumId')?.toString()
            if (args.containsKey('statsName')) this.statsName = args.get('statsName')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('minDurationMillis')) this.minDurationMillis = args.get('minDurationMillis') != null ? (args.get('minDurationMillis') instanceof BigDecimal ? (BigDecimal) args.get('minDurationMillis') : new BigDecimal(args.get('minDurationMillis').toString())) : null
            if (args.containsKey('maxDurationMillis')) this.maxDurationMillis = args.get('maxDurationMillis') != null ? (args.get('maxDurationMillis') instanceof BigDecimal ? (BigDecimal) args.get('maxDurationMillis') : new BigDecimal(args.get('maxDurationMillis').toString())) : null
            if (args.containsKey('avgDurationMillis')) this.avgDurationMillis = args.get('avgDurationMillis') != null ? (args.get('avgDurationMillis') instanceof BigDecimal ? (BigDecimal) args.get('avgDurationMillis') : new BigDecimal(args.get('avgDurationMillis').toString())) : null
            if (args.containsKey('totalSquaredDurationMillis')) this.totalSquaredDurationMillis = args.get('totalSquaredDurationMillis') != null ? (args.get('totalSquaredDurationMillis') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredDurationMillis') : new BigDecimal(args.get('totalSquaredDurationMillis').toString())) : null
            if (args.containsKey('minLatency')) this.minLatency = args.get('minLatency') != null ? (args.get('minLatency') instanceof BigDecimal ? (BigDecimal) args.get('minLatency') : new BigDecimal(args.get('minLatency').toString())) : null
            if (args.containsKey('maxLatency')) this.maxLatency = args.get('maxLatency') != null ? (args.get('maxLatency') instanceof BigDecimal ? (BigDecimal) args.get('maxLatency') : new BigDecimal(args.get('maxLatency').toString())) : null
            if (args.containsKey('avgLatency')) this.avgLatency = args.get('avgLatency') != null ? (args.get('avgLatency') instanceof BigDecimal ? (BigDecimal) args.get('avgLatency') : new BigDecimal(args.get('avgLatency').toString())) : null
            if (args.containsKey('totalSquaredLatency')) this.totalSquaredLatency = args.get('totalSquaredLatency') != null ? (args.get('totalSquaredLatency') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredLatency') : new BigDecimal(args.get('totalSquaredLatency').toString())) : null
            if (args.containsKey('minJitter')) this.minJitter = args.get('minJitter') != null ? (args.get('minJitter') instanceof BigDecimal ? (BigDecimal) args.get('minJitter') : new BigDecimal(args.get('minJitter').toString())) : null
            if (args.containsKey('maxJitter')) this.maxJitter = args.get('maxJitter') != null ? (args.get('maxJitter') instanceof BigDecimal ? (BigDecimal) args.get('maxJitter') : new BigDecimal(args.get('maxJitter').toString())) : null
            if (args.containsKey('avgJitter')) this.avgJitter = args.get('avgJitter') != null ? (args.get('avgJitter') instanceof BigDecimal ? (BigDecimal) args.get('avgJitter') : new BigDecimal(args.get('avgJitter').toString())) : null
            if (args.containsKey('totalSquaredJitter')) this.totalSquaredJitter = args.get('totalSquaredJitter') != null ? (args.get('totalSquaredJitter') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredJitter') : new BigDecimal(args.get('totalSquaredJitter').toString())) : null
            if (args.containsKey('minTotalDisplacement')) this.minTotalDisplacement = args.get('minTotalDisplacement') != null ? (args.get('minTotalDisplacement') instanceof BigDecimal ? (BigDecimal) args.get('minTotalDisplacement') : new BigDecimal(args.get('minTotalDisplacement').toString())) : null
            if (args.containsKey('maxTotalDisplacement')) this.maxTotalDisplacement = args.get('maxTotalDisplacement') != null ? (args.get('maxTotalDisplacement') instanceof BigDecimal ? (BigDecimal) args.get('maxTotalDisplacement') : new BigDecimal(args.get('maxTotalDisplacement').toString())) : null
            if (args.containsKey('avgTotalDisplacement')) this.avgTotalDisplacement = args.get('avgTotalDisplacement') != null ? (args.get('avgTotalDisplacement') instanceof BigDecimal ? (BigDecimal) args.get('avgTotalDisplacement') : new BigDecimal(args.get('avgTotalDisplacement').toString())) : null
            if (args.containsKey('totalSquaredDisplacement')) this.totalSquaredDisplacement = args.get('totalSquaredDisplacement') != null ? (args.get('totalSquaredDisplacement') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredDisplacement') : new BigDecimal(args.get('totalSquaredDisplacement').toString())) : null
            if (args.containsKey('minVelocity')) this.minVelocity = args.get('minVelocity') != null ? (args.get('minVelocity') instanceof BigDecimal ? (BigDecimal) args.get('minVelocity') : new BigDecimal(args.get('minVelocity').toString())) : null
            if (args.containsKey('maxVelocity')) this.maxVelocity = args.get('maxVelocity') != null ? (args.get('maxVelocity') instanceof BigDecimal ? (BigDecimal) args.get('maxVelocity') : new BigDecimal(args.get('maxVelocity').toString())) : null
            if (args.containsKey('avgVelocity')) this.avgVelocity = args.get('avgVelocity') != null ? (args.get('avgVelocity') instanceof BigDecimal ? (BigDecimal) args.get('avgVelocity') : new BigDecimal(args.get('avgVelocity').toString())) : null
            if (args.containsKey('totalSquaredVelocity')) this.totalSquaredVelocity = args.get('totalSquaredVelocity') != null ? (args.get('totalSquaredVelocity') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredVelocity') : new BigDecimal(args.get('totalSquaredVelocity').toString())) : null
            if (args.containsKey('minAcceleration')) this.minAcceleration = args.get('minAcceleration') != null ? (args.get('minAcceleration') instanceof BigDecimal ? (BigDecimal) args.get('minAcceleration') : new BigDecimal(args.get('minAcceleration').toString())) : null
            if (args.containsKey('maxAcceleration')) this.maxAcceleration = args.get('maxAcceleration') != null ? (args.get('maxAcceleration') instanceof BigDecimal ? (BigDecimal) args.get('maxAcceleration') : new BigDecimal(args.get('maxAcceleration').toString())) : null
            if (args.containsKey('avgAcceleration')) this.avgAcceleration = args.get('avgAcceleration') != null ? (args.get('avgAcceleration') instanceof BigDecimal ? (BigDecimal) args.get('avgAcceleration') : new BigDecimal(args.get('avgAcceleration').toString())) : null
            if (args.containsKey('totalSquaredAcceleration')) this.totalSquaredAcceleration = args.get('totalSquaredAcceleration') != null ? (args.get('totalSquaredAcceleration') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredAcceleration') : new BigDecimal(args.get('totalSquaredAcceleration').toString())) : null
            if (args.containsKey('minJerk')) this.minJerk = args.get('minJerk') != null ? (args.get('minJerk') instanceof BigDecimal ? (BigDecimal) args.get('minJerk') : new BigDecimal(args.get('minJerk').toString())) : null
            if (args.containsKey('maxJerk')) this.maxJerk = args.get('maxJerk') != null ? (args.get('maxJerk') instanceof BigDecimal ? (BigDecimal) args.get('maxJerk') : new BigDecimal(args.get('maxJerk').toString())) : null
            if (args.containsKey('avgJerk')) this.avgJerk = args.get('avgJerk') != null ? (args.get('avgJerk') instanceof BigDecimal ? (BigDecimal) args.get('avgJerk') : new BigDecimal(args.get('avgJerk').toString())) : null
            if (args.containsKey('totalSquaredJerk')) this.totalSquaredJerk = args.get('totalSquaredJerk') != null ? (args.get('totalSquaredJerk') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredJerk') : new BigDecimal(args.get('totalSquaredJerk').toString())) : null
            if (args.containsKey('minSnap')) this.minSnap = args.get('minSnap') != null ? (args.get('minSnap') instanceof BigDecimal ? (BigDecimal) args.get('minSnap') : new BigDecimal(args.get('minSnap').toString())) : null
            if (args.containsKey('maxSnap')) this.maxSnap = args.get('maxSnap') != null ? (args.get('maxSnap') instanceof BigDecimal ? (BigDecimal) args.get('maxSnap') : new BigDecimal(args.get('maxSnap').toString())) : null
            if (args.containsKey('avgSnap')) this.avgSnap = args.get('avgSnap') != null ? (args.get('avgSnap') instanceof BigDecimal ? (BigDecimal) args.get('avgSnap') : new BigDecimal(args.get('avgSnap').toString())) : null
            if (args.containsKey('totalSquaredSnap')) this.totalSquaredSnap = args.get('totalSquaredSnap') != null ? (args.get('totalSquaredSnap') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredSnap') : new BigDecimal(args.get('totalSquaredSnap').toString())) : null
            if (args.containsKey('totalRuns')) this.totalRuns = args.get('totalRuns') != null ? ((Number) args.get('totalRuns')).longValue() : null
            if (args.containsKey('totalSuccessRuns')) this.totalSuccessRuns = args.get('totalSuccessRuns') != null ? ((Number) args.get('totalSuccessRuns')).longValue() : null
            if (args.containsKey('averageSuccessRate')) this.averageSuccessRate = args.get('averageSuccessRate') != null ? (args.get('averageSuccessRate') instanceof BigDecimal ? (BigDecimal) args.get('averageSuccessRate') : new BigDecimal(args.get('averageSuccessRate').toString())) : null
            if (args.containsKey('errorCount')) this.errorCount = args.get('errorCount') != null ? ((Number) args.get('errorCount')).longValue() : null
            if (args.containsKey('avgPositionError')) this.avgPositionError = args.get('avgPositionError') != null ? (args.get('avgPositionError') instanceof BigDecimal ? (BigDecimal) args.get('avgPositionError') : new BigDecimal(args.get('avgPositionError').toString())) : null
            if (args.containsKey('avgVelocityError')) this.avgVelocityError = args.get('avgVelocityError') != null ? (args.get('avgVelocityError') instanceof BigDecimal ? (BigDecimal) args.get('avgVelocityError') : new BigDecimal(args.get('avgVelocityError').toString())) : null
            if (args.containsKey('avgAccelerationError')) this.avgAccelerationError = args.get('avgAccelerationError') != null ? (args.get('avgAccelerationError') instanceof BigDecimal ? (BigDecimal) args.get('avgAccelerationError') : new BigDecimal(args.get('avgAccelerationError').toString())) : null
            if (args.containsKey('avgJerkError')) this.avgJerkError = args.get('avgJerkError') != null ? (args.get('avgJerkError') instanceof BigDecimal ? (BigDecimal) args.get('avgJerkError') : new BigDecimal(args.get('avgJerkError').toString())) : null
            if (args.containsKey('avgSnapError')) this.avgSnapError = args.get('avgSnapError') != null ? (args.get('avgSnapError') instanceof BigDecimal ? (BigDecimal) args.get('avgSnapError') : new BigDecimal(args.get('avgSnapError').toString())) : null
            if (args.containsKey('avgCompositeError')) this.avgCompositeError = args.get('avgCompositeError') != null ? (args.get('avgCompositeError') instanceof BigDecimal ? (BigDecimal) args.get('avgCompositeError') : new BigDecimal(args.get('avgCompositeError').toString())) : null
            if (args.containsKey('meanTimeBetweenFailures')) this.meanTimeBetweenFailures = args.get('meanTimeBetweenFailures') != null ? (args.get('meanTimeBetweenFailures') instanceof BigDecimal ? (BigDecimal) args.get('meanTimeBetweenFailures') : new BigDecimal(args.get('meanTimeBetweenFailures').toString())) : null
            if (args.containsKey('meanTimeToRepair')) this.meanTimeToRepair = args.get('meanTimeToRepair') != null ? (args.get('meanTimeToRepair') instanceof BigDecimal ? (BigDecimal) args.get('meanTimeToRepair') : new BigDecimal(args.get('meanTimeToRepair').toString())) : null
            if (args.containsKey('availability')) this.availability = args.get('availability') != null ? (args.get('availability') instanceof BigDecimal ? (BigDecimal) args.get('availability') : new BigDecimal(args.get('availability').toString())) : null
            if (args.containsKey('probabilityFailureDemand')) this.probabilityFailureDemand = args.get('probabilityFailureDemand') != null ? (args.get('probabilityFailureDemand') instanceof BigDecimal ? (BigDecimal) args.get('probabilityFailureDemand') : new BigDecimal(args.get('probabilityFailureDemand').toString())) : null
            if (args.containsKey('failureRate')) this.failureRate = args.get('failureRate') != null ? (args.get('failureRate') instanceof BigDecimal ? (BigDecimal) args.get('failureRate') : new BigDecimal(args.get('failureRate').toString())) : null
            if (args.containsKey('riskPriorityNumber')) this.riskPriorityNumber = args.get('riskPriorityNumber') != null ? ((Number) args.get('riskPriorityNumber')).longValue() : null
            if (args.containsKey('accuracy')) this.accuracy = args.get('accuracy') != null ? (args.get('accuracy') instanceof BigDecimal ? (BigDecimal) args.get('accuracy') : new BigDecimal(args.get('accuracy').toString())) : null
            if (args.containsKey('precision')) this.precision = args.get('precision') != null ? (args.get('precision') instanceof BigDecimal ? (BigDecimal) args.get('precision') : new BigDecimal(args.get('precision').toString())) : null
            if (args.containsKey('repeatability')) this.repeatability = args.get('repeatability') != null ? (args.get('repeatability') instanceof BigDecimal ? (BigDecimal) args.get('repeatability') : new BigDecimal(args.get('repeatability').toString())) : null
            if (args.containsKey('repeatabilityStdDev')) this.repeatabilityStdDev = args.get('repeatabilityStdDev') != null ? (args.get('repeatabilityStdDev') instanceof BigDecimal ? (BigDecimal) args.get('repeatabilityStdDev') : new BigDecimal(args.get('repeatabilityStdDev').toString())) : null
            if (args.containsKey('slowRepetitionCount')) this.slowRepetitionCount = args.get('slowRepetitionCount') != null ? ((Number) args.get('slowRepetitionCount')).longValue() : null
            if (args.containsKey('avgEnergyConsumption')) this.avgEnergyConsumption = args.get('avgEnergyConsumption') != null ? (args.get('avgEnergyConsumption') instanceof BigDecimal ? (BigDecimal) args.get('avgEnergyConsumption') : new BigDecimal(args.get('avgEnergyConsumption').toString())) : null
            if (args.containsKey('avgControlEffort')) this.avgControlEffort = args.get('avgControlEffort') != null ? (args.get('avgControlEffort') instanceof BigDecimal ? (BigDecimal) args.get('avgControlEffort') : new BigDecimal(args.get('avgControlEffort').toString())) : null
            if (args.containsKey('minEfficiency')) this.minEfficiency = args.get('minEfficiency') != null ? (args.get('minEfficiency') instanceof BigDecimal ? (BigDecimal) args.get('minEfficiency') : new BigDecimal(args.get('minEfficiency').toString())) : null
            if (args.containsKey('maxEfficiency')) this.maxEfficiency = args.get('maxEfficiency') != null ? (args.get('maxEfficiency') instanceof BigDecimal ? (BigDecimal) args.get('maxEfficiency') : new BigDecimal(args.get('maxEfficiency').toString())) : null
            if (args.containsKey('avgEfficiency')) this.avgEfficiency = args.get('avgEfficiency') != null ? (args.get('avgEfficiency') instanceof BigDecimal ? (BigDecimal) args.get('avgEfficiency') : new BigDecimal(args.get('avgEfficiency').toString())) : null
            if (args.containsKey('totalSquaredEfficiency')) this.totalSquaredEfficiency = args.get('totalSquaredEfficiency') != null ? (args.get('totalSquaredEfficiency') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredEfficiency') : new BigDecimal(args.get('totalSquaredEfficiency').toString())) : null
            if (args.containsKey('maxPowerDraw')) this.maxPowerDraw = args.get('maxPowerDraw') != null ? (args.get('maxPowerDraw') instanceof BigDecimal ? (BigDecimal) args.get('maxPowerDraw') : new BigDecimal(args.get('maxPowerDraw').toString())) : null
            if (args.containsKey('maxTemperature')) this.maxTemperature = args.get('maxTemperature') != null ? (args.get('maxTemperature') instanceof BigDecimal ? (BigDecimal) args.get('maxTemperature') : new BigDecimal(args.get('maxTemperature').toString())) : null
            if (args.containsKey('naturalFrequency')) this.naturalFrequency = args.get('naturalFrequency') != null ? (args.get('naturalFrequency') instanceof BigDecimal ? (BigDecimal) args.get('naturalFrequency') : new BigDecimal(args.get('naturalFrequency').toString())) : null
            if (args.containsKey('avgDampingRatio')) this.avgDampingRatio = args.get('avgDampingRatio') != null ? (args.get('avgDampingRatio') instanceof BigDecimal ? (BigDecimal) args.get('avgDampingRatio') : new BigDecimal(args.get('avgDampingRatio').toString())) : null
            if (args.containsKey('avgPowerSpectralDensity')) this.avgPowerSpectralDensity = args.get('avgPowerSpectralDensity') != null ? (args.get('avgPowerSpectralDensity') instanceof BigDecimal ? (BigDecimal) args.get('avgPowerSpectralDensity') : new BigDecimal(args.get('avgPowerSpectralDensity').toString())) : null
            if (args.containsKey('avgHarmonicDistortion')) this.avgHarmonicDistortion = args.get('avgHarmonicDistortion') != null ? (args.get('avgHarmonicDistortion') instanceof BigDecimal ? (BigDecimal) args.get('avgHarmonicDistortion') : new BigDecimal(args.get('avgHarmonicDistortion').toString())) : null
            if (args.containsKey('avgVibrationLevel')) this.avgVibrationLevel = args.get('avgVibrationLevel') != null ? (args.get('avgVibrationLevel') instanceof BigDecimal ? (BigDecimal) args.get('avgVibrationLevel') : new BigDecimal(args.get('avgVibrationLevel').toString())) : null
            if (args.containsKey('minInertia')) this.minInertia = args.get('minInertia') != null ? (args.get('minInertia') instanceof BigDecimal ? (BigDecimal) args.get('minInertia') : new BigDecimal(args.get('minInertia').toString())) : null
            if (args.containsKey('maxInertia')) this.maxInertia = args.get('maxInertia') != null ? (args.get('maxInertia') instanceof BigDecimal ? (BigDecimal) args.get('maxInertia') : new BigDecimal(args.get('maxInertia').toString())) : null
            if (args.containsKey('avgInertia')) this.avgInertia = args.get('avgInertia') != null ? (args.get('avgInertia') instanceof BigDecimal ? (BigDecimal) args.get('avgInertia') : new BigDecimal(args.get('avgInertia').toString())) : null
            if (args.containsKey('totalSquaredInertia')) this.totalSquaredInertia = args.get('totalSquaredInertia') != null ? (args.get('totalSquaredInertia') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredInertia') : new BigDecimal(args.get('totalSquaredInertia').toString())) : null
            if (args.containsKey('minFriction')) this.minFriction = args.get('minFriction') != null ? (args.get('minFriction') instanceof BigDecimal ? (BigDecimal) args.get('minFriction') : new BigDecimal(args.get('minFriction').toString())) : null
            if (args.containsKey('maxFriction')) this.maxFriction = args.get('maxFriction') != null ? (args.get('maxFriction') instanceof BigDecimal ? (BigDecimal) args.get('maxFriction') : new BigDecimal(args.get('maxFriction').toString())) : null
            if (args.containsKey('avgFriction')) this.avgFriction = args.get('avgFriction') != null ? (args.get('avgFriction') instanceof BigDecimal ? (BigDecimal) args.get('avgFriction') : new BigDecimal(args.get('avgFriction').toString())) : null
            if (args.containsKey('totalSquaredFriction')) this.totalSquaredFriction = args.get('totalSquaredFriction') != null ? (args.get('totalSquaredFriction') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredFriction') : new BigDecimal(args.get('totalSquaredFriction').toString())) : null
            if (args.containsKey('minResonance')) this.minResonance = args.get('minResonance') != null ? (args.get('minResonance') instanceof BigDecimal ? (BigDecimal) args.get('minResonance') : new BigDecimal(args.get('minResonance').toString())) : null
            if (args.containsKey('maxResonance')) this.maxResonance = args.get('maxResonance') != null ? (args.get('maxResonance') instanceof BigDecimal ? (BigDecimal) args.get('maxResonance') : new BigDecimal(args.get('maxResonance').toString())) : null
            if (args.containsKey('avgResonance')) this.avgResonance = args.get('avgResonance') != null ? (args.get('avgResonance') instanceof BigDecimal ? (BigDecimal) args.get('avgResonance') : new BigDecimal(args.get('avgResonance').toString())) : null
            if (args.containsKey('totalSquaredResonance')) this.totalSquaredResonance = args.get('totalSquaredResonance') != null ? (args.get('totalSquaredResonance') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredResonance') : new BigDecimal(args.get('totalSquaredResonance').toString())) : null
            if (args.containsKey('minCogging')) this.minCogging = args.get('minCogging') != null ? (args.get('minCogging') instanceof BigDecimal ? (BigDecimal) args.get('minCogging') : new BigDecimal(args.get('minCogging').toString())) : null
            if (args.containsKey('maxCogging')) this.maxCogging = args.get('maxCogging') != null ? (args.get('maxCogging') instanceof BigDecimal ? (BigDecimal) args.get('maxCogging') : new BigDecimal(args.get('maxCogging').toString())) : null
            if (args.containsKey('avgCogging')) this.avgCogging = args.get('avgCogging') != null ? (args.get('avgCogging') instanceof BigDecimal ? (BigDecimal) args.get('avgCogging') : new BigDecimal(args.get('avgCogging').toString())) : null
            if (args.containsKey('totalSquaredCogging')) this.totalSquaredCogging = args.get('totalSquaredCogging') != null ? (args.get('totalSquaredCogging') instanceof BigDecimal ? (BigDecimal) args.get('totalSquaredCogging') : new BigDecimal(args.get('totalSquaredCogging').toString())) : null
        }
    }

    TrajectoryStats trajectoryStatsId(String value) {
        this.trajectoryStatsId = value
        return this;
    }

    TrajectoryStats approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    TrajectoryStats statsTypeEnumId(String value) {
        this.statsTypeEnumId = value
        return this;
    }

    TrajectoryStats statsName(String value) {
        this.statsName = value
        return this;
    }

    TrajectoryStats description(String value) {
        this.description = value
        return this;
    }

    TrajectoryStats minDurationMillis(BigDecimal value) {
        this.minDurationMillis = value
        return this;
    }

    TrajectoryStats maxDurationMillis(BigDecimal value) {
        this.maxDurationMillis = value
        return this;
    }

    TrajectoryStats avgDurationMillis(BigDecimal value) {
        this.avgDurationMillis = value
        return this;
    }

    TrajectoryStats totalSquaredDurationMillis(BigDecimal value) {
        this.totalSquaredDurationMillis = value
        return this;
    }

    TrajectoryStats minLatency(BigDecimal value) {
        this.minLatency = value
        return this;
    }

    TrajectoryStats maxLatency(BigDecimal value) {
        this.maxLatency = value
        return this;
    }

    TrajectoryStats avgLatency(BigDecimal value) {
        this.avgLatency = value
        return this;
    }

    TrajectoryStats totalSquaredLatency(BigDecimal value) {
        this.totalSquaredLatency = value
        return this;
    }

    TrajectoryStats minJitter(BigDecimal value) {
        this.minJitter = value
        return this;
    }

    TrajectoryStats maxJitter(BigDecimal value) {
        this.maxJitter = value
        return this;
    }

    TrajectoryStats avgJitter(BigDecimal value) {
        this.avgJitter = value
        return this;
    }

    TrajectoryStats totalSquaredJitter(BigDecimal value) {
        this.totalSquaredJitter = value
        return this;
    }

    TrajectoryStats minTotalDisplacement(BigDecimal value) {
        this.minTotalDisplacement = value
        return this;
    }

    TrajectoryStats maxTotalDisplacement(BigDecimal value) {
        this.maxTotalDisplacement = value
        return this;
    }

    TrajectoryStats avgTotalDisplacement(BigDecimal value) {
        this.avgTotalDisplacement = value
        return this;
    }

    TrajectoryStats totalSquaredDisplacement(BigDecimal value) {
        this.totalSquaredDisplacement = value
        return this;
    }

    TrajectoryStats minVelocity(BigDecimal value) {
        this.minVelocity = value
        return this;
    }

    TrajectoryStats maxVelocity(BigDecimal value) {
        this.maxVelocity = value
        return this;
    }

    TrajectoryStats avgVelocity(BigDecimal value) {
        this.avgVelocity = value
        return this;
    }

    TrajectoryStats totalSquaredVelocity(BigDecimal value) {
        this.totalSquaredVelocity = value
        return this;
    }

    TrajectoryStats minAcceleration(BigDecimal value) {
        this.minAcceleration = value
        return this;
    }

    TrajectoryStats maxAcceleration(BigDecimal value) {
        this.maxAcceleration = value
        return this;
    }

    TrajectoryStats avgAcceleration(BigDecimal value) {
        this.avgAcceleration = value
        return this;
    }

    TrajectoryStats totalSquaredAcceleration(BigDecimal value) {
        this.totalSquaredAcceleration = value
        return this;
    }

    TrajectoryStats minJerk(BigDecimal value) {
        this.minJerk = value
        return this;
    }

    TrajectoryStats maxJerk(BigDecimal value) {
        this.maxJerk = value
        return this;
    }

    TrajectoryStats avgJerk(BigDecimal value) {
        this.avgJerk = value
        return this;
    }

    TrajectoryStats totalSquaredJerk(BigDecimal value) {
        this.totalSquaredJerk = value
        return this;
    }

    TrajectoryStats minSnap(BigDecimal value) {
        this.minSnap = value
        return this;
    }

    TrajectoryStats maxSnap(BigDecimal value) {
        this.maxSnap = value
        return this;
    }

    TrajectoryStats avgSnap(BigDecimal value) {
        this.avgSnap = value
        return this;
    }

    TrajectoryStats totalSquaredSnap(BigDecimal value) {
        this.totalSquaredSnap = value
        return this;
    }

    TrajectoryStats totalRuns(Long value) {
        this.totalRuns = value
        return this;
    }

    TrajectoryStats totalSuccessRuns(Long value) {
        this.totalSuccessRuns = value
        return this;
    }

    TrajectoryStats averageSuccessRate(BigDecimal value) {
        this.averageSuccessRate = value
        return this;
    }

    TrajectoryStats errorCount(Long value) {
        this.errorCount = value
        return this;
    }

    TrajectoryStats avgPositionError(BigDecimal value) {
        this.avgPositionError = value
        return this;
    }

    TrajectoryStats avgVelocityError(BigDecimal value) {
        this.avgVelocityError = value
        return this;
    }

    TrajectoryStats avgAccelerationError(BigDecimal value) {
        this.avgAccelerationError = value
        return this;
    }

    TrajectoryStats avgJerkError(BigDecimal value) {
        this.avgJerkError = value
        return this;
    }

    TrajectoryStats avgSnapError(BigDecimal value) {
        this.avgSnapError = value
        return this;
    }

    TrajectoryStats avgCompositeError(BigDecimal value) {
        this.avgCompositeError = value
        return this;
    }

    TrajectoryStats meanTimeBetweenFailures(BigDecimal value) {
        this.meanTimeBetweenFailures = value
        return this;
    }

    TrajectoryStats meanTimeToRepair(BigDecimal value) {
        this.meanTimeToRepair = value
        return this;
    }

    TrajectoryStats availability(BigDecimal value) {
        this.availability = value
        return this;
    }

    TrajectoryStats probabilityFailureDemand(BigDecimal value) {
        this.probabilityFailureDemand = value
        return this;
    }

    TrajectoryStats failureRate(BigDecimal value) {
        this.failureRate = value
        return this;
    }

    TrajectoryStats riskPriorityNumber(Long value) {
        this.riskPriorityNumber = value
        return this;
    }

    TrajectoryStats accuracy(BigDecimal value) {
        this.accuracy = value
        return this;
    }

    TrajectoryStats precision(BigDecimal value) {
        this.precision = value
        return this;
    }

    TrajectoryStats repeatability(BigDecimal value) {
        this.repeatability = value
        return this;
    }

    TrajectoryStats repeatabilityStdDev(BigDecimal value) {
        this.repeatabilityStdDev = value
        return this;
    }

    TrajectoryStats slowRepetitionCount(Long value) {
        this.slowRepetitionCount = value
        return this;
    }

    TrajectoryStats avgEnergyConsumption(BigDecimal value) {
        this.avgEnergyConsumption = value
        return this;
    }

    TrajectoryStats avgControlEffort(BigDecimal value) {
        this.avgControlEffort = value
        return this;
    }

    TrajectoryStats minEfficiency(BigDecimal value) {
        this.minEfficiency = value
        return this;
    }

    TrajectoryStats maxEfficiency(BigDecimal value) {
        this.maxEfficiency = value
        return this;
    }

    TrajectoryStats avgEfficiency(BigDecimal value) {
        this.avgEfficiency = value
        return this;
    }

    TrajectoryStats totalSquaredEfficiency(BigDecimal value) {
        this.totalSquaredEfficiency = value
        return this;
    }

    TrajectoryStats maxPowerDraw(BigDecimal value) {
        this.maxPowerDraw = value
        return this;
    }

    TrajectoryStats maxTemperature(BigDecimal value) {
        this.maxTemperature = value
        return this;
    }

    TrajectoryStats naturalFrequency(BigDecimal value) {
        this.naturalFrequency = value
        return this;
    }

    TrajectoryStats avgDampingRatio(BigDecimal value) {
        this.avgDampingRatio = value
        return this;
    }

    TrajectoryStats avgPowerSpectralDensity(BigDecimal value) {
        this.avgPowerSpectralDensity = value
        return this;
    }

    TrajectoryStats avgHarmonicDistortion(BigDecimal value) {
        this.avgHarmonicDistortion = value
        return this;
    }

    TrajectoryStats avgVibrationLevel(BigDecimal value) {
        this.avgVibrationLevel = value
        return this;
    }

    TrajectoryStats minInertia(BigDecimal value) {
        this.minInertia = value
        return this;
    }

    TrajectoryStats maxInertia(BigDecimal value) {
        this.maxInertia = value
        return this;
    }

    TrajectoryStats avgInertia(BigDecimal value) {
        this.avgInertia = value
        return this;
    }

    TrajectoryStats totalSquaredInertia(BigDecimal value) {
        this.totalSquaredInertia = value
        return this;
    }

    TrajectoryStats minFriction(BigDecimal value) {
        this.minFriction = value
        return this;
    }

    TrajectoryStats maxFriction(BigDecimal value) {
        this.maxFriction = value
        return this;
    }

    TrajectoryStats avgFriction(BigDecimal value) {
        this.avgFriction = value
        return this;
    }

    TrajectoryStats totalSquaredFriction(BigDecimal value) {
        this.totalSquaredFriction = value
        return this;
    }

    TrajectoryStats minResonance(BigDecimal value) {
        this.minResonance = value
        return this;
    }

    TrajectoryStats maxResonance(BigDecimal value) {
        this.maxResonance = value
        return this;
    }

    TrajectoryStats avgResonance(BigDecimal value) {
        this.avgResonance = value
        return this;
    }

    TrajectoryStats totalSquaredResonance(BigDecimal value) {
        this.totalSquaredResonance = value
        return this;
    }

    TrajectoryStats minCogging(BigDecimal value) {
        this.minCogging = value
        return this;
    }

    TrajectoryStats maxCogging(BigDecimal value) {
        this.maxCogging = value
        return this;
    }

    TrajectoryStats avgCogging(BigDecimal value) {
        this.avgCogging = value
        return this;
    }

    TrajectoryStats totalSquaredCogging(BigDecimal value) {
        this.totalSquaredCogging = value
        return this;
    }

    TrajectoryStats trajectory(Trajectory item) {
        this.trajectory = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.trajectoryStatsId != null) map.put('trajectoryStatsId', this.trajectoryStatsId);
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.statsTypeEnumId != null) map.put('statsTypeEnumId', this.statsTypeEnumId);
        if (this.statsName != null) map.put('statsName', this.statsName);
        if (this.description != null) map.put('description', this.description);
        if (this.minDurationMillis != null) map.put('minDurationMillis', this.minDurationMillis);
        if (this.maxDurationMillis != null) map.put('maxDurationMillis', this.maxDurationMillis);
        if (this.avgDurationMillis != null) map.put('avgDurationMillis', this.avgDurationMillis);
        if (this.totalSquaredDurationMillis != null) map.put('totalSquaredDurationMillis', this.totalSquaredDurationMillis);
        if (this.minLatency != null) map.put('minLatency', this.minLatency);
        if (this.maxLatency != null) map.put('maxLatency', this.maxLatency);
        if (this.avgLatency != null) map.put('avgLatency', this.avgLatency);
        if (this.totalSquaredLatency != null) map.put('totalSquaredLatency', this.totalSquaredLatency);
        if (this.minJitter != null) map.put('minJitter', this.minJitter);
        if (this.maxJitter != null) map.put('maxJitter', this.maxJitter);
        if (this.avgJitter != null) map.put('avgJitter', this.avgJitter);
        if (this.totalSquaredJitter != null) map.put('totalSquaredJitter', this.totalSquaredJitter);
        if (this.minTotalDisplacement != null) map.put('minTotalDisplacement', this.minTotalDisplacement);
        if (this.maxTotalDisplacement != null) map.put('maxTotalDisplacement', this.maxTotalDisplacement);
        if (this.avgTotalDisplacement != null) map.put('avgTotalDisplacement', this.avgTotalDisplacement);
        if (this.totalSquaredDisplacement != null) map.put('totalSquaredDisplacement', this.totalSquaredDisplacement);
        if (this.minVelocity != null) map.put('minVelocity', this.minVelocity);
        if (this.maxVelocity != null) map.put('maxVelocity', this.maxVelocity);
        if (this.avgVelocity != null) map.put('avgVelocity', this.avgVelocity);
        if (this.totalSquaredVelocity != null) map.put('totalSquaredVelocity', this.totalSquaredVelocity);
        if (this.minAcceleration != null) map.put('minAcceleration', this.minAcceleration);
        if (this.maxAcceleration != null) map.put('maxAcceleration', this.maxAcceleration);
        if (this.avgAcceleration != null) map.put('avgAcceleration', this.avgAcceleration);
        if (this.totalSquaredAcceleration != null) map.put('totalSquaredAcceleration', this.totalSquaredAcceleration);
        if (this.minJerk != null) map.put('minJerk', this.minJerk);
        if (this.maxJerk != null) map.put('maxJerk', this.maxJerk);
        if (this.avgJerk != null) map.put('avgJerk', this.avgJerk);
        if (this.totalSquaredJerk != null) map.put('totalSquaredJerk', this.totalSquaredJerk);
        if (this.minSnap != null) map.put('minSnap', this.minSnap);
        if (this.maxSnap != null) map.put('maxSnap', this.maxSnap);
        if (this.avgSnap != null) map.put('avgSnap', this.avgSnap);
        if (this.totalSquaredSnap != null) map.put('totalSquaredSnap', this.totalSquaredSnap);
        if (this.totalRuns != null) map.put('totalRuns', this.totalRuns);
        if (this.totalSuccessRuns != null) map.put('totalSuccessRuns', this.totalSuccessRuns);
        if (this.averageSuccessRate != null) map.put('averageSuccessRate', this.averageSuccessRate);
        if (this.errorCount != null) map.put('errorCount', this.errorCount);
        if (this.avgPositionError != null) map.put('avgPositionError', this.avgPositionError);
        if (this.avgVelocityError != null) map.put('avgVelocityError', this.avgVelocityError);
        if (this.avgAccelerationError != null) map.put('avgAccelerationError', this.avgAccelerationError);
        if (this.avgJerkError != null) map.put('avgJerkError', this.avgJerkError);
        if (this.avgSnapError != null) map.put('avgSnapError', this.avgSnapError);
        if (this.avgCompositeError != null) map.put('avgCompositeError', this.avgCompositeError);
        if (this.meanTimeBetweenFailures != null) map.put('meanTimeBetweenFailures', this.meanTimeBetweenFailures);
        if (this.meanTimeToRepair != null) map.put('meanTimeToRepair', this.meanTimeToRepair);
        if (this.availability != null) map.put('availability', this.availability);
        if (this.probabilityFailureDemand != null) map.put('probabilityFailureDemand', this.probabilityFailureDemand);
        if (this.failureRate != null) map.put('failureRate', this.failureRate);
        if (this.riskPriorityNumber != null) map.put('riskPriorityNumber', this.riskPriorityNumber);
        if (this.accuracy != null) map.put('accuracy', this.accuracy);
        if (this.precision != null) map.put('precision', this.precision);
        if (this.repeatability != null) map.put('repeatability', this.repeatability);
        if (this.repeatabilityStdDev != null) map.put('repeatabilityStdDev', this.repeatabilityStdDev);
        if (this.slowRepetitionCount != null) map.put('slowRepetitionCount', this.slowRepetitionCount);
        if (this.avgEnergyConsumption != null) map.put('avgEnergyConsumption', this.avgEnergyConsumption);
        if (this.avgControlEffort != null) map.put('avgControlEffort', this.avgControlEffort);
        if (this.minEfficiency != null) map.put('minEfficiency', this.minEfficiency);
        if (this.maxEfficiency != null) map.put('maxEfficiency', this.maxEfficiency);
        if (this.avgEfficiency != null) map.put('avgEfficiency', this.avgEfficiency);
        if (this.totalSquaredEfficiency != null) map.put('totalSquaredEfficiency', this.totalSquaredEfficiency);
        if (this.maxPowerDraw != null) map.put('maxPowerDraw', this.maxPowerDraw);
        if (this.maxTemperature != null) map.put('maxTemperature', this.maxTemperature);
        if (this.naturalFrequency != null) map.put('naturalFrequency', this.naturalFrequency);
        if (this.avgDampingRatio != null) map.put('avgDampingRatio', this.avgDampingRatio);
        if (this.avgPowerSpectralDensity != null) map.put('avgPowerSpectralDensity', this.avgPowerSpectralDensity);
        if (this.avgHarmonicDistortion != null) map.put('avgHarmonicDistortion', this.avgHarmonicDistortion);
        if (this.avgVibrationLevel != null) map.put('avgVibrationLevel', this.avgVibrationLevel);
        if (this.minInertia != null) map.put('minInertia', this.minInertia);
        if (this.maxInertia != null) map.put('maxInertia', this.maxInertia);
        if (this.avgInertia != null) map.put('avgInertia', this.avgInertia);
        if (this.totalSquaredInertia != null) map.put('totalSquaredInertia', this.totalSquaredInertia);
        if (this.minFriction != null) map.put('minFriction', this.minFriction);
        if (this.maxFriction != null) map.put('maxFriction', this.maxFriction);
        if (this.avgFriction != null) map.put('avgFriction', this.avgFriction);
        if (this.totalSquaredFriction != null) map.put('totalSquaredFriction', this.totalSquaredFriction);
        if (this.minResonance != null) map.put('minResonance', this.minResonance);
        if (this.maxResonance != null) map.put('maxResonance', this.maxResonance);
        if (this.avgResonance != null) map.put('avgResonance', this.avgResonance);
        if (this.totalSquaredResonance != null) map.put('totalSquaredResonance', this.totalSquaredResonance);
        if (this.minCogging != null) map.put('minCogging', this.minCogging);
        if (this.maxCogging != null) map.put('maxCogging', this.maxCogging);
        if (this.avgCogging != null) map.put('avgCogging', this.avgCogging);
        if (this.totalSquaredCogging != null) map.put('totalSquaredCogging', this.totalSquaredCogging);
        return map;
    }
}