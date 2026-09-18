/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelEventType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelEventType implements DslEnumValue {
    Start('MmetStart', '', 'Start-up', ''),
    Checkpoint('MmetCheckpoint', '', 'Checkpoint Saved', ''),
    Iteration('MmetIteration', '', 'Iteration Progress', ''),
    Converged('MmetConverged', '', 'Converged', ''),
    Diverged('MmetDiverged', '', 'Diverged / Error', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelEventType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MathModelEventType fromId(final String id) {
        if (id == null) return null
        for (MathModelEventType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelEventType fromCode(final String code) {
        if (code == null) return null
        for (MathModelEventType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MathModelEventType fromName(final String name) {
        if (name == null) return null
        for (MathModelEventType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('StartUp'.equalsIgnoreCase(name)) return Start
        if ('CheckpointSaved'.equalsIgnoreCase(name)) return Checkpoint
        if ('IterationProgress'.equalsIgnoreCase(name)) return Iteration
        if ('DivergedError'.equalsIgnoreCase(name)) return Diverged
        null
    }
}
