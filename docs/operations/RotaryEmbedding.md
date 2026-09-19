# Rotary Position Embedding (`TtRotaryEmbedding` / RoPE)

- **Status**: Derived Operation (TransformationType: `TtRotaryEmbedding`, parent: `TtUnaryNonLin`)
- **Bibliographic Reference**: Su et al., *"RoFormer: Enhanced Transformer with Rotary Position Embedding"*, Neurocomputing 2024 (arXiv:2104.09864).

## 1. Mathematical Definition

Rotary Position Embedding incorporates relative position into query and key representations by rotating 2D sub-vectors across consecutive feature dimensions:

$$\mathbf{R}_{\Theta, m}^d \mathbf{x}_m = \begin{pmatrix} x_m^{(1)} \cos m\theta_1 - x_m^{(2)} \sin m\theta_1 \\ x_m^{(1)} \sin m\theta_1 + x_m^{(2)} \cos m\theta_1 \\ \vdots \end{pmatrix}$$

where $\theta_i = 10000^{-2(i-1)/d}$.

## 2. Operands and Roles

| Operand | Role | Type | Expected Rank/Shape |
|---|---|---|---|
| `x` | Query or Key Activation Tensor | `Tensor` | $[B, H, T, d]$ or $[B, T, H, d]$ |

## 3. Parameter Definitions and Defaults

| Parameter | Type | Default | Description |
|---|---|---|---|
| `dim` | `Integer` | inferred ($d$) | Dimension to rotate (typically head dimension $d_k$). |
| `base` | `Float` | 10000.0 | Exponential base $\theta$ for frequency computation. |
| `maxSeqLen` | `Integer` | 4096 | Precomputed cache sequence length. |

## 4. Shape Inference Rule

- **Input**: $[B, H, T, d]$.
- **Output**: $[B, H, T, d]$ (identical rank and dimensions preserved).

## 5. Decomposition into Primitive Transformations

1. Compute frequencies $\Theta$ and position grid $m$.
2. Compute $\cos(m\Theta)$ and $\sin(m\Theta)$.
3. Split $X$ into even and odd indices ($X_1, X_2$), form rotated pair $(-X_2, X_1)$.
4. Apply element-wise multiplication `TtTensorMul` with $\cos$ and $\sin$, and combine via `TtTensorAdd`.
