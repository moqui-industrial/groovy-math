/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: BooleanYN
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum BooleanYN implements DslEnumValue {
    Y('BlY', '', 'Yes', ''),
    N('BlN', '', 'No', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    BooleanYN(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static BooleanYN fromId(final String id) {
        if (id == null) return null
        for (BooleanYN val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static BooleanYN fromCode(final String code) {
        if (code == null) return null
        for (BooleanYN val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
