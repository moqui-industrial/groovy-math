/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.spi

import org.junit.jupiter.api.Test
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.ModelValue

class MathProviderRegistryTest {

    @Test
    void discoversEveryShippedBackendThroughServiceLoader() {
        List<MathProviderFactory> factories = MathProviderRegistry.factories()

        assert factories*.providerId == ['onnx', 'opencv', 'jax', 'petsctao', 'openfoam', 'ortools', 'libtorch']
        // The order the dispatcher used to hard-code as if/else branches is now a priority,
        // with the general-purpose backend last by construction.
        assert factories*.priority == [70, 60, 50, 40, 30, 20, Integer.MIN_VALUE]
        assert factories.last().priority == Integer.MIN_VALUE
    }

    @Test
    void routesTheDeclaredExamplesToTheirBackends() {
        assert providerFor('examples/matrix-product.groovy', 'MatrixProduct') == 'libtorch'
        assert providerFor('examples/production-plan.groovy', 'ProductionPlan') == 'ortools'
        assert providerFor('examples/energy-dispatch.groovy', 'EnergyDispatch') == 'petsctao'
        assert providerFor('examples/openfoam-cavity.groovy', 'CavityIcoFoam') == 'openfoam'
        assert providerFor('examples/opencv-vision-pipeline.groovy', 'EdgePipeline') == 'opencv'
    }

    @Test
    void claimsVisionPipelineFromItsDeclaredOperatorsAlone() {
        // No OpenCV solving method and no ComputerVision model type: the claim comes only from
        // the TransformationType enums the pipeline declares. A step merely *named* "blur"
        // must not be enough.
        MathMeta mathMeta = MathDsl.math {
            MathModelDef('PlainDef', description: 'No vision enums at all') {
                pipeline('Step', stepSeqId: '01', transformationId: 'Smooth', sequenceNum: 10L)
            }
            MathModel('Pipeline') {
                mathModelDefId 'PlainDef'
                statusId 'MathModelDraft'
            }
            Transformation('Smooth', transformationTypeEnumId: 'TtGaussianBlur', name: 'anything')
        }.validate()

        assert new OpenCvProviderFactoryProbe().claimsOf(mathMeta, 'Pipeline')

        MathMeta named = MathDsl.math {
            MathModelDef('PlainDef2', description: 'A misleading name, no vision enum') {
                pipeline('Step2', stepSeqId: '01', transformationId: 'blur', sequenceNum: 10L)
            }
            MathModel('Pipeline2') {
                mathModelDefId 'PlainDef2'
                statusId 'MathModelDraft'
            }
            Transformation('blur', transformationTypeEnumId: 'TtMatrixProduct', name: 'gaussianBlur')
        }.validate()

        assert !new OpenCvProviderFactoryProbe().claimsOf(named, 'Pipeline2')
    }

    @Test
    void letsAThirdPartyBackendOutrankTheShippedOnes() {
        MathMeta mathMeta = MathDsl.evaluate(new File('examples/matrix-product.groovy')).validate()
        ModelValue model = mathMeta.entity('MathModel').findByName('MatrixProduct')

        List<MathProviderFactory> candidates = MathProviderRegistry.factories()
        assert MathProviderRegistry.select(candidates, mathMeta, model).providerId == 'libtorch'

        candidates.add(new CustomFactory())
        assert MathProviderRegistry.select(candidates, mathMeta, model).providerId == 'custom'
    }

    private static String providerFor(final String examplePath, final String mathModelId) {
        MathMeta mathMeta = MathDsl.evaluate(new File(System.getProperty('user.dir'), examplePath)).validate()
        ModelValue model = mathMeta.entity('MathModel').findByName(mathModelId)
        assert model != null : "no MathModel '${mathModelId}' in ${examplePath}"
        MathProviderRegistry.select(mathMeta, model)?.providerId
    }

    /** Reaches the OpenCV factory's claim without naming its package from the test. */
    private static class OpenCvProviderFactoryProbe {
        boolean claimsOf(final MathMeta mathMeta, final String mathModelId) {
            MathProviderFactory opencv = MathProviderRegistry.factories().find {
                MathProviderFactory it -> it.providerId == 'opencv'
            }
            opencv.claims(mathMeta, DeclaredModel.of(mathMeta, mathMeta.entity('MathModel').findByName(mathModelId)))
        }
    }

    /** Stands in for a backend shipped in someone else's jar. */
    private static class CustomFactory implements MathProviderFactory {
        @Override String getProviderId() { 'custom' }
        @Override int getPriority() { 1000 }
        @Override boolean claims(final MathMeta mathMeta, final DeclaredModel model) { true }
        @Override MathProvider<?, ?> create(final String mathModelId) {
            throw new UnsupportedOperationException('selection only')
        }
    }
}
