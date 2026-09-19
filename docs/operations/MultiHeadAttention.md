# Multi-Head Attention (`TtMultiHeadAttention`)

- **Status**: Derived Operation (TransformationType: `TtMultiHeadAttention`, parent: `TtNaryNonLin`)
- **Bibliographic Reference**: Vaswani et al., *"Attention Is All You Need"*, NeurIPS 2017 (arXiv:1706.03762).

## 1. Mathematical Definition

Given input sequence tensor $X \in \mathbb{R}^{B \times T \times D}$, Multi-Head Attention projects $X$ into $H$ heads of dimension $d_k = D / H$, executes Scaled Dot-Product Attention across heads in parallel, and projects concatenated outputs back to dimension $D$:

$$\text{MHA}(X) = \text{Concat}(\text{head}_1, \dots, \text{head}_H) W^O$$

where each head $i \in \{1, \dots, H\}$ is computed as:

$$\text{head}_i = \text{SDPA}(X W_i^Q, X W_i^K, X W_i^V) = \text{softmax}\left(\frac{Q_i K_i^T}{\sqrt{d_k}} + M\right) V_i$$

with optional additive attention mask $M$.

## 2. Operands and Roles

| Operand | Role | Type | Expected Rank/Shape |
|---|---|---|---|
| `x` | Input Sequence / Query Tensor | `Tensor` | $[B, T, D]$ or $[*, T, D]$ |
| `context` (optional) | Key/Value Source Tensor (for cross-attention) | `Tensor` | $[B, S, D]$ (defaults to `x` for self-attention) |

## 3. Parameter Definitions and Defaults

| Parameter | Type | Default | Description |
|---|---|---|---|
| `numHeads` | `Integer` | required (e.g. 12) | Number of parallel attention heads $H$. Must divide embedding dimension $D$. |
| `embedDim` | `Integer` | inferred from input ($D$) | Total embedding dimension $D$. |
| `dropoutProb` | `Float` | 0.0 | Attention dropout probability $p \in [0, 1)$. |
| `isCausal` | `Boolean` | false | Whether to apply lower-triangular causal attention mask. |

## 4. Shape Inference Rule

- **Input**: $[B, T, D]$ with constraint $D \pmod H = 0$.
- **Output**: $[B, T, D]$ (identical rank and dimensions preserved).

## 5. Decomposition into Primitive Transformations

The high-level `TtMultiHeadAttention` composite operation decomposes into a directed sequence of primitive kernel operations:

1. **Query/Key/Value Projections**:
   - $Q = \text{Affine}(X, W^Q) \in \mathbb{R}^{B \times T \times D}$
   - $K = \text{Affine}(X, W^K) \in \mathbb{R}^{B \times T \times D}$
   - $V = \text{Affine}(X, W^V) \in \mathbb{R}^{B \times T \times D}$
2. **Head Splitting & Permutation**:
   - $Q' = \text{TensorPermute}(\text{TensorReshape}(Q, [B, T, H, d_k]), [0, 2, 1, 3]) \in \mathbb{R}^{B \times H \times T \times d_k}$
   - $K' = \text{TensorPermute}(\text{TensorReshape}(K, [B, T, H, d_k]), [0, 2, 1, 3]) \in \mathbb{R}^{B \times H \times T \times d_k}$
   - $V' = \text{TensorPermute}(\text{TensorReshape}(V, [B, T, H, d_k]), [0, 2, 1, 3]) \in \mathbb{R}^{B \times H \times T \times d_k}$
3. **Scaled Dot-Product Attention**:
   - $A = \text{ScaledDotProductAttention}(Q', K', V', \text{isCausal}) \in \mathbb{R}^{B \times H \times T \times d_k}$
4. **Head Merging & Output Projection**:
   - $O = \text{TensorReshape}(\text{TensorPermute}(A, [0, 2, 1, 3]), [B, T, D]) \in \mathbb{R}^{B \times T \times D}$
   - $Y = \text{Affine}(O, W^O) \in \mathbb{R}^{B \times T \times D}$
