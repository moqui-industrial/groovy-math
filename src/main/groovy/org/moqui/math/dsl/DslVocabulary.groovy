/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.EnumerationDefinition
import org.moqui.math.entity.FieldDefinition
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.entity.RelationshipDefinition
import org.moqui.math.entity.StatusDefinition
import org.moqui.math.moqui.MoquiSchemaInspector

import java.util.concurrent.ConcurrentHashMap

@CompileStatic
final class DslVocabulary {
    private static final Map<ModelDefinition, DslVocabulary> CACHE = new ConcurrentHashMap<>()

    final ModelDefinition modelDefinition
    final Map<String, EntityDefinition> entityKeywords = new LinkedHashMap<>()
    final Set<String> transformationEntities = new LinkedHashSet<>()
    final Map<String, String> operandTypeMap = new LinkedHashMap<>()
    final Map<String, DslSymbol> symbols = new LinkedHashMap<>()
    final Map<String, List<DslSymbol>> ambiguousSymbols = new LinkedHashMap<>()
    final Map<String, DslSymbol> enumIdToPreferredSymbol = new LinkedHashMap<>()
    final Map<String, Map<String, DslSymbol>> symbolsByEnumType = new LinkedHashMap<>()

    static DslVocabulary of(final ModelDefinition modelDefinition) {
        Objects.requireNonNull(modelDefinition, 'ModelDefinition must not be null')
        CACHE.computeIfAbsent(modelDefinition) { ModelDefinition defn -> new DslVocabulary(defn) }
    }

    private DslVocabulary(final ModelDefinition modelDefinition) {
        this.modelDefinition = modelDefinition
        indexEntities()
        indexTransformations()
        indexOperandTypes()
        indexSymbols()
    }

    private void indexEntities() {
        modelDefinition.entities.values().each { EntityDefinition ed ->
            entityKeywords.put(ed.fullName, ed)
            entityKeywords.put(ed.name, ed)
            entityKeywords.put(uncapitalize(ed.name), ed)

            if (ed.name.startsWith('Math') && ed.name.length() > 4) {
                String stripped = ed.name.substring(4)
                entityKeywords.putIfAbsent(stripped, ed)
                entityKeywords.putIfAbsent(uncapitalize(stripped), ed)
            }
            if (ed.name.startsWith('Graph') && ed.name.length() > 5) {
                String stripped = ed.name.substring(5)
                entityKeywords.putIfAbsent(stripped, ed)
                entityKeywords.putIfAbsent(uncapitalize(stripped), ed)
            }
            if (ed.shortAlias) {
                entityKeywords.putIfAbsent(ed.shortAlias, ed)
                entityKeywords.putIfAbsent(uncapitalize(ed.shortAlias), ed)
            }
        }

        if (modelDefinition.hasEntity('moqui.math.ct.CategoryObject')) {
            EntityDefinition ed = modelDefinition.entity('moqui.math.ct.CategoryObject')
            entityKeywords.put('object', ed)
            entityKeywords.put('Object', ed)
        }
        if (modelDefinition.hasEntity('moqui.math.GraphVertex')) {
            EntityDefinition ed = modelDefinition.entity('moqui.math.GraphVertex')
            entityKeywords.put('vertex', ed)
            entityKeywords.put('Vertex', ed)
        }
        if (modelDefinition.hasEntity('moqui.math.GraphEdge')) {
            EntityDefinition ed = modelDefinition.entity('moqui.math.GraphEdge')
            entityKeywords.put('edge', ed)
            entityKeywords.put('Edge', ed)
        }
    }

    private void indexTransformations() {
        modelDefinition.entities.values().each { EntityDefinition ed ->
            if (ed.fullName == 'moqui.math.Transformation' || ed.fullName.startsWith('moqui.math.sat.')) {
                transformationEntities.add(ed.fullName)
            } else if (ed.fields.containsKey('transformationId') &&
                       ed.relationships.values().any { it.relatedEntityName == 'moqui.math.Transformation' }) {
                transformationEntities.add(ed.fullName)
            }
        }
    }

    private void indexOperandTypes() {
        List<EnumerationDefinition> enums = modelDefinition.enumerationsOfType('TransformationOperandType')
        enums.each { EnumerationDefinition ed ->
            String id = ed.enumId
            if (id.startsWith('Tot')) {
                String rest = id.substring(3)
                operandTypeMap.put(uncapitalize(rest), id)
                operandTypeMap.put("operand${rest}".toString(), id)
                if (rest == 'Left') operandTypeMap.put('leftTransformation', id)
                if (rest == 'Right') operandTypeMap.put('rightTransformation', id)
            }
        }
    }

    boolean isDeclaredEntity(final String entityName) {
        if (!entityName) return false
        modelDefinition.hasEntity(entityName)
    }

    boolean isDeclaredField(final String entityName, final String fieldName) {
        if (!entityName || !fieldName) return false
        EntityDefinition ed = entityKeywords.get(entityName)
        if (ed == null && modelDefinition.hasEntity(entityName)) {
            ed = modelDefinition.entity(entityName)
        }
        ed != null && ed.fields.containsKey(fieldName)
    }

    boolean isDeclaredRelationship(final String entityName, final String relName) {
        if (!entityName || !relName) return false
        EntityDefinition ed = entityKeywords.get(entityName)
        if (ed == null && modelDefinition.hasEntity(entityName)) {
            ed = modelDefinition.entity(entityName)
        }
        if (ed == null) return false
        ed.relationships.containsKey(relName) ||
            ed.relationships.values().any { it.name == relName || it.relatedEntityName.endsWith('.' + relName) }
    }

    boolean isDeclaredEnum(final String enumId) {
        if (!enumId) return false
        modelDefinition.enumeration(enumId) != null
    }

    boolean isDeclaredEnumType(final String enumTypeId) {
        if (!enumTypeId) return false
        modelDefinition.isDeclaredEnumerationType(enumTypeId)
    }

    boolean isDeclaredStatus(final String statusId) {
        if (!statusId) return false
        modelDefinition.statuses.containsKey(statusId)
    }

    Set<String> allEntityNames() {
        modelDefinition.entities.keySet()
    }

    Set<String> allEnumTypes() {
        modelDefinition.enumerationTypes
    }

    Set<String> allEnumValues() {
        modelDefinition.enumerations.keySet()
    }

    List<EnumerationDefinition> enumValuesForType(final String enumTypeId) {
        if (!enumTypeId) return Collections.emptyList()
        modelDefinition.enumerationsOfType(enumTypeId)
    }

    private void indexSymbols() {
        // 1. Registered DSL enums implementing DslEnumValue from generated classes
        for (String typeId : modelDefinition.enumerationTypes) {
            try {
                Class<?> cls = Class.forName("org.moqui.math.dsl.${typeId}")
                if (Enum.isAssignableFrom(cls) && DslEnumValue.isAssignableFrom(cls)) {
                    for (Object constant : cls.enumConstants) {
                        Enum<?> e = (Enum<?>) constant
                        if (e instanceof DslEnumValue) {
                            String id = ((DslEnumValue) e).id
                            addSymbol(e.name(), id, typeId)
                            addSymbol(uncapitalize(e.name()), id, typeId)
                            enumIdToPreferredSymbol.putIfAbsent(id, new DslSymbol(e.name(), id, typeId))
                        }
                    }
                }
            } catch (ClassNotFoundException ignored) {
            }
        }

        // Compatibility wrappers
        for (String aliasTypeName : ['DataType', 'DeviceType', 'MathSpace']) {
            try {
                Class<?> cls = Class.forName("org.moqui.math.dsl.${aliasTypeName}")
                if (Enum.isAssignableFrom(cls) && DslEnumValue.isAssignableFrom(cls)) {
                    for (Object constant : cls.enumConstants) {
                        Enum<?> e = (Enum<?>) constant
                        if (e instanceof DslEnumValue) {
                            String id = ((DslEnumValue) e).id
                            addSymbol(e.name(), id, aliasTypeName)
                            addSymbol(uncapitalize(e.name()), id, aliasTypeName)
                            enumIdToPreferredSymbol.putIfAbsent(id, new DslSymbol(e.name(), id, aliasTypeName))
                        }
                    }
                }
            } catch (ClassNotFoundException ignored) {
            }
        }

        for (Object constant : OptimizationObjectiveSense.enumConstants) {
            Enum<?> e = (Enum<?>) constant
            if (e instanceof DslEnumValue) {
                String id = ((DslEnumValue) e).id
                addSymbol(e.name(), id, 'OptimizationObjectiveSense')
                addSymbol(uncapitalize(e.name()), id, 'OptimizationObjectiveSense')
                enumIdToPreferredSymbol.putIfAbsent(id, new DslSymbol(e.name(), id, 'OptimizationObjectiveSense'))
            }
        }

        // 2. Group all schema enumerations by enumTypeId
        Map<String, List<EnumerationDefinition>> byType = new LinkedHashMap<>()
        for (EnumerationDefinition ed : modelDefinition.enumerations.values()) {
            String type = ed.enumTypeId ?: 'Enumeration'
            byType.computeIfAbsent(type) { new ArrayList<EnumerationDefinition>() }.add(ed)
        }

        byType.each { String type, List<EnumerationDefinition> list ->
            String lcp = computeLongestCommonPrefix(list.collect { it.enumId })

            // Check if stripped prefix produces unique names within this enumTypeId
            Map<String, Integer> strippedCounts = new LinkedHashMap<>()
            list.each { EnumerationDefinition ed ->
                String id = ed.enumId
                if (lcp.length() > 0 && id.startsWith(lcp) && id.length() > lcp.length()) {
                    String stripped = id.substring(lcp.length())
                    strippedCounts.put(stripped, (strippedCounts.get(stripped) ?: 0) + 1)
                }
                if (id.startsWith(type) && id.length() > type.length()) {
                    String stripped = id.substring(type.length())
                    strippedCounts.put(stripped, (strippedCounts.get(stripped) ?: 0) + 1)
                }
            }

            list.each { EnumerationDefinition ed ->
                String id = ed.enumId

                // Determine preferred symbol:
                // 1. Groovy enum constant if exists
                // 2. enumCode if exists
                // 3. stripped unique prefix
                // 4. full enumId
                String preferred = null
                if (enumIdToPreferredSymbol.containsKey(id)) {
                    preferred = enumIdToPreferredSymbol.get(id).name
                } else if (ed.enumCode && isCleanIdentifier(ed.enumCode)) {
                    preferred = ed.enumCode
                } else if (lcp.length() > 0 && id.startsWith(lcp) && id.length() > lcp.length()) {
                    String stripped = id.substring(lcp.length())
                    if (strippedCounts.get(stripped) == 1 && isCleanIdentifier(stripped)) {
                        preferred = stripped
                    }
                }
                if (preferred == null) preferred = id
                enumIdToPreferredSymbol.putIfAbsent(id, new DslSymbol(preferred, id, type))

                // Register all accepted forms in order:
                // 1. Full enumId
                addSymbol(id, id, type)
                addSymbol(uncapitalize(id), id, type)

                // 2. enumCode
                if (ed.enumCode) {
                    addSymbol(ed.enumCode, id, type)
                    addSymbol(uncapitalize(ed.enumCode), id, type)
                }

                // 3. Stripped unique prefix
                if (lcp.length() > 0 && id.startsWith(lcp) && id.length() > lcp.length()) {
                    String stripped = id.substring(lcp.length())
                    if (strippedCounts.get(stripped) == 1) {
                        addSymbol(stripped, id, type)
                        addSymbol(uncapitalize(stripped), id, type)
                    }
                }
                if (id.startsWith(type) && id.length() > type.length()) {
                    String stripped = id.substring(type.length())
                    if (strippedCounts.get(stripped) == 1) {
                        addSymbol(stripped, id, type)
                        addSymbol(uncapitalize(stripped), id, type)
                    }
                }

                // 4. Normalized CamelCase description
                if (ed.description) {
                    String cleanDesc = ed.description.replaceAll(/\(.*?\)/, '').trim()
                    String descCamel = toCamelCase(cleanDesc)
                    if (descCamel && isCleanIdentifier(descCamel)) {
                        addSymbol(descCamel, id, type)
                        addSymbol(uncapitalize(descCamel), id, type)
                    }
                }
            }
        }

        // Common domain aliases & synonyms
        addSymbol('minimise', 'MINIMIZE', 'OptimizationObjectiveSense')
        addSymbol('minimize', 'MINIMIZE', 'OptimizationObjectiveSense')
        addSymbol('maximise', 'MAXIMIZE', 'OptimizationObjectiveSense')
        addSymbol('maximize', 'MAXIMIZE', 'OptimizationObjectiveSense')

        // Status items (MathModelStatus, etc.)
        for (StatusDefinition sd : modelDefinition.statuses.values()) {
            String type = sd.statusTypeId ?: 'Status'
            String id = sd.statusId
            addSymbol(id, id, type)
            addSymbol(uncapitalize(id), id, type)
            addSymbol(id, id, 'Status')
            addSymbol(uncapitalize(id), id, 'Status')
            if (sd.statusCode) {
                addSymbol(sd.statusCode, id, type)
                addSymbol(uncapitalize(sd.statusCode), id, type)
                addSymbol(sd.statusCode, id, 'Status')
                addSymbol(uncapitalize(sd.statusCode), id, 'Status')
            }
            if (id.startsWith('MathModel') && id.length() > 9) {
                String rest = id.substring(9)
                addSymbol(rest, id, type)
                addSymbol(uncapitalize(rest), id, type)
                addSymbol(rest, id, 'Status')
                addSymbol(uncapitalize(rest), id, 'Status')
            }
        }
    }

    private static String computeLongestCommonPrefix(final List<String> strings) {
        if (!strings || strings.isEmpty()) return ''
        if (strings.size() == 1) {
            String single = strings[0]
            for (int i = 1; i < single.length(); i++) {
                if (Character.isUpperCase(single.charAt(i))) {
                    return single.substring(0, i)
                }
            }
            return ''
        }
        String prefix = strings[0]
        for (int i = 1; i < strings.size(); i++) {
            String current = strings[i]
            int j = 0
            while (j < prefix.length() && j < current.length() && prefix.charAt(j) == current.charAt(j)) {
                j++
            }
            prefix = prefix.substring(0, j)
            if (prefix.isEmpty()) break
        }
        prefix
    }

    private static String toCamelCase(final String s) {
        if (!s) return s
        String[] parts = s.split(/[^a-zA-Z0-9]+/)
        StringBuilder sb = new StringBuilder()
        for (String part : parts) {
            if (part.length() > 0) {
                sb.append(part.substring(0, 1).toUpperCase()).append(part.substring(1))
            }
        }
        sb.toString()
    }

    private static boolean isCleanIdentifier(final String s) {
        if (!s || s.isEmpty()) return false
        if (!Character.isJavaIdentifierStart(s.charAt(0))) return false
        for (int i = 1; i < s.length(); i++) {
            if (!Character.isJavaIdentifierPart(s.charAt(i))) return false
        }
        true
    }

    private void addSymbol(final String name, final String id, final String type) {
        if (!name || !id) return
        DslSymbol sym = new DslSymbol(name, id, type)
        symbolsByEnumType.computeIfAbsent(type) { new LinkedHashMap<String, DslSymbol>() }.put(name, sym)

        if (symbols.containsKey(name)) {
            DslSymbol existing = symbols.get(name)
            if (existing.id == id) return
            List<DslSymbol> list = ambiguousSymbols.computeIfAbsent(name) { new ArrayList<DslSymbol>() }
            if (!list.contains(existing)) list.add(existing)
            if (!list.contains(sym)) list.add(sym)
            symbols.remove(name)
            return
        }
        if (ambiguousSymbols.containsKey(name)) {
            List<DslSymbol> list = ambiguousSymbols.get(name)
            if (!list.contains(sym)) list.add(sym)
            return
        }
        symbols.put(name, sym)
    }

    DslSymbol resolveSymbol(final String name, final String enumTypeId = null) {
        if (enumTypeId != null && symbolsByEnumType.containsKey(enumTypeId)) {
            DslSymbol typeSym = symbolsByEnumType.get(enumTypeId).get(name)
            if (typeSym != null) return typeSym
            DslSymbol uncap = symbolsByEnumType.get(enumTypeId).get(uncapitalize(name))
            if (uncap != null) return uncap
        }
        if (ambiguousSymbols.containsKey(name)) {
            if (enumTypeId != null) {
                List<DslSymbol> matches = ambiguousSymbols.get(name).findAll { it.enumTypeId == enumTypeId }
                if (matches.size() == 1) return matches.first()
            }
            List<DslSymbol> conflicts = ambiguousSymbols.get(name)
            String details = conflicts.collect { "${it.enumTypeId}.${it.name} (${it.id})" }.join(', ')
            throw new IllegalArgumentException("Ambiguous DSL symbol '${name}' matches multiple enumeration types: ${details}")
        }
        symbols.get(name)
    }

    DslSymbol resolveSymbolForField(final String symbolName, final String enumTypeId,
                                   final EntityDefinition entity = null, final String fieldName = null) {
        if (!symbolName) return null
        String targetType = enumTypeId
        if (targetType == null && entity != null && fieldName != null) {
            targetType = entity.enumTypeFor(fieldName)
            if (targetType == null && (fieldName == 'statusId' || fieldName.endsWith('StatusId') || fieldName == 'status')) {
                targetType = 'MathModelStatus'
                if (!symbolsByEnumType.containsKey(targetType)) targetType = 'Status'
            }
            if (targetType == null && (fieldName == 'symbolicValue' || fieldName == 'symbolic')) {
                targetType = 'OptimizationObjectiveSense'
            }
        }
        if (targetType == 'DomainVectorSpace' || targetType == 'CodomainVectorSpace') {
            targetType = 'MathSpace'
        }
        if (targetType == 'ObjectiveSense') {
            targetType = 'OptimizationObjectiveSense'
        }

        Map<String, DslSymbol> typeSymbols = targetType != null ? symbolsByEnumType.get(targetType) : null
        if (typeSymbols != null) {
            if (typeSymbols.containsKey(symbolName)) return typeSymbols.get(symbolName)
            if (typeSymbols.containsKey(uncapitalize(symbolName))) return typeSymbols.get(uncapitalize(symbolName))
            if (typeSymbols.containsKey(symbolName.toUpperCase())) return typeSymbols.get(symbolName.toUpperCase())

            // Try matching enumId directly
            for (DslSymbol sym : typeSymbols.values()) {
                if (sym.id == symbolName) return sym
            }

            // Try case-insensitive matching
            for (Map.Entry<String, DslSymbol> entry : typeSymbols.entrySet()) {
                if (entry.key.equalsIgnoreCase(symbolName)) return entry.value
            }

            // Not found in expected target domain -> compute edit distance suggestion
            Set<String> allowed = typeSymbols.keySet()
            String suggestion = findClosestSymbol(symbolName, allowed)
            String didYouMean = suggestion ? " Did you mean '${suggestion}'?" : ""
            throw new IllegalArgumentException(
                "Invalid symbol '${symbolName}' for field '${fieldName ?: 'unknown'}' " +
                "on entity '${entity?.name ?: 'unknown'}' (expected domain '${targetType}'). " +
                "Allowed values: ${allowed.take(15).join(', ')}${allowed.size() > 15 ? '...' : ''}.${didYouMean}"
            )
        }

        if (entity != null && fieldName != null) {
            throw new IllegalArgumentException(
                "Field '${fieldName}' on entity '${entity.name}' has no known enumeration domain; " +
                "cannot resolve bare symbol '${symbolName}'"
            )
        }

        // Global fallback when neither targetType nor entity/fieldName were provided
        DslSymbol global = resolveSymbol(symbolName)
        if (global != null) return global

        throw new IllegalArgumentException("Unresolved bare symbol '${symbolName}'")
    }

    static int editDistance(final String s1, final String s2) {
        if (s1 == null) return s2 == null ? 0 : s2.length()
        if (s2 == null) return s1.length()
        String a = s1.toLowerCase()
        String b = s2.toLowerCase()
        int[] costs = new int[b.length() + 1]
        for (int j = 0; j <= b.length(); j++) costs[j] = j
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i
            int nw = i - 1
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                        a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1)
                nw = costs[j]
                costs[j] = cj
            }
        }
        costs[b.length()]
    }

    static String findClosestSymbol(final String name, final Collection<String> candidates) {
        if (!candidates || !name) return null
        String best = null
        int bestDist = Integer.MAX_VALUE
        for (String cand : candidates) {
            int dist = editDistance(name, cand)
            if (dist < bestDist) {
                bestDist = dist
                best = cand
            }
        }
        if (best != null && bestDist <= Math.max(3, best.length() / 2)) {
            return best
        }
        null
    }

    String preferredSymbolForId(final String enumId) {
        if (!enumId) return null
        DslSymbol pref = enumIdToPreferredSymbol.get(enumId)
        if (pref != null) {
            if (ambiguousSymbols.containsKey(pref.name)) {
                return "${pref.enumTypeId}.${pref.name}".toString()
            }
            return pref.name
        }
        null
    }

    boolean hasEntity(final String name) {
        entityKeywords.containsKey(name) || modelDefinition.hasEntity(name)
    }

    EntityDefinition findEntity(final String name) {
        EntityDefinition ed = entityKeywords.get(name)
        if (ed != null) return ed
        modelDefinition.entity(name)
    }

    boolean isTransformationEntity(final String entityFullName) {
        transformationEntities.contains(entityFullName)
    }

    String getOperandTypeEnumId(final String methodName) {
        operandTypeMap.get(methodName)
    }

    String generateMarkdownSnapshot() {
        StringBuilder sb = new StringBuilder()
        sb.append('# DSL Vocabulary Reference Snapshot\n\n')
        sb.append('This document is automatically generated from the schema-derived `DslVocabulary`. Do not edit manually.\n\n')

        sb.append('## 1. Entity Keywords\n\n')
        sb.append('| Keyword | Entity Full Name | Short Alias |\n')
        sb.append('| :--- | :--- | :--- |\n')
        Map<String, EntityDefinition> sortedEntities = new TreeMap<>(entityKeywords)
        sortedEntities.each { String kw, EntityDefinition ed ->
            sb.append("| `${kw}` | `${ed.fullName}` | `${ed.shortAlias ?: '-'}` |\n")
        }

        sb.append('\n## 2. Transformation Entities & Derived Functions\n\n')
        sb.append('| Transformation Entity | Derived LowerCamel Function |\n')
        sb.append('| :--- | :--- |\n')
        transformationEntities.toSorted().each { String te ->
            String name = te.tokenize('.').last()
            sb.append("| `${te}` | `${uncapitalize(name)}` |\n")
        }

        sb.append('\n## 3. Enumeration Symbols by Type\n\n')
        Map<String, Map<String, DslSymbol>> sortedByDomain = new TreeMap<>(symbolsByEnumType)
        sortedByDomain.each { String domain, Map<String, DslSymbol> syms ->
            sb.append("### Domain: `${domain}`\n\n")
            sb.append('| Preferred Symbol | Canonical Enum ID | Accepted Aliases |\n')
            sb.append('| :--- | :--- | :--- |\n')
            Map<String, List<String>> idToAliases = new TreeMap<>()
            syms.each { String symName, DslSymbol sym ->
                idToAliases.computeIfAbsent(sym.id) { new ArrayList<String>() }.add(symName)
            }
            idToAliases.each { String eid, List<String> aliases ->
                String pref = enumIdToPreferredSymbol.get(eid)?.name ?: aliases.first()
                List<String> otherAliases = aliases.findAll { it != pref }
                String aliasStr = otherAliases.collect { "`" + it + "`" }.join(', ') ?: '-'
                sb.append("| `${pref}` | `${eid}` | ${aliasStr} |\n")
            }
            sb.append('\n')
        }

        sb.toString()
    }

    static void main(String[] args) {
        DslVocabulary vocab = of(MoquiSchemaInspector.embedded())
        File target = new File('docs/dsl/vocabulary.md')
        target.parentFile.mkdirs()
        target.text = vocab.generateMarkdownSnapshot()
        println "Updated ${target.path}"
    }

    private static String uncapitalize(final String s) {
        if (!s || s.length() == 0) return s
        if (s.length() == 1) return s.toLowerCase()
        Character.toLowerCase(s.charAt(0)).toString() + s.substring(1)
    }
}
