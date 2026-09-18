/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorPurpose implements DslEnumValue {
    Original('TpOriginal', '', 'Original Tensor', ''),
    Gradient('TpGradient', '', 'Gradient Tensor', ''),
    Hessian('TpHessian', '', 'Hessian Tensor', ''),
    Stress('TpStress', '', 'Stress Tensor', ''),
    Strain('TpStrain', '', 'Strain Tensor', ''),
    Inertia('TpInertia', '', 'Mass/Inertia Tensor', ''),
    ModelParams('TpModelParams', '', 'Trainable Model Parameters', ''),
    ImageRep('TpImageRep', '', 'Image / Feature Map Data', ''),
    PhysicalState('TpPhysicalState', '', 'Physical Simulation State', ''),
    FuncSampling('TpFuncSampling', '', 'Sampled Discrete Function', ''),
    Covariance('TpCovariance', '', 'Covariance / Correlation Matrix', ''),
    StateMatrix('TpStateMatrix', '', 'Dynamic State Transition Matrix (A)', ''),
    InputMatrix('TpInputMatrix', '', 'Control Input Matrix (B)', ''),
    OutputMatrix('TpOutputMatrix', '', 'Measurement Output Matrix (C)', ''),
    FeedforwardMatrix('TpFeedforwardMatrix', '', 'Direct Transmission Feedforward Matrix (D)', ''),
    StateVector('TpStateVector', '', 'State Vector x(t)', ''),
    ControlVector('TpControlVector', '', 'Control Input Vector u(t)', ''),
    FeedbackGain('TpFeedbackGain', '', 'State Feedback Gain Matrix (K)', ''),
    ObserverGain('TpObserverGain', '', 'Observer / Kalman Gain Matrix (L)', ''),
    CovarianceProcess('TpCovarianceProcess', '', 'Process Noise Covariance Matrix (Q)', ''),
    CovarianceMeasurement('TpCovarianceMeasurement', '', 'Measurement Noise Covariance Matrix (R)', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorPurpose fromId(final String id) {
        if (id == null) return null
        for (TensorPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorPurpose fromCode(final String code) {
        if (code == null) return null
        for (TensorPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
