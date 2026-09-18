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
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.FieldDefinition
import org.moqui.math.entity.ModelProvider
import org.moqui.math.entity.ModelValue
import org.moqui.math.entity.RelationshipDefinition

@CompileStatic
final class MathDslBuilder {
    final MathMeta mathMeta
    final DslVocabulary vocabulary

    private final Map<String, Object> localVariables = new LinkedHashMap<>()

    MathDslBuilder(final MathMeta mathMeta) {
        this.mathMeta = Objects.requireNonNull(mathMeta, 'Math metadata must not be null')
        this.vocabulary = DslVocabulary.of(mathMeta.definition)
    }

    ModelProvider entity(final String entityName, final String modelKey,
                         final Map<String, ?> values = Collections.emptyMap(),
                         final Closure<?> action = null) {
        EntityDefinition definition = vocabulary.findEntity(entityName)
        LinkedHashMap<String, Object> attributes = copyValues(values)
        declare(definition, modelKey, attributes, action, null, null).provider
    }

    // Top-level literal & data constructors
    ModelProvider matrix(final Object... args) {
        Map<String, Object> options = new LinkedHashMap<>()
        String key = null
        int i = 0
        if (args.length > i && args[i] instanceof CharSequence) {
            key = args[i].toString()
            i++
        }
        if (args.length > i && args[i] instanceof Number && args.length > i + 1 && args[i + 1] instanceof Number) {
            options.put('rows', ((Number) args[i]).intValue())
            options.put('cols', ((Number) args[i + 1]).intValue())
            i += 2
        } else if (args.length > i && args[i] instanceof List) {
            options.put('componentArray', args[i])
            i++
        }
        while (i < args.length) {
            if (args[i] instanceof Map) options.putAll((Map<String, Object>) args[i])
            i++
        }
        if (key == null) {
            key = options.remove('_key')?.toString() ?: options.get('name')?.toString() ?: "Matrix_${System.identityHashCode(options)}"
        }
        if (!options.containsKey('name')) options.put('name', key)
        ModelProvider p = declare(vocabulary.findEntity('Matrix'), key, options, null, null, null).provider
        localVariables.put(key, p)
        p
    }

    ModelProvider vector(final Object... args) {
        Map<String, Object> options = new LinkedHashMap<>()
        String key = null
        int i = 0
        if (args.length > i && args[i] instanceof CharSequence) {
            key = args[i].toString()
            i++
        }
        if (args.length > i && args[i] instanceof Number) {
            options.put('dimension', ((Number) args[i]).intValue())
            i++
        } else if (args.length > i && args[i] instanceof List) {
            options.put('componentArray', args[i])
            i++
        }
        while (i < args.length) {
            if (args[i] instanceof Map) options.putAll((Map<String, Object>) args[i])
            i++
        }
        if (key == null) {
            key = options.remove('_key')?.toString() ?: options.get('name')?.toString() ?: "Vector_${System.identityHashCode(options)}"
        }
        if (!options.containsKey('name')) options.put('name', key)
        ModelProvider p = declare(vocabulary.findEntity('Vector'), key, options, null, null, null).provider
        localVariables.put(key, p)
        p
    }

    ModelProvider tensor(final Object... args) {
        Map<String, Object> options = new LinkedHashMap<>()
        String key = null
        int i = 0
        if (args.length > i && args[i] instanceof CharSequence) {
            key = args[i].toString()
            i++
        }
        if (args.length > i && args[i] instanceof List) {
            options.put('componentArray', args[i])
            i++
        }
        while (i < args.length) {
            if (args[i] instanceof Map) options.putAll((Map<String, Object>) args[i])
            i++
        }
        if (key == null) {
            key = options.remove('_key')?.toString() ?: options.get('name')?.toString() ?: "Tensor_${System.identityHashCode(options)}"
        }
        if (!options.containsKey('name')) options.put('name', key)
        ModelProvider p = declare(vocabulary.findEntity('Tensor'), key, options, null, null, null).provider
        localVariables.put(key, p)
        p
    }

    ModelProvider eye(final int n) {
        List<List<Double>> data = new ArrayList<>()
        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>()
            for (int j = 0; j < n; j++) row.add(i == j ? 1.0d : 0.0d)
            data.add(row)
        }
        matrix(data, [name: "Eye_${n}", matrixType: 'Dense'])
    }

    ModelProvider zeros(final int rows, final int cols) {
        List<List<Double>> data = new ArrayList<>()
        for (int i = 0; i < rows; i++) {
            List<Double> row = new ArrayList<>()
            for (int j = 0; j < cols; j++) row.add(0.0d)
            data.add(row)
        }
        matrix(data, [name: "Zeros_${rows}x${cols}", matrixType: 'Dense'])
    }

    ModelProvider ones(final int rows, final int cols) {
        List<List<Double>> data = new ArrayList<>()
        for (int i = 0; i < rows; i++) {
            List<Double> row = new ArrayList<>()
            for (int j = 0; j < cols; j++) row.add(1.0d)
            data.add(row)
        }
        matrix(data, [name: "Ones_${rows}x${cols}", matrixType: 'Dense'])
    }

    ModelProvider diag(final List<Number> diagonal) {
        int n = diagonal.size()
        List<List<Double>> data = new ArrayList<>()
        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>()
            for (int j = 0; j < n; j++) row.add(i == j ? diagonal.get(i).doubleValue() : 0.0d)
            data.add(row)
        }
        matrix(data, [name: "Diag_${n}", matrixType: 'Diagonal'])
    }

    // Top-level functions
    DecompositionResult svd(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String baseKey = options.get('name')?.toString() ?: "Svd_${System.identityHashCode(operand)}"
        String uKey = "${baseKey}_U"
        String sigmaKey = "${baseKey}_Sigma"
        String vtKey = "${baseKey}_Vt"

        ModelProvider u = matrix([name: uKey, matrixType: 'Dense'])
        ModelProvider sigma = vector([name: sigmaKey])
        ModelProvider vt = matrix([name: vtKey, matrixType: 'Dense'])

        String tKey = "Decomp_${baseKey}"
        mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtSvd',
            name: "SVD Decomposition of ${operand}"
        ])
        mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: (operand instanceof ModelProvider ? ((ModelProvider) operand).name : operand?.toString())
        ])
        mathMeta.declare('moqui.math.MatrixDecomposition', baseKey, [
            decompositionId: baseKey,
            transformationId: tKey,
            decompMethodEnumId: 'TdmSvd',
            uMatrixId: uKey,
            sigmaVectorId: sigmaKey,
            vMatrixId: vtKey,
            rankApproximation: options.get('rankApproximation')
        ])

        new DecompositionResult(u, sigma, vt)
    }

    ModelProvider diagonalExtraction(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String key = options.get('name')?.toString() ?: "DiagExt_${System.identityHashCode(operand)}"
        ModelProvider res = vector([name: key])
        String tKey = "T_${key}"
        mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtDiagonalExtraction',
            name: key,
            resultVectorId: key
        ])
        mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: (operand instanceof ModelProvider ? ((ModelProvider) operand).name : operand?.toString())
        ])
        mathMeta.declare('moqui.math.DiagonalExtraction', key, [
            diagonalExtractionId: key,
            transformationId: tKey,
            axisOffset: options.get('axisOffset') ?: 0
        ])
        res
    }

    ModelProvider triangularExtraction(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String key = options.get('name')?.toString() ?: "TriExt_${System.identityHashCode(operand)}"
        ModelProvider res = matrix([name: key, matrixType: 'Dense'])
        String tKey = "T_${key}"
        mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtTriangularExtraction',
            name: key,
            resultMatrixId: key
        ])
        mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: (operand instanceof ModelProvider ? ((ModelProvider) operand).name : operand?.toString())
        ])
        Object typeVal = options.get('type')
        String tetId = typeVal == 'Lower' || typeVal == TriangularExtractionType.Lower ? 'TetLower' : 'TetUpper'
        mathMeta.declare('moqui.math.TriangularExtraction', key, [
            triangularExtractionId: key,
            transformationId: tKey,
            triangularTypeEnumId: tetId
        ])
        res
    }

    ModelProvider normResult(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String key = options.get('name')?.toString() ?: "Norm_${System.identityHashCode(operand)}"
        ModelProvider res = vector([name: key, dimension: 1])
        String tKey = "T_${key}"
        mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtNorm',
            name: key,
            resultVectorId: key
        ])
        mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: (operand instanceof ModelProvider ? ((ModelProvider) operand).name : operand?.toString())
        ])
        String domainEnumId = vocabulary.resolveSymbolForField(options.get('domain')?.toString(), 'NormDomain')?.id ?: 'NdMatrix'
        String orderEnumId = vocabulary.resolveSymbolForField(options.get('order')?.toString(), 'NormOrder')?.id ?: 'NoMatFrobenius'
        mathMeta.declare('moqui.math.NormResult', key, [
            normResultId: key,
            transformationId: tKey,
            domainEnumId: domainEnumId,
            orderEnumId: orderEnumId,
            normValue: options.get('normValue') ?: 0.0d
        ])
        res
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String entityName, final Object rawArguments) {
        List<Object> arguments = normalizeArguments(rawArguments)
        EntityDefinition entityDefinition = vocabulary.findEntity(entityName)
        ParsedDeclaration parsed = parseArguments(entityName, arguments)
        declare(entityDefinition, parsed.modelKey, parsed.values, parsed.action, null, null).provider
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object propertyMissing(final String name) {
        if (localVariables.containsKey(name)) return localVariables.get(name)
        new DslDeferredSymbol(name)
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    void propertyMissing(final String name, final Object value) {
        localVariables.put(name, value)
    }

    @PackageScope
    DslDeclaration declareNested(final String entityName, final Object rawArguments,
                                 final DslDeclaration parent,
                                 final RelationshipDefinition relationship = null) {
        EntityDefinition entityDefinition = vocabulary.findEntity(entityName)
        ParsedDeclaration parsed = parseArguments(entityName, normalizeArguments(rawArguments))
        declare(entityDefinition, parsed.modelKey, parsed.values, parsed.action, parent, relationship)
    }

    private DslDeclaration declare(final EntityDefinition entityDefinition, final String requestedKey,
                                   final LinkedHashMap<String, Object> values, final Closure<?> action,
                                   final DslDeclaration parent,
                                   final RelationshipDefinition relationship) {
        LinkedHashMap<String, Object> normalizedValues = normalizeFieldAliases(entityDefinition, values)
        if (parent != null) {
            inheritRelationshipKeys(parent, entityDefinition, normalizedValues, relationship)
            inheritAncestorKeys(parent, entityDefinition, normalizedValues)
        }
        String modelKey = requestedKey ?: keyFromValues(entityDefinition, normalizedValues)
        addSinglePrimaryKey(entityDefinition, modelKey, normalizedValues)

        ModelProvider provider = mathMeta.declare(entityDefinition.fullName, modelKey, normalizedValues)
        DslDeclaration record = new DslDeclaration(entityDefinition, modelKey, normalizedValues, provider, parent)
        if (action != null) new DslRecordDelegate(this, record).configure(action)
        record
    }

    private void inheritRelationshipKeys(final DslDeclaration parent, final EntityDefinition child,
                                         final Map<String, Object> childValues,
                                         final RelationshipDefinition requestedRelationship) {
        RelationshipLink link = requestedRelationship != null ?
            linkForRequestedRelationship(parent.definition, child, requestedRelationship) :
            inferRelationship(parent.definition, child)

        link.parentToChildFields.each { String parentField, String childField ->
            Object inherited = parent.values.get(parentField)
            if (inherited == null) {
                throw new IllegalArgumentException(
                    "Cannot nest ${child.fullName} under ${parent.definition.fullName}: " +
                        "parent field ${parentField} has no declared value"
                )
            }
            Object explicit = childValues.get(childField)
            if (explicit != null && explicit != inherited) {
                throw new IllegalArgumentException(
                    "Nested ${child.fullName}.${childField} '${explicit}' conflicts with " +
                        "${parent.definition.fullName}.${parentField} '${inherited}'"
                )
            }
            childValues.put(childField, inherited)
        }
    }

    private static void inheritAncestorKeys(final DslDeclaration parent, final EntityDefinition child,
                                            final Map<String, Object> childValues) {
        Set<String> childPrimaryKeys = child.primaryKeyFields.collect { FieldDefinition field -> field.name } as Set<String>
        DslDeclaration ancestor = parent
        while (ancestor != null) {
            ancestor.definition.primaryKeyFields.each { FieldDefinition field ->
                if (!childPrimaryKeys.contains(field.name) && child.fields.containsKey(field.name) &&
                    !childValues.containsKey(field.name)) {
                    Object inherited = ancestor.values.get(field.name)
                    if (inherited != null) childValues.put(field.name, inherited)
                }
            }
            ancestor = ancestor.parent
        }
    }

    private static RelationshipLink linkForRequestedRelationship(final EntityDefinition parent,
                                                                  final EntityDefinition child,
                                                                  final RelationshipDefinition relationship) {
        if (relationship.relatedEntityName != child.fullName) {
            throw new IllegalArgumentException(
                "Relationship ${parent.fullName}.${relationship.name} expects " +
                    "${relationship.relatedEntityName}, not ${child.fullName}"
            )
        }
        parentRelationshipLink(parent, child, relationship)
    }

    private static RelationshipLink inferRelationship(final EntityDefinition parent,
                                                       final EntityDefinition child) {
        List<RelationshipLink> parentMany = parent.relationships.values()
            .findAll { RelationshipDefinition rel -> rel.type == 'many' && rel.relatedEntityName == child.fullName }
            .collect { RelationshipDefinition rel -> parentRelationshipLink(parent, child, rel) }
        if (parentMany.size() == 1) return parentMany.first()
        if (parentMany.size() > 1) throw ambiguousRelationship(parent, child, parentMany)

        List<RelationshipLink> childToParent = child.relationships.values()
            .findAll { RelationshipDefinition rel -> rel.type == 'one' && rel.relatedEntityName == parent.fullName }
            .collect { RelationshipDefinition rel -> childRelationshipLink(parent, child, rel) }
        if (childToParent.size() == 1) return childToParent.first()
        if (childToParent.size() > 1) throw ambiguousRelationship(parent, child, childToParent)

        List<RelationshipLink> parentOne = parent.relationships.values()
            .findAll { RelationshipDefinition rel -> rel.type == 'one' && rel.relatedEntityName == child.fullName }
            .collect { RelationshipDefinition rel -> parentRelationshipLink(parent, child, rel) }
        if (parentOne.size() == 1) return parentOne.first()
        if (parentOne.size() > 1) throw ambiguousRelationship(parent, child, parentOne)

        List<RelationshipLink> anyRel = parent.relationships.values()
            .findAll { RelationshipDefinition rel -> rel.relatedEntityName == child.fullName }
            .collect { RelationshipDefinition rel -> parentRelationshipLink(parent, child, rel) }
        if (anyRel.size() == 1) return anyRel.first()
        if (anyRel.size() > 1) throw ambiguousRelationship(parent, child, anyRel)

        throw new IllegalArgumentException("No declared relationship connects ${parent.fullName} to ${child.fullName}")
    }

    private static RelationshipLink parentRelationshipLink(final EntityDefinition parent,
                                                           final EntityDefinition child,
                                                           final RelationshipDefinition relationship) {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>()
        if (relationship.keyMap.isEmpty()) {
            String targetPk = child.primaryKeyFields.empty ? null : child.primaryKeyFields.first().name
            if (targetPk != null && parent.fields.containsKey(targetPk)) {
                fields.put(targetPk, targetPk)
            }
        } else {
            relationship.keyMap.each { String parentField, String childField ->
                if (parent.fields.containsKey(parentField) && child.fields.containsKey(childField)) {
                    fields.put(parentField, childField)
                }
            }
        }
        validateRelationshipFields(parent, child, relationship, fields)
        new RelationshipLink(relationship.name, fields)
    }

    private static RelationshipLink childRelationshipLink(final EntityDefinition parent,
                                                          final EntityDefinition child,
                                                          final RelationshipDefinition relationship) {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>()
        if (relationship.keyMap.isEmpty()) {
            String parentPk = parent.primaryKeyFields.empty ? null : parent.primaryKeyFields.first().name
            if (parentPk != null && child.fields.containsKey(parentPk)) {
                fields.put(parentPk, parentPk)
            }
        } else {
            relationship.keyMap.each { String childField, String parentField ->
                if (parent.fields.containsKey(parentField) && child.fields.containsKey(childField)) {
                    fields.put(parentField, childField)
                }
            }
        }
        validateRelationshipFields(parent, child, relationship, fields)
        new RelationshipLink(relationship.name, fields)
    }

    private static void validateRelationshipFields(final EntityDefinition parent, final EntityDefinition child,
                                                   final RelationshipDefinition relationship,
                                                   final Map<String, String> fields) {
        if (fields.isEmpty() && !relationship.keyMap.isEmpty()) {
            throw new IllegalArgumentException(
                "Relationship ${relationship.name} between ${parent.fullName} and ${child.fullName} " +
                    'has no usable key mapping'
            )
        }
    }

    private static IllegalArgumentException ambiguousRelationship(final EntityDefinition parent,
                                                                   final EntityDefinition child,
                                                                   final List<RelationshipLink> links) {
        new IllegalArgumentException(
            "Multiple relationships connect ${parent.fullName} to ${child.fullName}: " +
                "${links*.name.join(', ')}; use a relationship block to disambiguate"
        )
    }

    private static ParsedDeclaration parseArguments(final String entityName, final List<Object> arguments) {
        LinkedHashMap<String, Object> values = new LinkedHashMap<>()
        Closure<?> action
        String modelKey
        arguments.each { Object argument ->
            if (argument instanceof Map) values.putAll((Map<String, Object>) argument)
            else if (argument instanceof Closure) action = (Closure<?>) argument
            else if (argument instanceof CharSequence) modelKey = argument.toString()
            else if (argument instanceof List) values.put('componentArray', argument)
            else throw new IllegalArgumentException(
                "Unsupported argument ${argument?.class?.name} for ${entityName} declaration"
            )
        }
        new ParsedDeclaration(modelKey, values, action)
    }

    private LinkedHashMap<String, Object> copyValues(final Map<String, ?> source) {
        LinkedHashMap<String, Object> values = new LinkedHashMap<>()
        source.each { String name, Object value -> values.put(name, normalizeValue(value)) }
        values
    }

    @PackageScope
    Object normalizeValue(final Object value, final EntityDefinition entity = null, final String fieldName = null) {
        if (value instanceof DslDeferredSymbol) {
            String symName = ((DslDeferredSymbol) value).name
            String enumTypeId = entity != null && fieldName != null ? entity.enumTypeFor(fieldName) : null
            DslSymbol resolved = vocabulary.resolveSymbolForField(symName, enumTypeId, entity, fieldName)
            if (resolved != null) return resolved.id
            return symName
        }
        if (value instanceof DslSymbol) return ((DslSymbol) value).id
        if (value instanceof DslEnumValue) return ((DslEnumValue) value).id
        if (value instanceof Enum<?>) return ((Enum<?>) value).name()
        if (value instanceof org.moqui.math.metamodel.EntityRef) return ((org.moqui.math.metamodel.EntityRef<?>) value).id
        if (value instanceof ModelProvider) return ((ModelProvider) value).name
        if (value instanceof Map) {
            LinkedHashMap<Object, Object> normalized = new LinkedHashMap<>()
            ((Map<?, ?>) value).each { Object key, Object nestedValue -> normalized.put(key, normalizeValue(nestedValue, entity, fieldName)) }
            return normalized
        }
        if (value instanceof Collection) {
            List<Object> normalized = []
            ((Collection<?>) value).each { Object nestedValue -> normalized.add(normalizeValue(nestedValue, entity, fieldName)) }
            return normalized
        }
        if (value instanceof Object[]) {
            List<Object> normalized = []
            ((Object[]) value).each { Object nestedValue -> normalized.add(normalizeValue(nestedValue, entity, fieldName)) }
            return normalized
        }
        value
    }

    @PackageScope
    static String resolveFieldName(final EntityDefinition definition, final String requestedName) {
        if (definition.fields.containsKey(requestedName)) return requestedName
        if (requestedName == 'from' && definition.fields.containsKey('fromVertexId')) return 'fromVertexId'
        if (requestedName == 'to' && definition.fields.containsKey('toVertexId')) return 'toVertexId'
        if (requestedName == 'fromVertex' && definition.fields.containsKey('fromVertexId')) return 'fromVertexId'
        if (requestedName == 'toVertex' && definition.fields.containsKey('toVertexId')) return 'toVertexId'
        if (requestedName == 'source' && definition.fields.containsKey('sourceObjectId')) return 'sourceObjectId'
        if (requestedName == 'target' && definition.fields.containsKey('targetObjectId')) return 'targetObjectId'
        if (requestedName == 'def' && definition.fields.containsKey('parameterDefId')) return 'parameterDefId'
        if (requestedName == 'text' && definition.fields.containsKey('textValue')) return 'textValue'
        if (requestedName == 'symbolic' && definition.fields.containsKey('symbolicValue')) return 'symbolicValue'
        if (requestedName == 'value' && definition.fields.containsKey('numericValue')) return 'numericValue'
        if (requestedName == 'code' && definition.fields.containsKey('parameterCode')) return 'parameterCode'
        if (requestedName == 'alias' && definition.fields.containsKey('modelAlias')) return 'modelAlias'
        if (requestedName == 'alias' && definition.fields.containsKey('parameterAlias')) return 'parameterAlias'
        if (requestedName == 'status' && definition.fields.containsKey('statusId')) return 'statusId'
        if (requestedName == 'type' && definition.fields.containsKey('parameterTypeEnumId')) return 'parameterTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('modelTypeEnumId')) return 'modelTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('transformationTypeEnumId')) return 'transformationTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('matrixTypeEnumId')) return 'matrixTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('vectorTypeEnumId')) return 'vectorTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('tensorTypeEnumId')) return 'tensorTypeEnumId'
        if (requestedName == 'matrixType' && definition.fields.containsKey('matrixTypeEnumId')) return 'matrixTypeEnumId'
        if (requestedName == 'vectorType' && definition.fields.containsKey('vectorTypeEnumId')) return 'vectorTypeEnumId'
        if (requestedName == 'tensorType' && definition.fields.containsKey('tensorTypeEnumId')) return 'tensorTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('meshTypeEnumId')) return 'meshTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('categoryTypeEnumId')) return 'categoryTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('objectTypeEnumId')) return 'objectTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('morphismTypeEnumId')) return 'morphismTypeEnumId'
        if (requestedName == 'usage' && definition.fields.containsKey('usageContextEnumId')) return 'usageContextEnumId'
        if (requestedName == 'purpose' && definition.fields.containsKey('purposeEnumId')) return 'purposeEnumId'
        if (requestedName == 'adaptation' && definition.fields.containsKey('adaptationTypeEnumId')) return 'adaptationTypeEnumId'
        if (requestedName == 'method' && definition.fields.containsKey('solvingMethodEnumId')) return 'solvingMethodEnumId'
        if (requestedName == 'name' && definition.fields.containsKey('parameterName')) return 'parameterName'
        if (requestedName == 'name' && definition.fields.containsKey('categoryName')) return 'categoryName'
        if (requestedName == 'name' && definition.fields.containsKey('objectName')) return 'objectName'
        if (requestedName == 'name' && definition.fields.containsKey('morphismName')) return 'morphismName'
        if (requestedName == 'symbol' && definition.fields.containsKey('objectSymbol')) return 'objectSymbol'
        if (requestedName == 'symbol' && definition.fields.containsKey('morphismSymbol')) return 'morphismSymbol'
        if (requestedName == 'pk' && definition.fields.containsKey('objectPkPrimaryValue')) return 'objectPkPrimaryValue'
        if (requestedName == 'entityName' && definition.fields.containsKey('objectEntityName')) return 'objectEntityName'
        if (requestedName.endsWith('Enum')) {
            String alias = requestedName + 'Id'
            if (definition.fields.containsKey(alias)) return alias
        }
        if (definition.fields.containsKey(requestedName + 'EnumId')) {
            return requestedName + 'EnumId'
        }
        if (definition.fields.containsKey(requestedName + 'Id')) {
            return requestedName + 'Id'
        }
        requestedName
    }

    private LinkedHashMap<String, Object> normalizeFieldAliases(final EntityDefinition definition,
                                                                 final LinkedHashMap<String, Object> source) {
        LinkedHashMap<String, Object> normalized = new LinkedHashMap<>()
        source.each { String name, Object rawValue ->
            String resolved = resolveFieldName(definition, name)
            if (normalized.containsKey(resolved) && resolved != name) {
                throw new IllegalArgumentException(
                    "Duplicate DSL values for ${definition.fullName}.${resolved} via '${name}' alias")
            }
            normalized.put(resolved, normalizeValue(rawValue, definition, resolved))
        }
        inferStructuralProperties(definition, normalized)
        normalized
    }

    private static void inferStructuralProperties(final EntityDefinition definition, final LinkedHashMap<String, Object> normalized) {
        if (definition.fullName == 'moqui.math.Matrix' || definition.name == 'Matrix') {
            if (definition.fields.containsKey('matrixTypeEnumId') && !normalized.containsKey('matrixTypeEnumId')) {
                normalized.put('matrixTypeEnumId', 'MtDense')
            }
            Object comp = normalized.get('componentArray')
            if (comp instanceof List) {
                List<?> list = (List<?>) comp
                if (!list.isEmpty() && list.get(0) instanceof List) {
                    int infRows = list.size()
                    int infCols = ((List<?>) list.get(0)).size()
                    if (normalized.containsKey('rows')) {
                        int decRows = ((Number) normalized.get('rows')).intValue()
                        if (decRows != infRows) {
                            throw new IllegalArgumentException("Declared rows (${decRows}) does not match literal rows (${infRows})")
                        }
                    } else if (definition.fields.containsKey('rows')) {
                        normalized.put('rows', infRows)
                    }
                    if (normalized.containsKey('cols')) {
                        int decCols = ((Number) normalized.get('cols')).intValue()
                        if (decCols != infCols) {
                            throw new IllegalArgumentException("Declared cols (${decCols}) does not match literal cols (${infCols})")
                        }
                    } else if (definition.fields.containsKey('cols')) {
                        normalized.put('cols', infCols)
                    }
                }
                normalized.put('componentArray', groovy.json.JsonOutput.toJson(comp))
            }
            if (definition.fields.containsKey('domainSpaceEnumId') && !normalized.containsKey('domainSpaceEnumId')) {
                if (normalized.containsKey('cols') && ((Number) normalized.get('cols')).intValue() == 3) {
                    normalized.put('domainSpaceEnumId', 'Eng3DEuclideanSpace')
                } else {
                    normalized.put('domainSpaceEnumId', 'Eng2DEuclideanSpace')
                }
            }
            if (definition.fields.containsKey('codomainSpaceEnumId') && !normalized.containsKey('codomainSpaceEnumId')) {
                if (normalized.containsKey('rows') && ((Number) normalized.get('rows')).intValue() == 3) {
                    normalized.put('codomainSpaceEnumId', 'Eng3DEuclideanSpace')
                } else {
                    normalized.put('codomainSpaceEnumId', 'Eng2DEuclideanSpace')
                }
            }
        } else if (definition.fullName == 'moqui.math.Vector' || definition.name == 'Vector') {
            Object comp = normalized.get('componentArray')
            if (comp instanceof List) {
                List<?> list = (List<?>) comp
                int infDim = list.size()
                if (normalized.containsKey('dimension')) {
                    int decDim = ((Number) normalized.get('dimension')).intValue()
                    if (decDim != infDim) {
                        throw new IllegalArgumentException("Declared dimension (${decDim}) does not match literal dimension (${infDim})")
                    }
                } else if (definition.fields.containsKey('dimension')) {
                    normalized.put('dimension', infDim)
                }
                normalized.put('componentArray', groovy.json.JsonOutput.toJson(comp))
            }
        } else if (definition.fullName == 'moqui.math.Tensor' || definition.name == 'Tensor') {
            Object comp = normalized.get('componentArray')
            if (comp instanceof List) {
                normalized.put('componentArray', groovy.json.JsonOutput.toJson(comp))
            }
        }
    }

    private static void addSinglePrimaryKey(final EntityDefinition definition, final String modelKey,
                                             final Map<String, Object> values) {
        List<FieldDefinition> primaryKeys = definition.primaryKeyFields
        if (primaryKeys.size() == 1) {
            String fieldName = primaryKeys.first().name
            Object explicit = values.get(fieldName)
            if (explicit != null && explicit.toString() != modelKey) {
                throw new IllegalArgumentException(
                    "DSL key '${modelKey}' conflicts with ${definition.fullName}.${fieldName} '${explicit}'"
                )
            }
            values.put(fieldName, modelKey)
        }
    }

    private static List<Object> normalizeArguments(final Object rawArguments) {
        if (rawArguments == null) return []
        if (rawArguments instanceof Object[]) return new ArrayList<Object>(Arrays.asList((Object[]) rawArguments))
        if (rawArguments instanceof Collection) return new ArrayList<Object>((Collection<?>) rawArguments)
        [rawArguments]
    }

    private static String keyFromValues(final EntityDefinition definition, final Map<String, Object> values) {
        Object explicitKey = values.remove('_key')
        if (explicitKey != null) return explicitKey.toString()

        List<FieldDefinition> primaryKeys = definition.primaryKeyFields
        if (primaryKeys.empty) {
            throw new IllegalArgumentException("${definition.fullName} requires an explicit DSL _key")
        }
        if (!primaryKeys.every { FieldDefinition field -> values.containsKey(field.name) }) {
            throw new IllegalArgumentException(
                "${definition.fullName} requires a name, _key, or all primary-key fields"
            )
        }
        if (primaryKeys.size() == 1) return values.get(primaryKeys.first().name).toString()
        primaryKeys.collect { FieldDefinition field -> "${field.name}=${values.get(field.name)}" }.join('|')
    }

    @CompileStatic
    private static final class ParsedDeclaration {
        final String modelKey
        final LinkedHashMap<String, Object> values
        final Closure<?> action

        ParsedDeclaration(final String modelKey, final LinkedHashMap<String, Object> values,
                          final Closure<?> action) {
            this.modelKey = modelKey
            this.values = values
            this.action = action
        }
    }

    @CompileStatic
    private static final class RelationshipLink {
        final String name
        final LinkedHashMap<String, String> parentToChildFields

        RelationshipLink(final String name, final LinkedHashMap<String, String> fields) {
            this.name = name
            this.parentToChildFields = fields
        }
    }
}
