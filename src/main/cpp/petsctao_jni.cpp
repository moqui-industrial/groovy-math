/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

#include <jni.h>
#include <petsctao.h>

#include <algorithm>
#include <cmath>
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
        PetscBool initialized = PETSC_FALSE;
        PetscErrorCode code = PetscInitialized(&initialized);
        if (code != PETSC_SUCCESS) throw std::runtime_error("PetscInitialized failed");
        if (!initialized) {
            code = PetscInitializeNoArguments();
            if (code != PETSC_SUCCESS) throw std::runtime_error("PetscInitialize failed");
        }
    });
}

void check(PetscErrorCode code, const char* operation) {
    if (code != PETSC_SUCCESS) {
        throw std::runtime_error(std::string(operation) + " failed with PETSc error " +
                                 std::to_string(static_cast<long long>(code)));
    }
}

void throw_java(JNIEnv* env, const std::exception& error) {
    jclass type = env->FindClass("java/lang/IllegalStateException");
    if (type != nullptr) env->ThrowNew(type, error.what());
}

std::vector<PetscScalar> scalars(JNIEnv* env, jdoubleArray array, jsize expected,
                                 const char* label) {
    if (array == nullptr) throw std::invalid_argument(std::string(label) + " must not be null");
    jsize size = env->GetArrayLength(array);
    if (size != expected) throw std::invalid_argument(std::string(label) + " has invalid length");
    std::vector<double> raw(static_cast<std::size_t>(size));
    env->GetDoubleArrayRegion(array, 0, size, raw.data());
    if (env->ExceptionCheck()) throw std::runtime_error(std::string("could not read ") + label);
    std::vector<PetscScalar> result(raw.size());
    std::transform(raw.begin(), raw.end(), result.begin(),
                   [](double value) { return static_cast<PetscScalar>(value); });
    return result;
}

Plan& plan(jlong handle) {
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

}

extern "C" {

JNIEXPORT jlong JNICALL
Java_org_moqui_math_petsctao_PetscTaoBindings_nativeCreateBoundedQuadraticPlan(
        JNIEnv* env, jclass, jint dimension, jdoubleArray hessian_array,
        jdoubleArray linear_array, jdoubleArray lower_array, jdoubleArray upper_array,
        jdoubleArray initial_array) {
    try {
        ensure_petsc();
        if (dimension <= 0) throw std::invalid_argument("dimension must be positive");
        auto result = std::make_unique<Plan>();
        result->dimension = static_cast<PetscInt>(dimension);
        result->hessian = scalars(env, hessian_array, dimension * dimension, "Hessian");
        result->linear = scalars(env, linear_array, dimension, "linear vector");
        result->lower = scalars(env, lower_array, dimension, "lower bounds");
        result->upper = scalars(env, upper_array, dimension, "upper bounds");
        result->initial = scalars(env, initial_array, dimension, "initial point");
        return reinterpret_cast<jlong>(result.release());
    } catch (const std::exception& error) {
        throw_java(env, error);
        return 0;
    }
}

JNIEXPORT jdoubleArray JNICALL
Java_org_moqui_math_petsctao_PetscTaoBindings_nativeSolve(
        JNIEnv* env, jclass, jlong handle) {
    try {
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
        std::vector<double> encoded(static_cast<std::size_t>(target.dimension + 4));
        for (PetscInt index = 0; index < target.dimension; ++index) {
            encoded[static_cast<std::size_t>(index)] =
                static_cast<double>(PetscRealPart(solution_values[index]));
        }
        check(VecRestoreArrayRead(objects.solution, &solution_values),
              "VecRestoreArrayRead solution");
        encoded[static_cast<std::size_t>(target.dimension)] = static_cast<double>(objective);
        encoded[static_cast<std::size_t>(target.dimension + 1)] = static_cast<double>(gradient_norm);
        encoded[static_cast<std::size_t>(target.dimension + 2)] = static_cast<double>(iterations);
        encoded[static_cast<std::size_t>(target.dimension + 3)] = static_cast<double>(reason);

        jdoubleArray result = env->NewDoubleArray(static_cast<jsize>(encoded.size()));
        if (result == nullptr) throw std::runtime_error("could not allocate Java result array");
        env->SetDoubleArrayRegion(result, 0, static_cast<jsize>(encoded.size()), encoded.data());
        if (env->ExceptionCheck()) return nullptr;
        return result;
    } catch (const std::exception& error) {
        throw_java(env, error);
        return nullptr;
    }
}

JNIEXPORT void JNICALL
Java_org_moqui_math_petsctao_PetscTaoBindings_nativeDestroy(
        JNIEnv*, jclass, jlong handle) {
    delete reinterpret_cast<Plan*>(handle);
}

}
