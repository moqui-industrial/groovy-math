/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class MathDslPrettyPrinterTest {

    @Test
    void testRoundTripMatrixProduct() {
        File originalFile = new File('examples/matrix-product.groovy')
        MathMeta originalMeta = MathDsl.evaluate(originalFile)

        String printedDsl = MathDslPrettyPrinter.print(originalMeta)
        assertNotNull(printedDsl)
        assertFalse(printedDsl.trim().isEmpty())

        println("--- PRETTY PRINTED DSL ---")
        println(printedDsl)
        println("--------------------------")

        File tempFile = File.createTempFile("pretty-printed-matrix-product", ".groovy")
        try {
            tempFile.text = printedDsl
            MathMeta reEvaluatedMeta = MathDsl.evaluate(tempFile)

            String originalDump = CanonicalDump.dump(originalMeta)
            String reEvaluatedDump = CanonicalDump.dump(reEvaluatedMeta)
            assertEquals(originalDump, reEvaluatedDump,
                "CanonicalDump of re-evaluated pretty-printed DSL must match original")
        } finally {
            tempFile.delete()
        }
    }
}
