/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ApproxFuncDerivativePurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ApproxFuncDerivativePurpose implements DslEnumValue {
    TangentMatrix('AfdpTangentMatrix', '', 'First derivative matrix (e.g., Jacobian or directional velocity matrix).                  Includes geometric derivatives or velocity transformation (∂position/∂parameter, ∂position/∂t).', ''),
    NormalMatrix('AfdpNormalMatrix', '', 'Second derivative matrix (e.g., Hessian or acceleration transformation).                 Describes how direction or velocity changes. Includes acceleration (∂²position/∂parameter², ∂²position/∂t²).', ''),
    BinormalMatrix('AfdpBinormalMatrix', '', 'Third derivative matrix (e.g., rate of change of acceleration or binormal transformation).', ''),
    SnapMatrix('AfdpSnapMatrix', '', 'Fourth derivative matrix (snap-like linear transformation).                     Often used in smooth motion generation or symbolic modeling.', ''),
    CurvatureTensor('AfdpCurvatureTensor', '', 'Curvature tensor matrix. Represents curvature behavior for multiparameter paths or geometric domains.', ''),
    TorsionTensor('AfdpTorsionTensor', '', 'Matrix representation of torsion or twisting behavior along the path (symbolic or 3D geometric interpretation).', ''),
    OsculatingPlaneMatrix('AfdpOsculatingPlaneMatrix', '', 'Plane matrix spanned by tangent and normal vectors at the point (symbolic representation of the osculating plane).', ''),
    GeodesicCurvatureMatrix('AfdpGeodesicCurvatureMatrix', '', 'Matrix form representing geodesic curvature on curved surfaces (i.e., deviation from intrinsic path).', ''),
    SurfaceNormalMatrix('AfdpSurfaceNormalMatrix', '', 'Surface normal expressed as a matrix transformation (e.g., from tangent space to world space).', ''),
    JerkTensor('AfdpJerkTensor', '', 'High-order tensor for jerk (3rd derivative) with symbolic or multi-parameter domains.', ''),
    SnapTensor('AfdpSnapTensor', '', 'Symbolic or multi-axis matrix for 4th derivative behavior (snap-like change in acceleration direction).', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ApproxFuncDerivativePurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ApproxFuncDerivativePurpose fromId(final String id) {
        if (id == null) return null
        for (ApproxFuncDerivativePurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ApproxFuncDerivativePurpose fromCode(final String code) {
        if (code == null) return null
        for (ApproxFuncDerivativePurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
