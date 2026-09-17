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
        if (!OpenFoamPanama.INSTANCE.isAvailable()) {
            MathMeta mathMeta = createCavityModel()
            OpenFoamProvider provider = new OpenFoamProvider('CavityIcoFoam')
            OpenFoamPlan plan = provider.compile(mathMeta)
            assertThrows(UnsatisfiedLinkError) {
                provider.execute(plan, Collections.emptyMap())
            }
        }
    }

    @Test
    void testExecuteFvmSimulationAndCheckGeneratedDicts() {
        MathMeta mathMeta = createCavityModel()
        OpenFoamProvider provider = new OpenFoamProvider('CavityFvm')
        OpenFoamPlan plan = provider.compile(mathMeta)
        OpenFoamResult result = provider.execute(plan, Collections.emptyMap())

        assertNotNull(result)
        assertTrue(result.status == 'CONVERGED' || result.status == 'NOT_CONVERGED')
        assertEquals(400, result.cellCount)
        assertEquals(400, result.velocityField.size())
        assertEquals(400, result.pressureField.size())
        assertTrue(result.executionTimeMs > 0)

        // Residuals must be computed mathematically, not hardcoded
        assertNotNull(result.residuals)
        assertTrue(result.residuals.containsKey('p'))
        assertTrue(result.residuals.containsKey('Ux'))
        assertTrue(result.residuals.containsKey('Uy'))
        assertTrue(result.residuals.get('p') >= 0.0d)
        assertTrue(result.residuals.get('Ux') >= 0.0d)
        assertTrue(result.residuals.get('Uy') >= 0.0d)

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
        assertTrue(foamResult.status == 'CONVERGED' || foamResult.status == 'NOT_CONVERGED')
        assertEquals(400, foamResult.cellCount)
    }

    @Test
    void testModelParameterScopeIsolation() {
        // Evaluate two models in the same MathMeta and ensure parameter scoping prevents leakage
        MathMeta mathMeta = MathDsl.math {
            ParameterDef('nuDef', parameterCode: 'kinematicViscosity', parameterName: 'Viscosity',
                purposeEnum: org.moqui.math.dsl.ParameterPurpose.FluidProperty,
                parameterTypeEnum: org.moqui.math.dsl.ParameterType.NumberDecimal, defaultValue: 0.01)
            Graph('TestGraph')
            Mesh('TestMesh', graphId: 'TestGraph', meshTypeEnumId: 'MtHexahedral', purposeEnumId: 'MpCFD')

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

        assertEquals(0.02d, planA.kinematicViscosity, 1e-6)
        assertEquals(0.07d, planB.kinematicViscosity, 1e-6)
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
