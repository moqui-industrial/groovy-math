/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package org.moqui.math.dsl

/**
 * Vector spaces a Matrix maps between.
 *
 * <p>Matrix.domainSpaceEnumId and codomainSpaceEnumId take an enum-group id, so these resolve to
 * the declared groups rather than to bare literals: 'R2' and 'R3' were never declared anywhere
 * in the schema and would have been rejected on the way to a database.
 */
enum MathSpace implements DslEnumValue {
    R2('Eng2DEuclideanSpace'),
    R3('Eng3DEuclideanSpace')

    final String id

    MathSpace(final String id) {
        this.id = id
    }
}
