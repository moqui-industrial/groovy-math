/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: GeoAssocType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum GeoAssocType implements DslEnumValue {
    GROUP_MEMBER('GAT_GROUP_MEMBER', '', 'Geo Group Member', ''),
    REGIONS('GAT_REGIONS', '', 'Region of a Larger Geo', ''),
    COUNTY_SEAT('GAT_COUNTY_SEAT', '', 'Administrative City', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    GeoAssocType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static GeoAssocType fromId(final String id) {
        if (id == null) return null
        for (GeoAssocType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static GeoAssocType fromCode(final String code) {
        if (code == null) return null
        for (GeoAssocType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static GeoAssocType fromName(final String name) {
        if (name == null) return null
        for (GeoAssocType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('GeoGroupMember'.equalsIgnoreCase(name)) return GROUP_MEMBER
        if ('RegionOfALargerGeo'.equalsIgnoreCase(name)) return REGIONS
        if ('AdministrativeCity'.equalsIgnoreCase(name)) return COUNTY_SEAT
        null
    }
}
