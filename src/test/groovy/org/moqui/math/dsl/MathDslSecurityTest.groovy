/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.codehaus.groovy.control.MultipleCompilationErrorsException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir

import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue

class MathDslSecurityTest {

    @TempDir
    File tempDir

    @Test
    void rejectsSystemExit() {
        File maliciousFile = new File(tempDir, "malicious-exit.groovy")
        maliciousFile.text = """
            System.exit(1)
            MathModelDef('Hacked')
        """

        Exception ex = assertThrows(Exception) {
            MathDsl.evaluate(maliciousFile)
        }
        assertTrue(ex instanceof MultipleCompilationErrorsException || ex instanceof SecurityException,
            "Expected compilation/security exception but got: ${ex.class.name}: ${ex.message}")
    }

    @Test
    void rejectsRuntimeExec() {
        File maliciousFile = new File(tempDir, "malicious-exec.groovy")
        maliciousFile.text = """
            Runtime.getRuntime().exec("echo pwned")
            MathModelDef('Hacked')
        """

        Exception ex = assertThrows(Exception) {
            MathDsl.evaluate(maliciousFile)
        }
        assertTrue(ex instanceof MultipleCompilationErrorsException || ex instanceof SecurityException,
            "Expected compilation/security exception but got: ${ex.class.name}: ${ex.message}")
    }

    @Test
    void rejectsFileConstruction() {
        File maliciousFile = new File(tempDir, "malicious-file.groovy")
        maliciousFile.text = """
            new File("/tmp/should_not_exist").delete()
            MathModelDef('Hacked')
        """

        Exception ex = assertThrows(Exception) {
            MathDsl.evaluate(maliciousFile)
        }
        assertTrue(ex instanceof MultipleCompilationErrorsException || ex instanceof SecurityException,
            "Expected compilation/security exception but got: ${ex.class.name}: ${ex.message}")
    }

    @Test
    void rejectsProcessBuilder() {
        File maliciousFile = new File(tempDir, "malicious-pb.groovy")
        maliciousFile.text = """
            new ProcessBuilder("echo", "pwned").start()
            MathModelDef('Hacked')
        """

        Exception ex = assertThrows(Exception) {
            MathDsl.evaluate(maliciousFile)
        }
        assertTrue(ex instanceof MultipleCompilationErrorsException || ex instanceof SecurityException,
            "Expected compilation/security exception but got: ${ex.class.name}: ${ex.message}")
    }

    @Test
    void allowsLegitimateMathDsl() {
        File validFile = new File(tempDir, "valid-math.groovy")
        validFile.text = """
            MathModelDef('SafeModel', description: 'Safe math declaration') {
                MathModel('SafeClassifier') {
                    statusId 'MathModelDraft'
                }
            }
        """

        MathMeta meta = MathDsl.evaluate(validFile).validate()
        assertTrue(meta.size() >= 2)
    }
}
