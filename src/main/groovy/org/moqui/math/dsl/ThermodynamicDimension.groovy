/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ThermodynamicDimension
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ThermodynamicDimension implements DslEnumValue {
    Mass('DimMass', 'M', 'Mass', ''),
    Volume('DimVolume', 'V', 'Volume Dimension', ''),
    Pressure('DimPressure', 'P', 'Pressure Dimension', ''),
    Temperature('DimTemperature', 'T', 'Temperature Dimension', ''),
    Enthalpy('DimEnthalpy', 'H', 'Enthalpy Dimension', ''),
    Entropy('DimEntropy', 'S', 'Entropy Dimension', ''),
    RelativeHumidity('DimRelativeHumidity', 'RH', 'Relative Humidity Dimension', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ThermodynamicDimension(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ThermodynamicDimension fromId(final String id) {
        if (id == null) return null
        for (ThermodynamicDimension val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ThermodynamicDimension fromCode(final String code) {
        if (code == null) return null
        for (ThermodynamicDimension val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static ThermodynamicDimension fromName(final String name) {
        if (name == null) return null
        for (ThermodynamicDimension val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('VolumeDimension'.equalsIgnoreCase(name)) return Volume
        if ('PressureDimension'.equalsIgnoreCase(name)) return Pressure
        if ('TemperatureDimension'.equalsIgnoreCase(name)) return Temperature
        if ('EnthalpyDimension'.equalsIgnoreCase(name)) return Enthalpy
        if ('EntropyDimension'.equalsIgnoreCase(name)) return Entropy
        if ('RelativeHumidityDimension'.equalsIgnoreCase(name)) return RelativeHumidity
        null
    }
}
