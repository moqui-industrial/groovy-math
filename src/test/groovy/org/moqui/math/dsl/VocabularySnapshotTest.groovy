/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import org.moqui.math.moqui.MoquiSchemaInspector

import static org.junit.jupiter.api.Assertions.*

class VocabularySnapshotTest {

    @Test
    void testVocabularySnapshotMatchesGeneratedMarkdown() {
        DslVocabulary vocab = DslVocabulary.of(MoquiSchemaInspector.embedded())
        String generatedMarkdown = vocab.generateMarkdownSnapshot()

        File snapshotFile = new File('docs/dsl/vocabulary.md')
        assertTrue(snapshotFile.exists(), "docs/dsl/vocabulary.md must exist")

        String onDisk = snapshotFile.text
        assertEquals(generatedMarkdown, onDisk, "docs/dsl/vocabulary.md must match schema-derived vocabulary snapshot exactly")
    }
}
