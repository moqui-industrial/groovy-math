/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math

import groovy.transform.CompileStatic
import groovy.math.dsl.MathMeta
import groovy.math.entity.ModelValue
import groovy.math.jax.Jax
import groovy.math.libtorch.PyTorch
import groovy.math.opencv.OpenCv
import groovy.math.petsctao.PetscTaoProvider
import groovy.math.ortools.OrToolsProvider

@CompileStatic
final class Math {
    private Math() { }

    static Object execute(final MathMeta mathMeta, final String mathModelId,
                          @DelegatesTo(value = ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> request) {
        ExecutionRequest execution = new ExecutionRequest()
        execution.configure(request)

        Objects.requireNonNull(mathMeta, 'Math metadata must not be null').freeze()
        ModelValue model = mathMeta.entity('MathModel').findByName(mathModelId)
        if (model == null) throw new IllegalArgumentException("Unknown MathModel '${mathModelId}'")

        String solvingMethod = model.get('solvingMethodEnumId') as String
        String modelType = model.get('modelTypeEnumId') as String

        // Check for explicit computer vision operations
        boolean hasVisionOps = false
        for (ModelValue data : mathMeta.entity('MathModelData')) {
            if (data.get('mathModelId') == mathModelId && data.get('transformationId') != null) {
                ModelValue tf = mathMeta.entity('Transformation').findByName(data.get('transformationId') as String)
                if (tf != null) {
                    String tfType = (tf.get('transformationTypeEnumId') ?: '') as String
                    String tfName = (tf.get('name') ?: '') as String
                    if (tfType in ['TtGaussianBlur', 'TtSobel', 'TtCanny', 'TtWarpAffine', 'TtWarpPerspective', 'TtFilter2D'] ||
                        tfName.toLowerCase().contains('blur') || tfName.toLowerCase().contains('sobel') || tfName.toLowerCase().contains('gaussian')) {
                        hasVisionOps = true
                        break
                    }
                }
            }
        }

        // Automatic Engine Dispatch
        if (solvingMethod == 'MmsmOpenCv' || modelType == 'MmtComputerVision' || hasVisionOps) {
            return OpenCv.execute(mathMeta, mathModelId) {
                for (Map.Entry<String, Object> entry : execution.inputs) {
                    input entry.key, entry.value
                }
            }
        } else if (solvingMethod == 'MmsmJax') {
            return Jax.execute(mathMeta, mathModelId) {
                for (Map.Entry<String, Object> entry : execution.inputs) {
                    input entry.key, entry.value
                }
            }
        } else if (solvingMethod == 'MmsmPetscTao' || modelType == 'MmtQp') {
            PetscTaoProvider provider = new PetscTaoProvider(mathModelId)
            return provider.run(mathMeta, execution.inputs)
        } else if (solvingMethod == 'MmsmOrTools' || modelType in ['MmtLp', 'MmtMilp']) {
            OrToolsProvider provider = new OrToolsProvider(mathModelId)
            return provider.run(mathMeta, execution.inputs)
        } else {
            // Default to LibTorch / PyTorch provider
            return PyTorch.execute(mathMeta, mathModelId) {
                for (Map.Entry<String, Object> entry : execution.inputs) {
                    input entry.key, entry.value
                }
            }
        }
    }

    @CompileStatic
    public static final class ExecutionRequest {
        final LinkedHashMap<String, Object> inputs = new LinkedHashMap<>()

        void input(final String name, final Object value) {
            if (!name) throw new IllegalArgumentException('Input name must not be empty')
            inputs.put(name, value)
        }

        void configure(@DelegatesTo(value = ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure) {
            if (closure == null) return
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = this
            copy.call()
        }
    }
}
