/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.dsl

enum MathModelSolvingMethod implements DslEnumValue {
    Simplex('MmsmSimplex'),
    InteriorPoint('MmsmInteriorPoint'),
    OpenCv('MmsmOpenCv'),
    Jax('MmsmJax'),
    LibTorch('MmsmLibTorch'),
    PetscTao('MmsmPetscTao'),
    OrTools('MmsmOrTools')

    final String id

    MathModelSolvingMethod(final String id) {
        this.id = id
    }
}
