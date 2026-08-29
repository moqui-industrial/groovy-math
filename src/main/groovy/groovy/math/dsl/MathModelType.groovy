/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.dsl

enum MathModelType implements DslEnumValue {
    LinearAlgebra('MmtLinearAlgebra'),
    DlFeedforward('MmtDlFeedforward'),
    LinearProgram('MmtLp'),
    QuadraticProgram('MmtQp'),
    ComputerVision('MmtComputerVision')

    final String id

    MathModelType(final String id) {
        this.id = id
    }
}
