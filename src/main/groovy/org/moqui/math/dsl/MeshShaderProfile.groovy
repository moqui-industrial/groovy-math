/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshShaderProfile
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshShaderProfile implements DslEnumValue {
    Flat('MspFlat', '', 'Flat shading', ''),
    Gouraud('MspGouraud', '', 'Gouraud shading', ''),
    Phong('MspPhong', '', 'Phong shading', ''),
    BlinnPhong('MspBlinnPhong', '', 'Blinn-Phong shading', ''),
    Toon('MspToon', '', 'Toon (non-photorealistic) shading', ''),
    Wireframe('MspWireframe', '', 'Wireframe rendering', ''),
    Unlit('MspUnlit', '', 'Unlit color (no lighting calculation)', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MeshShaderProfile(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MeshShaderProfile fromId(final String id) {
        if (id == null) return null
        for (MeshShaderProfile val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MeshShaderProfile fromCode(final String code) {
        if (code == null) return null
        for (MeshShaderProfile val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
