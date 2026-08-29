/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelRun
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
@EqualsAndHashCode(includes = ['mathModelRunId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MathModelRun implements Serializable {
    private static final long serialVersionUID = 1L

    /** mathModelRunId */
    String mathModelRunId

    /** mathModelId */
    String mathModelId

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** startTime */
    java.sql.Timestamp startTime

    /** endDate */
    java.sql.Timestamp endDate

    /** runningTimeMillis */
    Double runningTimeMillis

    /** isSlowHit */
    String isSlowHit

    /** parameters */
    String parameters

    /** results */
    String results

    /** messages */
    String messages

    /** hasError */
    String hasError

    /** errors */
    String errors

    /** userId */
    String userId

    MathModel model

    Trajectory trajectory

    List<MathModelEvent> events = new ArrayList<>()

    List<MathModelPerf> performances = new ArrayList<>()

    MathModelRun() {}

    MathModelRun(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('mathModelRunId')) this.mathModelRunId = args.get('mathModelRunId')?.toString()
            if (args.containsKey('mathModelId')) this.mathModelId = args.get('mathModelId')?.toString()
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('startTime')) this.startTime = (java.sql.Timestamp) args.get('startTime')
            if (args.containsKey('endDate')) this.endDate = (java.sql.Timestamp) args.get('endDate')
            if (args.containsKey('runningTimeMillis')) this.runningTimeMillis = args.get('runningTimeMillis') != null ? ((Number) args.get('runningTimeMillis')).doubleValue() : null
            if (args.containsKey('isSlowHit')) this.isSlowHit = args.get('isSlowHit')?.toString()
            if (args.containsKey('parameters')) this.parameters = args.get('parameters')?.toString()
            if (args.containsKey('results')) this.results = args.get('results')?.toString()
            if (args.containsKey('messages')) this.messages = args.get('messages')?.toString()
            if (args.containsKey('hasError')) this.hasError = args.get('hasError')?.toString()
            if (args.containsKey('errors')) this.errors = args.get('errors')?.toString()
            if (args.containsKey('userId')) this.userId = args.get('userId')?.toString()
        }
    }

    MathModelRun mathModelRunId(String value) {
        this.mathModelRunId = value
        return this;
    }

    MathModelRun mathModelId(String value) {
        this.mathModelId = value
        return this;
    }

    MathModelRun approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    MathModelRun startTime(java.sql.Timestamp value) {
        this.startTime = value
        return this;
    }

    MathModelRun endDate(java.sql.Timestamp value) {
        this.endDate = value
        return this;
    }

    MathModelRun runningTimeMillis(Double value) {
        this.runningTimeMillis = value
        return this;
    }

    MathModelRun isSlowHit(String value) {
        this.isSlowHit = value
        return this;
    }

    MathModelRun parameters(String value) {
        this.parameters = value
        return this;
    }

    MathModelRun results(String value) {
        this.results = value
        return this;
    }

    MathModelRun messages(String value) {
        this.messages = value
        return this;
    }

    MathModelRun hasError(String value) {
        this.hasError = value
        return this;
    }

    MathModelRun errors(String value) {
        this.errors = value
        return this;
    }

    MathModelRun userId(String value) {
        this.userId = value
        return this;
    }

    MathModelRun model(MathModel item) {
        this.model = item;
        return this;
    }

    MathModelRun trajectory(Trajectory item) {
        this.trajectory = item;
        return this;
    }

    MathModelRun events(List<MathModelEvent> list) {
        this.events = list;
        return this;
    }

    MathModelRun performances(List<MathModelPerf> list) {
        this.performances = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.mathModelRunId != null) map.put('mathModelRunId', this.mathModelRunId);
        if (this.mathModelId != null) map.put('mathModelId', this.mathModelId);
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.startTime != null) map.put('startTime', this.startTime);
        if (this.endDate != null) map.put('endDate', this.endDate);
        if (this.runningTimeMillis != null) map.put('runningTimeMillis', this.runningTimeMillis);
        if (this.isSlowHit != null) map.put('isSlowHit', this.isSlowHit);
        if (this.parameters != null) map.put('parameters', this.parameters);
        if (this.results != null) map.put('results', this.results);
        if (this.messages != null) map.put('messages', this.messages);
        if (this.hasError != null) map.put('hasError', this.hasError);
        if (this.errors != null) map.put('errors', this.errors);
        if (this.userId != null) map.put('userId', this.userId);
        return map;
    }
}