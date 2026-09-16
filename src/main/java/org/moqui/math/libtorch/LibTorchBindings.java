/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch;

import java.nio.ByteBuffer;

final class LibTorchBindings {
    private LibTorchBindings() { }

    static native long nativeCreatePlan(int inputWidth);
    static native void nativeAddAffine(long handle, int inputSlot, int outputSlot,
                                       int inputWidth, int outputWidth, float[] weight, float[] bias);
    static native void nativeAddMatrixProduct(long handle, int inputSlot, int outputSlot,
                                              int inputWidth, int outputWidth, float[] rightMatrix);
    static native void nativeAddRelu(long handle, int inputSlot, int outputSlot);
    static native void nativeSeal(long handle, int outputSlot, int outputWidth);
    static native float[] nativeExecute(long handle, float[] input, int batchSize);
    static native void nativeExecuteDirect(long handle, ByteBuffer input, int batchSize, ByteBuffer output);
    static native void nativeDestroy(long handle);
    static native void nativeConfigureThreads(int intraOpThreads, int interOpThreads);
    static native int nativeIntraOpThreads();
    static native int nativeInterOpThreads();
}
