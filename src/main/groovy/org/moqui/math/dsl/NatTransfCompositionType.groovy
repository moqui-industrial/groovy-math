/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: NatTransfCompositionType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum NatTransfCompositionType implements DslEnumValue {
    Horizontal('NctHorizontal', '', 'Horizontal composition (*)', ''),
    Vertical('NctVertical', '', 'Vertical composition (o)', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    NatTransfCompositionType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static NatTransfCompositionType fromId(final String id) {
        if (id == null) return null
        for (NatTransfCompositionType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static NatTransfCompositionType fromCode(final String code) {
        if (code == null) return null
        for (NatTransfCompositionType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static NatTransfCompositionType fromName(final String name) {
        if (name == null) return null
        for (NatTransfCompositionType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('HorizontalComposition'.equalsIgnoreCase(name)) return Horizontal
        if ('VerticalComposition'.equalsIgnoreCase(name)) return Vertical
        null
    }
}
