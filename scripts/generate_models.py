#!/usr/bin/env python3
import xml.etree.ElementTree as ET
import os

xml_path = os.environ.get('MOQUI_MATH_ENTITIES', '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml')
if not os.path.exists(xml_path):
    xml_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'))

tree = ET.parse(xml_path)
root = tree.getroot()

model_dir = 'src/main/groovy/groovy/math/model'
meta_dir = 'src/main/groovy/groovy/math/metamodel'
os.makedirs(model_dir, exist_ok=True)
os.makedirs(meta_dir, exist_ok=True)

reserved = {
    'abstract', 'assert', 'boolean', 'break', 'byte', 'case', 'catch', 'char', 'class', 'const',
    'continue', 'default', 'do', 'double', 'else', 'enum', 'extends', 'final', 'finally', 'float',
    'for', 'goto', 'if', 'implements', 'import', 'instanceof', 'int', 'interface', 'long', 'native',
    'new', 'package', 'private', 'protected', 'public', 'return', 'short', 'static', 'strictfp',
    'super', 'switch', 'synchronized', 'this', 'throw', 'throws', 'transient', 'try', 'void',
    'volatile', 'while', 'def', 'trait', 'in', 'as', 'null', 'true', 'false'
}

def sanitize(name):
    return name + 'Ref' if name in reserved else name

def map_type(mtype):
    if not mtype: return 'Object'
    if mtype in ['id', 'id-long', 'id-vlong', 'text-short', 'text-medium', 'text-long', 'text-very-long', 'text-indicator']:
        return 'String'
    if mtype == 'number-integer':
        return 'Long'
    if mtype == 'number-decimal':
        return 'BigDecimal'
    if mtype == 'number-float':
        return 'Double'
    if mtype == 'date-time':
        return 'java.sql.Timestamp'
    if mtype == 'date':
        return 'java.sql.Date'
    if mtype == 'time':
        return 'java.sql.Time'
    if mtype == 'binary-very-long':
        return 'byte[]'
    return 'Object'

entities = root.findall('.//entity')
count = 0

for ent in entities:
    pkg = ent.get('package', '')
    if not pkg.startswith('moqui.math'):
        continue

    class_name = ent.get('entity-name')
    full_name = f"{pkg}.{class_name}"
    
    fields = []
    pks = []
    required = []
    
    for f in ent.findall('field'):
        fname = f.get('name')
        ftype = f.get('type')
        ispk = f.get('is-pk', 'false').lower() == 'true'
        isreq = ispk or f.get('not-null', 'false').lower() == 'true'
        fields.append((fname, ftype, ispk, isreq))
        if ispk: pks.append(fname)
        if isreq: required.append(fname)

    rels = []
    for r in ent.findall('relationship'):
        rtype = r.get('type', 'one')
        related_pkg = r.get('related', '')
        if not related_pkg.startswith('moqui.math'):
            continue
        related = related_pkg.split('.')[-1]
        alias = r.get('short-alias')
        if not alias:
            title = r.get('title', '')
            raw_name = title + related
            alias = raw_name[0].lower() + raw_name[1:] if raw_name else related.lower()
        if related:
            rels.append((rtype, related, sanitize(alias)))

    has_seq = any(f[0] == 'sequenceNum' for f in fields)
    
    # 1. Generate Model Entity
    m_lines = []
    m_lines.append("/*\n * Generated domain model for Moqui Math Metamodel\n * Entity: " + full_name + "\n */")
    m_lines.append("package groovy.math.model\n")
    m_lines.append("import groovy.transform.CompileStatic")
    m_lines.append("import groovy.transform.EqualsAndHashCode")
    m_lines.append("import groovy.transform.ToString")
    m_lines.append("import groovy.transform.AutoClone")
    if has_seq:
        m_lines.append("import groovy.transform.Sortable")
    m_lines.append("import java.util.Map")
    m_lines.append("import java.util.List")
    m_lines.append("import java.util.ArrayList\n")
    
    pk_str = ", ".join(f"'{sanitize(p)}'" for p in pks)
    m_lines.append("@CompileStatic")
    m_lines.append(f"@EqualsAndHashCode(includes = [{pk_str}])")
    m_lines.append("@ToString(includePackage = false, includeNames = true)")
    m_lines.append("@AutoClone")
    if has_seq:
        m_lines.append("@Sortable(includes = ['sequenceNum'])")
    m_lines.append(f"class {class_name} implements Serializable {{")
    m_lines.append("    private static final long serialVersionUID = 1L\n")
    
    for fname, ftype, ispk, isreq in fields:
        jtype = map_type(ftype)
        pname = sanitize(fname)
        m_lines.append(f"    /** {fname} */\n    {jtype} {pname}\n")

    # Relationship fields
    for rtype, related, ralias in rels:
        if rtype == 'many':
            m_lines.append(f"    List<{related}> {ralias} = new ArrayList<>()\n")
        else:
            m_lines.append(f"    {related} {ralias}\n")

    # Default constructor
    m_lines.append(f"    {class_name}() {{}}\n")
    
    # Map constructor
    m_lines.append(f"    {class_name}(Map<String, Object> args) {{")
    m_lines.append("        if (args != null) {")
    for fname, ftype, ispk, isreq in fields:
        jtype = map_type(ftype)
        pname = sanitize(fname)
        if jtype == 'Long':
            m_lines.append(f"            if (args.containsKey('{fname}')) this.{pname} = args.get('{fname}') != null ? ((Number) args.get('{fname}')).longValue() : null")
        elif jtype == 'BigDecimal':
            m_lines.append(f"            if (args.containsKey('{fname}')) this.{pname} = args.get('{fname}') != null ? (args.get('{fname}') instanceof BigDecimal ? (BigDecimal) args.get('{fname}') : new BigDecimal(args.get('{fname}').toString())) : null")
        elif jtype == 'Double':
            m_lines.append(f"            if (args.containsKey('{fname}')) this.{pname} = args.get('{fname}') != null ? ((Number) args.get('{fname}')).doubleValue() : null")
        elif jtype == 'String':
            m_lines.append(f"            if (args.containsKey('{fname}')) this.{pname} = args.get('{fname}')?.toString()")
        else:
            m_lines.append(f"            if (args.containsKey('{fname}')) this.{pname} = ({jtype}) args.get('{fname}')")
    m_lines.append("        }\n    }\n")

    # Fluent Builder methods for fields
    for fname, ftype, ispk, isreq in fields:
        jtype = map_type(ftype)
        pname = sanitize(fname)
        m_lines.append(f"    {class_name} {pname}({jtype} value) {{\n        this.{pname} = value\n        return this;\n    }}\n")

    # Fluent Builder methods for relationships
    for rtype, related, ralias in rels:
        if rtype == 'many':
            m_lines.append(f"    {class_name} {ralias}(List<{related}> list) {{\n        this.{ralias} = list;\n        return this;\n    }}\n")
        else:
            m_lines.append(f"    {class_name} {ralias}({related} item) {{\n        this.{ralias} = item;\n        return this;\n    }}\n")

    # toMap
    m_lines.append("    Map<String, Object> toMap() {\n        Map<String, Object> map = new LinkedHashMap<>();")
    for fname, ftype, ispk, isreq in fields:
        pname = sanitize(fname)
        m_lines.append(f"        if (this.{pname} != null) map.put('{fname}', this.{pname});")
    m_lines.append("        return map;\n    }\n}")

    with open(os.path.join(model_dir, f"{class_name}.groovy"), "w") as fh:
        fh.write("\n".join(m_lines))

    # 2. Generate Canonical Metamodel Class
    c_lines = []
    c_lines.append("/*\n * Canonical Static Metamodel for Moqui Math Entity: " + full_name + "\n * JPA Criteria-style Metamodel Descriptor\n */")
    c_lines.append("package groovy.math.metamodel\n")
    c_lines.append("import groovy.transform.CompileStatic")
    c_lines.append(f"import groovy.math.model.{class_name}\n")
    c_lines.append("@CompileStatic")
    c_lines.append(f"class {class_name}_ {{")
    c_lines.append(f"    public static final String ENTITY_NAME = '{class_name}'")
    c_lines.append(f"    public static final String FULL_NAME = '{full_name}'\n")
    for fname, ftype, ispk, isreq in fields:
        jtype = map_type(ftype)
        pname = sanitize(fname)
        ispk_str = 'true' if ispk else 'false'
        isreq_str = 'true' if isreq else 'false'
        c_lines.append(f"    public static final Attribute<{class_name}, {jtype}> {pname} = new Attribute<>('{fname}', {class_name}.class, {jtype}.class, {ispk_str}, {isreq_str})")
    c_lines.append("}\n")

    with open(os.path.join(meta_dir, f"{class_name}_.groovy"), "w") as fh:
        fh.write("\n".join(c_lines))

    count += 1

print(f"Successfully generated {count} model entities and canonical metamodel classes.")
