/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.ModelValue
import org.moqui.math.spi.MathProvider
import org.moqui.math.tensor.TensorContractException
import org.moqui.math.tensor.TensorDataTypes
import org.moqui.math.tensor.TensorDescriptor

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

        Map<String, ModelValue> tensors = index(mathMeta.entity('Tensor'), 'tensorId')
        Map<String, ModelValue> matrices = index(mathMeta.entity('Matrix'), 'matrixId')
        Map<String, ModelValue> transformations = index(mathMeta.entity('Transformation'), 'transformationId')
        List<ModelValue> operands = []
        for (ModelValue value : mathMeta.entity('TransformationOperand')) operands.add(value)

        List<ModelValue> pipelineSteps = []
        ModelValue inputObject = null
        String inputId = null

        if (model != null) {
            List<ModelValue> modelData = []
            for (ModelValue value : mathMeta.entity('MathModelData')) {
                if (value.get('mathModelId') == mathModelId) modelData.add(value)
            }
            modelData.sort(Comparator.comparingInt { ModelValue value -> sequence(value) })

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
            inputObject = inputTensor
            inputId = inputTensor?.get('tensorId') as String
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

            String defId = model.get('mathModelDefId') as String
            if (defId != null) {
                for (ModelValue step : mathMeta.entity('MathModelDefPipeline')) {
                    if (step.get('mathModelDefId') == defId && step.get('transformationId') != null) {
                        pipelineSteps.add(step)
                    }
                }
                pipelineSteps.sort(Comparator.comparingInt { ModelValue value -> sequence(value) })
            }
            if (pipelineSteps.isEmpty()) {
                for (ModelValue data : modelData) {
                    if (data.get('transformationId') != null) pipelineSteps.add(data)
                }
            }
        } else {
            // Standalone Transformation or Plan!
            ModelValue singleTf = mathMeta.entity('Transformation').findByName(mathModelId)
            if (singleTf != null) {
                pipelineSteps.add(singleTf)
            } else {
                for (ModelValue tf : mathMeta.entity('Transformation')) {
                    pipelineSteps.add(tf)
                }
            }
            if (pipelineSteps.isEmpty()) {
                throw new IllegalArgumentException("Unknown MathModel or Transformation '${mathModelId}'")
            }

            // Find input object from operands of the first transformation
            String firstTfId = pipelineSteps[0].get('transformationId') as String
            for (ModelValue op : operands) {
                if (op.get('transformationId') == firstTfId) {
                    String opType = op.get('operandTypeEnumId') as String
                    String mId = op.get('operandMatrixId') as String
                    String tId = op.get('operandTensorId') as String
                    if (opType == 'TotLeftMatrix' || opType == 'TotSingle' || opType == 'TotLeftTensor' || inputObject == null) {
                        if (mId && matrices.containsKey(mId)) {
                            inputObject = matrices.get(mId)
                            inputId = mId
                            if (opType == 'TotLeftMatrix') break
                        } else if (tId && tensors.containsKey(tId)) {
                            inputObject = tensors.get(tId)
                            inputId = tId
                            if (opType == 'TotLeftTensor') break
                        }
                    }
                }
            }
        }

        if (inputObject == null) throw new IllegalStateException("MathModel or Transformation '${mathModelId}' has no runtime input")

        int inputWidth = objectWidth(inputObject)
        int inputRows = objectRows(inputObject)
        LinkedHashMap<String, Integer> slots = new LinkedHashMap<>()
        slots.put(inputId, 0)
        int nextSlot = 1
        int operationCount = 0
        int outputSlot = 0
        int outputWidth = inputWidth
        String outputId = inputId
        long handle
        try {
            handle = backend.createPlan(inputWidth)
        } catch (UnsatisfiedLinkError e) {
            throw new org.moqui.math.spi.ProviderUnavailableException(providerId, e.message, "Run './gradlew buildLibTorchNative'.", e)
        }
        try {
            for (ModelValue data : pipelineSteps) {
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
                    case 'TtTensorSigmoid':
                    case 'TtTensorGelu':
                    case 'TtTensorSilu':
                    case 'TtTensorTanh':
                    case 'TtTensorLeakyReLu':
                    case 'TtTensorElu':
                    case 'TtTensorSoftmax':
                    case 'TtTensorLogSoftmax':
                    case 'TtTensorExp':
                    case 'TtTensorLog':
                    case 'TtTensorSqrt':
                    case 'TtTensorPow':
                    case 'TtTensorSum':
                    case 'TtTensorMean':
                        ModelValue srcOperand = role(operationOperands, 'TotSingle', 0) ?: operationOperands[0]
                        String srcId = requiredTensorId(srcOperand, transformationId)
                        Integer srcSlot = slots.get(srcId)
                        if (srcSlot == null) throw new IllegalStateException("Tensor '${srcId}' is not available before '${transformationId}'")
                        String opEnum = transformation.get('transformationTypeEnumId') as String
                        switch (opEnum) {
                            case 'TtTensorReLu': backend.addRelu(handle, srcSlot, resultSlot); break
                            case 'TtTensorSigmoid': backend.addSigmoid(handle, srcSlot, resultSlot); break
                            case 'TtTensorGelu': backend.addGelu(handle, srcSlot, resultSlot); break
                            case 'TtTensorSilu': backend.addSilu(handle, srcSlot, resultSlot); break
                            case 'TtTensorTanh': backend.addTanh(handle, srcSlot, resultSlot); break
                            case 'TtTensorLeakyReLu': backend.addLeakyRelu(handle, srcSlot, resultSlot, 0.01f); break
                            case 'TtTensorElu': backend.addElu(handle, srcSlot, resultSlot, 1.0f); break
                            case 'TtTensorSoftmax': backend.addSoftmax(handle, srcSlot, resultSlot, -1L); break
                            case 'TtTensorLogSoftmax': backend.addLogSoftmax(handle, srcSlot, resultSlot, -1L); break
                            case 'TtTensorExp': backend.addUnaryMath(handle, 0, srcSlot, resultSlot, 0.0f); break
                            case 'TtTensorLog': backend.addUnaryMath(handle, 1, srcSlot, resultSlot, 0.0f); break
                            case 'TtTensorSqrt': backend.addUnaryMath(handle, 2, srcSlot, resultSlot, 0.0f); break
                            case 'TtTensorPow': backend.addUnaryMath(handle, 3, srcSlot, resultSlot, 2.0f); break
                            case 'TtTensorSum': backend.addReduction(handle, 0, srcSlot, resultSlot, -1L, true); break
                            case 'TtTensorMean': backend.addReduction(handle, 1, srcSlot, resultSlot, -1L, true); break
                        }
                        outputWidth = tensors.get(resultId) != null ? objectWidth(tensors.get(resultId)) : outputWidth
                        break
                    case 'TtLayerNorm':
                    case 'TtRMSNorm':
                        ModelValue normSrc = role(operationOperands, 'TotSingle', 0) ?: operationOperands[0]
                        String normId = requiredTensorId(normSrc, transformationId)
                        Integer normSlot = slots.get(normId)
                        if (normSlot == null) throw new IllegalStateException("Tensor '${normId}' is not available before '${transformationId}'")
                        int normW = tensors.get(resultId) != null ? objectWidth(tensors.get(resultId)) : outputWidth
                        if (transformation.get('transformationTypeEnumId') == 'TtLayerNorm') {
                            backend.addLayerNorm(handle, normSlot, resultSlot, normW, null, null, 1e-5f)
                        } else {
                            backend.addRMSNorm(handle, normSlot, resultSlot, normW, null, 1e-5f)
                        }
                        outputWidth = normW
                        break
                    case 'TtAttentionMask':
                        ModelValue maskSrc = role(operationOperands, 'TotSingle', 0) ?: operationOperands[0]
                        ModelValue maskOp = role(operationOperands, 'TotBiasTensor', 1) ?: operationOperands[1]
                        String mSrcId = requiredTensorId(maskSrc, transformationId)
                        Integer mSlot = slots.get(mSrcId)
                        if (mSlot == null) throw new IllegalStateException("Tensor '${mSrcId}' is not available")
                        ModelValue maskTensor = requiredTensor(tensors, maskOp, transformationId)
                        List<Integer> mShape = shape(maskTensor)
                        float[] mData = elements(maskTensor, mShape[0] * mShape[1])
                        backend.addAttentionMask(handle, mSlot, resultSlot, mShape[0], mShape[1], mData)
                        outputWidth = mShape[1]
                        break
                    case 'TtScaledDotProductAttention':
                        Integer qSlot = slots.get(requiredTensorId(operationOperands[0], transformationId))
                        Integer kSlot = slots.get(requiredTensorId(operationOperands[1], transformationId))
                        Integer vSlot = slots.get(requiredTensorId(operationOperands[2], transformationId))
                        backend.addScaledDotProductAttention(handle, qSlot, kSlot, vSlot, resultSlot, 1.0f)
                        outputWidth = tensors.get(resultId) != null ? objectWidth(tensors.get(resultId)) : outputWidth
                        break
                    case 'TtTensorAdd':
                    case 'TtTensorSub':
                    case 'TtTensorMul':
                    case 'TtTensorDiv':
                    case 'TtLossMse':
                    case 'TtLossCrossEntropy':
                        Integer slotA = slots.get(requiredTensorId(operationOperands[0], transformationId))
                        Integer slotB = slots.get(requiredTensorId(operationOperands[1], transformationId))
                        if (slotA == null || slotB == null) throw new IllegalStateException("Operands for binary op not found")
                        String bType = transformation.get('transformationTypeEnumId') as String
                        switch (bType) {
                            case 'TtTensorAdd': backend.addBinaryOp(handle, 0, slotA, slotB, resultSlot); break
                            case 'TtTensorSub': backend.addBinaryOp(handle, 1, slotA, slotB, resultSlot); break
                            case 'TtTensorMul': backend.addBinaryOp(handle, 2, slotA, slotB, resultSlot); break
                            case 'TtTensorDiv': backend.addBinaryOp(handle, 3, slotA, slotB, resultSlot); break
                            case 'TtLossMse': backend.addLoss(handle, 0, slotA, slotB, resultSlot); break
                            case 'TtLossCrossEntropy': backend.addLoss(handle, 1, slotA, slotB, resultSlot); break
                        }
                        outputWidth = tensors.get(resultId) != null ? objectWidth(tensors.get(resultId)) : outputWidth
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
            float[] defaultInputValues = initialElements(inputObject, inputRows * inputWidth)
            new LibTorchPlan(mathModelId, inputName, outputName, inputRows, inputWidth, outputWidth,
                operationCount, backend, handle, inputId, declaredShape(inputObject),
                declaredDataType(inputObject, inputId), defaultInputValues)
        } catch (Throwable failure) {
            backend.destroy(handle)
            throw failure
        }
    }

    @Override
    LibTorchResult execute(final LibTorchPlan plan, final Map<String, ?> inputs) {
        Objects.requireNonNull(plan, 'LibTorch plan must not be null')
        Object raw = inputs != null ? inputs.get(plan.inputName) : null
        if (raw == null && inputs != null) raw = inputs.get('input')
        if (raw == null && inputs != null && plan.inputId != null) raw = inputs.get(plan.inputId)
        if (raw == null && plan.defaultInputValues != null && plan.defaultInputValues.length > 0) {
            return plan.execute(plan.defaultInputValues)
        }
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

    private static float[] initialElements(final ModelValue value, final int expectedCount) {
        if (value == null) return null
        if (value.definition.name == 'Matrix') {
            if (value.get('componentArray') == null) return null
            return matrixElements(value, expectedCount)
        }
        if (value.get('elementArray') != null) {
            return elements(value, expectedCount)
        }
        null
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

    /**
     * The element type the model declares for its input, as an FFM dtype code.
     *
     * <p>The C++ bridge is float32 throughout: every plan slot, weight and buffer is a float,
     * and run_plan reshapes the input with at::kFloat. A model that declares float64 would be
     * narrowed on the way across without a word, so a declaration this backend cannot honour is
     * refused while the plan is being built rather than producing quietly wrong numbers.
     *
     * <p>An object that declares nothing is taken as float32, which is what it was already being
     * treated as. Matrix has no element type in the schema at all, only Tensor does.
     */
    private static int declaredDataType(final ModelValue value, final String inputId) {
        if (value == null || !value.definition.fields.containsKey('dataTypeEnumId')) {
            return TensorDescriptor.DTYPE_FLOAT32
        }
        String declared = value.get('dataTypeEnumId') as String
        if (!declared) return TensorDescriptor.DTYPE_FLOAT32

        int code = TensorDataTypes.codeForEnumId(declared)
        if (code != TensorDescriptor.DTYPE_FLOAT32) {
            throw new TensorContractException(inputId, 'Element type is not supported by the LibTorch bridge',
                'DtFloat32', declared)
        }
        code
    }

    /**
     * Shape as the model declares it, or null when it does not fix one. A Matrix declares its
     * shape as rows and cols; a Tensor declares it in its shape field, which the schema marks
     * not-null. This is what turns a DSL declaration into a contract the runtime can enforce.
     */
    private static long[] declaredShape(final ModelValue value) {
        if (value == null) return null
        if (value.definition.name == 'Matrix') {
            Number rows = value.get('rows') as Number
            Number cols = value.get('cols') as Number
            if (rows == null || cols == null) return null
            return [rows.longValue(), cols.longValue()] as long[]
        }
        List<Integer> dimensions = shape(value)
        if (dimensions.empty) return null
        long[] declared = new long[dimensions.size()]
        for (int index = 0; index < dimensions.size(); index++) declared[index] = dimensions[index].longValue()
        declared
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
