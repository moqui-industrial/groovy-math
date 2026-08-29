/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta
import groovy.math.libtorch.LibTorchResult

@CompileStatic
class MathDispatcherTest {

    @Test
    @Tag('libtorch-native')
    void testDispatchToPyTorch() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES') ?: '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'
        MathMeta mathMeta = MathDsl.evaluate(
            new File(schemaPath),
            new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy'))

        def result = Math.execute(mathMeta, 'MatrixProduct') {
            input 'A', [[1, 2, 3], [4, 5, 6]]
        }

        assert result instanceof LibTorchResult
        LibTorchResult torchResult = (LibTorchResult) result
        assert torchResult.values.toList() == [58f, 64f, 139f, 154f]
    }
}
