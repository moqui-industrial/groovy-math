/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TrajectoryPoint
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
@EqualsAndHashCode(includes = ['approximatedFunctionId', 'approximatedFunctionSampleId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class TrajectoryPoint implements Serializable {
    private static final long serialVersionUID = 1L

    String approximatedFunctionId
    String approximatedFunctionSampleId
    String isBreakPoint
    BigDecimal breakDuration
    String breakReason
    String blendingEnumId
    BigDecimal pointTimeOffsetMillis
    String velocityVectorId
    String accelerationVectorId
    String jerkVectorId
    String snapVectorId

    // --- Relationships (In-Memory Navigation) ---
    ParametricPathPoint pathPoint
    Vector velocityVector
    Vector accelerationVector
    Vector jerkVector
    Vector snapVector
    Object blending

    TrajectoryPoint() { }

    TrajectoryPoint(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('approximatedFunctionSampleId')) this.approximatedFunctionSampleId = args.get('approximatedFunctionSampleId') as String
            if (args.containsKey('isBreakPoint')) this.isBreakPoint = args.get('isBreakPoint') as String
            if (args.containsKey('breakDuration')) this.breakDuration = args.get('breakDuration') as BigDecimal
            if (args.containsKey('breakReason')) this.breakReason = args.get('breakReason') as String
            if (args.containsKey('blendingEnumId')) this.blendingEnumId = args.get('blendingEnumId') as String
            if (args.containsKey('pointTimeOffsetMillis')) this.pointTimeOffsetMillis = args.get('pointTimeOffsetMillis') as BigDecimal
            if (args.containsKey('velocityVectorId')) this.velocityVectorId = args.get('velocityVectorId') as String
            if (args.containsKey('accelerationVectorId')) this.accelerationVectorId = args.get('accelerationVectorId') as String
            if (args.containsKey('jerkVectorId')) this.jerkVectorId = args.get('jerkVectorId') as String
            if (args.containsKey('snapVectorId')) this.snapVectorId = args.get('snapVectorId') as String
            if (args.containsKey('pathPoint')) this.pathPoint = args.get('pathPoint') as ParametricPathPoint
            if (args.containsKey('velocityVector')) this.velocityVector = args.get('velocityVector') as Vector
            if (args.containsKey('accelerationVector')) this.accelerationVector = args.get('accelerationVector') as Vector
            if (args.containsKey('jerkVector')) this.jerkVector = args.get('jerkVector') as Vector
            if (args.containsKey('snapVector')) this.snapVector = args.get('snapVector') as Vector
            if (args.containsKey('blending')) this.blending = args.get('blending') as Object
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
    TrajectoryPoint configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TrajectoryPoint) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    ParametricPathPoint pathPoint(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPathPoint) Closure<?> action) {
        if (this.pathPoint == null) this.pathPoint = new ParametricPathPoint()
        this.pathPoint.configure(action)
        this.pathPoint
    }

    Vector velocityVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.velocityVector == null) this.velocityVector = new Vector()
        this.velocityVector.configure(action)
        this.velocityVector
    }

    Vector accelerationVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.accelerationVector == null) this.accelerationVector = new Vector()
        this.accelerationVector.configure(action)
        this.accelerationVector
    }

    Vector jerkVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.jerkVector == null) this.jerkVector = new Vector()
        this.jerkVector.configure(action)
        this.jerkVector
    }

    Vector snapVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.snapVector == null) this.snapVector = new Vector()
        this.snapVector.configure(action)
        this.snapVector
    }
}
