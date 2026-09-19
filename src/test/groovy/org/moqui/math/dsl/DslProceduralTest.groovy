/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.codehaus.groovy.control.MultipleCompilationErrorsException
import org.junit.jupiter.api.Test
import org.moqui.math.entity.ModelValue
import static org.junit.jupiter.api.Assertions.*

class DslProceduralTest {

    @Test
    void defWithExplicitIdAllowed() {
        String script = '''
x = tensor('x_in', [1, 4])
def h = layerNorm('h0.ln1', x)
def mat = matrix('A', rows: 2, cols: 2)
'''
        MathMeta meta = MathDsl.evaluate(script)
        assertNotNull(meta.entity('Tensor').findByName('h0.ln1'))
        assertNotNull(meta.entity('Matrix').findByName('A'))
    }

    @Test
    void defWithoutIdRejected() {
        String script = '''
x = tensor('x_in', [1, 4])
def h = layerNorm(x)
'''
        MultipleCompilationErrorsException ex = assertThrows(MultipleCompilationErrorsException) {
            MathDsl.evaluate(script)
        }
        assertTrue(ex.message.contains("usa 'h = layerNorm(...)' senza 'def': con 'def' il nome non entra nel modello"))
    }

    @Test
    void handleReassignmentInLoopAllowed() {
        String script = '''
x = tensor('x_in', [1, 4])
for (int i = 0; i < 4; i++) {
    x = layerNorm("ln_${i}", x)
}
'''
        MathMeta meta = MathDsl.evaluate(script)
        for (int i = 0; i < 4; i++) {
            assertNotNull(meta.entity('Tensor').findByName("ln_${i}"))
        }
    }

    @Test
    void helperClosureGeneratesNTensors() {
        String script = '''
def makeTensor = { String key, List shape ->
    tensor(key, shape: shape, rank: shape.size())
}

t1 = makeTensor('T1', [2, 3])
t2 = makeTensor('T2', [4, 5])
t3 = makeTensor('T3', [1, 8])
'''
        MathMeta meta = MathDsl.evaluate(script)
        assertNotNull(meta.entity('Tensor').findByName('T1'))
        assertNotNull(meta.entity('Tensor').findByName('T2'))
        assertNotNull(meta.entity('Tensor').findByName('T3'))
    }

    @Test
    void twelveBlocksGeneratedInLoopCompilesAndEvaluates() {
        String script = '''
MathModelDef('TwelveLayerModel', type: DeepNeuralNetwork, usage: Inference) {
    MathModel('GPTBlocks', alias: 'gpt_blocks', source: Manual, status: Draft) {
        x = tensor('Input', shape: [1, 16, 768], rank: 3)
        for (int i = 0; i < 12; i++) {
            h = layerNorm("h.${i}.ln1", x)
            attn = multiHeadAttention("h.${i}.attn", h, numHeads: 12, embedDim: 768)
            x = tensorAdd("h.${i}.add1", x, attn)
            h2 = layerNorm("h.${i}.ln2", x)
            w = tensor("h.${i}.mlp_w", shape: [768, 3072], rank: 2)
            mlp = affine("h.${i}.mlp", h2, w)
            w_proj = tensor("h.${i}.proj_w", shape: [3072, 768], rank: 2)
            mlp_out = affine("h.${i}.mlp_proj", mlp, w_proj)
            x = tensorAdd("h.${i}.add2", x, mlp_out)
        }
    }
}
'''
        MathMeta meta = MathDsl.evaluate(script)
        assertEquals(1, meta.entity('MathModelDef').size())
        assertEquals(1, meta.entity('MathModel').size())
        for (int i = 0; i < 12; i++) {
            assertNotNull(meta.entity('Tensor').findByName("h.${i}.ln1"))
            assertNotNull(meta.entity('Tensor').findByName("h.${i}.attn"))
            assertNotNull(meta.entity('Tensor').findByName("h.${i}.add1"))
            assertNotNull(meta.entity('Tensor').findByName("h.${i}.ln2"))
            assertNotNull(meta.entity('Tensor').findByName("h.${i}.mlp"))
            assertNotNull(meta.entity('Tensor').findByName("h.${i}.add2"))
        }
    }
}
