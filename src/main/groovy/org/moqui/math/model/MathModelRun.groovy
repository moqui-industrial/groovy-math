/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelRun
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
@EqualsAndHashCode(includes = ['mathModelRunId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MathModelRun implements Serializable {
    private static final long serialVersionUID = 1L

    String mathModelRunId
    String mathModelId // Required
    String approximatedFunctionId
    java.sql.Timestamp startTime
    java.sql.Timestamp endDate
    Double runningTimeMillis
    String isSlowHit
    String parameters
    String results
    String messages
    String hasError
    String errors
    String userId

    // --- Relationships (In-Memory Navigation) ---
    MathModel model
    Trajectory trajectory
    Object userAccount
    List<MathModelEvent> events = []
    List<MathModelPerf> performances = []

    MathModelRun() { }

    MathModelRun(String mathModelRunId) {
        this.mathModelRunId = Objects.requireNonNull(mathModelRunId, "MathModelRun.mathModelRunId cannot be null")
    }

    MathModelRun(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('mathModelRunId')) this.mathModelRunId = args.get('mathModelRunId') as String
            if (args.containsKey('mathModelId')) this.mathModelId = args.get('mathModelId') as String
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('startTime')) this.startTime = args.get('startTime') as java.sql.Timestamp
            if (args.containsKey('endDate')) this.endDate = args.get('endDate') as java.sql.Timestamp
            if (args.containsKey('runningTimeMillis')) this.runningTimeMillis = args.get('runningTimeMillis') as Double
            if (args.containsKey('isSlowHit')) this.isSlowHit = args.get('isSlowHit') as String
            if (args.containsKey('parameters')) this.parameters = args.get('parameters') as String
            if (args.containsKey('results')) this.results = args.get('results') as String
            if (args.containsKey('messages')) this.messages = args.get('messages') as String
            if (args.containsKey('hasError')) this.hasError = args.get('hasError') as String
            if (args.containsKey('errors')) this.errors = args.get('errors') as String
            if (args.containsKey('userId')) this.userId = args.get('userId') as String
            if (args.containsKey('model')) this.model = args.get('model') as MathModel
            if (args.containsKey('trajectory')) this.trajectory = args.get('trajectory') as Trajectory
            if (args.containsKey('userAccount')) this.userAccount = args.get('userAccount') as Object
            if (args.containsKey('events')) this.events = args.get('events') as List<MathModelEvent>
            if (args.containsKey('performances')) this.performances = args.get('performances') as List<MathModelPerf>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.mathModelId == null) throw new IllegalStateException("Required property missing: MathModelRun.mathModelId")
    }

    /**
     * Gradle-style closure configurator
     */
    MathModelRun configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelRun) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MathModel model(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModel) Closure<?> action) {
        if (this.model == null) this.model = new MathModel()
        this.model.configure(action)
        this.model
    }

    Trajectory trajectory(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Trajectory) Closure<?> action) {
        if (this.trajectory == null) this.trajectory = new Trajectory()
        this.trajectory.configure(action)
        this.trajectory
    }

    MathModelEvent events(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelEvent) Closure<?> action) {
        MathModelEvent item = new MathModelEvent()
        item.configure(action)
        if (this.events == null) this.events = []
        this.events.add(item)
        item
    }

    MathModelPerf performances(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelPerf) Closure<?> action) {
        MathModelPerf item = new MathModelPerf()
        item.configure(action)
        if (this.performances == null) this.performances = []
        this.performances.add(item)
        item
    }
}
