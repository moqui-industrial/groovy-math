/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

#include <ATen/ATen.h>
#include <ATen/Parallel.h>
#include <c10/core/InferenceMode.h>

#include <algorithm>
#include <cmath>
#include <cstdint>
#include <cstring>
#include <memory>
#include <mutex>
#include <stdexcept>
#include <string>
#include <unordered_map>
#include <utility>
#include <variant>
#include <vector>

namespace {

struct Affine {
    int32_t input_slot;
    int32_t output_slot;
    int32_t input_width;
    int32_t output_width;
    at::Tensor weight;
    at::Tensor bias;
};

struct Relu {
    int32_t input_slot;
    int32_t output_slot;
};

struct Sigmoid {
    int32_t input_slot;
    int32_t output_slot;
};

struct Gelu {
    int32_t input_slot;
    int32_t output_slot;
};

struct Silu {
    int32_t input_slot;
    int32_t output_slot;
};

struct Tanh {
    int32_t input_slot;
    int32_t output_slot;
};

struct LeakyRelu {
    int32_t input_slot;
    int32_t output_slot;
    float negative_slope;
};

struct Elu {
    int32_t input_slot;
    int32_t output_slot;
    float alpha;
};

struct Softmax {
    int32_t input_slot;
    int32_t output_slot;
    int64_t dim;
};

struct LogSoftmax {
    int32_t input_slot;
    int32_t output_slot;
    int64_t dim;
};

struct LayerNorm {
    int32_t input_slot;
    int32_t output_slot;
    int32_t normalized_width;
    at::Tensor weight;
    at::Tensor bias;
    float eps;
};

struct RMSNorm {
    int32_t input_slot;
    int32_t output_slot;
    int32_t normalized_width;
    at::Tensor weight;
    float eps;
};

struct MatrixProduct {
    int32_t input_slot;
    int32_t output_slot;
    int32_t input_width;
    int32_t output_width;
    at::Tensor right;
};

struct AttentionMask {
    int32_t input_slot;
    int32_t output_slot;
    at::Tensor mask_tensor;
};

struct ScaledDotProductAttention {
    int32_t query_slot;
    int32_t key_slot;
    int32_t value_slot;
    int32_t output_slot;
    float scale;
};

struct BinaryOp {
    enum class Type { ADD, SUB, MUL, DIV };
    Type type;
    int32_t input_slot_a;
    int32_t input_slot_b;
    int32_t output_slot;
};

struct UnaryMath {
    enum class Type { EXP, LOG, SQRT, POW };
    Type type;
    int32_t input_slot;
    int32_t output_slot;
    float param = 0.0f;
};

struct Reduction {
    enum class Type { SUM, MEAN, MAX, MIN };
    Type type;
    int32_t input_slot;
    int32_t output_slot;
    int64_t dim = -1;
    bool keepdim = true;
};

struct LossOp {
    enum class Type { MSE, CROSS_ENTROPY, BCE_WITH_LOGITS };
    Type type;
    int32_t pred_slot;
    int32_t target_slot;
    int32_t output_slot;
};

using Operation = std::variant<
    Affine, Relu, Sigmoid, Gelu, Silu, Tanh, LeakyRelu, Elu,
    Softmax, LogSoftmax, LayerNorm, RMSNorm,
    MatrixProduct, AttentionMask, ScaledDotProductAttention,
    BinaryOp, UnaryMath, Reduction, LossOp
>;

struct Plan {
    explicit Plan(int32_t width) : input_width(width) {
        if (width <= 0) throw std::invalid_argument("input width must be positive");
    }

    int32_t input_width;
    int32_t output_slot = -1;
    int32_t output_width = -1;
    bool sealed = false;
    bool training = false;
    int64_t step_count = 0;
    std::vector<Operation> operations;
    std::unordered_map<int32_t, at::Tensor> last_slots;
    std::unordered_map<void*, at::Tensor> adam_m;
    std::unordered_map<void*, at::Tensor> adam_v;
    std::unordered_map<void*, at::Tensor> momentum_buffers;

    std::vector<at::Tensor*> get_trainable_params() {
        std::vector<at::Tensor*> params;
        for (Operation& op : operations) {
            std::visit([&params](auto& typed) {
                using T = std::decay_t<decltype(typed)>;
                if constexpr (std::is_same_v<T, Affine>) {
                    params.push_back(&typed.weight);
                    params.push_back(&typed.bias);
                } else if constexpr (std::is_same_v<T, MatrixProduct>) {
                    params.push_back(&typed.right);
                } else if constexpr (std::is_same_v<T, LayerNorm>) {
                    params.push_back(&typed.weight);
                    params.push_back(&typed.bias);
                } else if constexpr (std::is_same_v<T, RMSNorm>) {
                    params.push_back(&typed.weight);
                }
            }, op);
        }
        return params;
    }
};

std::mutex thread_configuration_mutex;
int configured_interop_threads = 0;

Plan& plan(int64_t handle) {
    if (handle == 0) throw std::invalid_argument("native plan handle is zero");
    return *reinterpret_cast<Plan*>(handle);
}

at::Tensor run_plan(Plan& execution_plan, const float* input, int32_t batch_size) {
    if (!execution_plan.sealed) throw std::logic_error("native plan is not sealed");
    if (batch_size <= 0) throw std::invalid_argument("batch size must be positive");

    if (execution_plan.training) {
        for (at::Tensor* p : execution_plan.get_trainable_params()) {
            if (p) p->set_requires_grad(true);
        }
    }

    std::unordered_map<int32_t, at::Tensor> slots;
    slots.emplace(0, at::from_blob(const_cast<float*>(input), {batch_size, execution_plan.input_width}, at::kFloat).clone());

    for (Operation& operation : execution_plan.operations) {
        std::visit([&slots](auto& typed) {
            using T = std::decay_t<decltype(typed)>;

            if constexpr (std::is_same_v<T, Affine>) {
                auto src = slots.find(typed.input_slot);
                if (src == slots.end()) throw std::logic_error("affine input unavailable");
                slots[typed.output_slot] = at::matmul(src->second, typed.weight.transpose(0, 1)) + typed.bias;
            } else if constexpr (std::is_same_v<T, Relu>) {
                slots[typed.output_slot] = at::relu(slots.at(typed.input_slot));
            } else if constexpr (std::is_same_v<T, Sigmoid>) {
                slots[typed.output_slot] = at::sigmoid(slots.at(typed.input_slot));
            } else if constexpr (std::is_same_v<T, Gelu>) {
                slots[typed.output_slot] = at::gelu(slots.at(typed.input_slot));
            } else if constexpr (std::is_same_v<T, Silu>) {
                slots[typed.output_slot] = at::silu(slots.at(typed.input_slot));
            } else if constexpr (std::is_same_v<T, Tanh>) {
                slots[typed.output_slot] = at::tanh(slots.at(typed.input_slot));
            } else if constexpr (std::is_same_v<T, LeakyRelu>) {
                slots[typed.output_slot] = at::leaky_relu(slots.at(typed.input_slot), typed.negative_slope);
            } else if constexpr (std::is_same_v<T, Elu>) {
                slots[typed.output_slot] = at::elu(slots.at(typed.input_slot), typed.alpha);
            } else if constexpr (std::is_same_v<T, Softmax>) {
                slots[typed.output_slot] = at::softmax(slots.at(typed.input_slot), typed.dim);
            } else if constexpr (std::is_same_v<T, LogSoftmax>) {
                slots[typed.output_slot] = at::log_softmax(slots.at(typed.input_slot), typed.dim);
            } else if constexpr (std::is_same_v<T, LayerNorm>) {
                auto src = slots.at(typed.input_slot);
                slots[typed.output_slot] = at::layer_norm(src, {typed.normalized_width}, typed.weight, typed.bias, typed.eps);
            } else if constexpr (std::is_same_v<T, RMSNorm>) {
                auto src = slots.at(typed.input_slot);
                auto variance = src.pow(2).mean(-1, true);
                slots[typed.output_slot] = src * at::rsqrt(variance + typed.eps) * typed.weight;
            } else if constexpr (std::is_same_v<T, MatrixProduct>) {
                auto src = slots.at(typed.input_slot);
                slots[typed.output_slot] = at::matmul(src, typed.right);
            } else if constexpr (std::is_same_v<T, AttentionMask>) {
                slots[typed.output_slot] = slots.at(typed.input_slot) + typed.mask_tensor;
            } else if constexpr (std::is_same_v<T, ScaledDotProductAttention>) {
                auto q = slots.at(typed.query_slot);
                auto k = slots.at(typed.key_slot);
                auto v = slots.at(typed.value_slot);
                auto scores = at::matmul(q, k.transpose(-2, -1)) * typed.scale;
                auto probs = at::softmax(scores, -1);
                slots[typed.output_slot] = at::matmul(probs, v);
            } else if constexpr (std::is_same_v<T, BinaryOp>) {
                auto a = slots.at(typed.input_slot_a);
                auto b = slots.at(typed.input_slot_b);
                switch (typed.type) {
                    case BinaryOp::Type::ADD: slots[typed.output_slot] = a + b; break;
                    case BinaryOp::Type::SUB: slots[typed.output_slot] = a - b; break;
                    case BinaryOp::Type::MUL: slots[typed.output_slot] = a * b; break;
                    case BinaryOp::Type::DIV: slots[typed.output_slot] = a / b; break;
                }
            } else if constexpr (std::is_same_v<T, UnaryMath>) {
                auto src = slots.at(typed.input_slot);
                switch (typed.type) {
                    case UnaryMath::Type::EXP:  slots[typed.output_slot] = at::exp(src); break;
                    case UnaryMath::Type::LOG:  slots[typed.output_slot] = at::log(src); break;
                    case UnaryMath::Type::SQRT: slots[typed.output_slot] = at::sqrt(src); break;
                    case UnaryMath::Type::POW:  slots[typed.output_slot] = at::pow(src, typed.param); break;
                }
            } else if constexpr (std::is_same_v<T, Reduction>) {
                auto src = slots.at(typed.input_slot);
                switch (typed.type) {
                    case Reduction::Type::SUM:  slots[typed.output_slot] = at::sum(src, typed.dim, typed.keepdim); break;
                    case Reduction::Type::MEAN: slots[typed.output_slot] = at::mean(src, typed.dim, typed.keepdim); break;
                    case Reduction::Type::MAX:  slots[typed.output_slot] = std::get<0>(at::max(src, typed.dim, typed.keepdim)); break;
                    case Reduction::Type::MIN:  slots[typed.output_slot] = std::get<0>(at::min(src, typed.dim, typed.keepdim)); break;
                }
            } else if constexpr (std::is_same_v<T, LossOp>) {
                auto p = slots.at(typed.pred_slot);
                auto t = slots.at(typed.target_slot);
                switch (typed.type) {
                    case LossOp::Type::MSE: slots[typed.output_slot] = at::mse_loss(p, t); break;
                    case LossOp::Type::CROSS_ENTROPY: slots[typed.output_slot] = at::cross_entropy_loss(p, t); break;
                    case LossOp::Type::BCE_WITH_LOGITS: slots[typed.output_slot] = at::binary_cross_entropy_with_logits(p, t); break;
                }
            }
        }, operation);
    }

    auto out_it = slots.find(execution_plan.output_slot);
    if (out_it == slots.end()) throw std::logic_error("plan output slot is unavailable");

    if (execution_plan.training) {
        execution_plan.last_slots = std::move(slots);
        return execution_plan.last_slots[execution_plan.output_slot].contiguous();
    }
    return out_it->second.contiguous();
}

} // namespace

extern "C" {

int64_t torch_panama_create_plan(int32_t input_width) {
    try {
        return reinterpret_cast<int64_t>(new Plan(input_width));
    } catch (...) {
        return 0;
    }
}

void torch_panama_destroy(int64_t handle) {
    delete reinterpret_cast<Plan*>(handle);
}

int32_t torch_panama_output_width(int64_t handle) {
    if (handle == 0) return 0;
    return plan(handle).output_width;
}

void torch_panama_seal(int64_t handle, int32_t output_slot, int32_t output_width) {
    Plan& target = plan(handle);
    if (target.operations.empty()) throw std::logic_error("cannot seal an empty native plan");
    if (output_width <= 0) throw std::invalid_argument("output width must be positive");
    target.output_slot = output_slot;
    target.output_width = output_width;
    target.sealed = true;
}

void torch_panama_set_training(int64_t handle, int32_t is_training) {
    Plan& target = plan(handle);
    target.training = (is_training != 0);
    for (at::Tensor* p : target.get_trainable_params()) {
        if (p) p->set_requires_grad(target.training);
    }
}

void torch_panama_add_affine(int64_t handle, int32_t input_slot, int32_t output_slot,
                             int32_t input_width, int32_t output_width,
                             const float* weight, const float* bias) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify a sealed native plan");
    Affine op{
        input_slot, output_slot, input_width, output_width,
        at::from_blob(const_cast<float*>(weight), {output_width, input_width}, at::kFloat).clone(),
        at::from_blob(const_cast<float*>(bias), {output_width}, at::kFloat).clone()
    };
    target.operations.emplace_back(std::move(op));
}

void torch_panama_add_relu(int64_t handle, int32_t input_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    target.operations.emplace_back(Relu{input_slot, output_slot});
}

void torch_panama_add_sigmoid(int64_t handle, int32_t input_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    target.operations.emplace_back(Sigmoid{input_slot, output_slot});
}

void torch_panama_add_gelu(int64_t handle, int32_t input_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    target.operations.emplace_back(Gelu{input_slot, output_slot});
}

void torch_panama_add_silu(int64_t handle, int32_t input_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    target.operations.emplace_back(Silu{input_slot, output_slot});
}

void torch_panama_add_tanh(int64_t handle, int32_t input_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    target.operations.emplace_back(Tanh{input_slot, output_slot});
}

void torch_panama_add_leaky_relu(int64_t handle, int32_t input_slot, int32_t output_slot, float negative_slope) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    target.operations.emplace_back(LeakyRelu{input_slot, output_slot, negative_slope});
}

void torch_panama_add_elu(int64_t handle, int32_t input_slot, int32_t output_slot, float alpha) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    target.operations.emplace_back(Elu{input_slot, output_slot, alpha});
}

void torch_panama_add_softmax(int64_t handle, int32_t input_slot, int32_t output_slot, int64_t dim) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    target.operations.emplace_back(Softmax{input_slot, output_slot, dim});
}

void torch_panama_add_log_softmax(int64_t handle, int32_t input_slot, int32_t output_slot, int64_t dim) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    target.operations.emplace_back(LogSoftmax{input_slot, output_slot, dim});
}

void torch_panama_add_layer_norm(int64_t handle, int32_t input_slot, int32_t output_slot,
                                 int32_t normalized_width, const float* weight, const float* bias, float eps) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    at::Tensor w = weight ? at::from_blob(const_cast<float*>(weight), {normalized_width}, at::kFloat).clone() : at::ones({normalized_width}, at::kFloat);
    at::Tensor b = bias ? at::from_blob(const_cast<float*>(bias), {normalized_width}, at::kFloat).clone() : at::zeros({normalized_width}, at::kFloat);
    LayerNorm op{input_slot, output_slot, normalized_width, w, b, eps};
    target.operations.emplace_back(std::move(op));
}

void torch_panama_add_rms_norm(int64_t handle, int32_t input_slot, int32_t output_slot,
                               int32_t normalized_width, const float* weight, float eps) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    at::Tensor w = weight ? at::from_blob(const_cast<float*>(weight), {normalized_width}, at::kFloat).clone() : at::ones({normalized_width}, at::kFloat);
    RMSNorm op{input_slot, output_slot, normalized_width, w, eps};
    target.operations.emplace_back(std::move(op));
}

void torch_panama_add_matrix_product(int64_t handle, int32_t input_slot, int32_t output_slot,
                                     int32_t input_width, int32_t output_width,
                                     const float* right) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    MatrixProduct op{
        input_slot, output_slot, input_width, output_width,
        at::from_blob(const_cast<float*>(right), {input_width, output_width}, at::kFloat).clone()
    };
    target.operations.emplace_back(std::move(op));
}

void torch_panama_add_attention_mask(int64_t handle, int32_t input_slot, int32_t output_slot,
                                     int64_t rows, int64_t cols, const float* mask_data) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    target.operations.emplace_back(AttentionMask{
        input_slot, output_slot,
        at::from_blob(const_cast<float*>(mask_data), {rows, cols}, at::kFloat).clone()
    });
}

void torch_panama_add_scaled_dot_product_attention(int64_t handle, int32_t query_slot, int32_t key_slot,
                                                   int32_t value_slot, int32_t output_slot, float scale) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    target.operations.emplace_back(ScaledDotProductAttention{query_slot, key_slot, value_slot, output_slot, scale});
}

void torch_panama_add_binary_op(int64_t handle, int32_t op_type, int32_t input_slot_a, int32_t input_slot_b, int32_t output_slot) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    BinaryOp::Type t = static_cast<BinaryOp::Type>(op_type);
    target.operations.emplace_back(BinaryOp{t, input_slot_a, input_slot_b, output_slot});
}

void torch_panama_add_unary_math(int64_t handle, int32_t op_type, int32_t input_slot, int32_t output_slot, float param) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    UnaryMath::Type t = static_cast<UnaryMath::Type>(op_type);
    target.operations.emplace_back(UnaryMath{t, input_slot, output_slot, param});
}

void torch_panama_add_reduction(int64_t handle, int32_t red_type, int32_t input_slot, int32_t output_slot, int64_t dim, int32_t keepdim) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    Reduction::Type t = static_cast<Reduction::Type>(red_type);
    target.operations.emplace_back(Reduction{t, input_slot, output_slot, dim, keepdim != 0});
}

void torch_panama_add_loss(int64_t handle, int32_t loss_type, int32_t pred_slot, int32_t target_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify sealed plan");
    LossOp::Type t = static_cast<LossOp::Type>(loss_type);
    target.operations.emplace_back(LossOp{t, pred_slot, target_slot, output_slot});
}

void torch_panama_execute(int64_t handle, const float* input, int32_t batch_size, float* output) {
    Plan& target = plan(handle);
    at::Tensor result;
    if (target.training) {
        result = run_plan(target, input, batch_size);
    } else {
        c10::InferenceMode guard;
        result = run_plan(target, input, batch_size);
    }
    const size_t output_bytes = static_cast<size_t>(batch_size) * target.output_width * sizeof(float);
    std::memcpy(output, result.data_ptr<float>(), output_bytes);
}

void torch_panama_backward(int64_t handle, int32_t loss_slot) {
    Plan& target = plan(handle);
    auto it = target.last_slots.find(loss_slot);
    if (it == target.last_slots.end()) throw std::logic_error("loss slot not found for backward");
    if (it->second.numel() == 1) {
        it->second.backward();
    } else {
        it->second.sum().backward();
    }
}

void torch_panama_step_optimizer(int64_t handle, int32_t opt_type, float lr, float weight_decay, float momentum) {
    Plan& target = plan(handle);
    target.step_count++;

    for (at::Tensor* p : target.get_trainable_params()) {
        if (!p || !p->grad().defined()) continue;
        at::Tensor g = p->grad();

        if (opt_type == 0) { // SGD
            if (weight_decay != 0.0f) g = g + weight_decay * (*p);
            if (momentum > 0.0f) {
                void* key = static_cast<void*>(p);
                auto& buf = target.momentum_buffers[key];
                if (!buf.defined()) buf = g.clone();
                else buf = momentum * buf + g;
                p->data().add_(buf, -lr);
            } else {
                p->data().add_(g, -lr);
            }
        } else if (opt_type == 1 || opt_type == 2) { // Adam / AdamW
            void* key = static_cast<void*>(p);
            auto& m = target.adam_m[key];
            auto& v = target.adam_v[key];
            if (!m.defined()) {
                m = at::zeros_like(*p);
                v = at::zeros_like(*p);
            }
            float b1 = 0.9f, b2 = 0.999f, eps = 1e-8f;
            if (opt_type == 1 && weight_decay != 0.0f) {
                g = g + weight_decay * (*p);
            }
            m = b1 * m + (1.0f - b1) * g;
            v = b2 * v + (1.0f - b2) * g.pow(2);
            auto m_hat = m / (1.0f - std::pow(b1, target.step_count));
            auto v_hat = v / (1.0f - std::pow(b2, target.step_count));

            if (opt_type == 2 && weight_decay != 0.0f) { // AdamW decoupled weight decay
                p->data().add_(*p, -lr * weight_decay);
            }
            p->data().addcdiv_(m_hat, at::sqrt(v_hat) + eps, -lr);
        }
    }
}

void torch_panama_zero_grad(int64_t handle) {
    Plan& target = plan(handle);
    for (at::Tensor* p : target.get_trainable_params()) {
        if (p && p->grad().defined()) {
            p->grad().zero_();
        }
    }
}

void torch_panama_matmul(const float* a, int64_t a_rows, int64_t a_cols,
                         const float* b, int64_t b_rows, int64_t b_cols,
                         float* out) {
    c10::InferenceMode guard;
    at::Tensor tensor_a = at::from_blob(const_cast<float*>(a), {a_rows, a_cols}, at::kFloat);
    at::Tensor tensor_b = at::from_blob(const_cast<float*>(b), {b_rows, b_cols}, at::kFloat);
    at::Tensor result = at::matmul(tensor_a, tensor_b);
    std::memcpy(out, result.data_ptr<float>(), static_cast<size_t>(a_rows * b_cols) * sizeof(float));
}

void torch_panama_tensor_op(int32_t op_id, const float* a, int64_t size, float* out, float param) {
    c10::InferenceMode guard;
    at::Tensor tensor_a = at::from_blob(const_cast<float*>(a), {size}, at::kFloat);
    at::Tensor res;
    switch (op_id) {
        case 1: res = at::gelu(tensor_a); break;
        case 2: res = at::silu(tensor_a); break;
        case 3: res = at::tanh(tensor_a); break;
        case 4: res = at::sigmoid(tensor_a); break;
        case 5: res = at::leaky_relu(tensor_a, param); break;
        case 6: res = at::elu(tensor_a, param); break;
        case 7: res = at::exp(tensor_a); break;
        case 8: res = at::log(tensor_a); break;
        case 9: res = at::sqrt(tensor_a); break;
        default: res = at::relu(tensor_a); break;
    }
    std::memcpy(out, res.data_ptr<float>(), static_cast<size_t>(size) * sizeof(float));
}

void torch_panama_configure_threads(int32_t intra_op_threads, int32_t inter_op_threads) {
    std::lock_guard<std::mutex> guard(thread_configuration_mutex);
    if (intra_op_threads > 0) at::set_num_threads(intra_op_threads);
    if (inter_op_threads > 0) {
        if (configured_interop_threads == 0) {
            at::set_num_interop_threads(inter_op_threads);
            configured_interop_threads = inter_op_threads;
        }
    }
}

int32_t torch_panama_intra_op_threads() {
    return at::get_num_threads();
}

int32_t torch_panama_inter_op_threads() {
    return at::get_num_interop_threads();
}

} // extern "C"
