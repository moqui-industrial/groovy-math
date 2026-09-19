# Positional Encoding (`TtPositionalEncoding`)

- **Status**: Derived Operation (TransformationType: `TtPositionalEncoding`, parent: `TtUnaryNonLin`)
- **Bibliographic Reference**: Vaswani et al., *"Attention Is All You Need"*, NeurIPS 2017.

## 1. Mathematical Definition

Adds sequence position information to input token representations $X \in \mathbb{R}^{B \times T \times D}$:

$$Y = X + P$$

where $P \in \mathbb{R}^{T \times D}$ is either learned via embedding or computed via sinusoidal functions:

$$P_{(pos, 2i)} = \sin\left(\frac{pos}{10000^{2i/D}}\right), \quad P_{(pos, 2i+1)} = \cos\left(\frac{pos}{10000^{2i/D}}\right)$$

## 2. Operands and Roles

| Operand | Role | Type | Expected Rank/Shape |
|---|---|---|---|
| `x` | Input Sequence Tensor | `Tensor` | $[B, T, D]$ or $[*, T, D]$ |

## 3. Parameter Definitions and Defaults

| Parameter | Type | Default | Description |
|---|---|---|---|
| `maxLen` | `Integer` | 5000 | Maximum context length $T_{max}$. |
| `embedDim` | `Integer` | inferred ($D$) | Embedding dimension $D$. |
| `encodingType` | `String` | 'Sinusoidal' | 'Sinusoidal' or 'Learned'. |

## 4. Shape Inference Rule

- **Input**: $[B, T, D]$.
- **Output**: $[B, T, D]$ (identical rank and dimensions preserved).

## 5. Decomposition into Primitive Transformations

- `TtTensorAdd` between the input tensor $X$ and the slice $P_{[0:T, :]}$ of the position embedding table.
