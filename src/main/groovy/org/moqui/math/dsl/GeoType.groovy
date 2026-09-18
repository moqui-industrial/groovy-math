/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: GeoType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum GeoType implements DslEnumValue {
    GROUP('GEOT_GROUP', '', 'Group', ''),
    REGION('GEOT_REGION', '', 'Region', ''),
    SALES_REGION('GEOT_SALES_REGION', '', 'Sales Region', ''),
    SERVICE_REGION('GEOT_SERVICE_REGION', '', 'Service Region', ''),
    CITY('GEOT_CITY', '', 'City', ''),
    STATE('GEOT_STATE', '', 'State', ''),
    POSTAL_CODE('GEOT_POSTAL_CODE', '', 'Postal Code', ''),
    COUNTRY('GEOT_COUNTRY', '', 'Country', ''),
    COUNTY('GEOT_COUNTY', '', 'County', ''),
    COUNTY_CITY('GEOT_COUNTY_CITY', '', 'County-City', ''),
    MUNICIPALITY('GEOT_MUNICIPALITY', '', 'Municipality', ''),
    PROVINCE('GEOT_PROVINCE', '', 'Province', ''),
    TERRITORY('GEOT_TERRITORY', '', 'Territory', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    GeoType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static GeoType fromId(final String id) {
        if (id == null) return null
        for (GeoType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static GeoType fromCode(final String code) {
        if (code == null) return null
        for (GeoType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static GeoType fromName(final String name) {
        if (name == null) return null
        for (GeoType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('Group'.equalsIgnoreCase(name)) return GROUP
        if ('Region'.equalsIgnoreCase(name)) return REGION
        if ('SalesRegion'.equalsIgnoreCase(name)) return SALES_REGION
        if ('ServiceRegion'.equalsIgnoreCase(name)) return SERVICE_REGION
        if ('City'.equalsIgnoreCase(name)) return CITY
        if ('State'.equalsIgnoreCase(name)) return STATE
        if ('PostalCode'.equalsIgnoreCase(name)) return POSTAL_CODE
        if ('Country'.equalsIgnoreCase(name)) return COUNTRY
        if ('County'.equalsIgnoreCase(name)) return COUNTY
        if ('CountyCity'.equalsIgnoreCase(name)) return COUNTY_CITY
        if ('Municipality'.equalsIgnoreCase(name)) return MUNICIPALITY
        if ('Province'.equalsIgnoreCase(name)) return PROVINCE
        if ('Territory'.equalsIgnoreCase(name)) return TERRITORY
        null
    }
}
