/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.opencv

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.ModelValue
import org.moqui.math.spi.DeclaredModel
import org.moqui.math.spi.MathProvider
import org.moqui.math.spi.MathProviderFactory

/**
 * Claims computer-vision models: those that declare OpenCV explicitly, and those whose declared
 * pipeline contains vision operators even when the model's own enums do not say so.
 */
@CompileStatic
final class OpenCvProviderFactory implements MathProviderFactory {
    /**
     * Transformation types that mean the pipeline is doing image work.
     *
     * <p>Matching is on the declared TransformationType enum, never on a transformation's
     * free-text name: routing on names would silently misroute any model whose author happened
     * to call a step "blur" or "gaussianSomething".
     */
    private static final Set<String> VISION_TRANSFORMATIONS = Collections.unmodifiableSet(
        new LinkedHashSet<String>(['TtGaussianBlur', 'TtSobel', 'TtCanny',
                                   'TtWarpAffine', 'TtWarpPerspective', 'TtFilter2D']))

    @Override
    String getProviderId() { 'opencv' }

    @Override
    int getPriority() { 60 }

    @Override
    boolean claims(final MathMeta mathMeta, final DeclaredModel model) {
        if (model.solvingMethod == 'MmsmOpenCv') return true
        if (model.modelType == 'MmtComputerVision') return true
        declaresVisionOperator(mathMeta, model.mathModelId)
    }

    @Override
    MathProvider<?, ?> create(final String mathModelId) {
        new OpenCvProvider(mathModelId)
    }

    private static boolean declaresVisionOperator(final MathMeta mathMeta, final String mathModelId) {
        ModelValue model = mathMeta.entity('MathModel').findByName(mathModelId)
        String defId = model?.get('mathModelDefId') as String
        if (defId != null) {
            for (ModelValue step : mathMeta.entity('MathModelDefPipeline')) {
                if (step.get('mathModelDefId') != defId || step.get('transformationId') == null) continue
                ModelValue transformation = mathMeta.entity('Transformation')
                    .findByName(step.get('transformationId') as String)
                if (transformation == null) continue
                if (VISION_TRANSFORMATIONS.contains(transformation.get('transformationTypeEnumId') as String)) {
                    return true
                }
            }
        }
        for (ModelValue data : mathMeta.entity('MathModelData')) {
            if (data.get('mathModelId') != mathModelId || data.get('transformationId') == null) continue
            ModelValue transformation = mathMeta.entity('Transformation')
                .findByName(data.get('transformationId') as String)
            if (transformation == null) continue
            if (VISION_TRANSFORMATIONS.contains(transformation.get('transformationTypeEnumId') as String)) {
                return true
            }
        }
        false
    }
}
