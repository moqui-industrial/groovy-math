/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshGroupType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshGroupType implements DslEnumValue {
    SelectionSet('MgtSelectionSet', '', 'Selection Set', ''),
    ExportZone('MgtExportZone', '', 'Export Zone', ''),
    PhysicsDomain('MgtPhysicsDomain', '', 'Physics Domain', ''),
    TextureRegion('MgtTextureRegion', '', 'Texture Region', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MeshGroupType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MeshGroupType fromId(final String id) {
        if (id == null) return null
        for (MeshGroupType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MeshGroupType fromCode(final String code) {
        if (code == null) return null
        for (MeshGroupType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MeshGroupType fromName(final String name) {
        if (name == null) return null
        for (MeshGroupType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        null
    }
}
