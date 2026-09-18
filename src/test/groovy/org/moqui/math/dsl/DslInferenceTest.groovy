/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.entity.ModelValue
import org.moqui.math.moqui.MoquiSchemaInspector
import static org.junit.jupiter.api.Assertions.*

class DslInferenceTest {

    @Test
    void infersVectorDimensionAndRejectsMismatch() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        MathMeta meta = MathDsl.evaluate(model, '''
            v = vector([1.0, 2.0, 3.0])
        ''')
        ModelValue vec = meta.entity('Vector').findByName('v')
        assertNotNull(vec)
        assertEquals(3, ((Number) vec.get('dimension')).intValue())

        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(model, '''
                v2 = vector([1.0, 2.0, 3.0], dimension: 4)
            ''')
        }
        assertTrue(ex.message.contains("Declared dimension (4) does not match literal dimension (3)"))
    }

    @Test
    void infersMatrixRowsAndColsAndRejectsMismatch() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        MathMeta meta = MathDsl.evaluate(model, '''
            m = matrix([[1, 2, 3], [4, 5, 6]])
        ''')
        ModelValue mat = meta.entity('Matrix').findByName('m')
        assertNotNull(mat)
        assertEquals(2, ((Number) mat.get('rows')).intValue())
        assertEquals(3, ((Number) mat.get('cols')).intValue())

        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(model, '''
                m2 = matrix([[1, 2, 3], [4, 5, 6]], rows: 3, cols: 3)
            ''')
        }
        assertTrue(ex.message.contains("Declared rows (3) does not match literal rows (2)"))
    }

    @Test
    void infersTensorShapeAndRejectsMismatch() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        MathMeta meta = MathDsl.evaluate(model, '''
            t = tensor([[[1, 2], [3, 4]], [[5, 6], [7, 8]]])
        ''')
        ModelValue tns = meta.entity('Tensor').findByName('t')
        assertNotNull(tns)
        assertEquals('[2,2,2]', tns.get('shape').toString().replaceAll(/\s+/, ''))

        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(model, '''
                t2 = tensor([[[1, 2], [3, 4]], [[5, 6], [7, 8]]], shape: '[2,3,2]')
            ''')
        }
        assertTrue(ex.message.contains("Declared shape") && ex.message.contains("does not match literal shape"))
    }

    @Test
    void rejectsMixedStringAndNumberArrays() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(model, '''
                v = vector([1.0, 'two', 3.0])
            ''')
        }
        assertTrue(ex.message.contains("Non sono ammessi array con elementi misti di tipo stringa e numero"))
    }

    @Test
    void rejectsDeclaredComputedFields() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(model, '''
                m = matrix(rows: 2, cols: 2, determinant: 5.0)
            ''')
        }
        assertTrue(ex.message.contains("campo calcolato: lo popola il provider o la regola entity-eca in Moqui"))
    }

    @Test
    void defaultsMathModelStatusToDraft() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        MathMeta meta = MathDsl.math(model) {
            MathModelDef('TestDef')
            MathModel('TestModel', mathModelDefId: 'TestDef', statusFlowId: 'MathModelStatusFlow')
        }
        ModelValue defRecord = meta.entity('MathModel').findByName('TestModel')
        assertNotNull(defRecord)
        assertEquals('MathModelDraft', defRecord.get('statusId'))
    }

    @Test
    void rejectsIdentifiersLongerThan250Characters() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        String veryLongId = 'A' * 251
        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.math(model) {
                matrix(veryLongId, rows: 2, cols: 2)
            }
        }
        assertTrue(ex.message.contains("supera la lunghezza massima di 250 caratteri"))
    }
}
