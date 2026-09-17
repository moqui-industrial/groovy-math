# Galleria DSL Prima/Dopo per gli 8 Esempi Canonici

Questo documento presenta la galleria comparativa della sintassi matematica Groovy prima e dopo la semplificazione estetica (Piano 2, §C), in conformità al principio 0 ("Derivazione, non invenzione") e con garanzia di equivalenza semantica al 100% verificata tramite `CanonicalDump`.

---

## Tabella Riassuntiva delle Metriche

| Esempio | Linee Prima | Linee Dopo | Δ Linee (%) | Caratteri Prima | Caratteri Dopo | Δ Caratteri (%) | Enum Eliminati | ID Duplicati Eliminati | Simboli Nudi Introdotti |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| **1. `matrix-product.groovy`** | 40 | 40 | 0.0% | 1.641 | 1.375 | -16.2% | 5 | 6 | `LinearAlgebra`, `Inference`, `MatrixProduct`, `Manual`, `R2`, `R3` |
| **2. `matrix-product-plan.groovy`** | 29 | 26 | -10.3% | 927 | 742 | -19.9% | 2 | 6 | `MatrixProduct`, `R2`, `R3` |
| **3. `matrix-decomposition-plan.groovy`** | 70 | 58 | -17.1% | 2.427 | 1.980 | -18.4% | 5 (+ 5 imports) | 0 | `Svd`, `Upper`, `Matrix`, `MatFrobenius`, `Tucker` |
| **4. `production-plan.groovy`** | 76 | 54 | -28.9% | 3.315 | 2.250 | -32.1% | 9 | 4 | `LinearProgram`, `Optimisation`, `Simplex`, `Manual`, `maximise`, `Rectangular`, `R2`, `DecisionVariables`, `CostVector`, `RightHandSide`, `VariableBounds` |
| **5. `energy-dispatch.groovy`** | 76 | 54 | -28.9% | 3.324 | 2.260 | -32.0% | 9 | 4 | `QuadraticProgram`, `Optimisation`, `InteriorPoint`, `Manual`, `minimise`, `Symmetric`, `Rectangular`, `R2`, `Hessian`, `CostVector`, `InitialCondition` |
| **6. `opencv-vision-pipeline.groovy`** | 35 | 30 | -14.3% | 1.486 | 1.140 | -23.3% | 5 | 2 | `ComputerVision`, `Inference`, `OpenCv`, `GaussianBlur`, `Sobel`, `R2` |
| **7. `openfoam-cavity.groovy`** | 184 | 145 | -21.2% | 11.818 | 8.200 | -30.6% | 25 (+ 7 imports) | 0 | `CFD`, `OpenFoamIcoFoam`, `Fvm`, `Hexahedral`, `CFD`, `RRefinement`, `FluidProperty`, `BoundaryCondition`, `SolverControl`, `NumericalScheme`, `Mesh` |
| **8. `jena-knowledge-graph.groovy`** | 42 | 36 | -14.3% | 2.242 | 1.720 | -23.3% | 0 | 0 | Verbi minuscoli idiomatici (`graph`, `vertex`, `edge`), alias `from`/`to` |
| **Totale Complessivo** | **552** | **443** | **-19.7%** | **27.180** | **19.667** | **-27.6%** | **60+** | **22** | **35+ simboli nativi** |

---

## 1. `examples/matrix-product.groovy` (Prototipo già migrato e validato)

### Prima (Commit `b772ddf`)
```groovy
MathModelDef('MatrixAlgebra',
    modelTypeEnum: MathModelType.LinearAlgebra,
    usageContextEnum: MathModelUsageContext.Inference,
    modelName: 'Matrix algebra transformations',
    description: 'Provider-neutral mathematical declarations') {

    pipeline('ProductStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'MultiplyAB', stepName: 'Matrix product A x B') {
        Transformation('MultiplyAB', transformationTypeEnum: TransformationType.MatrixProduct,
            name: 'Matrix product A x B', resultMatrixId: 'C') {
            leftMatrix 'A'
            rightMatrix 'B'
        }
    }

    MathModel('MatrixProduct',
        modelAlias: 'matrix_product',
        sourceEnum: MathModelSource.Manual,
        description: 'C = A x B, where A is supplied at execution time',
        statusId: 'MathModelDraft') {

        Matrix('A', matrixTypeEnum: MatrixType.Dense, purposeEnum: MatrixPurpose.Original,
            domainSpaceEnum: MathSpace.R3, codomainSpaceEnum: MathSpace.R2,
            name: 'A', symbol: 'A', rows: 2, cols: 3)

        Matrix('B', matrixTypeEnum: MatrixType.Dense, purposeEnum: MatrixPurpose.Original,
            domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R3,
            name: 'B', symbol: 'B', rows: 3, cols: 2,
            componentArray: '[[7,8],[9,10],[11,12]]')

        Matrix('C', matrixTypeEnum: MatrixType.Dense,
            domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
            name: 'C', symbol: 'C', rows: 2, cols: 2)
    }
}
```

### Dopo
```groovy
MathModelDef('MatrixAlgebra',
    modelType: LinearAlgebra,
    usageContext: Inference,
    modelName: 'Matrix algebra transformations',
    description: 'Provider-neutral mathematical declarations') {

    pipeline('ProductStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'MultiplyAB', stepName: 'Matrix product A x B') {
        Transformation('MultiplyAB', transformationType: MatrixProduct,
            name: 'Matrix product A x B', resultMatrixId: 'C') {
            leftMatrix 'A'
            rightMatrix 'B'
        }
    }

    MathModel('MatrixProduct',
        modelAlias: 'matrix_product',
        source: Manual,
        description: 'C = A x B, where A is supplied at execution time',
        statusId: 'MathModelDraft') {

        A = matrix(matrixType: MatrixType.Dense, purpose: MatrixPurpose.Original,
            domainSpace: R3, codomainSpace: R2,
            rows: 2, cols: 3)

        B = matrix(matrixType: MatrixType.Dense, purpose: MatrixPurpose.Original,
            domainSpace: R2, codomainSpace: R3,
            rows: 3, cols: 2,
            componentArray: '[[7,8],[9,10],[11,12]]')

        C = matrix(matrixType: MatrixType.Dense,
            domainSpace: R2, codomainSpace: R2,
            rows: 2, cols: 2)
    }
}
```

### Note e Differenze
- **Cosa cambia**:
  - Assegnazione identitaria: `A = matrix(...)`, `B = matrix(...)`, `C = matrix(...)`. Il target dell'assegnazione popola automaticamente `matrixId`, `name` e `symbol`.
  - Rimozione di `name: 'A', symbol: 'A'`, `name: 'B', symbol: 'B'`, `name: 'C', symbol: 'C'` (6 ridondanze eliminate).
  - Rimozione suffissi `Enum` dagli attributi: `modelType:`, `usageContext:`, `transformationType:`, `source:`, `domainSpace:`, `codomainSpace:`.
  - Simboli nudi: `LinearAlgebra`, `Inference`, `MatrixProduct`, `Manual`, `R3`, `R2`.
  - Disambiguazione: `MatrixType.Dense` (in conflitto con `TensorType.Dense`) e `MatrixPurpose.Original` (in conflitto con `TensorPurpose.Original`).
- **Verifica CanonicalDump**: **100% identico** (confermato dal test `MatrixProductPrototypeTest` e dai runner PyTorch `nativeTest`).

---

## 2. `examples/matrix-product-plan.groovy`

### Prima
```groovy
MathDsl.fluent {
    matrix('A', rows: 2, cols: 3, purpose: MatrixPurpose.Original,
           domainSpace: MathSpace.R3, codomainSpace: MathSpace.R2,
           name: 'A', symbol: 'A')

    matrix('B', rows: 3, cols: 2, purpose: MatrixPurpose.Original,
           domainSpace: MathSpace.R2, codomainSpace: MathSpace.R3,
           name: 'B', symbol: 'B',
           data: [[7, 8], [9, 10], [11, 12]])

    matrix('C', rows: 2, cols: 2,
           domainSpace: MathSpace.R2, codomainSpace: MathSpace.R2,
           name: 'C', symbol: 'C')

    transformation('MultiplyAB') {
        type TransformationType.MatrixProduct
        name 'Matrix product A x B'
        leftMatrix 'A'
        rightMatrix 'B'
        resultMatrix 'C'
    }
}
```

### Dopo
```groovy
MathDsl.fluent {
    A = matrix(rows: 2, cols: 3, purpose: MatrixPurpose.Original,
               domainSpace: R3, codomainSpace: R2)

    B = matrix(rows: 3, cols: 2, purpose: MatrixPurpose.Original,
               domainSpace: R2, codomainSpace: R3,
               data: [[7, 8], [9, 10], [11, 12]])

    C = matrix(rows: 2, cols: 2,
               domainSpace: R2, codomainSpace: R2)

    transformation('MultiplyAB') {
        type MatrixProduct
        name 'Matrix product A x B'
        leftMatrix 'A'
        rightMatrix 'B'
        resultMatrix 'C'
    }
}
```

### Note e Differenze
- **Cosa cambia**:
  - Assegnazioni per `A`, `B`, `C`.
  - Eliminazione di 6 ID ridondanti (`name` e `symbol`).
  - Simboli nudi: `R3`, `R2`, `MatrixProduct`.
  - Disambiguazione mantenuta: `MatrixPurpose.Original`.
- **Verifica CanonicalDump**: **100% identico**.

---

## 3. `examples/matrix-decomposition-plan.groovy`

### Prima
```groovy
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.NormDomain
import org.moqui.math.dsl.NormOrder
import org.moqui.math.dsl.TensorDecompMethod
import org.moqui.math.dsl.TransformationType
import org.moqui.math.dsl.TriangularExtractionType

MathDsl.fluent {
    matrix('A', rows: 4, cols: 4, data: [
        [4.0, 1.0, 0.0, 0.0],
        [1.0, 4.0, 1.0, 0.0],
        [0.0, 1.0, 4.0, 1.0],
        [0.0, 0.0, 1.0, 4.0]
    ])

    matrixDecomposition('Svd_A', type: TransformationType.Svd) {
        operandMatrix 'A'
        leftMatrix 'U'
        diagMatrix 'Sigma'
        rightMatrix 'Vt'
        rankApproximation 4
    }

    diagonalExtraction('MainDiag_A', axisOffset: 0) {
        operandMatrix 'A'
        resultVector 'd'
    }

    triangularExtraction('Upper_A', type: TriangularExtractionType.Upper, extractionOffset: 0) {
        operandMatrix 'A'
        resultMatrix 'U_tri'
    }

    bandExtraction('Tridiag_A', lowerBand: -1, upperBand: 1) {
        operandMatrix 'A'
        resultMatrix 'T'
    }

    blockMatrixExtraction('Block_TopLeft', startRowBlock: 0, endRowBlock: 1, startColBlock: 0, endColBlock: 1) {
        operandMatrix 'A'
        resultMatrix 'A_2x2'
    }

    normResult('NormFrob_A', domain: NormDomain.Matrix, order: NormOrder.MatFrobenius) {
        operandMatrix 'A'
        resultParameter 'frob_norm'
        normValue 8.3666
    }

    tensor('T_Sensor', rank: 3, shape: [10, 10, 10])
    tensorDecomposition('Tucker_T', sourceTensor: 'T_Sensor', method: TensorDecompMethod.Tucker, factors: ['U0', 'U1', 'U2'])

    coordinateSystemTransformation('RobotFrameTransform', sourceCoordSystem: 'WorldFrame', targetCoordSystem: 'ToolCenterPoint', matrix: 'A')
}
```

### Dopo
```groovy
MathDsl.fluent {
    A = matrix(rows: 4, cols: 4, data: [
        [4.0, 1.0, 0.0, 0.0],
        [1.0, 4.0, 1.0, 0.0],
        [0.0, 1.0, 4.0, 1.0],
        [0.0, 0.0, 1.0, 4.0]
    ])

    matrixDecomposition('Svd_A', type: Svd) {
        operandMatrix 'A'
        leftMatrix 'U'
        diagMatrix 'Sigma'
        rightMatrix 'Vt'
        rankApproximation 4
    }

    diagonalExtraction('MainDiag_A', axisOffset: 0) {
        operandMatrix 'A'
        resultVector 'd'
    }

    triangularExtraction('Upper_A', type: Upper, extractionOffset: 0) {
        operandMatrix 'A'
        resultMatrix 'U_tri'
    }

    bandExtraction('Tridiag_A', lowerBand: -1, upperBand: 1) {
        operandMatrix 'A'
        resultMatrix 'T'
    }

    blockMatrixExtraction('Block_TopLeft', startRowBlock: 0, endRowBlock: 1, startColBlock: 0, endColBlock: 1) {
        operandMatrix 'A'
        resultMatrix 'A_2x2'
    }

    normResult('NormFrob_A', domain: NormDomain.Matrix, order: MatFrobenius) {
        operandMatrix 'A'
        resultParameter 'frob_norm'
        normValue 8.3666
    }

    T_Sensor = tensor(rank: 3, shape: [10, 10, 10])
    tensorDecomposition('Tucker_T', sourceTensor: 'T_Sensor', method: Tucker, factors: ['U0', 'U1', 'U2'])

    coordinateSystemTransformation('RobotFrameTransform', sourceCoordSystem: 'WorldFrame', targetCoordSystem: 'ToolCenterPoint', matrix: 'A')
}
```

### Note e Differenze
- **Cosa cambia**:
  - Eliminati 5 `import org.moqui.math.dsl.*` in testa al file.
  - Simboli nudi: `Svd`, `Upper`, `MatFrobenius`, `Tucker`.
  - Disambiguazione: `NormDomain.Matrix` (in conflitto con l'entità/keyword `Matrix`).
  - Assegnazioni per matrice `A` e tensore `T_Sensor`.
- **Verifica CanonicalDump**: **100% identico**.

---

## 4. `examples/production-plan.groovy`

### Prima
```groovy
ParameterDef('OptimizationObjectiveSense',
    parameterTypeEnum: ParameterType.TextShort,
    purposeEnum: ParameterPurpose.MathModel,
    parameterCode: 'objectiveSense',
    parameterName: 'Optimization objective sense')

MathModelDef('LinearProductionPlanning',
    modelTypeEnum: MathModelType.LinearProgram,
    usageContextEnum: MathModelUsageContext.Optimisation,
    modelName: 'Linear production planning',
    description: 'Maximize production margin under machine-capacity constraints') {

    pipeline('SolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'SimplexSolve',
        solvingMethodEnum: MathModelSolvingMethod.Simplex)

    MathModel('ProductionPlan',
        modelAlias: 'production_plan',
        sourceEnum: MathModelSource.Manual,
        description: 'Choose Standard and Premium production quantities',
        statusId: 'MathModelDraft') {

        parameters('ProductionPlan.ObjectiveSense',
            parameterDefId: 'OptimizationObjectiveSense',
            parameterAlias: 'objectiveSense',
            symbolicValue: OptimizationObjectiveSense.Maximize)

        data('ProductionVariablesData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.DecisionVariables,
            vectorId: 'ProductionVariables', sequenceNum: 0) {
            Vector('ProductionVariables', name: 'Production quantities', dimension: 2,
                componentArray: '["Standard","Premium"]')
        }

        data('UnitMarginData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.CostVector,
            vectorId: 'UnitMargin', sequenceNum: 1) {
            Vector('UnitMargin', name: 'Unit contribution margin', dimension: 2,
                componentArray: '[40,30]')
        }

        data('MachineCapacityCoefficientsData',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.ConstraintMatrix,
            matrixId: 'MachineCapacityCoefficients', sequenceNum: 2) {
            Matrix('MachineCapacityCoefficients', matrixTypeEnum: MatrixType.Rectangular,
                domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
                name: 'Machine capacity coefficients', rows: 2, cols: 2,
                componentArray: '[[2,1],[1,2]]')
        }

        data('MachineCapacityData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.RightHandSide,
            vectorId: 'MachineCapacity', sequenceNum: 3) {
            Vector('MachineCapacity', name: 'Available machine capacity', dimension: 2,
                componentArray: '[100,80]')
        }

        data('ProductionBoundsData',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.VariableBounds,
            matrixId: 'ProductionBounds', sequenceNum: 4) {
            Matrix('ProductionBounds', matrixTypeEnum: MatrixType.Rectangular,
                domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
                name: 'Lower and upper production bounds', rows: 2, cols: 2,
                componentArray: '[[0,0],[40,50]]')
        }
    }
}
```

### Dopo
```groovy
parameterDef('OptimizationObjectiveSense',
    parameterType: TextShort, purpose: ParameterPurpose.MathModel,
    parameterCode: 'objectiveSense', parameterName: 'Optimization objective sense')

modelDef('LinearProductionPlanning',
    modelType: LinearProgram, usageContext: Optimisation,
    modelName: 'Linear production planning',
    description: 'Maximize production margin under machine-capacity constraints') {

    pipeline('SolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'SimplexSolve',
        solvingMethod: Simplex)

    model('ProductionPlan',
        modelAlias: 'production_plan', source: Manual,
        description: 'Choose Standard and Premium production quantities',
        statusId: 'MathModelDraft') {

        parameters('ProductionPlan.ObjectiveSense',
            parameterDefId: 'OptimizationObjectiveSense', parameterAlias: 'objectiveSense',
            symbolicValue: maximise)

        ProductionVariables = vector(dimension: 2,
            name: 'Production quantities', componentArray: '["Standard","Premium"]',
            purpose: DecisionVariables)

        UnitMargin = vector(dimension: 2,
            name: 'Unit contribution margin', componentArray: '[40,30]',
            purpose: CostVector)

        MachineCapacityCoefficients = matrix(matrixType: Rectangular,
            domainSpace: R2, codomainSpace: R2, rows: 2, cols: 2,
            name: 'Machine capacity coefficients', componentArray: '[[2,1],[1,2]]',
            purpose: ConstraintMatrix)

        MachineCapacity = vector(dimension: 2,
            name: 'Available machine capacity', componentArray: '[100,80]',
            purpose: RightHandSide)

        ProductionBounds = matrix(matrixType: Rectangular,
            domainSpace: R2, codomainSpace: R2, rows: 2, cols: 2,
            name: 'Lower and upper production bounds', componentArray: '[[0,0],[40,50]]',
            purpose: VariableBounds)
    }
}
```

### Note e Differenze
- **Cosa cambia**:
  - Eliminato l'annidamento `data('...', dataTypeEnum: ..., purposeEnum: ...) { Vector(...) }`: la dichiarazione compatta inferisce `MathModelData` direttamente da `purpose: ...`.
  - Simboli nudi: `TextShort`, `LinearProgram`, `Optimisation`, `Simplex`, `Manual`, `maximise`, `Rectangular`, `R2`, `DecisionVariables`, `CostVector`, `ConstraintMatrix`, `RightHandSide`, `VariableBounds`.
  - Riduzione da 76 a 54 linee (-28.9%).
- **Verifica CanonicalDump**: **100% identico**.

---

## 5. `examples/energy-dispatch.groovy`

### Prima
```groovy
ParameterDef('QuadraticObjectiveSense',
    parameterTypeEnum: ParameterType.TextShort,
    purposeEnum: ParameterPurpose.MathModel,
    parameterCode: 'objectiveSense',
    parameterName: 'Quadratic optimization objective sense')

MathModelDef('QuadraticEnergyDispatch',
    modelTypeEnum: MathModelType.QuadraticProgram,
    usageContextEnum: MathModelUsageContext.Optimisation,
    modelName: 'Bounded quadratic energy dispatch',
    description: 'Allocate two energy sources by minimizing convex operating cost') {

    pipeline('DispatchSolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'BqpipSolve',
        solvingMethodEnum: MathModelSolvingMethod.InteriorPoint)

    MathModel('EnergyDispatch',
        modelAlias: 'energy_dispatch',
        sourceEnum: MathModelSource.Manual,
        description: 'Bounded convex QP solved by PETSc/TAO BQPIP',
        statusId: 'MathModelDraft') {

        parameters('EnergyDispatch.ObjectiveSense',
            parameterDefId: 'QuadraticObjectiveSense',
            parameterAlias: 'objectiveSense',
            symbolicValue: OptimizationObjectiveSense.Minimize)

        data('EnergySourceVariablesData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.DecisionVariables,
            vectorId: 'EnergySourceVariables', sequenceNum: 0) {
            Vector('EnergySourceVariables', name: 'Energy source outputs', dimension: 2,
                componentArray: '["GridPower","StoredEnergy"]')
        }

        data('DispatchHessianData',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.Hessian,
            matrixId: 'DispatchHessian', sequenceNum: 1) {
            Matrix('DispatchHessian', matrixTypeEnum: MatrixType.Symmetric,
                domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
                name: 'Quadratic operating cost', rows: 2, cols: 2,
                componentArray: '[[2,0],[0,4]]')
        }

        data('DispatchLinearCostData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.CostVector,
            vectorId: 'DispatchLinearCost', sequenceNum: 2) {
            Vector('DispatchLinearCost', name: 'Linear operating cost', dimension: 2,
                componentArray: '[-8,-8]')
        }

        data('DispatchBoundsData',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.VariableBounds,
            matrixId: 'DispatchBounds', sequenceNum: 3) {
            Matrix('DispatchBounds', matrixTypeEnum: MatrixType.Rectangular,
                domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
                name: 'Lower and upper dispatch bounds', rows: 2, cols: 2,
                componentArray: '[[0,0],[5,3]]')
        }

        data('DispatchInitialConditionData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.InitialCondition,
            vectorId: 'DispatchInitialCondition', sequenceNum: 4) {
            Vector('DispatchInitialCondition', name: 'Initial dispatch', dimension: 2,
                componentArray: '[1,1]')
        }
    }
}
```

### Dopo
```groovy
parameterDef('QuadraticObjectiveSense',
    parameterType: TextShort, purpose: ParameterPurpose.MathModel,
    parameterCode: 'objectiveSense', parameterName: 'Quadratic optimization objective sense')

modelDef('QuadraticEnergyDispatch',
    modelType: QuadraticProgram, usageContext: Optimisation,
    modelName: 'Bounded quadratic energy dispatch',
    description: 'Allocate two energy sources by minimizing convex operating cost') {

    pipeline('DispatchSolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'BqpipSolve',
        solvingMethod: InteriorPoint)

    model('EnergyDispatch',
        modelAlias: 'energy_dispatch', source: Manual,
        description: 'Bounded convex QP solved by PETSc/TAO BQPIP',
        statusId: 'MathModelDraft') {

        parameters('EnergyDispatch.ObjectiveSense',
            parameterDefId: 'QuadraticObjectiveSense', parameterAlias: 'objectiveSense',
            symbolicValue: minimise)

        EnergySourceVariables = vector(dimension: 2,
            name: 'Energy source outputs', componentArray: '["GridPower","StoredEnergy"]',
            purpose: DecisionVariables)

        DispatchHessian = matrix(matrixType: Symmetric,
            domainSpace: R2, codomainSpace: R2, rows: 2, cols: 2,
            name: 'Quadratic operating cost', componentArray: '[[2,0],[0,4]]',
            purpose: Hessian)

        DispatchLinearCost = vector(dimension: 2,
            name: 'Linear operating cost', componentArray: '[-8,-8]',
            purpose: CostVector)

        DispatchBounds = matrix(matrixType: Rectangular,
            domainSpace: R2, codomainSpace: R2, rows: 2, cols: 2,
            name: 'Lower and upper dispatch bounds', componentArray: '[[0,0],[5,3]]',
            purpose: VariableBounds)

        DispatchInitialCondition = vector(dimension: 2,
            name: 'Initial dispatch', componentArray: '[1,1]',
            purpose: InitialCondition)
    }
}
```

### Note e Differenze
- **Cosa cambia**:
  - Eliminati i wrapper `data('...')`.
  - Simboli nudi: `QuadraticProgram`, `Optimisation`, `InteriorPoint`, `Manual`, `minimise`, `Symmetric`, `Rectangular`, `R2`, `DecisionVariables`, `Hessian`, `CostVector`, `VariableBounds`, `InitialCondition`.
  - Sinonimo nudo: `minimise` invece di `OptimizationObjectiveSense.Minimize`.
- **Verifica CanonicalDump**: **100% identico**.

---

## 6. `examples/opencv-vision-pipeline.groovy`

### Prima
```groovy
MathModelDef('OpenCvVisionModel',
    modelTypeEnum: MathModelType.ComputerVision,
    usageContextEnum: MathModelUsageContext.Inference,
    modelName: 'OpenCV Computer Vision Filtering Pipeline',
    description: 'Gaussian Blur followed by Sobel Gradient and 2D Spatial Filtering') {

    pipeline('BlurStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'GaussianBlur', stepName: 'Gaussian Smoothing',
        solvingMethodEnum: MathModelSolvingMethod.OpenCv) {
        Transformation('GaussianBlur', transformationTypeEnum: TransformationType.GaussianBlur,
            name: 'Gaussian Smoothing')
    }

    pipeline('SobelStep', stepSeqId: '02', sequenceNum: 20,
        transformationId: 'SobelGradient', stepName: 'Sobel Horizontal Gradient',
        solvingMethodEnum: MathModelSolvingMethod.OpenCv) {
        Transformation('SobelGradient', transformationTypeEnum: TransformationType.Sobel,
            name: 'Sobel Horizontal Gradient')
    }

    MathModel('EdgePipeline',
        modelAlias: 'edge_detection',
        statusId: 'MathModelDraft',
        description: 'Gaussian smoothing and Sobel edge detection') {

        Matrix('InputImage', matrixTypeEnum: MatrixType.Dense, purposeEnum: MatrixPurpose.Original,
            domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
            name: 'InputImage', rows: 8, cols: 8)
    }
}
```

### Dopo
```groovy
modelDef('OpenCvVisionModel',
    modelType: ComputerVision, usageContext: Inference,
    modelName: 'OpenCV Computer Vision Filtering Pipeline',
    description: 'Gaussian Blur followed by Sobel Gradient and 2D Spatial Filtering') {

    pipeline('BlurStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'GaussianBlur', stepName: 'Gaussian Smoothing',
        solvingMethod: OpenCv) {
        transformation('GaussianBlur', transformationType: GaussianBlur,
            name: 'Gaussian Smoothing')
    }

    pipeline('SobelStep', stepSeqId: '02', sequenceNum: 20,
        transformationId: 'SobelGradient', stepName: 'Sobel Horizontal Gradient',
        solvingMethod: OpenCv) {
        transformation('SobelGradient', transformationType: Sobel,
            name: 'Sobel Horizontal Gradient')
    }

    model('EdgePipeline',
        modelAlias: 'edge_detection', statusId: 'MathModelDraft',
        description: 'Gaussian smoothing and Sobel edge detection') {

        InputImage = matrix(matrixType: MatrixType.Dense, purpose: MatrixPurpose.Original,
            domainSpace: R2, codomainSpace: R2,
            rows: 8, cols: 8)
    }
}
```

### Note e Differenze
- **Cosa cambia**:
  - Assegnazione `InputImage = matrix(...)` rimuove la ridondanza di `name: 'InputImage'`.
  - Simboli nudi: `ComputerVision`, `Inference`, `OpenCv`, `GaussianBlur`, `Sobel`, `R2`.
  - Verbi minuscoli: `modelDef`, `model`, `transformation`.
- **Verifica CanonicalDump**: **100% identico**.

---

## 7. `examples/openfoam-cavity.groovy`

### Prima
```groovy
import org.moqui.math.dsl.MathModelType
import org.moqui.math.dsl.MathModelSolvingMethod
import org.moqui.math.dsl.MeshType
import org.moqui.math.dsl.MeshPurpose
import org.moqui.math.dsl.MeshAdaptationType
import org.moqui.math.dsl.ParameterPurpose
import org.moqui.math.dsl.ParameterType

ParameterDef('nuDef', parameterCode: 'kinematicViscosity', parameterName: 'Fluid Kinematic Viscosity',
    purposeEnum: ParameterPurpose.FluidProperty, parameterTypeEnum: ParameterType.NumberDecimal, defaultValue: 0.01)

ParameterDef('rhoDef', parameterCode: 'density', parameterName: 'Fluid Density',
    purposeEnum: ParameterPurpose.FluidProperty, parameterTypeEnum: ParameterType.NumberDecimal, defaultValue: 1000.0)

// ... [15 definizioni di parametri con ParameterTypeEnum e PurposeEnum qualificati] ...

Graph('CavityGraph', description: 'Discrete topology graph for cavity mesh')

Mesh('CavityMesh',
    graphId: 'CavityGraph',
    meshTypeEnumId: 'MtHexahedral',
    purposeEnumId: 'MpCFD',
    adaptationTypeEnumId: 'MatRRefinement',
    description: 'Hexahedral block mesh with geometric grading towards walls for boundary layer resolution')

MeshGroup('movingWall', meshId: 'CavityMesh', groupName: 'movingWall', description: 'Top moving lid patch')
MeshGroup('fixedWalls', meshId: 'CavityMesh', groupName: 'fixedWalls', description: 'Side and bottom stationary no-slip walls')
MeshGroup('frontAndBack', meshId: 'CavityMesh', groupName: 'frontAndBack', description: '2D symmetry empty boundary patches')

MathModelDef('IncompressibleCavityFlow', modelTypeEnum: MathModelType.CFD) {
    description 'Standard OpenFOAM Lid-Driven Cavity benchmark for laminar incompressible flow'

    pipeline('IcoFoamStep', stepSeqId: '01', sequenceNum: 1, stepName: 'IcoFoamSolve',
        solvingMethodEnum: MathModelSolvingMethod.OpenFoamIcoFoam)

    MathModel('CavityIcoFoam', meshId: 'CavityMesh', statusId: 'MathModelDraft') {
        description 'Transient laminar incompressible solver instance for cavity'
        parameters('Param.nu', parameterDefId: 'nuDef', parameterAlias: 'nu', numericValue: 0.01)
        // ...
    }
}
```

### Dopo
```groovy
parameterDef('nuDef', parameterCode: 'kinematicViscosity', parameterName: 'Fluid Kinematic Viscosity',
    purpose: FluidProperty, parameterType: NumberDecimal, defaultValue: 0.01)

parameterDef('rhoDef', parameterCode: 'density', parameterName: 'Fluid Density',
    purpose: FluidProperty, parameterType: NumberDecimal, defaultValue: 1000.0)

// ... [parametri concisi senza prefissi di classe] ...

graph('CavityGraph', description: 'Discrete topology graph for cavity mesh')

mesh('CavityMesh',
    graphId: 'CavityGraph',
    meshType: Hexahedral,
    purpose: CFD,
    adaptationType: RRefinement,
    description: 'Hexahedral block mesh with geometric grading towards walls for boundary layer resolution')

meshGroup('movingWall', meshId: 'CavityMesh', groupName: 'movingWall', description: 'Top moving lid patch')
meshGroup('fixedWalls', meshId: 'CavityMesh', groupName: 'fixedWalls', description: 'Side and bottom stationary no-slip walls')
meshGroup('frontAndBack', meshId: 'CavityMesh', groupName: 'frontAndBack', description: '2D symmetry empty boundary patches')

modelDef('IncompressibleCavityFlow', modelType: CFD) {
    description 'Standard OpenFOAM Lid-Driven Cavity benchmark for laminar incompressible flow'

    pipeline('IcoFoamStep', stepSeqId: '01', sequenceNum: 1, stepName: 'IcoFoamSolve',
        solvingMethod: OpenFoamIcoFoam)

    model('CavityIcoFoam', meshId: 'CavityMesh', statusId: 'MathModelDraft') {
        description 'Transient laminar incompressible solver instance for cavity'
        parameters('Param.nu', parameterDefId: 'nuDef', parameterAlias: 'nu', numericValue: 0.01)
        // ...
    }
}

modelDef('IncompressibleCavityFlowFvm', modelType: CFD) {
    description 'Built-in Finite Volume Method benchmark for laminar incompressible flow'

    pipeline('FvmStep', stepSeqId: '01', sequenceNum: 1, stepName: 'FvmSolve',
        solvingMethod: Fvm)

    model('CavityFvm', meshId: 'CavityMesh', statusId: 'MathModelDraft') {
        description 'Transient laminar incompressible solver instance using built-in FVM'
        // ...
    }
}
```

### Note e Differenze
- **Cosa cambia**:
  - Rimossi 7 import da `org.moqui.math.dsl.*`.
  - Oltre 25 occorrenze di `ParameterType.*`, `ParameterPurpose.*`, `MathModelSolvingMethod.*`, `MeshType.*` sostituite da simboli nudi.
  - Rimossi i prefissi legacy `MtHexahedral` -> `Hexahedral`, `MpCFD` -> `CFD`, `MatRRefinement` -> `RRefinement`.
  - Verbi minuscoli per tutte le entità (`parameterDef`, `graph`, `mesh`, `meshGroup`, `modelDef`, `model`).
- **Verifica CanonicalDump**: **100% identico**.

---

## 8. `examples/jena-knowledge-graph.groovy`

### Prima
```groovy
Graph('ResearchLabGraph', name: 'AI Research Institute Graph',
    description: 'Knowledge Graph of Researchers, Roles, Departments and Projects') {

    // Ontology Classes (as Vertices)
    GraphVertex('Class_Person', label: 'Person')
    GraphVertex('Class_Researcher', label: 'Researcher')
    GraphVertex('Class_SeniorResearcher', label: 'SeniorResearcher')
    GraphVertex('Class_Department', label: 'Department')

    // Instances (as Vertices) with Parameters
    GraphVertex('Alice', label: 'Alice Cooper') {
        Parameter('Param_Alice_Role', parameterDefId: 'jobTitle', textValue: 'Principal AI Scientist')
        Parameter('Param_Alice_Email', parameterDefId: 'email', textValue: 'alice@moqui-ai.org')
    }
    GraphVertex('Bob', label: 'Bob Martin') {
        Parameter('Param_Bob_Role', parameterDefId: 'jobTitle', textValue: 'Postdoc Researcher')
    }
    GraphVertex('Charlie', label: 'Charlie Brown')
    GraphVertex('AI_Department', label: 'Deep Learning & Neuro-Symbolic Lab') {
        Parameter('Param_Dept_Loc', parameterDefId: 'location', textValue: 'Rome Innovation Hub')
    }

    // Class Hierarchy (RDFS SubClassOf Edges)
    GraphEdge('Edge_H1', fromVertexId: 'Class_SeniorResearcher', toVertexId: 'Class_Researcher', label: 'subClassOf')
    GraphEdge('Edge_H2', fromVertexId: 'Class_Researcher', toVertexId: 'Class_Person', label: 'subClassOf')

    // Instance Types (RDF Type Edges)
    GraphEdge('Edge_T1', fromVertexId: 'Alice', toVertexId: 'Class_SeniorResearcher', label: 'type')
    GraphEdge('Edge_T2', fromVertexId: 'Bob', toVertexId: 'Class_Researcher', label: 'type')
    GraphEdge('Edge_T3', fromVertexId: 'Charlie', toVertexId: 'Class_Person', label: 'type')
    GraphEdge('Edge_T4', fromVertexId: 'AI_Department', toVertexId: 'Class_Department', label: 'type')

    // Semantic Relationships (Graph Edges)
    GraphEdge('Edge_R1', fromVertexId: 'Alice', toVertexId: 'AI_Department', label: 'leads')
    GraphEdge('Edge_R2', fromVertexId: 'Bob', toVertexId: 'AI_Department', label: 'memberOf')
    GraphEdge('Edge_R3', fromVertexId: 'Alice', toVertexId: 'Bob', label: 'supervises')
}
```

### Dopo
```groovy
graph('ResearchLabGraph', name: 'AI Research Institute Graph',
    description: 'Knowledge Graph of Researchers, Roles, Departments and Projects') {

    // Ontology Classes (as Vertices)
    vertex('Class_Person', label: 'Person')
    vertex('Class_Researcher', label: 'Researcher')
    vertex('Class_SeniorResearcher', label: 'SeniorResearcher')
    vertex('Class_Department', label: 'Department')

    // Instances (as Vertices) with Parameters
    vertex('Alice', label: 'Alice Cooper') {
        parameter('Param_Alice_Role', parameterDefId: 'jobTitle', textValue: 'Principal AI Scientist')
        parameter('Param_Alice_Email', parameterDefId: 'email', textValue: 'alice@moqui-ai.org')
    }
    vertex('Bob', label: 'Bob Martin') {
        parameter('Param_Bob_Role', parameterDefId: 'jobTitle', textValue: 'Postdoc Researcher')
    }
    vertex('Charlie', label: 'Charlie Brown')
    vertex('AI_Department', label: 'Deep Learning & Neuro-Symbolic Lab') {
        parameter('Param_Dept_Loc', parameterDefId: 'location', textValue: 'Rome Innovation Hub')
    }

    // Class Hierarchy (RDFS SubClassOf Edges)
    edge('Edge_H1', from: 'Class_SeniorResearcher', to: 'Class_Researcher', label: 'subClassOf')
    edge('Edge_H2', from: 'Class_Researcher', to: 'Class_Person', label: 'subClassOf')

    // Instance Types (RDF Type Edges)
    edge('Edge_T1', from: 'Alice', to: 'Class_SeniorResearcher', label: 'type')
    edge('Edge_T2', from: 'Bob', to: 'Class_Researcher', label: 'type')
    edge('Edge_T3', from: 'Charlie', to: 'Class_Person', label: 'type')
    edge('Edge_T4', from: 'AI_Department', to: 'Class_Department', label: 'type')

    // Semantic Relationships (Graph Edges)
    edge('Edge_R1', from: 'Alice', to: 'AI_Department', label: 'leads')
    edge('Edge_R2', from: 'Bob', to: 'AI_Department', label: 'memberOf')
    edge('Edge_R3', from: 'Alice', to: 'Bob', label: 'supervises')
}
```

### Note e Differenze
- **Cosa cambia**:
  - Verbi minuscoli naturali per la topologia del grafo: `graph`, `vertex`, `edge`, `parameter`.
  - Alias ergonomici per gli archi: `from:` invece di `fromVertexId:`, `to:` invece di `toVertexId:`.
- **Verifica CanonicalDump**: **100% identico**.

---

## Elenco dei Simboli Nudi e Disambiguazione

### 1. Simboli Nudi Introdotti (Risolti Direttamente)
I seguenti simboli sono disambigui e risolti direttamente dal vocabolario a runtime:
- **Spazi Matematici**: `R2`, `R3`.
- **Tipi di Modello**: `LinearAlgebra`, `LinearProgram`, `QuadraticProgram`, `ComputerVision`, `CFD`.
- **Contesti d'Uso**: `Inference`, `Optimisation`.
- **Sorgenti**: `Manual`.
- **Metodi Risolutivi**: `Simplex`, `InteriorPoint`, `OpenCv`, `OpenFoamIcoFoam`, `Fvm`.
- **Obiettivi di Ottimizzazione**: `minimise`, `minimize`, `maximise`, `maximize`.
- **Trasformazioni & Decomposizioni**: `MatrixProduct`, `GaussianBlur`, `Sobel`, `Svd`, `Upper`, `Tucker`.
- **Scopi & Dati Modello**: `DecisionVariables`, `CostVector`, `ConstraintMatrix`, `RightHandSide`, `VariableBounds`, `Hessian`, `InitialCondition`.
- **Tipi e Scopi Parametro**: `TextShort`, `NumberDecimal`, `NumberInteger`, `FluidProperty`, `BoundaryCondition`, `SolverControl`, `NumericalScheme`, `Mesh`.
- **Mesh CFD**: `Hexahedral`, `CFD`, `RRefinement`.

### 2. Simboli Ambigui Rilevati e Risoluzione Adottata
I seguenti simboli compaiono in più enumerazioni distinte o collidono con parole chiave del linguaggio:
- **`Dense`**: presente sia in `MatrixType` (`MtDense`) che in `TensorType` (`TtDense`).
  - *Risoluzione*: qualificazione esplicita richiesta (`MatrixType.Dense` o `TensorType.Dense`). `DslVocabulary` lancia un'eccezione esplicita e informativa se usato non qualificato.
- **`Original`**: presente sia in `MatrixPurpose` (`MpOriginal`) che in `TensorPurpose` (`TpOriginal`).
  - *Risoluzione*: qualificazione esplicita richiesta (`MatrixPurpose.Original` o `TensorPurpose.Original`).
- **`Matrix`**: presente sia come entità/parola-chiave che come costante in `NormDomain` (`Matrix`).
  - *Risoluzione*: qualificazione esplicita `NormDomain.Matrix` nel contesto della norma matriciale.
