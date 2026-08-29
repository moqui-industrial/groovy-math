/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.libtorch

import groovy.transform.CompileStatic
import groovy.math.dsl.MathMeta
import groovy.math.entity.ModelValue
import groovy.math.spi.MathProvider

import java.util.regex.Matcher
import java.util.regex.Pattern

@CompileStatic
final class LibTorchProvider implements MathProvider<LibTorchPlan, LibTorchResult> {
    private static final Pattern NUMBER = Pattern.compile('[-+]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][-+]?\\d+)?')

    final String mathModelId
    private final LibTorchBackend backend

    LibTorchProvider(final String mathModelId) {
        this(mathModelId, LibTorchPanama.INSTANCE)
    }

    LibTorchProvider(final String mathModelId, final LibTorchBackend backend) {
        if (!mathModelId) throw new IllegalArgumentException('mathModelId must not be empty')
        this.mathModelId = mathModelId
        this.backend = Objects.requireNonNull(backend, 'LibTorch backend must not be null')
    }

    @Override
    String getProviderId() { 'libtorch' }

    void configureThreads(final int intraOpThreads, final int interOpThreads) {
        if (intraOpThreads <= 0 || interOpThreads <= 0) {
            throw new IllegalArgumentException('Thread counts must be positive')
        }
        backend.configureThreads(intraOpThreads, interOpThreads)
    }

    int getIntraOpThreads() { backend.intraOpThreads() }
    int getInterOpThreads() { backend.interOpThreads() }

    @Override
    LibTorchPlan compile(final MathMeta mathMeta) {
        Objects.requireNonNull(mathMeta, 'Math metadata must not be null').freeze()
        ModelValue model = mathMeta.entity('MathModel').findByName(mathModelId)
        if (model == null) throw new IllegalArgumentException("Unknown MathModel '${mathModelId}'")

        List<ModelValue> modelData = []
        for (ModelValue value : mathMeta.entity('MathModelData')) {
            if (value.get('mathModelId') == mathModelId) modelData.add(value)
        }
        modelData.sort(Comparator.comparingInt { ModelValue value -> sequence(value) })
        Map<String, ModelValue> tensors = index(mathMeta.entity('Tensor'), 'tensorId')
        Map<String, ModelValue> matrices = index(mathMeta.entity('Matrix'), 'matrixId')
        Map<String, ModelValue> transformations = index(mathMeta.entity('Transformation'), 'transformationId')
        List<ModelValue> operands = []
        for (ModelValue value : mathMeta.entity('TransformationOperand')) operands.add(value)

        List<ModelValue> tensorData = []
        for (ModelValue value : modelData) if (value.get('tensorId') != null) tensorData.add(value)
        ModelValue inputTensor
        for (ModelValue value : tensorData) {
            ModelValue candidate = tensors.get(value.get('tensorId') as String)
            if (candidate?.get('purposeEnumId') == 'TpOriginal') { inputTensor = candidate; break }
        }
        if (inputTensor == null) {
            for (ModelValue value : tensorData) {
                ModelValue candidate = tensors.get(value.get('tensorId') as String)
                if (candidate != null && candidate.get('purposeEnumId') != 'TpModelParams') {
                    inputTensor = candidate
                    break
                }
            }
        }
        ModelValue inputObject = inputTensor
        String inputId = inputTensor?.get('tensorId') as String
        if (inputObject == null) {
            for (ModelValue value : modelData) {
                if (value.get('matrixId') == null) continue
                ModelValue candidate = matrices.get(value.get('matrixId') as String)
                if (candidate != null && candidate.get('purposeEnumId') == 'MpOriginal' &&
                    candidate.get('componentArray') == null) {
                    inputObject = candidate
                    inputId = candidate.get('matrixId') as String
                    break
                }
            }
        }
        if (inputObject == null) throw new IllegalStateException("MathModel '${mathModelId}' has no runtime input")

        int inputWidth = objectWidth(inputObject)
        int inputRows = objectRows(inputObject)
        LinkedHashMap<String, Integer> slots = new LinkedHashMap<>()
        slots.put(inputId, 0)
        int nextSlot = 1
        int operationCount = 0
        int outputSlot = 0
        int outputWidth = inputWidth
        String outputId = inputId
        long handle = backend.createPlan(inputWidth)
        try {
            for (ModelValue data : modelData) {
                if (data.get('transformationId') == null) continue
                String transformationId = data.get('transformationId') as String
                ModelValue transformation = transformations.get(transformationId)
                if (transformation == null) throw new IllegalStateException("Missing Transformation '${transformationId}'")
                List<ModelValue> operationOperands = []
                for (ModelValue operand : operands) {
                    if (operand.get('transformationId') == transformationId) operationOperands.add(operand)
                }
                operationOperands.sort(Comparator.comparingInt {
                    ModelValue operand -> ((Number) operand.get('operandIndex')).intValue()
                })
                String resultId = (transformation.get('resultTensorId') ?:
                    transformation.get('resultMatrixId')) as String
                if (!resultId) throw new IllegalStateException("Transformation '${transformationId}' has no result object")
                int resultSlot = nextSlot++

                switch (transformation.get('transformationTypeEnumId') as String) {
                    case 'TtAffine':
                        ModelValue sourceOperand = role(operationOperands, 'TotLeftTensor', 0)
                        ModelValue weightOperand = role(operationOperands, 'TotKernelTensor', 1)
                        ModelValue biasOperand = role(operationOperands, 'TotBiasTensor', 2)
                        String sourceId = requiredTensorId(sourceOperand, transformationId)
                        Integer sourceSlot = slots.get(sourceId)
                        if (sourceSlot == null) throw new IllegalStateException("Tensor '${sourceId}' is not available before '${transformationId}'")
                        ModelValue weightTensor = requiredTensor(tensors, weightOperand, transformationId)
                        ModelValue biasTensor = requiredTensor(tensors, biasOperand, transformationId)
                        List<Integer> weightShape = shape(weightTensor)
                        if (weightShape.size() != 2) throw new IllegalStateException("Weight '${weightTensor.tensorId}' must have rank 2")
                        int affineOutputWidth = weightShape[0]
                        int affineInputWidth = weightShape[1]
                        float[] weight = elements(weightTensor, affineOutputWidth * affineInputWidth)
                        float[] bias = elements(biasTensor, affineOutputWidth)
                        backend.addAffine(handle, sourceSlot, resultSlot, affineInputWidth, affineOutputWidth, weight, bias)
                        outputWidth = affineOutputWidth
                        break
                    case 'TtTensorReLu':
                        ModelValue sourceOperand = role(operationOperands, 'TotSingle', 0)
                        String sourceId = requiredTensorId(sourceOperand, transformationId)
                        Integer sourceSlot = slots.get(sourceId)
                        if (sourceSlot == null) throw new IllegalStateException("Tensor '${sourceId}' is not available before '${transformationId}'")
                        backend.addRelu(handle, sourceSlot, resultSlot)
                        outputWidth = objectWidth(tensors.get(resultId))
                        break
                    case 'TtMatrixProduct':
                        ModelValue leftOperand = role(operationOperands, 'TotLeftMatrix', 0)
                        ModelValue rightOperand = role(operationOperands, 'TotRightMatrix', 1)
                        String leftId = requiredMatrixId(leftOperand, transformationId)
                        Integer leftSlot = slots.get(leftId)
                        if (leftSlot == null) throw new IllegalStateException(
                            "Matrix '${leftId}' is not available before '${transformationId}'")
                        ModelValue rightMatrix = requiredMatrix(matrices, rightOperand, transformationId)
                        int matrixInputWidth = ((Number) rightMatrix.get('rows')).intValue()
                        int matrixOutputWidth = ((Number) rightMatrix.get('cols')).intValue()
                        float[] rightValues = matrixElements(
                            rightMatrix, matrixInputWidth * matrixOutputWidth)
                        backend.addMatrixProduct(handle, leftSlot, resultSlot,
                            matrixInputWidth, matrixOutputWidth, rightValues)
                        outputWidth = matrixOutputWidth
                        break
                    default:
                        throw new UnsupportedOperationException(
                            "LibTorch provider does not support ${transformation.get('transformationTypeEnumId')} (${transformationId})")
                }
                slots.put(resultId, resultSlot)
                outputSlot = resultSlot
                outputId = resultId
                operationCount++
            }
            if (operationCount == 0) throw new IllegalStateException("MathModel '${mathModelId}' has no transformations")
            backend.seal(handle, outputSlot, outputWidth)
            ModelValue outputObject = tensors.get(outputId) ?: matrices.get(outputId)
            String inputName = (inputObject.get('name') ?: inputId) as String
            String outputName = (outputObject?.get('name') ?: outputId) as String
            new LibTorchPlan(mathModelId, inputName, outputName, inputRows, inputWidth, outputWidth,
                operationCount, backend, handle)
        } catch (Throwable failure) {
            backend.destroy(handle)
            throw failure
        }
    }

    @Override
    LibTorchResult execute(final LibTorchPlan plan, final Map<String, ?> inputs) {
        Objects.requireNonNull(plan, 'LibTorch plan must not be null')
        Object raw = inputs.get(plan.inputName)
        if (raw == null) raw = inputs.get('input')
        float[] values
        if (raw instanceof float[]) values = (float[]) raw
        else if (raw instanceof Collection) {
            List<Float> flattened = []
            flatten((Collection<?>) raw, flattened)
            values = new float[flattened.size()]
            for (int index = 0; index < flattened.size(); index++) values[index] = flattened[index]
        } else {
            throw new IllegalArgumentException("Input '${plan.inputName}' must be float[] or Collection<Number>")
        }
        plan.execute(values)
    }

    private static int sequence(final ModelValue value) {
        value.sequenceNum == null ? Integer.MAX_VALUE : ((Number) value.sequenceNum).intValue()
    }

    private static Map<String, ModelValue> index(final Iterable<ModelValue> values, final String fieldName) {
        LinkedHashMap<String, ModelValue> result = new LinkedHashMap<>()
        values.each { ModelValue value -> result.put(value.get(fieldName) as String, value) }
        result
    }

    private static ModelValue role(final List<ModelValue> operands, final String role, final int fallbackIndex) {
        operands.find { ModelValue value -> value.operandTypeEnumId == role } ?:
            operands.find { ModelValue value -> ((Number) value.operandIndex).intValue() == fallbackIndex }
    }

    private static String requiredTensorId(final ModelValue operand, final String transformationId) {
        String tensorId = operand?.operandTensorId as String
        if (!tensorId) throw new IllegalStateException("Transformation '${transformationId}' has an incomplete tensor operand")
        tensorId
    }

    private static ModelValue requiredTensor(final Map<String, ModelValue> tensors, final ModelValue operand,
                                             final String transformationId) {
        String tensorId = requiredTensorId(operand, transformationId)
        ModelValue tensor = tensors.get(tensorId)
        if (tensor == null) throw new IllegalStateException("Missing Tensor '${tensorId}'")
        tensor
    }

    private static String requiredMatrixId(final ModelValue operand, final String transformationId) {
        String matrixId = operand?.get('operandMatrixId') as String
        if (!matrixId) throw new IllegalStateException(
            "Transformation '${transformationId}' has an incomplete matrix operand")
        matrixId
    }

    private static ModelValue requiredMatrix(final Map<String, ModelValue> matrices, final ModelValue operand,
                                             final String transformationId) {
        String matrixId = requiredMatrixId(operand, transformationId)
        ModelValue matrix = matrices.get(matrixId)
        if (matrix == null) throw new IllegalStateException("Missing Matrix '${matrixId}'")
        matrix
    }

    private static int objectWidth(final ModelValue value) {
        if (value == null) throw new IllegalStateException('Missing mathematical object dimensions')
        if (value.definition.name == 'Matrix') return ((Number) value.get('cols')).intValue()
        List<Integer> dimensions = shape(value)
        if (dimensions.empty || dimensions.last() <= 0) {
            throw new IllegalStateException("Tensor '${value.get('tensorId')}' has no fixed trailing dimension")
        }
        dimensions.last()
    }

    private static int objectRows(final ModelValue value) {
        if (value.definition.name == 'Matrix') return ((Number) value.get('rows')).intValue()
        List<Integer> dimensions = shape(value)
        dimensions.size() >= 2 ? dimensions[0] : -1
    }

    private static List<Integer> shape(final ModelValue tensor) {
        List<Float> numbers = parseNumbers(tensor.shape as String)
        numbers.collect { Float value -> value.intValue() }
    }

    private static float[] elements(final ModelValue tensor, final int expectedCount) {
        List<Float> values = parseNumbers(tensor.elementArray as String)
        if (values.size() != expectedCount) {
            throw new IllegalStateException(
                "Tensor '${tensor.tensorId}' contains ${values.size()} elements; expected ${expectedCount}")
        }
        float[] result = new float[expectedCount]
        for (int i = 0; i < expectedCount; i++) result[i] = values[i]
        result
    }

    private static float[] matrixElements(final ModelValue matrix, final int expectedCount) {
        List<Float> values = parseNumbers(matrix.get('componentArray') as String)
        if (values.size() != expectedCount) {
            throw new IllegalStateException(
                "Matrix '${matrix.get('matrixId')}' contains ${values.size()} elements; expected ${expectedCount}")
        }
        float[] result = new float[expectedCount]
        for (int index = 0; index < expectedCount; index++) result[index] = values[index]
        result
    }

    private static void flatten(final Collection<?> source, final List<Float> target) {
        source.each { Object value ->
            if (value instanceof Collection) flatten((Collection<?>) value, target)
            else if (value instanceof Number) target.add(((Number) value).floatValue())
            else throw new IllegalArgumentException('Nested inputs may contain only numbers')
        }
    }

    private static List<Float> parseNumbers(final String text) {
        if (!text) return Collections.emptyList()
        Matcher matcher = NUMBER.matcher(text)
        List<Float> values = []
        while (matcher.find()) values.add(Float.parseFloat(matcher.group()))
        values
    }
}
