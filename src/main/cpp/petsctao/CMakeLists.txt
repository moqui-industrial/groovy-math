cmake_minimum_required(VERSION 3.18 FATAL_ERROR)
project(groovy_math_petsctao LANGUAGES CXX)

find_package(MPI REQUIRED COMPONENTS CXX)
find_package(PkgConfig REQUIRED)
pkg_check_modules(PETSC REQUIRED IMPORTED_TARGET PETSc)

add_library(groovy_math_petsctao SHARED petsctao_panama.cpp)
target_link_libraries(groovy_math_petsctao PRIVATE PkgConfig::PETSC MPI::MPI_CXX)
target_compile_features(groovy_math_petsctao PRIVATE cxx_std_17)
set_target_properties(groovy_math_petsctao PROPERTIES CXX_EXTENSIONS OFF)
