/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.builder

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test
import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta
import groovy.math.dsl.MathModelSolvingMethod
import groovy.math.dsl.MathModelType
import groovy.math.dsl.MathSpace
import groovy.math.dsl.MatrixPurpose
import groovy.math.dsl.TransformationType
import groovy.math.metamodel.EntityRef
import groovy.math.metamodel.GraphVertex_
import groovy.math.metamodel.Graph_
import groovy.math.metamodel.Matrix_
import groovy.math.model.Graph
import groovy.math.model.GraphVertex
import groovy.math.model.Matrix

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull

@CompileStatic
class FluentMathTest {

    @Test
    void testFluentTypeSafeModelDefinition() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES') ?: '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'
        File schemaFile = new File(schemaPath)

        MathMeta mathMeta = MathDsl.fluent(schemaFile) {
            modelDef('VisionPipelineDef') {
                name 'Vision Processing Pipeline'
                modelType MathModelType.ComputerVision

                model('EdgeDetectionModel') {
                    description 'Gaussian Smoothing and Sobel Gradient'
                    solvingMethod MathModelSolvingMethod.OpenCv

                    EntityRef<Matrix> inputImg = matrix('InputImage') {
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
}
