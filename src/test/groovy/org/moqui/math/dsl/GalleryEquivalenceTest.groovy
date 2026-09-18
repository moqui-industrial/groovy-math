/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

class GalleryEquivalenceTest {

    @ParameterizedTest(name = "Equivalence of {0}")
    @ValueSource(strings = [
        'matrix-product.groovy',
        'matrix-product-plan.groovy',
        'matrix-decomposition-plan.groovy',
        'production-plan.groovy',
        'energy-dispatch.groovy',
        'opencv-vision-pipeline.groovy',
        'openfoam-cavity.groovy',
        'jena-knowledge-graph.groovy',
        'product-catalog-graph.groovy'
    ])
    void testCanonicalAndCompactEquivalence(String filename) {
        File canonicalFile = new File("examples/canonical/${filename}")
        File compactFile = new File("examples/${filename}")

        assertTrue(canonicalFile.exists(), "Canonical example file does not exist: ${canonicalFile.path}")
        assertTrue(compactFile.exists(), "Compact example file does not exist: ${compactFile.path}")

        MathMeta canonicalMeta = MathDsl.evaluate(canonicalFile)
        MathMeta compactMeta = MathDsl.evaluate(compactFile)

        assertNotNull(canonicalMeta, "Failed to evaluate canonical file: ${filename}")
        assertNotNull(compactMeta, "Failed to evaluate compact file: ${filename}")

        CanonicalDump.assertStructuralEquals(canonicalMeta, compactMeta)
    }
}
