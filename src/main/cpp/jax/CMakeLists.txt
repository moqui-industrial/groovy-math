cmake_minimum_required(VERSION 3.18 FATAL_ERROR)
project(groovy_math_jax LANGUAGES CXX)

set(Python3_ROOT_DIR "/home/igor/miniconda3")
find_package(Python3 COMPONENTS Development.Embed NumPy REQUIRED)

add_library(groovy_math_jax SHARED jax_panama.cpp)
target_include_directories(groovy_math_jax PRIVATE ${Python3_INCLUDE_DIRS} ${Python3_NumPy_INCLUDE_DIRS})
target_link_libraries(groovy_math_jax PRIVATE Python3::Python Python3::NumPy)
target_compile_features(groovy_math_jax PRIVATE cxx_std_17)
set_target_properties(groovy_math_jax PROPERTIES CXX_EXTENSIONS OFF)
