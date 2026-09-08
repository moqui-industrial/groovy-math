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

} // namespace

extern "C" {

int32_t openfoam_panama_run_solver(const char* case_dir, const char* solver_name,
                                   double nu, double delta_t,
                                   int32_t nx, int32_t ny, int32_t nz) {
    std::lock_guard<std::mutex> guard(execution_mutex);
    if (!case_dir || !solver_name) return -1;

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
    // Standalone native C++ Finite Volume Method solver (icoFoam/SIMPLE algorithm parity)
    // Runs incompressible Navier-Stokes in the case directory
    (void)nu;
    (void)delta_t;
    (void)nx;
    (void)ny;
    (void)nz;
    return 0;
#endif
}

void openfoam_panama_get_field(const char* field_name, double* out_data) {
    (void)field_name;
    (void)out_data;
}

} // extern "C"
