/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.builder

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.dsl.MathModelSolvingMethod
import org.moqui.math.dsl.MathModelType
import org.moqui.math.dsl.MathSpace
import org.moqui.math.dsl.MatrixPurpose
import org.moqui.math.dsl.NormDomain
import org.moqui.math.dsl.NormOrder
import org.moqui.math.dsl.TensorDecompMethod
import org.moqui.math.dsl.TransformationType
import org.moqui.math.dsl.TriangularExtractionType
import org.moqui.math.metamodel.EntityRef
import org.moqui.math.metamodel.GraphVertex_
import org.moqui.math.metamodel.Graph_
import org.moqui.math.metamodel.Matrix_
import org.moqui.math.metamodel.Transformation_
import org.moqui.math.model.GraphVertex
import org.moqui.math.model.Matrix

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull

@CompileStatic
class FluentMathTest {

    @Test
    void testFluentTypeSafeModelDefinition() {
        MathMeta mathMeta = MathDsl.fluent {
            modelDef('VisionPipelineDef') {
                name 'Vision Processing Pipeline'
                modelType MathModelType.ComputerVision

                model('EdgeDetectionModel') {
                    description 'Gaussian Smoothing and Sobel Gradient'
                    solvingMethod MathModelSolvingMethod.OpenCv

                    matrix('InputImage') {
                        rows 8
                        cols 8
                        purpose MatrixPurpose.Original
                        domainSpace MathSpace.R2
                        codomainSpace MathSpace.R2
                    }

                    transformation('BlurStep') {
                        name 'Gaussian Filter'
                        type TransformationType.GaussianBlur
                    }

                    transformation('SobelStep') {
                        name 'Sobel Gradient'
                        type TransformationType.Sobel
                    }
                }
            }

            graph('KnowledgeGraph') {
                name 'Research Entity Graph'
                EntityRef<GraphVertex> alice = vertex('Alice') {
                    label 'Alice Cooper'
                    parameter('role', 'Scientist')
                }
                EntityRef<GraphVertex> bob = vertex('Bob') {
                    label 'Bob Martin'
                }
                connect(alice, bob, 'collaboratesWith', 1.0)
            }
        }

        assertNotNull(mathMeta)
        assertEquals('Vision Processing Pipeline', mathMeta.entity('MathModelDef').findByName('VisionPipelineDef').get('modelName'))
        assertEquals(8L, mathMeta.entity('Matrix').findByName('InputImage').get(Matrix_.rows.name))
        assertEquals(8L, mathMeta.entity('Matrix').findByName('InputImage').get(Matrix_.cols.name))
        assertEquals('Research Entity Graph', mathMeta.entity('Graph').findByName('KnowledgeGraph').get(Graph_.name.name))
        assertEquals('Alice Cooper', mathMeta.entity('GraphVertex').findByName('Alice').get(GraphVertex_.label.name))
    }

    @Test
    void testStandaloneTransformationWithOperands() {
        MathMeta mathMeta = MathDsl.fluent {
            matrix('A', rows: 2, cols: 3, name: 'Matrix A')
            matrix('B', rows: 3, cols: 2, data: [[7, 8], [9, 10], [11, 12]])
            matrix('C', rows: 2, cols: 2, name: 'Matrix C')

            transformation('MultiplyAB') {
                type TransformationType.MatrixProduct
                name 'Matrix product A x B'
                leftMatrix 'A'
                rightMatrix 'B'
                resultMatrix 'C'
            }
        }

        assertNotNull(mathMeta)
        assertEquals(2L, mathMeta.entity('Matrix').findByName('A').get(Matrix_.rows.name))
        assertEquals('[[7,8],[9,10],[11,12]]', mathMeta.entity('Matrix').findByName('B').get('componentArray'))
        assertEquals('C', mathMeta.entity('Transformation').findByName('MultiplyAB').get(Transformation_.resultMatrixId.name))

        // Check operands
        assertNotNull(mathMeta.entity('TransformationOperand').findByName('MultiplyAB_Op_0'))
        assertEquals('A', mathMeta.entity('TransformationOperand').findByName('MultiplyAB_Op_0').get('operandMatrixId'))
        assertEquals('TotLeftMatrix', mathMeta.entity('TransformationOperand').findByName('MultiplyAB_Op_0').get('operandTypeEnumId'))

        assertNotNull(mathMeta.entity('TransformationOperand').findByName('MultiplyAB_Op_1'))
        assertEquals('B', mathMeta.entity('TransformationOperand').findByName('MultiplyAB_Op_1').get('operandMatrixId'))
        assertEquals('TotRightMatrix', mathMeta.entity('TransformationOperand').findByName('MultiplyAB_Op_1').get('operandTypeEnumId'))
    }

    @Test
    void testSatelliteEntitiesDeclaration() {
        MathMeta mathMeta = MathDsl.fluent {
            matrix('A', rows: 4, cols: 4)

            diagonalExtraction('Diag_A', axisOffset: 1)
            triangularExtraction('Upper_A', type: TriangularExtractionType.Upper, extractionOffset: 0)
            bandExtraction('Band_A', lowerBand: -1, upperBand: 1)
            blockMatrixExtraction('Block_A', startRowBlock: 0, endRowBlock: 2, startColBlock: 1, endColBlock: 3)
            matrixDecomposition('Svd_A', leftMatrix: 'U', diagMatrix: 'Sigma', rightMatrix: 'Vt', rankApproximation: 2)
            normResult('Norm_A', domain: NormDomain.Matrix, order: NormOrder.MatFrobenius, normValue: 42.5)
            coordinateSystemTransformation('Coord_Change', sourceCoordSystem: 'World', targetCoordSystem: 'Camera', matrix: 'A')
            tensor('T_Input', dimensions: 3)
            tensorSlice('Slice_T', slice: [[axis: 0, start: 0, end: 5]])
            tensorDecomposition('Tucker_T', sourceTensor: 'T_Input', method: TensorDecompMethod.Tucker, factors: ['U0', 'U1', 'U2'])
        }

        assertNotNull(mathMeta)
        assertEquals(1L, mathMeta.entity('DiagonalExtraction').findByName('Diag_A').get('axisOffset'))
        assertEquals('TetUpper', mathMeta.entity('TriangularExtraction').findByName('Upper_A').get('extractionTypeEnumId'))
        assertEquals(-1L, mathMeta.entity('BandExtraction').findByName('Band_A').get('lowerBand'))
        assertEquals(2L, mathMeta.entity('BlockMatrixExtraction').findByName('Block_A').get('endRowBlock'))
        assertEquals('U', mathMeta.entity('MatrixDecomposition').findByName('Svd_A').get('leftMatrixId'))
        assertEquals(2L, mathMeta.entity('MatrixDecomposition').findByName('Svd_A').get('rankApproximation'))
        assertEquals('NoMatFrobenius', mathMeta.entity('NormResult').findByName('Norm_A').get('orderEnumId'))
        assertEquals('World', mathMeta.entity('CoordinateSystemTransformation').findByName('Coord_Change').get('sourceCoordinateSystemId'))
        assertEquals('[{"axis":0,"start":0,"end":5}]', mathMeta.entity('TensorSlice').findByName('Slice_T').get('sliceDefinitionJson'))
        assertEquals('TdmTucker', mathMeta.entity('TensorDecomposition').findByName('Tucker_T').get('decompositionMethodEnumId'))
        assertEquals('U1', mathMeta.entity('TensorDecompositionFactor').findByName('Tucker_T_Mode_1').get('factorMatrixId'))
    }

    @Test
    void testSatelliteEntitiesWithClosuresAndOperands() {
        MathMeta mathMeta = MathDsl.fluent {
            matrix('A', rows: 4, cols: 4)

            matrixDecomposition('Svd_Plan', type: TransformationType.Svd) {
                operandMatrix 'A'
                leftMatrix 'U'
                diagMatrix 'Sigma'
                rightMatrix 'Vt'
                rankApproximation 4
            }

            diagonalExtraction('Diag_Plan', axisOffset: 0) {
                operandMatrix 'A'
                resultVector 'd'
            }

            triangularExtraction('Tri_Plan', type: TriangularExtractionType.Upper) {
                operandMatrix 'A'
                resultMatrix 'U_tri'
            }
        }

        assertNotNull(mathMeta)
        assertEquals('U', mathMeta.entity('MatrixDecomposition').findByName('Svd_Plan').get('leftMatrixId'))
        assertEquals('TtSvd', mathMeta.entity('Transformation').findByName('Svd_Plan').get('transformationTypeEnumId'))
        assertNotNull(mathMeta.entity('TransformationOperand').findByName('Svd_Plan_Op_0'))
        assertEquals('A', mathMeta.entity('TransformationOperand').findByName('Svd_Plan_Op_0').get('operandMatrixId'))

        assertEquals('d', mathMeta.entity('Transformation').findByName('Diag_Plan').get('resultVectorId'))
        assertEquals('A', mathMeta.entity('TransformationOperand').findByName('Diag_Plan_Op_0').get('operandMatrixId'))

        assertEquals('U_tri', mathMeta.entity('Transformation').findByName('Tri_Plan').get('resultMatrixId'))
    }
}
