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
    SOFTMAX,
    ATTENTION_MASK
};

struct Operation {
    OpType type;
    int32_t input_slot;
    int32_t output_slot;
    int32_t input_width;
    int32_t output_width;
    std::vector<float> weights;
    std::vector<float> bias;
    int64_t dim = -1;
    int64_t mask_rows = 0;
    int64_t mask_cols = 0;
};

struct Plan {
    int32_t input_width;
    int32_t output_slot = -1;
    int32_t output_width = -1;
    bool sealed = false;
    std::vector<Operation> operations;

    explicit Plan(int32_t width) : input_width(width) {
        if (width <= 0) throw std::invalid_argument("input width must be positive");
    }
};

#ifndef GROOVY_MATH_PYTHON_LIBRARY
#define GROOVY_MATH_PYTHON_LIBRARY "libpython3.so"
#endif

// Resolves the libpython shared object to pre-load with RTLD_GLOBAL: an
// operator override (GROOVY_MATH_PYTHON_LIBRARY env var) takes precedence,
// then the exact path CMake resolved at build time, so the bridge stays
// portable across machines instead of pointing at one developer's install.
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
PyObject* maximum_func = nullptr;
PyObject* softmax_func = nullptr;
std::mutex jax_execution_mutex;

void ensure_jax() {
    std::call_once(jax_init_flag, [] {
        // Ensure libpython symbols are exported globally for NumPy and JAX C extensions
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
        maximum_func = PyObject_GetAttrString(jnp_module, "maximum");
        softmax_func = PyObject_GetAttrString(jax_nn_module, "softmax");
    });
}

Plan& plan(int64_t handle) {
    if (handle == 0) throw std::invalid_argument("native plan handle is zero");
    return *reinterpret_cast<Plan*>(handle);
}

// Wrap raw float pointer into a 2D NumPy array with zero copy
PyObject* wrap_2d_array(const float* data, npy_intp rows, npy_intp cols) {
    npy_intp dims[2] = {rows, cols};
    return PyArray_SimpleNewFromData(2, dims, NPY_FLOAT32, const_cast<float*>(data));
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

void jax_panama_add_softmax(int64_t handle, int32_t input_slot, int32_t output_slot, int64_t dim) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::SOFTMAX;
    op.input_slot = input_slot;
    op.output_slot = output_slot;
    op.dim = dim;
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

void jax_panama_matmul(const float* a, int64_t a_rows, int64_t a_cols,
                       const float* b, int64_t b_rows, int64_t b_cols,
                       float* out) {
    ensure_jax();
    std::lock_guard<std::mutex> lock(jax_execution_mutex);
    PyGILState_STATE gstate = PyGILState_Ensure();

    PyObject* py_a = wrap_2d_array(a, static_cast<npy_intp>(a_rows), static_cast<npy_intp>(a_cols));
    PyObject* py_b = wrap_2d_array(b, static_cast<npy_intp>(b_rows), static_cast<npy_intp>(b_cols));

    PyObject* args = PyTuple_Pack(2, py_a, py_b);
    PyObject* result = PyObject_CallObject(matmul_func, args);
    Py_DECREF(args);
    Py_DECREF(py_a);
    Py_DECREF(py_b);

    if (result) {
        copy_to_output(result, out, static_cast<size_t>(a_rows * b_cols));
        Py_DECREF(result);
    }
    PyGILState_Release(gstate);
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
                PyObject* args = PyTuple_Pack(2, in_val, py_w);
                out_val = PyObject_CallObject(matmul_func, args);
                Py_DECREF(args);
                Py_DECREF(py_w);
                break;
            }
            case OpType::AFFINE: {
                PyObject* py_w = wrap_2d_array(op.weights.data(), op.input_width, op.output_width);
                PyObject* args_mm = PyTuple_Pack(2, in_val, py_w);
                PyObject* mm_res = PyObject_CallObject(matmul_func, args_mm);
                Py_DECREF(args_mm);
                Py_DECREF(py_w);

                PyObject* py_b = wrap_2d_array(op.bias.data(), 1, op.output_width);
                PyObject* args_add = PyTuple_Pack(2, mm_res, py_b);
                out_val = PyObject_CallObject(add_func, args_add);
                Py_DECREF(args_add);
                Py_DECREF(py_b);
                Py_DECREF(mm_res);
                break;
            }
            case OpType::RELU: {
                PyObject* py_zero = PyFloat_FromDouble(0.0);
                PyObject* args = PyTuple_Pack(2, in_val, py_zero);
                out_val = PyObject_CallObject(maximum_func, args);
                Py_DECREF(args);
                Py_DECREF(py_zero);
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
            case OpType::ATTENTION_MASK: {
                PyObject* py_mask = wrap_2d_array(op.weights.data(), op.mask_rows, op.mask_cols);
                PyObject* args = PyTuple_Pack(2, in_val, py_mask);
                out_val = PyObject_CallObject(add_func, args);
                Py_DECREF(args);
                Py_DECREF(py_mask);
                break;
            }
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

void jax_panama_configure_threads(int32_t, int32_t) {
}

int32_t jax_panama_intra_op_threads() { return 1; }
int32_t jax_panama_inter_op_threads() { return 1; }

} // extern "C"
