/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorMemoryFormat
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorMemoryFormat implements DslEnumValue {
    ontig('TmfContig', '', 'Contiguous', ''),
    hLast2('TmfChLast2', '', 'Channels-Last 2-D (NHWC)', ''),
    hLast3('TmfChLast3', '', 'Channels-Last 3-D (NDHWC)', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorMemoryFormat(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorMemoryFormat fromId(final String id) {
        if (id == null) return null
        for (TensorMemoryFormat val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorMemoryFormat fromCode(final String code) {
        if (code == null) return null
        for (TensorMemoryFormat val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
