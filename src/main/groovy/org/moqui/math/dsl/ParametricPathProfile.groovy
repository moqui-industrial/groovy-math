/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ParametricPathProfile
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ParametricPathProfile implements DslEnumValue {
    PppfParameterProgression('PppfParameterProgression', '', 'Parameter Progression Profile', ''),
    PppfTrajectoryProfile('PppfTrajectoryProfile', '', 'Trajectory Profile', ''),
    PppfTrapezoidalProfile('PppfTrapezoidalProfile', '', 'Trapezoidal Velocity Profile ', 'PppfTrajectoryProfile'),
    PppfSCurveProfile('PppfSCurveProfile', '', 'S-curve (Jerk-limited) Profile', 'PppfTrajectoryProfile'),
    PppfDoubleSCurveProfile('PppfDoubleSCurveProfile', '', 'Double S-curve Profile', 'PppfTrajectoryProfile'),
    PppfPolyProfile('PppfPolyProfile', '', 'Polynomial Profile', 'PppfTrajectoryProfile'),
    Pppf3rdDegPolyProfile('Pppf3rdDegPolyProfile', '', 'Polynomial of 3rd Degree Profile', 'PppfPolyProfile'),
    Pppf5thDegPolyProfile('Pppf5thDegPolyProfile', '', 'Polynomial of 5th Degree Profile', 'PppfPolyProfile'),
    Pppf7thDegPolyProfile('Pppf7thDegPolyProfile', '', 'Polynomial of 7th Degree Profile', 'PppfPolyProfile'),
    PppfHighDegPolyProfile('PppfHighDegPolyProfile', '', 'Polynomial of Higher Degree Profile', 'PppfPolyProfile'),
    PppfTrigProfile('PppfTrigProfile', '', 'Trigonometric Profile', 'PppfTrajectoryProfile'),
    PppfCycloidalProfile('PppfCycloidalProfile', '', 'Cycloidal Profile', 'PppfTrigProfile'),
    PppfEllipticProfile('PppfEllipticProfile', '', 'Elliptic Profile', 'PppfTrigProfile');

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

    static ParametricPathProfile fromName(final String name) {
        if (name == null) return null
        for (ParametricPathProfile val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('ParameterProgressionProfile'.equalsIgnoreCase(name)) return PppfParameterProgression
        if ('TrajectoryProfile'.equalsIgnoreCase(name)) return PppfTrajectoryProfile
        if ('TrapezoidalVelocityProfile'.equalsIgnoreCase(name)) return PppfTrapezoidalProfile
        if ('SCurveProfile'.equalsIgnoreCase(name)) return PppfSCurveProfile
        if ('DoubleSCurveProfile'.equalsIgnoreCase(name)) return PppfDoubleSCurveProfile
        if ('PolynomialProfile'.equalsIgnoreCase(name)) return PppfPolyProfile
        if ('PolynomialOf3rdDegreeProfile'.equalsIgnoreCase(name)) return Pppf3rdDegPolyProfile
        if ('PolynomialOf5thDegreeProfile'.equalsIgnoreCase(name)) return Pppf5thDegPolyProfile
        if ('PolynomialOf7thDegreeProfile'.equalsIgnoreCase(name)) return Pppf7thDegPolyProfile
        if ('PolynomialOfHigherDegreeProfile'.equalsIgnoreCase(name)) return PppfHighDegPolyProfile
        if ('TrigonometricProfile'.equalsIgnoreCase(name)) return PppfTrigProfile
        if ('CycloidalProfile'.equalsIgnoreCase(name)) return PppfCycloidalProfile
        if ('EllipticProfile'.equalsIgnoreCase(name)) return PppfEllipticProfile
        null
    }
}
