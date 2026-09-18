/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorDecompMethod
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorDecompMethod implements DslEnumValue {
    Hosvd('TdmHosvd', '', 'Tucker / Higher-Order SVD (HOSVD)', ''),
    CP('TdmCP', '', 'Canonical Polyadic (CP / PARAFAC)', ''),
    Tucker('TdmTucker', '', 'Tucker (HOOI)', ''),
    TT('TdmTT', '', 'Tensor-Train (MPS)', ''),
    HT('TdmHT', '', 'Hierarchical-Tucker', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorDecompMethod(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorDecompMethod fromId(final String id) {
        if (id == null) return null
        for (TensorDecompMethod val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorDecompMethod fromCode(final String code) {
        if (code == null) return null
        for (TensorDecompMethod val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
