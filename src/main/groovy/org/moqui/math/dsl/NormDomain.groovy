/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

enum NormDomain implements DslEnumValue {
    Vector('NdVector'),
    Matrix('NdMatrix'),
    Tensor('NdTensor')

    final String id

    NormDomain(final String id) {
        this.id = id
    }
}
