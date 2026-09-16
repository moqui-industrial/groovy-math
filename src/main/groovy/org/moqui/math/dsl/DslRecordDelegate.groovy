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

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.transform.TypeCheckingMode
import org.moqui.math.entity.RelationshipDefinition

@CompileStatic
@PackageScope
final class DslRecordDelegate {
    private final MathDslBuilder root
    private final DslDeclaration record

    DslRecordDelegate(final MathDslBuilder root, final DslDeclaration record) {
        this.root = root
        this.record = record
    }

    DslRecordDelegate configure(final Closure<?> action) {
        Closure<?> configured = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        configured.resolveStrategy = Closure.DELEGATE_ONLY
        if (configured.maximumNumberOfParameters == 0) configured.call()
        else configured.call(this)
        this
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String name, final Object rawArguments) {
        Object[] arguments = rawArguments instanceof Object[] ?
            (Object[]) rawArguments : [rawArguments] as Object[]
        String fieldName = MathDslBuilder.resolveFieldName(record.definition, name)
        if (record.definition.fields.containsKey(fieldName)) {
            if (arguments.length != 1 || arguments[0] instanceof Closure) {
                throw new MissingMethodException(name, getClass(), arguments)
            }
            Object fieldValue = MathDslBuilder.normalizeValue(arguments[0])
            record.values.put(fieldName, fieldValue)
            record.provider.configure { value -> value.put(fieldName, fieldValue) }
            return this
        }

        List<String> transformationEntities = [
            'moqui.math.Transformation', 'moqui.math.DiagonalExtraction', 'moqui.math.TriangularExtraction',
            'moqui.math.BandExtraction', 'moqui.math.BlockMatrixExtraction', 'moqui.math.MatrixDecomposition',
            'moqui.math.TensorDecomposition', 'moqui.math.TensorSlice', 'moqui.math.NormResult',
            'moqui.math.CoordinateSystemTransformation'
        ]
        if (transformationEntities.contains(record.definition.fullName)) {
            if (record.definition.fullName != 'moqui.math.Transformation') {
                if (!root.mathMeta.hasEntity('Transformation') || root.mathMeta.entity('Transformation').findByName(record.modelKey) == null) {
                    Map<String, Object> tVals = [transformationId: record.modelKey, transformationTypeEnumId: 'TtMeta']
                    root.mathMeta.declare('moqui.math.Transformation', record.modelKey, tVals)
                }
            }

            if (['resultMatrix', 'resultVector', 'resultTensor', 'resultParameter', 'resultFunction'].contains(name)) {
                Object targetId = arguments.length > 0 ? (arguments[0] instanceof org.moqui.math.metamodel.EntityRef ? ((org.moqui.math.metamodel.EntityRef<?>) arguments[0]).id : arguments[0]?.toString()) : null
                String fieldIdName = "${name}Id"
                if (root.mathMeta.hasEntity('Transformation') && root.mathMeta.entity('Transformation').findByName(record.modelKey) != null) {
                    root.mathMeta.entity('Transformation').findByName(record.modelKey).configure { org.moqui.math.entity.ModelValue val -> val.put(fieldIdName, targetId) }
                }
                return this
            }

            Map<String, String> operandTypeMap = [
                leftMatrix: 'TotLeftMatrix',
                rightMatrix: 'TotRightMatrix',
                operandMatrix: 'TotMatrix',
                leftVector: 'TotLeftVector',
                rightVector: 'TotRightVector',
                operandVector: 'TotVector',
                leftTensor: 'TotLeftTensor',
                rightTensor: 'TotRightTensor',
                operandTensor: 'TotTensor',
                kernelMatrix: 'TotKernelMatrix',
                biasMatrix: 'TotBiasMatrix',
                kernelVector: 'TotKernelVector',
                biasVector: 'TotBiasVector',
                operandTransformation: 'TotTransformation',
                leftTransformation: 'TotLeft',
                rightTransformation: 'TotRight',
                operandParameter: 'TotParameter'
            ]
            if (operandTypeMap.containsKey(name)) {
                String operandTypeEnumId = operandTypeMap.get(name)
                Object targetId = arguments.length > 0 ? (arguments[0] instanceof org.moqui.math.metamodel.EntityRef ? ((org.moqui.math.metamodel.EntityRef<?>) arguments[0]).id : arguments[0]?.toString()) : null
                Map<String, Object> opValues = new LinkedHashMap<>()
                opValues.put('transformationId', record.modelKey)
                opValues.put('operandTypeEnumId', operandTypeEnumId)
                if (name.endsWith('Matrix')) opValues.put('operandMatrixId', targetId)
                else if (name.endsWith('Vector')) opValues.put('operandVectorId', targetId)
                else if (name.endsWith('Tensor')) opValues.put('operandTensorId', targetId)
                else if (name.endsWith('Transformation')) opValues.put('operandTransformationId', targetId)
                else if (name.endsWith('Parameter')) opValues.put('operandParameterId', targetId)

                long opIndex = root.mathMeta.hasEntity('TransformationOperand') ?
                    (long) root.mathMeta.entity('TransformationOperand').count { Object v ->
                        (v instanceof org.moqui.math.entity.ModelValue ? ((org.moqui.math.entity.ModelValue) v).get('transformationId') : null) == record.modelKey
                    } : 0L
                opValues.put('operandIndex', opIndex)
                String opKey = "${record.modelKey}_Op_${opIndex}"
                root.mathMeta.declare('moqui.math.TransformationOperand', opKey, opValues)
                return this
            }
        }

        if (record.definition.fullName == 'moqui.math.MathModel' && ['Matrix', 'Vector', 'Tensor'].contains(name)) {
            DslDeclaration childDecl = root.declareNested(name, rawArguments, null, null)
            String targetKey = childDecl.modelKey
            long seq = root.mathMeta.hasEntity('MathModelData') ?
                (long) root.mathMeta.entity('MathModelData').count { Object v ->
                    (v instanceof org.moqui.math.entity.ModelValue ? ((org.moqui.math.entity.ModelValue) v).get('mathModelId') : null) == record.modelKey
                } : 0L
            String dataId = "${record.modelKey}_Data_${targetKey}"
            Map<String, Object> dataValues = [
                mathModelDataId: dataId,
                mathModelId: record.modelKey,
                dataTypeEnumId: "Mmdt${name}",
                sequenceNum: seq
            ]
            if (name == 'Matrix') dataValues.put('matrixId', targetKey)
            else if (name == 'Vector') dataValues.put('vectorId', targetKey)
            else if (name == 'Tensor') dataValues.put('tensorId', targetKey)
            root.mathMeta.declare('moqui.math.MathModelData', dataId, dataValues)
            return childDecl.provider
        }

        RelationshipDefinition relationship = record.definition.relationships.get(name)
        if (relationship != null) {
            if (arguments.length == 1 && arguments[0] instanceof Closure) {
                return new DslRelationshipDelegate(root, record, relationship)
                    .configure((Closure<?>) arguments[0])
            }
            return root.declareNested(relationship.relatedEntityName, arguments, record, relationship)
        }
        root.declareNested(name, arguments, record)
    }
}
