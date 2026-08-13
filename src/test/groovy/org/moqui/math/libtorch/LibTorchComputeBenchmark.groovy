/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.Callable
import java.util.concurrent.Executors

final class LibTorchComputeBenchmark {
    static void main(final String[] arguments) {
        int width = Integer.getInteger('groovy.math.benchmark.width', 256)
        int batch = Integer.getInteger('groovy.math.benchmark.batch', 16)
        int iterations = Integer.getInteger('groovy.math.benchmark.iterations', 100)
        LibTorchBackend backend = LibTorchNative.INSTANCE
        LibTorchPlan plan = createPlan(backend, width)
        println 'intraOp,callers,width,batch,iterations,elapsedMs,samplesPerSecond'
        try {
            backend.configureThreads(Math.min(8, Runtime.runtime.availableProcessors()), 1)
            runWorker(plan, width, batch, Math.max(10, iterations.intdiv(4)))
            [1, 2, 4, 8].each { int intraOp ->
                backend.configureThreads(intraOp, 1)
                [1, 2, 4, 8].each { int callers ->
                    measure(plan, intraOp, callers, width, batch, iterations)
                }
            }
        } finally {
            plan.close()
        }
    }

    private static LibTorchPlan createPlan(final LibTorchBackend backend, final int width) {
        float[] weight = new float[width * width]
        float[] bias = new float[width]
        for (int index = 0; index < width; index++) weight[index * width + index] = 0.5f
        long handle = backend.createPlan(width)
        try {
            backend.addAffine(handle, 0, 1, width, width, weight, bias)
            backend.addRelu(handle, 1, 2)
            backend.addAffine(handle, 2, 3, width, width, weight, bias)
            backend.seal(handle, 3, width)
            new LibTorchPlan('SyntheticDense', 'input', 'output', width, width, 3, backend, handle)
        } catch (Throwable failure) {
            backend.destroy(handle)
            throw failure
        }
    }

    private static void measure(final LibTorchPlan plan, final int intraOp, final int callers,
                                final int width, final int batch, final int iterations) {
        def pool = Executors.newFixedThreadPool(callers)
        try {
            List<Callable<Long>> workers = (0..<callers).collect {
                { -> runWorker(plan, width, batch, iterations) } as Callable<Long>
            }
            List<Callable<Long>> warmers = (0..<callers).collect {
                { -> runWorker(plan, width, batch, Math.max(5, iterations.intdiv(10))) } as Callable<Long>
            }
            pool.invokeAll(warmers)*.get()
            long[] elapsedValues = new long[3]
            for (int repetition = 0; repetition < elapsedValues.length; repetition++) {
                long started = System.nanoTime()
                pool.invokeAll(workers)*.get()
                elapsedValues[repetition] = System.nanoTime() - started
            }
            Arrays.sort(elapsedValues)
            long elapsed = elapsedValues[1]
            double rate = callers * (double) iterations * batch * 1_000_000_000d / elapsed
            println String.format(Locale.ROOT, '%d,%d,%d,%d,%d,%.3f,%.1f',
                intraOp, callers, width, batch, iterations, elapsed / 1_000_000d, rate)
        } finally {
            pool.shutdown()
        }
    }

    private static long runWorker(final LibTorchPlan plan, final int width, final int batch,
                                  final int iterations) {
        ByteBuffer input = ByteBuffer.allocateDirect(width * batch * Float.BYTES).order(ByteOrder.nativeOrder())
        ByteBuffer output = ByteBuffer.allocateDirect(width * batch * Float.BYTES).order(ByteOrder.nativeOrder())
        for (int index = 0; index < width * batch; index++) input.asFloatBuffer().put(index, 1f)
        long checksum = 0
        for (int iteration = 0; iteration < iterations; iteration++) {
            plan.executeDirect(input, batch, output)
            checksum += (long) output.asFloatBuffer().get(0)
        }
        checksum
    }
}
