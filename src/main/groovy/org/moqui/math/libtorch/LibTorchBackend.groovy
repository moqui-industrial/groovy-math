/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import java.nio.ByteBuffer

interface LibTorchBackend {
    long createPlan(int inputWidth)
    void addAffine(long handle, int inputSlot, int outputSlot, int inputWidth, int outputWidth,
                   float[] weight, float[] bias)
    void addRelu(long handle, int inputSlot, int outputSlot)
    void seal(long handle, int outputSlot, int outputWidth)
    float[] execute(long handle, float[] input, int batchSize)
    void executeDirect(long handle, ByteBuffer input, int batchSize, ByteBuffer output)
    void destroy(long handle)
    void configureThreads(int intraOpThreads, int interOpThreads)
    int intraOpThreads()
    int interOpThreads()
}
