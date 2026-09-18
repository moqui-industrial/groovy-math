/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TimeDimension
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TimeDimension implements DslEnumValue {
    Time('DimTime', 't', 'Time Dimension', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TimeDimension(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TimeDimension fromId(final String id) {
        if (id == null) return null
        for (TimeDimension val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TimeDimension fromCode(final String code) {
        if (code == null) return null
        for (TimeDimension val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
