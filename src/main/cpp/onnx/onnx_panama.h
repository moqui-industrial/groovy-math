#ifndef GROOVY_MATH_ONNX_PANAMA_H
#define GROOVY_MATH_ONNX_PANAMA_H

#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int onnx_panama_is_available(void);
const char* onnx_panama_last_error(void);
int64_t onnx_panama_create_session(const char* model_path);
void onnx_panama_destroy_session(int64_t session_handle);
int64_t onnx_panama_run(int64_t session_handle,
                        const char* input_name,
                        const float* input_data,
                        const int64_t* input_shape,
                        int64_t input_rank,
                        const char* output_name,
                        float* output_data,
                        int64_t output_size,
                        int64_t output_capacity_bytes);

#ifdef __cplusplus
}
#endif

#endif // GROOVY_MATH_ONNX_PANAMA_H
