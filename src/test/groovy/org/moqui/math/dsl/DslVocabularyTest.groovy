/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import org.moqui.math.moqui.MoquiSchemaInspector

import static org.junit.jupiter.api.Assertions.*

class DslVocabularyTest {

    @Test
    void testVocabularyDerivationFromSchema() {
        DslVocabulary vocab = DslVocabulary.of(MoquiSchemaInspector.embedded())

        // Entities and keywords
        assertTrue(vocab.hasEntity('moqui.math.MathModel'))
        assertTrue(vocab.hasEntity('MathModel'))
        assertTrue(vocab.hasEntity('mathModel'))
        assertTrue(vocab.hasEntity('Matrix'))
        assertTrue(vocab.hasEntity('matrix'))
        assertTrue(vocab.hasEntity('Vector'))
        assertTrue(vocab.hasEntity('vector'))
        assertTrue(vocab.hasEntity('Tensor'))
        assertTrue(vocab.hasEntity('tensor'))
        assertTrue(vocab.hasEntity('Parameter'))
        assertTrue(vocab.hasEntity('parameter'))
        assertTrue(vocab.hasEntity('Transformation'))
        assertTrue(vocab.hasEntity('transformation'))
        assertFalse(vocab.hasEntity('nonExistentEntityXYZ'))

        // Transformation entities derived dynamically
        assertTrue(vocab.isTransformationEntity('moqui.math.Transformation'))
        assertTrue(vocab.isTransformationEntity('moqui.math.DiagonalExtraction'))
        assertTrue(vocab.isTransformationEntity('moqui.math.MatrixDecomposition'))
        assertTrue(vocab.isTransformationEntity('moqui.math.TensorSlice'))

        // Operands derived dynamically from TransformationOperandType
        assertEquals('TotLeftMatrix', vocab.getOperandTypeEnumId('leftMatrix'))
        assertEquals('TotRightMatrix', vocab.getOperandTypeEnumId('rightMatrix'))
        assertEquals('TotMatrix', vocab.getOperandTypeEnumId('operandMatrix'))
        assertEquals('TotParameter', vocab.getOperandTypeEnumId('operandParameter'))
        assertEquals('TotTransformation', vocab.getOperandTypeEnumId('operandTransformation'))
    }

    @Test
    void testNakedSymbolResolution() {
        DslVocabulary vocab = DslVocabulary.of(MoquiSchemaInspector.embedded())

        // Objective sense
        DslSymbol minUpper = vocab.resolveSymbol('Minimize')
        assertNotNull(minUpper)
        assertEquals('MINIMIZE', minUpper.id)

        DslSymbol minLower = vocab.resolveSymbol('minimize')
        assertNotNull(minLower)
        assertEquals('MINIMIZE', minLower.id)

        // MathModelType
        DslSymbol qp = vocab.resolveSymbol('QuadraticProgram')
        assertNotNull(qp)
        assertEquals('MmtQp', qp.id)

        DslSymbol lp = vocab.resolveSymbol('LinearProgram')
        assertNotNull(lp)
        assertEquals('MmtLp', lp.id)

        // DataType
        DslSymbol f32 = vocab.resolveSymbol('Float32')
        assertNotNull(f32)
        assertEquals('DtFloat32', f32.id)

        // DeviceType
        DslSymbol cpu = vocab.resolveSymbol('Cpu')
        assertNotNull(cpu)
        assertEquals('DevCpu', cpu.id)

        DslSymbol cuda = vocab.resolveSymbol('Cuda')
        assertNotNull(cuda)
        assertEquals('DevCuda', cuda.id)
    }

    @Test
    void testAmbiguousSymbolThrowsException() {
        DslVocabulary vocab = DslVocabulary.of(MoquiSchemaInspector.embedded())
        if (!vocab.ambiguousSymbols.isEmpty()) {
            String ambiguousName = vocab.ambiguousSymbols.keySet().first()
            IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
                vocab.resolveSymbol(ambiguousName)
            }
            assertTrue(ex.message.contains("Ambiguous DSL symbol '${ambiguousName}'"))
        }
    }
}
