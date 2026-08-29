/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MathModelPerf
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MathModelPerf

@CompileStatic
class MathModelPerf_ {
    public static final String ENTITY_NAME = 'MathModelPerf'
    public static final String FULL_NAME = 'moqui.math.MathModelPerf'

    public static final Attribute<MathModelPerf, String> mathModelPerfId = new Attribute<>('mathModelPerfId', MathModelPerf.class, String.class, true, true)
    public static final Attribute<MathModelPerf, String> mathModelRunId = new Attribute<>('mathModelRunId', MathModelPerf.class, String.class, false, true)
    public static final Attribute<MathModelPerf, BigDecimal> totalDurationSec = new Attribute<>('totalDurationSec', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> inferenceLatencyMs = new Attribute<>('inferenceLatencyMs', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> cpuSeconds = new Attribute<>('cpuSeconds', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> gpuSeconds = new Attribute<>('gpuSeconds', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> memoryPeakMb = new Attribute<>('memoryPeakMb', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, Long> solverIterations = new Attribute<>('solverIterations', MathModelPerf.class, Long.class, false, false)
    public static final Attribute<MathModelPerf, Long> epochs = new Attribute<>('epochs', MathModelPerf.class, Long.class, false, false)
    public static final Attribute<MathModelPerf, Long> totalBatches = new Attribute<>('totalBatches', MathModelPerf.class, Long.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> trainLossFinal = new Attribute<>('trainLossFinal', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> valLossFinal = new Attribute<>('valLossFinal', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> top1Accuracy = new Attribute<>('top1Accuracy', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> top5Accuracy = new Attribute<>('top5Accuracy', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> f1Score = new Attribute<>('f1Score', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> rocAuc = new Attribute<>('rocAuc', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, Long> paramCount = new Attribute<>('paramCount', MathModelPerf.class, Long.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> theoreticalFlops = new Attribute<>('theoreticalFlops', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> throughputSamplesSec = new Attribute<>('throughputSamplesSec', MathModelPerf.class, BigDecimal.class, false, false)
    public static final Attribute<MathModelPerf, BigDecimal> gpuMemoryPeakMb = new Attribute<>('gpuMemoryPeakMb', MathModelPerf.class, BigDecimal.class, false, false)
}
