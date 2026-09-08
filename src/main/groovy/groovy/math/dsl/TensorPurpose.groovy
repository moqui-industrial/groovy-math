/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package groovy.math.dsl

enum TensorPurpose implements DslEnumValue {
    Original('TpOriginal'),
    Gradient('TpGradient'),
    Hessian('TpHessian'),
    Stress('TpStress'),
    Strain('TpStrain'),
    Inertia('TpInertia'),
    ModelParams('TpModelParams'),
    ImageRep('TpImageRep'),
    PhysicalState('TpPhysicalState'),
    FuncSampling('TpFuncSampling'),
    Covariance('TpCovariance'),
    StateMatrix('TpStateMatrix'),
    InputMatrix('TpInputMatrix'),
    OutputMatrix('TpOutputMatrix'),
    FeedforwardMatrix('TpFeedforwardMatrix'),
    StateVector('TpStateVector'),
    ControlVector('TpControlVector'),
    FeedbackGain('TpFeedbackGain'),
    ObserverGain('TpObserverGain'),
    CovarianceProcess('TpCovarianceProcess'),
    CovarianceMeasurement('TpCovarianceMeasurement')

    final String id

    TensorPurpose(final String id) {
        this.id = id
    }
}
