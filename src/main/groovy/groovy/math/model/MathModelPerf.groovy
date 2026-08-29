/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelPerf
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
@EqualsAndHashCode(includes = ['mathModelPerfId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MathModelPerf implements Serializable {
    private static final long serialVersionUID = 1L

    /** mathModelPerfId */
    String mathModelPerfId

    /** mathModelRunId */
    String mathModelRunId

    /** totalDurationSec */
    BigDecimal totalDurationSec

    /** inferenceLatencyMs */
    BigDecimal inferenceLatencyMs

    /** cpuSeconds */
    BigDecimal cpuSeconds

    /** gpuSeconds */
    BigDecimal gpuSeconds

    /** memoryPeakMb */
    BigDecimal memoryPeakMb

    /** solverIterations */
    Long solverIterations

    /** epochs */
    Long epochs

    /** totalBatches */
    Long totalBatches

    /** trainLossFinal */
    BigDecimal trainLossFinal

    /** valLossFinal */
    BigDecimal valLossFinal

    /** top1Accuracy */
    BigDecimal top1Accuracy

    /** top5Accuracy */
    BigDecimal top5Accuracy

    /** f1Score */
    BigDecimal f1Score

    /** rocAuc */
    BigDecimal rocAuc

    /** paramCount */
    Long paramCount

    /** theoreticalFlops */
    BigDecimal theoreticalFlops

    /** throughputSamplesSec */
    BigDecimal throughputSamplesSec

    /** gpuMemoryPeakMb */
    BigDecimal gpuMemoryPeakMb

    MathModelRun run

    MathModelPerf() {}

    MathModelPerf(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('mathModelPerfId')) this.mathModelPerfId = args.get('mathModelPerfId')?.toString()
            if (args.containsKey('mathModelRunId')) this.mathModelRunId = args.get('mathModelRunId')?.toString()
            if (args.containsKey('totalDurationSec')) this.totalDurationSec = args.get('totalDurationSec') != null ? (args.get('totalDurationSec') instanceof BigDecimal ? (BigDecimal) args.get('totalDurationSec') : new BigDecimal(args.get('totalDurationSec').toString())) : null
            if (args.containsKey('inferenceLatencyMs')) this.inferenceLatencyMs = args.get('inferenceLatencyMs') != null ? (args.get('inferenceLatencyMs') instanceof BigDecimal ? (BigDecimal) args.get('inferenceLatencyMs') : new BigDecimal(args.get('inferenceLatencyMs').toString())) : null
            if (args.containsKey('cpuSeconds')) this.cpuSeconds = args.get('cpuSeconds') != null ? (args.get('cpuSeconds') instanceof BigDecimal ? (BigDecimal) args.get('cpuSeconds') : new BigDecimal(args.get('cpuSeconds').toString())) : null
            if (args.containsKey('gpuSeconds')) this.gpuSeconds = args.get('gpuSeconds') != null ? (args.get('gpuSeconds') instanceof BigDecimal ? (BigDecimal) args.get('gpuSeconds') : new BigDecimal(args.get('gpuSeconds').toString())) : null
            if (args.containsKey('memoryPeakMb')) this.memoryPeakMb = args.get('memoryPeakMb') != null ? (args.get('memoryPeakMb') instanceof BigDecimal ? (BigDecimal) args.get('memoryPeakMb') : new BigDecimal(args.get('memoryPeakMb').toString())) : null
            if (args.containsKey('solverIterations')) this.solverIterations = args.get('solverIterations') != null ? ((Number) args.get('solverIterations')).longValue() : null
            if (args.containsKey('epochs')) this.epochs = args.get('epochs') != null ? ((Number) args.get('epochs')).longValue() : null
            if (args.containsKey('totalBatches')) this.totalBatches = args.get('totalBatches') != null ? ((Number) args.get('totalBatches')).longValue() : null
            if (args.containsKey('trainLossFinal')) this.trainLossFinal = args.get('trainLossFinal') != null ? (args.get('trainLossFinal') instanceof BigDecimal ? (BigDecimal) args.get('trainLossFinal') : new BigDecimal(args.get('trainLossFinal').toString())) : null
            if (args.containsKey('valLossFinal')) this.valLossFinal = args.get('valLossFinal') != null ? (args.get('valLossFinal') instanceof BigDecimal ? (BigDecimal) args.get('valLossFinal') : new BigDecimal(args.get('valLossFinal').toString())) : null
            if (args.containsKey('top1Accuracy')) this.top1Accuracy = args.get('top1Accuracy') != null ? (args.get('top1Accuracy') instanceof BigDecimal ? (BigDecimal) args.get('top1Accuracy') : new BigDecimal(args.get('top1Accuracy').toString())) : null
            if (args.containsKey('top5Accuracy')) this.top5Accuracy = args.get('top5Accuracy') != null ? (args.get('top5Accuracy') instanceof BigDecimal ? (BigDecimal) args.get('top5Accuracy') : new BigDecimal(args.get('top5Accuracy').toString())) : null
            if (args.containsKey('f1Score')) this.f1Score = args.get('f1Score') != null ? (args.get('f1Score') instanceof BigDecimal ? (BigDecimal) args.get('f1Score') : new BigDecimal(args.get('f1Score').toString())) : null
            if (args.containsKey('rocAuc')) this.rocAuc = args.get('rocAuc') != null ? (args.get('rocAuc') instanceof BigDecimal ? (BigDecimal) args.get('rocAuc') : new BigDecimal(args.get('rocAuc').toString())) : null
            if (args.containsKey('paramCount')) this.paramCount = args.get('paramCount') != null ? ((Number) args.get('paramCount')).longValue() : null
            if (args.containsKey('theoreticalFlops')) this.theoreticalFlops = args.get('theoreticalFlops') != null ? (args.get('theoreticalFlops') instanceof BigDecimal ? (BigDecimal) args.get('theoreticalFlops') : new BigDecimal(args.get('theoreticalFlops').toString())) : null
            if (args.containsKey('throughputSamplesSec')) this.throughputSamplesSec = args.get('throughputSamplesSec') != null ? (args.get('throughputSamplesSec') instanceof BigDecimal ? (BigDecimal) args.get('throughputSamplesSec') : new BigDecimal(args.get('throughputSamplesSec').toString())) : null
            if (args.containsKey('gpuMemoryPeakMb')) this.gpuMemoryPeakMb = args.get('gpuMemoryPeakMb') != null ? (args.get('gpuMemoryPeakMb') instanceof BigDecimal ? (BigDecimal) args.get('gpuMemoryPeakMb') : new BigDecimal(args.get('gpuMemoryPeakMb').toString())) : null
        }
    }

    MathModelPerf mathModelPerfId(String value) {
        this.mathModelPerfId = value
        return this;
    }

    MathModelPerf mathModelRunId(String value) {
        this.mathModelRunId = value
        return this;
    }

    MathModelPerf totalDurationSec(BigDecimal value) {
        this.totalDurationSec = value
        return this;
    }

    MathModelPerf inferenceLatencyMs(BigDecimal value) {
        this.inferenceLatencyMs = value
        return this;
    }

    MathModelPerf cpuSeconds(BigDecimal value) {
        this.cpuSeconds = value
        return this;
    }

    MathModelPerf gpuSeconds(BigDecimal value) {
        this.gpuSeconds = value
        return this;
    }

    MathModelPerf memoryPeakMb(BigDecimal value) {
        this.memoryPeakMb = value
        return this;
    }

    MathModelPerf solverIterations(Long value) {
        this.solverIterations = value
        return this;
    }

    MathModelPerf epochs(Long value) {
        this.epochs = value
        return this;
    }

    MathModelPerf totalBatches(Long value) {
        this.totalBatches = value
        return this;
    }

    MathModelPerf trainLossFinal(BigDecimal value) {
        this.trainLossFinal = value
        return this;
    }

    MathModelPerf valLossFinal(BigDecimal value) {
        this.valLossFinal = value
        return this;
    }

    MathModelPerf top1Accuracy(BigDecimal value) {
        this.top1Accuracy = value
        return this;
    }

    MathModelPerf top5Accuracy(BigDecimal value) {
        this.top5Accuracy = value
        return this;
    }

    MathModelPerf f1Score(BigDecimal value) {
        this.f1Score = value
        return this;
    }

    MathModelPerf rocAuc(BigDecimal value) {
        this.rocAuc = value
        return this;
    }

    MathModelPerf paramCount(Long value) {
        this.paramCount = value
        return this;
    }

    MathModelPerf theoreticalFlops(BigDecimal value) {
        this.theoreticalFlops = value
        return this;
    }

    MathModelPerf throughputSamplesSec(BigDecimal value) {
        this.throughputSamplesSec = value
        return this;
    }

    MathModelPerf gpuMemoryPeakMb(BigDecimal value) {
        this.gpuMemoryPeakMb = value
        return this;
    }

    MathModelPerf run(MathModelRun item) {
        this.run = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.mathModelPerfId != null) map.put('mathModelPerfId', this.mathModelPerfId);
        if (this.mathModelRunId != null) map.put('mathModelRunId', this.mathModelRunId);
        if (this.totalDurationSec != null) map.put('totalDurationSec', this.totalDurationSec);
        if (this.inferenceLatencyMs != null) map.put('inferenceLatencyMs', this.inferenceLatencyMs);
        if (this.cpuSeconds != null) map.put('cpuSeconds', this.cpuSeconds);
        if (this.gpuSeconds != null) map.put('gpuSeconds', this.gpuSeconds);
        if (this.memoryPeakMb != null) map.put('memoryPeakMb', this.memoryPeakMb);
        if (this.solverIterations != null) map.put('solverIterations', this.solverIterations);
        if (this.epochs != null) map.put('epochs', this.epochs);
        if (this.totalBatches != null) map.put('totalBatches', this.totalBatches);
        if (this.trainLossFinal != null) map.put('trainLossFinal', this.trainLossFinal);
        if (this.valLossFinal != null) map.put('valLossFinal', this.valLossFinal);
        if (this.top1Accuracy != null) map.put('top1Accuracy', this.top1Accuracy);
        if (this.top5Accuracy != null) map.put('top5Accuracy', this.top5Accuracy);
        if (this.f1Score != null) map.put('f1Score', this.f1Score);
        if (this.rocAuc != null) map.put('rocAuc', this.rocAuc);
        if (this.paramCount != null) map.put('paramCount', this.paramCount);
        if (this.theoreticalFlops != null) map.put('theoreticalFlops', this.theoreticalFlops);
        if (this.throughputSamplesSec != null) map.put('throughputSamplesSec', this.throughputSamplesSec);
        if (this.gpuMemoryPeakMb != null) map.put('gpuMemoryPeakMb', this.gpuMemoryPeakMb);
        return map;
    }
}