/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ApproxFuncPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ApproxFuncPurpose implements DslEnumValue {
    NumericalFunction('AfpNumericalFunction', '', 'Numerical Function', ''),
    GeometricCurve('AfpGeometricCurve', '', 'Geometric Curve', ''),
    Trajectory('AfpTrajectory', '', 'Trajectory', ''),
    RobotMotion('AfpRobotMotion', '', 'Robot Motion', 'AfpTrajectory'),
    MachineMotion('AfpMachineMotion', '', 'Automatic Machine Motion', 'AfpTrajectory'),
    Thermodynamics('AfpThermodynamics', '', 'Thermodynamics Process', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ApproxFuncPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ApproxFuncPurpose fromId(final String id) {
        if (id == null) return null
        for (ApproxFuncPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ApproxFuncPurpose fromCode(final String code) {
        if (code == null) return null
        for (ApproxFuncPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static ApproxFuncPurpose fromName(final String name) {
        if (name == null) return null
        for (ApproxFuncPurpose val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('AutomaticMachineMotion'.equalsIgnoreCase(name)) return MachineMotion
        if ('ThermodynamicsProcess'.equalsIgnoreCase(name)) return Thermodynamics
        null
    }
}
