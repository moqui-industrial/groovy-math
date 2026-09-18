/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelSolvingMethod
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelSolvingMethod implements DslEnumValue {
    Fem('MmsmFem', '', 'Finite Element Method - FEM', ''),
    Fvm('MmsmFvm', '', 'Finite Volume Method - FVM', ''),
    Fdm('MmsmFdm', '', 'Finite Difference Method - FDM', ''),
    Dg('MmsmDg', '', 'Discontinuous Galerkin Method - DG', ''),
    Sem('MmsmSem', '', 'Spectral Element Method - SEM', ''),
    Meshless('MmsmMeshless', '', 'Meshless', ''),
    Simplex('MmsmSimplex', '', 'Simplex / Revised Simplex', ''),
    InteriorPoint('MmsmInteriorPoint', '', 'Interior-Point (Barrier/Primal-Dual)', ''),
    BranchAndBound('MmsmBranchAndBound', '', 'Branch-and-Bound / Branch-and-Cut', ''),
    CuttingPlane('MmsmCuttingPlane', '', 'Cutting Plane Methods', ''),
    AugLag('MmsmAugLag', '', 'Augmented Lagrangian / Penalty', ''),
    Admm('MmsmAdmm', '', 'ADMM', ''),
    HeuristicGA('MmsmHeuristicGA', '', 'Genetic / Evolutionary Heuristics', ''),
    SimAnn('MmsmSimAnn', '', 'Simulated Annealing', ''),
    Tabu('MmsmTabu', '', 'Tabu Search', ''),
    OpenCv('MmsmOpenCv', '', 'OpenCV Computer Vision Runtime', ''),
    Onnx('MmsmOnnx', '', 'ONNX Runtime Neural Network Inference Engine', ''),
    Jax('MmsmJax', '', 'Google JAX / OpenXLA Runtime', ''),
    JaxJit('MmsmJaxJit', '', 'Google JAX XLA JIT Compiled / Autograd Runtime', 'MmsmJax'),
    LibTorch('MmsmLibTorch', '', 'LibTorch Native Runtime', ''),
    LibTorchTraining('MmsmLibTorchTraining', '', 'LibTorch Autograd & Training Runtime', 'MmsmLibTorch'),
    PetscTao('MmsmPetscTao', '', 'PETSc / TAO Numerical Optimizer', ''),
    OrTools('MmsmOrTools', '', 'Google OR-Tools Mathematical Optimization', ''),
    OpenFoam('MmsmOpenFoam', '', 'OpenFOAM Finite Volume CFD Engine', ''),
    OpenFoamIcoFoam('MmsmOpenFoamIcoFoam', '', 'OpenFOAM Incompressible Laminar Transient Solver', 'MmsmOpenFoam'),
    OpenFoamSimpleFoam('MmsmOpenFoamSimpleFoam', '', 'OpenFOAM Incompressible Turbulent Steady-State Solver', 'MmsmOpenFoam');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelSolvingMethod(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
        this.id = id
        this.enumCode = enumCode
        this.description = description
        this.parentEnumId = parentEnumId
    }

    @Override
    String getId() { id }

    @Override
    String getEnumCode() { enumCode }

    @Override
    String getDescription() { description }

    @Override
    String getParentEnumId() { parentEnumId }

    static MathModelSolvingMethod fromId(final String id) {
        if (id == null) return null
        for (MathModelSolvingMethod val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelSolvingMethod fromCode(final String code) {
        if (code == null) return null
        for (MathModelSolvingMethod val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MathModelSolvingMethod fromName(final String name) {
        if (name == null) return null
        for (MathModelSolvingMethod val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('FiniteElementMethodFem'.equalsIgnoreCase(name)) return Fem
        if ('FiniteVolumeMethodFvm'.equalsIgnoreCase(name)) return Fvm
        if ('FiniteDifferenceMethodFdm'.equalsIgnoreCase(name)) return Fdm
        if ('DiscontinuousGalerkinMethodDg'.equalsIgnoreCase(name)) return Dg
        if ('SpectralElementMethodSem'.equalsIgnoreCase(name)) return Sem
        if ('SimplexRevisedSimplex'.equalsIgnoreCase(name)) return Simplex
        if ('BranchAndBoundBranchAndCut'.equalsIgnoreCase(name)) return BranchAndBound
        if ('CuttingPlaneMethods'.equalsIgnoreCase(name)) return CuttingPlane
        if ('AugmentedLagrangianPenalty'.equalsIgnoreCase(name)) return AugLag
        if ('GeneticEvolutionaryHeuristics'.equalsIgnoreCase(name)) return HeuristicGA
        if ('SimulatedAnnealing'.equalsIgnoreCase(name)) return SimAnn
        if ('TabuSearch'.equalsIgnoreCase(name)) return Tabu
        if ('OpencvComputerVisionRuntime'.equalsIgnoreCase(name)) return OpenCv
        if ('OnnxRuntimeNeuralNetworkInferenceEngine'.equalsIgnoreCase(name)) return Onnx
        if ('GoogleJaxOpenxlaRuntime'.equalsIgnoreCase(name)) return Jax
        if ('GoogleJaxXlaJitCompiledAutogradRuntime'.equalsIgnoreCase(name)) return JaxJit
        if ('LibtorchNativeRuntime'.equalsIgnoreCase(name)) return LibTorch
        if ('LibtorchAutogradTrainingRuntime'.equalsIgnoreCase(name)) return LibTorchTraining
        if ('PetscTaoNumericalOptimizer'.equalsIgnoreCase(name)) return PetscTao
        if ('GoogleOrToolsMathematicalOptimization'.equalsIgnoreCase(name)) return OrTools
        if ('OpenfoamFiniteVolumeCfdEngine'.equalsIgnoreCase(name)) return OpenFoam
        if ('OpenfoamIncompressibleLaminarTransientSolver'.equalsIgnoreCase(name)) return OpenFoamIcoFoam
        if ('OpenfoamIncompressibleTurbulentSteadyStateSolver'.equalsIgnoreCase(name)) return OpenFoamSimpleFoam
        null
    }
}
