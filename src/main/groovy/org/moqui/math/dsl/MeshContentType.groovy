/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshContentType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshContentType implements DslEnumValue {
    CntCustom('MCntCustom', 'Custom', 'Custom', ''),
    CtnGltf('MCtnGltf', 'Gltf', 'GLTF Binary (GLB)', ''),
    CntObj('MCntObj', 'Obj', 'Wavefront OBJ Compressed (gzip)', ''),
    CntVtk('MCntVtk', 'Vtk', 'VTK / VTU Binary', ''),
    CntStl('MCntStl', 'Stl', 'STL Binary', ''),
    CntPly('MCntPly', 'Ply', 'PLY Binary Little Endian', '');

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
