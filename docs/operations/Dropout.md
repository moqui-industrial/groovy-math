# Dropout Regularization (`TtDropout`)

- **Status**: Derived Operation (TransformationType: `TtDropout`, parent: `TtUnaryNonLin`)
- **Bibliographic Reference**: Srivastava et al., *"Dropout: A Simple Way to Prevent Neural Networks from Overfitting"*, JMLR 2014.

## 1. Mathematical Definition

During training mode, elements of input tensor $X$ are randomly zeroed with probability $p$ using samples from a Bernoulli distribution, and scaled by $\frac{1}{1-p}$ (Inverted Dropout):

$$Y = \begin{cases} \frac{1}{1-p} X \odot M, & \text{training mode} \\ X, & \text{inference mode} \end{cases}$$

where $M_{i} \sim \text{Bernoulli}(1-p)$.

## 2. Operands and Roles

| Operand | Role | Type | Expected Rank/Shape |
|---|---|---|---|
| `x` | Input Activation Tensor | `Tensor` | arbitrary $[*]$ |

## 3. Parameter Definitions and Defaults

| Parameter | Type | Default | Description |
|---|---|---|---|
| `dropoutProb` | `Float` | 0.5 | Dropout probability $p \in [0, 1)$. |
| `seed` | `Long` | null | Random seed for deterministic mask generation (upstream `RandomSeed`). |

## 4. Shape Inference Rule

- **Input**: $[*]$ (arbitrary dimensions).
- **Output**: $[*]$ (identical rank and dimensions preserved).

## 5. Decomposition into Primitive Transformations

- **Training**: Sample Bernoulli mask $M \in \{0, 1\}^{[*]}$ with probability $1-p$, multiply $X$ by $M$ via `TtTensorMul`, and scale by scalar factor $\frac{1}{1-p}$.
- **Inference**: Pass-through identity transformation.
