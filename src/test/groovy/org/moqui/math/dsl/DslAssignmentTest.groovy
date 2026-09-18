/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.codehaus.groovy.control.MultipleCompilationErrorsException
import org.junit.jupiter.api.Test
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.entity.ModelValue
import org.moqui.math.moqui.MoquiSchemaInspector
import static org.junit.jupiter.api.Assertions.*

class DslAssignmentTest {

    @Test
    void assignmentInjectsIdAndSymbolWithoutName() {
        File tempFile = File.createTempFile('test_assign', '.groovy')
        tempFile.deleteOnExit()
        tempFile.text = '''
A = matrix(rows: 2, cols: 2)
'''
        ModelDefinition model = MoquiSchemaInspector.embedded()
        MathMeta meta = MathDsl.evaluate(model, tempFile)
        ModelValue mat = meta.entity('Matrix').findByName('A')
        assertNotNull(mat)
        assertEquals('A', mat.modelKey)
        assertEquals('A', mat.get('symbol'))
        assertNull(mat.get('name'))
    }

    @Test
    void rejectsDefInDslScript() {
        File tempFile = File.createTempFile('test_def', '.groovy')
        tempFile.deleteOnExit()
        tempFile.text = '''
def A = matrix(rows: 2, cols: 2)
'''
        ModelDefinition model = MoquiSchemaInspector.embedded()
        MultipleCompilationErrorsException ex = assertThrows(MultipleCompilationErrorsException) {
            MathDsl.evaluate(model, tempFile)
        }
        assertTrue(ex.message.contains("usa 'A = matrix(...)' senza 'def': con 'def' il nome non entra nel modello"))
    }

    @Test
    void rejectsDuplicateAssignmentInSameFile() {
        File tempFile = File.createTempFile('test_duplicate', '.groovy')
        tempFile.deleteOnExit()
        tempFile.text = '''
A = matrix(rows: 2, cols: 2)
A = matrix(rows: 3, cols: 3)
'''
        ModelDefinition model = MoquiSchemaInspector.embedded()
        MultipleCompilationErrorsException ex = assertThrows(MultipleCompilationErrorsException) {
            MathDsl.evaluate(model, tempFile)
        }
        assertTrue(ex.message.contains("l'identificatore 'A' è già dichiarato"))
    }

    @Test
    void resolvesForwardReferencesWithinScript() {
        File tempFile = File.createTempFile('test_forward_ref', '.groovy')
        tempFile.deleteOnExit()
        tempFile.text = '''
T = transformation(transformationType: MatrixProduct, resultMatrix: C)
C = matrix(rows: 2, cols: 2)
'''
        ModelDefinition model = MoquiSchemaInspector.embedded()
        MathMeta meta = MathDsl.evaluate(model, tempFile)
        ModelValue trans = meta.entity('Transformation').findByName('T')
        assertNotNull(trans)
        assertEquals('C', trans.get('resultMatrixId'))
    }
}
