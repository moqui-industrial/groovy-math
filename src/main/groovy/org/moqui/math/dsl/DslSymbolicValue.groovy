/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

/**
 * A DSL constant that carries a symbolic literal, not an enumeration id.
 *
 * <p>Most DSL enums name a value of a declared moqui.basic.Enumeration, and pointing one at an
 * id the schema does not declare is a defect. A few do not: an objective sense is written into
 * Parameter.symbolicValue, which is free text. Both normalize to their string the same way, so
 * this interface exists to say which kind a constant is, and to keep the conformance check from
 * demanding an Enumeration that was never meant to exist.
 */
interface DslSymbolicValue extends DslEnumValue {
}
