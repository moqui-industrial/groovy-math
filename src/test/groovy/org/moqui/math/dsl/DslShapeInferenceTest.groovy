/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.json.JsonSlurper
import org.junit.jupiter.api.Test
import org.moqui.math.entity.ModelValue
import static org.junit.jupiter.api.Assertions.*

class DslShapeInferenceTest {

    private static List<Object> parseShape(ModelValue tensor) {
        assertNotNull(tensor)
        Object s = tensor.get('shape')
        assertNotNull(s, "Shape should not be null for ${tensor.modelKey}")
        if (s instanceof List) return (List<Object>) s
        new JsonSlurper().parseText(s.toString()) as List<Object>
    }

    // 1. Identical shape propagation
    @Test
    void testIdenticalShapePropagation() {
        String script = '''
x = tensor('x', shape: [2, 4, 8], rank: 3)
ln = layerNorm('ln', x)
rms = rmsNorm('rms', x)
gelu = tensorGelu('gelu', x)
relu = tensorReLu('relu', x)
sm = tensorSoftmax('sm', x)
drop = dropout('drop', x)
'''
        MathMeta meta = MathDsl.evaluate(script)
        assertEquals([2, 4, 8], parseShape(meta.entity('Tensor').findByName('ln')))
        assertEquals([2, 4, 8], parseShape(meta.entity('Tensor').findByName('rms')))
        assertEquals([2, 4, 8], parseShape(meta.entity('Tensor').findByName('gelu')))
        assertEquals([2, 4, 8], parseShape(meta.entity('Tensor').findByName('relu')))
        assertEquals([2, 4, 8], parseShape(meta.entity('Tensor').findByName('sm')))
        assertEquals([2, 4, 8], parseShape(meta.entity('Tensor').findByName('drop')))
    }

    // 2. Contraction: MatrixProduct, Affine, TensorContract
    @Test
    void testContractionFamilies() {
        String script = '''
A = matrix('A', rows: 2, cols: 3)
B = matrix('B', rows: 3, cols: 4)
C = A * B

x = tensor('x', shape: [2, 16, 64], rank: 3)
W = tensor('W', shape: [64, 128], rank: 2)
y = affine('y', x, W)

tA = tensor('tA', shape: [2, 3, 4], rank: 3)
tB = tensor('tB', shape: [4, 5], rank: 2)
tC = tensorContract('tC', tA, tB)
'''
        MathMeta meta = MathDsl.evaluate(script)
        ModelValue matC = meta.entity('Matrix').findByName('C')
        assertNotNull(matC)
        assertEquals(2, matC.get('rows'))
        assertEquals(4, matC.get('cols'))

        assertEquals([2, 16, 128], parseShape(meta.entity('Tensor').findByName('y')))
        assertEquals([2, 3, 5], parseShape(meta.entity('Tensor').findByName('tC')))
    }

    // 3. Element-wise arithmetic with NumPy broadcasting
    @Test
    void testElementWiseBroadcasting() {
        String script = '''
A = tensor('A', shape: [2, 1, 4], rank: 3)
B = tensor('B', shape: [3, 4], rank: 2)
add = tensorAdd('add', A, B)
sub = tensorSub('sub', A, B)
mul = tensorMul('mul', A, B)
div = tensorDiv('div', A, B)
pow = tensorPow('pow', A, B)
'''
        MathMeta meta = MathDsl.evaluate(script)
        assertEquals([2, 3, 4], parseShape(meta.entity('Tensor').findByName('add')))
        assertEquals([2, 3, 4], parseShape(meta.entity('Tensor').findByName('sub')))
        assertEquals([2, 3, 4], parseShape(meta.entity('Tensor').findByName('mul')))
        assertEquals([2, 3, 4], parseShape(meta.entity('Tensor').findByName('div')))
        assertEquals([2, 3, 4], parseShape(meta.entity('Tensor').findByName('pow')))
    }

    // 4. Reshape and Permute
    @Test
    void testReshapeAndPermute() {
        String script = '''
x = tensor('x', shape: [2, 3, 4], rank: 3)
r = tensorReshape('r', x, shape: [6, 4])
p = tensorPermute('p', x, dims: [2, 0, 1])
'''
        MathMeta meta = MathDsl.evaluate(script)
        assertEquals([6, 4], parseShape(meta.entity('Tensor').findByName('r')))
        assertEquals([4, 2, 3], parseShape(meta.entity('Tensor').findByName('p')))
    }

    // 5. Attention: ScaledDotProductAttention & MultiHeadAttention
    @Test
    void testAttentionSignatures() {
        String script = '''
Q = tensor('Q', shape: [2, 8, 16, 64], rank: 4)
K = tensor('K', shape: [2, 8, 16, 64], rank: 4)
V = tensor('V', shape: [2, 8, 16, 64], rank: 4)
sdpa = scaledDotProductAttention('sdpa', Q, K, V)

x = tensor('x', shape: [2, 16, 768], rank: 3)
mha = multiHeadAttention('mha', x, numHeads: 12, embedDim: 768)
'''
        MathMeta meta = MathDsl.evaluate(script)
        assertEquals([2, 8, 16, 64], parseShape(meta.entity('Tensor').findByName('sdpa')))
        assertEquals([2, 16, 768], parseShape(meta.entity('Tensor').findByName('mha')))
    }

    // 6. Embedding Lookup
    @Test
    void testEmbeddingLookup() {
        String script = '''
tokens = tensor('tokens', shape: [2, 16], rank: 2)
vocab = tensor('vocab', shape: [50257, 768], rank: 2)
emb = embedding('emb', tokens, vocab)
'''
        MathMeta meta = MathDsl.evaluate(script)
        assertEquals([2, 16, 768], parseShape(meta.entity('Tensor').findByName('emb')))
    }

    // 7. Reductions: Sum, Mean, Max, Min with axis and keepdim
    @Test
    void testReductionFamilies() {
        String script = '''
x = tensor('x', shape: [2, 3, 4], rank: 3)
s1 = tensorSum('s1', x, axis: 1)
s2 = tensorSum('s2', x, axis: 1, keepdim: true)
m1 = tensorMean('m1', x)
m2 = tensorMean('m2', x, keepdim: true)
mx = tensorMax('mx', x, axis: 2)
mn = tensorMin('mn', x, axis: 0, keepdim: true)
'''
        MathMeta meta = MathDsl.evaluate(script)
        assertEquals([2, 4], parseShape(meta.entity('Tensor').findByName('s1')))
        assertEquals([2, 1, 4], parseShape(meta.entity('Tensor').findByName('s2')))
        assertEquals([1], parseShape(meta.entity('Tensor').findByName('m1')))
        assertEquals([1, 1, 1], parseShape(meta.entity('Tensor').findByName('m2')))
        assertEquals([2, 3], parseShape(meta.entity('Tensor').findByName('mx')))
        assertEquals([1, 3, 4], parseShape(meta.entity('Tensor').findByName('mn')))
    }

    // 8. Symbolic Dimensions Propagation
    @Test
    void testSymbolicDimensionsPropagation() {
        String script = '''
x = tensor('x', shape: ['B', 'T', 768], rank: 3)
W = tensor('W', shape: [768, 3072], rank: 2)
proj = affine('proj', x, W)
'''
        MathMeta meta = MathDsl.evaluate(script)
        assertEquals(['B', 'T', 3072], parseShape(meta.entity('Tensor').findByName('proj')))
    }

    // 9. Missing shape leaves field empty without error
    @Test
    void testMissingShapeLeavesFieldEmptyWithoutError() {
        String script = '''
x = tensor('x')
ln = layerNorm('ln', x)
'''
        MathMeta meta = MathDsl.evaluate(script)
        ModelValue lnTensor = meta.entity('Tensor').findByName('ln')
        assertNotNull(lnTensor)
        assertNull(lnTensor.get('shape'))
    }

    // 10. Three rejected incompatibility cases
    @Test
    void rejectsContractionWithMismatchingDimensions() {
        String script = '''
A = matrix('A', rows: 2, cols: 3)
B = matrix('B', rows: 4, cols: 2)
C = A * B
'''
        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(script)
        }
        assertTrue(ex.message.contains('Incompatible shapes for matrix product: [2, 3] and [4, 2]'))
    }

    @Test
    void rejectsReshapeWithDifferentElementCount() {
        String script = '''
x = tensor('x', shape: [2, 3], rank: 2)
r = tensorReshape('r', x, shape: [2, 4])
'''
        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(script)
        }
        assertTrue(ex.message.contains('Reshape element count mismatch: input shape [2, 3] (6 elements) != target shape [2, 4] (8 elements)'))
    }

    @Test
    void rejectsMhaWithDimensionNotDivisibleByNumHeads() {
        String script = '''
x = tensor('x', shape: [2, 16, 768], rank: 3)
mha = multiHeadAttention('mha', x, numHeads: 7, embedDim: 768)
'''
        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(script)
        }
        assertTrue(ex.message.contains('Embedding dimension 768 is not divisible by numHeads 7'))
    }

    // 11. Verification that matrix-product.groovy evaluates without explicit rows/cols on C
    @Test
    void testMatrixProductExampleEvaluatesWithoutExplicitDimensionsOnC() {
        MathMeta meta = MathDsl.evaluate(new File('examples/matrix-product.groovy'))
        assertNotNull(meta.entity('Matrix').findByName('C'))
    }
}
