/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

#include <jni.h>

#include <ATen/ATen.h>
#include <ATen/Parallel.h>
#include <c10/core/InferenceMode.h>

#include <algorithm>
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
    int input_slot;
    int output_slot;
    int input_width;
    int output_width;
    at::Tensor weight;
    at::Tensor bias;
};

struct Relu {
    int input_slot;
    int output_slot;
};

struct MatrixProduct {
    int input_slot;
    int output_slot;
    int input_width;
    int output_width;
    at::Tensor right;
};

using Operation = std::variant<Affine, Relu, MatrixProduct>;

struct Plan {
    explicit Plan(int width) : input_width(width) {
        if (width <= 0) throw std::invalid_argument("input width must be positive");
    }

    int input_width;
    int output_slot = -1;
    int output_width = -1;
    bool sealed = false;
    std::vector<Operation> operations;
};

std::mutex thread_configuration_mutex;
int configured_interop_threads = 0;

Plan& plan(jlong handle) {
    if (handle == 0) throw std::invalid_argument("native plan handle is zero");
    return *reinterpret_cast<Plan*>(handle);
}

void throw_java(JNIEnv* env, const std::exception& error) {
    jclass type = env->FindClass("java/lang/IllegalStateException");
    if (type != nullptr) env->ThrowNew(type, error.what());
}

std::vector<float> floats(JNIEnv* env, jfloatArray array) {
    if (array == nullptr) throw std::invalid_argument("float array must not be null");
    jsize size = env->GetArrayLength(array);
    std::vector<float> result(static_cast<std::size_t>(size));
    env->GetFloatArrayRegion(array, 0, size, result.data());
    if (env->ExceptionCheck()) throw std::runtime_error("could not read Java float array");
    return result;
}

at::Tensor run(const Plan& execution_plan, float* input, int batch_size) {
    if (!execution_plan.sealed) throw std::logic_error("native plan is not sealed");
    if (batch_size <= 0) throw std::invalid_argument("batch size must be positive");

    c10::InferenceMode inference_guard;
    std::unordered_map<int, at::Tensor> slots;
    slots.emplace(0, at::from_blob(input, {batch_size, execution_plan.input_width}, at::kFloat));
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
            } else {
                if (source->second.size(-1) != typed.input_width) {
                    throw std::invalid_argument("matrix product input width does not match the left matrix");
                }
                slots[typed.output_slot] = at::matmul(source->second, typed.right);
            }
        }, operation);
    }
    auto output = slots.find(execution_plan.output_slot);
    if (output == slots.end()) throw std::logic_error("plan output slot is unavailable");
    return output->second.contiguous();
}

}

extern "C" {

JNIEXPORT jlong JNICALL
Java_org_moqui_math_libtorch_LibTorchBindings_nativeCreatePlan(JNIEnv* env, jclass, jint input_width) {
    try {
        return reinterpret_cast<jlong>(new Plan(input_width));
    } catch (const std::exception& error) {
        throw_java(env, error);
        return 0;
    }
}

JNIEXPORT void JNICALL
Java_org_moqui_math_libtorch_LibTorchBindings_nativeAddAffine(
        JNIEnv* env, jclass, jlong handle, jint input_slot, jint output_slot,
        jint input_width, jint output_width, jfloatArray weight_array, jfloatArray bias_array) {
    try {
        Plan& target = plan(handle);
        if (target.sealed) throw std::logic_error("cannot modify a sealed native plan");
        std::vector<float> weight = floats(env, weight_array);
        std::vector<float> bias = floats(env, bias_array);
        if (weight.size() != static_cast<std::size_t>(input_width * output_width)) {
            throw std::invalid_argument("affine weight element count is invalid");
        }
        if (bias.size() != static_cast<std::size_t>(output_width)) {
            throw std::invalid_argument("affine bias element count is invalid");
        }
        target.operations.emplace_back(Affine{
            input_slot, output_slot, input_width, output_width,
            at::from_blob(weight.data(), {output_width, input_width}, at::kFloat).clone(),
            at::from_blob(bias.data(), {output_width}, at::kFloat).clone()
        });
    } catch (const std::exception& error) {
        throw_java(env, error);
    }
}

JNIEXPORT void JNICALL
Java_org_moqui_math_libtorch_LibTorchBindings_nativeAddRelu(
        JNIEnv* env, jclass, jlong handle, jint input_slot, jint output_slot) {
    try {
        Plan& target = plan(handle);
        if (target.sealed) throw std::logic_error("cannot modify a sealed native plan");
        target.operations.emplace_back(Relu{input_slot, output_slot});
    } catch (const std::exception& error) {
        throw_java(env, error);
    }
}

JNIEXPORT void JNICALL
Java_org_moqui_math_libtorch_LibTorchBindings_nativeAddMatrixProduct(
        JNIEnv* env, jclass, jlong handle, jint input_slot, jint output_slot,
        jint input_width, jint output_width, jfloatArray right_array) {
    try {
        Plan& target = plan(handle);
        if (target.sealed) throw std::logic_error("cannot modify a sealed native plan");
        std::vector<float> right = floats(env, right_array);
        if (right.size() != static_cast<std::size_t>(input_width * output_width)) {
            throw std::invalid_argument("right matrix element count is invalid");
        }
        target.operations.emplace_back(MatrixProduct{
            input_slot, output_slot, input_width, output_width,
            at::from_blob(right.data(), {input_width, output_width}, at::kFloat).clone()
        });
    } catch (const std::exception& error) {
        throw_java(env, error);
    }
}

JNIEXPORT void JNICALL
Java_org_moqui_math_libtorch_LibTorchBindings_nativeSeal(
        JNIEnv* env, jclass, jlong handle, jint output_slot, jint output_width) {
    try {
        Plan& target = plan(handle);
        if (target.operations.empty()) throw std::logic_error("cannot seal an empty native plan");
        if (output_width <= 0) throw std::invalid_argument("output width must be positive");
        target.output_slot = output_slot;
        target.output_width = output_width;
        target.sealed = true;
    } catch (const std::exception& error) {
        throw_java(env, error);
    }
}

JNIEXPORT jfloatArray JNICALL
Java_org_moqui_math_libtorch_LibTorchBindings_nativeExecute(
        JNIEnv* env, jclass, jlong handle, jfloatArray input_array, jint batch_size) {
    try {
        Plan& target = plan(handle);
        std::vector<float> input = floats(env, input_array);
        if (input.size() != static_cast<std::size_t>(batch_size * target.input_width)) {
            throw std::invalid_argument("input element count does not match batch and width");
        }
        at::Tensor output = run(target, input.data(), batch_size);
        jsize count = static_cast<jsize>(output.numel());
        jfloatArray result = env->NewFloatArray(count);
        if (result == nullptr) throw std::runtime_error("could not allocate Java result array");
        env->SetFloatArrayRegion(result, 0, count, output.data_ptr<float>());
        return result;
    } catch (const std::exception& error) {
        throw_java(env, error);
        return nullptr;
    }
}

JNIEXPORT void JNICALL
Java_org_moqui_math_libtorch_LibTorchBindings_nativeExecuteDirect(
        JNIEnv* env, jclass, jlong handle, jobject input_buffer, jint batch_size, jobject output_buffer) {
    try {
        Plan& target = plan(handle);
        auto* input = static_cast<float*>(env->GetDirectBufferAddress(input_buffer));
        auto* output = static_cast<float*>(env->GetDirectBufferAddress(output_buffer));
        jlong input_capacity = env->GetDirectBufferCapacity(input_buffer);
        jlong output_capacity = env->GetDirectBufferCapacity(output_buffer);
        if (input == nullptr || output == nullptr) throw std::invalid_argument("buffers must be direct");
        const jlong input_bytes = static_cast<jlong>(batch_size) * target.input_width * sizeof(float);
        const jlong output_bytes = static_cast<jlong>(batch_size) * target.output_width * sizeof(float);
        if (input_capacity < input_bytes || output_capacity < output_bytes) {
            throw std::invalid_argument("direct buffer capacity is too small");
        }
        at::Tensor result = run(target, input, batch_size);
        std::memcpy(output, result.data_ptr<float>(), static_cast<std::size_t>(output_bytes));
    } catch (const std::exception& error) {
        throw_java(env, error);
    }
}

JNIEXPORT void JNICALL
Java_org_moqui_math_libtorch_LibTorchBindings_nativeDestroy(JNIEnv*, jclass, jlong handle) {
    delete reinterpret_cast<Plan*>(handle);
}

JNIEXPORT void JNICALL
Java_org_moqui_math_libtorch_LibTorchBindings_nativeConfigureThreads(
        JNIEnv* env, jclass, jint intra_op_threads, jint inter_op_threads) {
    try {
        if (intra_op_threads <= 0 || inter_op_threads <= 0) {
            throw std::invalid_argument("thread counts must be positive");
        }
        std::lock_guard<std::mutex> guard(thread_configuration_mutex);
        at::set_num_threads(intra_op_threads);
        if (configured_interop_threads == 0) {
            at::set_num_interop_threads(inter_op_threads);
            configured_interop_threads = inter_op_threads;
        } else if (configured_interop_threads != inter_op_threads) {
            throw std::logic_error("inter-op threads can only be configured once per process");
        }
    } catch (const std::exception& error) {
        throw_java(env, error);
    }
}

JNIEXPORT jint JNICALL
Java_org_moqui_math_libtorch_LibTorchBindings_nativeIntraOpThreads(JNIEnv*, jclass) {
    return at::get_num_threads();
}

JNIEXPORT jint JNICALL
Java_org_moqui_math_libtorch_LibTorchBindings_nativeInterOpThreads(JNIEnv*, jclass) {
    return at::get_num_interop_threads();
}

}
