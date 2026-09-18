/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.moqui.math.entity.ModelValue
import org.moqui.math.entity.NamedModelContainer

import java.util.regex.Pattern

@CompileStatic
final class CanonicalDump {
    private static final Pattern SYNTHETIC_KEY_PATTERN = Pattern.compile('.*(_Op_|_Data_|anon_|#|\\$[0-9]+).*')

    private CanonicalDump() { }

    static String dump(final MathMeta meta) {
        dumpInternal(meta, false)
    }

    static String dumpStructural(final MathMeta meta) {
        dumpInternal(meta, true)
    }

    static boolean equals(final MathMeta a, final MathMeta b) {
        if (a == b) return true
        if (a == null || b == null) return false
        dump(a) == dump(b)
    }

    static boolean structuralEquals(final MathMeta a, final MathMeta b) {
        if (a == b) return true
        if (a == null || b == null) return false
        dumpStructural(a) == dumpStructural(b)
    }

    static void assertEquals(final MathMeta expected, final MathMeta actual) {
        String expectedDump = dump(expected)
        String actualDump = dump(actual)
        if (expectedDump != actualDump) {
            throw new AssertionError(
                "Canonical representations of MathMeta do not match:\nExpected:\n" + expectedDump + "\nActual:\n" + actualDump
            )
        }
    }

    static void assertStructuralEquals(final MathMeta expected, final MathMeta actual) {
        String expectedDump = dumpStructural(expected)
        String actualDump = dumpStructural(actual)
        if (expectedDump != actualDump) {
            throw new AssertionError(
                "Structural representations of MathMeta do not match:\nExpected:\n" + expectedDump + "\nActual:\n" + actualDump
            )
        }
    }

    private static String dumpInternal(final MathMeta meta, final boolean structural) {
        if (meta == null) return "null"
        StringBuilder sb = new StringBuilder()

        List<String> entityNames = new ArrayList<String>()
        meta.definition.entities.values().each { ed ->
            if (meta.hasEntity(ed.fullName)) {
                NamedModelContainer container = meta.entity(ed.fullName)
                if (container.size() > 0) {
                    entityNames.add(ed.fullName)
                }
            }
        }
        entityNames.sort()

        Map<String, String> syntheticIdMap = new LinkedHashMap<>()
        int syntheticSeq = 1

        for (String entityName : entityNames) {
            NamedModelContainer container = meta.entity(entityName)
            List<ModelValue> records = new ArrayList<ModelValue>()
            container.each { ModelValue v -> records.add(v) }

            records.sort { ModelValue a, ModelValue b ->
                String keyA = stableSortKey(a, structural)
                String keyB = stableSortKey(b, structural)
                keyA <=> keyB
            }

            sb.append("ENTITY ").append(entityName).append(" (").append(records.size()).append(" records):\n")
            for (int i = 0; i < records.size(); i++) {
                ModelValue record = records.get(i)
                String origKey = record.modelKey
                String displayKey = origKey
                if (structural) {
                    displayKey = structuralPathFor(record)
                } else if (isSyntheticKey(origKey)) {
                    displayKey = syntheticIdMap.computeIfAbsent(origKey) { "#${syntheticSeq++}" }
                }

                sb.append("  RECORD [").append(displayKey).append("]:\n")
                List<String> fieldNames = new ArrayList<String>(record.keySet())
                fieldNames.sort()

                for (String field : fieldNames) {
                    // In structural mode, ignore synthetic primary key fields or foreign keys with synthetic IDs
                    if (structural && isPureIdField(field, entityName)) continue
                    if (structural && field == 'name' && (entityName.endsWith('Matrix') || entityName.endsWith('Vector') || entityName.endsWith('Tensor')) &&
                        (record.get('name') == record.modelKey || record.get('name') == record.get('symbol'))) {
                        continue
                    }

                    Object val = record.get(field)
                    if (val == null) continue
                    String formattedVal = formatValue(val, syntheticIdMap, structural)
                    sb.append("    ").append(field).append(": ").append(formattedVal).append("\n")
                }
            }
        }

        sb.toString()
    }

    private static boolean isPureIdField(final String field, final String entityName) {
        if (field == 'mathModelDataId' || field == 'parameterId' || field == 'transformationId') return true
        if (field.endsWith('Id') && !field.endsWith('EnumId') && !field.endsWith('TypeEnumId') && !field.endsWith('UomId')) {
            if (field == 'leftMatrixId' || field == 'rightMatrixId' || field == 'resultMatrixId' ||
                field == 'resultVectorId' || field == 'operandMatrixId' || field == 'operandVectorId') {
                return false
            }
            return true
        }
        false
    }

    private static String structuralPathFor(final ModelValue record) {
        String entity = record.definition.name
        if (entity == 'MathModelDef') {
            return "MathModelDef[${record.get('modelName') ?: record.get('mathModelDefId')}]"
        }
        if (entity == 'MathModel') {
            return "MathModel[${record.get('modelName') ?: record.get('modelAlias') ?: record.get('mathModelId')}]"
        }
        if (entity == 'MathModelData') {
            return "MathModelData[purpose=${record.get('purposeEnumId') ?: record.get('dataTypeEnumId') ?: record.get('sequenceNum')}]"
        }
        if (entity == 'Matrix') {
            return "Matrix[${record.get('name') ?: record.get('symbol') ?: record.get('matrixId')}]"
        }
        if (entity == 'Vector') {
            return "Vector[${record.get('name') ?: record.get('symbol') ?: record.get('vectorId')}]"
        }
        if (entity == 'Tensor') {
            return "Tensor[${record.get('name') ?: record.get('symbol') ?: record.get('tensorId')}]"
        }
        if (entity == 'Parameter') {
            return "Parameter[${record.get('parameterCode') ?: record.get('parameterAlias') ?: record.get('parameterId')}]"
        }
        if (entity == 'Transformation') {
            return "Transformation[${record.get('transformationTypeEnumId') ?: record.get('transformationId')}]"
        }
        if (entity == 'TransformationOperand') {
            return "TransformationOperand[${record.get('operandIndex')}_${record.get('operandTypeEnumId')}]"
        }
        if (entity == 'Category') {
            return "Category[${record.get('categoryName') ?: record.get('categoryId')}]"
        }
        if (entity == 'CategoryObject') {
            return "CategoryObject[${record.get('objectName') ?: record.get('objectSymbol') ?: record.get('categoryObjectId')}]"
        }
        if (entity == 'Morphism') {
            return "Morphism[${record.get('morphismName') ?: record.get('morphismSymbol') ?: record.get('morphismId')}]"
        }
        if (entity == 'Graph') {
            return "Graph[${record.get('graphName') ?: record.get('graphId')}]"
        }
        if (entity == 'GraphVertex') {
            return "GraphVertex[${record.get('label') ?: record.get('vertexName') ?: record.get('vertexId')}]"
        }
        if (entity == 'GraphEdge') {
            return "GraphEdge[${record.get('label') ?: record.get('edgeName') ?: record.get('edgeId')}]"
        }
        "${entity}[${record.modelKey}]"
    }

    private static boolean isSyntheticKey(final String key) {
        if (key == null) return false
        SYNTHETIC_KEY_PATTERN.matcher(key).matches()
    }

    private static String stableSortKey(final ModelValue record, final boolean structural) {
        if (structural) {
            return structuralPathFor(record)
        }
        Object name = record.get('name')
        if (name != null) return "0:" + name.toString()
        Object symbol = record.get('symbol')
        if (symbol != null) return "1:" + symbol.toString()
        Object id = record.get(record.definition.primaryKeyFields.empty ? 'id' : record.definition.primaryKeyFields.first().name)
        if (id != null && !isSyntheticKey(id.toString())) return "2:" + id.toString()

        List<String> fields = new ArrayList<String>(record.keySet())
        fields.sort()
        StringBuilder sb = new StringBuilder("3:")
        for (String f : fields) {
            if (f.endsWith('Id') && isSyntheticKey(String.valueOf(record.get(f)))) continue
            sb.append(f).append('=').append(record.get(f)).append(';')
        }
        sb.toString()
    }

    private static String formatValue(final Object val, final Map<String, String> syntheticIdMap, final boolean structural = false) {
        if (val == null) return "null"
        if (val instanceof CharSequence) {
            String s = val.toString()
            if (structural) {
                // If it's a JSON array string like '[[7,8]]', normalize and format as JSON list
                String trimmed = s.trim()
                if ((trimmed.startsWith('[') && trimmed.endsWith(']')) || (trimmed.startsWith('{') && trimmed.endsWith('}'))) {
                    try {
                        Object parsed = new JsonSlurper().parseText(trimmed)
                        return formatValue(parsed, syntheticIdMap, structural)
                    } catch (Exception ignored) {
                        // Not JSON, continue as string
                    }
                }
            }
            if (!structural && isSyntheticKey(s) && syntheticIdMap.containsKey(s)) {
                return syntheticIdMap.get(s)
            }
            return "\"" + s + "\""
        }
        if (val instanceof Number) {
            if (val instanceof Double) {
                Double d = (Double) val
                if (d.isNaN()) return "NaN"
                if (d.isInfinite()) return d > 0 ? "Infinity" : "-Infinity"
                return BigDecimal.valueOf(d).stripTrailingZeros().toPlainString()
            }
            if (val instanceof Float) {
                Float f = (Float) val
                if (f.isNaN()) return "NaN"
                if (f.isInfinite()) return f > 0 ? "Infinity" : "-Infinity"
                return BigDecimal.valueOf(f.doubleValue()).stripTrailingZeros().toPlainString()
            }
            if (val instanceof BigDecimal) {
                return ((BigDecimal) val).stripTrailingZeros().toPlainString()
            }
            return val.toString()
        }
        if (val instanceof Map) {
            Map<?, ?> m = (Map<?, ?>) val
            List<String> keys = new ArrayList<String>()
            m.keySet().each { k -> keys.add(k.toString()) }
            keys.sort()
            StringBuilder sb = new StringBuilder("{")
            for (int i = 0; i < keys.size(); i++) {
                String k = keys.get(i)
                if (i > 0) sb.append(", ")
                sb.append(k).append(": ").append(formatValue(m.get(k), syntheticIdMap, structural))
            }
            sb.append("}")
            return sb.toString()
        }
        if (val instanceof Collection) {
            Collection<?> c = (Collection<?>) val
            StringBuilder sb = new StringBuilder("[")
            int idx = 0
            for (Object item : c) {
                if (idx++ > 0) sb.append(", ")
                sb.append(formatValue(item, syntheticIdMap, structural))
            }
            sb.append("]")
            return sb.toString()
        }
        if (val instanceof Object[]) {
            return formatValue(Arrays.asList((Object[]) val), syntheticIdMap, structural)
        }
        val.toString()
    }
}
