/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.opencv

import groovy.transform.CompileStatic
import groovy.math.dsl.MathMeta
import groovy.math.entity.ModelValue
import groovy.math.spi.MathProvider

@CompileStatic
final class OpenCvProvider implements MathProvider<OpenCvPlan, OpenCvResult> {
    final String mathModelId
    private final OpenCvPanama panama

    OpenCvProvider(final String mathModelId) {
        this(mathModelId, OpenCvPanama.INSTANCE)
    }

    OpenCvProvider(final String mathModelId, final OpenCvPanama panama) {
        if (!mathModelId) throw new IllegalArgumentException('mathModelId must not be empty')
        this.mathModelId = mathModelId
        this.panama = Objects.requireNonNull(panama, 'OpenCv Panama instance must not be null')
    }

    @Override
    String getProviderId() { 'opencv' }

    @Override
    OpenCvPlan compile(final MathMeta mathMeta) {
        Objects.requireNonNull(mathMeta, 'Math metadata must not be null').freeze()
        ModelValue model = mathMeta.entity('MathModel').findByName(mathModelId)
        if (model == null) throw new IllegalArgumentException("Unknown MathModel '${mathModelId}'")

        List<ModelValue> modelData = []
        for (ModelValue value : mathMeta.entity('MathModelData')) {
            if (value.get('mathModelId') == mathModelId) modelData.add(value)
        }
        modelData.sort(Comparator.comparingInt { ModelValue v -> ((v.get('sequenceNum') as Number) ?: 0).intValue() })

        String inputName = null
        String outputName = null
        int width = 0
        int height = 0
        int outWidth = 0
        int outHeight = 0

        for (ModelValue data : modelData) {
            String matrixId = data.get('matrixId') as String
            String tensorId = data.get('tensorId') as String
            if (matrixId) {
                ModelValue matrix = mathMeta.entity('Matrix').findByName(matrixId)
                if (matrix) {
                    if (inputName == null) {
                        inputName = matrixId
                        height = ((Number) matrix.get('rows'))?.intValue() ?: 0
                        width = ((Number) matrix.get('cols'))?.intValue() ?: 0
                    }
                    outputName = matrixId
                }
            } else if (tensorId) {
                ModelValue tensor = mathMeta.entity('Tensor').findByName(tensorId)
                if (tensor) {
                    if (inputName == null) {
                        inputName = tensorId
                        List<ModelValue> axes = []
                        for (ModelValue ax : mathMeta.entity('TensorAxis')) {
                            if (ax.get('tensorId') == tensorId) axes.add(ax)
                        }
                        axes.sort(Comparator.comparingInt { ModelValue v -> ((v.get('axisIndex') as Number) ?: 0).intValue() })
                        if (axes.size() >= 2) {
                            height = ((Number) axes.get(0).get('dimensionSize')).intValue()
                            width = ((Number) axes.get(1).get('dimensionSize')).intValue()
                        }
                    }
                    outputName = tensorId
                }
            }
        }

        if (width <= 0 || height <= 0) {
            width = 64
            height = 64
        }
        outWidth = width
        outHeight = height

        long planHandle = panama.createPlan(width, height)
        if (planHandle == 0L) throw new IllegalStateException('Failed to create native OpenCV plan')

        for (ModelValue data : modelData) {
            String transformationId = data.get('transformationId') as String
            if (!transformationId) continue
            ModelValue tf = mathMeta.entity('Transformation').findByName(transformationId)
            if (!tf) continue

            String tfType = (tf.get('transformationTypeEnumId') ?: tf.get('transformationTypeEnum') ?: '') as String
            String name = (tf.get('name') ?: '') as String

            if (tfType == 'GaussianBlur' || name.toLowerCase().contains('gaussian') || name.toLowerCase().contains('blur')) {
                panama.addGaussianBlur(planHandle, 3, 1.0)
            } else if (tfType == 'Sobel' || name.toLowerCase().contains('sobel') || name.toLowerCase().contains('edge')) {
                panama.addSobel(planHandle, 1, 0, 3)
            } else if (tfType == 'Affine' || name.toLowerCase().contains('affine') || name.toLowerCase().contains('warp')) {
                double[] m = [1.0, 0.0, 0.0, 0.0, 1.0, 0.0] as double[]
                panama.addWarpAffine(planHandle, m, outWidth, outHeight)
            } else {
                panama.addGaussianBlur(planHandle, 3, 1.0)
            }
        }

        panama.seal(planHandle, outWidth, outHeight)
        new OpenCvPlan(mathModelId, inputName ?: 'Image', outputName ?: 'Result', width, height, outWidth, outHeight, panama, planHandle)
    }

    @Override
    OpenCvResult execute(final OpenCvPlan plan, final Map<String, ?> context) {
        Object inputObj = context.get(plan.inputName) ?: context.values().find { it != null }
        if (inputObj == null) throw new IllegalArgumentException("Missing input '${plan.inputName}' in execution context")

        float[] rawInput
        if (inputObj instanceof float[]) {
            rawInput = (float[]) inputObj
        } else if (inputObj instanceof List) {
            rawInput = flattenFloatList((List<?>) inputObj)
        } else {
            throw new IllegalArgumentException("Unsupported input type: ${inputObj.class.name}")
        }

        plan.execute(rawInput)
    }

    private static float[] flattenFloatList(final List<?> list) {
        List<Float> result = new ArrayList<>()
        flattenRecursive(list, result)
        float[] arr = new float[result.size()]
        for (int i = 0; i < result.size(); i++) arr[i] = result.get(i).floatValue()
        return arr
    }

    private static void flattenRecursive(final Object item, final List<Float> target) {
        if (item instanceof List) {
            for (Object sub : (List<?>) item) flattenRecursive(sub, target)
        } else if (item instanceof Number) {
            target.add(((Number) item).floatValue())
        }
    }
}
