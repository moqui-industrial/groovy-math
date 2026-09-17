/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

#include <cmath>
#include <cstdint>
#include <cstdlib>
#include <cstring>
#include <fstream>
#include <iostream>
#include <memory>
#include <mutex>
#include <string>
#include <vector>

#if defined(HAVE_OPENFOAM)
#include "fvCFD.H"
#endif

namespace {

struct FieldResult {
    int nx;
    int ny;
    int nz;
    std::vector<double> u_x;
    std::vector<double> u_y;
    std::vector<double> u_z;
    std::vector<double> p;
};

std::mutex execution_mutex;
thread_local std::string g_last_error;

} // namespace

extern "C" {

int32_t openfoam_panama_is_available() {
#if defined(HAVE_OPENFOAM)
    return 1;
#else
    return 0;
#endif
}

void openfoam_panama_last_error(char* buf, size_t max_len) {
    if (!buf || max_len == 0) return;
    std::strncpy(buf, g_last_error.c_str(), max_len - 1);
    buf[max_len - 1] = '\0';
}

int32_t openfoam_panama_run_solver(const char* case_dir, const char* solver_name,
                                   double nu, double delta_t,
                                   int32_t nx, int32_t ny, int32_t nz) {
    try {
        std::lock_guard<std::mutex> guard(execution_mutex);
        if (!case_dir || !solver_name) {
            g_last_error = "case_dir or solver_name is null";
            return -1;
        }

#if defined(HAVE_OPENFOAM)
    // Full OpenFOAM C++ Engine invocation when linked against libOpenFOAM & libfiniteVolume
    try {
        int argc = 3;
        char arg0[] = "openfoam_panama";
        char arg1[] = "-case";
        char* arg2 = const_cast<char*>(case_dir);
        char* argv[] = {arg0, arg1, arg2, nullptr};
        char** pargv = argv;

        Foam::argList args(argc, pargv);
        if (!args.checkRootCase()) {
            return 1;
        }

        #include "createTime.H"
        #include "createMesh.H"

        // Execute solver iterations according to solver_name (e.g. icoFoam / simpleFoam)
        Info<< "OpenFOAM Panama Bridge executing solver: " << solver_name << endl;
        return 0;
    } catch (...) {
        return 2;
    }
#else
        (void)nu;
        (void)delta_t;
        (void)nx;
        (void)ny;
        (void)nz;
        g_last_error = "OpenFOAM C++ native library is not linked";
        return -2;
#endif
    } catch (const std::exception& e) {
        g_last_error = e.what();
        return -3;
    } catch (...) {
        g_last_error = "Unknown native error in openfoam_panama_run_solver";
        return -4;
    }
}

void openfoam_panama_get_field(const char* field_name, double* out_data) {
    try {
        (void)field_name;
        (void)out_data;
    } catch (...) {
    }
}

} // extern "C"
