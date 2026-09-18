/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: UomType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum UomType implements DslEnumValue {
    CURRENCY_MEASURE('UT_CURRENCY_MEASURE', '', 'Currency', ''),
    DATA_MEASURE('UT_DATA_MEASURE', '', 'Data Size', ''),
    DATASPD_MEASURE('UT_DATASPD_MEASURE', '', 'Data Speed', ''),
    TIME_FREQ_MEASURE('UT_TIME_FREQ_MEASURE', '', 'Time/Frequency', ''),
    LENGTH_MEASURE('UT_LENGTH_MEASURE', '', 'Length', ''),
    VELOCITY_MEASURE('UT_VELOCITY_MEASURE', '', 'Velocity', ''),
    AREA_MEASURE('UT_AREA_MEASURE', '', 'Area', ''),
    VOLUME_LIQ_MEAS('UT_VOLUME_LIQ_MEAS', '', 'Liquid Volume', ''),
    VOLUME_DRY_MEAS('UT_VOLUME_DRY_MEAS', '', 'Dry Volume', ''),
    DENSITY_MEAS('UT_DENSITY_MEAS', '', 'Density', ''),
    WEIGHT_MEASURE('UT_WEIGHT_MEASURE', '', 'Weight', ''),
    ENERGY_MEASURE('UT_ENERGY_MEASURE', '', 'Energy', ''),
    POWER_MEASURE('UT_POWER_MEASURE', '', 'Power', ''),
    PRESSURE_MEASURE('UT_PRESSURE_MEASURE', '', 'Pressure', ''),
    TEMP_MEASURE('UT_TEMP_MEASURE', '', 'Temperature', ''),
    OTHER_MEASURE('UT_OTHER_MEASURE', '', 'Other', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    UomType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static UomType fromId(final String id) {
        if (id == null) return null
        for (UomType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static UomType fromCode(final String code) {
        if (code == null) return null
        for (UomType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
