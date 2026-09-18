/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshKCellType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshKCellType implements DslEnumValue {
    Vertex('MkctVertex', '', 'Vertex', ''),
    Edge('MkctEdge', '', 'Edge', ''),
    Face('MkctFace', '', 'Face', ''),
    Triangle('MkctTriangle', '', 'Triangle', 'MkctFace'),
    Quadrilateral('MkctQuadrilateral', '', 'Quadrilateral', 'MkctFace'),
    Polygon('MkctPolygon', '', 'Polygon', 'MkctFace'),
    BezierPatch('MkctBezierPatch', '', 'Bezier surface patch', 'MkctFace'),
    NurbsPatch('MkctNurbsPatch', '', 'NURBS surface patch', 'MkctFace'),
    Volume('MkctVolume', '', 'Volume', ''),
    Tetrahedron('MkctTetrahedron', '', 'Tetrahedron', 'MkctVolume'),
    Pyramid('MkctPyramid', '', 'Pyramid', 'MkctVolume'),
    TriangularPrism('MkctTriangularPrism', '', 'Triangular Prism', 'MkctVolume'),
    Hexahedron('MkctHexahedron', '', 'Hexahedron', 'MkctVolume'),
    Polyhedron('MkctPolyhedron', '', 'Polyhedron', 'MkctVolume'),
    Hypervolume('MkctHypervolume', '', 'Hypervolume', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MeshKCellType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MeshKCellType fromId(final String id) {
        if (id == null) return null
        for (MeshKCellType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MeshKCellType fromCode(final String code) {
        if (code == null) return null
        for (MeshKCellType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
