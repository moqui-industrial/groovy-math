/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ApproxFuncInterpolation
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ApproxFuncInterpolation implements DslEnumValue {
    P2PPath('AfiP2PPath', '', 'Point-to-Point Path', ''),
    PolyPath('AfiPolyPath', '', 'Polynomial Path', 'AfiP2PPath'),
    LinearPath('AfiLinearPath', '', 'Linear Path (constant velocity)', 'AfiPolyPath'),
    ParabolicPath('AfiParabolicPath', '', 'Parabolic Path (constant acceleration)', 'AfiPolyPath'),
    AsymmetricPath('AfiAsymmetricPath', '', 'Path with Asymmetric Constant Acceleration', 'AfiPolyPath'),
    CubicPath('AfiCubicPath', '', 'Cubic Path', 'AfiPolyPath'),
    Afi5thDegPolyPath('Afi5thDegPolyPath', '', 'Polynomial of 5th Degree Path', 'AfiPolyPath'),
    Afi7thDegPolyPath('Afi7thDegPolyPath', '', 'Polynomial of Degree Seven Path', 'AfiPolyPath'),
    HighDegPolyPath('AfiHighDegPolyPath', '', 'Polynomial of Higher Degree Path', 'AfiPolyPath'),
    TrigPath('AfiTrigPath', '', 'Trigonometric Path', 'AfiP2PPath'),
    HarmonicPath('AfiHarmonicPath', '', 'Harmonic Path', 'AfiTrigPath'),
    CycloidalPath('AfiCycloidalPath', '', 'Cycloidal Path', 'AfiTrigPath'),
    EllipticPath('AfiEllipticPath', '', 'Elliptic Path', 'AfiTrigPath'),
    ExpPath('AfiExpPath', '', 'Exponential Path', 'AfiP2PPath'),
    FourierSeriesExpansionPath('AfiFourierSeriesExpansionPath', '', 'Path Based on the Fourier Series Expansion', 'AfiP2PPath'),
    GutmanPath('AfiGutmanPath', '', 'Gutman Path', 'AfiFourierSeriesExpansionPath'),
    FreudensteinPath('AfiFreudensteinPath', '', 'Freudenstein Path', 'AfiFourierSeriesExpansionPath'),
    MultipointPath('AfiMultipointPath', '', 'Multipoint Path', ''),
    ApproxPath('AfiApproxPath', '', 'Multipoint Approximated Path', 'AfiMultipointPath'),
    ApproxPolyPath('AfiApproxPolyPath', '', 'Approximated Polynomial Path', 'AfiApproxPath'),
    ApproxOrthogonalPolyPath('AfiApproxOrthogonalPolyPath', '', 'Approximated Orthogonal Polynomials Path', 'AfiApproxPath'),
    ApproxTrigPolynomialPath('AfiApproxTrigPolynomialPath', '', 'Approximated Trigonometric Polynomial Path', 'AfiApproxPath'),
    ApproxCubicSplinesPath('AfiApproxCubicSplinesPath', '', 'Approximated Cubic Splines Path', 'AfiApproxPath'),
    InterpPath('AfiInterpPath', '', 'Multipoint Interpolated Path', 'AfiMultipointPath'),
    InterpPolyPath('AfiInterpPolyPath', '', 'Interpolated Polynomial Path', 'AfiInterpPath'),
    InterpOrthogonalPolyPath('AfiInterpOrthogonalPolyPath', '', 'Interpolated Orthogonal Polynomials Path', 'AfiInterpPath'),
    InterpTrigPolyPath('AfiInterpTrigPolyPath', '', 'Interpolated Trigonometric Polynomial Path', 'AfiInterpPath'),
    InterpCubicSplinesPath('AfiInterpCubicSplinesPath', '', 'Interpolated Cubic Splines Path', 'AfiInterpPath'),
    Piecewise('AfiPiecewise', '', 'Linear (Piecewise) Curve Path ', 'AfiInterpPath'),
    BezierPath('AfiBezierPath', '', 'Bézier (Bernstein Basis) Curve Path ', 'AfiInterpPath'),
    BSplinePath('AfiBSplinePath', '', 'B-Spline Path', 'AfiInterpPath'),
    NurbsPath('AfiNurbsPath', '', 'NURBS (Rational B-Spline) Path', 'AfiInterpPath'),
    HermiteSpline('AfiHermiteSpline', '', 'Hermite Spline Curve Path ', 'AfiInterpPath'),
    LagrangePolynomial('AfiLagrangePolynomial', '', 'Lagrange Polynomial Curve Path ', 'AfiInterpPath'),
    CatmullRomSpline('AfiCatmullRomSpline', '', 'Catmull-Rom Spline Curve Path ', 'AfiInterpPath'),
    AdaptivePath('AfiAdaptivePath', '', 'Adaptive Learning-Based Path', ''),
    LearningPath('AfiLearningPath', '', 'Reinforcement Learning Path', 'AfiAdaptivePath'),
    AIPath('AfiAIPath', '', 'AI-Optimized Path', 'AfiAdaptivePath');

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
