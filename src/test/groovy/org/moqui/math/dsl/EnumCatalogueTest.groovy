/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import org.moqui.math.entity.EnumerationDefinition
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.moqui.MoquiSchemaInspector

/**
 * The DSL's enums and the schema's seed data are two hand-written lists of the same facts, and
 * they had drifted: values were named that no Enumeration declared, so a model that used them
 * was accepted here and would have been rejected on the way to a database. These tests make the
 * drift impossible to reintroduce silently.
 */
class EnumCatalogueTest {

    @Test
    void loadsTheDeclaredEnumerationsNotJustTheirCount() {
        ModelDefinition model = MoquiSchemaInspector.embedded()

        assert model.enumerations.size() > 1100
        assert model.enumerationTypes.size() > 100

        EnumerationDefinition dense = model.enumeration('MtDense')
        assert dense.enumTypeId == 'MatrixType'
        assert dense.enumCode == 'DENSE'
        assert dense.description == 'Dense Matrix'
    }

    @Test
    void resolvesAHumanCodeWithinItsType() {
        ModelDefinition model = MoquiSchemaInspector.embedded()

        assert model.enumIdForCode('TensorDataType', 'float32') == 'DtFloat32'
        assert model.enumIdForCode('TensorDevice', 'cpu') == 'DevCpu'
        assert model.enumIdForCode('LogicalOperator', 'AND') == 'OpAnd'

        // Codes are unique per type, never globally: the same code under another type is a miss.
        assert model.enumIdForCode('TensorDevice', 'float32') == null
        assert model.enumIdForCode('NoSuchType', 'float32') == null
    }

    @Test
    void walksTheParentHierarchyTheSchemaAlreadyDeclares() {
        ModelDefinition model = MoquiSchemaInspector.embedded()

        // The backend families the dispatcher used to list by hand are declared as parentEnumId.
        assert model.isEnumOrDescendantOf('MmsmJaxJit', 'MmsmJax')
        assert model.isEnumOrDescendantOf('MmsmOpenFoamIcoFoam', 'MmsmOpenFoam')
        assert model.isEnumOrDescendantOf('MmsmOpenFoamSimpleFoam', 'MmsmOpenFoam')
        assert model.isEnumOrDescendantOf('MmsmLibTorchTraining', 'MmsmLibTorch')

        // A value is trivially its own ancestor; unrelated values are not related.
        assert model.isEnumOrDescendantOf('MmsmJax', 'MmsmJax')
        assert !model.isEnumOrDescendantOf('MmsmSimplex', 'MmsmJax')
        assert !model.isEnumOrDescendantOf('MmsmJax', 'MmsmJaxJit')
    }

    @Test
    void listsAnEnumerationTypeTheWayMoquiWould() {
        ModelDefinition model = MoquiSchemaInspector.embedded()

        // find#Enumeration orders by description; so does this, so a model reading a type here
        // sees it in the order the framework would have served from the database.
        List<EnumerationDefinition> devices = model.enumerationsOfType('TensorDevice')
        assert devices*.enumId as Set == ['DevCpu', 'DevCuda', 'DevRocm', 'DevMps', 'DevTpu'] as Set
        assert devices*.description == devices*.description.sort()
        assert model.enumerationsOfType('NoSuchType').isEmpty()
    }

    @Test
    void walksDownTheParentHierarchyTheWayMoquiWould() {
        ModelDefinition model = MoquiSchemaInspector.embedded()

        // find#EnumerationByParent: the parent is included by default, one level deep unless
        // nested is asked for.
        assert model.enumIdsUnder('MmsmOpenFoam') ==
            ['MmsmOpenFoam', 'MmsmOpenFoamIcoFoam', 'MmsmOpenFoamSimpleFoam'] as Set
        assert model.enumIdsUnder('MmsmOpenFoam', false) ==
            ['MmsmOpenFoamIcoFoam', 'MmsmOpenFoamSimpleFoam'] as Set

        // MmtCFD sits under MmtPDEs, which sits under MmtModelDriven: one level does not reach it.
        assert !model.enumIdsUnder('MmtModelDriven', false, false).contains('MmtCFD')
        assert model.enumIdsUnder('MmtModelDriven', false, true).contains('MmtCFD')

        assert model.enumIdsUnder('NoSuchEnum').isEmpty()
    }

    @Test
    void everyDslEnumValueNamesADeclaredEnumeration() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        List<String> unbacked = []

        dslEnumClasses().each { Class<?> type ->
            // DslSymbolicValue constants are literals for free-text fields, not enumeration ids.
            if (DslSymbolicValue.isAssignableFrom(type)) return
            type.enumConstants.each { Object constant ->
                String enumId = ((DslEnumValue) constant).id
                if (model.enumeration(enumId) == null) {
                    unbacked.add("${type.simpleName}.${((Enum) constant).name()} = '${enumId}'".toString())
                }
            }
        }

        assert unbacked.isEmpty() : "DSL enum values with no declared Enumeration: ${unbacked}"
    }

    @Test
    void everyShippedExampleDeclaresOnlyKnownEnumerations() {
        List<File> examples = exampleModelFiles()
        assert examples.size() >= 8 : 'declarative examples were not found; has the layout changed?'

        List<String> problems = []
        examples.each { File example ->
            try {
                MathMeta mathMeta = MathDsl.evaluate(example).validate()
                mathMeta.enumViolations().each { String violation ->
                    problems.add("${example.name}: ${violation}".toString())
                }
                mathMeta.unknownEnumReferences().each { String reference ->
                    problems.add("${example.name}: ${reference}".toString())
                }
            } catch (Throwable t) {
                problems.add("${example.name}: Evaluation failed: ${t.message}".toString())
            }
        }
        assert problems.isEmpty() : "Examples referencing undeclared enumerations:\n${problems.join('\n')}"
    }

    @Test
    void allDeclaredEnumerationTypesHaveDslEnumClasses() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        List<Class<?>> enums = dslEnumClasses()

        assert enums.size() >= 86 : "Expected at least 86 DSL enum classes, found ${enums.size()}"

        Set<String> classNames = enums.collect { it.simpleName } as Set
        List<String> missingTypes = []
        model.enumerationTypes.each { String typeId ->
            if (!classNames.contains(typeId) && !classNames.contains(typeId.replaceAll(/[^a-zA-Z0-9_]/, '_'))) {
                missingTypes.add(typeId)
            }
        }
        assert missingTypes.isEmpty() : "Missing DSL enum classes for schema EnumerationTypes: ${missingTypes}"
    }

    @Test
    void generatedEnumClassesSupportBidirectionalLookup() {
        MatrixType dense = MatrixType.fromId('MtDense')
        assert dense == MatrixType.Dense
        assert dense.id == 'MtDense'
        assert dense.enumCode == 'DENSE'

        MatrixType fromCode = MatrixType.fromCode('DENSE')
        assert fromCode == MatrixType.Dense

        TransformationType matrixProd = TransformationType.fromId('TtMatrixProduct')
        assert matrixProd != null
        assert matrixProd.id == 'TtMatrixProduct'

        VariableDomain vdContinuous = VariableDomain.fromId('VdContinuous')
        assert vdContinuous == VariableDomain.Continuous
        assert vdContinuous.id == 'VdContinuous'
    }

    @Test
    void constantNamesAreWellFormedAndDerivedFromSchema() {
        ModelDefinition model = MoquiSchemaInspector.embedded()
        List<String> malformed = []

        dslEnumClasses().each { Class<?> type ->
            type.enumConstants.each { Object constant ->
                String name = ((Enum) constant).name()
                if (!Character.isUpperCase(name.charAt(0))) {
                    malformed.add("${type.simpleName}.${name} does not start with uppercase".toString())
                }
                for (int i = 0; i < name.length(); i++) {
                    char c = name.charAt(i)
                    if (i == 0 && !Character.isJavaIdentifierStart(c)) {
                        malformed.add("${type.simpleName}.${name} invalid identifier start".toString())
                    } else if (!Character.isJavaIdentifierPart(c)) {
                        malformed.add("${type.simpleName}.${name} invalid character '${c}'".toString())
                    }
                }
            }
        }
        assert malformed.isEmpty() : "Malformed constant names in generated enums:\n${malformed.join('\n')}"
    }

    @Test
    void descriptionAliasesResolve() {
        assert MathModelType.fromName('LinearProgram') == MathModelType.Lp
        assert MathModelType.fromName('QuadraticProgram') == MathModelType.Qp
        assert MathModelType.fromName('MixedIntegerLinearProgram') == MathModelType.Milp
        assert MathModelDataPurpose.fromName('RightHandSide') == MathModelDataPurpose.RhsVector
        assert MathModelDataPurpose.fromName('DecisionVariables') == MathModelDataPurpose.DecisionVars
        assert MathModelDataPurpose.fromName('VariableBounds') == MathModelDataPurpose.VarBounds
        assert MathModelDataPurpose.fromName('ObjectiveFunction') == MathModelDataPurpose.Objective
        assert MathModelDataPurpose.fromName('CostVector') == MathModelDataPurpose.CostVector
    }

    private static List<Class<?>> dslEnumClasses() {
        List<Class<?>> found = []
        new File('src/main/groovy/org/moqui/math/dsl').listFiles()
            .findAll { File file -> file.name.endsWith('.groovy') }
            .sort { File file -> file.name }
            .each { File file ->
                Class<?> type
                try {
                    type = Class.forName('org.moqui.math.dsl.' + file.name[0..-8])
                } catch (ClassNotFoundException ignored) {
                    return
                }
                if (type.isEnum() && DslEnumValue.isAssignableFrom(type)) found.add(type)
            }
        assert found.size() >= 86 : 'DSL enum classes were not found; has the package moved?'
        found
    }

    /** The declarative model files, as opposed to the run-* execution runners beside them. */
    private static List<File> exampleModelFiles() {
        new File('examples').listFiles()
            .findAll { File file -> file.name.endsWith('.groovy') && !file.name.startsWith('run-') }
            .sort { File file -> file.name }
    }
}
