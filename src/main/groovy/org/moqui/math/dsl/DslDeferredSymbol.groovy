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

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

/**
 * Represents a bare DSL symbol whose resolution is deferred until it is assigned to a
 * specific entity field or method argument, allowing contextual resolution against the
 * expected enumeration domain without global name collisions.
 */
@CompileStatic
@EqualsAndHashCode(includes = ['name'])
final class DslDeferredSymbol implements CharSequence, Serializable {
    private static final long serialVersionUID = 1L
    final String name
    final String sourceFile
    final int line
    final int column

    DslDeferredSymbol(final String name, final String sourceFile = null, final int line = -1, final int column = -1) {
        this.name = Objects.requireNonNull(name, 'Symbol name must not be null')
        this.sourceFile = sourceFile
        this.line = line
        this.column = column
    }

    @Override
    int length() {
        name.length()
    }

    @Override
    char charAt(int index) {
        name.charAt(index)
    }

    @Override
    CharSequence subSequence(int start, int end) {
        name.subSequence(start, end)
    }

    @Override
    String toString() {
        name
    }
}
