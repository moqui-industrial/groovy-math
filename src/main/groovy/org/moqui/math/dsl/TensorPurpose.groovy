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
    CovarianceMeasurement('TpCovarianceMeasurement', '', 'Measurement Noise Covariance Matrix (R)', ''),
    OptimizerState('TpOptimizerState', '', 'Optimizer State (momentum, variance)', '');

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

    static TensorPurpose fromName(final String name) {
        if (name == null) return null
        for (TensorPurpose val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('OriginalTensor'.equalsIgnoreCase(name)) return Original
        if ('GradientTensor'.equalsIgnoreCase(name)) return Gradient
        if ('HessianTensor'.equalsIgnoreCase(name)) return Hessian
        if ('StressTensor'.equalsIgnoreCase(name)) return Stress
        if ('StrainTensor'.equalsIgnoreCase(name)) return Strain
        if ('MassInertiaTensor'.equalsIgnoreCase(name)) return Inertia
        if ('TrainableModelParameters'.equalsIgnoreCase(name)) return ModelParams
        if ('ImageFeatureMapData'.equalsIgnoreCase(name)) return ImageRep
        if ('PhysicalSimulationState'.equalsIgnoreCase(name)) return PhysicalState
        if ('SampledDiscreteFunction'.equalsIgnoreCase(name)) return FuncSampling
        if ('CovarianceCorrelationMatrix'.equalsIgnoreCase(name)) return Covariance
        if ('DynamicStateTransitionMatrix'.equalsIgnoreCase(name)) return StateMatrix
        if ('ControlInputMatrix'.equalsIgnoreCase(name)) return InputMatrix
        if ('MeasurementOutputMatrix'.equalsIgnoreCase(name)) return OutputMatrix
        if ('DirectTransmissionFeedforwardMatrix'.equalsIgnoreCase(name)) return FeedforwardMatrix
        if ('StateVectorX'.equalsIgnoreCase(name)) return StateVector
        if ('ControlInputVectorU'.equalsIgnoreCase(name)) return ControlVector
        if ('StateFeedbackGainMatrix'.equalsIgnoreCase(name)) return FeedbackGain
        if ('ObserverKalmanGainMatrix'.equalsIgnoreCase(name)) return ObserverGain
        if ('ProcessNoiseCovarianceMatrix'.equalsIgnoreCase(name)) return CovarianceProcess
        if ('MeasurementNoiseCovarianceMatrix'.equalsIgnoreCase(name)) return CovarianceMeasurement
        null
    }
}
