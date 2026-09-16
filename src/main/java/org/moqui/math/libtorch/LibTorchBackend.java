/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch;

import java.nio.ByteBuffer;

public interface LibTorchBackend {
    long createPlan(int inputWidth);
    void addAffine(long handle, int inputSlot, int outputSlot, int inputWidth, int outputWidth,
                   float[] weight, float[] bias);
    void addMatrixProduct(long handle, int inputSlot, int outputSlot, int inputWidth, int outputWidth,
                          float[] rightMatrix);
    void addRelu(long handle, int inputSlot, int outputSlot);
    void seal(long handle, int outputSlot, int outputWidth);
    float[] execute(long handle, float[] input, int batchSize);
    void executeDirect(long handle, ByteBuffer input, int batchSize, ByteBuffer output);
    void destroy(long handle);
    void configureThreads(int intraOpThreads, int interOpThreads);
    int intraOpThreads();
    int interOpThreads();

    // DL Activations
    default void addSigmoid(long handle, int inputSlot, int outputSlot) { throw new UnsupportedOperationException(); }
    default void addGelu(long handle, int inputSlot, int outputSlot) { throw new UnsupportedOperationException(); }
    default void addSilu(long handle, int inputSlot, int outputSlot) { throw new UnsupportedOperationException(); }
    default void addTanh(long handle, int inputSlot, int outputSlot) { throw new UnsupportedOperationException(); }
    default void addLeakyRelu(long handle, int inputSlot, int outputSlot, float negativeSlope) { throw new UnsupportedOperationException(); }
    default void addElu(long handle, int inputSlot, int outputSlot, float alpha) { throw new UnsupportedOperationException(); }
    default void addSoftmax(long handle, int inputSlot, int outputSlot, long dim) { throw new UnsupportedOperationException(); }
    default void addLogSoftmax(long handle, int inputSlot, int outputSlot, long dim) { throw new UnsupportedOperationException(); }

    // Normalizations
    default void addLayerNorm(long handle, int inputSlot, int outputSlot, int normalizedWidth,
                              float[] weight, float[] bias, float eps) { throw new UnsupportedOperationException(); }
    default void addRMSNorm(long handle, int inputSlot, int outputSlot, int normalizedWidth,
                            float[] weight, float eps) { throw new UnsupportedOperationException(); }

    // Attention & Masking
    default void addAttentionMask(long handle, int inputSlot, int outputSlot, long rows, long cols,
                                  float[] maskData) { throw new UnsupportedOperationException(); }
    default void addScaledDotProductAttention(long handle, int querySlot, int keySlot, int valueSlot,
                                              int outputSlot, float scale) { throw new UnsupportedOperationException(); }

    // Elementwise Math & Reductions
    default void addBinaryOp(long handle, int opType, int inputSlotA, int inputSlotB, int outputSlot) {
        throw new UnsupportedOperationException();
    }
    default void addUnaryMath(long handle, int opType, int inputSlot, int outputSlot, float param) {
        throw new UnsupportedOperationException();
    }
    default void addReduction(long handle, int redType, int inputSlot, int outputSlot, long dim, boolean keepDim) {
        throw new UnsupportedOperationException();
    }
    default void addLoss(long handle, int lossType, int predSlot, int targetSlot, int outputSlot) {
        throw new UnsupportedOperationException();
    }

    // Autograd, Training & Optimizer
    default void setTraining(long handle, boolean isTraining) { throw new UnsupportedOperationException(); }
    default void backward(long handle, int lossSlot) { throw new UnsupportedOperationException(); }
    default void stepOptimizer(long handle, int optType, float lr, float weightDecay, float momentum) {
        throw new UnsupportedOperationException();
    }
    default void zeroGrad(long handle) { throw new UnsupportedOperationException(); }
    default void tensorOp(int opId, float[] input, float[] output, float param) {
        throw new UnsupportedOperationException();
    }
}
