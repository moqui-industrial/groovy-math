/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ApproxFuncSampleType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ApproxFuncSampleType implements DslEnumValue {
    StartPoint('AfstStartPoint', '', 'Start Point', ''),
    EndPoint('AfstEndPoint', '', 'End Point', ''),
    ControlPoint('AfstControlPoint', '', 'Control Point', ''),
    BreakPoint('AfstBreakPoint', '', 'Break Point', ''),
    SampledPoint('AfstSampledPoint', '', 'Sampled Point', ''),
    InspectionPoint('AfstInspectionPoint', '', 'Inspection Point', ''),
    EmergencyStopPoint('AfstEmergencyStopPoint', '', 'Emergency Stop Point', ''),
    Waypoint('AfstWaypoint', '', 'Waypoint ', ''),
    CalculatedPoint('AfstCalculatedPoint', '', 'Calculated or Derived Point', ''),
    TemporaryPoint('AfstTemporaryPoint', '', 'Temporary Point', ''),
    SafetyStopPoint('AfstSafetyStopPoint', '', 'Safety Stop Point', 'AfstTemporaryPoint'),
    DeviationPoint('AfstDeviationPoint', '', 'Deviation Point', 'AfstTemporaryPoint'),
    Corner('AfstCorner', '', 'Corner Point', ''),
    ArcStart('AfstArcStart', '', 'Arc Start', ''),
    ArcEnd('AfstArcEnd', '', 'Arc End', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ApproxFuncSampleType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ApproxFuncSampleType fromId(final String id) {
        if (id == null) return null
        for (ApproxFuncSampleType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ApproxFuncSampleType fromCode(final String code) {
        if (code == null) return null
        for (ApproxFuncSampleType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static ApproxFuncSampleType fromName(final String name) {
        if (name == null) return null
        for (ApproxFuncSampleType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('CalculatedOrDerivedPoint'.equalsIgnoreCase(name)) return CalculatedPoint
        if ('CornerPoint'.equalsIgnoreCase(name)) return Corner
        null
    }
}
