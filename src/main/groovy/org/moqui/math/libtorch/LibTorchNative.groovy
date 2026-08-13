/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import groovy.transform.CompileStatic

import java.nio.ByteBuffer

@CompileStatic
final class LibTorchNative implements LibTorchBackend {
    static final LibTorchNative INSTANCE = new LibTorchNative()
    private static boolean loaded

    private LibTorchNative() { }

    private static synchronized void ensureLoaded() {
        if (loaded) return
        String explicitPath = System.getProperty('groovy.math.libtorch.library')
        if (explicitPath) System.load(new File(explicitPath).absolutePath)
        else System.loadLibrary('groovy_math_libtorch')
        loaded = true
    }

    @Override long createPlan(final int inputWidth) { ensureLoaded(); LibTorchBindings.nativeCreatePlan(inputWidth) }
    @Override void addAffine(final long handle, final int inputSlot, final int outputSlot,
                             final int inputWidth, final int outputWidth,
                             final float[] weight, final float[] bias) {
        ensureLoaded()
        LibTorchBindings.nativeAddAffine(handle, inputSlot, outputSlot, inputWidth, outputWidth, weight, bias)
    }
    @Override void addRelu(final long handle, final int inputSlot, final int outputSlot) {
        ensureLoaded(); LibTorchBindings.nativeAddRelu(handle, inputSlot, outputSlot)
    }
    @Override void addMatrixProduct(final long handle, final int inputSlot, final int outputSlot,
                                    final int inputWidth, final int outputWidth,
                                    final float[] rightMatrix) {
        ensureLoaded()
        LibTorchBindings.nativeAddMatrixProduct(
            handle, inputSlot, outputSlot, inputWidth, outputWidth, rightMatrix)
    }
    @Override void seal(final long handle, final int outputSlot, final int outputWidth) {
        ensureLoaded(); LibTorchBindings.nativeSeal(handle, outputSlot, outputWidth)
    }
    @Override float[] execute(final long handle, final float[] input, final int batchSize) {
        ensureLoaded(); LibTorchBindings.nativeExecute(handle, input, batchSize)
    }
    @Override void executeDirect(final long handle, final ByteBuffer input, final int batchSize,
                                 final ByteBuffer output) {
        ensureLoaded(); LibTorchBindings.nativeExecuteDirect(handle, input, batchSize, output)
    }
    @Override void destroy(final long handle) { ensureLoaded(); LibTorchBindings.nativeDestroy(handle) }
    @Override void configureThreads(final int intraOpThreads, final int interOpThreads) {
        ensureLoaded(); LibTorchBindings.nativeConfigureThreads(intraOpThreads, interOpThreads)
    }
    @Override int intraOpThreads() { ensureLoaded(); LibTorchBindings.nativeIntraOpThreads() }
    @Override int interOpThreads() { ensureLoaded(); LibTorchBindings.nativeInterOpThreads() }
}
