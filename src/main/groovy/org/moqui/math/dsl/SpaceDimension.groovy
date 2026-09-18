/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: SpaceDimension
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum SpaceDimension implements DslEnumValue {
    X('DimX', 'x', 'X Dimension', ''),
    Y('DimY', 'y', 'Y Dimension', ''),
    Z('DimZ', 'z', 'Z Dimension', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    SpaceDimension(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static SpaceDimension fromId(final String id) {
        if (id == null) return null
        for (SpaceDimension val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static SpaceDimension fromCode(final String code) {
        if (code == null) return null
        for (SpaceDimension val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
