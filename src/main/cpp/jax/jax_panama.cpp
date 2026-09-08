/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

#define NPY_NO_DEPRECATED_API NPY_1_7_API_VERSION

#include <Python.h>
#include <numpy/arrayobject.h>
#include <dlfcn.h>

#include <algorithm>
#include <cmath>
#include <cstdint>
#include <cstdlib>
#include <memory>
#include <mutex>
#include <stdexcept>
#include <string>
#include <unordered_map>
#include <vector>

namespace {

enum class OpType {
    AFFINE,
    MATRIX_PRODUCT,
    RELU,
    SIGMOID,
    GELU,
    SILU,
    TANH,
    LEAKY_RELU,
    ELU,
    SOFTMAX,
    LOG_SOFTMAX,
    LAYER_NORM,
    RMS_NORM,
    ATTENTION_MASK,
    SCALED_DOT_PRODUCT_ATTENTION,
    BINARY_ADD,
    BINARY_SUB,
    BINARY_MUL,
    BINARY_DIV,
    UNARY_EXP,
    UNARY_LOG,
    UNARY_SQRT,
    UNARY_POW,
    REDUCTION_SUM,
    REDUCTION_MEAN,
    REDUCTION_MAX,
    REDUCTION_MIN,
    LOSS_MSE,
    LOSS_CROSS_ENTROPY
};

struct Operation {
    OpType type;
    int32_t input_slot;
    int32_t input_slot_b = -1;
    int32_t input_slot_c = -1;
    int32_t output_slot;
    int32_t input_width = 0;
    int32_t output_width = 0;
    std::vector<float> weights;
    std::vector<float> bias;
    int64_t dim = -1;
    int64_t mask_rows = 0;
    int64_t mask_cols = 0;
    float param = 0.0f;
    float eps = 1e-5f;
    bool keepdim = true;
};

struct Plan {
    int32_t input_width;
    int32_t output_slot = -1;
    int32_t output_width = -1;
    bool sealed = false;
    bool training = false;
    std::vector<Operation> operations;

    explicit Plan(int32_t width) : input_width(width) {
        if (width <= 0) throw std::invalid_argument("input width must be positive");
    }
};

#ifndef GROOVY_MATH_PYTHON_LIBRARY
#define GROOVY_MATH_PYTHON_LIBRARY "libpython3.so"
#endif

const char* resolve_python_library_path() {
    const char* override_path = std::getenv("GROOVY_MATH_PYTHON_LIBRARY");
    return (override_path && *override_path) ? override_path : GROOVY_MATH_PYTHON_LIBRARY;
}

std::once_flag jax_init_flag;
PyObject* np_module = nullptr;
PyObject* np_asarray_func = nullptr;
PyObject* jnp_module = nullptr;
PyObject* jax_nn_module = nullptr;

PyObject* matmul_func = nullptr;
PyObject* add_func = nullptr;
PyObject* sub_func = nullptr;
PyObject* mul_func = nullptr;
PyObject* div_func = nullptr;
PyObject* pow_func = nullptr;
PyObject* exp_func = nullptr;
PyObject* log_func = nullptr;
PyObject* sqrt_func = nullptr;
PyObject* maximum_func = nullptr;
PyObject* minimum_func = nullptr;
PyObject* sum_func = nullptr;
PyObject* mean_func = nullptr;
PyObject* tanh_func = nullptr;

PyObject* relu_func = nullptr;
PyObject* sigmoid_func = nullptr;
PyObject* gelu_func = nullptr;
PyObject* silu_func = nullptr;
PyObject* softmax_func = nullptr;
PyObject* log_softmax_func = nullptr;
PyObject* leaky_relu_func = nullptr;
PyObject* elu_func = nullptr;

std::mutex jax_execution_mutex;

void ensure_jax() {
    std::call_once(jax_init_flag, [] {
        dlopen(resolve_python_library_path(), RTLD_NOW | RTLD_GLOBAL);

        if (!Py_IsInitialized()) {
            Py_Initialize();
        }
        np_module = PyImport_ImportModule("numpy");
        if (!np_module) {
            PyErr_Print();
            throw std::runtime_error("Failed to import numpy");
        }
        np_asarray_func = PyObject_GetAttrString(np_module, "asarray");

        if (_import_array() < 0) {
            PyErr_Print();
            throw std::runtime_error("Failed to initialize NumPy C-API");
        }

        jnp_module = PyImport_ImportModule("jax.numpy");
        if (!jnp_module) {
            PyErr_Print();
            throw std::runtime_error("Failed to import jax.numpy");
        }
        jax_nn_module = PyImport_ImportModule("jax.nn");
        if (!jax_nn_module) {
            PyErr_Print();
            throw std::runtime_error("Failed to import jax.nn");
        }

        matmul_func = PyObject_GetAttrString(jnp_module, "matmul");
        add_func = PyObject_GetAttrString(jnp_module, "add");
        sub_func = PyObject_GetAttrString(jnp_module, "subtract");
        mul_func = PyObject_GetAttrString(jnp_module, "multiply");
        div_func = PyObject_GetAttrString(jnp_module, "divide");
        pow_func = PyObject_GetAttrString(jnp_module, "power");
        exp_func = PyObject_GetAttrString(jnp_module, "exp");
        log_func = PyObject_GetAttrString(jnp_module, "log");
        sqrt_func = PyObject_GetAttrString(jnp_module, "sqrt");
        maximum_func = PyObject_GetAttrString(jnp_module, "maximum");
        minimum_func = PyObject_GetAttrString(jnp_module, "minimum");
        sum_func = PyObject_GetAttrString(jnp_module, "sum");
        mean_func = PyObject_GetAttrString(jnp_module, "mean");
        tanh_func = PyObject_GetAttrString(jnp_module, "tanh");

        relu_func = PyObject_GetAttrString(jax_nn_module, "relu");
        sigmoid_func = PyObject_GetAttrString(jax_nn_module, "sigmoid");
        gelu_func = PyObject_GetAttrString(jax_nn_module, "gelu");
        silu_func = PyObject_GetAttrString(jax_nn_module, "silu");
        softmax_func = PyObject_GetAttrString(jax_nn_module, "softmax");
        log_softmax_func = PyObject_GetAttrString(jax_nn_module, "log_softmax");
        leaky_relu_func = PyObject_GetAttrString(jax_nn_module, "leaky_relu");
        elu_func = PyObject_GetAttrString(jax_nn_module, "elu");
    });
}

Plan& plan(int64_t handle) {
    if (handle == 0) throw std::invalid_argument("native plan handle is zero");
    return *reinterpret_cast<Plan*>(handle);
}

PyObject* wrap_2d_array(const float* data, npy_intp rows, npy_intp cols) {
    npy_intp dims[2] = {rows, cols};
    return PyArray_SimpleNewFromData(2, dims, NPY_FLOAT32, const_cast<float*>(data));
}

PyObject* wrap_1d_array(const float* data, npy_intp count) {
    npy_intp dims[1] = {count};
    return PyArray_SimpleNewFromData(1, dims, NPY_FLOAT32, const_cast<float*>(data));
}

void copy_to_output(PyObject* obj, float* output, size_t count) {
    if (!obj) return;
    PyObject* args = PyTuple_Pack(1, obj);
    PyObject* np_arr = PyObject_CallObject(np_asarray_func, args);
    Py_DECREF(args);
    if (np_arr && PyArray_Check(np_arr)) {
        PyArrayObject* arr = reinterpret_cast<PyArrayObject*>(np_arr);
        float* data = static_cast<float*>(PyArray_DATA(arr));
        std::copy(data, data + count, output);
    }
    Py_XDECREF(np_arr);
}

PyObject* call_unary(PyObject* func, PyObject* arg) {
    PyObject* args = PyTuple_Pack(1, arg);
    PyObject* res = PyObject_CallObject(func, args);
    Py_DECREF(args);
    return res;
}

PyObject* call_binary(PyObject* func, PyObject* a, PyObject* b) {
    PyObject* args = PyTuple_Pack(2, a, b);
    PyObject* res = PyObject_CallObject(func, args);
    Py_DECREF(args);
    return res;
}

PyObject* call_gelu(PyObject* arg) {
    if (!gelu_func) return nullptr;
    PyObject* kwargs = PyDict_New();
    PyDict_SetItemString(kwargs, "approximate", Py_False);
    PyObject* args = PyTuple_Pack(1, arg);
    PyObject* res = PyObject_Call(gelu_func, args, kwargs);
    Py_DECREF(args);
    Py_DECREF(kwargs);
    return res;
}

} // namespace

extern "C" {

int64_t jax_panama_create_plan(int32_t input_width) {
    try {
        ensure_jax();
        return reinterpret_cast<int64_t>(new Plan(input_width));
    } catch (...) {
        return 0;
    }
}

void jax_panama_destroy(int64_t handle) {
    if (handle != 0) {
        delete reinterpret_cast<Plan*>(handle);
    }
}

int32_t jax_panama_output_width(int64_t handle) {
    if (handle == 0) return 0;
    return plan(handle).output_width;
}

void jax_panama_seal(int64_t handle, int32_t output_slot, int32_t output_width) {
    Plan& target = plan(handle);
    if (target.operations.empty()) throw std::logic_error("cannot seal an empty plan");
    if (output_width <= 0) throw std::invalid_argument("output width must be positive");
    target.output_slot = output_slot;
    target.output_width = output_width;
    target.sealed = true;
}

void jax_panama_set_training(int64_t handle, int32_t is_training) {
    Plan& target = plan(handle);
    target.training = (is_training != 0);
}

void jax_panama_add_affine(int64_t handle, int32_t input_slot, int32_t output_slot,
                           int32_t input_width, int32_t output_width,
                           const float* weight, const float* bias) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::AFFINE;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.input_width = input_width;
    op.output_width = output_width;
    op.weights.assign(weight, weight + input_width * output_width);
    op.bias.assign(bias, bias + output_width);
    target.operations.push_back(std::move(op));
}

void jax_panama_add_matrix_product(int64_t handle, int32_t input_slot, int32_t output_slot,
                                  int32_t input_width, int32_t output_width,
                                  const float* right_matrix) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::MATRIX_PRODUCT;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.input_width = input_width;
    op.output_width = output_width;
    op.weights.assign(right_matrix, right_matrix + input_width * output_width);
    target.operations.push_back(std::move(op));
}

void jax_panama_add_relu(int64_t handle, int32_t input_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::RELU;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    target.operations.push_back(std::move(op));
}

void jax_panama_add_sigmoid(int64_t handle, int32_t input_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::SIGMOID;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    target.operations.push_back(std::move(op));
}

void jax_panama_add_gelu(int64_t handle, int32_t input_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::GELU;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    target.operations.push_back(std::move(op));
}

void jax_panama_add_silu(int64_t handle, int32_t input_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::SILU;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    target.operations.push_back(std::move(op));
}

void jax_panama_add_tanh(int64_t handle, int32_t input_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::TANH;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    target.operations.push_back(std::move(op));
}

void jax_panama_add_leaky_relu(int64_t handle, int32_t input_slot, int32_t output_slot, float negative_slope) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::LEAKY_RELU;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.param = negative_slope;
    target.operations.push_back(std::move(op));
}

void jax_panama_add_elu(int64_t handle, int32_t input_slot, int32_t output_slot, float alpha) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::ELU;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.param = alpha;
    target.operations.push_back(std::move(op));
}

void jax_panama_add_softmax(int64_t handle, int32_t input_slot, int32_t output_slot, int64_t dim) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::SOFTMAX;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.dim = dim;
    target.operations.push_back(std::move(op));
}

void jax_panama_add_log_softmax(int64_t handle, int32_t input_slot, int32_t output_slot, int64_t dim) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::LOG_SOFTMAX;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.dim = dim;
    target.operations.push_back(std::move(op));
}

void jax_panama_add_layer_norm(int64_t handle, int32_t input_slot, int32_t output_slot,
                              int32_t normalized_width, const float* weight, const float* bias, float eps) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::LAYER_NORM;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.output_width = normalized_width;
    op.eps = eps;
    if (weight) op.weights.assign(weight, weight + normalized_width);
    else op.weights.assign(normalized_width, 1.0f);
    if (bias) op.bias.assign(bias, bias + normalized_width);
    else op.bias.assign(normalized_width, 0.0f);
    target.operations.push_back(std::move(op));
}

void jax_panama_add_rms_norm(int64_t handle, int32_t input_slot, int32_t output_slot,
                            int32_t normalized_width, const float* weight, float eps) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::RMS_NORM;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.output_width = normalized_width;
    op.eps = eps;
    if (weight) op.weights.assign(weight, weight + normalized_width);
    else op.weights.assign(normalized_width, 1.0f);
    target.operations.push_back(std::move(op));
}

void jax_panama_add_attention_mask(int64_t handle, int32_t input_slot, int32_t output_slot,
                                   int64_t rows, int64_t cols, const float* mask_data) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::ATTENTION_MASK;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.mask_rows = rows;
    op.mask_cols = cols;
    op.weights.assign(mask_data, mask_data + rows * cols);
    target.operations.push_back(std::move(op));
}

void jax_panama_add_scaled_dot_product_attention(int64_t handle, int32_t query_slot, int32_t key_slot,
                                                 int32_t value_slot, int32_t output_slot, float scale) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::SCALED_DOT_PRODUCT_ATTENTION;
    op.input_slot = query_slot;
    op.input_slot_b = key_slot;
    op.input_slot_c = value_slot;
    op.output_slot = output_slot;
    op.param = scale;
    target.operations.push_back(std::move(op));
}

void jax_panama_add_binary_op(int64_t handle, int32_t op_type, int32_t input_slot_a, int32_t input_slot_b, int32_t output_slot) {
    Plan& target = plan(handle);
    Operation op;
    op.input_slot = input_slot_a;
    op.input_slot_b = input_slot_b;
    op.output_slot = output_slot;
    switch (op_type) {
        case 0: op.type = OpType::BINARY_ADD; break;
        case 1: op.type = OpType::BINARY_SUB; break;
        case 2: op.type = OpType::BINARY_MUL; break;
        case 3: op.type = OpType::BINARY_DIV; break;
    }
    target.operations.push_back(std::move(op));
}

void jax_panama_add_unary_math(int64_t handle, int32_t op_type, int32_t input_slot, int32_t output_slot, float param) {
    Plan& target = plan(handle);
    Operation op;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.param = param;
    switch (op_type) {
        case 0: op.type = OpType::UNARY_EXP; break;
        case 1: op.type = OpType::UNARY_LOG; break;
        case 2: op.type = OpType::UNARY_SQRT; break;
        case 3: op.type = OpType::UNARY_POW; break;
    }
    target.operations.push_back(std::move(op));
}

void jax_panama_add_reduction(int64_t handle, int32_t red_type, int32_t input_slot, int32_t output_slot, int64_t dim, int32_t keepdim) {
    Plan& target = plan(handle);
    Operation op;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.dim = dim;
    op.keepdim = (keepdim != 0);
    switch (red_type) {
        case 0: op.type = OpType::REDUCTION_SUM; break;
        case 1: op.type = OpType::REDUCTION_MEAN; break;
        case 2: op.type = OpType::REDUCTION_MAX; break;
        case 3: op.type = OpType::REDUCTION_MIN; break;
    }
    target.operations.push_back(std::move(op));
}

void jax_panama_add_loss(int64_t handle, int32_t loss_type, int32_t pred_slot, int32_t target_slot, int32_t output_slot) {
    Plan& target = plan(handle);
    Operation op;
    op.input_slot = pred_slot;
    op.input_slot_b = target_slot;
    op.output_slot = output_slot;
    switch (loss_type) {
        case 0: op.type = OpType::LOSS_MSE; break;
        case 1: op.type = OpType::LOSS_CROSS_ENTROPY; break;
    }
    target.operations.push_back(std::move(op));
}

void jax_panama_execute(int64_t handle, const float* input, int32_t batch_size, float* output) {
    ensure_jax();
    std::lock_guard<std::mutex> lock(jax_execution_mutex);
    PyGILState_STATE gstate = PyGILState_Ensure();

    Plan& target = plan(handle);
    if (!target.sealed) throw std::logic_error("cannot execute unsealed plan");

    std::unordered_map<int32_t, PyObject*> slots;
    slots[0] = wrap_2d_array(input, batch_size, target.input_width);

    for (const auto& op : target.operations) {
        auto it = slots.find(op.input_slot);
        if (it == slots.end()) continue;
        PyObject* in_val = it->second;
        PyObject* out_val = nullptr;

        switch (op.type) {
            case OpType::MATRIX_PRODUCT: {
                PyObject* py_w = wrap_2d_array(op.weights.data(), op.input_width, op.output_width);
                out_val = call_binary(matmul_func, in_val, py_w);
                Py_DECREF(py_w);
                break;
            }
            case OpType::AFFINE: {
                PyObject* py_w = wrap_2d_array(op.weights.data(), op.output_width, op.input_width);
                PyObject* py_wt = PyObject_GetAttrString(py_w, "T");
                PyObject* mm_res = call_binary(matmul_func, in_val, py_wt ? py_wt : py_w);
                Py_XDECREF(py_wt);
                Py_DECREF(py_w);

                PyObject* py_b = wrap_2d_array(op.bias.data(), 1, op.output_width);
                out_val = call_binary(add_func, mm_res, py_b);
                Py_DECREF(py_b);
                Py_DECREF(mm_res);
                break;
            }
            case OpType::RELU: out_val = call_unary(relu_func, in_val); break;
            case OpType::SIGMOID: out_val = call_unary(sigmoid_func, in_val); break;
            case OpType::GELU: out_val = call_gelu(in_val); break;
            case OpType::SILU: out_val = call_unary(silu_func, in_val); break;
            case OpType::TANH: out_val = call_unary(tanh_func, in_val); break;
            case OpType::LEAKY_RELU: {
                PyObject* py_slope = PyFloat_FromDouble(op.param);
                PyObject* kwargs = PyDict_New();
                PyDict_SetItemString(kwargs, "negative_slope", py_slope);
                PyObject* args = PyTuple_Pack(1, in_val);
                out_val = PyObject_Call(leaky_relu_func, args, kwargs);
                Py_DECREF(args);
                Py_DECREF(kwargs);
                Py_DECREF(py_slope);
                break;
            }
            case OpType::ELU: {
                PyObject* py_alpha = PyFloat_FromDouble(op.param);
                PyObject* kwargs = PyDict_New();
                PyDict_SetItemString(kwargs, "alpha", py_alpha);
                PyObject* args = PyTuple_Pack(1, in_val);
                out_val = PyObject_Call(elu_func, args, kwargs);
                Py_DECREF(args);
                Py_DECREF(kwargs);
                Py_DECREF(py_alpha);
                break;
            }
            case OpType::SOFTMAX: {
                PyObject* py_dim = PyLong_FromLong(op.dim);
                PyObject* kwargs = PyDict_New();
                PyDict_SetItemString(kwargs, "axis", py_dim);
                PyObject* args = PyTuple_Pack(1, in_val);
                out_val = PyObject_Call(softmax_func, args, kwargs);
                Py_DECREF(args);
                Py_DECREF(kwargs);
                Py_DECREF(py_dim);
                break;
            }
            case OpType::LOG_SOFTMAX: {
                PyObject* py_dim = PyLong_FromLong(op.dim);
                PyObject* kwargs = PyDict_New();
                PyDict_SetItemString(kwargs, "axis", py_dim);
                PyObject* args = PyTuple_Pack(1, in_val);
                out_val = PyObject_Call(log_softmax_func, args, kwargs);
                Py_DECREF(args);
                Py_DECREF(kwargs);
                Py_DECREF(py_dim);
                break;
            }
            case OpType::LAYER_NORM: {
                // (x - mean) / sqrt(var + eps) * weight + bias
                PyObject* kwargs = PyDict_New();
                PyDict_SetItemString(kwargs, "axis", PyLong_FromLong(-1));
                PyDict_SetItemString(kwargs, "keepdims", Py_True);
                PyObject* args_m = PyTuple_Pack(1, in_val);
                PyObject* mean_val = PyObject_Call(mean_func, args_m, kwargs);
                Py_DECREF(args_m);

                PyObject* diff = call_binary(sub_func, in_val, mean_val);
                Py_DECREF(mean_val);

                PyObject* diff_sq = call_binary(pow_func, diff, PyFloat_FromDouble(2.0));
                PyObject* args_var = PyTuple_Pack(1, diff_sq);
                PyObject* var_val = PyObject_Call(mean_func, args_var, kwargs);
                Py_DECREF(args_var);
                Py_DECREF(diff_sq);
                Py_DECREF(kwargs);

                PyObject* var_eps = call_binary(add_func, var_val, PyFloat_FromDouble(op.eps));
                Py_DECREF(var_val);
                PyObject* std_val = call_unary(sqrt_func, var_eps);
                Py_DECREF(var_eps);

                PyObject* norm = call_binary(div_func, diff, std_val);
                Py_DECREF(diff);
                Py_DECREF(std_val);

                PyObject* py_w = wrap_2d_array(op.weights.data(), 1, op.output_width);
                PyObject* scaled = call_binary(mul_func, norm, py_w);
                Py_DECREF(norm);
                Py_DECREF(py_w);

                PyObject* py_b = wrap_2d_array(op.bias.data(), 1, op.output_width);
                out_val = call_binary(add_func, scaled, py_b);
                Py_DECREF(scaled);
                Py_DECREF(py_b);
                break;
            }
            case OpType::RMS_NORM: {
                // x / sqrt(mean(x^2) + eps) * weight
                PyObject* kwargs = PyDict_New();
                PyDict_SetItemString(kwargs, "axis", PyLong_FromLong(-1));
                PyDict_SetItemString(kwargs, "keepdims", Py_True);

                PyObject* x_sq = call_binary(pow_func, in_val, PyFloat_FromDouble(2.0));
                PyObject* args_var = PyTuple_Pack(1, x_sq);
                PyObject* var_val = PyObject_Call(mean_func, args_var, kwargs);
                Py_DECREF(args_var);
                Py_DECREF(x_sq);
                Py_DECREF(kwargs);

                PyObject* var_eps = call_binary(add_func, var_val, PyFloat_FromDouble(op.eps));
                Py_DECREF(var_val);
                PyObject* std_val = call_unary(sqrt_func, var_eps);
                Py_DECREF(var_eps);

                PyObject* norm = call_binary(div_func, in_val, std_val);
                Py_DECREF(std_val);

                PyObject* py_w = wrap_2d_array(op.weights.data(), 1, op.output_width);
                out_val = call_binary(mul_func, norm, py_w);
                Py_DECREF(norm);
                Py_DECREF(py_w);
                break;
            }
            case OpType::ATTENTION_MASK: {
                PyObject* py_mask = wrap_2d_array(op.weights.data(), op.mask_rows, op.mask_cols);
                out_val = call_binary(add_func, in_val, py_mask);
                Py_DECREF(py_mask);
                break;
            }
            case OpType::SCALED_DOT_PRODUCT_ATTENTION: {
                auto k_it = slots.find(op.input_slot_b);
                auto v_it = slots.find(op.input_slot_c);
                if (k_it != slots.end() && v_it != slots.end()) {
                    PyObject* k_trans = PyObject_GetAttrString(k_it->second, "T");
                    PyObject* scores = call_binary(matmul_func, in_val, k_trans);
                    Py_DECREF(k_trans);

                    PyObject* py_scale = PyFloat_FromDouble(op.param);
                    PyObject* scaled_scores = call_binary(mul_func, scores, py_scale);
                    Py_DECREF(scores);
                    Py_DECREF(py_scale);

                    PyObject* kwargs = PyDict_New();
                    PyDict_SetItemString(kwargs, "axis", PyLong_FromLong(-1));
                    PyObject* args = PyTuple_Pack(1, scaled_scores);
                    PyObject* probs = PyObject_Call(softmax_func, args, kwargs);
                    Py_DECREF(args);
                    Py_DECREF(kwargs);
                    Py_DECREF(scaled_scores);

                    out_val = call_binary(matmul_func, probs, v_it->second);
                    Py_DECREF(probs);
                }
                break;
            }
            case OpType::BINARY_ADD: {
                auto b_it = slots.find(op.input_slot_b);
                if (b_it != slots.end()) out_val = call_binary(add_func, in_val, b_it->second);
                break;
            }
            case OpType::BINARY_SUB: {
                auto b_it = slots.find(op.input_slot_b);
                if (b_it != slots.end()) out_val = call_binary(sub_func, in_val, b_it->second);
                break;
            }
            case OpType::BINARY_MUL: {
                auto b_it = slots.find(op.input_slot_b);
                if (b_it != slots.end()) out_val = call_binary(mul_func, in_val, b_it->second);
                break;
            }
            case OpType::BINARY_DIV: {
                auto b_it = slots.find(op.input_slot_b);
                if (b_it != slots.end()) out_val = call_binary(div_func, in_val, b_it->second);
                break;
            }
            case OpType::UNARY_EXP: out_val = call_unary(exp_func, in_val); break;
            case OpType::UNARY_LOG: out_val = call_unary(log_func, in_val); break;
            case OpType::UNARY_SQRT: out_val = call_unary(sqrt_func, in_val); break;
            case OpType::UNARY_POW: {
                PyObject* py_p = PyFloat_FromDouble(op.param);
                out_val = call_binary(pow_func, in_val, py_p);
                Py_DECREF(py_p);
                break;
            }
            case OpType::REDUCTION_SUM: {
                PyObject* kwargs = PyDict_New();
                if (op.dim >= 0) PyDict_SetItemString(kwargs, "axis", PyLong_FromLong(op.dim));
                PyDict_SetItemString(kwargs, "keepdims", op.keepdim ? Py_True : Py_False);
                PyObject* args = PyTuple_Pack(1, in_val);
                out_val = PyObject_Call(sum_func, args, kwargs);
                Py_DECREF(args);
                Py_DECREF(kwargs);
                break;
            }
            case OpType::REDUCTION_MEAN: {
                PyObject* kwargs = PyDict_New();
                if (op.dim >= 0) PyDict_SetItemString(kwargs, "axis", PyLong_FromLong(op.dim));
                PyDict_SetItemString(kwargs, "keepdims", op.keepdim ? Py_True : Py_False);
                PyObject* args = PyTuple_Pack(1, in_val);
                out_val = PyObject_Call(mean_func, args, kwargs);
                Py_DECREF(args);
                Py_DECREF(kwargs);
                break;
            }
            case OpType::LOSS_MSE: {
                auto t_it = slots.find(op.input_slot_b);
                if (t_it != slots.end()) {
                    PyObject* diff = call_binary(sub_func, in_val, t_it->second);
                    PyObject* diff_sq = call_binary(pow_func, diff, PyFloat_FromDouble(2.0));
                    Py_DECREF(diff);
                    out_val = call_unary(mean_func, diff_sq);
                    Py_DECREF(diff_sq);
                }
                break;
            }
            case OpType::LOSS_CROSS_ENTROPY: {
                auto t_it = slots.find(op.input_slot_b);
                if (t_it != slots.end()) {
                    // - mean(sum(t * log_softmax(p), axis=-1))
                    PyObject* kwargs = PyDict_New();
                    PyDict_SetItemString(kwargs, "axis", PyLong_FromLong(-1));
                    PyObject* args = PyTuple_Pack(1, in_val);
                    PyObject* log_probs = PyObject_Call(log_softmax_func, args, kwargs);
                    Py_DECREF(args);
                    Py_DECREF(kwargs);

                    PyObject* prod = call_binary(mul_func, t_it->second, log_probs);
                    Py_DECREF(log_probs);

                    PyObject* sum_kwargs = PyDict_New();
                    PyDict_SetItemString(sum_kwargs, "axis", PyLong_FromLong(-1));
                    PyObject* sum_args = PyTuple_Pack(1, prod);
                    PyObject* sum_res = PyObject_Call(sum_func, sum_args, sum_kwargs);
                    Py_DECREF(sum_args);
                    Py_DECREF(sum_kwargs);
                    Py_DECREF(prod);

                    PyObject* mean_loss = call_unary(mean_func, sum_res);
                    Py_DECREF(sum_res);

                    out_val = call_binary(mul_func, mean_loss, PyFloat_FromDouble(-1.0));
                    Py_DECREF(mean_loss);
                }
                break;
            }
            default: break;
        }

        if (out_val) {
            slots[op.output_slot] = out_val;
        }
    }

    auto out_it = slots.find(target.output_slot);
    if (out_it != slots.end() && out_it->second) {
        copy_to_output(out_it->second, output, static_cast<size_t>(batch_size * target.output_width));
    }

    for (auto& pair : slots) {
        Py_DECREF(pair.second);
    }
    PyGILState_Release(gstate);
}

void jax_panama_matmul(const float* a, int64_t a_rows, int64_t a_cols,
                       const float* b, int64_t b_rows, int64_t b_cols,
                       float* out) {
    ensure_jax();
    std::lock_guard<std::mutex> lock(jax_execution_mutex);
    PyGILState_STATE gstate = PyGILState_Ensure();

    PyObject* py_a = wrap_2d_array(a, static_cast<npy_intp>(a_rows), static_cast<npy_intp>(a_cols));
    PyObject* py_b = wrap_2d_array(b, static_cast<npy_intp>(b_rows), static_cast<npy_intp>(b_cols));

    PyObject* result = call_binary(matmul_func, py_a, py_b);
    Py_DECREF(py_a);
    Py_DECREF(py_b);

    if (result) {
        copy_to_output(result, out, static_cast<size_t>(a_rows * b_cols));
        Py_DECREF(result);
    }
    PyGILState_Release(gstate);
}

void jax_panama_tensor_op(int32_t op_id, const float* a, int64_t size, float* out, float param) {
    ensure_jax();
    std::lock_guard<std::mutex> lock(jax_execution_mutex);
    PyGILState_STATE gstate = PyGILState_Ensure();

    PyObject* py_a = wrap_1d_array(a, static_cast<npy_intp>(size));
    PyObject* res = nullptr;

    switch (op_id) {
        case 1: res = call_gelu(py_a); break;
        case 2: res = call_unary(silu_func, py_a); break;
        case 3: res = call_unary(tanh_func, py_a); break;
        case 4: res = call_unary(sigmoid_func, py_a); break;
        case 5: {
            PyObject* kwargs = PyDict_New();
            PyDict_SetItemString(kwargs, "negative_slope", PyFloat_FromDouble(param));
            PyObject* args = PyTuple_Pack(1, py_a);
            res = PyObject_Call(leaky_relu_func, args, kwargs);
            Py_DECREF(args);
            Py_DECREF(kwargs);
            break;
        }
        case 6: {
            PyObject* kwargs = PyDict_New();
            PyDict_SetItemString(kwargs, "alpha", PyFloat_FromDouble(param));
            PyObject* args = PyTuple_Pack(1, py_a);
            res = PyObject_Call(elu_func, args, kwargs);
            Py_DECREF(args);
            Py_DECREF(kwargs);
            break;
        }
        case 7: res = call_unary(exp_func, py_a); break;
        case 8: res = call_unary(log_func, py_a); break;
        case 9: res = call_unary(sqrt_func, py_a); break;
        default: res = call_unary(relu_func, py_a); break;
    }

    Py_DECREF(py_a);

    if (res) {
        copy_to_output(res, out, static_cast<size_t>(size));
        Py_DECREF(res);
    }
    PyGILState_Release(gstate);
}

void jax_panama_configure_threads(int32_t, int32_t) {}
int32_t jax_panama_intra_op_threads() { return 1; }
int32_t jax_panama_inter_op_threads() { return 1; }

} // extern "C"
