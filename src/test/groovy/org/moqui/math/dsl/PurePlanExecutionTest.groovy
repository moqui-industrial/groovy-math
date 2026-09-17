/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import org.moqui.math.MathEngine
import org.moqui.math.libtorch.LibTorchPlan
import org.moqui.math.libtorch.LibTorchProvider
import org.moqui.math.libtorch.LibTorchResult

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

class PurePlanExecutionTest {

    @Test
    void testPureMatrixProductPlanCompilation() {
        MathMeta meta = MathDsl.evaluate(
            new File(System.getProperty('user.dir'), 'examples/matrix-product-plan.groovy'))

        LibTorchProvider provider = new LibTorchProvider('MultiplyAB')
        LibTorchPlan plan = provider.compile(meta)
        try {
            assertEquals('A', plan.inputName)
            assertEquals('C', plan.outputName)
            assertEquals(3, plan.inputWidth)
            assertEquals(2, plan.outputWidth)
            assertEquals(1, plan.operationCount)
        } finally {
            plan.close()
        }
    }

    @Test
    void testPurePlanExecutionWithSuppliedInput() {
        MathMeta meta = MathDsl.evaluate(
            new File(System.getProperty('user.dir'), 'examples/matrix-product-plan.groovy'))

        // Execute using MathEngine with supplied inputs
        Object result = MathEngine.execute(meta, 'MultiplyAB') {
            input 'A', [[1, 2, 3], [4, 5, 6]]
        }

        assertNotNull(result)
        assertTrue(result instanceof LibTorchResult)
        LibTorchResult torchResult = (LibTorchResult) result
        assertEquals('C', torchResult.tensorName)
        assertEquals(2, torchResult.batchSize)
        assertEquals(2, torchResult.width)
        assertEquals([58f, 64f, 139f, 154f], torchResult.values.toList())
    }

    @Test
    void testPurePlanExecutionWithPrePopulatedInputData() {
        // Define a pure plan where matrix A already carries its own data
        MathMeta meta = MathDsl.fluent {
            matrix('A', rows: 2, cols: 3, data: [[1, 2, 3], [4, 5, 6]])
            matrix('B', rows: 3, cols: 2, data: [[7, 8], [9, 10], [11, 12]])
            matrix('C', rows: 2, cols: 2)

            transformation('MultiplyAB') {
                type TransformationType.MatrixProduct
                leftMatrix 'A'
                rightMatrix 'B'
                resultMatrix 'C'
            }
        }

        // Execute directly via meta.execute() without passing input or ID
        Object result = meta.execute()

        assertNotNull(result)
        assertTrue(result instanceof LibTorchResult)
        LibTorchResult torchResult = (LibTorchResult) result
        assertEquals('C', torchResult.tensorName)
        assertEquals(2, torchResult.batchSize)
        assertEquals(2, torchResult.width)
        assertEquals([58f, 64f, 139f, 154f], torchResult.values.toList())
    }

    @Test
    void testLibTorchPanamaAvailability() {
        assertTrue(org.moqui.math.libtorch.LibTorchPanama.INSTANCE.isAvailable(),
            "LibTorch native library should be available after build")
    }
}
