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

    @Test
    void structuralComparisonDetectsSwappedOperands() {
        MathMeta meta1 = MathDsl.math {
            matrix('A', rows: 2, cols: 2)
            matrix('B', rows: 2, cols: 2)
            Transformation('T1', transformationTypeEnumId: 'TtMatrixProduct') {
                TransformationOperand(operandIndex: 1L, operandTypeEnumId: 'TotLeftMatrix', operandMatrixId: 'A')
                TransformationOperand(operandIndex: 2L, operandTypeEnumId: 'TotRightMatrix', operandMatrixId: 'B')
            }
        }

        MathMeta meta2 = MathDsl.math {
            matrix('A', rows: 2, cols: 2)
            matrix('B', rows: 2, cols: 2)
            Transformation('T1', transformationTypeEnumId: 'TtMatrixProduct') {
                TransformationOperand(operandIndex: 1L, operandTypeEnumId: 'TotLeftMatrix', operandMatrixId: 'B')
                TransformationOperand(operandIndex: 2L, operandTypeEnumId: 'TotRightMatrix', operandMatrixId: 'A')
            }
        }

        assertFalse(CanonicalDump.structuralEquals(meta1, meta2))
        assertThrows(AssertionError) { CanonicalDump.assertStructuralEquals(meta1, meta2) }
    }

    @Test
    void structuralComparisonDetectsRewiredEdge() {
        MathMeta meta1 = MathDsl.math {
            Graph('G1') {
                GraphVertex('v1', label: 'v1')
                GraphVertex('v2', label: 'v2')
                GraphVertex('v3', label: 'v3')
                GraphEdge('e1', label: 'e1', fromVertexId: 'v1', toVertexId: 'v2')
            }
        }

        MathMeta meta2 = MathDsl.math {
            Graph('G1') {
                GraphVertex('v1', label: 'v1')
                GraphVertex('v2', label: 'v2')
                GraphVertex('v3', label: 'v3')
                GraphEdge('e1', label: 'e1', fromVertexId: 'v1', toVertexId: 'v3')
            }
        }

        assertFalse(CanonicalDump.structuralEquals(meta1, meta2))
        assertThrows(AssertionError) { CanonicalDump.assertStructuralEquals(meta1, meta2) }
    }

    @Test
    void structuralComparisonDetectsDifferentDataTarget() {
        MathMeta meta1 = MathDsl.math {
            MathModelDef('Def1', modelType: Lp) {
                vector('c1', dimension: 2)
                vector('c2', dimension: 2)
                MathModel('Model1') {
                    MathModelData('d1', purpose: CostVector, vectorId: 'c1')
                }
            }
        }

        MathMeta meta2 = MathDsl.math {
            MathModelDef('Def1', modelType: Lp) {
                vector('c1', dimension: 2)
                vector('c2', dimension: 2)
                MathModel('Model1') {
                    MathModelData('d1', purpose: CostVector, vectorId: 'c2')
                }
            }
        }

        assertFalse(CanonicalDump.structuralEquals(meta1, meta2))
        assertThrows(AssertionError) { CanonicalDump.assertStructuralEquals(meta1, meta2) }
    }

    @Test
    void structuralComparisonIgnoresOnlyDerivedIdentifiers() {
        // Model 1: auto-nested matrix inside MathModel (gets derived id 'Model1_Data_A')
        MathMeta meta1 = MathDsl.math {
            MathModelDef('Def1', modelType: Lp) {
                MathModel('Model1') {
                    matrix('A', rows: 2, cols: 2, purpose: ConstraintMatrix)
                }
            }
        }

        // Model 2: explicit custom id for MathModelData pointing to same matrix A
        MathMeta meta2 = MathDsl.math {
            MathModelDef('Def1', modelType: Lp) {
                matrix('A', rows: 2, cols: 2)
                MathModel('Model1') {
                    MathModelData('customDataId1', purpose: ConstraintMatrix, matrixId: 'A', sequenceNum: 0L, dataTypeEnumId: 'MmdtMatrix')
                }
            }
        }

        assertTrue(CanonicalDump.structuralEquals(meta1, meta2))
        assertDoesNotThrow({ CanonicalDump.assertStructuralEquals(meta1, meta2) } as org.junit.jupiter.api.function.Executable)
    }
}
