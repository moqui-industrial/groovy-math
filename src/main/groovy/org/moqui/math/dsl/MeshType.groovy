/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshType implements DslEnumValue {
    Triangle('MtTriangle', '', 'Triangle Mesh', ''),
    Quad('MtQuad', '', 'Quad Mesh', ''),
    Polygon('MtPolygon', '', 'Polygon Mesh', ''),
    Tetrahedral('MtTetrahedral', '', 'Tetrahedral Mesh', ''),
    Hexahedral('MtHexahedral', '', 'Hexahedral Mesh', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MeshType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MeshType fromId(final String id) {
        if (id == null) return null
        for (MeshType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MeshType fromCode(final String code) {
        if (code == null) return null
        for (MeshType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MeshType fromName(final String name) {
        if (name == null) return null
        for (MeshType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('TriangleMesh'.equalsIgnoreCase(name)) return Triangle
        if ('QuadMesh'.equalsIgnoreCase(name)) return Quad
        if ('PolygonMesh'.equalsIgnoreCase(name)) return Polygon
        if ('TetrahedralMesh'.equalsIgnoreCase(name)) return Tetrahedral
        if ('HexahedralMesh'.equalsIgnoreCase(name)) return Hexahedral
        null
    }
}
