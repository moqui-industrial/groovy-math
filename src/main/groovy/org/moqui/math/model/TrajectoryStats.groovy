/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TrajectoryStats
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
@EqualsAndHashCode(includes = ['trajectoryStatsId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class TrajectoryStats implements Serializable {
    private static final long serialVersionUID = 1L

    String trajectoryStatsId
    String approximatedFunctionId // Required
    String statsTypeEnumId
    String statsName
    String description
    BigDecimal minDurationMillis
    BigDecimal maxDurationMillis
    BigDecimal avgDurationMillis
    BigDecimal totalSquaredDurationMillis
    BigDecimal minLatency
    BigDecimal maxLatency
    BigDecimal avgLatency
    BigDecimal totalSquaredLatency
    BigDecimal minJitter
    BigDecimal maxJitter
    BigDecimal avgJitter
    BigDecimal totalSquaredJitter
    BigDecimal minTotalDisplacement
    BigDecimal maxTotalDisplacement
    BigDecimal avgTotalDisplacement
    BigDecimal totalSquaredDisplacement
    BigDecimal minVelocity
    BigDecimal maxVelocity
    BigDecimal avgVelocity
    BigDecimal totalSquaredVelocity
    BigDecimal minAcceleration
    BigDecimal maxAcceleration
    BigDecimal avgAcceleration
    BigDecimal totalSquaredAcceleration
    BigDecimal minJerk
    BigDecimal maxJerk
    BigDecimal avgJerk
    BigDecimal totalSquaredJerk
    BigDecimal minSnap
    BigDecimal maxSnap
    BigDecimal avgSnap
    BigDecimal totalSquaredSnap
    Long totalRuns
    Long totalSuccessRuns
    BigDecimal averageSuccessRate
    Long errorCount
    BigDecimal avgPositionError
    BigDecimal avgVelocityError
    BigDecimal avgAccelerationError
    BigDecimal avgJerkError
    BigDecimal avgSnapError
    BigDecimal avgCompositeError
    BigDecimal meanTimeBetweenFailures
    BigDecimal meanTimeToRepair
    BigDecimal availability
    BigDecimal probabilityFailureDemand
    BigDecimal failureRate
    Long riskPriorityNumber
    BigDecimal accuracy
    BigDecimal precision
    BigDecimal repeatability
    BigDecimal repeatabilityStdDev
    Long slowRepetitionCount
    BigDecimal avgEnergyConsumption
    BigDecimal avgControlEffort
    BigDecimal minEfficiency
    BigDecimal maxEfficiency
    BigDecimal avgEfficiency
    BigDecimal totalSquaredEfficiency
    BigDecimal maxPowerDraw
    BigDecimal maxTemperature
    BigDecimal naturalFrequency
    BigDecimal avgDampingRatio
    BigDecimal avgPowerSpectralDensity
    BigDecimal avgHarmonicDistortion
    BigDecimal avgVibrationLevel
    BigDecimal minInertia
    BigDecimal maxInertia
    BigDecimal avgInertia
    BigDecimal totalSquaredInertia
    BigDecimal minFriction
    BigDecimal maxFriction
    BigDecimal avgFriction
    BigDecimal totalSquaredFriction
    BigDecimal minResonance
    BigDecimal maxResonance
    BigDecimal avgResonance
    BigDecimal totalSquaredResonance
    BigDecimal minCogging
    BigDecimal maxCogging
    BigDecimal avgCogging
    BigDecimal totalSquaredCogging

    // --- Relationships (In-Memory Navigation) ---
    Trajectory trajectory
    Object type

    TrajectoryStats() { }

    TrajectoryStats(String trajectoryStatsId) {
        this.trajectoryStatsId = Objects.requireNonNull(trajectoryStatsId, "TrajectoryStats.trajectoryStatsId cannot be null")
    }

    TrajectoryStats(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('trajectoryStatsId')) this.trajectoryStatsId = args.get('trajectoryStatsId') as String
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('statsTypeEnumId')) this.statsTypeEnumId = args.get('statsTypeEnumId') as String
            if (args.containsKey('statsName')) this.statsName = args.get('statsName') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('minDurationMillis')) this.minDurationMillis = args.get('minDurationMillis') as BigDecimal
            if (args.containsKey('maxDurationMillis')) this.maxDurationMillis = args.get('maxDurationMillis') as BigDecimal
            if (args.containsKey('avgDurationMillis')) this.avgDurationMillis = args.get('avgDurationMillis') as BigDecimal
            if (args.containsKey('totalSquaredDurationMillis')) this.totalSquaredDurationMillis = args.get('totalSquaredDurationMillis') as BigDecimal
            if (args.containsKey('minLatency')) this.minLatency = args.get('minLatency') as BigDecimal
            if (args.containsKey('maxLatency')) this.maxLatency = args.get('maxLatency') as BigDecimal
            if (args.containsKey('avgLatency')) this.avgLatency = args.get('avgLatency') as BigDecimal
            if (args.containsKey('totalSquaredLatency')) this.totalSquaredLatency = args.get('totalSquaredLatency') as BigDecimal
            if (args.containsKey('minJitter')) this.minJitter = args.get('minJitter') as BigDecimal
            if (args.containsKey('maxJitter')) this.maxJitter = args.get('maxJitter') as BigDecimal
            if (args.containsKey('avgJitter')) this.avgJitter = args.get('avgJitter') as BigDecimal
            if (args.containsKey('totalSquaredJitter')) this.totalSquaredJitter = args.get('totalSquaredJitter') as BigDecimal
            if (args.containsKey('minTotalDisplacement')) this.minTotalDisplacement = args.get('minTotalDisplacement') as BigDecimal
            if (args.containsKey('maxTotalDisplacement')) this.maxTotalDisplacement = args.get('maxTotalDisplacement') as BigDecimal
            if (args.containsKey('avgTotalDisplacement')) this.avgTotalDisplacement = args.get('avgTotalDisplacement') as BigDecimal
            if (args.containsKey('totalSquaredDisplacement')) this.totalSquaredDisplacement = args.get('totalSquaredDisplacement') as BigDecimal
            if (args.containsKey('minVelocity')) this.minVelocity = args.get('minVelocity') as BigDecimal
            if (args.containsKey('maxVelocity')) this.maxVelocity = args.get('maxVelocity') as BigDecimal
            if (args.containsKey('avgVelocity')) this.avgVelocity = args.get('avgVelocity') as BigDecimal
            if (args.containsKey('totalSquaredVelocity')) this.totalSquaredVelocity = args.get('totalSquaredVelocity') as BigDecimal
            if (args.containsKey('minAcceleration')) this.minAcceleration = args.get('minAcceleration') as BigDecimal
            if (args.containsKey('maxAcceleration')) this.maxAcceleration = args.get('maxAcceleration') as BigDecimal
            if (args.containsKey('avgAcceleration')) this.avgAcceleration = args.get('avgAcceleration') as BigDecimal
            if (args.containsKey('totalSquaredAcceleration')) this.totalSquaredAcceleration = args.get('totalSquaredAcceleration') as BigDecimal
            if (args.containsKey('minJerk')) this.minJerk = args.get('minJerk') as BigDecimal
            if (args.containsKey('maxJerk')) this.maxJerk = args.get('maxJerk') as BigDecimal
            if (args.containsKey('avgJerk')) this.avgJerk = args.get('avgJerk') as BigDecimal
            if (args.containsKey('totalSquaredJerk')) this.totalSquaredJerk = args.get('totalSquaredJerk') as BigDecimal
            if (args.containsKey('minSnap')) this.minSnap = args.get('minSnap') as BigDecimal
            if (args.containsKey('maxSnap')) this.maxSnap = args.get('maxSnap') as BigDecimal
            if (args.containsKey('avgSnap')) this.avgSnap = args.get('avgSnap') as BigDecimal
            if (args.containsKey('totalSquaredSnap')) this.totalSquaredSnap = args.get('totalSquaredSnap') as BigDecimal
            if (args.containsKey('totalRuns')) this.totalRuns = args.get('totalRuns') as Long
            if (args.containsKey('totalSuccessRuns')) this.totalSuccessRuns = args.get('totalSuccessRuns') as Long
            if (args.containsKey('averageSuccessRate')) this.averageSuccessRate = args.get('averageSuccessRate') as BigDecimal
            if (args.containsKey('errorCount')) this.errorCount = args.get('errorCount') as Long
            if (args.containsKey('avgPositionError')) this.avgPositionError = args.get('avgPositionError') as BigDecimal
            if (args.containsKey('avgVelocityError')) this.avgVelocityError = args.get('avgVelocityError') as BigDecimal
            if (args.containsKey('avgAccelerationError')) this.avgAccelerationError = args.get('avgAccelerationError') as BigDecimal
            if (args.containsKey('avgJerkError')) this.avgJerkError = args.get('avgJerkError') as BigDecimal
            if (args.containsKey('avgSnapError')) this.avgSnapError = args.get('avgSnapError') as BigDecimal
            if (args.containsKey('avgCompositeError')) this.avgCompositeError = args.get('avgCompositeError') as BigDecimal
            if (args.containsKey('meanTimeBetweenFailures')) this.meanTimeBetweenFailures = args.get('meanTimeBetweenFailures') as BigDecimal
            if (args.containsKey('meanTimeToRepair')) this.meanTimeToRepair = args.get('meanTimeToRepair') as BigDecimal
            if (args.containsKey('availability')) this.availability = args.get('availability') as BigDecimal
            if (args.containsKey('probabilityFailureDemand')) this.probabilityFailureDemand = args.get('probabilityFailureDemand') as BigDecimal
            if (args.containsKey('failureRate')) this.failureRate = args.get('failureRate') as BigDecimal
            if (args.containsKey('riskPriorityNumber')) this.riskPriorityNumber = args.get('riskPriorityNumber') as Long
            if (args.containsKey('accuracy')) this.accuracy = args.get('accuracy') as BigDecimal
            if (args.containsKey('precision')) this.precision = args.get('precision') as BigDecimal
            if (args.containsKey('repeatability')) this.repeatability = args.get('repeatability') as BigDecimal
            if (args.containsKey('repeatabilityStdDev')) this.repeatabilityStdDev = args.get('repeatabilityStdDev') as BigDecimal
            if (args.containsKey('slowRepetitionCount')) this.slowRepetitionCount = args.get('slowRepetitionCount') as Long
            if (args.containsKey('avgEnergyConsumption')) this.avgEnergyConsumption = args.get('avgEnergyConsumption') as BigDecimal
            if (args.containsKey('avgControlEffort')) this.avgControlEffort = args.get('avgControlEffort') as BigDecimal
            if (args.containsKey('minEfficiency')) this.minEfficiency = args.get('minEfficiency') as BigDecimal
            if (args.containsKey('maxEfficiency')) this.maxEfficiency = args.get('maxEfficiency') as BigDecimal
            if (args.containsKey('avgEfficiency')) this.avgEfficiency = args.get('avgEfficiency') as BigDecimal
            if (args.containsKey('totalSquaredEfficiency')) this.totalSquaredEfficiency = args.get('totalSquaredEfficiency') as BigDecimal
            if (args.containsKey('maxPowerDraw')) this.maxPowerDraw = args.get('maxPowerDraw') as BigDecimal
            if (args.containsKey('maxTemperature')) this.maxTemperature = args.get('maxTemperature') as BigDecimal
            if (args.containsKey('naturalFrequency')) this.naturalFrequency = args.get('naturalFrequency') as BigDecimal
            if (args.containsKey('avgDampingRatio')) this.avgDampingRatio = args.get('avgDampingRatio') as BigDecimal
            if (args.containsKey('avgPowerSpectralDensity')) this.avgPowerSpectralDensity = args.get('avgPowerSpectralDensity') as BigDecimal
            if (args.containsKey('avgHarmonicDistortion')) this.avgHarmonicDistortion = args.get('avgHarmonicDistortion') as BigDecimal
            if (args.containsKey('avgVibrationLevel')) this.avgVibrationLevel = args.get('avgVibrationLevel') as BigDecimal
            if (args.containsKey('minInertia')) this.minInertia = args.get('minInertia') as BigDecimal
            if (args.containsKey('maxInertia')) this.maxInertia = args.get('maxInertia') as BigDecimal
            if (args.containsKey('avgInertia')) this.avgInertia = args.get('avgInertia') as BigDecimal
            if (args.containsKey('totalSquaredInertia')) this.totalSquaredInertia = args.get('totalSquaredInertia') as BigDecimal
            if (args.containsKey('minFriction')) this.minFriction = args.get('minFriction') as BigDecimal
            if (args.containsKey('maxFriction')) this.maxFriction = args.get('maxFriction') as BigDecimal
            if (args.containsKey('avgFriction')) this.avgFriction = args.get('avgFriction') as BigDecimal
            if (args.containsKey('totalSquaredFriction')) this.totalSquaredFriction = args.get('totalSquaredFriction') as BigDecimal
            if (args.containsKey('minResonance')) this.minResonance = args.get('minResonance') as BigDecimal
            if (args.containsKey('maxResonance')) this.maxResonance = args.get('maxResonance') as BigDecimal
            if (args.containsKey('avgResonance')) this.avgResonance = args.get('avgResonance') as BigDecimal
            if (args.containsKey('totalSquaredResonance')) this.totalSquaredResonance = args.get('totalSquaredResonance') as BigDecimal
            if (args.containsKey('minCogging')) this.minCogging = args.get('minCogging') as BigDecimal
            if (args.containsKey('maxCogging')) this.maxCogging = args.get('maxCogging') as BigDecimal
            if (args.containsKey('avgCogging')) this.avgCogging = args.get('avgCogging') as BigDecimal
            if (args.containsKey('totalSquaredCogging')) this.totalSquaredCogging = args.get('totalSquaredCogging') as BigDecimal
            if (args.containsKey('trajectory')) this.trajectory = args.get('trajectory') as Trajectory
            if (args.containsKey('type')) this.type = args.get('type') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.approximatedFunctionId == null) throw new IllegalStateException("Required property missing: TrajectoryStats.approximatedFunctionId")
    }

    /**
     * Gradle-style closure configurator
     */
    TrajectoryStats configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TrajectoryStats) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Trajectory trajectory(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Trajectory) Closure<?> action) {
        if (this.trajectory == null) this.trajectory = new Trajectory()
        this.trajectory.configure(action)
        this.trajectory
    }
}
