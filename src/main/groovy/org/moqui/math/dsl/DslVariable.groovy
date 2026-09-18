/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
class DslVariable {
    final String name
    final double lowerBound
    final double upperBound
    final Double initialValue

    DslVariable(final String name, final double lowerBound, final double upperBound, final Double initialValue = null) {
        this.name = name
        this.lowerBound = lowerBound
        this.upperBound = upperBound
        this.initialValue = initialValue
    }

    DslExpression multiply(final Number scalar) {
        new DslExpression().addLinear(this, scalar.doubleValue())
    }

    DslExpression multiply(final DslVariable other) {
        new DslExpression().addQuadratic(this, other, 1.0d)
    }

    DslExpression plus(final DslVariable other) {
        new DslExpression().addLinear(this, 1.0d).addLinear(other, 1.0d)
    }

    DslExpression plus(final DslExpression expr) {
        expr.plus(this)
    }

    DslExpression plus(final Number constant) {
        new DslExpression().addLinear(this, 1.0d).addConstant(constant.doubleValue())
    }

    DslExpression minus(final DslVariable other) {
        new DslExpression().addLinear(this, 1.0d).addLinear(other, -1.0d)
    }

    DslExpression minus(final DslExpression expr) {
        new DslExpression().addLinear(this, 1.0d).minus(expr)
    }

    DslExpression minus(final Number constant) {
        new DslExpression().addLinear(this, 1.0d).addConstant(-constant.doubleValue())
    }

    DslExpression power(final int p) {
        if (p == 2) {
            return new DslExpression().addQuadratic(this, this, 1.0d)
        }
        throw new UnsupportedOperationException("Variable power ${p} not supported (only quadratic term ** 2)")
    }

    @Override
    String toString() {
        name
    }
}
