/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathGraph
import org.moqui.math.moqui.MoquiSchemaInspector

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.Callable
import java.util.concurrent.Executors

@Tag('libtorch-native')
class LibTorchNativeTest {
    @Test
    void executesArraysDirectBuffersAndConcurrentRequests() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES')
        assert schemaPath
        MathGraph graph = MathDsl.evaluate(
            MoquiSchemaInspector.inspect(new File(schemaPath)),
            new File(System.getProperty('user.dir'), 'examples/libtorch-mlp.groovy'))
        LibTorchProvider provider = new LibTorchProvider('IrisClassifier')
        provider.configureThreads(1, 1)

        LibTorchPlan plan = provider.compile(graph)
        try {
            LibTorchResult result = plan.execute([1f, 2f, 3f, 4f] as float[])
            assert result.values.toList() == [1.1f, 1.9f, 7.05f]

            ByteBuffer input = directFloats([1f, 2f, 3f, 4f] as float[])
            ByteBuffer output = plan.executeDirect(input, 1)
            assert [output.asFloatBuffer().get(0), output.asFloatBuffer().get(1),
                    output.asFloatBuffer().get(2)] == [1.1f, 1.9f, 7.05f]

            def pool = Executors.newFixedThreadPool(8)
            try {
                List<Callable<Float>> work = (0..<64).collect {
                    { -> plan.execute([1f, 2f, 3f, 4f] as float[]).values[2] } as Callable<Float>
                }
                assert pool.invokeAll(work)*.get().every { Math.abs(it - 7.05f) < 0.0001f }
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
