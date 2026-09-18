/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class CanonicalDumpTest {

    @Test
    void testCanonicalDumpDeterminismAndEquality() {
        MathMeta meta1 = MathDsl.math {
            MathModelDef('ModelDefA', modelType: LinearAlgebra) {
                MathModel('ModelA', description: 'Model A') {
                    Matrix('Mat1', domainSpace: R2, codomainSpace: R2, rows: 2, cols: 2, name: 'Mat1', symbol: 'M1')
                }
            }
        }

        MathMeta meta2 = MathDsl.math {
            MathModelDef('ModelDefA', modelType: LinearAlgebra) {
                MathModel('ModelA', description: 'Model A') {
                    Matrix('Mat1', domainSpace: R2, codomainSpace: R2, rows: 2, cols: 2, name: 'Mat1', symbol: 'M1')
                }
            }
        }

        String dump1 = CanonicalDump.dump(meta1)
        String dump2 = CanonicalDump.dump(meta2)

        assertEquals(dump1, dump2)
        assertTrue(CanonicalDump.equals(meta1, meta2))
        assertDoesNotThrow({ CanonicalDump.assertEquals(meta1, meta2) } as org.junit.jupiter.api.function.Executable)
    }

    @Test
    void testNakedSymbolsInMathDsl() {
        MathMeta meta = MathDsl.math {
            MathModelDef('QPModelDef', modelType: QuadraticProgram) {
                MathModel('QPModel', description: 'QP Model') {
                    Matrix('Q', matrixTypeEnum: MatrixType.Dense, domainSpace: R2, codomainSpace: R2, rows: 2, cols: 2, name: 'Q', symbol: 'Q')
                    parameters('objSense', parameterDefId: 'OptDef', symbolicValue: minimize)
                }
            }
        }

        assertNotNull(meta.entity('MathModelDef').findByName('QPModelDef'))
        assertEquals('MmtQp', meta.entity('MathModelDef').findByName('QPModelDef').get('modelTypeEnumId'))
        assertEquals('MtDense', meta.entity('Matrix').findByName('Q').get('matrixTypeEnumId'))
        assertEquals('MINIMIZE', meta.entity('Parameter').findByName('objSense').get('symbolicValue'))
    }

    @Test
    void testStructuralEquivalence() {
        MathMeta meta1 = MathDsl.math {
            MathModelDef('ModelDefA', modelType: Lp) {
                MathModel('ModelA') {
                    matrix('A', rows: 2, cols: 2)
                }
            }
        }
        MathMeta meta2 = MathDsl.math {
            MathModelDef('ModelDefA', modelType: Lp) {
                MathModel('ModelA') {
                    matrix('A', rows: 2, cols: 2)
                }
            }
        }
        assertTrue(CanonicalDump.structuralEquals(meta1, meta2))
        assertDoesNotThrow({ CanonicalDump.assertStructuralEquals(meta1, meta2) } as org.junit.jupiter.api.function.Executable)
    }
}
