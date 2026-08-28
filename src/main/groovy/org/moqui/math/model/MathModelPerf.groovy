/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelPerf
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
@EqualsAndHashCode(includes = ['mathModelPerfId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MathModelPerf implements Serializable {
    private static final long serialVersionUID = 1L

    String mathModelPerfId
    String mathModelRunId // Required
    BigDecimal totalDurationSec
    BigDecimal inferenceLatencyMs
    BigDecimal cpuSeconds
    BigDecimal gpuSeconds
    BigDecimal memoryPeakMb
    Long solverIterations
    Long epochs
    Long totalBatches
    BigDecimal trainLossFinal
    BigDecimal valLossFinal
    BigDecimal top1Accuracy
    BigDecimal top5Accuracy
    BigDecimal f1Score
    BigDecimal rocAuc
    Long paramCount
    BigDecimal theoreticalFlops
    BigDecimal throughputSamplesSec
    BigDecimal gpuMemoryPeakMb

    // --- Relationships (In-Memory Navigation) ---
    MathModelRun run

    MathModelPerf() { }

    MathModelPerf(String mathModelPerfId) {
        this.mathModelPerfId = Objects.requireNonNull(mathModelPerfId, "MathModelPerf.mathModelPerfId cannot be null")
    }

    MathModelPerf(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('mathModelPerfId')) this.mathModelPerfId = args.get('mathModelPerfId') as String
            if (args.containsKey('mathModelRunId')) this.mathModelRunId = args.get('mathModelRunId') as String
            if (args.containsKey('totalDurationSec')) this.totalDurationSec = args.get('totalDurationSec') as BigDecimal
            if (args.containsKey('inferenceLatencyMs')) this.inferenceLatencyMs = args.get('inferenceLatencyMs') as BigDecimal
            if (args.containsKey('cpuSeconds')) this.cpuSeconds = args.get('cpuSeconds') as BigDecimal
            if (args.containsKey('gpuSeconds')) this.gpuSeconds = args.get('gpuSeconds') as BigDecimal
            if (args.containsKey('memoryPeakMb')) this.memoryPeakMb = args.get('memoryPeakMb') as BigDecimal
            if (args.containsKey('solverIterations')) this.solverIterations = args.get('solverIterations') as Long
            if (args.containsKey('epochs')) this.epochs = args.get('epochs') as Long
            if (args.containsKey('totalBatches')) this.totalBatches = args.get('totalBatches') as Long
            if (args.containsKey('trainLossFinal')) this.trainLossFinal = args.get('trainLossFinal') as BigDecimal
            if (args.containsKey('valLossFinal')) this.valLossFinal = args.get('valLossFinal') as BigDecimal
            if (args.containsKey('top1Accuracy')) this.top1Accuracy = args.get('top1Accuracy') as BigDecimal
            if (args.containsKey('top5Accuracy')) this.top5Accuracy = args.get('top5Accuracy') as BigDecimal
            if (args.containsKey('f1Score')) this.f1Score = args.get('f1Score') as BigDecimal
            if (args.containsKey('rocAuc')) this.rocAuc = args.get('rocAuc') as BigDecimal
            if (args.containsKey('paramCount')) this.paramCount = args.get('paramCount') as Long
            if (args.containsKey('theoreticalFlops')) this.theoreticalFlops = args.get('theoreticalFlops') as BigDecimal
            if (args.containsKey('throughputSamplesSec')) this.throughputSamplesSec = args.get('throughputSamplesSec') as BigDecimal
            if (args.containsKey('gpuMemoryPeakMb')) this.gpuMemoryPeakMb = args.get('gpuMemoryPeakMb') as BigDecimal
            if (args.containsKey('run')) this.run = args.get('run') as MathModelRun
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.mathModelRunId == null) throw new IllegalStateException("Required property missing: MathModelPerf.mathModelRunId")
    }

    /**
     * Gradle-style closure configurator
     */
    MathModelPerf configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelPerf) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MathModelRun run(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelRun) Closure<?> action) {
        if (this.run == null) this.run = new MathModelRun()
        this.run.configure(action)
        this.run
    }
}
