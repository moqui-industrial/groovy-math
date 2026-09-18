/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MatrixConditionNorm
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MatrixConditionNorm implements DslEnumValue {
    One('McnOne', '', '||·||₁', ''),
    Two('McnTwo', '', '||·||₂ (spectral)', ''),
    Inf('McnInf', '', '||·||∞', ''),
    Fro('McnFro', '', '||·||_F (Frobenius)', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MatrixConditionNorm(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MatrixConditionNorm fromId(final String id) {
        if (id == null) return null
        for (MatrixConditionNorm val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MatrixConditionNorm fromCode(final String code) {
        if (code == null) return null
        for (MatrixConditionNorm val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
