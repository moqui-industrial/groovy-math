/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MatrixPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MatrixPurpose implements DslEnumValue {
    Original('MpOriginal', '', 'Original Matrix', ''),
    Approximate('MpApproximate', '', 'Approximate Matrix', ''),
    Block('MpBlock', '', 'Block Matrix', ''),
    SvdU('MpSvdU', '', 'SVD U Matrix', ''),
    SvdSigma('MpSvdSigma', '', 'SVD Σ Matrix', ''),
    SvdV('MpSvdV', '', 'SVD V Matrix', ''),
    LuL('MpLuL', '', 'LU L Matrix', ''),
    LuU('MpLuU', '', 'LU U Matrix', ''),
    State('MpState', '', 'Dynamic State Transition Matrix (A)', ''),
    Input('MpInput', '', 'Control Input Matrix (B)', ''),
    Output('MpOutput', '', 'Measurement Output Matrix (C)', ''),
    Feedforward('MpFeedforward', '', 'Direct Transmission Feedforward Matrix (D)', ''),
    FeedbackGain('MpFeedbackGain', '', 'State Feedback Gain Matrix (K)', ''),
    ObserverGain('MpObserverGain', '', 'Observer or Kalman Gain Matrix (L)', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MatrixPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MatrixPurpose fromId(final String id) {
        if (id == null) return null
        for (MatrixPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MatrixPurpose fromCode(final String code) {
        if (code == null) return null
        for (MatrixPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MatrixPurpose fromName(final String name) {
        if (name == null) return null
        for (MatrixPurpose val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('OriginalMatrix'.equalsIgnoreCase(name)) return Original
        if ('ApproximateMatrix'.equalsIgnoreCase(name)) return Approximate
        if ('BlockMatrix'.equalsIgnoreCase(name)) return Block
        if ('SvdUMatrix'.equalsIgnoreCase(name)) return SvdU
        if ('SvdMatrix'.equalsIgnoreCase(name)) return SvdSigma
        if ('SvdVMatrix'.equalsIgnoreCase(name)) return SvdV
        if ('LuLMatrix'.equalsIgnoreCase(name)) return LuL
        if ('LuUMatrix'.equalsIgnoreCase(name)) return LuU
        if ('DynamicStateTransitionMatrix'.equalsIgnoreCase(name)) return State
        if ('ControlInputMatrix'.equalsIgnoreCase(name)) return Input
        if ('MeasurementOutputMatrix'.equalsIgnoreCase(name)) return Output
        if ('DirectTransmissionFeedforwardMatrix'.equalsIgnoreCase(name)) return Feedforward
        if ('StateFeedbackGainMatrix'.equalsIgnoreCase(name)) return FeedbackGain
        if ('ObserverOrKalmanGainMatrix'.equalsIgnoreCase(name)) return ObserverGain
        null
    }
}
