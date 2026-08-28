/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

#include <petsctao.h>

#include <algorithm>
#include <cmath>
#include <cstdint>
#include <cstdlib>
#include <memory>
#include <mutex>
#include <stdexcept>
#include <string>
#include <vector>

#if defined(PETSC_USE_COMPLEX)
#error "groovy-math PETSc/TAO provider requires a real-scalar PETSc build"
#endif

namespace {

struct Plan {
    PetscInt dimension;
    std::vector<PetscScalar> hessian;
    std::vector<PetscScalar> linear;
    std::vector<PetscScalar> lower;
    std::vector<PetscScalar> upper;
    std::vector<PetscScalar> initial;
};

std::once_flag petsc_initialization;
std::mutex petsc_execution_mutex;

void ensure_petsc() {
    std::call_once(petsc_initialization, [] {
        // Prevent hwloc in OpenMPI from hanging on unresponsive X11 display sockets
        setenv("HWLOC_COMPONENTS", "-gl", 0);

        PetscBool initialized = PETSC_FALSE;
        PetscErrorCode code = PetscInitialized(&initialized);
        if (code != PETSC_SUCCESS) return;
        if (!initialized) {
            int argc = 2;
            char arg0[] = "groovy-math";
            char arg1[] = "-no_signal_handler";
            char* argv[] = {arg0, arg1, nullptr};
            char** pargv = argv;
            PetscInitialize(&argc, &pargv, nullptr, nullptr);
            PetscPopSignalHandler();
        }
    });
}

void check(PetscErrorCode code, const char* operation) {
    if (code != PETSC_SUCCESS) {
        throw std::runtime_error(std::string(operation) + " failed with PETSc error " +
                                 std::to_string(static_cast<long long>(code)));
    }
}

Plan& plan(int64_t handle) {
    if (handle == 0) throw std::invalid_argument("native plan handle is zero");
    return *reinterpret_cast<Plan*>(handle);
}

PetscErrorCode objective_gradient(Tao, Vec x, PetscReal* objective, Vec gradient,
                                  void* context) {
    auto* target = static_cast<Plan*>(context);
    const PetscScalar* x_values = nullptr;
    PetscScalar* gradient_values = nullptr;
    PetscCall(VecGetArrayRead(x, &x_values));
    PetscCall(VecGetArrayWrite(gradient, &gradient_values));

    PetscReal value = 0.0;
    for (PetscInt row = 0; row < target->dimension; ++row) {
        PetscScalar qx = 0.0;
        for (PetscInt column = 0; column < target->dimension; ++column) {
            qx += target->hessian[static_cast<std::size_t>(row * target->dimension + column)] *
                  x_values[column];
        }
        gradient_values[row] = qx + target->linear[static_cast<std::size_t>(row)];
        value += PetscRealPart(0.5 * x_values[row] * qx +
                               target->linear[static_cast<std::size_t>(row)] * x_values[row]);
    }
    *objective = value;
    PetscCall(VecRestoreArrayWrite(gradient, &gradient_values));
    PetscCall(VecRestoreArrayRead(x, &x_values));
    return PETSC_SUCCESS;
}

PetscErrorCode hessian(Tao, Vec, Mat matrix, Mat preconditioner, void* context) {
    auto* target = static_cast<Plan*>(context);
    PetscCall(MatZeroEntries(matrix));
    for (PetscInt row = 0; row < target->dimension; ++row) {
        for (PetscInt column = 0; column < target->dimension; ++column) {
            PetscScalar value = target->hessian[static_cast<std::size_t>(row * target->dimension + column)];
            PetscCall(MatSetValue(matrix, row, column, value, INSERT_VALUES));
        }
    }
    PetscCall(MatAssemblyBegin(matrix, MAT_FINAL_ASSEMBLY));
    PetscCall(MatAssemblyEnd(matrix, MAT_FINAL_ASSEMBLY));
    if (preconditioner != matrix) {
        PetscCall(MatCopy(matrix, preconditioner, SAME_NONZERO_PATTERN));
    }
    return PETSC_SUCCESS;
}

struct Objects {
    Tao tao = nullptr;
    Vec solution = nullptr;
    Vec gradient = nullptr;
    Vec lower = nullptr;
    Vec upper = nullptr;
    Mat hessian = nullptr;

    ~Objects() {
        if (tao != nullptr) TaoDestroy(&tao);
        if (hessian != nullptr) MatDestroy(&hessian);
        if (upper != nullptr) VecDestroy(&upper);
        if (lower != nullptr) VecDestroy(&lower);
        if (gradient != nullptr) VecDestroy(&gradient);
        if (solution != nullptr) VecDestroy(&solution);
    }
};

void fill_vector(Vec vector, const std::vector<PetscScalar>& values) {
    PetscScalar* target = nullptr;
    check(VecGetArrayWrite(vector, &target), "VecGetArrayWrite");
    std::copy(values.begin(), values.end(), target);
    check(VecRestoreArrayWrite(vector, &target), "VecRestoreArrayWrite");
}

} // namespace

extern "C" {

int64_t petsc_panama_create_bounded_quadratic_plan(
        int32_t dimension,
        const double* hessian_data,
        const double* linear_data,
        const double* lower_data,
        const double* upper_data,
        const double* initial_data) {
    try {
        ensure_petsc();
        if (dimension <= 0) return 0;
        if (!hessian_data || !linear_data || !lower_data || !upper_data || !initial_data) return 0;

        auto result = std::make_unique<Plan>();
        result->dimension = static_cast<PetscInt>(dimension);
        result->hessian.assign(hessian_data, hessian_data + dimension * dimension);
        result->linear.assign(linear_data, linear_data + dimension);
        result->lower.assign(lower_data, lower_data + dimension);
        result->upper.assign(upper_data, upper_data + dimension);
        result->initial.assign(initial_data, initial_data + dimension);

        return reinterpret_cast<int64_t>(result.release());
    } catch (...) {
        return 0;
    }
}

int32_t petsc_panama_solve(int64_t handle, double* out_solution_and_meta) {
    try {
        if (handle == 0 || !out_solution_and_meta) return -1;
        ensure_petsc();
        std::lock_guard<std::mutex> execution_guard(petsc_execution_mutex);
        Plan& target = plan(handle);
        Objects objects;

        check(VecCreateSeq(PETSC_COMM_SELF, target.dimension, &objects.solution), "VecCreateSeq");
        check(VecDuplicate(objects.solution, &objects.gradient), "VecDuplicate gradient");
        check(VecDuplicate(objects.solution, &objects.lower), "VecDuplicate lower bounds");
        check(VecDuplicate(objects.solution, &objects.upper), "VecDuplicate upper bounds");
        fill_vector(objects.solution, target.initial);
        fill_vector(objects.lower, target.lower);
        fill_vector(objects.upper, target.upper);

        check(MatCreateSeqDense(PETSC_COMM_SELF, target.dimension, target.dimension,
                                nullptr, &objects.hessian), "MatCreateSeqDense");
        check(hessian(nullptr, nullptr, objects.hessian, objects.hessian, &target),
              "Hessian assembly");

        check(TaoCreate(PETSC_COMM_SELF, &objects.tao), "TaoCreate");
        check(TaoSetType(objects.tao, TAOBQPIP), "TaoSetType BQPIP");
        check(TaoSetSolution(objects.tao, objects.solution), "TaoSetSolution");
        check(TaoSetVariableBounds(objects.tao, objects.lower, objects.upper),
              "TaoSetVariableBounds");
        check(TaoSetObjectiveAndGradient(objects.tao, objects.gradient,
                                         objective_gradient, &target),
              "TaoSetObjectiveAndGradient");
        check(TaoSetHessian(objects.tao, objects.hessian, objects.hessian,
                            hessian, &target), "TaoSetHessian");
        check(TaoSetFromOptions(objects.tao), "TaoSetFromOptions");
        check(TaoSolve(objects.tao), "TaoSolve");

        PetscInt iterations = 0;
        PetscReal objective = 0.0;
        PetscReal gradient_norm = 0.0;
        TaoConvergedReason reason = TAO_CONTINUE_ITERATING;
        check(TaoGetSolutionStatus(objects.tao, &iterations, &objective, &gradient_norm,
                                   nullptr, nullptr, &reason), "TaoGetSolutionStatus");

        const PetscScalar* solution_values = nullptr;
        check(VecGetArrayRead(objects.solution, &solution_values), "VecGetArrayRead solution");
        for (PetscInt index = 0; index < target.dimension; ++index) {
            out_solution_and_meta[index] = static_cast<double>(PetscRealPart(solution_values[index]));
        }
        check(VecRestoreArrayRead(objects.solution, &solution_values),
              "VecRestoreArrayRead solution");

        out_solution_and_meta[target.dimension] = static_cast<double>(objective);
        out_solution_and_meta[target.dimension + 1] = static_cast<double>(gradient_norm);
        out_solution_and_meta[target.dimension + 2] = static_cast<double>(iterations);
        out_solution_and_meta[target.dimension + 3] = static_cast<double>(reason);

        return 0; // Success
    } catch (...) {
        return -1;
    }
}

void petsc_panama_destroy(int64_t handle) {
    if (handle != 0) {
        delete reinterpret_cast<Plan*>(handle);
    }
}

} // extern "C"
