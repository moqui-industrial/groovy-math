cmake_minimum_required(VERSION 3.18 FATAL_ERROR)
project(groovy_math_libtorch LANGUAGES CXX)

find_package(JNI REQUIRED)
find_package(Torch REQUIRED)

add_library(groovy_math_libtorch SHARED libtorch_jni.cpp libtorch_panama.cpp)
target_include_directories(groovy_math_libtorch PRIVATE ${JNI_INCLUDE_DIRS})
target_link_libraries(groovy_math_libtorch PRIVATE ${TORCH_LIBRARIES})
target_compile_options(groovy_math_libtorch PRIVATE ${TORCH_CXX_FLAGS})
target_compile_features(groovy_math_libtorch PRIVATE cxx_std_20)
set_target_properties(groovy_math_libtorch PROPERTIES
    CXX_EXTENSIONS OFF
    BUILD_RPATH "${TORCH_INSTALL_PREFIX}/lib"
    INSTALL_RPATH "${TORCH_INSTALL_PREFIX}/lib")
