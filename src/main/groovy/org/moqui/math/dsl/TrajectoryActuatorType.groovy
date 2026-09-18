/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TrajectoryActuatorType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TrajectoryActuatorType implements DslEnumValue {
    Continuous('PtactContinuous', '', 'Continuous - Revolves around an axis with a continuous range of motion (unlimited motion)', ''),
    Fixed('PtactFixed', '', 'Fixed - Axis does not move', ''),
    Prismatic('PtactPrismatic', '', 'Prismatic - Linear sliding motion along an axis within a fixed range', ''),
    Revolute('PtactRevolute', '', 'Revolute - Rotates around an axis within a fixed range of motion', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TrajectoryActuatorType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TrajectoryActuatorType fromId(final String id) {
        if (id == null) return null
        for (TrajectoryActuatorType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TrajectoryActuatorType fromCode(final String code) {
        if (code == null) return null
        for (TrajectoryActuatorType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static TrajectoryActuatorType fromName(final String name) {
        if (name == null) return null
        for (TrajectoryActuatorType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('ContinuousRevolvesAroundAnAxisWithAContinuousRangeOfMotion'.equalsIgnoreCase(name)) return Continuous
        if ('FixedAxisDoesNotMove'.equalsIgnoreCase(name)) return Fixed
        if ('PrismaticLinearSlidingMotionAlongAnAxisWithinAFixedRange'.equalsIgnoreCase(name)) return Prismatic
        if ('RevoluteRotatesAroundAnAxisWithinAFixedRangeOfMotion'.equalsIgnoreCase(name)) return Revolute
        null
    }
}
