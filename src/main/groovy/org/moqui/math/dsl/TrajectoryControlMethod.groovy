/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TrajectoryControlMethod
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TrajectoryControlMethod implements DslEnumValue {
    PID('PtcmPID', '', 'PID Algorithm', ''),
    PI('PtcmPI', '', 'PI Algorithm', ''),
    PD('PtcmPD', '', 'PD Algorithm', ''),
    BangBang('PtcmBangBang', '', 'Bang-Bang Control', ''),
    LQR('PtcmLQR', '', 'Linear Quadratic Regulator (LQR)', ''),
    LQI('PtcmLQI', '', 'Linear Quadratic Integrator (LQI)', ''),
    HInfinity('PtcmHInfinity', '', 'H-infinity Control', ''),
    MPC('PtcmMPC', '', 'Model Predictive Control (MPC)', ''),
    SMC('PtcmSMC', '', 'Sliding Mode Control (SMC)', ''),
    Adaptive('PtcmAdaptive', '', 'Adaptive Control', ''),
    FuzzyLogic('PtcmFuzzyLogic', '', 'Fuzzy Logic Control', ''),
    NNControl('PtcmNNControl', '', 'Neural Network Control', ''),
    RLControl('PtcmRLControl', '', 'Reinforcement Learning Control', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TrajectoryControlMethod(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TrajectoryControlMethod fromId(final String id) {
        if (id == null) return null
        for (TrajectoryControlMethod val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TrajectoryControlMethod fromCode(final String code) {
        if (code == null) return null
        for (TrajectoryControlMethod val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static TrajectoryControlMethod fromName(final String name) {
        if (name == null) return null
        for (TrajectoryControlMethod val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('PidAlgorithm'.equalsIgnoreCase(name)) return PID
        if ('PiAlgorithm'.equalsIgnoreCase(name)) return PI
        if ('PdAlgorithm'.equalsIgnoreCase(name)) return PD
        if ('BangBangControl'.equalsIgnoreCase(name)) return BangBang
        if ('LinearQuadraticRegulator'.equalsIgnoreCase(name)) return LQR
        if ('LinearQuadraticIntegrator'.equalsIgnoreCase(name)) return LQI
        if ('HInfinityControl'.equalsIgnoreCase(name)) return HInfinity
        if ('ModelPredictiveControl'.equalsIgnoreCase(name)) return MPC
        if ('SlidingModeControl'.equalsIgnoreCase(name)) return SMC
        if ('AdaptiveControl'.equalsIgnoreCase(name)) return Adaptive
        if ('FuzzyLogicControl'.equalsIgnoreCase(name)) return FuzzyLogic
        if ('NeuralNetworkControl'.equalsIgnoreCase(name)) return NNControl
        if ('ReinforcementLearningControl'.equalsIgnoreCase(name)) return RLControl
        null
    }
}
