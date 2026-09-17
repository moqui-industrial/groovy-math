/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

enum MathModelSolvingMethod implements DslEnumValue {
    Simplex('MmsmSimplex'),
    InteriorPoint('MmsmInteriorPoint'),
    OpenCv('MmsmOpenCv'),
    Jax('MmsmJax'),
    JaxJit('MmsmJaxJit'),
    LibTorch('MmsmLibTorch'),
    LibTorchTraining('MmsmLibTorchTraining'),
    PetscTao('MmsmPetscTao'),
    OrTools('MmsmOrTools'),
    OpenFoam('MmsmOpenFoam'),
    OpenFoamIcoFoam('MmsmOpenFoamIcoFoam'),
    OpenFoamSimpleFoam('MmsmOpenFoamSimpleFoam'),
    Onnx('MmsmOnnx')

    final String id

    MathModelSolvingMethod(final String id) {
        this.id = id
    }
}
