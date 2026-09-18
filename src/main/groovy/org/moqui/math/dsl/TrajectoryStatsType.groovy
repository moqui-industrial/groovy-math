/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TrajectoryStatsType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TrajectoryStatsType implements DslEnumValue {
    DynamicAnalysis('PtstDynamicAnalysis', '', 'Dynamic Analysis ', ''),
    VibrationAnalysis('PtstVibrationAnalysis', '', 'Vibration Analysis', ''),
    ThermodynamicAnalysis('PtstThermodynamicAnalysis', '', 'Thermodynamic Analysis', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TrajectoryStatsType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TrajectoryStatsType fromId(final String id) {
        if (id == null) return null
        for (TrajectoryStatsType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TrajectoryStatsType fromCode(final String code) {
        if (code == null) return null
        for (TrajectoryStatsType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
