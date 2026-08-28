/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

#include <ATen/ATen.h>
#include <ATen/Parallel.h>
#include <c10/core/InferenceMode.h>

#include <algorithm>
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

struct Softmax {
    int32_t input_slot;
    int32_t output_slot;
    int64_t dim;
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

using Operation = std::variant<Affine, Relu, Softmax, MatrixProduct, AttentionMask>;

struct Plan {
    explicit Plan(int32_t width) : input_width(width) {
        if (width <= 0) throw std::invalid_argument("input width must be positive");
    }

    int32_t input_width;
    int32_t output_slot = -1;
    int32_t output_width = -1;
    bool sealed = false;
    std::vector<Operation> operations;
};

std::mutex thread_configuration_mutex;
int configured_interop_threads = 0;

Plan& plan(int64_t handle) {
    if (handle == 0) throw std::invalid_argument("native plan handle is zero");
    return *reinterpret_cast<Plan*>(handle);
}

at::Tensor run(const Plan& execution_plan, const float* input, int32_t batch_size) {
    if (!execution_plan.sealed) throw std::logic_error("native plan is not sealed");
    if (batch_size <= 0) throw std::invalid_argument("batch size must be positive");

    c10::InferenceMode inference_guard;
    std::unordered_map<int32_t, at::Tensor> slots;
    slots.emplace(0, at::from_blob(const_cast<float*>(input), {batch_size, execution_plan.input_width}, at::kFloat));

    for (const Operation& operation : execution_plan.operations) {
        std::visit([&slots](const auto& typed) {
            using T = std::decay_t<decltype(typed)>;
            auto source = slots.find(typed.input_slot);
            if (source == slots.end()) throw std::logic_error("operation input slot is unavailable");

            if constexpr (std::is_same_v<T, Affine>) {
                if (source->second.size(-1) != typed.input_width) {
                    throw std::invalid_argument("affine input width does not match the tensor");
                }
                slots[typed.output_slot] = at::matmul(source->second, typed.weight.transpose(0, 1)) + typed.bias;
            } else if constexpr (std::is_same_v<T, Relu>) {
                slots[typed.output_slot] = at::relu(source->second);
            } else if constexpr (std::is_same_v<T, Softmax>) {
                slots[typed.output_slot] = at::softmax(source->second, typed.dim);
            } else if constexpr (std::is_same_v<T, MatrixProduct>) {
                if (source->second.size(-1) != typed.input_width) {
                    throw std::invalid_argument("matrix product input width does not match the left matrix");
                }
                slots[typed.output_slot] = at::matmul(source->second, typed.right);
            } else if constexpr (std::is_same_v<T, AttentionMask>) {
                slots[typed.output_slot] = source->second + typed.mask_tensor;
            }
        }, operation);
    }
    auto output = slots.find(execution_plan.output_slot);
    if (output == slots.end()) throw std::logic_error("plan output slot is unavailable");
    return output->second.contiguous();
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

void torch_panama_add_affine(int64_t handle, int32_t input_slot, int32_t output_slot,
                             int32_t input_width, int32_t output_width,
                             const float* weight, const float* bias) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify a sealed native plan");
    target.operations.emplace_back(Affine{
        input_slot, output_slot, input_width, output_width,
        at::from_blob(const_cast<float*>(weight), {output_width, input_width}, at::kFloat).clone(),
        at::from_blob(const_cast<float*>(bias), {output_width}, at::kFloat).clone()
    });
}

void torch_panama_add_relu(int64_t handle, int32_t input_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify a sealed native plan");
    target.operations.emplace_back(Relu{input_slot, output_slot});
}

void torch_panama_add_softmax(int64_t handle, int32_t input_slot, int32_t output_slot, int64_t dim) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify a sealed native plan");
    target.operations.emplace_back(Softmax{input_slot, output_slot, dim});
}

void torch_panama_add_matrix_product(int64_t handle, int32_t input_slot, int32_t output_slot,
                                     int32_t input_width, int32_t output_width,
                                     const float* right) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify a sealed native plan");
    target.operations.emplace_back(MatrixProduct{
        input_slot, output_slot, input_width, output_width,
        at::from_blob(const_cast<float*>(right), {input_width, output_width}, at::kFloat).clone()
    });
}

void torch_panama_add_attention_mask(int64_t handle, int32_t input_slot, int32_t output_slot,
                                     int64_t rows, int64_t cols, const float* mask_data) {
    Plan& target = plan(handle);
    if (target.sealed) throw std::logic_error("cannot modify a sealed native plan");
    target.operations.emplace_back(AttentionMask{
        input_slot, output_slot,
        at::from_blob(const_cast<float*>(mask_data), {rows, cols}, at::kFloat).clone()
    });
}

void torch_panama_execute(int64_t handle, const float* input, int32_t batch_size, float* output) {
    Plan& target = plan(handle);
    at::Tensor result = run(target, input, batch_size);
    const size_t output_bytes = static_cast<size_t>(batch_size) * target.output_width * sizeof(float);
    std::memcpy(output, result.data_ptr<float>(), output_bytes);
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
