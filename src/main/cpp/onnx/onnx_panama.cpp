/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

#include "onnx_panama.h"
#include "onnxruntime_c_api.h"

#include <atomic>
#include <cstring>
#include <dlfcn.h>
#include <iostream>
#include <memory>
#include <mutex>
#include <string>
#include <unordered_map>
#include <vector>

static void* g_ort_lib = nullptr;
static const OrtApi* g_ort = nullptr;
static OrtEnv* g_env = nullptr;

thread_local std::string g_last_error;

struct OnnxSessionContext {
    OrtSession* session = nullptr;
    OrtAllocator* allocator = nullptr;
    std::string input_name;
    std::string output_name;

    ~OnnxSessionContext() {
        if (session && g_ort) {
            g_ort->ReleaseSession(session);
            session = nullptr;
        }
    }
};

static std::mutex g_sessions_mutex;
static std::unordered_map<int64_t, std::unique_ptr<OnnxSessionContext>> g_sessions;
static std::atomic<int64_t> g_next_session_id{1};

const char* onnx_panama_last_error(void) {
    return g_last_error.empty() ? nullptr : g_last_error.c_str();
}

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

    if (!g_ort_lib) {
        g_last_error = "Could not dlopen libonnxruntime";
        return false;
    }

    typedef const OrtApiBase* (*OrtGetApiBaseFn)(void);
    OrtGetApiBaseFn get_api_base = (OrtGetApiBaseFn) dlsym(g_ort_lib, "OrtGetApiBase");
    if (!get_api_base) {
        g_last_error = "Could not find OrtGetApiBase symbol in onnxruntime library";
        return false;
    }

    const OrtApiBase* base = get_api_base();
    if (!base) {
        g_last_error = "OrtGetApiBase returned null";
        return false;
    }

    g_ort = base->GetApi(ORT_API_VERSION);
    if (!g_ort) {
        g_last_error = "Failed to obtain OrtApi version " + std::to_string(ORT_API_VERSION);
        return false;
    }

    OrtStatus* status = g_ort->CreateEnv(ORT_LOGGING_LEVEL_WARNING, "groovy_math_onnx", &g_env);
    if (status != nullptr) {
        const char* msg = g_ort->GetErrorMessage(status);
        g_last_error = msg ? msg : "CreateEnv failed";
        g_ort->ReleaseStatus(status);
        g_env = nullptr;
        return false;
    }

    return true;
}

int onnx_panama_is_available(void) {
    try {
        return init_onnx_api() ? 1 : 0;
    } catch (const std::exception& e) {
        g_last_error = e.what();
        return 0;
    } catch (...) {
        g_last_error = "Unknown error initializing ONNX API";
        return 0;
    }
}

int64_t onnx_panama_create_session(const char* model_path) {
    try {
        g_last_error.clear();
        if (!init_onnx_api()) return 0;
        if (!model_path) {
            g_last_error = "Model path cannot be null";
            return 0;
        }

        OrtSessionOptions* session_options = nullptr;
        OrtStatus* status = g_ort->CreateSessionOptions(&session_options);
        if (status != nullptr) {
            const char* msg = g_ort->GetErrorMessage(status);
            g_last_error = msg ? msg : "CreateSessionOptions failed";
            g_ort->ReleaseStatus(status);
            return 0;
        }

        OrtSession* session = nullptr;
        status = g_ort->CreateSession(g_env, model_path, session_options, &session);
        g_ort->ReleaseSessionOptions(session_options);

        if (status != nullptr) {
            const char* msg = g_ort->GetErrorMessage(status);
            g_last_error = msg ? msg : "CreateSession failed";
            g_ort->ReleaseStatus(status);
            return 0;
        }

        auto ctx = std::make_unique<OnnxSessionContext>();
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

        int64_t handle = g_next_session_id.fetch_add(1);
        {
            std::lock_guard<std::mutex> lock(g_sessions_mutex);
            g_sessions[handle] = std::move(ctx);
        }
        return handle;
    } catch (const std::exception& e) {
        g_last_error = e.what();
        return 0;
    } catch (...) {
        g_last_error = "Unknown error in onnx_panama_create_session";
        return 0;
    }
}

void onnx_panama_destroy_session(int64_t session_handle) {
    try {
        if (session_handle == 0) return;
        std::lock_guard<std::mutex> lock(g_sessions_mutex);
        g_sessions.erase(session_handle);
    } catch (...) {
        // Suppress in destructor/cleanup
    }
}

int64_t onnx_panama_run(int64_t session_handle,
                        const char* input_name,
                        const float* input_data,
                        const int64_t* input_shape,
                        int64_t input_rank,
                        const char* output_name,
                        float* output_data,
                        int64_t output_size,
                        int64_t output_capacity_bytes) {
    try {
        g_last_error.clear();
        if (session_handle == 0 || !g_ort || !input_data || !input_shape || !output_data) {
            g_last_error = "Invalid arguments to onnx_panama_run: null pointer or zero handle";
            return -1;
        }

        if (output_size * static_cast<int64_t>(sizeof(float)) > output_capacity_bytes) {
            g_last_error = "Output buffer overflow prevented: required " +
                           std::to_string(output_size * sizeof(float)) + " bytes, buffer capacity is " +
                           std::to_string(output_capacity_bytes) + " bytes";
            return -2;
        }

        OnnxSessionContext* ctx = nullptr;
        {
            std::lock_guard<std::mutex> lock(g_sessions_mutex);
            auto it = g_sessions.find(session_handle);
            if (it == g_sessions.end()) {
                g_last_error = "Invalid or expired session handle: " + std::to_string(session_handle);
                return -3;
            }
            ctx = it->second.get();
        }

        const char* in_name = (input_name && input_name[0] != '\0') ? input_name : ctx->input_name.c_str();
        const char* out_name = (output_name && output_name[0] != '\0') ? output_name : ctx->output_name.c_str();

        int64_t total_elements = 1;
        for (int64_t i = 0; i < input_rank; ++i) total_elements *= input_shape[i];
        size_t total_bytes = total_elements * sizeof(float);

        OrtMemoryInfo* mem_info = nullptr;
        OrtStatus* status = g_ort->CreateCpuMemoryInfo(OrtArenaAllocator, OrtMemTypeDefault, &mem_info);
        if (status != nullptr) {
            const char* msg = g_ort->GetErrorMessage(status);
            g_last_error = msg ? msg : "CreateCpuMemoryInfo failed";
            g_ort->ReleaseStatus(status);
            return -4;
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
            const char* msg = g_ort->GetErrorMessage(status);
            g_last_error = msg ? msg : "CreateTensorWithDataAsOrtValue failed";
            g_ort->ReleaseStatus(status);
            return -5;
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
            const char* msg = g_ort->GetErrorMessage(status);
            g_last_error = msg ? msg : "Session Run failed";
            g_ort->ReleaseStatus(status);
            return -6;
        }

        void* out_raw = nullptr;
        status = g_ort->GetTensorMutableData(out_tensor, &out_raw);
        if (status != nullptr || !out_raw) {
            if (status) {
                const char* msg = g_ort->GetErrorMessage(status);
                g_last_error = msg ? msg : "GetTensorMutableData failed";
                g_ort->ReleaseStatus(status);
            }
            g_ort->ReleaseValue(out_tensor);
            return -7;
        }

        std::memcpy(output_data, out_raw, output_size * sizeof(float));
        g_ort->ReleaseValue(out_tensor);

        return 0;
    } catch (const std::exception& e) {
        g_last_error = e.what();
        return -99;
    } catch (...) {
        g_last_error = "Unknown exception in onnx_panama_run";
        return -99;
    }
}
