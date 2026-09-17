cmake_minimum_required(VERSION 3.18 FATAL_ERROR)
project(groovy_math_onnx LANGUAGES CXX)

add_library(groovy_math_onnx SHARED onnx_panama.cpp)
target_include_directories(groovy_math_onnx PRIVATE ${CMAKE_CURRENT_SOURCE_DIR})
target_link_libraries(groovy_math_onnx PRIVATE ${CMAKE_DL_LIBS})
target_compile_features(groovy_math_onnx PRIVATE cxx_std_17)
set_target_properties(groovy_math_onnx PROPERTIES CXX_EXTENSIONS OFF)
