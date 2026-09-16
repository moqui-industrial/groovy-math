/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

enum TransformationType implements DslEnumValue {
    MatrixProduct('TtMatrixProduct'),
    TensorReLu('TtTensorReLu'),
    TensorSigmoid('TtTensorSigmoid'),
    TensorGelu('TtTensorGelu'),
    TensorSilu('TtTensorSilu'),
    TensorTanh('TtTensorTanh'),
    TensorLeakyReLu('TtTensorLeakyReLu'),
    TensorElu('TtTensorElu'),
    TensorSoftmax('TtTensorSoftmax'),
    TensorLogSoftmax('TtTensorLogSoftmax'),
    LayerNorm('TtLayerNorm'),
    RMSNorm('TtRMSNorm'),
    BatchNorm('TtBatchNorm'),
    GroupNorm('TtGroupNorm'),
    Affine('TtAffine'),
    GaussianBlur('TtGaussianBlur'),
    Sobel('TtSobel'),
    Canny('TtCanny'),
    WarpAffine('TtWarpAffine'),
    WarpPerspective('TtWarpPerspective'),
    Filter2D('TtFilter2D'),
    Conv1d('TtConv1d'),
    Conv2d('TtConv2d'),
    MaxPool2d('TtMaxPool2d'),
    AvgPool2d('TtAvgPool2d'),
    AdaptiveAvgPool2d('TtAdaptiveAvgPool2d'),
    AttentionMask('TtAttentionMask'),
    ScaledDotProductAttention('TtScaledDotProductAttention'),
    Embedding('TtEmbedding'),
    TensorAdd('TtTensorAdd'),
    TensorSub('TtTensorSub'),
    TensorMul('TtTensorMul'),
    TensorDiv('TtTensorDiv'),
    TensorPow('TtTensorPow'),
    TensorConcat('TtTensorConcat'),
    TensorSplit('TtTensorSplit'),
    TensorSqueeze('TtTensorSqueeze'),
    TensorUnsqueeze('TtTensorUnsqueeze'),
    TensorSum('TtTensorSum'),
    TensorMean('TtTensorMean'),
    TensorMax('TtTensorMax'),
    TensorMin('TtTensorMin'),
    LossMse('TtLossMse'),
    LossCrossEntropy('TtLossCrossEntropy'),
    LossBceWithLogits('TtLossBceWithLogits'),
    LossL1('TtLossL1'),
    OptimizerSgd('TtOptimizerSgd'),
    OptimizerAdam('TtOptimizerAdam'),
    OptimizerAdamW('TtOptimizerAdamW'),
    Svd('TtSvd'),
    LuDecomp('TtLuDecomp'),
    QrDecomp('TtQrDecomp'),
    CholeskyDecomp('TtCholeskyDecomp'),
    EigenvalueDecomp('TtEigenvalueDecomp'),
    DiagExtract('TtDiagExtract'),
    UpperTriangExtract('TtMatrixUpperTriang'),
    LowerTriangExtract('TtMatrixLowerTriang'),
    BandExtract('TtMatrixBandExtract'),
    BlockMatrixExtract('TtBlockMatrixExtr'),
    Norm('TtNorm'),
    Composition('TtComposition'),
    TensorDecomp('TtTensorDecomp')

    final String id

    TransformationType(final String id) {
        this.id = id
    }
}
