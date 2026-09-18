/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ApproxFuncInterpolation
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ApproxFuncInterpolation implements DslEnumValue {
    AfiP2PPath('AfiP2PPath', '', 'Point-to-Point Path', ''),
    AfiPolyPath('AfiPolyPath', '', 'Polynomial Path', 'AfiP2PPath'),
    AfiLinearPath('AfiLinearPath', '', 'Linear Path (constant velocity)', 'AfiPolyPath'),
    AfiParabolicPath('AfiParabolicPath', '', 'Parabolic Path (constant acceleration)', 'AfiPolyPath'),
    AfiAsymmetricPath('AfiAsymmetricPath', '', 'Path with Asymmetric Constant Acceleration', 'AfiPolyPath'),
    AfiCubicPath('AfiCubicPath', '', 'Cubic Path', 'AfiPolyPath'),
    Afi5thDegPolyPath('Afi5thDegPolyPath', '', 'Polynomial of 5th Degree Path', 'AfiPolyPath'),
    Afi7thDegPolyPath('Afi7thDegPolyPath', '', 'Polynomial of Degree Seven Path', 'AfiPolyPath'),
    AfiHighDegPolyPath('AfiHighDegPolyPath', '', 'Polynomial of Higher Degree Path', 'AfiPolyPath'),
    AfiTrigPath('AfiTrigPath', '', 'Trigonometric Path', 'AfiP2PPath'),
    AfiHarmonicPath('AfiHarmonicPath', '', 'Harmonic Path', 'AfiTrigPath'),
    AfiCycloidalPath('AfiCycloidalPath', '', 'Cycloidal Path', 'AfiTrigPath'),
    AfiEllipticPath('AfiEllipticPath', '', 'Elliptic Path', 'AfiTrigPath'),
    AfiExpPath('AfiExpPath', '', 'Exponential Path', 'AfiP2PPath'),
    AfiFourierSeriesExpansionPath('AfiFourierSeriesExpansionPath', '', 'Path Based on the Fourier Series Expansion', 'AfiP2PPath'),
    AfiGutmanPath('AfiGutmanPath', '', 'Gutman Path', 'AfiFourierSeriesExpansionPath'),
    AfiFreudensteinPath('AfiFreudensteinPath', '', 'Freudenstein Path', 'AfiFourierSeriesExpansionPath'),
    AfiMultipointPath('AfiMultipointPath', '', 'Multipoint Path', ''),
    AfiApproxPath('AfiApproxPath', '', 'Multipoint Approximated Path', 'AfiMultipointPath'),
    AfiApproxPolyPath('AfiApproxPolyPath', '', 'Approximated Polynomial Path', 'AfiApproxPath'),
    AfiApproxOrthogonalPolyPath('AfiApproxOrthogonalPolyPath', '', 'Approximated Orthogonal Polynomials Path', 'AfiApproxPath'),
    AfiApproxTrigPolynomialPath('AfiApproxTrigPolynomialPath', '', 'Approximated Trigonometric Polynomial Path', 'AfiApproxPath'),
    AfiApproxCubicSplinesPath('AfiApproxCubicSplinesPath', '', 'Approximated Cubic Splines Path', 'AfiApproxPath'),
    AfiInterpPath('AfiInterpPath', '', 'Multipoint Interpolated Path', 'AfiMultipointPath'),
    AfiInterpPolyPath('AfiInterpPolyPath', '', 'Interpolated Polynomial Path', 'AfiInterpPath'),
    AfiInterpOrthogonalPolyPath('AfiInterpOrthogonalPolyPath', '', 'Interpolated Orthogonal Polynomials Path', 'AfiInterpPath'),
    AfiInterpTrigPolyPath('AfiInterpTrigPolyPath', '', 'Interpolated Trigonometric Polynomial Path', 'AfiInterpPath'),
    AfiInterpCubicSplinesPath('AfiInterpCubicSplinesPath', '', 'Interpolated Cubic Splines Path', 'AfiInterpPath'),
    AfiPiecewise('AfiPiecewise', '', 'Linear (Piecewise) Curve Path ', 'AfiInterpPath'),
    AfiBezierPath('AfiBezierPath', '', 'Bézier (Bernstein Basis) Curve Path ', 'AfiInterpPath'),
    AfiBSplinePath('AfiBSplinePath', '', 'B-Spline Path', 'AfiInterpPath'),
    AfiNurbsPath('AfiNurbsPath', '', 'NURBS (Rational B-Spline) Path', 'AfiInterpPath'),
    AfiHermiteSpline('AfiHermiteSpline', '', 'Hermite Spline Curve Path ', 'AfiInterpPath'),
    AfiLagrangePolynomial('AfiLagrangePolynomial', '', 'Lagrange Polynomial Curve Path ', 'AfiInterpPath'),
    AfiCatmullRomSpline('AfiCatmullRomSpline', '', 'Catmull-Rom Spline Curve Path ', 'AfiInterpPath'),
    AfiAdaptivePath('AfiAdaptivePath', '', 'Adaptive Learning-Based Path', ''),
    AfiLearningPath('AfiLearningPath', '', 'Reinforcement Learning Path', 'AfiAdaptivePath'),
    AfiAIPath('AfiAIPath', '', 'AI-Optimized Path', 'AfiAdaptivePath');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ApproxFuncInterpolation(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ApproxFuncInterpolation fromId(final String id) {
        if (id == null) return null
        for (ApproxFuncInterpolation val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ApproxFuncInterpolation fromCode(final String code) {
        if (code == null) return null
        for (ApproxFuncInterpolation val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
