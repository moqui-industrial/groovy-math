package scripts

import org.moqui.math.moqui.MoquiSchemaInspector
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.FieldDefinition
import org.moqui.math.entity.RelationshipDefinition
import java.beans.Introspector

File xmlFile = new File(System.getenv('MOQUI_MATH_ENTITIES') ?: '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml')
if (!xmlFile.exists()) {
    println "Schema file not found at ${xmlFile.absolutePath}"
    System.exit(1)
}

File outDir = new File('src/main/groovy/org/moqui/math/model')
if (outDir.exists()) {
    outDir.deleteDir()
}
outDir.mkdirs()

ModelDefinition model = MoquiSchemaInspector.inspect(xmlFile)

def reservedKeywords = [
    'abstract', 'assert', 'boolean', 'break', 'byte', 'case', 'catch', 'char', 'class', 'const',
    'continue', 'default', 'do', 'double', 'else', 'enum', 'extends', 'final', 'finally', 'float',
    'for', 'goto', 'if', 'implements', 'import', 'instanceof', 'int', 'interface', 'long', 'native',
    'new', 'package', 'private', 'protected', 'public', 'return', 'short', 'static', 'strictfp',
    'super', 'switch', 'synchronized', 'this', 'throw', 'throws', 'transient', 'try', 'void',
    'volatile', 'while', 'def', 'trait', 'in', 'as', 'null', 'true', 'false'
] as Set<String>

def sanitizeIdentifier = { String name ->
    if (reservedKeywords.contains(name)) {
        return name + 'Ref'
    }
    return name
}

def mapFieldType = { String moquiType ->
    if (!moquiType) return 'Object'
    switch (moquiType) {
        case 'id':
        case 'id-long':
        case 'id-vlong':
        case 'text-short':
        case 'text-medium':
        case 'text-long':
        case 'text-very-long':
        case 'text-indicator':
            return 'String'
        case 'number-integer':
            return 'Long'
        case 'number-decimal':
            return 'BigDecimal'
        case 'number-float':
            return 'Double'
        case 'date-time':
            return 'java.sql.Timestamp'
        case 'date':
            return 'java.sql.Date'
        case 'time':
            return 'java.sql.Time'
        case 'binary-very-long':
            return 'byte[]'
        default:
            return 'Object'
    }
}

model.entities.values().each { EntityDefinition entity ->
    if (!entity.packageName.startsWith('moqui.math')) return

    String className = entity.name
    File classFile = new File(outDir, "${className}.groovy")
    
    List<FieldDefinition> fields = entity.fields.values().toList()
    List<RelationshipDefinition> relationships = entity.relationships.values().toList()
    List<FieldDefinition> pks = entity.primaryKeyFields
    List<FieldDefinition> requiredFields = fields.findAll { it.isRequired() }

    StringBuilder code = new StringBuilder()
    code.append("""/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: ${entity.fullName}
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
""")

    boolean hasSequenceNum = fields.any { it.name == 'sequenceNum' }
    if (hasSequenceNum) {
        code.append("import groovy.transform.Sortable\n")
    }

    code.append("""import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
""")

    // Add EqualsAndHashCode on primary keys to prevent graph cycles in RAM
    if (!pks.isEmpty()) {
        String pkList = pks.collect { "'${it.name}'" }.join(', ')
        code.append("@EqualsAndHashCode(includes = [${pkList}])\n")
    } else {
        String fieldList = fields.collect { "'${it.name}'" }.join(', ')
        code.append("@EqualsAndHashCode(includes = [${fieldList}])\n")
    }

    code.append("@ToString(includeNames = true, ignoreNulls = true)\n")
    code.append("@AutoClone\n")
    if (hasSequenceNum) {
        code.append("@Sortable(includes = ['sequenceNum'])\n")
    }
    code.append("@Builder(builderStrategy = SimpleStrategy, prefix = '')\n")
    code.append("class ${className} implements Serializable {\n")
    code.append("    private static final long serialVersionUID = 1L\n\n")

    // Direct entity fields
    fields.each { FieldDefinition f ->
        String fieldName = sanitizeIdentifier(f.name)
        String fieldType = mapFieldType(f.type)
        String comment = f.isRequired() ? " // Required${f.primaryKey ? ' (PK)' : ''}" : ""
        code.append("    ${fieldType} ${fieldName}${comment}\n")
    }

    // Relationships (Dot Notation and in-memory graph navigation)
    if (!relationships.isEmpty()) {
        code.append("\n    // --- Relationships (In-Memory Navigation) ---\n")
        relationships.each { RelationshipDefinition rel ->
            String rawPropName = Introspector.decapitalize(rel.name)
            if (fields.any { it.name == rawPropName }) {
                rawPropName = rawPropName + 'Rel'
            }
            String relPropName = sanitizeIdentifier(rawPropName)
            boolean isMathEntity = rel.relatedEntityName.startsWith('moqui.math.')
            String targetType = isMathEntity ? rel.relatedEntityName.tokenize('.').last() : 'Object'

            if (rel.type == 'many') {
                code.append("    List<${targetType}> ${relPropName} = []\n")
            } else {
                code.append("    ${targetType} ${relPropName}\n")
            }
        }
    }

    code.append("\n")

    // Default constructor
    code.append("    ${className}() { }\n\n")

    // Primary key constructor if there is a single PK
    if (pks.size() == 1) {
        FieldDefinition pk = pks.first()
        String pkType = mapFieldType(pk.type)
        String pkName = sanitizeIdentifier(pk.name)
        code.append("""    ${className}(${pkType} ${pkName}) {
        this.${pkName} = Objects.requireNonNull(${pkName}, "${className}.${pkName} cannot be null")
    }

""")
    }

    // Map-based constructor for Groovy map coercions
    code.append("""    ${className}(Map<String, ?> args) {
        if (args != null) {
""")
    fields.each { FieldDefinition f ->
        String fieldName = sanitizeIdentifier(f.name)
        String fieldType = mapFieldType(f.type)
        code.append("            if (args.containsKey('${f.name}')) this.${fieldName} = args.get('${f.name}') as ${fieldType}\n")
    }
    relationships.each { RelationshipDefinition rel ->
        String rawPropName = Introspector.decapitalize(rel.name)
        if (fields.any { it.name == rawPropName }) rawPropName = rawPropName + 'Rel'
        String relPropName = sanitizeIdentifier(rawPropName)
        boolean isMathEntity = rel.relatedEntityName.startsWith('moqui.math.')
        String targetType = isMathEntity ? rel.relatedEntityName.tokenize('.').last() : 'Object'
        if (rel.type == 'many') {
            code.append("            if (args.containsKey('${relPropName}')) this.${relPropName} = args.get('${relPropName}') as List<${targetType}>\n")
            if (rawPropName != relPropName) {
                code.append("            else if (args.containsKey('${rawPropName}')) this.${relPropName} = args.get('${rawPropName}') as List<${targetType}>\n")
            }
        } else {
            code.append("            if (args.containsKey('${relPropName}')) this.${relPropName} = args.get('${relPropName}') as ${targetType}\n")
            if (rawPropName != relPropName) {
                code.append("            else if (args.containsKey('${rawPropName}')) this.${relPropName} = args.get('${rawPropName}') as ${targetType}\n")
            }
        }
    }
    code.append("""        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
""")
    requiredFields.each { FieldDefinition f ->
        String fieldName = sanitizeIdentifier(f.name)
        code.append("        if (this.${fieldName} == null) throw new IllegalStateException(\"Required property missing: ${className}.${fieldName}\")\n")
    }
    code.append("""    }

    /**
     * Gradle-style closure configurator
     */
    ${className} configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ${className}) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }
""")

    // Relationship closure builders for Gradle-style DSL
    relationships.each { RelationshipDefinition rel ->
        String rawPropName = Introspector.decapitalize(rel.name)
        if (fields.any { it.name == rawPropName }) rawPropName = rawPropName + 'Rel'
        String relPropName = sanitizeIdentifier(rawPropName)
        boolean isMathEntity = rel.relatedEntityName.startsWith('moqui.math.')
        if (isMathEntity) {
            String targetType = rel.relatedEntityName.tokenize('.').last()
            if (rel.type == 'many') {
                code.append("""
    ${targetType} ${relPropName}(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ${targetType}) Closure<?> action) {
        ${targetType} item = new ${targetType}()
        item.configure(action)
        if (this.${relPropName} == null) this.${relPropName} = []
        this.${relPropName}.add(item)
        item
    }
""")
            } else {
                code.append("""
    ${targetType} ${relPropName}(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ${targetType}) Closure<?> action) {
        if (this.${relPropName} == null) this.${relPropName} = new ${targetType}()
        this.${relPropName}.configure(action)
        this.${relPropName}
    }
""")
            }
        }
    }

    code.append("}\n")

    classFile.text = code.toString()
    println "Generated ${className}.groovy in org.moqui.math.model"
}

println "Model generation complete (${outDir.listFiles().length} model classes generated)."
