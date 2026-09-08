/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.openfoam

import org.junit.jupiter.api.Test
import groovy.math.Math as GroovyMath
import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta

import java.nio.file.Files
import java.nio.file.Paths

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertThrows

class OpenFoamProviderTest {

    private static MathMeta createCavityModel() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES') ?:
            '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'
        File schemaFile = new File(schemaPath)
        File dslFile = new File(System.getProperty('user.dir'), 'examples/openfoam-cavity.groovy')
        if (!dslFile.exists()) {
            dslFile = new File('examples/openfoam-cavity.groovy')
        }
        MathDsl.evaluate(schemaFile, dslFile)
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
    void testExecuteOpenFoamSimulationAndCheckGeneratedDicts() {
        MathMeta mathMeta = createCavityModel()
        OpenFoamProvider provider = new OpenFoamProvider('CavityIcoFoam')
        OpenFoamPlan plan = provider.compile(mathMeta)
        OpenFoamResult result = provider.execute(plan, Collections.emptyMap())

        assertNotNull(result)
        assertEquals('CONVERGED', result.status)
        assertEquals(400, result.cellCount)
        assertEquals(400, result.velocityField.size())
        assertEquals(400, result.pressureField.size())
        assertTrue(result.executionTimeMs > 0)

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
    void testMathDispatcherRoutesToOpenFoam() {
        MathMeta mathMeta = createCavityModel()
        Object result = GroovyMath.execute(mathMeta, 'CavityIcoFoam') {}

        assertTrue(result instanceof OpenFoamResult)
        OpenFoamResult foamResult = (OpenFoamResult) result
        assertEquals('CONVERGED', foamResult.status)
        assertEquals(400, foamResult.cellCount)
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
