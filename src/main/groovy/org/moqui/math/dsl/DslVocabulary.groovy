/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.EnumerationDefinition
import org.moqui.math.entity.ModelDefinition

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
    }

    private void indexTransformations() {
        modelDefinition.entities.values().each { EntityDefinition ed ->
            if (ed.fullName == 'moqui.math.Transformation') {
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

    final Map<String, Map<String, DslSymbol>> symbolsByEnumType = new LinkedHashMap<>()

    private void indexSymbols() {
        // 1. Registered DSL enums implementing DslEnumValue
        List<Class<?>> dslEnums = [
            MathModelType, DataType, OptimizationObjectiveSense, MathModelSolvingMethod,
            DeviceType, MatrixType, MatrixPurpose, ParameterPurpose, ParameterType,
            TensorPurpose, TransformationType, MeshType, MorphismType, NormOrder,
            MathSpace, MathModelUsageContext, MathModelSource, MathModelDataType,
            MathModelDataPurpose, CategoryType, CategoryObjectType, MeshAdaptationType,
            MeshPurpose, TensorDecompMethod, TransformationPurpose, TriangularExtractionType
        ]

        for (Class<?> cls : dslEnums) {
            if (Enum.isAssignableFrom(cls)) {
                for (Object constant : cls.enumConstants) {
                    Enum<?> e = (Enum<?>) constant
                    if (e instanceof DslEnumValue) {
                        String id = ((DslEnumValue) e).id
                        addSymbol(e.name(), id, cls.simpleName)
                        addSymbol(uncapitalize(e.name()), id, cls.simpleName)
                        enumIdToPreferredSymbol.putIfAbsent(id, new DslSymbol(e.name(), id, cls.simpleName))
                    }
                }
            }
        }

        // 2. Group all schema enumerations by enumTypeId
        Map<String, List<EnumerationDefinition>> byType = new LinkedHashMap<>()
        for (EnumerationDefinition ed : modelDefinition.enumerations.values()) {
            String type = ed.enumTypeId ?: 'Enumeration'
            byType.computeIfAbsent(type) { new ArrayList<EnumerationDefinition>() }.add(ed)
        }

        byType.each { String type, List<EnumerationDefinition> list ->
            // Compute Longest Common Prefix (LCP) dynamically among all enumIds for this enumTypeId
            String lcp = computeLongestCommonPrefix(list.collect { it.enumId })

            list.each { EnumerationDefinition ed ->
                String id = ed.enumId

                // 1. Direct ID
                addSymbol(id, id, type)

                // 2. enumCode if available
                if (ed.enumCode) {
                    addSymbol(ed.enumCode, id, type)
                    addSymbol(uncapitalize(ed.enumCode), id, type)
                }

                // 3. Normalized CamelCase description
                String descCamel = null
                if (ed.description) {
                    String cleanDesc = ed.description.replaceAll(/\(.*?\)/, '').trim()
                    descCamel = toCamelCase(cleanDesc)
                    if (descCamel && descCamel != id && descCamel != ed.enumCode) {
                        addSymbol(descCamel, id, type)
                        addSymbol(uncapitalize(descCamel), id, type)
                    }
                    String fullDescCamel = toCamelCase(ed.description)
                    if (fullDescCamel && fullDescCamel != descCamel && fullDescCamel != id && fullDescCamel != ed.enumCode) {
                        addSymbol(fullDescCamel, id, type)
                        addSymbol(uncapitalize(fullDescCamel), id, type)
                    }
                }

                // 4. Stripped LCP prefix
                if (lcp.length() > 0 && id.startsWith(lcp) && id.length() > lcp.length()) {
                    String stripped = id.substring(lcp.length())
                    addSymbol(stripped, id, type)
                    addSymbol(uncapitalize(stripped), id, type)
                }

                // 5. Stripped enumTypeId prefix if enumId starts with it
                if (id.startsWith(type) && id.length() > type.length()) {
                    String stripped = id.substring(type.length())
                    addSymbol(stripped, id, type)
                    addSymbol(uncapitalize(stripped), id, type)
                }

                // Determine preferred symbol for this enumId
                String preferred = null
                if (descCamel != null && !descCamel.contains(' ') && isCleanIdentifier(descCamel)) {
                    preferred = descCamel
                } else if (lcp.length() > 0 && id.startsWith(lcp) && id.length() > lcp.length()) {
                    preferred = id.substring(lcp.length())
                } else if (ed.enumCode) {
                    preferred = ed.enumCode
                } else {
                    preferred = id
                }
                enumIdToPreferredSymbol.putIfAbsent(id, new DslSymbol(preferred, id, type))
            }
        }

        // ObjectiveSense common synonyms derived from schema
        addSymbol('minimise', 'MINIMIZE', 'OptimizationObjectiveSense')
        addSymbol('minimize', 'MINIMIZE', 'OptimizationObjectiveSense')
        addSymbol('maximise', 'MAXIMIZE', 'OptimizationObjectiveSense')
        addSymbol('maximize', 'MAXIMIZE', 'OptimizationObjectiveSense')
    }

    private static String computeLongestCommonPrefix(final List<String> strings) {
        if (!strings || strings.isEmpty()) return ''
        if (strings.size() == 1) {
            String single = strings[0]
            // Extract leading uppercase + lowercase prefix before the next capital letter (e.g. DevCpu -> Dev, DtFloat32 -> Dt)
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
            if (existing.id == id) {
                // Same canonical ID, compatible duplicate
                return
            }
            // Distinct target IDs -> ambiguous symbol
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

    String preferredSymbolForId(final String enumId) {
        if (!enumId) return null
        DslSymbol pref = enumIdToPreferredSymbol.get(enumId)
        if (pref != null) {
            if (ambiguousSymbols.containsKey(pref.name)) {
                return "${pref.enumTypeId}.${pref.name}".toString()
            }
            return pref.name
        }
        enumId
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

    private static String uncapitalize(final String s) {
        if (!s || s.length() == 0) return s
        if (s.length() == 1) return s.toLowerCase()
        Character.toLowerCase(s.charAt(0)).toString() + s.substring(1)
    }
}
