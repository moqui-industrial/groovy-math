/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.dsl

enum DataType implements DslEnumValue {
    Float32('DtFloat32'),
    Float64('DtFloat64'),
    Int32('DtInt32'),
    Int64('DtInt64'),
    Bool('DtBool'),
    BFloat16('DtBFloat16')

    final String id

    DataType(final String id) {
        this.id = id
    }
}
