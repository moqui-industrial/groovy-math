/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.libtorch

import org.junit.jupiter.api.Test
import groovy.math.dsl.MathDsl
import groovy.math.moqui.MoquiSchemaInspector

import java.nio.ByteBuffer

class LibTorchProviderTest {
    @Test
    void lowersMoquiMathObjectsDirectlyToProviderOperationsWhenSchemaIsConfigured() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES')
        if (!schemaPath) return
        RecordingBackend backend = new RecordingBackend()
        LibTorchProvider provider = new LibTorchProvider('MatrixProduct', backend)

        LibTorchPlan plan = provider.compile(MathDsl.evaluate(
            MoquiSchemaInspector.inspect(new File(schemaPath)),
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
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES')
        if (!schemaPath) return
        RecordingBackend backend = new RecordingBackend()
        LibTorchProvider provider = new LibTorchProvider('MatrixProduct', backend)

        LibTorchResult result = provider.run(MathDsl.evaluate(
            MoquiSchemaInspector.inspect(new File(schemaPath)),
            new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy')),
            [A: [[1, 2, 3], [4, 5, 6]]])

        assert result.tensorName == 'C'
        assert result.batchSize == 2
        assert result.width == 2
        assert result.values.toList() == [58f, 64f, 139f, 154f]
        assert backend.destroyed
    }

    private static final class RecordingBackend implements LibTorchBackend {
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
