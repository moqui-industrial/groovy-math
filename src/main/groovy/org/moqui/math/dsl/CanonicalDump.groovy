/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.moqui.math.entity.ModelValue
import org.moqui.math.entity.NamedModelContainer

import java.util.regex.Pattern

@CompileStatic
final class CanonicalDump {
    private static final Pattern SYNTHETIC_KEY_PATTERN = Pattern.compile('.*(_Op_|_Data_|anon_|#|\\$[0-9]+).*')

    private CanonicalDump() { }

    static String dump(final MathMeta meta) {
        if (meta == null) return "null"
        StringBuilder sb = new StringBuilder()

        // 1. Sort entities alphabetically by full name
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

        // Map to normalize synthetic IDs across all entities
        Map<String, String> syntheticIdMap = new LinkedHashMap<>()
        int syntheticSeq = 1

        for (String entityName : entityNames) {
            NamedModelContainer container = meta.entity(entityName)
            List<ModelValue> records = new ArrayList<ModelValue>()
            container.each { ModelValue v -> records.add(v) }

            // Sort records by stable canonical key
            records.sort { ModelValue a, ModelValue b ->
                String keyA = stableSortKey(a)
                String keyB = stableSortKey(b)
                keyA <=> keyB
            }

            sb.append("ENTITY ").append(entityName).append(" (").append(records.size()).append(" records):\n")
            for (int i = 0; i < records.size(); i++) {
                ModelValue record = records.get(i)
                String origKey = record.modelKey
                String displayKey = origKey
                if (isSyntheticKey(origKey)) {
                    displayKey = syntheticIdMap.computeIfAbsent(origKey) { "#${syntheticSeq++}" }
                }

                sb.append("  RECORD [").append(displayKey).append("]:\n")
                List<String> fieldNames = new ArrayList<String>(record.keySet())
                fieldNames.sort()

                for (String field : fieldNames) {
                    Object val = record.get(field)
                    if (val == null) continue
                    String formattedVal = formatValue(val, syntheticIdMap)
                    sb.append("    ").append(field).append(": ").append(formattedVal).append("\n")
                }
            }
        }

        sb.toString()
    }

    static boolean equals(final MathMeta a, final MathMeta b) {
        if (a == b) return true
        if (a == null || b == null) return false
        dump(a) == dump(b)
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

    private static boolean isSyntheticKey(final String key) {
        if (key == null) return false
        SYNTHETIC_KEY_PATTERN.matcher(key).matches()
    }

    private static String stableSortKey(final ModelValue record) {
        // Priority to 'name', then 'symbol', then primary keys, then sorted fields
        Object name = record.get('name')
        if (name != null) return "0:" + name.toString()
        Object symbol = record.get('symbol')
        if (symbol != null) return "1:" + symbol.toString()
        Object id = record.get(record.definition.primaryKeyFields.empty ? 'id' : record.definition.primaryKeyFields.first().name)
        if (id != null && !isSyntheticKey(id.toString())) return "2:" + id.toString()

        // Fallback to sorted field content
        List<String> fields = new ArrayList<String>(record.keySet())
        fields.sort()
        StringBuilder sb = new StringBuilder("3:")
        for (String f : fields) {
            if (f.endsWith('Id') && isSyntheticKey(String.valueOf(record.get(f)))) continue
            sb.append(f).append('=').append(record.get(f)).append(';')
        }
        sb.toString()
    }

    private static String formatValue(final Object val, final Map<String, String> syntheticIdMap) {
        if (val == null) return "null"
        if (val instanceof String) {
            String s = (String) val
            if (isSyntheticKey(s) && syntheticIdMap.containsKey(s)) {
                return syntheticIdMap.get(s)
            }
            return "\"" + s + "\""
        }
        if (val instanceof Number) {
            if (val instanceof Float || val instanceof Double) {
                return String.format(Locale.US, "%.6f", ((Number) val).doubleValue())
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
                sb.append(k).append(": ").append(formatValue(m.get(k), syntheticIdMap))
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
                sb.append(formatValue(item, syntheticIdMap))
            }
            sb.append("]")
            return sb.toString()
        }
        if (val instanceof Object[]) {
            return formatValue(Arrays.asList((Object[]) val), syntheticIdMap)
        }
        val.toString()
    }
}
