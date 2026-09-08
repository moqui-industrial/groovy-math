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

enum ParameterPurpose implements DslEnumValue {
    MathModel('PpMathModel'),
    Physical('PpPhysical'),
    FluidProperty('PpFluidProperty'),
    BoundaryCondition('PpBoundaryCondition'),
    SolverControl('PpSolverControl'),
    NumericalScheme('PpNumericalScheme'),
    Mesh('PpMesh'),
    Control('PpControl'),
    MlHyperparameter('PpMlHyperparameter'),
    LearningRate('PpLearningRate'),
    WeightDecay('PpWeightDecay'),
    BatchSize('PpBatchSize'),
    Epochs('PpEpochs'),
    Momentum('PpMomentum'),
    DropoutRate('PpDropoutRate')

    final String id

    ParameterPurpose(final String id) {
        this.id = id
    }
}
