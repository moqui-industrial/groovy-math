/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorQuantScheme
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorQuantScheme implements DslEnumValue {
    TensorAff('TqsPerTensorAff', '', 'Per-Tensor Affine (INT8)', ''),
    ChannelAff('TqsPerChannelAff', '', 'Per-Channel Affine (INT8)', ''),
    TensorSym('TqsPerTensorSym', '', 'Per-Tensor Symmetric', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorQuantScheme(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorQuantScheme fromId(final String id) {
        if (id == null) return null
        for (TensorQuantScheme val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorQuantScheme fromCode(final String code) {
        if (code == null) return null
        for (TensorQuantScheme val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
