/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

enum TensorDecompMethod implements DslEnumValue {
    Hosvd('TdmHosvd'),
    CP('TdmCP'),
    Tucker('TdmTucker'),
    TensorTrain('TdmTT'),
    HierarchicalTucker('TdmHT')

    final String id

    TensorDecompMethod(final String id) {
        this.id = id
    }
}
