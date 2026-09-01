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
    GAUSSIAN_BLUR,
    SOBEL,
    CANNY,
    WARP_AFFINE,
    WARP_PERSPECTIVE,
    FILTER_2D
};

struct Operation {
    OpType type;
    int32_t ksize = 3;
    double sigma = 1.0;
    int32_t dx = 1;
    int32_t dy = 0;
    double threshold1 = 50.0;
    double threshold2 = 150.0;
    std::vector<double> transform_matrix;
    std::vector<float> kernel_data;
    int32_t out_width = 0;
    int32_t out_height = 0;
};

struct Plan {
    int32_t input_width;
    int32_t input_height;
    int32_t output_width = 0;
    int32_t output_height = 0;
    bool sealed = false;
    std::vector<Operation> operations;

    Plan(int32_t w, int32_t h) : input_width(w), input_height(h) {
        if (w <= 0 || h <= 0) throw std::invalid_argument("dimensions must be positive");
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

std::once_flag opencv_init_flag;
PyObject* cv2_module = nullptr;
PyObject* np_module = nullptr;
PyObject* np_asarray_func = nullptr;
PyObject* gaussian_blur_func = nullptr;
PyObject* sobel_func = nullptr;
PyObject* canny_func = nullptr;
PyObject* warp_affine_func = nullptr;
PyObject* warp_perspective_func = nullptr;
PyObject* filter2d_func = nullptr;
std::mutex opencv_execution_mutex;

void ensure_opencv() {
    std::call_once(opencv_init_flag, [] {
        // Ensure python symbols are available globally
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

        cv2_module = PyImport_ImportModule("cv2");
        if (!cv2_module) {
            PyErr_Print();
            throw std::runtime_error("Failed to import cv2");
        }

        gaussian_blur_func = PyObject_GetAttrString(cv2_module, "GaussianBlur");
        sobel_func = PyObject_GetAttrString(cv2_module, "Sobel");
        canny_func = PyObject_GetAttrString(cv2_module, "Canny");
        warp_affine_func = PyObject_GetAttrString(cv2_module, "warpAffine");
        warp_perspective_func = PyObject_GetAttrString(cv2_module, "warpPerspective");
        filter2d_func = PyObject_GetAttrString(cv2_module, "filter2D");
    });
}

Plan& plan(int64_t handle) {
    if (handle == 0) throw std::invalid_argument("native plan handle is zero");
    return *reinterpret_cast<Plan*>(handle);
}

PyObject* wrap_2d_float_array(const float* data, npy_intp rows, npy_intp cols) {
    npy_intp dims[2] = {rows, cols};
    return PyArray_SimpleNewFromData(2, dims, NPY_FLOAT32, const_cast<float*>(data));
}

PyObject* wrap_2d_double_array(const double* data, npy_intp rows, npy_intp cols) {
    npy_intp dims[2] = {rows, cols};
    return PyArray_SimpleNewFromData(2, dims, NPY_FLOAT64, const_cast<double*>(data));
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

int64_t opencv_panama_create_plan(int32_t width, int32_t height) {
    try {
        ensure_opencv();
        return reinterpret_cast<int64_t>(new Plan(width, height));
    } catch (...) {
        return 0;
    }
}

void opencv_panama_destroy(int64_t handle) {
    if (handle != 0) {
        delete reinterpret_cast<Plan*>(handle);
    }
}

int32_t opencv_panama_output_width(int64_t handle) {
    if (handle == 0) return 0;
    return plan(handle).output_width;
}

int32_t opencv_panama_output_height(int64_t handle) {
    if (handle == 0) return 0;
    return plan(handle).output_height;
}

void opencv_panama_seal(int64_t handle, int32_t output_width, int32_t output_height) {
    Plan& target = plan(handle);
    if (target.operations.empty()) throw std::logic_error("cannot seal empty plan");
    target.output_width = output_width > 0 ? output_width : target.input_width;
    target.output_height = output_height > 0 ? output_height : target.input_height;
    target.sealed = true;
}

void opencv_panama_add_gaussian_blur(int64_t handle, int32_t ksize, double sigma) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::GAUSSIAN_BLUR;
    op.ksize = ksize;
    op.sigma = sigma;
    target.operations.push_back(std::move(op));
}

void opencv_panama_add_sobel(int64_t handle, int32_t dx, int32_t dy, int32_t ksize) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::SOBEL;
    op.dx = dx;
    op.dy = dy;
    op.ksize = ksize;
    target.operations.push_back(std::move(op));
}

void opencv_panama_add_warp_affine(int64_t handle, const double* matrix_2x3, int32_t out_width, int32_t out_height) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::WARP_AFFINE;
    op.transform_matrix.assign(matrix_2x3, matrix_2x3 + 6);
    op.out_width = out_width;
    op.out_height = out_height;
    target.operations.push_back(std::move(op));
}

void opencv_panama_add_warp_perspective(int64_t handle, const double* matrix_3x3, int32_t out_width, int32_t out_height) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::WARP_PERSPECTIVE;
    op.transform_matrix.assign(matrix_3x3, matrix_3x3 + 9);
    op.out_width = out_width;
    op.out_height = out_height;
    target.operations.push_back(std::move(op));
}

void opencv_panama_add_filter2d(int64_t handle, const float* kernel, int32_t kwidth, int32_t kheight) {
    Plan& target = plan(handle);
    Operation op;
    op.type = OpType::FILTER_2D;
    op.ksize = kwidth;
    op.kernel_data.assign(kernel, kernel + kwidth * kheight);
    target.operations.push_back(std::move(op));
}

void opencv_panama_execute(int64_t handle, const float* input, float* output) {
    ensure_opencv();
    std::lock_guard<std::mutex> lock(opencv_execution_mutex);
    PyGILState_STATE gstate = PyGILState_Ensure();

    Plan& target = plan(handle);
    if (!target.sealed) throw std::logic_error("plan not sealed");

    PyObject* current_img = wrap_2d_float_array(input, target.input_height, target.input_width);

    for (const auto& op : target.operations) {
        PyObject* next_img = nullptr;

        switch (op.type) {
            case OpType::GAUSSIAN_BLUR: {
                PyObject* ksize_tuple = Py_BuildValue("(ii)", op.ksize, op.ksize);
                PyObject* args = PyTuple_Pack(3, current_img, ksize_tuple, PyFloat_FromDouble(op.sigma));
                next_img = PyObject_CallObject(gaussian_blur_func, args);
                Py_DECREF(args);
                Py_DECREF(ksize_tuple);
                break;
            }
            case OpType::SOBEL: {
                // cv2.Sobel(src, ddepth=cv2.CV_32F, dx, dy, ksize)
                PyObject* ddepth = PyLong_FromLong(5); // CV_32F = 5
                PyObject* dx = PyLong_FromLong(op.dx);
                PyObject* dy = PyLong_FromLong(op.dy);
                PyObject* ksize = PyLong_FromLong(op.ksize);
                PyObject* args = PyTuple_Pack(5, current_img, ddepth, dx, dy, ksize);
                next_img = PyObject_CallObject(sobel_func, args);
                Py_DECREF(args);
                Py_DECREF(ddepth);
                Py_DECREF(dx);
                Py_DECREF(dy);
                Py_DECREF(ksize);
                break;
            }
            case OpType::WARP_AFFINE: {
                PyObject* m = wrap_2d_double_array(op.transform_matrix.data(), 2, 3);
                PyObject* dsize = Py_BuildValue("(ii)", op.out_width, op.out_height);
                PyObject* args = PyTuple_Pack(3, current_img, m, dsize);
                next_img = PyObject_CallObject(warp_affine_func, args);
                Py_DECREF(args);
                Py_DECREF(m);
                Py_DECREF(dsize);
                break;
            }
            case OpType::WARP_PERSPECTIVE: {
                PyObject* m = wrap_2d_double_array(op.transform_matrix.data(), 3, 3);
                PyObject* dsize = Py_BuildValue("(ii)", op.out_width, op.out_height);
                PyObject* args = PyTuple_Pack(3, current_img, m, dsize);
                next_img = PyObject_CallObject(warp_perspective_func, args);
                Py_DECREF(args);
                Py_DECREF(m);
                Py_DECREF(dsize);
                break;
            }
            case OpType::FILTER_2D: {
                // cv2.filter2D(src, ddepth=-1, kernel)
                PyObject* ddepth = PyLong_FromLong(-1);
                PyObject* kernel = wrap_2d_float_array(op.kernel_data.data(), op.ksize, op.ksize);
                PyObject* args = PyTuple_Pack(3, current_img, ddepth, kernel);
                next_img = PyObject_CallObject(filter2d_func, args);
                Py_DECREF(args);
                Py_DECREF(ddepth);
                Py_DECREF(kernel);
                break;
            }
            default:
                break;
        }

        if (next_img) {
            Py_DECREF(current_img);
            current_img = next_img;
        }
    }

    copy_to_output(current_img, output, static_cast<size_t>(target.output_width * target.output_height));
    Py_DECREF(current_img);
    PyGILState_Release(gstate);
}

void opencv_panama_filter2d_direct(const float* src, int32_t width, int32_t height,
                                   const float* kernel, int32_t kwidth, int32_t kheight,
                                   float* dst) {
    ensure_opencv();
    std::lock_guard<std::mutex> lock(opencv_execution_mutex);
    PyGILState_STATE gstate = PyGILState_Ensure();

    PyObject* py_src = wrap_2d_float_array(src, height, width);
    PyObject* py_kernel = wrap_2d_float_array(kernel, kheight, kwidth);
    PyObject* ddepth = PyLong_FromLong(-1);

    PyObject* args = PyTuple_Pack(3, py_src, ddepth, py_kernel);
    PyObject* result = PyObject_CallObject(filter2d_func, args);
    Py_DECREF(args);
    Py_DECREF(py_src);
    Py_DECREF(py_kernel);
    Py_DECREF(ddepth);

    if (result) {
        copy_to_output(result, dst, static_cast<size_t>(width * height));
        Py_DECREF(result);
    }
    PyGILState_Release(gstate);
}

void opencv_panama_warp_affine_direct(const float* src, int32_t width, int32_t height,
                                     const double* m2x3, int32_t out_w, int32_t out_h,
                                     float* dst) {
    ensure_opencv();
    std::lock_guard<std::mutex> lock(opencv_execution_mutex);
    PyGILState_STATE gstate = PyGILState_Ensure();

    PyObject* py_src = wrap_2d_float_array(src, height, width);
    PyObject* py_m = wrap_2d_double_array(m2x3, 2, 3);
    PyObject* dsize = Py_BuildValue("(ii)", out_w, out_h);

    PyObject* args = PyTuple_Pack(3, py_src, py_m, dsize);
    PyObject* result = PyObject_CallObject(warp_affine_func, args);
    Py_DECREF(args);
    Py_DECREF(py_src);
    Py_DECREF(py_m);
    Py_DECREF(dsize);

    if (result) {
        copy_to_output(result, dst, static_cast<size_t>(out_w * out_h));
        Py_DECREF(result);
    }
    PyGILState_Release(gstate);
}

} // extern "C"
