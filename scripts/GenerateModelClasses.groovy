package scripts

import groovy.math.moqui.MoquiSchemaInspector
import groovy.math.entity.ModelDefinition
import groovy.math.entity.EntityDefinition
import groovy.math.entity.FieldDefinition
import groovy.math.entity.RelationshipDefinition
import java.beans.Introspector

File xmlFile = new File(System.getenv('MOQUI_MATH_ENTITIES') ?: '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml')
if (!xmlFile.exists()) {
    println "Schema file not found at ${xmlFile.absolutePath}"
    System.exit(1)
}

File outDir = new File('src/main/groovy/groovy/math/model')
outDir.mkdirs()

File metaOutDir = new File('src/main/groovy/groovy/math/metamodel')
metaOutDir.mkdirs()

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

    // 1. Generate Domain Model Entity
    StringBuilder code = new StringBuilder()
    code.append("""/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: ${entity.fullName}
 */
package groovy.math.model

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
import groovy.transform.builder.ExternalStrategy
import groovy.transform.NamedVariant
import java.util.Map

@CompileStatic
@EqualsAndHashCode(includes = [${pks.collect { "'${sanitizeIdentifier(it.name)}'" }.join(', ')}])
@ToString(includePackage = false, includeNames = true)
@AutoClone
""")
    if (hasSequenceNum) {
        code.append("@Sortable(includes = ['sequenceNum'])\n")
    }
    code.append("""class ${className} implements Serializable {
    private static final long serialVersionUID = 1L

""")

    fields.each { FieldDefinition field ->
        String fieldType = mapFieldType(field.type)
        String propName = sanitizeIdentifier(field.name)
        code.append("    /** ${field.name} */\n")
        code.append("    ${fieldType} ${propName}\n\n")
    }

    // Default Constructor
    code.append("    ${className}() {}\n\n")

    // Map Constructor
    code.append("""    ${className}(Map<String, Object> args) {
        if (args != null) {
""")
    fields.each { FieldDefinition field ->
        String propName = sanitizeIdentifier(field.name)
        String fieldType = mapFieldType(field.type)
        if (fieldType == 'Long') {
            code.append("            if (args.containsKey('${field.name}')) this.${propName} = args.get('${field.name}') != null ? ((Number) args.get('${field.name}')).longValue() : null\n")
        } else if (fieldType == 'BigDecimal') {
            code.append("            if (args.containsKey('${field.name}')) this.${propName} = args.get('${field.name}') != null ? (args.get('${field.name}') instanceof BigDecimal ? (BigDecimal) args.get('${field.name}') : new BigDecimal(args.get('${field.name}').toString())) : null\n")
        } else if (fieldType == 'Double') {
            code.append("            if (args.containsKey('${field.name}')) this.${propName} = args.get('${field.name}') != null ? ((Number) args.get('${field.name}')).doubleValue() : null\n")
        } else if (fieldType == 'String') {
            code.append("            if (args.containsKey('${field.name}')) this.${propName} = args.get('${field.name}')?.toString()\n")
        } else {
            code.append("            if (args.containsKey('${field.name}')) this.${propName} = (${fieldType}) args.get('${field.name}')\n")
        }
    }
    code.append("""        }
    }

""")

    // NamedVariant Constructor
    code.append("    @NamedVariant\n")
    code.append("    ${className}(\n")
    fields.eachWithIndex { FieldDefinition field, int idx ->
        String fieldType = mapFieldType(field.type)
        String propName = sanitizeIdentifier(field.name)
        String comma = (idx < fields.size() - 1) ? ',' : ''
        code.append("        ${fieldType} ${propName}${comma}\n")
    }
    code.append("    ) {\n")
    fields.each { FieldDefinition field ->
        String propName = sanitizeIdentifier(field.name)
        code.append("        this.${propName} = ${propName}\n")
    }
    code.append("    }\n\n")

    // Fluent Setters / Builder Methods
    fields.each { FieldDefinition field ->
        String fieldType = mapFieldType(field.type)
        String propName = sanitizeIdentifier(field.name)
        code.append("""    ${className} ${propName}(${fieldType} value) {
        this.${propName} = value
        this
    }

""")
    }

    // Required fields validation helper
    if (!requiredFields.isEmpty()) {
        code.append("""    void validate() throws IllegalStateException {
        List<String> missing = []
""")
        requiredFields.each { FieldDefinition rf ->
            String propName = sanitizeIdentifier(rf.name)
            code.append("        if (this.${propName} == null) missing.add('${rf.name}')\n")
        }
        code.append("""        if (!missing.isEmpty()) {
            throw new IllegalStateException("Missing required fields for ${className}: " + missing.join(', '))
        }
    }
""")
    }

    // toMap() converter
    code.append("""    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>()
""")
    fields.each { FieldDefinition field ->
        String propName = sanitizeIdentifier(field.name)
        code.append("        if (this.${propName} != null) map.put('${field.name}', this.${propName})\n")
    }
    code.append("""        return map
    }
}
""")

    classFile.text = code.toString()
    println "Generated ${className}.groovy in groovy.math.model"

    // 2. Generate Canonical Metamodel Class (e.g. Matrix_, Graph_, etc.)
    File metaClassFile = new File(metaOutDir, "${className}_.groovy")
    StringBuilder metaCode = new StringBuilder()
    metaCode.append("""/*
 * Canonical Static Metamodel for Moqui Math Entity: ${entity.fullName}
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.${className}

@CompileStatic
class ${className}_ {
    public static final String ENTITY_NAME = '${entity.name}'
    public static final String FULL_NAME = '${entity.fullName}'

""")
    fields.each { FieldDefinition field ->
        String fieldType = mapFieldType(field.type)
        String propName = sanitizeIdentifier(field.name)
        metaCode.append("    public static final Attribute<${className}, ${fieldType}> ${propName} = new Attribute<>('${field.name}', ${className}.class, ${fieldType}.class, ${field.isPrimaryKey()}, ${field.isRequired()})\n")
    }
    metaCode.append("}\n")
    metaClassFile.text = metaCode.toString()
    println "Generated ${className}_.groovy in groovy.math.metamodel"
}

println "Model generation complete (${model.entities.values().count { it.packageName.startsWith('moqui.math') }} model classes and canonical metamodels generated)."
