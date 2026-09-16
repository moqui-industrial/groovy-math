/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import org.moqui.math.dsl.MathModelType
import org.moqui.math.dsl.MathModelSolvingMethod
import org.moqui.math.dsl.MeshType
import org.moqui.math.dsl.MeshPurpose
import org.moqui.math.dsl.MeshAdaptationType
import org.moqui.math.dsl.ParameterPurpose
import org.moqui.math.dsl.ParameterType

// =========================================================================
// OpenFOAM Standard Tutorial: Lid-Driven Cavity Flow (icoFoam / simpleFoam)
// Structured with SimScale Simulation Setup Taxonomy & Moqui Math Metamodel
// =========================================================================

// -------------------------------------------------------------------------
// 1. SimScale Simulation Setup: Physical & Material Parameters
// -------------------------------------------------------------------------
ParameterDef('nuDef',
    parameterCode: 'kinematicViscosity',
    parameterName: 'Fluid Kinematic Viscosity',
    purposeEnum: ParameterPurpose.FluidProperty,
    parameterTypeEnum: ParameterType.NumberDecimal,
    defaultValue: 0.01) // m^2/s

ParameterDef('rhoDef',
    parameterCode: 'density',
    parameterName: 'Fluid Density',
    purposeEnum: ParameterPurpose.FluidProperty,
    parameterTypeEnum: ParameterType.NumberDecimal,
    defaultValue: 1000.0) // kg/m^3

// -------------------------------------------------------------------------
// 2. SimScale Simulation Setup: Boundary & Operating Parameters
// -------------------------------------------------------------------------
ParameterDef('lidVelocityDef',
    parameterCode: 'lidVelocityX',
    parameterName: 'Lid Velocity X',
    purposeEnum: ParameterPurpose.BoundaryCondition,
    parameterTypeEnum: ParameterType.NumberDecimal,
    defaultValue: 1.0) // m/s (movingWall)

// -------------------------------------------------------------------------
// 3. SimScale Simulation Setup: Simulation Controls & Numerics
// -------------------------------------------------------------------------
ParameterDef('dtDef',
    parameterCode: 'deltaT',
    parameterName: 'Time Step Size',
    purposeEnum: ParameterPurpose.SolverControl,
    parameterTypeEnum: ParameterType.NumberDecimal,
    defaultValue: 0.005)

ParameterDef('endTimeDef',
    parameterCode: 'endTime',
    parameterName: 'Simulation End Time',
    purposeEnum: ParameterPurpose.SolverControl,
    parameterTypeEnum: ParameterType.NumberDecimal,
    defaultValue: 0.1)

ParameterDef('pTolDef',
    parameterCode: 'residualToleranceP',
    parameterName: 'Pressure Residual Tolerance',
    purposeEnum: ParameterPurpose.NumericalScheme,
    parameterTypeEnum: ParameterType.NumberDecimal,
    defaultValue: 1e-6)

// -------------------------------------------------------------------------
// 4. Mesh Spatial Bounds & Discretization Parameter Definitions
// -------------------------------------------------------------------------
ParameterDef('xMinDef', parameterCode: 'xMin', parameterName: 'X Min', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberDecimal, defaultValue: 0.0)
ParameterDef('xMaxDef', parameterCode: 'xMax', parameterName: 'X Max', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberDecimal, defaultValue: 0.1)
ParameterDef('yMinDef', parameterCode: 'yMin', parameterName: 'Y Min', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberDecimal, defaultValue: 0.0)
ParameterDef('yMaxDef', parameterCode: 'yMax', parameterName: 'Y Max', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberDecimal, defaultValue: 0.1)
ParameterDef('zMinDef', parameterCode: 'zMin', parameterName: 'Z Min', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberDecimal, defaultValue: 0.0)
ParameterDef('zMaxDef', parameterCode: 'zMax', parameterName: 'Z Max', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberDecimal, defaultValue: 0.01)

ParameterDef('nxDef', parameterCode: 'nx', parameterName: 'Cells in X', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberInteger, defaultValue: 20)
ParameterDef('nyDef', parameterCode: 'ny', parameterName: 'Cells in Y', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberInteger, defaultValue: 20)
ParameterDef('nzDef', parameterCode: 'nz', parameterName: 'Cells in Z', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberInteger, defaultValue: 1)

ParameterDef('gradingXDef', parameterCode: 'gradingX', parameterName: 'Grading X', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberDecimal, defaultValue: 1.0)
ParameterDef('gradingYDef', parameterCode: 'gradingY', parameterName: 'Grading Y', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberDecimal, defaultValue: 2.0)
ParameterDef('gradingZDef', parameterCode: 'gradingZ', parameterName: 'Grading Z', purposeEnum: ParameterPurpose.Mesh, parameterTypeEnum: ParameterType.NumberDecimal, defaultValue: 1.0)

// -------------------------------------------------------------------------
// 5. Domain & Mesh Discretization: Local Geometric Grading / Refinement
// -------------------------------------------------------------------------
Graph('CavityGraph', description: 'Discrete topology graph for cavity mesh')

Mesh('CavityMesh',
    graphId: 'CavityGraph',
    meshTypeEnumId: 'MtHexahedral',
    purposeEnumId: 'MpCFD',
    adaptationTypeEnumId: 'MatRRefinement',
    description: 'Hexahedral block mesh with geometric grading towards walls for boundary layer resolution')

// Boundary patches modeled as MeshGroup entities (SimScale / OpenFOAM patches)
MeshGroup('movingWall', meshId: 'CavityMesh', groupName: 'movingWall', description: 'Top moving lid patch')
MeshGroup('fixedWalls', meshId: 'CavityMesh', groupName: 'fixedWalls', description: 'Side and bottom stationary no-slip walls')
MeshGroup('frontAndBack', meshId: 'CavityMesh', groupName: 'frontAndBack', description: '2D symmetry empty boundary patches')

// -------------------------------------------------------------------------
// 6. Concrete Simulation Model Instance (icoFoam transient laminar solver)
// -------------------------------------------------------------------------
MathModelDef('IncompressibleCavityFlow', modelTypeEnum: MathModelType.CFD) {
    description 'Standard OpenFOAM Lid-Driven Cavity benchmark for laminar incompressible flow'

    pipeline('IcoFoamStep', stepSeqId: '01', sequenceNum: 1, stepName: 'IcoFoamSolve',
        solvingMethodEnum: MathModelSolvingMethod.OpenFoamIcoFoam)

    MathModel('CavityIcoFoam',
        meshId: 'CavityMesh',
        statusId: 'MathModelDraft') {
        description 'Transient laminar incompressible solver instance for cavity'

        // Bind physical & numerical parameters
        parameters('Param.nu', parameterDefId: 'nuDef', parameterAlias: 'nu', numericValue: 0.01)
        parameters('Param.rho', parameterDefId: 'rhoDef', parameterAlias: 'rho', numericValue: 1000.0)
        parameters('Param.lidVelocity', parameterDefId: 'lidVelocityDef', parameterAlias: 'lidVelocity', numericValue: 1.0)
        parameters('Param.deltaT', parameterDefId: 'dtDef', parameterAlias: 'deltaT', numericValue: 0.005)
        parameters('Param.endTime', parameterDefId: 'endTimeDef', parameterAlias: 'endTime', numericValue: 0.1)
        parameters('Param.pTolerance', parameterDefId: 'pTolDef', parameterAlias: 'pTolerance', numericValue: 1e-6)

        // Mesh spatial bounds and cell division parameters
        parameters('Mesh.xMin', parameterDefId: 'xMinDef', parameterAlias: 'xMin', numericValue: 0.0)
        parameters('Mesh.xMax', parameterDefId: 'xMaxDef', parameterAlias: 'xMax', numericValue: 0.1)
        parameters('Mesh.yMin', parameterDefId: 'yMinDef', parameterAlias: 'yMin', numericValue: 0.0)
        parameters('Mesh.yMax', parameterDefId: 'yMaxDef', parameterAlias: 'yMax', numericValue: 0.1)
        parameters('Mesh.zMin', parameterDefId: 'zMinDef', parameterAlias: 'zMin', numericValue: 0.0)
        parameters('Mesh.zMax', parameterDefId: 'zMaxDef', parameterAlias: 'zMax', numericValue: 0.01)
        parameters('Mesh.nx', parameterDefId: 'nxDef', parameterAlias: 'nx', numericValue: 20)
        parameters('Mesh.ny', parameterDefId: 'nyDef', parameterAlias: 'ny', numericValue: 20)
        parameters('Mesh.nz', parameterDefId: 'nzDef', parameterAlias: 'nz', numericValue: 1)

        // Local grading refinement: grading towards walls
        parameters('Mesh.gradingX', parameterDefId: 'gradingXDef', parameterAlias: 'gradingX', numericValue: 1.0)
        parameters('Mesh.gradingY', parameterDefId: 'gradingYDef', parameterAlias: 'gradingY', numericValue: 2.0)
        parameters('Mesh.gradingZ', parameterDefId: 'gradingZDef', parameterAlias: 'gradingZ', numericValue: 1.0)
    }
}
