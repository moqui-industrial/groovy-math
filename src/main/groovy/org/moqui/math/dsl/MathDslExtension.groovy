/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.moqui.math.entity.ModelProvider

@CompileStatic
class MathDslExtension {
    static ModelProvider multiply(final Number self, final ModelProvider other) {
        if (other == null) throw new IllegalArgumentException("Cannot multiply Number by null ModelProvider")
        other.multiply(self)
    }

    static DslExpression multiply(final Number self, final DslVariable other) {
        if (other == null) throw new IllegalArgumentException("Cannot multiply Number by null DslVariable")
        other.multiply(self)
    }

    static DslExpression multiply(final Number self, final DslExpression other) {
        if (other == null) throw new IllegalArgumentException("Cannot multiply Number by null DslExpression")
        other.multiply(self.doubleValue())
    }
}
