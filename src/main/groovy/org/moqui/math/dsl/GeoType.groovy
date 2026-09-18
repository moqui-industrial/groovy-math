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
}
