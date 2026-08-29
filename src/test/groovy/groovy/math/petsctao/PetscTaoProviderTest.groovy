/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.petsctao

import org.junit.jupiter.api.Test
import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathModelDataPurpose
import groovy.math.dsl.MathModelSolvingMethod
import groovy.math.dsl.MathModelType
import groovy.math.dsl.MathMeta
import groovy.math.entity.EntityDefinition
import groovy.math.entity.FieldDefinition
import groovy.math.entity.ModelDefinition

import static org.junit.jupiter.api.Assertions.assertThrows

class PetscTaoProviderTest {
    @Test
    void lowersBoundedQuadraticProgramToBackendPlan() {
        RecordingBackend backend = new RecordingBackend()
        PetscTaoProvider provider = new PetscTaoProvider('EnergyDispatch', backend)

        PetscTaoPlan plan = provider.compile(energyDispatch())
        try {
            assert provider.providerId == 'petsc-tao'
            assert plan.solverType == 'bqpip'
            assert plan.variableNames == ['GridPower', 'StoredEnergy']
            assert backend.dimension == 2
            assert backend.hessian.toList() == [2d, 0d, 0d, 4d]
            assert backend.linear.toList() == [-8d, -8d]
            assert backend.lower.toList() == [0d, 0d]
            assert backend.upper.toList() == [5d, 3d]
            assert backend.initial.toList() == [1d, 1d]

            PetscTaoResult result = provider.execute(plan, [:])
            assert result.success
            assert result.reason == 'TAO_CONVERGED_GATOL'
            assert result.objectiveValue == -24d
            assert result.variableValues == [GridPower: 4d, StoredEnergy: 2d]
        } finally {
            plan.close()
        }
        assert plan.closed
        assert backend.destroyed
    }

    @Test
    void rejectsNonSymmetricHessianBeforeCallingNativeBackend() {
        MathMeta mathMeta = energyDispatch()
        mathMeta.Matrix.named('DispatchHessian').configure {
            componentArray '[[2,1],[0,4]]'
        }
        RecordingBackend backend = new RecordingBackend()

        IllegalStateException failure = assertThrows(IllegalStateException) {
            new PetscTaoProvider('EnergyDispatch', backend).compile(mathMeta)
        }

        assert failure.message == 'Hessian must be symmetric'
        assert backend.dimension == 0
    }

    private static MathMeta energyDispatch() {
        MathDsl.math(modelDefinition()) {
            MathModelDef('QuadraticEnergyDispatch', modelTypeEnum: MathModelType.QuadraticProgram)
            MathModel('EnergyDispatch', mathModelDefId: 'QuadraticEnergyDispatch',
                solvingMethodEnum: MathModelSolvingMethod.InteriorPoint)
            Parameter('ObjectiveSense', mathModelId: 'EnergyDispatch',
                parameterAlias: 'objectiveSense', symbolicValue: 'MINIMIZE')
            Vector('EnergySourceVariables', componentArray: '["GridPower","StoredEnergy"]')
            Matrix('DispatchHessian', rows: 2, cols: 2, componentArray: '[[2,0],[0,4]]')
            Vector('DispatchLinearCost', componentArray: '[-8,-8]')
            Matrix('DispatchBounds', rows: 2, cols: 2, componentArray: '[[0,0],[5,3]]')
            Vector('DispatchInitialCondition', componentArray: '[1,1]')
            MathModelData('variables', mathModelId: 'EnergyDispatch',
                purposeEnum: MathModelDataPurpose.DecisionVariables, vectorId: 'EnergySourceVariables')
            MathModelData('hessian', mathModelId: 'EnergyDispatch',
                purposeEnum: MathModelDataPurpose.Hessian, matrixId: 'DispatchHessian')
            MathModelData('linear', mathModelId: 'EnergyDispatch',
                purposeEnum: MathModelDataPurpose.CostVector, vectorId: 'DispatchLinearCost')
            MathModelData('bounds', mathModelId: 'EnergyDispatch',
                purposeEnum: MathModelDataPurpose.VariableBounds, matrixId: 'DispatchBounds')
            MathModelData('initial', mathModelId: 'EnergyDispatch',
                purposeEnum: MathModelDataPurpose.InitialCondition, vectorId: 'DispatchInitialCondition')
        }
    }

    private static ModelDefinition modelDefinition() {
        ModelDefinition model = new ModelDefinition()
        model.addEntity(entity('MathModelDef', [mathModelDefId: true, modelTypeEnumId: false]))
        model.addEntity(entity('MathModel', [mathModelId: true, mathModelDefId: false,
            solvingMethodEnumId: false]))
        model.addEntity(entity('Parameter', [parameterId: true, mathModelId: false,
            parameterAlias: false, symbolicValue: false]))
        model.addEntity(entity('Vector', [vectorId: true, componentArray: false]))
        model.addEntity(entity('Matrix', [matrixId: true, rows: false, cols: false,
            componentArray: false]))
        model.addEntity(entity('MathModelData', [mathModelDataId: true, mathModelId: false,
            purposeEnumId: false, vectorId: false, matrixId: false]))
        model
    }

    private static EntityDefinition entity(final String name, final Map<String, Boolean> fields) {
        EntityDefinition entity = new EntityDefinition('moqui.math', name)
        fields.each { String fieldName, Boolean primaryKey ->
            entity.addField(new FieldDefinition(fieldName, 'text', primaryKey, false, null))
        }
        entity
    }

    private static final class RecordingBackend implements PetscTaoBackend {
        int dimension
        double[] hessian
        double[] linear
        double[] lower
        double[] upper
        double[] initial
        boolean destroyed

        @Override
        long createBoundedQuadraticPlan(final int dimension, final double[] hessian,
                                        final double[] linear, final double[] lowerBounds,
                                        final double[] upperBounds, final double[] initialPoint) {
            this.dimension = dimension
            this.hessian = hessian.clone()
            this.linear = linear.clone()
            this.lower = lowerBounds.clone()
            this.upper = upperBounds.clone()
            this.initial = initialPoint.clone()
            1L
        }

        @Override
        PetscTaoNativeResult solve(final long handle, final int dimension) {
            assert handle == 1L
            assert dimension == 2
            new PetscTaoNativeResult([4d, 2d] as double[], -24d, 0d, 5, 3)
        }

        @Override
        void destroy(final long handle) {
            assert handle == 1L
            destroyed = true
        }
    }
}
