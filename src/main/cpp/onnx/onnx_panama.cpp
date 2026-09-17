/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

#include "onnx_panama.h"
#include "onnxruntime_c_api.h"

#include <dlfcn.h>
#include <iostream>
#include <string>
#include <vector>
#include <cstring>

static void* g_ort_lib = nullptr;
static const OrtApi* g_ort = nullptr;
static OrtEnv* g_env = nullptr;

struct OnnxSessionContext {
    OrtSession* session = nullptr;
    OrtAllocator* allocator = nullptr;
    std::string input_name;
    std::string output_name;
};

static bool init_onnx_api() {
    if (g_ort != nullptr) return true;

    const char* candidates[] = {
        "/usr/lib/x86_64-linux-gnu/libonnxruntime.so.1.23",
        "libonnxruntime.so.1.23",
        "libonnxruntime.so.1",
        "libonnxruntime.so",
        "onnxruntime.dll",
        "libonnxruntime.dylib",
        nullptr
    };

    for (int i = 0; candidates[i] != nullptr; ++i) {
        g_ort_lib = dlopen(candidates[i], RTLD_LAZY | RTLD_GLOBAL);
        if (g_ort_lib) break;
    }

    if (!g_ort_lib) {
        // Try fallback to standard symbols already loaded in process
        g_ort_lib = dlopen(nullptr, RTLD_LAZY);
    }

    if (!g_ort_lib) return false;

    typedef const OrtApiBase* (*OrtGetApiBaseFn)(void);
    OrtGetApiBaseFn get_api_base = (OrtGetApiBaseFn) dlsym(g_ort_lib, "OrtGetApiBase");
    if (!get_api_base) return false;

    const OrtApiBase* base = get_api_base();
    if (!base) return false;

    g_ort = base->GetApi(ORT_API_VERSION);
    if (!g_ort) return false;

    OrtStatus* status = g_ort->CreateEnv(ORT_LOGGING_LEVEL_WARNING, "groovy_math_onnx", &g_env);
    if (status != nullptr) {
        g_ort->ReleaseStatus(status);
        g_env = nullptr;
        return false;
    }

    return true;
}

int onnx_panama_is_available(void) {
    return init_onnx_api() ? 1 : 0;
}

int64_t onnx_panama_create_session(const char* model_path) {
    if (!init_onnx_api() || !model_path) return 0;

    OrtSessionOptions* session_options = nullptr;
    OrtStatus* status = g_ort->CreateSessionOptions(&session_options);
    if (status != nullptr) {
        g_ort->ReleaseStatus(status);
        return 0;
    }

    OrtSession* session = nullptr;
    status = g_ort->CreateSession(g_env, model_path, session_options, &session);
    g_ort->ReleaseSessionOptions(session_options);

    if (status != nullptr) {
        g_ort->ReleaseStatus(status);
        return 0;
    }

    OnnxSessionContext* ctx = new OnnxSessionContext();
    ctx->session = session;

    status = g_ort->GetAllocatorWithDefaultOptions(&ctx->allocator);
    if (status != nullptr) {
        g_ort->ReleaseStatus(status);
    } else {
        // Query default input name (index 0)
        size_t num_inputs = 0;
        if (g_ort->SessionGetInputCount(session, &num_inputs) == nullptr && num_inputs > 0) {
            char* name = nullptr;
            if (g_ort->SessionGetInputName(session, 0, ctx->allocator, &name) == nullptr && name) {
                ctx->input_name = name;
                ctx->allocator->Free(ctx->allocator, name);
            }
        }

        // Query default output name (index 0)
        size_t num_outputs = 0;
        if (g_ort->SessionGetOutputCount(session, &num_outputs) == nullptr && num_outputs > 0) {
            char* name = nullptr;
            if (g_ort->SessionGetOutputName(session, 0, ctx->allocator, &name) == nullptr && name) {
                ctx->output_name = name;
                ctx->allocator->Free(ctx->allocator, name);
            }
        }
    }

    return reinterpret_cast<int64_t>(ctx);
}

void onnx_panama_destroy_session(int64_t session_handle) {
    if (session_handle == 0 || !g_ort) return;
    OnnxSessionContext* ctx = reinterpret_cast<OnnxSessionContext*>(session_handle);
    if (ctx->session) {
        g_ort->ReleaseSession(ctx->session);
    }
    delete ctx;
}

int64_t onnx_panama_run(int64_t session_handle,
                        const char* input_name,
                        const float* input_data,
                        const int64_t* input_shape,
                        int64_t input_rank,
                        const char* output_name,
                        float* output_data,
                        int64_t output_size) {
    if (session_handle == 0 || !g_ort || !input_data || !input_shape || !output_data) return -1;
    OnnxSessionContext* ctx = reinterpret_cast<OnnxSessionContext*>(session_handle);

    const char* in_name = (input_name && input_name[0] != '\0') ? input_name : ctx->input_name.c_str();
    const char* out_name = (output_name && output_name[0] != '\0') ? output_name : ctx->output_name.c_str();

    int64_t total_elements = 1;
    for (int64_t i = 0; i < input_rank; ++i) total_elements *= input_shape[i];
    size_t total_bytes = total_elements * sizeof(float);

    OrtMemoryInfo* mem_info = nullptr;
    OrtStatus* status = g_ort->CreateCpuMemoryInfo(OrtArenaAllocator, OrtMemTypeDefault, &mem_info);
    if (status != nullptr) {
        g_ort->ReleaseStatus(status);
        return -2;
    }

    OrtValue* in_tensor = nullptr;
    status = g_ort->CreateTensorWithDataAsOrtValue(
        mem_info,
        const_cast<float*>(input_data),
        total_bytes,
        input_shape,
        input_rank,
        ONNX_TENSOR_ELEMENT_DATA_TYPE_FLOAT,
        &in_tensor
    );
    g_ort->ReleaseMemoryInfo(mem_info);

    if (status != nullptr) {
        g_ort->ReleaseStatus(status);
        return -3;
    }

    OrtValue* out_tensor = nullptr;
    status = g_ort->Run(
        ctx->session,
        nullptr,
        &in_name,
        &in_tensor,
        1,
        &out_name,
        1,
        &out_tensor
    );
    g_ort->ReleaseValue(in_tensor);

    if (status != nullptr) {
        g_ort->ReleaseStatus(status);
        return -4;
    }

    void* out_raw = nullptr;
    status = g_ort->GetTensorMutableData(out_tensor, &out_raw);
    if (status != nullptr || !out_raw) {
        if (status) g_ort->ReleaseStatus(status);
        g_ort->ReleaseValue(out_tensor);
        return -5;
    }

    std::memcpy(output_data, out_raw, output_size * sizeof(float));
    g_ort->ReleaseValue(out_tensor);

    return 0;
}
