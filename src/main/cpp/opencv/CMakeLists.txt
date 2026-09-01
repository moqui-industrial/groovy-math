cmake_minimum_required(VERSION 3.18 FATAL_ERROR)
project(groovy_math_opencv LANGUAGES CXX)

# Honor an explicit interpreter root for non-standard installs (e.g. a
# dedicated conda environment); otherwise let CMake resolve `python3` from
# PATH so the build stays portable across machines and CI runners.
if(DEFINED ENV{GROOVY_MATH_PYTHON3_ROOT})
    set(Python3_ROOT_DIR "$ENV{GROOVY_MATH_PYTHON3_ROOT}")
endif()
find_package(Python3 COMPONENTS Development.Embed NumPy REQUIRED)

add_library(groovy_math_opencv SHARED opencv_panama.cpp)
target_include_directories(groovy_math_opencv PRIVATE ${Python3_INCLUDE_DIRS} ${Python3_NumPy_INCLUDE_DIRS})
target_link_libraries(groovy_math_opencv PRIVATE Python3::Python Python3::NumPy)
target_compile_features(groovy_math_opencv PRIVATE cxx_std_17)
set_target_properties(groovy_math_opencv PROPERTIES CXX_EXTENSIONS OFF)

# The embedding process must dlopen libpython itself with RTLD_GLOBAL so
# NumPy/OpenCV (cv2) C extension modules can resolve Python C-API symbols;
# pass the exact library CMake resolved so the runtime dlopen stays in step
# with the interpreter this bridge was actually built and linked against.
list(GET Python3_LIBRARIES 0 GROOVY_MATH_PYTHON_LIBRARY_PATH)
target_compile_definitions(groovy_math_opencv PRIVATE
    "GROOVY_MATH_PYTHON_LIBRARY=\"${GROOVY_MATH_PYTHON_LIBRARY_PATH}\"")
