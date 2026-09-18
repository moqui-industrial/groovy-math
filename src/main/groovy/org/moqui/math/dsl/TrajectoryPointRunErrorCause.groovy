/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TrajectoryPointRunErrorCause
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TrajectoryPointRunErrorCause implements DslEnumValue {
    SensorFault('TprecSensorFault', '', 'Sensor Fault', ''),
    Environmental('TprecEnvironmental', '', 'Environmental Interference', ''),
    MechanicalJam('TprecMechanicalJam', '', 'Mechanical Jam', ''),
    SoftwareGlitch('TprecSoftwareGlitch', '', 'Software Glitch', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TrajectoryPointRunErrorCause(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TrajectoryPointRunErrorCause fromId(final String id) {
        if (id == null) return null
        for (TrajectoryPointRunErrorCause val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TrajectoryPointRunErrorCause fromCode(final String code) {
        if (code == null) return null
        for (TrajectoryPointRunErrorCause val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
