/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ParametricPathProfile
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ParametricPathProfile implements DslEnumValue {
    ParameterProgression('PppfParameterProgression', '', 'Parameter Progression Profile', ''),
    TrajectoryProfile('PppfTrajectoryProfile', '', 'Trajectory Profile', ''),
    TrapezoidalProfile('PppfTrapezoidalProfile', '', 'Trapezoidal Velocity Profile ', 'PppfTrajectoryProfile'),
    SCurveProfile('PppfSCurveProfile', '', 'S-curve (Jerk-limited) Profile', 'PppfTrajectoryProfile'),
    DoubleSCurveProfile('PppfDoubleSCurveProfile', '', 'Double S-curve Profile', 'PppfTrajectoryProfile'),
    PolyProfile('PppfPolyProfile', '', 'Polynomial Profile', 'PppfTrajectoryProfile'),
    Pppf3rdDegPolyProfile('Pppf3rdDegPolyProfile', '', 'Polynomial of 3rd Degree Profile', 'PppfPolyProfile'),
    Pppf5thDegPolyProfile('Pppf5thDegPolyProfile', '', 'Polynomial of 5th Degree Profile', 'PppfPolyProfile'),
    Pppf7thDegPolyProfile('Pppf7thDegPolyProfile', '', 'Polynomial of 7th Degree Profile', 'PppfPolyProfile'),
    HighDegPolyProfile('PppfHighDegPolyProfile', '', 'Polynomial of Higher Degree Profile', 'PppfPolyProfile'),
    TrigProfile('PppfTrigProfile', '', 'Trigonometric Profile', 'PppfTrajectoryProfile'),
    CycloidalProfile('PppfCycloidalProfile', '', 'Cycloidal Profile', 'PppfTrigProfile'),
    EllipticProfile('PppfEllipticProfile', '', 'Elliptic Profile', 'PppfTrigProfile');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ParametricPathProfile(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ParametricPathProfile fromId(final String id) {
        if (id == null) return null
        for (ParametricPathProfile val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ParametricPathProfile fromCode(final String code) {
        if (code == null) return null
        for (ParametricPathProfile val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
