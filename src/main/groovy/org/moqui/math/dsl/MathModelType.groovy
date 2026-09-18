/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelType implements DslEnumValue {
    ModelDriven('MmtModelDriven', '', 'Model-Driven / Physics-Based / Constructed Model', ''),
    Optimization('MmtOptimization', '', 'Optimization Problem', 'MmtModelDriven'),
    Symbolic('MmtSymbolic', '', 'Symbolic / Analytic Model', 'MmtModelDriven'),
    ElementaryFunc('MmtElementaryFunc', '', 'Elementary Equation', 'MmtModelDriven'),
    CodeDefinedFunction('MmtCodeDefinedFunction', '', 'Code-Defined Function', 'MmtModelDriven'),
    Functional('MmtFunctional', '', 'Functional Model', 'MmtModelDriven'),
    Algebraic('MmtAlgebraic', '', 'Algebraic Equations', 'MmtModelDriven'),
    Polynomial('MmtPolynomial', '', 'Polynomial', 'MmtModelDriven'),
    RationalFunc('MmtRationalFunc', '', 'Rational Function', 'MmtModelDriven'),
    ParametricPath('MmtParametricPath', '', 'Parametric Path', 'MmtModelDriven'),
    PDEs('MmtPDEs', '', 'Partial Differential Equation PDE System', 'MmtModelDriven'),
    CFD('MmtCFD', '', 'Computational Fluid Dynamics', 'MmtPDEs'),
    ODEs('MmtODEs', '', 'Ordinary Differential Equations ODE System', 'MmtModelDriven'),
    DynamicSystem('MmtDynamicSystem', '', 'Dynamic System', 'MmtModelDriven'),
    LinearEqSystem('MmtLinearEqSystem', '', 'Linear Equation System', 'MmtModelDriven'),
    NonLinearEqSystem('MmtNonLinearEqSystem', '', 'Non Linear Equation System', 'MmtModelDriven'),
    Constraint('MmtConstraint', '', 'Constraint', 'MmtModelDriven'),
    DataDriven('MmtDataDriven', '', 'Data Driven Model', ''),
    MlModel('MmtMlModel', '', 'Machine‑Learning Model', 'MmtDataDriven'),
    DlModel('MmtDlModel', '', 'Deep‑Learning Model', 'MmtDataDriven'),
    ComputerVision('MmtComputerVision', '', 'Computer Vision & Image Processing', 'MmtDataDriven'),
    StochasticProcess('MmtStochasticProcess', '', 'Stochastic Process', 'MmtModelDriven'),
    Lp('MmtLp', '', 'Linear Program (LP)', 'MmtOptimization'),
    Qp('MmtQp', '', 'Quadratic Program (QP)', 'MmtOptimization'),
    Milp('MmtMilp', '', 'Mixed-Integer Linear Program (MILP)', 'MmtOptimization'),
    Miqp('MmtMiqp', '', 'Mixed-Integer Quadratic Program (MIQP)', 'MmtOptimization'),
    Socp('MmtSocp', '', 'Second-Order Cone Program (SOCP)', 'MmtOptimization'),
    Sdp('MmtSdp', '', 'Semidefinite Program (SDP)', 'MmtOptimization'),
    Qcqp('MmtQcqp', '', 'Quadratically Constrained QP (QCQP)', 'MmtOptimization'),
    Nlp('MmtNlp', '', 'Nonlinear Program (NLP)', 'MmtOptimization'),
    Cp('MmtCp', '', 'Constraint Programming (CP)', 'MmtOptimization'),
    MarkovChain('MmtMarkovChain', '', 'Markov Chain', 'MmtStochasticProcess'),
    BirthDeath('MmtBirthDeath', '', 'Birth-Death Process', 'MmtStochasticProcess'),
    PoissonProcess('MmtPoissonProcess', '', 'Poisson Process', 'MmtStochasticProcess'),
    Queueing('MmtQueueing', '', 'Queueing Model', 'MmtStochasticProcess'),
    Mdp('MmtMdp', '', 'Markov Decision Process (MDP)', 'MmtStochasticProcess'),
    Smdp('MmtSmdp', '', 'Semi-Markov Decision Process (SMDP)', 'MmtStochasticProcess'),
    Pomdp('MmtPomdp', '', 'Partially Observable MDP (POMDP)', 'MmtStochasticProcess'),
    DlCnn('MmtDlCnn', '', 'Convolutional Neural Network', 'MmtDlModel'),
    DlRnn('MmtDlRnn', '', 'Recurrent Neural Network', 'MmtDlModel'),
    DlFeedforward('MmtDlFeedforward', '', 'Feedforward Neural Network', 'MmtDlModel'),
    DlGenerative('MmtDlGenerative', '', 'Generative Adversarial Neural Network', 'MmtDlModel'),
    DlLongShort('MmtDlLongShort', '', 'Long Short-Term Memory Neural Network', 'MmtDlModel'),
    DlDeep('MmtDlDeep', '', 'Deep Neural Network', 'MmtDlModel'),
    DlRadialBasisFunc('MmtDlRadialBasisFunc', '', 'Radial Basis Function Neural Network', 'MmtDlModel'),
    DlPerceptron('MmtDlPerceptron', '', 'Perceptron Neural Network', 'MmtDlModel'),
    DlModular('MmtDlModular', '', 'Modular Neural Network', 'MmtDlModel'),
    DlDeconvolutional('MmtDlDeconvolutional', '', 'Deconvolutional Neural Network', 'MmtDlModel'),
    DlAutoencoder('MmtDlAutoencoder', '', 'Autoencoder', 'MmtDlModel'),
    DlSoms('MmtDlSoms', '', 'Self-Organizing Maps', 'MmtDlModel'),
    DlTransformer('MmtDlTransformer', '', 'Transformer Neural Network', 'MmtDlModel'),
    DlSeqToSeq('MmtDlSeqToSeq', '', 'Sequence-To-Sequence Neural Network', 'MmtDlModel'),
    Regression('MmtRegression', '', 'Statistical Regression', 'MmtDataDriven'),
    MeasuredDataSeries('MmtMeasuredDataSeries', '', 'Measured Data Series Model', 'MmtDataDriven'),
    LinearAlgebra('MmtLinearAlgebra', 'LINEAR_ALGEBRA', 'Linear Algebra', 'MmtModelDriven');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MathModelType fromId(final String id) {
        if (id == null) return null
        for (MathModelType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelType fromCode(final String code) {
        if (code == null) return null
        for (MathModelType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
