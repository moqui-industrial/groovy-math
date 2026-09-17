parameterDef('nuDef',
    parameterCode: 'kinematicViscosity',
    parameterName: 'Fluid Kinematic Viscosity',
    purpose: FluidProperty,
    parameterType: NumberDecimal,
    defaultValue: 0.01)

parameterDef('rhoDef',
    parameterCode: 'density',
    parameterName: 'Fluid Density',
    purpose: FluidProperty,
    parameterType: NumberDecimal,
    defaultValue: 1000.0)

parameterDef('lidVelocityDef',
    parameterCode: 'lidVelocityX',
    parameterName: 'Lid Velocity X',
    purpose: ParameterPurpose.BoundaryCondition,
    parameterType: NumberDecimal,
    defaultValue: 1.0)

parameterDef('dtDef',
    parameterCode: 'deltaT',
    parameterName: 'Time Step Size',
    purpose: SolverControl,
    parameterType: NumberDecimal,
    defaultValue: 0.005)

parameterDef('endTimeDef',
    parameterCode: 'endTime',
    parameterName: 'Simulation End Time',
    purpose: SolverControl,
    parameterType: NumberDecimal,
    defaultValue: 0.1)

parameterDef('pTolDef',
    parameterCode: 'residualToleranceP',
    parameterName: 'Pressure Residual Tolerance',
    purpose: NumericalScheme,
    parameterType: NumberDecimal,
    defaultValue: 1e-6)

parameterDef('xMinDef', parameterCode: 'xMin', parameterName: 'X Min', purpose: ParameterPurpose.Mesh, parameterType: NumberDecimal, defaultValue: 0.0)
parameterDef('xMaxDef', parameterCode: 'xMax', parameterName: 'X Max', purpose: ParameterPurpose.Mesh, parameterType: NumberDecimal, defaultValue: 0.1)
parameterDef('yMinDef', parameterCode: 'yMin', parameterName: 'Y Min', purpose: ParameterPurpose.Mesh, parameterType: NumberDecimal, defaultValue: 0.0)
parameterDef('yMaxDef', parameterCode: 'yMax', parameterName: 'Y Max', purpose: ParameterPurpose.Mesh, parameterType: NumberDecimal, defaultValue: 0.1)
parameterDef('zMinDef', parameterCode: 'zMin', parameterName: 'Z Min', purpose: ParameterPurpose.Mesh, parameterType: NumberDecimal, defaultValue: 0.0)
parameterDef('zMaxDef', parameterCode: 'zMax', parameterName: 'Z Max', purpose: ParameterPurpose.Mesh, parameterType: NumberDecimal, defaultValue: 0.01)

parameterDef('nxDef', parameterCode: 'nx', parameterName: 'Cells in X', purpose: ParameterPurpose.Mesh, parameterType: NumberInteger, defaultValue: 20)
parameterDef('nyDef', parameterCode: 'ny', parameterName: 'Cells in Y', purpose: ParameterPurpose.Mesh, parameterType: NumberInteger, defaultValue: 20)
parameterDef('nzDef', parameterCode: 'nz', parameterName: 'Cells in Z', purpose: ParameterPurpose.Mesh, parameterType: NumberInteger, defaultValue: 1)

parameterDef('gradingXDef', parameterCode: 'gradingX', parameterName: 'Grading X', purpose: ParameterPurpose.Mesh, parameterType: NumberDecimal, defaultValue: 1.0)
parameterDef('gradingYDef', parameterCode: 'gradingY', parameterName: 'Grading Y', purpose: ParameterPurpose.Mesh, parameterType: NumberDecimal, defaultValue: 2.0)
parameterDef('gradingZDef', parameterCode: 'gradingZ', parameterName: 'Grading Z', purpose: ParameterPurpose.Mesh, parameterType: NumberDecimal, defaultValue: 1.0)

graph('CavityGraph', description: 'Discrete topology graph for cavity mesh')

mesh('CavityMesh',
    graphId: 'CavityGraph',
    meshType: Hexahedral,
    purpose: MeshPurpose.CFD,
    adaptationType: RRefinement,
    description: 'Hexahedral block mesh with geometric grading towards walls for boundary layer resolution')

meshGroup('movingWall', meshId: 'CavityMesh', groupName: 'movingWall', description: 'Top moving lid patch')
meshGroup('fixedWalls', meshId: 'CavityMesh', groupName: 'fixedWalls', description: 'Side and bottom stationary no-slip walls')
meshGroup('frontAndBack', meshId: 'CavityMesh', groupName: 'frontAndBack', description: '2D symmetry empty boundary patches')

modelDef('IncompressibleCavityFlow', modelType: MathModelType.CFD) {
    description 'Standard OpenFOAM Lid-Driven Cavity benchmark for laminar incompressible flow'

    pipeline('IcoFoamStep', stepSeqId: '01', sequenceNum: 1, stepName: 'IcoFoamSolve',
        solvingMethod: OpenFoamIcoFoam)

    model('CavityIcoFoam',
        meshId: 'CavityMesh',
        statusId: 'MathModelDraft') {
        description 'Transient laminar incompressible solver instance for cavity'

        parameters('Param.nu', parameterDefId: 'nuDef', parameterAlias: 'nu', numericValue: 0.01)
        parameters('Param.rho', parameterDefId: 'rhoDef', parameterAlias: 'rho', numericValue: 1000.0)
        parameters('Param.lidVelocity', parameterDefId: 'lidVelocityDef', parameterAlias: 'lidVelocity', numericValue: 1.0)
        parameters('Param.deltaT', parameterDefId: 'dtDef', parameterAlias: 'deltaT', numericValue: 0.005)
        parameters('Param.endTime', parameterDefId: 'endTimeDef', parameterAlias: 'endTime', numericValue: 0.1)
        parameters('Param.pTolerance', parameterDefId: 'pTolDef', parameterAlias: 'pTolerance', numericValue: 1e-6)

        parameters('Mesh.xMin', parameterDefId: 'xMinDef', parameterAlias: 'xMin', numericValue: 0.0)
        parameters('Mesh.xMax', parameterDefId: 'xMaxDef', parameterAlias: 'xMax', numericValue: 0.1)
        parameters('Mesh.yMin', parameterDefId: 'yMinDef', parameterAlias: 'yMin', numericValue: 0.0)
        parameters('Mesh.yMax', parameterDefId: 'yMaxDef', parameterAlias: 'yMax', numericValue: 0.1)
        parameters('Mesh.zMin', parameterDefId: 'zMinDef', parameterAlias: 'zMin', numericValue: 0.0)
        parameters('Mesh.zMax', parameterDefId: 'zMaxDef', parameterAlias: 'zMax', numericValue: 0.01)
        parameters('Mesh.nx', parameterDefId: 'nxDef', parameterAlias: 'nx', numericValue: 20)
        parameters('Mesh.ny', parameterDefId: 'nyDef', parameterAlias: 'ny', numericValue: 20)
        parameters('Mesh.nz', parameterDefId: 'nzDef', parameterAlias: 'nz', numericValue: 1)

        parameters('Mesh.gradingX', parameterDefId: 'gradingXDef', parameterAlias: 'gradingX', numericValue: 1.0)
        parameters('Mesh.gradingY', parameterDefId: 'gradingYDef', parameterAlias: 'gradingY', numericValue: 2.0)
        parameters('Mesh.gradingZ', parameterDefId: 'gradingZDef', parameterAlias: 'gradingZ', numericValue: 1.0)
    }
}

modelDef('IncompressibleCavityFlowFvm', modelType: MathModelType.CFD) {
    description 'Built-in Finite Volume Method benchmark for laminar incompressible flow'

    pipeline('FvmStep', stepSeqId: '01', sequenceNum: 1, stepName: 'FvmSolve',
        solvingMethod: Fvm)

    model('CavityFvm',
        meshId: 'CavityMesh',
        statusId: 'MathModelDraft') {
        description 'Transient laminar incompressible solver instance using built-in FVM'

        parameters('Param.Fvm.nu', parameterDefId: 'nuDef', parameterAlias: 'nu', numericValue: 0.01)
        parameters('Param.Fvm.rho', parameterDefId: 'rhoDef', parameterAlias: 'rho', numericValue: 1000.0)
        parameters('Param.Fvm.lidVelocity', parameterDefId: 'lidVelocityDef', parameterAlias: 'lidVelocity', numericValue: 1.0)
        parameters('Param.Fvm.deltaT', parameterDefId: 'dtDef', parameterAlias: 'deltaT', numericValue: 0.005)
        parameters('Param.Fvm.endTime', parameterDefId: 'endTimeDef', parameterAlias: 'endTime', numericValue: 0.1)
        parameters('Param.Fvm.pTolerance', parameterDefId: 'pTolDef', parameterAlias: 'pTolerance', numericValue: 1e-4)

        parameters('Mesh.Fvm.xMin', parameterDefId: 'xMinDef', parameterAlias: 'xMin', numericValue: 0.0)
        parameters('Mesh.Fvm.xMax', parameterDefId: 'xMaxDef', parameterAlias: 'xMax', numericValue: 0.1)
        parameters('Mesh.Fvm.yMin', parameterDefId: 'yMinDef', parameterAlias: 'yMin', numericValue: 0.0)
        parameters('Mesh.Fvm.yMax', parameterDefId: 'yMaxDef', parameterAlias: 'yMax', numericValue: 0.1)
        parameters('Mesh.Fvm.zMin', parameterDefId: 'zMinDef', parameterAlias: 'zMin', numericValue: 0.0)
        parameters('Mesh.Fvm.zMax', parameterDefId: 'zMaxDef', parameterAlias: 'zMax', numericValue: 0.01)
        parameters('Mesh.Fvm.nx', parameterDefId: 'nxDef', parameterAlias: 'nx', numericValue: 20)
        parameters('Mesh.Fvm.ny', parameterDefId: 'nyDef', parameterAlias: 'ny', numericValue: 20)
        parameters('Mesh.Fvm.nz', parameterDefId: 'nzDef', parameterAlias: 'nz', numericValue: 1)

        parameters('Mesh.Fvm.gradingX', parameterDefId: 'gradingXDef', parameterAlias: 'gradingX', numericValue: 1.0)
        parameters('Mesh.Fvm.gradingY', parameterDefId: 'gradingYDef', parameterAlias: 'gradingY', numericValue: 2.0)
        parameters('Mesh.Fvm.gradingZ', parameterDefId: 'gradingZDef', parameterAlias: 'gradingZ', numericValue: 1.0)
    }
}
