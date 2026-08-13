/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import org.junit.jupiter.api.Test
import org.moqui.math.dsl.MathDsl
import org.moqui.math.moqui.MoquiSchemaInspector

import java.nio.ByteBuffer

class LibTorchProviderTest {
    @Test
    void lowersMoquiMathObjectsDirectlyToProviderOperationsWhenSchemaIsConfigured() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES')
        if (!schemaPath) return
        RecordingBackend backend = new RecordingBackend()
        LibTorchProvider provider = new LibTorchProvider('IrisClassifier', backend)

        LibTorchPlan plan = provider.compile(MathDsl.evaluate(
            MoquiSchemaInspector.inspect(new File(schemaPath)),
            new File(System.getProperty('user.dir'), 'examples/libtorch-mlp.groovy')))
        try {
            assert plan.inputName == 'input'
            assert plan.outputName == 'logits'
            assert plan.inputWidth == 4
            assert plan.outputWidth == 3
            assert plan.operationCount == 3
            assert backend.operations == ['affine:4x8', 'relu', 'affine:8x3']
            assert backend.sealed
        } finally {
            plan.close()
        }
        assert backend.destroyed
    }

    private static final class RecordingBackend implements LibTorchBackend {
        final List<String> operations = []
        boolean sealed
        boolean destroyed

        @Override long createPlan(final int inputWidth) { assert inputWidth == 4; 1L }
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
        @Override void seal(final long handle, final int outputSlot, final int outputWidth) { sealed = true }
        @Override float[] execute(final long handle, final float[] input, final int batchSize) { new float[batchSize * 3] }
        @Override void executeDirect(final long handle, final ByteBuffer input, final int batchSize,
                                     final ByteBuffer output) { }
        @Override void destroy(final long handle) { destroyed = true }
        @Override void configureThreads(final int intraOpThreads, final int interOpThreads) { }
        @Override int intraOpThreads() { 1 }
        @Override int interOpThreads() { 1 }
    }
}
