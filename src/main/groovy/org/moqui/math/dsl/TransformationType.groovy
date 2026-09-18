/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TransformationType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TransformationType implements DslEnumValue {
    Linear('TtLinear', '', 'Linear Transformation', ''),
    NonLinear('TtNonLinear', '', 'Non-linear Transformation', ''),
    Meta('TtMeta', '', 'Meta-Transformation (composition, decomposition, compilation, etc.)', ''),
    Relation('TtRelation', '', 'Relational Transformation or Predicate', ''),
    Equality('TtEquality', '', 'Equality Relation', 'TtRelation'),
    Inequality('TtInequality', '', 'Inequality Relation', 'TtRelation'),
    NotEqual('TtNotEqual', '', 'Not Equal Relation', 'TtInequality'),
    LessThan('TtLessThan', '', 'Less Than Relation', 'TtInequality'),
    LessEqual('TtLessEqual', '', 'Less Than or Equal Relation', 'TtInequality'),
    GreaterThan('TtGreaterThan', '', 'Greater Than Relation', 'TtInequality'),
    GreaterEqual('TtGreaterEqual', '', 'Greater Than or Equal Relation', 'TtInequality'),
    Membership('TtMembership', '', 'Set or Type Membership Relation', 'TtRelation'),
    UnaryLin('TtUnaryLin', '', 'Unary linear transformation', 'TtLinear'),
    BinaryLin('TtBinaryLin', '', 'Binary linear transformation', 'TtLinear'),
    UnaryNonLin('TtUnaryNonLin', '', 'Unary non-linear transformation', 'TtNonLinear'),
    BinaryNonLin('TtBinaryNonLin', '', 'Binary non-linear transformation', 'TtNonLinear'),
    NaryNonLin('TtNaryNonLin', '', 'N-ary non-linear transformation', 'TtNonLinear'),
    SetPermutation('TtSetPermutation', '', 'Set Permutation', 'TtUnaryLin'),
    SetCombination('TtSetCombination', '', 'Set Combination', 'TtUnaryLin'),
    MatrixFlipLeftRight('TtMatrixFlipLeftRight', '', 'Matrix Flip Left Right', 'TtUnaryLin'),
    MatrixFlipUpDown('TtMatrixFlipUpDown', '', 'Matrix Flip Up Down', 'TtUnaryLin'),
    MatrixRotate90('TtMatrixRotate90', '', 'Matrix Rotate 90°', 'TtUnaryLin'),
    MatrixTranspose('TtMatrixTranspose', '', 'Matrix Transpose', 'TtUnaryLin'),
    MatrixAdjoint('TtMatrixAdjoint', '', 'Matrix Conjugate Transpose / Adjoint', 'TtUnaryLin'),
    MatrixTrace('TtMatrixTrace', '', 'Matrix Trace', 'TtUnaryLin'),
    MatrixDiagonal('TtMatrixDiagonal', '', 'Matrix Diagonal', 'TtUnaryLin'),
    MatrixAntiDiagonal('TtMatrixAntiDiagonal', '', 'Matrix AntiDiagonal', 'TtUnaryLin'),
    MatrixVectorize('TtMatrixVectorize', '', 'Matrix Vectorize', 'TtUnaryLin'),
    MatrixUpperTriang('TtMatrixUpperTriang', '', 'Matrix Upper-Triangular Extract', 'TtUnaryLin'),
    MatrixLowerTriang('TtMatrixLowerTriang', '', 'Matrix Lower-Triangular Extract', 'TtUnaryLin'),
    MatrixBandExtract('TtMatrixBandExtract', '', 'Matrix Band Extract', 'TtUnaryLin'),
    TensorReshape('TtTensorReshape', '', 'Tensor Reshape', 'TtUnaryLin'),
    TensorPermute('TtTensorPermute', '', 'Tensor Axis Permute / Transpose', 'TtUnaryLin'),
    TensorVectorize('TtTensorVectorize', '', 'Tensor Vectorize', 'TtUnaryLin'),
    TensorUnfold('TtTensorUnfold', '', 'Tensor Matricization / Unfold', 'TtUnaryLin'),
    TensorFlip('TtTensorFlip', '', 'Reverse tensor along given axis/axes', 'TtUnaryLin'),
    TensorFlipUpDown('TtTensorFlipUpDown', '', 'Flip up/down (reverse axis 0)', 'TtTensorFlip'),
    TensorFlipLeftRight('TtTensorFlipLeftRight', '', 'Flip left/right (reverse axis 1)', 'TtTensorFlip'),
    TensorRotate90('TtTensorRotate90', '', 'Rotate 90° k times around the last two axes', 'TtUnaryLin'),
    Norm('TtNorm', '', 'Vector or Matrix Norm, Frobenius, Nuclear, etc.', 'TtUnaryNonLin'),
    MatrixReciprocal('TtMatrixReciprocal', '', 'Matrix Reciprocal', 'TtUnaryNonLin'),
    TensorReciprocal('TtTensorReciprocal', '', 'Tensor Reciprocal', 'TtUnaryNonLin'),
    MatrixInverse('TtMatrixInverse', '', 'Matrix Inversion', 'TtUnaryNonLin'),
    MatrixExp('TtMatrixExp', '', 'Matrix Exponential (eᴬ)', 'TtUnaryNonLin'),
    MatrixPower('TtMatrixPower', '', 'Matrix Integer Power (Aⁿ)', 'TtUnaryNonLin'),
    MatrixPseudoInverse('TtMatrixPseudoInverse', '', 'Pseudo Inverse Matrix', 'TtUnaryNonLin'),
    FunctionCompose('TtFunctionCompose', '', 'Composition of Approximated Functions (f ∘ g)', 'TtUnaryLin'),
    DiagExtract('TtDiagExtract', '', 'Diagonal / Band Extraction', 'TtUnaryLin'),
    TensorExp('TtTensorExp', '', 'Element-wise exponential: Yᵢ = eˣᵢ', 'TtUnaryNonLin'),
    TensorLog('TtTensorLog', '', 'Element-wise natural log: Yᵢ = ln xᵢ', 'TtUnaryNonLin'),
    TensorSqrt('TtTensorSqrt', '', 'Element-wise square-root: Yᵢ = √xᵢ', 'TtUnaryNonLin'),
    TensorReLu('TtTensorReLu', '', 'ReLU: Yᵢ = max(0, xᵢ)', 'TtUnaryNonLin'),
    TensorSigmoid('TtTensorSigmoid', '', 'Sigmoid: Yᵢ = 1 / (1 + e⁻ˣᵢ)', 'TtUnaryNonLin'),
    TensorGelu('TtTensorGelu', '', 'Gaussian Error Linear Unit (GELU)', 'TtUnaryNonLin'),
    TensorSilu('TtTensorSilu', '', 'Sigmoid Linear Unit / Swish (SiLU)', 'TtUnaryNonLin'),
    TensorTanh('TtTensorTanh', '', 'Hyperbolic Tangent: tanh(x)', 'TtUnaryNonLin'),
    TensorLeakyReLu('TtTensorLeakyReLu', '', 'Leaky ReLU: max(αx, x)', 'TtUnaryNonLin'),
    TensorElu('TtTensorElu', '', 'Exponential Linear Unit (ELU)', 'TtUnaryNonLin'),
    TensorSoftmax('TtTensorSoftmax', '', 'Softmax Normalization', 'TtUnaryNonLin'),
    TensorLogSoftmax('TtTensorLogSoftmax', '', 'Log-Softmax Normalization', 'TtUnaryNonLin'),
    LayerNorm('TtLayerNorm', '', 'Layer Normalization', 'TtUnaryNonLin'),
    RMSNorm('TtRMSNorm', '', 'Root Mean Square Layer Normalization', 'TtUnaryNonLin'),
    BatchNorm('TtBatchNorm', '', 'Batch Normalization', 'TtUnaryNonLin'),
    GroupNorm('TtGroupNorm', '', 'Group Normalization', 'TtUnaryNonLin'),
    TensorInv('TtTensorInv', '', 'Tensor Inverse', 'TtUnaryNonLin'),
    GaussianBlur('TtGaussianBlur', '', 'Gaussian Blur Filter', 'TtUnaryLin'),
    Sobel('TtSobel', '', 'Sobel Spatial Gradient', 'TtUnaryLin'),
    Canny('TtCanny', '', 'Canny Edge Detector', 'TtUnaryNonLin'),
    WarpAffine('TtWarpAffine', '', 'Affine Geometric Transformation', 'TtAffine'),
    WarpPerspective('TtWarpPerspective', '', 'Perspective Homography Transformation', 'TtUnaryLin'),
    Filter2D('TtFilter2D', '', '2D Spatial Convolution Filter', 'TtUnaryLin'),
    Conv1d('TtConv1d', '', '1D Temporal Convolution', 'TtUnaryLin'),
    Conv2d('TtConv2d', '', '2D Spatial Convolution', 'TtUnaryLin'),
    MaxPool2d('TtMaxPool2d', '', '2D Spatial Max Pooling', 'TtUnaryNonLin'),
    AvgPool2d('TtAvgPool2d', '', '2D Spatial Average Pooling', 'TtUnaryLin'),
    AdaptiveAvgPool2d('TtAdaptiveAvgPool2d', '', '2D Adaptive Average Pooling', 'TtUnaryLin'),
    AttentionMask('TtAttentionMask', '', 'Attention Additive Mask', 'TtBinaryLin'),
    ScaledDotProductAttention('TtScaledDotProductAttention', '', 'Scaled Dot-Product Attention', 'TtNaryNonLin'),
    Embedding('TtEmbedding', '', 'Embedding Lookup Table', 'TtUnaryLin'),
    MatrixProduct('TtMatrixProduct', '', 'Matrix Product', 'TtBinaryLin'),
    MatrixApply('TtMatrixApply', '', 'Matrix Application to a Vector / Matrix', 'TtBinaryLin'),
    TensorContract('TtTensorContract', '', 'Tensor Contraction (Einsum)', 'TtBinaryLin'),
    TensorOuter('TtTensorOuter', '', 'Outer / Kronecker Product', 'TtBinaryLin'),
    VectorCross('TtVectorCross', '', '3-D Cross Product (a x b)', 'TtBinaryLin'),
    VectorDot('TtVectorDot', '', 'Vector Dot Product', 'TtBinaryLin'),
    TensorSolve('TtTensorSolve', '', 'Tensor Solve', 'TtBinaryNonLin'),
    TensorAdd('TtTensorAdd', '', 'Tensor Element-wise Addition', 'TtBinaryLin'),
    TensorSub('TtTensorSub', '', 'Tensor Element-wise Subtraction', 'TtBinaryLin'),
    TensorMul('TtTensorMul', '', 'Tensor Element-wise Multiplication', 'TtBinaryLin'),
    TensorDiv('TtTensorDiv', '', 'Tensor Element-wise Division', 'TtBinaryNonLin'),
    TensorPow('TtTensorPow', '', 'Tensor Element-wise Power', 'TtBinaryNonLin'),
    TensorConcat('TtTensorConcat', '', 'Tensor Concatenation', 'TtNaryLin'),
    TensorSplit('TtTensorSplit', '', 'Tensor Split', 'TtUnaryLin'),
    TensorSqueeze('TtTensorSqueeze', '', 'Tensor Squeeze', 'TtUnaryLin'),
    TensorUnsqueeze('TtTensorUnsqueeze', '', 'Tensor Unsqueeze', 'TtUnaryLin'),
    TensorSum('TtTensorSum', '', 'Tensor Sum Reduction', 'TtUnaryLin'),
    TensorMean('TtTensorMean', '', 'Tensor Mean Reduction', 'TtUnaryLin'),
    TensorMax('TtTensorMax', '', 'Tensor Max Reduction', 'TtUnaryNonLin'),
    TensorMin('TtTensorMin', '', 'Tensor Min Reduction', 'TtUnaryNonLin'),
    Affine('TtAffine', '', 'Affine Map (y = A·x + b)', 'TtBinaryNonLin'),
    LinearMap('TtLinearMap', '', 'Linear Map (y = A·x)', 'TtBinaryLin'),
    NaryLin('TtNaryLin', '', 'N-ary Transformation', 'TtLinear'),
    MultiDot('TtMultiDot', '', 'Multi-Matrix Dot', 'TtNaryLin'),
    HouseholderProd('TtHouseholderProd', '', 'Householder Product', 'TtNaryLin'),
    Composition('TtComposition', '', 'Composition of Transformations', 'TtMeta'),
    Decomp('TtDecomp', '', 'Decomposition / Factorisation', 'TtMeta'),
    TensorDecomp('TtTensorDecomp', '', 'Tensor Decomposition / Factorisation', 'TtDecomp'),
    MatrixDecomp('TtMatrixDecomp', '', 'Matrix Decomposition / Factorisation', 'TtDecomp'),
    Gradient('TtGradient', '', 'Gradient ∇f(x)', 'TtMeta'),
    Jacobian('TtJacobian', '', 'Jacobian J_f(x)', 'TtMeta'),
    Hessian('TtHessian', '', 'Hessian ∇²f(x)', 'TtMeta'),
    JitCompile('TtJitCompile', '', 'Just-in-time Compilation (XLA, Inductor, ecc.)', 'TtMeta'),
    Vmap('TtVmap', '', 'Vectorised Map', 'TtMeta'),
    LossMse('TtLossMse', '', 'Mean Squared Error Loss (MSE)', 'TtBinaryNonLin'),
    LossCrossEntropy('TtLossCrossEntropy', '', 'Cross Entropy Classification Loss', 'TtBinaryNonLin'),
    LossBceWithLogits('TtLossBceWithLogits', '', 'Binary Cross Entropy with Logits', 'TtBinaryNonLin'),
    LossL1('TtLossL1', '', 'L1 / Mean Absolute Error Loss', 'TtBinaryNonLin'),
    OptimizerSgd('TtOptimizerSgd', '', 'Stochastic Gradient Descent Optimizer', 'TtMeta'),
    OptimizerAdam('TtOptimizerAdam', '', 'Adam Optimizer', 'TtMeta'),
    OptimizerAdamW('TtOptimizerAdamW', '', 'AdamW Optimizer', 'TtMeta'),
    Logical('TtLogical', '', 'Logical Transformation', ''),
    And('TtAnd', '', 'Logical Conjunction', 'TtLogical'),
    Or('TtOr', '', 'Logical Disjunction', 'TtLogical'),
    Not('TtNot', '', 'Logical Negation', 'TtLogical'),
    Implies('TtImplies', '', 'Logical Implication', 'TtLogical'),
    Xor('TtXor', '', 'Logical Exclusive Or', 'TtLogical'),
    True('TtTrue', '', 'Logical True', 'TtLogical'),
    False('TtFalse', '', 'Logical False', 'TtLogical'),
    BlockMatrixExtr('TtBlockMatrixExtr', '', 'Block Matrix Extraction (submatrix) from a Matrix', ''),
    RegularBlockMatrixExtr('TtRegularBlockMatrixExtr', '', 'Grid or Regular Blocking Extraction. The matrix is partitioned into blocks by both rows and columns.', 'TtBlockMatrixExtr'),
    HorizBlockMatrixExtr('TtHorizBlockMatrixExtr', '', 'Horizontal Blocking Extraction. The matrix is split into horizontal strips (blocks). Each block contains a set of rows.', 'TtBlockMatrixExtr'),
    VertBlockMatrixExtr('TtVertBlockMatrixExtr', '', 'Vertical Blocking Extraction. The matrix is split into vertical strips (blocks). Each block contains a set of columns.', 'TtBlockMatrixExtr'),
    HierBlockMatrixExtr('TtHierBlockMatrixExtr', '', 'Hierarchical or Recursive Blocking. The matrix is split into vertical strips (blocks). A matrix is recursively divided into smaller blocks within blocks, creating a hierarchical structure.', 'TtBlockMatrixExtr'),
    DiagBlockMatrixExtr('TtDiagBlockMatrixExtr', '', 'Diagonal Blocking Extraction. The matrix is divided into blocks along the diagonal, with each block being a square or rectangular submatrix. Off-diagonal blocks are typically zero.', 'TtBlockMatrixExtr'),
    SymBlockMatrixExtr('TtSymBlockMatrixExtr', '', 'Symmetric Blocking Extraction. Blocks are extracted in such a way that if the original matrix is symmetric, the block structure also respects this symmetry.', 'TtBlockMatrixExtr'),
    IrregBlockMatrixExtr('TtIrregBlockMatrixExtr', '', 'Irregular Blocking Extraction. The matrix is divided into blocks that may have different sizes, depending on specific criteria, such as data distribution or computational efficiency.', 'TtBlockMatrixExtr'),
    DomainBlockMatrixExtr('TtDomainBlockMatrixExtr', '', 'Domain-Specific Blocking Extraction. The blocking pattern is chosen based on the application domain, such as signal processing, image processing, or finite element methods.', 'TtBlockMatrixExtr'),
    EigenvalueDecomp('TtEigenvalueDecomp', '', 'Eigenvalue Decomposition', 'TtMatrixDecomp'),
    Diagonalise('TtDiagonalise', '', 'Diagonalisation (A = PDP⁻¹)', 'TtEigenvalueDecomp'),
    SymmetricEigenDecomp('TtSymmetricEigenDecomp', '', 'Symmetric Eigenvalue Decomposition', 'TtMatrixDecomp'),
    Eigh('TtEigh', '', 'Eigh Decomposition.', 'TtMatrixDecomp'),
    LuDecomp('TtLuDecomp', '', 'LU Decomposition', 'TtMatrixDecomp'),
    LuFactorDecomp('TtLuFactorDecomp', '', 'LU Factor Decomposition', 'TtMatrixDecomp'),
    QrDecomp('TtQrDecomp', '', 'QR Decomposition', 'TtMatrixDecomp'),
    CholeskyDecomp('TtCholeskyDecomp', '', 'Cholesky Decomposition', 'TtMatrixDecomp'),
    JordanDecomp('TtJordanDecomp', '', 'Jordan Decomposition', 'TtMatrixDecomp'),
    LDLDecomp('TtLDLDecomp', '', 'LDLᵗ Decomposition', 'TtMatrixDecomp'),
    PolarDecomp('TtPolarDecomp', '', 'Polar Decomposition', 'TtMatrixDecomp'),
    SchurDecomp('TtSchurDecomp', '', 'Schur Decomposition', 'TtMatrixDecomp'),
    HessenbergDecomp('TtHessenbergDecomp', '', 'Hessenberg Decomposition', 'TtMatrixDecomp'),
    Svd('TtSvd', '', 'Singular Value Decomposition (SVD) on a Matrix', 'TtMatrixDecomp'),
    SvdTruncated('TtSvdTruncated', '', 'Truncated Singular Value Decomposition (SVD). It is used primarily in data compression, information retrieval.', 'TtSvd'),
    SvdEconomy('TtSvdEconomy', '', 'Economy Singular Value Decomposition (SVD). It removes redundant zero rows or columns from U and Sigma matrices.', 'TtSvd');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TransformationType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
        this.id = id
        this.enumCode = enumCode
        this.description = description
        this.parentEnumId = parentEnumId
    }

    @Override
    String getId() { id }

    @Override
    String getEnumCode() { enumCode }

    @Override
    String getDescription() { description }

    @Override
    String getParentEnumId() { parentEnumId }

    static TransformationType fromId(final String id) {
        if (id == null) return null
        for (TransformationType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TransformationType fromCode(final String code) {
        if (code == null) return null
        for (TransformationType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
