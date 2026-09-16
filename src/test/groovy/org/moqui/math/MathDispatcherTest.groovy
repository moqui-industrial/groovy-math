/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.libtorch.LibTorchResult

@CompileStatic
class MathDispatcherTest {

    @Test
    @Tag('libtorch-native')
    void testDispatchToPyTorch() {
        MathMeta mathMeta = MathDsl.evaluate(
            new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy'))

        def result = MathEngine.execute(mathMeta, 'MatrixProduct') {
            input 'A', [[1, 2, 3], [4, 5, 6]]
        }

        assert result instanceof LibTorchResult
        LibTorchResult torchResult = (LibTorchResult) result
        assert torchResult.values.toList() == [58f, 64f, 139f, 154f]
    }
}
