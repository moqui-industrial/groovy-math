/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorApproxMethod
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorApproxMethod implements DslEnumValue {
    Exact('TapExact', '', 'Exact / Full-Precision (No Approximation)', ''),
    TruncSVD('TapTruncSVD', '', 'Truncated SVD (Rank-K)', ''),
    Tucker('TapTucker', '', 'Tucker / HOSVD compression', ''),
    CP('TapCP', '', 'Canonical Polyadic (CP) Rank-R', ''),
    TT('TapTT', '', 'Tensor-Train (TT) Truncation', ''),
    Prune('TapPrune', '', 'Structured / Unstructured Pruning', ''),
    RandProj('TapRandProj', '', 'Random Projection / Count-Sketch', ''),
    CUR('TapCUR', '', 'CUR / Leverage-Score Sampling', ''),
    Wavelet('TapWavelet', '', 'Wavelet / Multiresolution Compression', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorApproxMethod(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorApproxMethod fromId(final String id) {
        if (id == null) return null
        for (TensorApproxMethod val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorApproxMethod fromCode(final String code) {
        if (code == null) return null
        for (TensorApproxMethod val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
