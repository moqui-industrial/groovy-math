/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TrajectoryPointRunExecutionStatus
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TrajectoryPointRunExecutionStatus implements DslEnumValue {
    Success('TpresSuccess', '', 'Success', ''),
    Deviation('TpresDeviation', '', 'Deviation', ''),
    Failure('TpresFailure', '', 'Failure', ''),
    Skipped('TpresSkipped', '', 'Skipped', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TrajectoryPointRunExecutionStatus(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TrajectoryPointRunExecutionStatus fromId(final String id) {
        if (id == null) return null
        for (TrajectoryPointRunExecutionStatus val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TrajectoryPointRunExecutionStatus fromCode(final String code) {
        if (code == null) return null
        for (TrajectoryPointRunExecutionStatus val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
