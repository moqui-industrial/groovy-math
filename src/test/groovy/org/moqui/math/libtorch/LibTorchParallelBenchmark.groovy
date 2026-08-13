/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import org.moqui.math.dsl.MathDsl
import org.moqui.math.moqui.MoquiSchemaInspector

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.Callable
import java.util.concurrent.Executors

final class LibTorchParallelBenchmark {
    static void main(final String[] arguments) {
        File schema = new File(requiredEnvironment('MOQUI_MATH_ENTITIES'))
        File example = new File(System.getProperty('user.dir'), 'examples/libtorch-mlp.groovy')
        int iterations = Integer.getInteger('groovy.math.benchmark.iterations', 2000)
        println 'mode,intraOp,callers,batch,iterations,elapsedMs,samplesPerSecond'

        [1, 2, 4, 8].each { int intraOp ->
            LibTorchProvider provider = new LibTorchProvider('IrisClassifier')
            provider.configureThreads(intraOp, 1)
            LibTorchPlan plan = provider.compile(MathDsl.evaluate(MoquiSchemaInspector.inspect(schema), example))
            try {
                [1, 64, 1024].each { int batch ->
                    [1, 2, 4, 8].each { int callers ->
                        measure(plan, 'array', intraOp, callers, batch, iterations)
                        measure(plan, 'direct', intraOp, callers, batch, iterations)
                    }
                }
            } finally {
                plan.close()
            }
        }
    }

    private static void measure(final LibTorchPlan plan, final String mode, final int intraOp,
                                final int callers, final int batch, final int iterations) {
        def pool = Executors.newFixedThreadPool(callers)
        try {
            List<Callable<Long>> workers = (0..<callers).collect {
                { -> runWorker(plan, mode, batch, iterations) } as Callable<Long>
            }
            runWorker(plan, mode, batch, Math.min(100, iterations))
            long started = System.nanoTime()
            pool.invokeAll(workers)*.get()
            long elapsed = System.nanoTime() - started
            double samplesPerSecond = callers * (double) iterations * batch * 1_000_000_000d / elapsed
            println String.format(Locale.ROOT, '%s,%d,%d,%d,%d,%.3f,%.1f',
                mode, intraOp, callers, batch, iterations, elapsed / 1_000_000d, samplesPerSecond)
        } finally {
            pool.shutdown()
        }
    }

    private static long runWorker(final LibTorchPlan plan, final String mode, final int batch,
                                  final int iterations) {
        float[] input = new float[batch * plan.inputWidth]
        for (int index = 0; index < input.length; index += plan.inputWidth) {
            input[index] = 1f; input[index + 1] = 2f; input[index + 2] = 3f; input[index + 3] = 4f
        }
        long checksum = 0L
        if (mode == 'array') {
            for (int iteration = 0; iteration < iterations; iteration++) {
                checksum += (long) (plan.execute(input).values[0] * 1000)
            }
        } else {
            ByteBuffer directInput = ByteBuffer.allocateDirect(input.length * Float.BYTES).order(ByteOrder.nativeOrder())
            directInput.asFloatBuffer().put(input)
            ByteBuffer directOutput = ByteBuffer.allocateDirect(batch * plan.outputWidth * Float.BYTES)
                .order(ByteOrder.nativeOrder())
            for (int iteration = 0; iteration < iterations; iteration++) {
                plan.executeDirect(directInput, batch, directOutput)
                checksum += (long) (directOutput.asFloatBuffer().get(0) * 1000)
            }
        }
        checksum
    }

    private static String requiredEnvironment(final String name) {
        String value = System.getenv(name)
        if (!value) throw new IllegalStateException("Environment variable ${name} is required")
        value
    }
}
