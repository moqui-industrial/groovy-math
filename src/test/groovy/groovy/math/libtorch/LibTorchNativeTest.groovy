/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.libtorch

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta
import groovy.math.moqui.MoquiSchemaInspector

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.Callable
import java.util.concurrent.Executors

@Tag('libtorch-native')
class LibTorchNativeTest {
    @Test
    void executesNestedMatrixInputThroughPyTorchBlock() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES')
        assert schemaPath
        MathMeta mathMeta = MathDsl.evaluate(
            new File(schemaPath),
            new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy'))

        LibTorchResult result = PyTorch.execute(mathMeta, 'MatrixProduct') {
            threads intraOp: 1, interOp: 1
            input 'A', [[1, 2, 3], [4, 5, 6]]
        }

        assert result.tensorName == 'C'
        assert result.batchSize == 2
        assert result.width == 2
        assert result.values.toList() == [58f, 64f, 139f, 154f]
    }

    @Test
    void executesArraysDirectBuffersAndConcurrentRequests() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES')
        assert schemaPath
        MathMeta mathMeta = MathDsl.evaluate(
            MoquiSchemaInspector.inspect(new File(schemaPath)),
            new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy'))
        LibTorchProvider provider = new LibTorchProvider('MatrixProduct')
        provider.configureThreads(1, 1)

        LibTorchPlan plan = provider.compile(mathMeta)
        try {
            LibTorchResult result = plan.execute([1f, 2f, 3f, 4f, 5f, 6f] as float[])
            assert result.values.toList() == [58f, 64f, 139f, 154f]

            ByteBuffer input = directFloats([1f, 2f, 3f, 4f, 5f, 6f] as float[])
            ByteBuffer output = plan.executeDirect(input, 2)
            assert [output.asFloatBuffer().get(0), output.asFloatBuffer().get(1),
                    output.asFloatBuffer().get(2), output.asFloatBuffer().get(3)] ==
                [58f, 64f, 139f, 154f]

            def pool = Executors.newFixedThreadPool(8)
            try {
                List<Callable<Float>> work = (0..<64).collect {
                    { -> plan.execute([1f, 2f, 3f, 4f, 5f, 6f] as float[]).values[3] } as Callable<Float>
                }
                assert pool.invokeAll(work)*.get().every { Math.abs(it - 154f) < 0.0001f }
            } finally {
                pool.shutdown()
            }
        } finally {
            plan.close()
        }
        assert plan.closed
    }

    private static ByteBuffer directFloats(final float[] values) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(values.length * Float.BYTES).order(ByteOrder.nativeOrder())
        buffer.asFloatBuffer().put(values)
        buffer
    }
}
