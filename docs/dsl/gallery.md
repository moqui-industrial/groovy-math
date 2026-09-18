# Groovy-Math DSL Gallery: Canonical vs. Compact Form

This gallery presents the 9 representative mathematical models and plans across all supported mathematical domains in `groovy-math`. It details the structural equivalence, syntactic compression, derivation rules, and execution equivalence between the canonical (full-schema) representation in `examples/canonical/` and the compact ergonomic DSL in `examples/`.

All compact models in `examples/` are verified for **100% structural and semantic identity** against their canonical counterparts in `examples/canonical/` by [`GalleryEquivalenceTest`](../../src/test/groovy/org/moqui/math/dsl/GalleryEquivalenceTest.groovy).

---

## 1. Metrics & Compression Summary

The table below reports the exact line and character counts computed directly from the files on disk at commit time:

| Example | Canonical (Lines / Chars) | Compact (Lines / Chars) | Line Reduction | Char Reduction | Equivalence Test |
| :--- | :--- | :--- | :--- | :--- | :--- |
| [`matrix-product.groovy`](../../examples/matrix-product.groovy) | 40 lines / 1375 chars | 27 lines / 964 chars | -32.5% | -29.9% | `GalleryEquivalenceTest.testCanonicalAndCompactEquivalence` |
| [`matrix-product-plan.groovy`](../../examples/matrix-product-plan.groovy) | 29 lines / 927 chars | 16 lines / 482 chars | -44.8% | -48.0% | `GalleryEquivalenceTest.testCanonicalAndCompactEquivalence` |
| [`matrix-decomposition-plan.groovy`](../../examples/matrix-decomposition-plan.groovy) | 70 lines / 2427 chars | 51 lines / 1460 chars | -27.1% | -39.8% | `GalleryEquivalenceTest.testCanonicalAndCompactEquivalence` |
| [`production-plan.groovy`](../../examples/production-plan.groovy) | 76 lines / 3290 chars | 28 lines / 1427 chars | -63.2% | -56.6% | `GalleryEquivalenceTest.testCanonicalAndCompactEquivalence` |
| [`energy-dispatch.groovy`](../../examples/energy-dispatch.groovy) | 76 lines / 3300 chars | 28 lines / 1435 chars | -63.2% | -56.5% | `GalleryEquivalenceTest.testCanonicalAndCompactEquivalence` |
| [`opencv-vision-pipeline.groovy`](../../examples/opencv-vision-pipeline.groovy) | 35 lines / 1466 chars | 24 lines / 1045 chars | -31.4% | -28.7% | `GalleryEquivalenceTest.testCanonicalAndCompactEquivalence` |
| [`openfoam-cavity.groovy`](../../examples/openfoam-cavity.groovy) | 184 lines / 11818 chars | 126 lines / 7927 chars | -31.5% | -32.9% | `GalleryEquivalenceTest.testCanonicalAndCompactEquivalence` |
| [`jena-knowledge-graph.groovy`](../../examples/jena-knowledge-graph.groovy) | 42 lines / 2242 chars | 42 lines / 1949 chars | +0.0% | -13.1% | `GalleryEquivalenceTest.testCanonicalAndCompactEquivalence` |
| [`product-catalog-graph.groovy`](../../examples/product-catalog-graph.groovy) | 60 lines / 3628 chars | 60 lines / 3187 chars | +0.0% | -12.2% | `GalleryEquivalenceTest.testCanonicalAndCompactEquivalence` |
| **Total** | **612 lines / 30473 chars** | **402 lines / 19876 chars** | **-34.3%** | **-34.8%** | **9/9 Passed (100%)** |

---

## 2. Derivation Rules & Architectural Decision Records (ADRs)

Each ergonomic abbreviation in the compact DSL maps directly to a systematic derivation rule or formal ADR:

| Abbreviation / Feature | Derivation Rule / ADR | Description & Metamodel Mapping |
| :--- | :--- | :--- |
| **Mathematical Operators** (`*`, `+`, `-`, `**`, `@`) | [ADR 0003: Mathematical Operators](../adr/0003-operators.md) | Overloaded Groovy operators on `ModelProvider`, `DslVariable`, `DslExpression` mapped to `moqui.math.Transformation` (`TtMatrixProduct`, `TtAddition`, `TtPower`, etc.). |
| **Optimization Keywords** (`variable`, `maximize`, `minimize`, `subjectTo`, `.le()`, `.ge()`, `.eq()`) | [ADR 0004: Optimization Keywords](../adr/0004-optimization-keywords.md) | Algebraic optimization surface compiling variables, bounds matrix ($2 \times N$), cost vector, constraint matrix, and relational transformations for LP (OR-Tools) and QP (PETSc/TAO). |
| **Naked Descriptive Symbols** (`LinearProgram`, `Simplex`, `DecisionVariables`, `CostVector`, `RightHandSide`, `VariableBounds`) | Piano 4 §B, Consegna 1 & 2 | Generated `DESCRIPTION_ALIASES` in generated enum classes mapping human-readable enum descriptions from `MathEntities.xml` to canonical `enumId`s. |
| **Pure Mathematical Plans** (`MathDsl.fluent { ... }`) | Piano 4 §C2, §F | Pure declarative plans omitting `MathModelDef` / `MathModel` metadata wrappers when lifecycle, versioning, or parameters are not required. |
| **Satellite Entity Functions** (`matrixDecomposition`, `diagonalExtraction`, `triangularExtraction`, `bandExtraction`, `blockMatrixExtraction`, `normResult`, `tensorDecomposition`, `coordinateSystemTransformation`) | Piano 4 §C5 | LowerCamel invocation for satellite entities mapped directly to respective entities and transformations in the Moqui schema. |
| **Shape & Layout Inference** | [Data Representation](../dsl/data-representation.md), Piano 4 §C3 | Automatic inference of matrix dimensions (`rows`, `cols`), vector `dimension`, and tensor `rank`/`shape` from nested literal data arrays. |
| **Purpose Routing** | Piano 4 §C3, Consegna 3 §0.3 | Contextual resolution of `purpose` into `MathModelDataPurpose` vs. entity-specific purposes (`MatrixPurpose`, `VectorPurpose`, `TensorPurpose`). |
| **Parameter Blocks & UOM Checking** | Piano 4 §C7 | `parameters { ... }` block validating unit compatibility against `UomDimensionType` and `UomDimTypeGroupMember` from embedded `UnitData.xml`. |

---

## 3. Exemplary Side-by-Side Comparisons

### 2.1 Optimization: Production Planning (Linear Program, Simplex / OR-Tools)

#### Canonical (`examples/production-plan.groovy` — 76 lines)
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

#### Compact Ergonomic (`examples/compact/production-plan.groovy` — 28 lines)
```groovy
ParameterDef('OptimizationObjectiveSense', type: TextShort, purpose: MathModel,
    code: 'objectiveSense', name: 'Optimization objective sense')

MathModelDef('LinearProductionPlanning', type: LinearProgram, usage: Optimisation,
    modelName: 'Linear production planning',
    description: 'Maximize production margin under machine-capacity constraints') {

    pipeline('SolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'SimplexSolve', method: Simplex)

    MathModel('ProductionPlan', alias: 'production_plan', source: Manual,
        description: 'Choose Standard and Premium production quantities', status: Draft) {

        parameters {
            objectiveSense = Maximize
        }

        vector('ProductionVariables', ['Standard', 'Premium'], purpose: DecisionVariables, name: 'Production quantities')
        vector('UnitMargin', [40, 30], purpose: CostVector, name: 'Unit contribution margin')
        matrix('MachineCapacityCoefficients', [[2, 1], [1, 2]], purpose: ConstraintMatrix, type: Rectangular, name: 'Machine capacity coefficients')
        vector('MachineCapacity', [100, 80], purpose: RightHandSide, name: 'Available machine capacity')
        matrix('ProductionBounds', [[0, 0], [40, 50]], purpose: VariableBounds, type: Rectangular, name: 'Lower and upper production bounds')
    }
}
```

---

### 2.2 Computer Vision: Image Filtering Pipeline (OpenCV via Panama FFM)

#### Canonical (`examples/opencv-vision-pipeline.groovy` — 35 lines)
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

#### Compact Ergonomic (`examples/compact/opencv-vision-pipeline.groovy` — 24 lines)
```groovy
MathModelDef('OpenCvVisionModel', type: ComputerVision, usage: Inference,
    modelName: 'OpenCV Computer Vision Filtering Pipeline',
    description: 'Gaussian Blur followed by Sobel Gradient and 2D Spatial Filtering') {

    pipeline('BlurStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'GaussianBlur', stepName: 'Gaussian Smoothing', method: OpenCv) {
        Transformation('GaussianBlur', type: GaussianBlur, name: 'Gaussian Smoothing')
    }

    pipeline('SobelStep', stepSeqId: '02', sequenceNum: 20,
        transformationId: 'SobelGradient', stepName: 'Sobel Horizontal Gradient', method: OpenCv) {
        Transformation('SobelGradient', type: Sobel, name: 'Sobel Horizontal Gradient')
    }

    MathModel('EdgePipeline', alias: 'edge_detection', status: Draft,
        description: 'Gaussian smoothing and Sobel edge detection') {
        matrix('InputImage', rows: 8, cols: 8, purpose: Original)
    }
}
```

## 3. Provider Runtime Verification

Each of the compact representations has been validated against its native runtime provider backend:

1. **Linear Programming / Simplex (OR-Tools)**: `./gradlew runOrToolsProductionPlan` -> `OPTIMAL: objective=2200.0, variables=[Standard:40.0, Premium:20.0]`
2. **Bounded Quadratic Programming (PETSc/TAO)**: `./gradlew runPetscTaoEnergyDispatch` -> `TAO_CONVERGED_GRTOL: objective=-24.0, variables=[GridPower:4.0, StoredEnergy:2.0]`
3. **Computer Vision (OpenCV C++ via Panama FFM)**: `./gradlew runOpenCvPipeline` -> Gaussian blur + Sobel gradient response verified
4. **CFD Fluid Dynamics (OpenFOAM FVM)**: `./gradlew runOpenFoamCavity` -> 20 steps, velocity vortex recirculation verified
5. **Knowledge Graph & Ontologies (Apache Jena RDF/OWL/SPARQL)**: `./gradlew runJenaGraphSparql`, `./gradlew runJenaProductCatalog` -> Transitive subclass inference and forward-chaining rules verified
6. **Dual Deep Learning / Tensor Acceleration (LibTorch & Google JAX via OpenXLA Panama FFM)**: `./gradlew runJaxPipeline` -> Identical forward pass computation across CPU/GPU runtimes

