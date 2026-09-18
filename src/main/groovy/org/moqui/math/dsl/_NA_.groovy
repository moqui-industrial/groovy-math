/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: _NA_
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum _NA_ implements DslEnumValue {
    A_('_NA_', '', 'Not Applicable', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    _NA_(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static DslEnumValue fromId(final String id) {
        if (id == null) return null
        for (_NA_ val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static DslEnumValue fromCode(final String code) {
        if (code == null) return null
        for (_NA_ val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
