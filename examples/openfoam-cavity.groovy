/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

// =========================================================================
// OpenFOAM Standard Tutorial: Lid-Driven Cavity Flow (icoFoam / simpleFoam)
// Structured with SimScale Simulation Setup Taxonomy & Moqui Math Metamodel
// =========================================================================

// 1. SimScale Simulation Setup: Physical & Material Parameters
ParameterDef('nuDef', code: 'kinematicViscosity', name: 'Fluid Kinematic Viscosity',
    purpose: FluidProperty, type: NumberDecimal, defaultValue: 0.01)
ParameterDef('rhoDef', code: 'density', name: 'Fluid Density',
    purpose: FluidProperty, type: NumberDecimal, defaultValue: 1000.0)

// 2. SimScale Simulation Setup: Boundary & Operating Parameters
ParameterDef('lidVelocityDef', code: 'lidVelocityX', name: 'Lid Velocity X',
    purpose: BoundaryCondition, type: NumberDecimal, defaultValue: 1.0)

// 3. SimScale Simulation Setup: Simulation Controls & Numerics
ParameterDef('dtDef', code: 'deltaT', name: 'Time Step Size',
    purpose: SolverControl, type: NumberDecimal, defaultValue: 0.005)
ParameterDef('endTimeDef', code: 'endTime', name: 'Simulation End Time',
    purpose: SolverControl, type: NumberDecimal, defaultValue: 0.1)
ParameterDef('pTolDef', code: 'residualToleranceP', name: 'Pressure Residual Tolerance',
    purpose: NumericalScheme, type: NumberDecimal, defaultValue: 1e-6)

// 4. Mesh Spatial Bounds & Discretization Parameter Definitions
ParameterDef('xMinDef', code: 'xMin', name: 'X Min', purpose: Mesh, type: NumberDecimal, defaultValue: 0.0)
ParameterDef('xMaxDef', code: 'xMax', name: 'X Max', purpose: Mesh, type: NumberDecimal, defaultValue: 0.1)
ParameterDef('yMinDef', code: 'yMin', name: 'Y Min', purpose: Mesh, type: NumberDecimal, defaultValue: 0.0)
ParameterDef('yMaxDef', code: 'yMax', name: 'Y Max', purpose: Mesh, type: NumberDecimal, defaultValue: 0.1)
ParameterDef('zMinDef', code: 'zMin', name: 'Z Min', purpose: Mesh, type: NumberDecimal, defaultValue: 0.0)
ParameterDef('zMaxDef', code: 'zMax', name: 'Z Max', purpose: Mesh, type: NumberDecimal, defaultValue: 0.01)

ParameterDef('nxDef', code: 'nx', name: 'Cells in X', purpose: Mesh, type: NumberInteger, defaultValue: 20)
ParameterDef('nyDef', code: 'ny', name: 'Cells in Y', purpose: Mesh, type: NumberInteger, defaultValue: 20)
ParameterDef('nzDef', code: 'nz', name: 'Cells in Z', purpose: Mesh, type: NumberInteger, defaultValue: 1)

ParameterDef('gradingXDef', code: 'gradingX', name: 'Grading X', purpose: Mesh, type: NumberDecimal, defaultValue: 1.0)
ParameterDef('gradingYDef', code: 'gradingY', name: 'Grading Y', purpose: Mesh, type: NumberDecimal, defaultValue: 2.0)
ParameterDef('gradingZDef', code: 'gradingZ', name: 'Grading Z', purpose: Mesh, type: NumberDecimal, defaultValue: 1.0)

// 5. Domain & Mesh Discretization: Local Geometric Grading / Refinement
Graph('CavityGraph', description: 'Discrete topology graph for cavity mesh')

Mesh('CavityMesh', graphId: 'CavityGraph', type: Hexahedral, purpose: CFD,
    adaptation: RRefinement,
    description: 'Hexahedral block mesh with geometric grading towards walls for boundary layer resolution')

// Boundary patches modeled as MeshGroup entities (SimScale / OpenFOAM patches)
MeshGroup('movingWall', meshId: 'CavityMesh', groupName: 'movingWall', description: 'Top moving lid patch')
MeshGroup('fixedWalls', meshId: 'CavityMesh', groupName: 'fixedWalls', description: 'Side and bottom stationary no-slip walls')
MeshGroup('frontAndBack', meshId: 'CavityMesh', groupName: 'frontAndBack', description: '2D symmetry empty boundary patches')

// 6. Concrete Simulation Model Instance (icoFoam transient laminar solver)
MathModelDef('IncompressibleCavityFlow', type: CFD,
    description: 'Standard OpenFOAM Lid-Driven Cavity benchmark for laminar incompressible flow') {

    pipeline('IcoFoamStep', stepSeqId: '01', sequenceNum: 1, stepName: 'IcoFoamSolve',
        method: OpenFoamIcoFoam)

    MathModel('CavityIcoFoam', meshId: 'CavityMesh', status: Draft,
        description: 'Transient laminar incompressible solver instance for cavity') {

        // Bind physical & numerical parameters
        parameters('Param.nu', def: 'nuDef', alias: 'nu', value: 0.01)
        parameters('Param.rho', def: 'rhoDef', alias: 'rho', value: 1000.0)
        parameters('Param.lidVelocity', def: 'lidVelocityDef', alias: 'lidVelocity', value: 1.0)
        parameters('Param.deltaT', def: 'dtDef', alias: 'deltaT', value: 0.005)
        parameters('Param.endTime', def: 'endTimeDef', alias: 'endTime', value: 0.1)
        parameters('Param.pTolerance', def: 'pTolDef', alias: 'pTolerance', value: 1e-6)

        // Mesh spatial bounds and cell division parameters
        parameters('Mesh.xMin', def: 'xMinDef', alias: 'xMin', value: 0.0)
        parameters('Mesh.xMax', def: 'xMaxDef', alias: 'xMax', value: 0.1)
        parameters('Mesh.yMin', def: 'yMinDef', alias: 'yMin', value: 0.0)
        parameters('Mesh.yMax', def: 'yMaxDef', alias: 'yMax', value: 0.1)
        parameters('Mesh.zMin', def: 'zMinDef', alias: 'zMin', value: 0.0)
        parameters('Mesh.zMax', def: 'zMaxDef', alias: 'zMax', value: 0.01)
        parameters('Mesh.nx', def: 'nxDef', alias: 'nx', value: 20)
        parameters('Mesh.ny', def: 'nyDef', alias: 'ny', value: 20)
        parameters('Mesh.nz', def: 'nzDef', alias: 'nz', value: 1)

        // Local grading refinement: grading towards walls
        parameters('Mesh.gradingX', def: 'gradingXDef', alias: 'gradingX', value: 1.0)
        parameters('Mesh.gradingY', def: 'gradingYDef', alias: 'gradingY', value: 2.0)
        parameters('Mesh.gradingZ', def: 'gradingZDef', alias: 'gradingZ', value: 1.0)
    }
}

// 7. Standalone Incompressible FVM Model Instance (built-in finite volume)
MathModelDef('IncompressibleCavityFlowFvm', type: CFD,
    description: 'Built-in Finite Volume Method benchmark for laminar incompressible flow') {

    pipeline('FvmStep', stepSeqId: '01', sequenceNum: 1, stepName: 'FvmSolve', method: Fvm)

    MathModel('CavityFvm', meshId: 'CavityMesh', status: Draft,
        description: 'Transient laminar incompressible solver instance using built-in FVM') {

        // Bind physical & numerical parameters
        parameters('Param.Fvm.nu', def: 'nuDef', alias: 'nu', value: 0.01)
        parameters('Param.Fvm.rho', def: 'rhoDef', alias: 'rho', value: 1000.0)
        parameters('Param.Fvm.lidVelocity', def: 'lidVelocityDef', alias: 'lidVelocity', value: 1.0)
        parameters('Param.Fvm.deltaT', def: 'dtDef', alias: 'deltaT', value: 0.005)
        parameters('Param.Fvm.endTime', def: 'endTimeDef', alias: 'endTime', value: 0.1)
        parameters('Param.Fvm.pTolerance', def: 'pTolDef', alias: 'pTolerance', value: 1e-4)

        // Mesh spatial bounds and cell division parameters
        parameters('Mesh.Fvm.xMin', def: 'xMinDef', alias: 'xMin', value: 0.0)
        parameters('Mesh.Fvm.xMax', def: 'xMaxDef', alias: 'xMax', value: 0.1)
        parameters('Mesh.Fvm.yMin', def: 'yMinDef', alias: 'yMin', value: 0.0)
        parameters('Mesh.Fvm.yMax', def: 'yMaxDef', alias: 'yMax', value: 0.1)
        parameters('Mesh.Fvm.zMin', def: 'zMinDef', alias: 'zMin', value: 0.0)
        parameters('Mesh.Fvm.zMax', def: 'zMaxDef', alias: 'zMax', value: 0.01)
        parameters('Mesh.Fvm.nx', def: 'nxDef', alias: 'nx', value: 20)
        parameters('Mesh.Fvm.ny', def: 'nyDef', alias: 'ny', value: 20)
        parameters('Mesh.Fvm.nz', def: 'nzDef', alias: 'nz', value: 1)

        // Local grading refinement: grading towards walls
        parameters('Mesh.Fvm.gradingX', def: 'gradingXDef', alias: 'gradingX', value: 1.0)
        parameters('Mesh.Fvm.gradingY', def: 'gradingYDef', alias: 'gradingY', value: 2.0)
        parameters('Mesh.Fvm.gradingZ', def: 'gradingZDef', alias: 'gradingZ', value: 1.0)
    }
}
