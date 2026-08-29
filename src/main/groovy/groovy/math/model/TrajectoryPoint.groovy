/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TrajectoryPoint
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
@EqualsAndHashCode(includes = ['approximatedFunctionId', 'approximatedFunctionSampleId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class TrajectoryPoint implements Serializable {
    private static final long serialVersionUID = 1L

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** approximatedFunctionSampleId */
    String approximatedFunctionSampleId

    /** isBreakPoint */
    String isBreakPoint

    /** breakDuration */
    BigDecimal breakDuration

    /** breakReason */
    String breakReason

    /** blendingEnumId */
    String blendingEnumId

    /** pointTimeOffsetMillis */
    BigDecimal pointTimeOffsetMillis

    /** velocityVectorId */
    String velocityVectorId

    /** accelerationVectorId */
    String accelerationVectorId

    /** jerkVectorId */
    String jerkVectorId

    /** snapVectorId */
    String snapVectorId

    ParametricPathPoint pathPoint

    Vector velocityVector

    Vector accelerationVector

    Vector jerkVector

    Vector snapVector

    TrajectoryPoint() {}

    TrajectoryPoint(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('approximatedFunctionSampleId')) this.approximatedFunctionSampleId = args.get('approximatedFunctionSampleId')?.toString()
            if (args.containsKey('isBreakPoint')) this.isBreakPoint = args.get('isBreakPoint')?.toString()
            if (args.containsKey('breakDuration')) this.breakDuration = args.get('breakDuration') != null ? (args.get('breakDuration') instanceof BigDecimal ? (BigDecimal) args.get('breakDuration') : new BigDecimal(args.get('breakDuration').toString())) : null
            if (args.containsKey('breakReason')) this.breakReason = args.get('breakReason')?.toString()
            if (args.containsKey('blendingEnumId')) this.blendingEnumId = args.get('blendingEnumId')?.toString()
            if (args.containsKey('pointTimeOffsetMillis')) this.pointTimeOffsetMillis = args.get('pointTimeOffsetMillis') != null ? (args.get('pointTimeOffsetMillis') instanceof BigDecimal ? (BigDecimal) args.get('pointTimeOffsetMillis') : new BigDecimal(args.get('pointTimeOffsetMillis').toString())) : null
            if (args.containsKey('velocityVectorId')) this.velocityVectorId = args.get('velocityVectorId')?.toString()
            if (args.containsKey('accelerationVectorId')) this.accelerationVectorId = args.get('accelerationVectorId')?.toString()
            if (args.containsKey('jerkVectorId')) this.jerkVectorId = args.get('jerkVectorId')?.toString()
            if (args.containsKey('snapVectorId')) this.snapVectorId = args.get('snapVectorId')?.toString()
        }
    }

    TrajectoryPoint approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    TrajectoryPoint approximatedFunctionSampleId(String value) {
        this.approximatedFunctionSampleId = value
        return this;
    }

    TrajectoryPoint isBreakPoint(String value) {
        this.isBreakPoint = value
        return this;
    }

    TrajectoryPoint breakDuration(BigDecimal value) {
        this.breakDuration = value
        return this;
    }

    TrajectoryPoint breakReason(String value) {
        this.breakReason = value
        return this;
    }

    TrajectoryPoint blendingEnumId(String value) {
        this.blendingEnumId = value
        return this;
    }

    TrajectoryPoint pointTimeOffsetMillis(BigDecimal value) {
        this.pointTimeOffsetMillis = value
        return this;
    }

    TrajectoryPoint velocityVectorId(String value) {
        this.velocityVectorId = value
        return this;
    }

    TrajectoryPoint accelerationVectorId(String value) {
        this.accelerationVectorId = value
        return this;
    }

    TrajectoryPoint jerkVectorId(String value) {
        this.jerkVectorId = value
        return this;
    }

    TrajectoryPoint snapVectorId(String value) {
        this.snapVectorId = value
        return this;
    }

    TrajectoryPoint pathPoint(ParametricPathPoint item) {
        this.pathPoint = item;
        return this;
    }

    TrajectoryPoint velocityVector(Vector item) {
        this.velocityVector = item;
        return this;
    }

    TrajectoryPoint accelerationVector(Vector item) {
        this.accelerationVector = item;
        return this;
    }

    TrajectoryPoint jerkVector(Vector item) {
        this.jerkVector = item;
        return this;
    }

    TrajectoryPoint snapVector(Vector item) {
        this.snapVector = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.approximatedFunctionSampleId != null) map.put('approximatedFunctionSampleId', this.approximatedFunctionSampleId);
        if (this.isBreakPoint != null) map.put('isBreakPoint', this.isBreakPoint);
        if (this.breakDuration != null) map.put('breakDuration', this.breakDuration);
        if (this.breakReason != null) map.put('breakReason', this.breakReason);
        if (this.blendingEnumId != null) map.put('blendingEnumId', this.blendingEnumId);
        if (this.pointTimeOffsetMillis != null) map.put('pointTimeOffsetMillis', this.pointTimeOffsetMillis);
        if (this.velocityVectorId != null) map.put('velocityVectorId', this.velocityVectorId);
        if (this.accelerationVectorId != null) map.put('accelerationVectorId', this.accelerationVectorId);
        if (this.jerkVectorId != null) map.put('jerkVectorId', this.jerkVectorId);
        if (this.snapVectorId != null) map.put('snapVectorId', this.snapVectorId);
        return map;
    }
}