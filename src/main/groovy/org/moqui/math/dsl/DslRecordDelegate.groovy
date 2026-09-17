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
import org.moqui.math.entity.ModelProvider
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
            if (arguments.length == 1 && !(arguments[0] instanceof Closure)) {
                Object fieldValue = MathDslBuilder.normalizeValue(arguments[0])
                record.values.put(fieldName, fieldValue)
                record.provider.configure { value -> value.put(fieldName, fieldValue) }
                return this
            }
        }

        if (root.vocabulary.isTransformationEntity(record.definition.fullName)) {
            if (record.definition.fullName != 'moqui.math.Transformation') {
                if (!root.mathMeta.hasEntity('Transformation') || root.mathMeta.entity('Transformation').findByName(record.modelKey) == null) {
                    Map<String, Object> tVals = [transformationId: record.modelKey, transformationTypeEnumId: 'TtMeta']
                    root.mathMeta.declare('moqui.math.Transformation', record.modelKey, tVals)
                }
            }

            if (['resultMatrix', 'resultVector', 'resultTensor', 'resultParameter', 'resultFunction'].contains(name)) {
                Object targetId = arguments.length > 0 ? (arguments[0] instanceof org.moqui.math.metamodel.EntityRef ? ((org.moqui.math.metamodel.EntityRef<?>) arguments[0]).id : (arguments[0] instanceof ModelProvider ? ((ModelProvider) arguments[0]).name : arguments[0]?.toString())) : null
                String fieldIdName = "${name}Id"
                if (root.mathMeta.hasEntity('Transformation') && root.mathMeta.entity('Transformation').findByName(record.modelKey) != null) {
                    root.mathMeta.entity('Transformation').findByName(record.modelKey).configure { org.moqui.math.entity.ModelValue val -> val.put(fieldIdName, targetId) }
                }
                return this
            }

            String operandTypeEnumId = root.vocabulary.getOperandTypeEnumId(name)
            if (operandTypeEnumId != null && arguments.length == 1 && !(arguments[0] instanceof Map) && !(arguments[0] instanceof Closure)) {
                Object targetId = arguments.length > 0 ? (arguments[0] instanceof org.moqui.math.metamodel.EntityRef ? ((org.moqui.math.metamodel.EntityRef<?>) arguments[0]).id : (arguments[0] instanceof ModelProvider ? ((ModelProvider) arguments[0]).name : arguments[0]?.toString())) : null
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

        String capName = name ? name.capitalize() : name
        if (record.definition.fullName == 'moqui.math.MathModel' && ['Matrix', 'Vector', 'Tensor'].contains(capName)) {
            DslDeclaration childDecl = root.declareNested(capName, rawArguments, null, null)
            String targetKey = childDecl.modelKey
            long seq = root.mathMeta.hasEntity('MathModelData') ?
                (long) root.mathMeta.entity('MathModelData').count { Object v ->
                    (v instanceof org.moqui.math.entity.ModelValue ? ((org.moqui.math.entity.ModelValue) v).get('mathModelId') : null) == record.modelKey
                } : 0L
            String dataId = "${record.modelKey}_Data_${targetKey}"
            Map<String, Object> dataValues = [
                mathModelDataId: dataId,
                mathModelId: record.modelKey,
                dataTypeEnumId: "Mmdt${capName}",
                sequenceNum: seq
            ]
            if (capName == 'Matrix') dataValues.put('matrixId', targetKey)
            else if (capName == 'Vector') dataValues.put('vectorId', targetKey)
            else if (capName == 'Tensor') dataValues.put('tensorId', targetKey)
            root.mathMeta.declare('moqui.math.MathModelData', dataId, dataValues)
            return childDecl.provider
        }

        RelationshipDefinition relationship = record.definition.relationships.get(name)
        if (relationship != null) {
            if (arguments.length == 1 && arguments[0] instanceof Closure) {
                return new DslRelationshipDelegate(root, record, relationship)
                    .configure((Closure<?>) arguments[0])
            }
            return root.declareNested(relationship.relatedEntityName, arguments, record, relationship).provider
        }
        root.declareNested(name, arguments, record).provider
    }

    private final Map<String, Object> localVariables = new LinkedHashMap<>()

    @CompileStatic(TypeCheckingMode.SKIP)
    Object propertyMissing(final String name) {
        if (localVariables.containsKey(name)) return localVariables.get(name)
        DslSymbol symbol = root.vocabulary.resolveSymbol(name)
        if (symbol != null) return symbol
        throw new MissingPropertyException(name, getClass())
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    void propertyMissing(final String name, final Object value) {
        localVariables.put(name, value)
    }
}
