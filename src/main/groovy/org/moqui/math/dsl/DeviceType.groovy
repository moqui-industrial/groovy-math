/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

enum DeviceType implements DslEnumValue {
    CPU('DevCpu'),
    CUDA('DevCuda'),
    MPS('DevMps'),
    TPU('DevTpu')

    final String id

    DeviceType(final String id) {
        this.id = id
    }
}
