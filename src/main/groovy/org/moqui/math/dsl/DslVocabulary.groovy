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

    private void indexSymbols() {
        // 1. Registered DSL enums
        List<Class<?>> dslEnums = [
            MathModelType, DataType, OptimizationObjectiveSense, MathModelSolvingMethod,
            DeviceType, MatrixType, MatrixPurpose, ParameterPurpose, ParameterType,
            TensorPurpose, TransformationType, MeshType, MorphismType, NormOrder,
            MathSpace, MathModelUsageContext, MathModelSource, MathModelDataType,
            MathModelDataPurpose, CategoryType, CategoryObjectType, MeshAdaptationType,
            MeshPurpose, TensorDecompMethod, TransformationPurpose, TriangularExtractionType
        ]

        dslEnums.each { Class<?> cls ->
            if (Enum.isAssignableFrom(cls)) {
                for (Object constant : cls.enumConstants) {
                    Enum<?> e = (Enum<?>) constant
                    if (e instanceof DslEnumValue) {
                        String id = ((DslEnumValue) e).id
                        addSymbol(e.name(), id, cls.simpleName)
                        enumIdToPreferredSymbol.putIfAbsent(id, new DslSymbol(e.name(), id, cls.simpleName))
                    }
                }
            }
        }

        // ObjectiveSense common synonyms
        addSymbol('minimise', 'MINIMIZE', 'OptimizationObjectiveSense')
        addSymbol('minimize', 'MINIMIZE', 'OptimizationObjectiveSense')
        addSymbol('maximise', 'MAXIMIZE', 'OptimizationObjectiveSense')
        addSymbol('maximize', 'MAXIMIZE', 'OptimizationObjectiveSense')

        // 2. Enumerations from schema
        List<String> knownPrefixes = ['Mmt', 'Mmdt', 'Mmsm', 'Hb', 'Pp', 'Pt', 'Mat', 'Mp', 'Tot', 'Tt', 'Mt', 'Mmos']
        modelDefinition.enumerations.values().each { EnumerationDefinition ed ->
            String id = ed.enumId
            String type = ed.enumTypeId ?: 'Enumeration'
            addSymbol(id, id, type)
            if (ed.enumCode) {
                addSymbol(ed.enumCode, id, type)
            }
            for (String prefix : knownPrefixes) {
                if (id.startsWith(prefix) && id.length() > prefix.length()) {
                    String stripped = id.substring(prefix.length())
                    addSymbol(stripped, id, type)
                    addSymbol(uncapitalize(stripped), id, type)
                    break
                }
            }
        }
    }

    private void addSymbol(final String name, final String id, final String type) {
        if (!name || !id) return
        if (symbols.containsKey(name)) {
            DslSymbol existing = symbols.get(name)
            if (existing.id == id) {
                // Same canonical ID, compatible duplicate
                return
            }
            // Distinct target IDs -> ambiguous symbol
            List<DslSymbol> list = ambiguousSymbols.computeIfAbsent(name) { new ArrayList<DslSymbol>() }
            if (!list.contains(existing)) list.add(existing)
            DslSymbol candidate = new DslSymbol(name, id, type)
            if (!list.contains(candidate)) list.add(candidate)
            symbols.remove(name)
            return
        }
        if (ambiguousSymbols.containsKey(name)) {
            DslSymbol candidate = new DslSymbol(name, id, type)
            List<DslSymbol> list = ambiguousSymbols.get(name)
            if (!list.contains(candidate)) list.add(candidate)
            return
        }
        symbols.put(name, new DslSymbol(name, id, type))
    }

    DslSymbol resolveSymbol(final String name) {
        if (ambiguousSymbols.containsKey(name)) {
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
        EnumerationDefinition ed = modelDefinition.enumeration(enumId)
        if (ed != null) {
            if (ed.enumCode && symbols.containsKey(ed.enumCode) && !ambiguousSymbols.containsKey(ed.enumCode) && symbols.get(ed.enumCode).id == enumId) {
                return ed.enumCode
            }
            List<String> knownPrefixes = ['Mmt', 'Mmdt', 'Mmsm', 'Hb', 'Pp', 'Pt', 'Mat', 'Mp', 'Tot', 'Tt', 'Mt', 'Mmos', 'Dev', 'Dt', 'Mmuc', 'Mms']
            for (String prefix : knownPrefixes) {
                if (enumId.startsWith(prefix) && enumId.length() > prefix.length()) {
                    String stripped = enumId.substring(prefix.length())
                    if (symbols.containsKey(stripped) && !ambiguousSymbols.containsKey(stripped) && symbols.get(stripped).id == enumId) {
                        return stripped
                    }
                }
            }
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

    private static String uncapitalize(final String s) {
        if (!s || s.length() == 0) return s
        if (s.length() == 1) return s.toLowerCase()
        Character.toLowerCase(s.charAt(0)).toString() + s.substring(1)
    }
}
