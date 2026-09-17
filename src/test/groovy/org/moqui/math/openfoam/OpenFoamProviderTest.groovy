/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.openfoam

import org.junit.jupiter.api.Test
import org.moqui.math.MathEngine
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta

import java.nio.file.Files
import java.nio.file.Paths

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertThrows

class OpenFoamProviderTest {

    private static MathMeta createCavityModel() {
        File dslFile = new File(System.getProperty('user.dir'), 'examples/openfoam-cavity.groovy')
        if (!dslFile.exists()) {
            dslFile = new File('examples/openfoam-cavity.groovy')
        }
        MathDsl.evaluate(dslFile)
    }

    @Test
    void testOpenFoamNativeStubIsNotAvailable() {
        // Native OpenFOAM runtime integration with libfiniteVolume is not yet linked.
        // It must report false unconditionally so callers do not fabricate results.
        assertFalse(OpenFoamPanama.INSTANCE.isAvailable(),
            "Native OpenFOAM Panama bridge must report isAvailable == false until real solver linking is implemented")
    }

    @Test
    void testCompileOpenFoamPlan() {
        MathMeta mathMeta = createCavityModel()
        OpenFoamProvider provider = new OpenFoamProvider('CavityIcoFoam')
        OpenFoamPlan plan = provider.compile(mathMeta)

        assertNotNull(plan)
        assertEquals('CavityIcoFoam', plan.mathModelId)
        assertEquals('icoFoam', plan.solver)
        assertEquals(0.01d, plan.kinematicViscosity, 1e-6)
        assertEquals(1000.0d, plan.density, 1e-6)
        assertEquals(20, plan.nx)
        assertEquals(20, plan.ny)
        assertEquals(1, plan.nz)
        assertEquals(400, plan.cellCount)
        assertEquals(2.0d, plan.grading[1], 1e-6)
        assertTrue(plan.boundaryPatches.containsKey('movingWall'))
        assertTrue(plan.boundaryPatches.containsKey('fixedWalls'))
    }

    @Test
    void testCompileFvmPlan() {
        MathMeta mathMeta = createCavityModel()
        OpenFoamProvider provider = new OpenFoamProvider('CavityFvm')
        OpenFoamPlan plan = provider.compile(mathMeta)

        assertNotNull(plan)
        assertEquals('CavityFvm', plan.mathModelId)
        assertEquals('incompressibleFvm', plan.solver)
        assertEquals(0.01d, plan.kinematicViscosity, 1e-6)
        assertEquals(400, plan.cellCount)
    }

    @Test
    void testNativeOpenFoamThrowsWhenNotInstalled() {
        MathMeta mathMeta = createCavityModel()
        OpenFoamProvider provider = new OpenFoamProvider('CavityIcoFoam')
        OpenFoamPlan plan = provider.compile(mathMeta)
        // Must fail with ProviderUnavailableException since native OpenFOAM runtime is not linked
        assertThrows(org.moqui.math.spi.ProviderUnavailableException) {
            provider.execute(plan, Collections.emptyMap())
        }
    }

    @Test
    void testExecuteFvmSimulationAndCheckGeneratedDicts() {
        MathMeta mathMeta = createCavityModel()
        OpenFoamProvider provider = new OpenFoamProvider('CavityFvm')
        OpenFoamPlan plan = provider.compile(mathMeta)
        OpenFoamResult result = provider.execute(plan, Collections.emptyMap())

        assertNotNull(result)
        assertEquals('NOT_CONVERGED', result.status)
        assertEquals(400, result.cellCount)
        assertEquals(400, result.velocityField.size())
        assertEquals(400, result.pressureField.size())
        assertTrue(result.executionTimeMs > 0)

        // Residuals must be computed mathematically from velocity and continuity, not hardcoded
        assertNotNull(result.residuals)
        assertTrue(result.residuals.containsKey('continuity'), "Continuity divergence residual must be present")
        assertFalse(result.residuals.containsKey('p'), "Residual must be named 'continuity', not 'p'")
        assertTrue(result.residuals.containsKey('Ux'))
        assertTrue(result.residuals.containsKey('Uy'))
        assertTrue(result.residuals.get('continuity') >= 0.0d)
        assertTrue(result.residuals.get('Ux') >= 0.0d)
        assertTrue(result.residuals.get('Uy') >= 0.0d)

        // Actual iterations and simulated time must come from the actual loop
        int maxIters = (int) Math.round((plan.endTime - plan.startTime) / plan.deltaT)
        assertTrue(result.iterations > 0)
        assertTrue(result.iterations <= maxIters,
            "actual iterations (${result.iterations}) must not exceed max allowed (${maxIters})")
        assertTrue(result.finalTime > plan.startTime)
        assertTrue(result.finalTime <= plan.endTime + 1e-9)

        // Verify generated OpenFOAM case files on disk
        String caseDir = plan.caseDirectory
        assertTrue(Files.exists(Paths.get(caseDir, 'system/controlDict')))
        assertTrue(Files.exists(Paths.get(caseDir, 'system/blockMeshDict')))
        assertTrue(Files.exists(Paths.get(caseDir, 'system/fvSchemes')))
        assertTrue(Files.exists(Paths.get(caseDir, 'system/fvSolution')))
        assertTrue(Files.exists(Paths.get(caseDir, 'constant/transportProperties')))
        assertTrue(Files.exists(Paths.get(caseDir, '0/U')))
        assertTrue(Files.exists(Paths.get(caseDir, '0/p')))

        // Check content of blockMeshDict
        String blockMesh = Files.readString(Paths.get(caseDir, 'system/blockMeshDict'))
        assertTrue(blockMesh.contains('hex (0 1 2 3 4 5 6 7) (20 20 1) simpleGrading (1.0 2.0 1.0)'))
        assertTrue(blockMesh.contains('movingWall'))
    }

    @Test
    void testMathDispatcherRoutesToFvm() {
        MathMeta mathMeta = createCavityModel()
        Object result = MathEngine.execute(mathMeta, 'CavityFvm') {}

        assertTrue(result instanceof OpenFoamResult)
        OpenFoamResult foamResult = (OpenFoamResult) result
        assertEquals('NOT_CONVERGED', foamResult.status)
        assertEquals(400, foamResult.cellCount)
    }

    private static MathMeta createCustomCavityModel(double nu, double deltaT, double endTime = 0.5d, double lidVelocity = 1.0d, double pTolerance = 1e-4d) {
        MathDsl.math {
            ParameterDef('nuDef', parameterCode: 'kinematicViscosity', parameterName: 'Viscosity',
                purposeEnum: org.moqui.math.dsl.ParameterPurpose.FluidProperty,
                parameterTypeEnum: org.moqui.math.dsl.ParameterType.NumberDecimal, defaultValue: nu)
            ParameterDef('dtDef', parameterCode: 'deltaT', parameterName: 'Time step',
                purposeEnum: org.moqui.math.dsl.ParameterPurpose.SolverControl,
                parameterTypeEnum: org.moqui.math.dsl.ParameterType.NumberDecimal, defaultValue: deltaT)
            ParameterDef('endDef', parameterCode: 'endTime', parameterName: 'End time',
                purposeEnum: org.moqui.math.dsl.ParameterPurpose.SolverControl,
                parameterTypeEnum: org.moqui.math.dsl.ParameterType.NumberDecimal, defaultValue: endTime)
            ParameterDef('lidDef', parameterCode: 'lidVelocityX', parameterName: 'Lid velocity',
                purposeEnum: org.moqui.math.dsl.ParameterPurpose.BoundaryCondition,
                parameterTypeEnum: org.moqui.math.dsl.ParameterType.NumberDecimal, defaultValue: lidVelocity)
            ParameterDef('pTolDef', parameterCode: 'residualToleranceP', parameterName: 'P Tol',
                purposeEnum: org.moqui.math.dsl.ParameterPurpose.NumericalScheme,
                parameterTypeEnum: org.moqui.math.dsl.ParameterType.NumberDecimal, defaultValue: pTolerance)

            Graph('TestGraph')
            Mesh('CavityMesh', graphId: 'TestGraph', meshTypeEnumId: 'MtHexahedral', purposeEnumId: 'MpCFD')

            MathModelDef('CavityDef', modelTypeEnum: org.moqui.math.dsl.MathModelType.CFD) {
                pipeline('FvmStep', stepSeqId: '01', sequenceNum: 1, stepName: 'FvmSolve',
                    solvingMethodEnum: org.moqui.math.dsl.MathModelSolvingMethod.Fvm)
                MathModel('CustomCavity', meshId: 'CavityMesh', statusId: 'MathModelDraft') {
                    parameters('P_nu', parameterDefId: 'nuDef', parameterAlias: 'nu', numericValue: nu)
                    parameters('P_dt', parameterDefId: 'dtDef', parameterAlias: 'deltaT', numericValue: deltaT)
                    parameters('P_end', parameterDefId: 'endDef', parameterAlias: 'endTime', numericValue: endTime)
                    parameters('P_lid', parameterDefId: 'lidDef', parameterAlias: 'lidVelocityX', numericValue: lidVelocity)
                    parameters('P_tol', parameterDefId: 'pTolDef', parameterAlias: 'pTolerance', numericValue: pTolerance)
                }
            }
        }
    }

    @Test
    void testDiscriminantLidStopped() {
        // §B.5 Test 1: Coperchio fermo (lidVelocityX = 0): residui <= 1e-15, stato CONVERGED, iterations == 1
        OpenFoamResult res = new OpenFoamProvider('CustomCavity').run(createCustomCavityModel(0.01, 0.005, 0.05, 0.0d))
        assertEquals('CONVERGED', res.status)
        assertEquals(1, res.iterations)
        assertTrue(res.residuals.get('continuity') <= 1e-15, "Continuity residual with stopped lid must be <= 1e-15, got: ${res.residuals.get('continuity')}")
        assertTrue(res.residuals.get('Ux') <= 1e-15, "Ux residual with stopped lid must be <= 1e-15, got: ${res.residuals.get('Ux')}")
        assertTrue(res.residuals.get('Uy') <= 1e-15, "Uy residual with stopped lid must be <= 1e-15, got: ${res.residuals.get('Uy')}")
    }

    @Test
    void testDiscriminantMovingLidSingleStep() {
        // §B.5 Test 2: Coperchio in moto con tolleranze 1e-12 ed endTime = deltaT: Ux > 0, stato NOT_CONVERGED, iterations == 1, finalTime == endTime
        double dt = 0.005d
        OpenFoamResult res = new OpenFoamProvider('CustomCavity').run(createCustomCavityModel(0.01, dt, dt, 1.0d, 1e-12d))
        assertEquals('NOT_CONVERGED', res.status)
        assertEquals(1, res.iterations)
        assertEquals(dt, res.finalTime, 1e-9)
        double topUx = res.velocityField[19 * 20 + 10][0]
        assertTrue(topUx > 0.0d, "Horizontal velocity near moving lid must be positive, got: ${topUx}")
    }

    @Test
    void testDiscriminantTwoStepsShortSimulation() {
        // §B.5 Test 3: Simulazione breve (endTime = 2*deltaT): iterations == 2, finalTime == endTime
        double dt = 0.005d
        double end = 2 * dt
        OpenFoamResult res = new OpenFoamProvider('CustomCavity').run(createCustomCavityModel(0.01, dt, end, 1.0d, 1e-12d))
        assertEquals(2, res.iterations)
        assertEquals(end, res.finalTime, 1e-9)
    }

    @Test
    void testDiscriminantNonMultipleEndTimeThrows() {
        // §B.5 Test 4: endTime non multiplo di deltaT: errore di validazione esplicito
        double dt = 0.005d
        double nonMultipleEnd = 0.007d
        assertThrows(IllegalArgumentException) {
            new OpenFoamProvider('CustomCavity').compile(createCustomCavityModel(0.01, dt, nonMultipleEnd))
        }
    }

    @Test
    void testDiscriminantResidualsVaryWithLidVelocity() {
        // §B.5 Test 5: Residui calcolati dai campi: due valori diversi di lidVelocityX producono residui diversi
        OpenFoamResult res1 = new OpenFoamProvider('CustomCavity').run(createCustomCavityModel(0.01, 0.005, 0.02, 0.5d))
        OpenFoamResult res2 = new OpenFoamProvider('CustomCavity').run(createCustomCavityModel(0.01, 0.005, 0.02, 1.0d))

        double cont1 = res1.residuals.get('continuity')
        double cont2 = res2.residuals.get('continuity')
        assertNotEquals(cont1, cont2, "Residuals must be computed from fields and differ with lidVelocity")
        double ux1 = res1.residuals.get('Ux')
        double ux2 = res2.residuals.get('Ux')
        assertNotEquals(ux1, ux2, "Ux residuals must differ with lidVelocity")
    }

    @Test
    void testViscositySensitivity() {
        // Discriminant test 1: Increasing nu changes the velocity field measurably
        OpenFoamResult res1 = new OpenFoamProvider('CustomCavity').run(createCustomCavityModel(0.01, 0.005, 0.2))
        OpenFoamResult res2 = new OpenFoamProvider('CustomCavity').run(createCustomCavityModel(0.1, 0.005, 0.2))

        double sumDiff = 0.0d
        for (int j = 0; j < 20; j++) {
            sumDiff += Math.abs(res1.velocityField[j * 20 + 10][0] - res2.velocityField[j * 20 + 10][0])
        }
        assertTrue(sumDiff > 1e-3, "Centerline horizontal velocity profile must vary with viscosity, sumDiff: ${sumDiff}")
    }

    @Test
    void testVortexAsymmetryShiftWithReynolds() {
        // Discriminant test 2: As Re increases (nu decreases), vortex core shifts
        OpenFoamResult resLowRe = new OpenFoamProvider('CustomCavity').run(createCustomCavityModel(0.1, 0.005, 0.25))
        OpenFoamResult resHighRe = new OpenFoamProvider('CustomCavity').run(createCustomCavityModel(0.01, 0.005, 0.25))

        // Compare vertical velocity along horizontal centerline (j=10)
        double sumDiff = 0.0d
        for (int i = 0; i < 20; i++) {
            double vLow = resLowRe.velocityField[10 * 20 + i][1]
            double vHigh = resHighRe.velocityField[10 * 20 + i][1]
            sumDiff += Math.abs(vLow - vHigh)
        }
        assertTrue(sumDiff > 1e-3, "Vortex velocity profile across centerline must shift with Reynolds number")
    }

    @Test
    void testMassConservationInInternalCells() {
        // Discriminant test 3: Continuity divergence must satisfy physical bounds across all cells
        OpenFoamResult res = new OpenFoamProvider('CustomCavity').run(createCustomCavityModel(0.01, 0.005, 0.15))
        double continuityResidual = res.residuals.get('continuity')
        assertTrue(continuityResidual < 2.0, "Continuity residual must be conserved: ${continuityResidual}")
    }

    @Test
    void testBoundaryConditionsAdherence() {
        // Discriminant test 4: No-slip at stationary walls, lid velocity at top
        OpenFoamResult res = new OpenFoamProvider('CustomCavity').run(createCustomCavityModel(0.01, 0.005, 0.15))

        // Bottom row (j=0 in 0-indexed mesh): near zero
        for (int i = 0; i < 20; i++) {
            double uBot = res.velocityField[0 * 20 + i][0]
            double vBot = res.velocityField[0 * 20 + i][1]
            assertTrue(Math.abs(uBot) < 0.15, "Bottom wall u velocity should be near zero, was: ${uBot}")
            assertTrue(Math.abs(vBot) < 0.15, "Bottom wall v velocity should be near zero, was: ${vBot}")
        }

        // Top row (j=19 near lid): positive horizontal flow driven by lid
        double avgTopU = 0.0d
        for (int i = 0; i < 20; i++) {
            avgTopU += res.velocityField[19 * 20 + i][0]
        }
        avgTopU /= 20.0
        assertTrue(avgTopU > 0.3, "Top row near moving lid must show strong positive u velocity, got: ${avgTopU}")
    }

    @Test
    void testExcessiveDeltaTCausesDivergence() {
        // Discriminant test 5: Massive CFL violation (dt = 0.5 with dx = 0.005 => CFL = 100) must NOT converge
        OpenFoamPlan plan = new OpenFoamProvider('CustomCavity').compile(createCustomCavityModel(0.01, 0.5, 0.5))
        OpenFoamResult res = new OpenFoamProvider('CustomCavity').execute(plan, Collections.emptyMap())
        assertEquals('NOT_CONVERGED', res.status, "Simulation with huge CFL must not report CONVERGED")
    }

    @Test
    void testModelParameterScopeIsolation() {
        // Evaluate two models in the same MathMeta and ensure parameters with null mathModelId
        // or a different mathModelId do not leak into model scope
        MathMeta mathMeta = MathDsl.math {
            ParameterDef('nuDef', parameterCode: 'kinematicViscosity', parameterName: 'Viscosity',
                purposeEnum: org.moqui.math.dsl.ParameterPurpose.FluidProperty,
                parameterTypeEnum: org.moqui.math.dsl.ParameterType.NumberDecimal, defaultValue: 0.01)
            Graph('TestGraph')
            Mesh('TestMesh', graphId: 'TestGraph', meshTypeEnumId: 'MtHexahedral', purposeEnumId: 'MpCFD')

            // Parameter with null mathModelId (must NOT pollute ModelA or ModelB)
            Parameter('P_Global', parameterDefId: 'nuDef', parameterAlias: 'nu', numericValue: 999.0)

            MathModelDef('Def1', modelTypeEnum: org.moqui.math.dsl.MathModelType.CFD) {
                MathModel('ModelA', meshId: 'TestMesh') {
                    parameters('P1', parameterDefId: 'nuDef', parameterAlias: 'nu', numericValue: 0.02)
                }
                MathModel('ModelB', meshId: 'TestMesh') {
                    parameters('P2', parameterDefId: 'nuDef', parameterAlias: 'nu', numericValue: 0.07)
                }
            }
        }

        OpenFoamPlan planA = new OpenFoamProvider('ModelA').compile(mathMeta)
        OpenFoamPlan planB = new OpenFoamProvider('ModelB').compile(mathMeta)

        assertEquals(0.02d, planA.kinematicViscosity, 1e-6, "ModelA must use its own parameter value 0.02, not global 999.0")
        assertEquals(0.07d, planB.kinematicViscosity, 1e-6, "ModelB must use its own parameter value 0.07, not global 999.0")
    }

    @Test
    void testInvalidModelThrowsException() {
        MathMeta mathMeta = createCavityModel()
        OpenFoamProvider provider = new OpenFoamProvider('NonExistentModel')
        assertThrows(IllegalArgumentException) {
            provider.compile(mathMeta)
        }
    }
}
