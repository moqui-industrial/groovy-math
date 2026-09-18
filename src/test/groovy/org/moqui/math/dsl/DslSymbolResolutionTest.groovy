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

class DslSymbolResolutionTest {

    @Test
    void resolvesBareSymbolContextuallyByFieldEnumType() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        MathMeta meta = MathDsl.math(model) {
            MathModelDef('TestDef', type: Lp) {
                MathModel('TestModel') {
                    matrix('A', rows: 2, cols: 2, type: Dense)
                }
            }
        }
        ModelValue modelDefRecord = meta.entity('MathModelDef').findByName('TestDef')
        assertNotNull(modelDefRecord)
        assertEquals('MmtLp', modelDefRecord.get('modelTypeEnumId'))

        ModelValue matRecord = meta.entity('Matrix').findByName('A')
        assertNotNull(matRecord)
        assertEquals('MtDense', matRecord.get('matrixTypeEnumId'))
    }

    @Test
    void rejectsBareSymbolOnNonEnumerationField() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.math(model) {
                A = matrix(rows: UnknownSymbol, cols: 2)
            }
        }
        assertTrue(ex.message.contains("has no known enumeration domain") || ex.message.contains("cannot resolve bare symbol"))
    }

    @Test
    void rejectsInvalidBareSymbolWithCandidatesAndLevenshteinSuggestion() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.math(model) {
                A = matrix(rows: 2, cols: 2, type: Denz)
            }
        }
        assertTrue(ex.message.contains("Invalid symbol 'Denz'"))
        assertTrue(ex.message.contains("MatrixType"))
        assertTrue(ex.message.toLowerCase().contains("dense"))
    }
}
