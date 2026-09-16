/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

enum NormOrder implements DslEnumValue {
    VecDefault('NoVecDefault'),
    Vec0('NoVec0'),
    Vec1('NoVec1'),
    Vec2('NoVec2'),
    VecInf('NoVecInf'),
    MatFrobenius('NoMatFrobenius'),
    MatNuclear('NoMatNuclear'),
    Mat1('NoMat1'),
    Mat2('NoMat2'),
    MatInf('NoMatInf')

    final String id

    NormOrder(final String id) {
        this.id = id
    }
}
