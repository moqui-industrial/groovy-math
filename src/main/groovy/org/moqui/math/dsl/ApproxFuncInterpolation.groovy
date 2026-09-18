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

    static ApproxFuncInterpolation fromName(final String name) {
        if (name == null) return null
        for (ApproxFuncInterpolation val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('PointToPointPath'.equalsIgnoreCase(name)) return AfiP2PPath
        if ('PolynomialPath'.equalsIgnoreCase(name)) return AfiPolyPath
        if ('LinearPath'.equalsIgnoreCase(name)) return AfiLinearPath
        if ('ParabolicPath'.equalsIgnoreCase(name)) return AfiParabolicPath
        if ('PathWithAsymmetricConstantAcceleration'.equalsIgnoreCase(name)) return AfiAsymmetricPath
        if ('CubicPath'.equalsIgnoreCase(name)) return AfiCubicPath
        if ('PolynomialOf5thDegreePath'.equalsIgnoreCase(name)) return Afi5thDegPolyPath
        if ('PolynomialOfDegreeSevenPath'.equalsIgnoreCase(name)) return Afi7thDegPolyPath
        if ('PolynomialOfHigherDegreePath'.equalsIgnoreCase(name)) return AfiHighDegPolyPath
        if ('TrigonometricPath'.equalsIgnoreCase(name)) return AfiTrigPath
        if ('HarmonicPath'.equalsIgnoreCase(name)) return AfiHarmonicPath
        if ('CycloidalPath'.equalsIgnoreCase(name)) return AfiCycloidalPath
        if ('EllipticPath'.equalsIgnoreCase(name)) return AfiEllipticPath
        if ('ExponentialPath'.equalsIgnoreCase(name)) return AfiExpPath
        if ('PathBasedOnTheFourierSeriesExpansion'.equalsIgnoreCase(name)) return AfiFourierSeriesExpansionPath
        if ('GutmanPath'.equalsIgnoreCase(name)) return AfiGutmanPath
        if ('FreudensteinPath'.equalsIgnoreCase(name)) return AfiFreudensteinPath
        if ('MultipointPath'.equalsIgnoreCase(name)) return AfiMultipointPath
        if ('MultipointApproximatedPath'.equalsIgnoreCase(name)) return AfiApproxPath
        if ('ApproximatedPolynomialPath'.equalsIgnoreCase(name)) return AfiApproxPolyPath
        if ('ApproximatedOrthogonalPolynomialsPath'.equalsIgnoreCase(name)) return AfiApproxOrthogonalPolyPath
        if ('ApproximatedTrigonometricPolynomialPath'.equalsIgnoreCase(name)) return AfiApproxTrigPolynomialPath
        if ('ApproximatedCubicSplinesPath'.equalsIgnoreCase(name)) return AfiApproxCubicSplinesPath
        if ('MultipointInterpolatedPath'.equalsIgnoreCase(name)) return AfiInterpPath
        if ('InterpolatedPolynomialPath'.equalsIgnoreCase(name)) return AfiInterpPolyPath
        if ('InterpolatedOrthogonalPolynomialsPath'.equalsIgnoreCase(name)) return AfiInterpOrthogonalPolyPath
        if ('InterpolatedTrigonometricPolynomialPath'.equalsIgnoreCase(name)) return AfiInterpTrigPolyPath
        if ('InterpolatedCubicSplinesPath'.equalsIgnoreCase(name)) return AfiInterpCubicSplinesPath
        if ('LinearCurvePath'.equalsIgnoreCase(name)) return AfiPiecewise
        if ('BZierCurvePath'.equalsIgnoreCase(name)) return AfiBezierPath
        if ('BSplinePath'.equalsIgnoreCase(name)) return AfiBSplinePath
        if ('NurbsPath'.equalsIgnoreCase(name)) return AfiNurbsPath
        if ('HermiteSplineCurvePath'.equalsIgnoreCase(name)) return AfiHermiteSpline
        if ('LagrangePolynomialCurvePath'.equalsIgnoreCase(name)) return AfiLagrangePolynomial
        if ('CatmullRomSplineCurvePath'.equalsIgnoreCase(name)) return AfiCatmullRomSpline
        if ('AdaptiveLearningBasedPath'.equalsIgnoreCase(name)) return AfiAdaptivePath
        if ('ReinforcementLearningPath'.equalsIgnoreCase(name)) return AfiLearningPath
        if ('AiOptimizedPath'.equalsIgnoreCase(name)) return AfiAIPath
        null
    }
}
