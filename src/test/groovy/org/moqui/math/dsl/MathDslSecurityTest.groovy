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
    void rejectsStringExecutePayload() {
        // Payload 1: 'touch /tmp/x'.execute()
        File maliciousFile = new File(tempDir, "exploit-execute.groovy")
        maliciousFile.text = """
            'touch /tmp/x'.execute()
            MathModelDef('Hacked')
        """

        Exception ex = assertThrows(Exception) {
            MathDsl.evaluate(maliciousFile)
        }
        assertTrue(ex instanceof MultipleCompilationErrorsException || ex instanceof SecurityException,
            "Expected compilation/security exception but got: ${ex.class.name}: ${ex.message}")
    }

    @Test
    void rejectsReflectionClassForNamePayload() {
        // Payload 2: Class.forName('java.lang.Runtime').getMethod('getRuntime').invoke(null).exec('touch /tmp/x')
        File maliciousFile = new File(tempDir, "exploit-reflection.groovy")
        maliciousFile.text = """
            Class.forName('java.lang.Runtime').getMethod('getRuntime').invoke(null).exec('touch /tmp/x')
            MathModelDef('Hacked')
        """

        Exception ex = assertThrows(Exception) {
            MathDsl.evaluate(maliciousFile)
        }
        assertTrue(ex instanceof MultipleCompilationErrorsException || ex instanceof SecurityException,
            "Expected compilation/security exception but got: ${ex.class.name}: ${ex.message}")
    }

    @Test
    void rejectsEvaluatePayload() {
        // Payload 3: evaluate("'touch /tmp/x'.execute()")
        File maliciousFile = new File(tempDir, "exploit-evaluate.groovy")
        maliciousFile.text = """
            evaluate("'touch /tmp/x'.execute()")
            MathModelDef('Hacked')
        """

        Exception ex = assertThrows(Exception) {
            MathDsl.evaluate(maliciousFile)
        }
        assertTrue(ex instanceof MultipleCompilationErrorsException || ex instanceof SecurityException,
            "Expected compilation/security exception but got: ${ex.class.name}: ${ex.message}")
    }

    @Test
    void rejectsDynamicRuntimeVariablePayload() {
        // Payload 4: def rt = Runtime; rt.getRuntime().exec('touch /tmp/x')
        File maliciousFile = new File(tempDir, "exploit-runtime-var.groovy")
        maliciousFile.text = """
            def rt = Runtime
            rt.getRuntime().exec('touch /tmp/x')
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
