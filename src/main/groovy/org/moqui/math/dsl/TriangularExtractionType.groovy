/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TriangularExtractionType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TriangularExtractionType implements DslEnumValue {
    Upper('TetUpper', '', 'Upper Triangular', ''),
    Lower('TetLower', '', 'Lower Triangular', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TriangularExtractionType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TriangularExtractionType fromId(final String id) {
        if (id == null) return null
        for (TriangularExtractionType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TriangularExtractionType fromCode(final String code) {
        if (code == null) return null
        for (TriangularExtractionType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static TriangularExtractionType fromName(final String name) {
        if (name == null) return null
        for (TriangularExtractionType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('UpperTriangular'.equalsIgnoreCase(name)) return Upper
        if ('LowerTriangular'.equalsIgnoreCase(name)) return Lower
        null
    }
}
