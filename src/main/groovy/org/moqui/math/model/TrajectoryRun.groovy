/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TrajectoryRun
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
@EqualsAndHashCode(includes = ['trajectoryRunId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class TrajectoryRun implements Serializable {
    private static final long serialVersionUID = 1L

    String trajectoryRunId
    String approximatedFunctionId // Required
    String lastTrajectoryPointId
    String lastSuccessfulTrajectoryPointId
    java.sql.Timestamp estimatedStartDateTime
    java.sql.Timestamp estimatedCompletionDateTime
    BigDecimal estimatedDurationMillis
    BigDecimal totalTimeMillisAllowed
    java.sql.Timestamp actualStartDateTime
    java.sql.Timestamp actualCompletionDateTime
    BigDecimal actualDurationMillis
    BigDecimal latency
    BigDecimal jitter
    BigDecimal totalDisplacement
    BigDecimal minVelocity
    BigDecimal maxVelocity
    BigDecimal avgVelocity
    BigDecimal minAcceleration
    BigDecimal maxAcceleration
    BigDecimal avgAcceleration
    BigDecimal minJerk
    BigDecimal maxJerk
    BigDecimal avgJerk
    BigDecimal minSnap
    BigDecimal maxSnap
    BigDecimal avgSnap
    BigDecimal minPositionError
    BigDecimal maxPositionError
    BigDecimal avgPositionError
    BigDecimal minVelocityError
    BigDecimal maxVelocityError
    BigDecimal avgVelocityError
    BigDecimal minAccelerationError
    BigDecimal maxAccelerationError
    BigDecimal avgAccelerationError
    BigDecimal minJerkError
    BigDecimal maxJerkError
    BigDecimal avgJerkError
    BigDecimal minSnapError
    BigDecimal maxSnapError
    BigDecimal avgSnapError
    BigDecimal avgCompositeError
    BigDecimal pointSuccessRate
    BigDecimal reliability
    String hasError
    String errors
    String isSlowRun
    String isDelayed
    BigDecimal settlingTime
    BigDecimal riseTime
    BigDecimal peakTime
    BigDecimal steadyStateError
    BigDecimal overshootPercentage
    BigDecimal integralAbsoluteError
    BigDecimal integralSquaredError
    BigDecimal integralTimeAbsoluteError
    BigDecimal integralTimeSquaredError
    BigDecimal accuracy
    BigDecimal pathDeviationIndex
    BigDecimal circularityError
    BigDecimal linearityError
    BigDecimal naturalFrequency
    BigDecimal dampingRatio
    BigDecimal powerSpectralDensity
    BigDecimal harmonicDistortion
    BigDecimal vibrationLevel
    BigDecimal inertia
    BigDecimal friction
    BigDecimal resonance
    BigDecimal cogging
    BigDecimal energyConsumption
    BigDecimal controlEffort
    BigDecimal efficiency
    BigDecimal maxPowerDraw
    BigDecimal maxTemperature

    // --- Relationships (In-Memory Navigation) ---
    Trajectory trajectory
    TrajectoryPoint lastTrajectoryPoint
    TrajectoryPoint lastSuccessfulTrajectoryPoint

    TrajectoryRun() { }

    TrajectoryRun(String trajectoryRunId) {
        this.trajectoryRunId = Objects.requireNonNull(trajectoryRunId, "TrajectoryRun.trajectoryRunId cannot be null")
    }

    TrajectoryRun(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('trajectoryRunId')) this.trajectoryRunId = args.get('trajectoryRunId') as String
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('lastTrajectoryPointId')) this.lastTrajectoryPointId = args.get('lastTrajectoryPointId') as String
            if (args.containsKey('lastSuccessfulTrajectoryPointId')) this.lastSuccessfulTrajectoryPointId = args.get('lastSuccessfulTrajectoryPointId') as String
            if (args.containsKey('estimatedStartDateTime')) this.estimatedStartDateTime = args.get('estimatedStartDateTime') as java.sql.Timestamp
            if (args.containsKey('estimatedCompletionDateTime')) this.estimatedCompletionDateTime = args.get('estimatedCompletionDateTime') as java.sql.Timestamp
            if (args.containsKey('estimatedDurationMillis')) this.estimatedDurationMillis = args.get('estimatedDurationMillis') as BigDecimal
            if (args.containsKey('totalTimeMillisAllowed')) this.totalTimeMillisAllowed = args.get('totalTimeMillisAllowed') as BigDecimal
            if (args.containsKey('actualStartDateTime')) this.actualStartDateTime = args.get('actualStartDateTime') as java.sql.Timestamp
            if (args.containsKey('actualCompletionDateTime')) this.actualCompletionDateTime = args.get('actualCompletionDateTime') as java.sql.Timestamp
            if (args.containsKey('actualDurationMillis')) this.actualDurationMillis = args.get('actualDurationMillis') as BigDecimal
            if (args.containsKey('latency')) this.latency = args.get('latency') as BigDecimal
            if (args.containsKey('jitter')) this.jitter = args.get('jitter') as BigDecimal
            if (args.containsKey('totalDisplacement')) this.totalDisplacement = args.get('totalDisplacement') as BigDecimal
            if (args.containsKey('minVelocity')) this.minVelocity = args.get('minVelocity') as BigDecimal
            if (args.containsKey('maxVelocity')) this.maxVelocity = args.get('maxVelocity') as BigDecimal
            if (args.containsKey('avgVelocity')) this.avgVelocity = args.get('avgVelocity') as BigDecimal
            if (args.containsKey('minAcceleration')) this.minAcceleration = args.get('minAcceleration') as BigDecimal
            if (args.containsKey('maxAcceleration')) this.maxAcceleration = args.get('maxAcceleration') as BigDecimal
            if (args.containsKey('avgAcceleration')) this.avgAcceleration = args.get('avgAcceleration') as BigDecimal
            if (args.containsKey('minJerk')) this.minJerk = args.get('minJerk') as BigDecimal
            if (args.containsKey('maxJerk')) this.maxJerk = args.get('maxJerk') as BigDecimal
            if (args.containsKey('avgJerk')) this.avgJerk = args.get('avgJerk') as BigDecimal
            if (args.containsKey('minSnap')) this.minSnap = args.get('minSnap') as BigDecimal
            if (args.containsKey('maxSnap')) this.maxSnap = args.get('maxSnap') as BigDecimal
            if (args.containsKey('avgSnap')) this.avgSnap = args.get('avgSnap') as BigDecimal
            if (args.containsKey('minPositionError')) this.minPositionError = args.get('minPositionError') as BigDecimal
            if (args.containsKey('maxPositionError')) this.maxPositionError = args.get('maxPositionError') as BigDecimal
            if (args.containsKey('avgPositionError')) this.avgPositionError = args.get('avgPositionError') as BigDecimal
            if (args.containsKey('minVelocityError')) this.minVelocityError = args.get('minVelocityError') as BigDecimal
            if (args.containsKey('maxVelocityError')) this.maxVelocityError = args.get('maxVelocityError') as BigDecimal
            if (args.containsKey('avgVelocityError')) this.avgVelocityError = args.get('avgVelocityError') as BigDecimal
            if (args.containsKey('minAccelerationError')) this.minAccelerationError = args.get('minAccelerationError') as BigDecimal
            if (args.containsKey('maxAccelerationError')) this.maxAccelerationError = args.get('maxAccelerationError') as BigDecimal
            if (args.containsKey('avgAccelerationError')) this.avgAccelerationError = args.get('avgAccelerationError') as BigDecimal
            if (args.containsKey('minJerkError')) this.minJerkError = args.get('minJerkError') as BigDecimal
            if (args.containsKey('maxJerkError')) this.maxJerkError = args.get('maxJerkError') as BigDecimal
            if (args.containsKey('avgJerkError')) this.avgJerkError = args.get('avgJerkError') as BigDecimal
            if (args.containsKey('minSnapError')) this.minSnapError = args.get('minSnapError') as BigDecimal
            if (args.containsKey('maxSnapError')) this.maxSnapError = args.get('maxSnapError') as BigDecimal
            if (args.containsKey('avgSnapError')) this.avgSnapError = args.get('avgSnapError') as BigDecimal
            if (args.containsKey('avgCompositeError')) this.avgCompositeError = args.get('avgCompositeError') as BigDecimal
            if (args.containsKey('pointSuccessRate')) this.pointSuccessRate = args.get('pointSuccessRate') as BigDecimal
            if (args.containsKey('reliability')) this.reliability = args.get('reliability') as BigDecimal
            if (args.containsKey('hasError')) this.hasError = args.get('hasError') as String
            if (args.containsKey('errors')) this.errors = args.get('errors') as String
            if (args.containsKey('isSlowRun')) this.isSlowRun = args.get('isSlowRun') as String
            if (args.containsKey('isDelayed')) this.isDelayed = args.get('isDelayed') as String
            if (args.containsKey('settlingTime')) this.settlingTime = args.get('settlingTime') as BigDecimal
            if (args.containsKey('riseTime')) this.riseTime = args.get('riseTime') as BigDecimal
            if (args.containsKey('peakTime')) this.peakTime = args.get('peakTime') as BigDecimal
            if (args.containsKey('steadyStateError')) this.steadyStateError = args.get('steadyStateError') as BigDecimal
            if (args.containsKey('overshootPercentage')) this.overshootPercentage = args.get('overshootPercentage') as BigDecimal
            if (args.containsKey('integralAbsoluteError')) this.integralAbsoluteError = args.get('integralAbsoluteError') as BigDecimal
            if (args.containsKey('integralSquaredError')) this.integralSquaredError = args.get('integralSquaredError') as BigDecimal
            if (args.containsKey('integralTimeAbsoluteError')) this.integralTimeAbsoluteError = args.get('integralTimeAbsoluteError') as BigDecimal
            if (args.containsKey('integralTimeSquaredError')) this.integralTimeSquaredError = args.get('integralTimeSquaredError') as BigDecimal
            if (args.containsKey('accuracy')) this.accuracy = args.get('accuracy') as BigDecimal
            if (args.containsKey('pathDeviationIndex')) this.pathDeviationIndex = args.get('pathDeviationIndex') as BigDecimal
            if (args.containsKey('circularityError')) this.circularityError = args.get('circularityError') as BigDecimal
            if (args.containsKey('linearityError')) this.linearityError = args.get('linearityError') as BigDecimal
            if (args.containsKey('naturalFrequency')) this.naturalFrequency = args.get('naturalFrequency') as BigDecimal
            if (args.containsKey('dampingRatio')) this.dampingRatio = args.get('dampingRatio') as BigDecimal
            if (args.containsKey('powerSpectralDensity')) this.powerSpectralDensity = args.get('powerSpectralDensity') as BigDecimal
            if (args.containsKey('harmonicDistortion')) this.harmonicDistortion = args.get('harmonicDistortion') as BigDecimal
            if (args.containsKey('vibrationLevel')) this.vibrationLevel = args.get('vibrationLevel') as BigDecimal
            if (args.containsKey('inertia')) this.inertia = args.get('inertia') as BigDecimal
            if (args.containsKey('friction')) this.friction = args.get('friction') as BigDecimal
            if (args.containsKey('resonance')) this.resonance = args.get('resonance') as BigDecimal
            if (args.containsKey('cogging')) this.cogging = args.get('cogging') as BigDecimal
            if (args.containsKey('energyConsumption')) this.energyConsumption = args.get('energyConsumption') as BigDecimal
            if (args.containsKey('controlEffort')) this.controlEffort = args.get('controlEffort') as BigDecimal
            if (args.containsKey('efficiency')) this.efficiency = args.get('efficiency') as BigDecimal
            if (args.containsKey('maxPowerDraw')) this.maxPowerDraw = args.get('maxPowerDraw') as BigDecimal
            if (args.containsKey('maxTemperature')) this.maxTemperature = args.get('maxTemperature') as BigDecimal
            if (args.containsKey('trajectory')) this.trajectory = args.get('trajectory') as Trajectory
            if (args.containsKey('lastTrajectoryPoint')) this.lastTrajectoryPoint = args.get('lastTrajectoryPoint') as TrajectoryPoint
            if (args.containsKey('lastSuccessfulTrajectoryPoint')) this.lastSuccessfulTrajectoryPoint = args.get('lastSuccessfulTrajectoryPoint') as TrajectoryPoint
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.approximatedFunctionId == null) throw new IllegalStateException("Required property missing: TrajectoryRun.approximatedFunctionId")
    }

    /**
     * Gradle-style closure configurator
     */
    TrajectoryRun configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TrajectoryRun) Closure<?> action) {
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

    TrajectoryPoint lastTrajectoryPoint(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TrajectoryPoint) Closure<?> action) {
        if (this.lastTrajectoryPoint == null) this.lastTrajectoryPoint = new TrajectoryPoint()
        this.lastTrajectoryPoint.configure(action)
        this.lastTrajectoryPoint
    }

    TrajectoryPoint lastSuccessfulTrajectoryPoint(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TrajectoryPoint) Closure<?> action) {
        if (this.lastSuccessfulTrajectoryPoint == null) this.lastSuccessfulTrajectoryPoint = new TrajectoryPoint()
        this.lastSuccessfulTrajectoryPoint.configure(action)
        this.lastSuccessfulTrajectoryPoint
    }
}
