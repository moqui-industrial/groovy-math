/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.dsl

enum TransformationType implements DslEnumValue {
    MatrixProduct('TtMatrixProduct'),
    TensorReLu('TtTensorReLu'),
    Affine('TtAffine'),
    GaussianBlur('TtGaussianBlur'),
    Sobel('TtSobel'),
    Canny('TtCanny'),
    WarpAffine('TtWarpAffine'),
    WarpPerspective('TtWarpPerspective'),
    Filter2D('TtFilter2D')

    final String id

    TransformationType(final String id) {
        this.id = id
    }
}
