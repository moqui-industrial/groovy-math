/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import org.junit.jupiter.api.Test
import org.moqui.math.dsl.MathDsl
import org.moqui.math.moqui.MoquiSchemaInspector

import org.moqui.math.dsl.MathMeta
import org.moqui.math.tensor.TensorContractException
import org.moqui.math.tensor.TensorDescriptor

import java.nio.ByteBuffer

import static org.junit.jupiter.api.Assertions.assertThrows

class LibTorchProviderTest {
    @Test
    void lowersMoquiMathObjectsDirectlyToProviderOperationsWhenSchemaIsConfigured() {
        RecordingBackend backend = new RecordingBackend()
        LibTorchProvider provider = new LibTorchProvider('MatrixProduct', backend)

        LibTorchPlan plan = provider.compile(MathDsl.evaluate(
            new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy')))
        try {
            assert plan.inputName == 'A'
            assert plan.outputName == 'C'
            assert plan.inputWidth == 3
            assert plan.outputWidth == 2
            assert plan.operationCount == 1
            assert backend.operations == ['matrixProduct:3x2']
            assert backend.sealed
        } finally {
            plan.close()
        }
        assert backend.destroyed
    }

    @Test
    void runsPolymorphicProviderLifecycleWhenSchemaIsConfigured() {
        RecordingBackend backend = new RecordingBackend()
        LibTorchProvider provider = new LibTorchProvider('MatrixProduct', backend)

        LibTorchResult result = provider.run(MathDsl.evaluate(
            new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy')),
            [A: [[1, 2, 3], [4, 5, 6]]])

        assert result.tensorName == 'C'
        assert result.batchSize == 2
        assert result.width == 2
        assert result.values.toList() == [58f, 64f, 139f, 154f]
        assert backend.destroyed
    }

    @Test
    void enforcesTheShapeDeclaredInTheModelWithNoNativeCallAtAll() {
        // examples/matrix-product.groovy declares Matrix A as rows: 2, cols: 3. Everything below
        // is rejected on the JVM side, so the backend is never reached: the declaration in the
        // DSL is the contract, not a comment about one.
        CountingBackend backend = new CountingBackend()
        LibTorchProvider provider = new LibTorchProvider('MatrixProduct', backend)
        LibTorchPlan plan = provider.compile(MathDsl.evaluate(
            new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy')))
        try {
            assert plan.inputId == 'A'
            assert plan.declaredInputShape.toList() == [2L, 3L]

            // Right width, wrong number of rows: 3x3 against a declared 2x3.
            TensorContractException wrongRows = assertThrows(TensorContractException) {
                plan.execute([1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f] as float[])
            }
            assert wrongRows.tensorId == 'A'
            assert wrongRows.message.contains('Dimension mismatch at axis 0')

            // Not a whole number of rows at all.
            TensorContractException ragged = assertThrows(TensorContractException) {
                plan.execute([1f, 2f, 3f, 4f] as float[])
            }
            assert ragged.tensorId == 'A'
            assert ragged.message.contains('not a whole number of rows')

            TensorContractException empty = assertThrows(TensorContractException) {
                plan.execute(new float[0])
            }
            assert empty.tensorId == 'A'

            assert backend.executions == 0 : 'A contract violation must not reach the backend'

            // The declared 2x3 still goes through.
            assert plan.execute([1f, 2f, 3f, 4f, 5f, 6f] as float[]).values.toList() == [58f, 64f, 139f, 154f]
            assert backend.executions == 1
        } finally {
            plan.close()
        }
    }

    @Test
    void refusesAnElementTypeTheBridgeCannotHonour() {
        // Every buffer, weight and slot in the C++ bridge is a float, and run_plan reshapes the
        // input with at::kFloat. A model declaring float64 would be narrowed on the way across
        // without a word, so the plan refuses to build instead of returning quietly wrong numbers.
        TensorContractException refused = assertThrows(TensorContractException) {
            new LibTorchProvider('DtModel', new CountingBackend()).compile(tensorPipeline('DtFloat64'))
        }
        assert refused.tensorId == 'InTensor'
        assert refused.expected == 'DtFloat32'
        assert refused.actual == 'DtFloat64'

        // The same pipeline declaring float32 builds, and so does one declaring nothing at all.
        ['DtFloat32', null].each { String declared ->
            LibTorchPlan plan = new LibTorchProvider('DtModel', new CountingBackend())
                .compile(tensorPipeline(declared))
            try {
                assert plan.declaredDataType == TensorDescriptor.DTYPE_FLOAT32
                assert plan.inputId == 'InTensor'
            } finally {
                plan.close()
            }
        }
    }

    /** A minimal tensor pipeline: one input tensor, one ReLU, one result. */
    private static MathMeta tensorPipeline(final String dataTypeEnumId) {
        MathDsl.math {
            MathModelDef('DtDef', description: 'Element type contract')
            MathModel('DtModel') {
                mathModelDefId 'DtDef'
                statusId 'MathModelDraft'
            }
            Map<String, Object> inputTensor = [purposeEnumId: 'TpOriginal', rank: 2L, shape: '[2, 3]']
            if (dataTypeEnumId != null) inputTensor.put('dataTypeEnumId', dataTypeEnumId)
            Tensor('InTensor', inputTensor)
            Tensor('OutTensor', rank: 2L, shape: '[2, 3]')
            Transformation('Activate', transformationTypeEnumId: 'TtTensorReLu', resultTensorId: 'OutTensor') {
                operands(operandIndex: 0L, operandTypeEnumId: 'TotSingle', operandTensorId: 'InTensor')
            }
            MathModelData('InData', mathModelId: 'DtModel', tensorId: 'InTensor',
                dataTypeEnumId: 'MmdtTensor', sequenceNum: 1L)
            MathModelDefPipeline('StepData', mathModelDefId: 'DtDef', stepSeqId: '01',
                transformationId: 'Activate', sequenceNum: 10L)
        }.validate()
    }

    /** Records how many times execution actually crossed into the backend. */
    private static class CountingBackend extends RecordingBackend {
        int executions

        @Override float[] execute(final long handle, final float[] input, final int batchSize) {
            executions++
            super.execute(handle, input, batchSize)
        }
    }

    private static class RecordingBackend implements LibTorchBackend {
        final List<String> operations = []
        boolean sealed
        boolean destroyed

        @Override long createPlan(final int inputWidth) { assert inputWidth == 3; 1L }
        @Override void addAffine(final long handle, final int inputSlot, final int outputSlot,
                                 final int inputWidth, final int outputWidth,
                                 final float[] weight, final float[] bias) {
            operations.add("affine:${inputWidth}x${outputWidth}")
            assert weight.length == inputWidth * outputWidth
            assert bias.length == outputWidth
        }
        @Override void addRelu(final long handle, final int inputSlot, final int outputSlot) {
            operations.add('relu')
        }
        @Override void addMatrixProduct(final long handle, final int inputSlot, final int outputSlot,
                                        final int inputWidth, final int outputWidth,
                                        final float[] rightMatrix) {
            operations.add("matrixProduct:${inputWidth}x${outputWidth}")
            assert rightMatrix.toList() == [7f, 8f, 9f, 10f, 11f, 12f]
        }
        @Override void seal(final long handle, final int outputSlot, final int outputWidth) { sealed = true }
        @Override float[] execute(final long handle, final float[] input, final int batchSize) {
            assert input.toList() == [1f, 2f, 3f, 4f, 5f, 6f]
            [58f, 64f, 139f, 154f] as float[]
        }
        @Override void executeDirect(final long handle, final ByteBuffer input, final int batchSize,
                                     final ByteBuffer output) { }
        @Override void destroy(final long handle) { destroyed = true }
        @Override void configureThreads(final int intraOpThreads, final int interOpThreads) { }
        @Override int intraOpThreads() { 1 }
        @Override int interOpThreads() { 1 }
    }
}
