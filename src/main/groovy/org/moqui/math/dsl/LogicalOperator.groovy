/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: LogicalOperator
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum LogicalOperator implements DslEnumValue {
    And('OpAnd', 'AND', 'Conjunction - AND', ''),
    Nand('OpNand', 'NAND', 'Alternative Denial - NAND', ''),
    Or('OpOr', 'OR', 'Disjunction - OR', ''),
    Nor('OpNor', 'NOR', 'Joint Denial - NOR', ''),
    Xor('OpXor', 'XOR', 'Exclusive Or - XOR', ''),
    Not('OpNot', 'NOT', 'Negation - NOT', ''),
    Xnor('OpXnor', 'XNOR', 'Biconditional - XNOR', ''),
    Eq('OpEq', 'EQ', 'Equality - EQ', ''),
    Ne('OpNe', 'NE', 'Inequality - NE', ''),
    Lt('OpLt', 'LT', 'Less Than - LT', ''),
    Le('OpLe', 'LE', 'Less Than or Equal - LE', ''),
    Gt('OpGt', 'GT', 'Greater Than - GT', ''),
    Ge('OpGe', 'GE', 'Greater Than or Equal - GE', ''),
    Implies('OpImplies', 'IMPLIES', 'Implication - IMPLIES', ''),
    In('OpIn', 'IN', 'Membership - IN', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    LogicalOperator(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
        this.id = id
        this.enumCode = enumCode
        this.description = description
        this.parentEnumId = parentEnumId
    }

    @Override
    String getId() { id }

    @Override
    String getEnumCode() { enumCode }

    @Override
    String getDescription() { description }

    @Override
    String getParentEnumId() { parentEnumId }

    static LogicalOperator fromId(final String id) {
        if (id == null) return null
        for (LogicalOperator val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static LogicalOperator fromCode(final String code) {
        if (code == null) return null
        for (LogicalOperator val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static LogicalOperator fromName(final String name) {
        if (name == null) return null
        for (LogicalOperator val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('ConjunctionAnd'.equalsIgnoreCase(name)) return And
        if ('AlternativeDenialNand'.equalsIgnoreCase(name)) return Nand
        if ('DisjunctionOr'.equalsIgnoreCase(name)) return Or
        if ('JointDenialNor'.equalsIgnoreCase(name)) return Nor
        if ('ExclusiveOrXor'.equalsIgnoreCase(name)) return Xor
        if ('NegationNot'.equalsIgnoreCase(name)) return Not
        if ('BiconditionalXnor'.equalsIgnoreCase(name)) return Xnor
        if ('EqualityEq'.equalsIgnoreCase(name)) return Eq
        if ('InequalityNe'.equalsIgnoreCase(name)) return Ne
        if ('LessThanLt'.equalsIgnoreCase(name)) return Lt
        if ('LessThanOrEqualLe'.equalsIgnoreCase(name)) return Le
        if ('GreaterThanGt'.equalsIgnoreCase(name)) return Gt
        if ('GreaterThanOrEqualGe'.equalsIgnoreCase(name)) return Ge
        if ('ImplicationImplies'.equalsIgnoreCase(name)) return Implies
        if ('MembershipIn'.equalsIgnoreCase(name)) return In
        null
    }
}
