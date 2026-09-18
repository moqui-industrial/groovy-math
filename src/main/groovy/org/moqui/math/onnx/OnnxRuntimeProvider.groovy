/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.onnx

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.ModelValue
import org.moqui.math.spi.MathProvider

@CompileStatic
final class OnnxRuntimeProvider implements MathProvider<OnnxPlan, Map<String, Object>> {

    final String mathModelId
    private final OnnxPanama panama

    OnnxRuntimeProvider(final String mathModelId = null) {
        this(mathModelId, OnnxPanama.instance)
    }

    OnnxRuntimeProvider(final String mathModelId, final OnnxPanama panama) {
        this.mathModelId = mathModelId
        this.panama = panama
    }

    @Override
    String getProviderId() { 'onnx' }

    @Override
    OnnxPlan compile(final MathMeta mathMeta) {
        if (!panama.isAvailable()) {
            throw new org.moqui.math.spi.ProviderUnavailableException(providerId,
                "ONNX Runtime native library is not available on this system.",
                "Run './gradlew buildOnnxNative' and ensure libonnxruntime is on library path.")
        }

        String modelPath = null
        ModelValue model = mathModelId ? mathMeta.entity('MathModel').findByName(mathModelId) : null
        if (model != null) {
            String defId = model.get('mathModelDefId') as String
            if (defId && mathMeta.entity('MathModelDefContent') != null) {
                for (String contentName : mathMeta.entity('MathModelDefContent').getNames()) {
                    ModelValue content = mathMeta.entity('MathModelDefContent').findByName(contentName)
                    if (content?.get('mathModelDefId') == defId) {
                        String typeEnum = content.get('contentTypeEnumId') as String
                        String loc = content.get('contentLocation') as String
                        if (typeEnum == 'MmCntOnnx' || (loc != null && loc.endsWith('.onnx'))) {
                            modelPath = loc
                            break
                        }
                    }
                }
            }
        }

        if (modelPath == null && mathMeta.entity('MathModelDefContent') != null) {
            for (String name : mathMeta.entity('MathModel').getNames()) {
                ModelValue candidate = mathMeta.entity('MathModel').findByName(name)
                String defId = candidate?.get('mathModelDefId') as String
                if (defId) {
                    for (String contentName : mathMeta.entity('MathModelDefContent').getNames()) {
                        ModelValue content = mathMeta.entity('MathModelDefContent').findByName(contentName)
                        if (content?.get('mathModelDefId') == defId) {
                            String typeEnum = content.get('contentTypeEnumId') as String
                            String loc = content.get('contentLocation') as String
                            if (typeEnum == 'MmCntOnnx' || (loc != null && loc.endsWith('.onnx')) ||
                                candidate.get('solvingMethod') == 'MmsmOnnx' || candidate.get('solvingMethodEnumId') == 'MmsmOnnx' || candidate.get('solvingMethod') == 'SmOnnx') {
                                modelPath = loc
                                break
                            }
                        }
                    }
                }
                if (modelPath != null) break
            }
        }

        if (!modelPath) {
            throw new IllegalArgumentException("No ONNX model location specified for model: ${mathModelId}")
        }

        long handle = panama.createSession(modelPath)
        if (handle == 0L) {
            throw new IllegalStateException("Failed to load ONNX model session from: ${modelPath}")
        }

        new OnnxPlan(mathModelId ?: 'OnnxModel', modelPath, null, null, null, null, panama, handle)
    }

    @Override
    Map<String, Object> execute(final OnnxPlan plan, final Map<String, ?> inputs) {
        Map.Entry<String, ?> firstEntry = inputs?.find { it.value != null }
        if (!firstEntry) {
            throw new IllegalArgumentException("No input data provided for ONNX model execution")
        }

        Object rawIn = firstEntry.value
        float[] inData
        if (rawIn instanceof float[]) {
            inData = (float[]) rawIn
        } else if (rawIn instanceof List) {
            List<?> list = (List<?>) rawIn
            inData = new float[list.size()]
            for (int i = 0; i < list.size(); i++) {
                inData[i] = ((Number) list[i]).floatValue()
            }
        } else {
            throw new IllegalArgumentException("Input data must be float[] or List<Number>")
        }

        OnnxResult result = plan.execute(inData)
        Map<String, Object> outMap = new LinkedHashMap<>()
        outMap.put(result.outputName, result.data)
        outMap
    }
}
