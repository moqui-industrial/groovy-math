/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Trajectory
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
@EqualsAndHashCode(includes = ['approximatedFunctionId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class Trajectory implements Serializable {
    private static final long serialVersionUID = 1L

    String approximatedFunctionId
    String actuationTypeEnumId
    String actuatorTypeEnumId
    String controlMethodEnumId

    // --- Relationships (In-Memory Navigation) ---
    ParametricPath parametricPath
    Object actuationType
    Object actuatorType
    Object controlMethod
    List<TrajectoryPoint> trajectoryPoints = []
    List<TrajectoryRun> trajectoryRun = []
    List<TrajectoryStats> trajectoryStats = []

    Trajectory() { }

    Trajectory(String approximatedFunctionId) {
        this.approximatedFunctionId = Objects.requireNonNull(approximatedFunctionId, "Trajectory.approximatedFunctionId cannot be null")
    }

    Trajectory(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('actuationTypeEnumId')) this.actuationTypeEnumId = args.get('actuationTypeEnumId') as String
            if (args.containsKey('actuatorTypeEnumId')) this.actuatorTypeEnumId = args.get('actuatorTypeEnumId') as String
            if (args.containsKey('controlMethodEnumId')) this.controlMethodEnumId = args.get('controlMethodEnumId') as String
            if (args.containsKey('parametricPath')) this.parametricPath = args.get('parametricPath') as ParametricPath
            if (args.containsKey('actuationType')) this.actuationType = args.get('actuationType') as Object
            if (args.containsKey('actuatorType')) this.actuatorType = args.get('actuatorType') as Object
            if (args.containsKey('controlMethod')) this.controlMethod = args.get('controlMethod') as Object
            if (args.containsKey('trajectoryPoints')) this.trajectoryPoints = args.get('trajectoryPoints') as List<TrajectoryPoint>
            if (args.containsKey('trajectoryRun')) this.trajectoryRun = args.get('trajectoryRun') as List<TrajectoryRun>
            if (args.containsKey('trajectoryStats')) this.trajectoryStats = args.get('trajectoryStats') as List<TrajectoryStats>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
    }

    /**
     * Gradle-style closure configurator
     */
    Trajectory configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Trajectory) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    ParametricPath parametricPath(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPath) Closure<?> action) {
        if (this.parametricPath == null) this.parametricPath = new ParametricPath()
        this.parametricPath.configure(action)
        this.parametricPath
    }

    TrajectoryPoint trajectoryPoints(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TrajectoryPoint) Closure<?> action) {
        TrajectoryPoint item = new TrajectoryPoint()
        item.configure(action)
        if (this.trajectoryPoints == null) this.trajectoryPoints = []
        this.trajectoryPoints.add(item)
        item
    }

    TrajectoryRun trajectoryRun(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TrajectoryRun) Closure<?> action) {
        TrajectoryRun item = new TrajectoryRun()
        item.configure(action)
        if (this.trajectoryRun == null) this.trajectoryRun = []
        this.trajectoryRun.add(item)
        item
    }

    TrajectoryStats trajectoryStats(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TrajectoryStats) Closure<?> action) {
        TrajectoryStats item = new TrajectoryStats()
        item.configure(action)
        if (this.trajectoryStats == null) this.trajectoryStats = []
        this.trajectoryStats.add(item)
        item
    }
}
