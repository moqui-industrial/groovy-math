/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
class DslExpression {
    final Map<String, Double> linearTerms = new LinkedHashMap<>()
    final Map<String, Double> quadraticTerms = new LinkedHashMap<>()
    double constant = 0.0d

    DslExpression addLinear(final DslVariable v, final double coeff) {
        linearTerms.put(v.name, (linearTerms.get(v.name) ?: 0.0d) + coeff)
        this
    }

    DslExpression addQuadratic(final DslVariable v1, final DslVariable v2, final double coeff) {
        String key = v1.name <= v2.name ? "${v1.name}|${v2.name}" : "${v2.name}|${v1.name}"
        quadraticTerms.put(key, (quadraticTerms.get(key) ?: 0.0d) + coeff)
        this
    }

    DslExpression addConstant(final double c) {
        this.constant += c
        this
    }

    DslExpression plus(final DslExpression other) {
        DslExpression res = copy()
        other.linearTerms.each { k, v -> res.linearTerms.put(k, (res.linearTerms.get(k) ?: 0.0d) + v) }
        other.quadraticTerms.each { k, v -> res.quadraticTerms.put(k, (res.quadraticTerms.get(k) ?: 0.0d) + v) }
        res.constant += other.constant
        res
    }

    DslExpression plus(final DslVariable v) {
        DslExpression res = copy()
        res.addLinear(v, 1.0d)
    }

    DslExpression plus(final Number n) {
        DslExpression res = copy()
        res.addConstant(n.doubleValue())
    }

    DslExpression minus(final DslExpression other) {
        DslExpression res = copy()
        other.linearTerms.each { k, v -> res.linearTerms.put(k, (res.linearTerms.get(k) ?: 0.0d) - v) }
        other.quadraticTerms.each { k, v -> res.quadraticTerms.put(k, (res.quadraticTerms.get(k) ?: 0.0d) - v) }
        res.constant -= other.constant
        res
    }

    DslExpression minus(final DslVariable v) {
        DslExpression res = copy()
        res.addLinear(v, -1.0d)
    }

    DslExpression minus(final Number n) {
        DslExpression res = copy()
        res.addConstant(-n.doubleValue())
    }

    DslExpression multiply(final Number scalar) {
        DslExpression res = new DslExpression()
        double s = scalar.doubleValue()
        linearTerms.each { k, v -> res.linearTerms.put(k, v * s) }
        quadraticTerms.each { k, v -> res.quadraticTerms.put(k, v * s) }
        res.constant = constant * s
        res
    }

    DslConstraint le(final Number rhs) {
        new DslConstraint(this, 'LE', rhs.doubleValue() - constant)
    }

    DslConstraint ge(final Number rhs) {
        new DslConstraint(this, 'GE', rhs.doubleValue() - constant)
    }

    DslConstraint eq(final Number rhs) {
        new DslConstraint(this, 'EQ', rhs.doubleValue() - constant)
    }

    DslExpression copy() {
        DslExpression expr = new DslExpression()
        expr.linearTerms.putAll(linearTerms)
        expr.quadraticTerms.putAll(quadraticTerms)
        expr.constant = constant
        expr
    }
}
