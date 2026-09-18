/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
class DslConstraint {
    String name
    final DslExpression expression
    String operator // 'LE', 'GE', 'EQ'
    double rhs

    DslConstraint(final DslExpression expression, final String operator, final double rhs) {
        this.expression = expression
        this.operator = operator
        this.rhs = rhs
    }

    DslConstraint withName(final String name) {
        this.name = name
        this
    }

    DslConstraint le(final Number rhs) {
        this.operator = 'LE'
        this.rhs = rhs.doubleValue() - (expression != null ? expression.constant : 0.0d)
        this
    }

    DslConstraint ge(final Number rhs) {
        this.operator = 'GE'
        this.rhs = rhs.doubleValue() - (expression != null ? expression.constant : 0.0d)
        this
    }

    DslConstraint eq(final Number rhs) {
        this.operator = 'EQ'
        this.rhs = rhs.doubleValue() - (expression != null ? expression.constant : 0.0d)
        this
    }
}
