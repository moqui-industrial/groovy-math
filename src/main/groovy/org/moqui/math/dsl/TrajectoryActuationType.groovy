/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TrajectoryActuationType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TrajectoryActuationType implements DslEnumValue {
    Direct('PtaDirect', '', 'Direct - Movement is initiated by the component', ''),
    Indirect('PtaIndirect', '', 'Indirect - Motion derived from a parent component or external force', ''),
    None('PtaNone', '', 'None - No actuation of this axis', ''),
    Virtual('PtaVirtual', '', 'Virtual - Motion is computed and used for expressing an imaginary or derived movement', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TrajectoryActuationType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TrajectoryActuationType fromId(final String id) {
        if (id == null) return null
        for (TrajectoryActuationType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TrajectoryActuationType fromCode(final String code) {
        if (code == null) return null
        for (TrajectoryActuationType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static TrajectoryActuationType fromName(final String name) {
        if (name == null) return null
        for (TrajectoryActuationType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('DirectMovementIsInitiatedByTheComponent'.equalsIgnoreCase(name)) return Direct
        if ('IndirectMotionDerivedFromAParentComponentOrExternalForce'.equalsIgnoreCase(name)) return Indirect
        if ('NoneNoActuationOfThisAxis'.equalsIgnoreCase(name)) return None
        if ('VirtualMotionIsComputedAndUsedForExpressingAnImaginaryOrDerivedMovement'.equalsIgnoreCase(name)) return Virtual
        null
    }
}
