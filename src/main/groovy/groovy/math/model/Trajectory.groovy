/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Trajectory
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
@EqualsAndHashCode(includes = ['approximatedFunctionId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Trajectory implements Serializable {
    private static final long serialVersionUID = 1L

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** actuationTypeEnumId */
    String actuationTypeEnumId

    /** actuatorTypeEnumId */
    String actuatorTypeEnumId

    /** controlMethodEnumId */
    String controlMethodEnumId

    ParametricPath parametricPath

    List<TrajectoryPoint> trajectoryPoints = new ArrayList<>()

    List<TrajectoryRun> trajectoryRun = new ArrayList<>()

    List<TrajectoryStats> trajectoryStats = new ArrayList<>()

    Trajectory() {}

    Trajectory(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('actuationTypeEnumId')) this.actuationTypeEnumId = args.get('actuationTypeEnumId')?.toString()
            if (args.containsKey('actuatorTypeEnumId')) this.actuatorTypeEnumId = args.get('actuatorTypeEnumId')?.toString()
            if (args.containsKey('controlMethodEnumId')) this.controlMethodEnumId = args.get('controlMethodEnumId')?.toString()
        }
    }

    Trajectory approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    Trajectory actuationTypeEnumId(String value) {
        this.actuationTypeEnumId = value
        return this;
    }

    Trajectory actuatorTypeEnumId(String value) {
        this.actuatorTypeEnumId = value
        return this;
    }

    Trajectory controlMethodEnumId(String value) {
        this.controlMethodEnumId = value
        return this;
    }

    Trajectory parametricPath(ParametricPath item) {
        this.parametricPath = item;
        return this;
    }

    Trajectory trajectoryPoints(List<TrajectoryPoint> list) {
        this.trajectoryPoints = list;
        return this;
    }

    Trajectory trajectoryRun(List<TrajectoryRun> list) {
        this.trajectoryRun = list;
        return this;
    }

    Trajectory trajectoryStats(List<TrajectoryStats> list) {
        this.trajectoryStats = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.actuationTypeEnumId != null) map.put('actuationTypeEnumId', this.actuationTypeEnumId);
        if (this.actuatorTypeEnumId != null) map.put('actuatorTypeEnumId', this.actuatorTypeEnumId);
        if (this.controlMethodEnumId != null) map.put('controlMethodEnumId', this.controlMethodEnumId);
        return map;
    }
}