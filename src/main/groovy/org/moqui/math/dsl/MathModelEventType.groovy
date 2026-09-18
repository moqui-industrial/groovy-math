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
}
