/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshContentType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshContentType implements DslEnumValue {
    ntCustom('MCntCustom', 'Custom', 'Custom', ''),
    tnGltf('MCtnGltf', 'Gltf', 'GLTF Binary (GLB)', ''),
    ntObj('MCntObj', 'Obj', 'Wavefront OBJ Compressed (gzip)', ''),
    ntVtk('MCntVtk', 'Vtk', 'VTK / VTU Binary', ''),
    ntStl('MCntStl', 'Stl', 'STL Binary', ''),
    ntPly('MCntPly', 'Ply', 'PLY Binary Little Endian', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MeshContentType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MeshContentType fromId(final String id) {
        if (id == null) return null
        for (MeshContentType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MeshContentType fromCode(final String code) {
        if (code == null) return null
        for (MeshContentType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
