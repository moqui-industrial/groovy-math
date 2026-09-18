/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: NormOrder
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum NormOrder implements DslEnumValue {
    VecDefault('NoVecDefault', '', 'Vector-2 (default L²)', ''),
    Vec0('NoVec0', '', 'L⁰ - number of non-zeros', ''),
    Vec1('NoVec1', '', 'L¹ - Manhattan', ''),
    VecNeg1('NoVecNeg1', '', 'L⁻¹', ''),
    Vec2('NoVec2', '', 'L² - Euclidean', ''),
    VecNeg2('NoVecNeg2', '', 'L⁻²', ''),
    VecInf('NoVecInf', '', 'L^∞ - max(abs(xᵢ))', ''),
    VecNegInf('NoVecNegInf', '', 'L^-∞ - min(abs(xᵢ))', ''),
    MatFrobenius('NoMatFrobenius', 'Frobenius', 'Frobenius ‖A‖_F', ''),
    MatNuclear('NoMatNuclear', 'Nuclear', 'Nuclear ‖A‖_* (Σ singular values)', ''),
    Mat1('NoMat1', '', '‖A‖₁ - max column sum', ''),
    MatNeg1('NoMatNeg1', '', '‖A‖_{-1}', ''),
    Mat2('NoMat2', '', 'Spectral ‖A‖₂ (largest σ)', ''),
    MatNeg2('NoMatNeg2', '', '‖A‖_{-2} (smallest σ)', ''),
    MatInf('NoMatInf', '', '‖A‖_∞ - max row sum', ''),
    MatNegInf('NoMatNegInf', '', '‖A‖_{-∞} - min row sum', ''),
    GenericP('NoGenericP', '', 'Arbitrary p-norm; optionValue holds p', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    NormOrder(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static NormOrder fromId(final String id) {
        if (id == null) return null
        for (NormOrder val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static NormOrder fromCode(final String code) {
        if (code == null) return null
        for (NormOrder val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
