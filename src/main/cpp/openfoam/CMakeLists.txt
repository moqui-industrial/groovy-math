cmake_minimum_required(VERSION 3.18 FATAL_ERROR)
project(groovy_math_openfoam LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_POSITION_INDEPENDENT_CODE ON)

add_library(groovy_math_openfoam SHARED openfoam_panama.cpp)

if(DEFINED ENV{WM_PROJECT_DIR})
    set(OPENFOAM_DIR $ENV{WM_PROJECT_DIR})
    message(STATUS "OpenFOAM found at: ${OPENFOAM_DIR}")
    target_compile_definitions(groovy_math_openfoam PRIVATE HAVE_OPENFOAM=1)
    target_include_directories(groovy_math_openfoam PRIVATE
        ${OPENFOAM_DIR}/src/OpenFOAM/lnInclude
        ${OPENFOAM_DIR}/src/finiteVolume/lnInclude
        ${OPENFOAM_DIR}/src/meshTools/lnInclude
        ${OPENFOAM_DIR}/src/OSspecific/POSIX/lnInclude
    )
    if(DEFINED ENV{FOAM_LIBBIN})
        target_link_directories(groovy_math_openfoam PRIVATE $ENV{FOAM_LIBBIN})
    endif()
    target_link_libraries(groovy_math_openfoam PRIVATE OpenFOAM finiteVolume)
else()
    message(STATUS "OpenFOAM environment not detected; compiling standalone native C++ FVM bridge.")
endif()
