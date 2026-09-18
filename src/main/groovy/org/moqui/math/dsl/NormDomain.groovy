/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: NormDomain
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum NormDomain implements DslEnumValue {
    Vector('NdVector', '', 'Vector Norm', ''),
    Matrix('NdMatrix', '', 'Matrix Norm', ''),
    Tensor('NdTensor', '', 'Higher-Order Tensor Norm', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    NormDomain(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static NormDomain fromId(final String id) {
        if (id == null) return null
        for (NormDomain val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static NormDomain fromCode(final String code) {
        if (code == null) return null
        for (NormDomain val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
