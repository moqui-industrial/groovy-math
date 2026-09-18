/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ParametricPathCompositionMethod
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ParametricPathCompositionMethod implements DslEnumValue {
    CircularBlend('PpcmCircularBlend', '', 'Circular Blend', ''),
    ParabolicBlend('PpcmParabolicBlend', '', 'Parabolic Blend', ''),
    PolynomialBlend('PpcmPolynomialBlend', '', 'Polynomial Blend', ''),
    DoubleS('PpcmDoubleS', '', 'Double S-curve Blend', ''),
    HarmonicBlend('PpcmHarmonicBlend', '', 'Harmonic Blend', ''),
    CycloidalBlend('PpcmCycloidalBlend', '', 'Cycloidal Blend', ''),
    CubicBlend('PpcmCubicBlend', '', 'Cubic Blend', ''),
    LinearBlend('PpcmLinearBlend', '', 'Linear Blend', ''),
    BezierBlend('PpcmBezierBlend', '', 'Bezier Blend', ''),
    SplineBlend('PpcmSplineBlend', '', 'Spline Blend', ''),
    CustomBlend('PpcmCustomBlend', '', 'Custom User-Defined Blend', ''),
    PlcAborting('PpcmPlcAborting', '', 'PLCopen Aborting - abort current segment, start next immediately', ''),
    PlcBuffered('PpcmPlcBuffered', '', 'PLCopen Buffered - queue next segment, start after current completes', ''),
    PlcBlendingLow('PpcmPlcBlendingLow', '', 'PLCopen Blending Low - blend at the lower of the two segment velocities', ''),
    PlcBlendingPrev('PpcmPlcBlendingPrev', '', 'PLCopen Blending Previous - blend at velocity of previous segment', ''),
    PlcBlendingNext('PpcmPlcBlendingNext', '', 'PLCopen Blending Next - blend at velocity of next segment', ''),
    PlcBlendingHigh('PpcmPlcBlendingHigh', '', 'PLCopen Blending High - blend at the higher of the two segment velocities', ''),
    PlcTMNone('PpcmPlcTMNone', '', 'PLCopen TMNone - exact stop at the point, no geometric blending', ''),
    PlcTMCornerDist('PpcmPlcTMCornerDist', '', 'PLCopen TMCornerDistance - blend within corner radius (ParametricPathPoint.tolerance)', ''),
    PlcTMConstVel('PpcmPlcTMConstVel', '', 'PLCopen TMConstantVelocity - maintain constant velocity through transition', ''),
    PlcTMStartVel('PpcmPlcTMStartVel', '', 'PLCopen TMStartVelocity - continue at current speed into next segment', ''),
    PlcTMMaxVel('PpcmPlcTMMaxVel', '', 'PLCopen TMMaxVelocity - maximize velocity through the transition', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ParametricPathCompositionMethod(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ParametricPathCompositionMethod fromId(final String id) {
        if (id == null) return null
        for (ParametricPathCompositionMethod val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ParametricPathCompositionMethod fromCode(final String code) {
        if (code == null) return null
        for (ParametricPathCompositionMethod val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
