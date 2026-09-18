/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: CoordinateSystemType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum CoordinateSystemType implements DslEnumValue {
    Cartesian('CstCartesian', 'Cartesian', 'Cartesian (Rectangular) Coordinate System', ''),
    Polar('CstPolar', 'Polar', 'Polar Coordinate System', ''),
    Cylindrical('CstCylindrical', 'Cylindrical', 'Cylindrical Coordinate System', ''),
    Spherical('CstSpherical', 'Spherical', 'Spherical Coordinate System', ''),
    Homogeneous('CstHomogeneous', 'Homogeneous', 'Homogeneous Coordinate System', ''),
    Curvilinear('CstCurvilinear', 'Curvilinear', 'Curvilinear Coordinate System', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    CoordinateSystemType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static CoordinateSystemType fromId(final String id) {
        if (id == null) return null
        for (CoordinateSystemType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static CoordinateSystemType fromCode(final String code) {
        if (code == null) return null
        for (CoordinateSystemType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
