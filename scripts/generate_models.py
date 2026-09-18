#!/usr/bin/env python3
import xml.etree.ElementTree as ET
import os
import re

# Generate from the schema vendored under src/main/resources, not from an upstream
# checkout: the POJOs must describe the XML that actually ships in the jar.
# Upstream -> scripts/sync_schema.py -> vendored resources -> this generator.
res_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'src', 'main', 'resources', 'moqui-math'))
xml_path = os.environ.get('MOQUI_MATH_ENTITIES') or os.path.join(res_dir, 'MathEntities.xml')
if not os.path.exists(xml_path):
    raise SystemExit('Schema not found: %s (run ./gradlew syncMoquiSchema)' % xml_path)

tree = ET.parse(xml_path)
root = tree.getroot()

model_dir = 'src/main/groovy/org/moqui/math/model'
meta_dir = 'src/main/groovy/org/moqui/math/metamodel'
dsl_dir = 'src/main/groovy/org/moqui/math/dsl'
os.makedirs(model_dir, exist_ok=True)
os.makedirs(meta_dir, exist_ok=True)
os.makedirs(dsl_dir, exist_ok=True)

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
    m_lines.append("package org.moqui.math.model\n")
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
    c_lines.append("package org.moqui.math.metamodel\n")
    c_lines.append("import groovy.transform.CompileStatic")
    c_lines.append(f"import org.moqui.math.model.{class_name}\n")
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

# 3. Generate DSL Enumeration Classes
xml_files = [os.path.join(res_dir, 'BasicEntities.xml'),
             os.path.join(res_dir, 'MathEntities.xml'),
             os.path.join(res_dir, 'MathData.xml')]

types = {}
enums = {}

for f in xml_files:
    if not os.path.exists(f): continue
    t = ET.parse(f)
    r = t.getroot()
    for tag in ['moqui.basic.EnumerationType', 'EnumerationType']:
        for et in r.findall(f'.//{tag}'):
            tid = et.get('enumTypeId')
            desc = et.get('description', '')
            parent = et.get('parentTypeId', '')
            types[tid] = {'enumTypeId': tid, 'description': desc, 'parentTypeId': parent, 'enums': []}
            
    for tag in ['moqui.basic.Enumeration', 'Enumeration']:
        for en in r.findall(f'.//{tag}'):
            eid = en.get('enumId')
            tid = en.get('enumTypeId')
            code = en.get('enumCode', '')
            desc = en.get('description', '')
            parent = en.get('parentEnumId', '')
            enums[eid] = {'enumId': eid, 'enumTypeId': tid, 'enumCode': code, 'description': desc, 'parentEnumId': parent}

for eid, edata in enums.items():
    tid = edata['enumTypeId']
    if tid in types:
        types[tid]['enums'].append(edata)

def is_valid_ident(s):
    return bool(re.match(r'^[a-zA-Z_][a-zA-Z0-9_]*$', s)) and s not in reserved

def compute_word_boundary_prefix(ids):
    if not ids: return ''
    p = ids[0]
    for s in ids[1:]:
        j = 0
        while j < len(p) and j < len(s) and p[j] == s[j]:
            j += 1
        p = p[:j]
        if not p: break
    
    for k in range(len(p), -1, -1):
        sub_p = p[:k]
        ok = True
        for s in ids:
            rem = s[len(sub_p):]
            if not rem or not rem[0].isupper() or not is_valid_ident(rem):
                ok = False
                break
        if ok:
            return sub_p
    return ''

def clean_identifier(name):
    clean = re.sub(r'[^a-zA-Z0-9_]', '_', name)
    if not clean: return 'Constant'
    if clean[0].isdigit(): clean = 'C_' + clean
    if clean in reserved: clean = clean + '_'
    return clean

def escape_groovy(s):
    if s is None: return ''
    return s.replace('\\', '\\\\').replace("'", "\\'")

def description_alias(desc):
    if not desc: return ''
    cleaned = re.sub(r'\(.*?\)', '', desc)
    words = [w for w in re.split(r'[^a-zA-Z0-9]+', cleaned) if w]
    camel = ''.join(w.capitalize() for w in words)
    return camel if is_valid_ident(camel) and camel[0].isupper() else ''

def generate_enum_class(type_id, enum_list, out_class_name=None):
    cname = out_class_name or type_id
    if not clean_identifier(cname) == cname:
        cname = clean_identifier(cname)
    
    ids = [e['enumId'] for e in enum_list]
    prefix = compute_word_boundary_prefix(ids)
    can_strip = bool(prefix)
    if can_strip:
        rem_set = set()
        for e in enum_list:
            rem = e['enumId'][len(prefix):]
            if rem in rem_set:
                can_strip = False
                break
            rem_set.add(rem)

    alias_counts = {}
    for e in enum_list:
        alias = description_alias(e.get('description', ''))
        if alias:
            alias_counts[alias] = alias_counts.get(alias, 0) + 1

    seen = set()
    constants = []
    const_names = []

    for e in enum_list:
        eid = e['enumId']
        code = e.get('enumCode', '')
        desc = e.get('description', '')
        parent = e.get('parentEnumId', '')
        
        name = None
        # Rule 1: stripped word-boundary prefix if valid and unique for all items
        if can_strip:
            rem = eid[len(prefix):]
            if rem and rem[0].isupper() and is_valid_ident(rem) and rem not in seen:
                name = rem
        # Rule 2: enumCode if present, valid and starts with uppercase
        elif code and is_valid_ident(code) and code[0].isupper() and code not in seen:
            name = code
        # Rule 3: full enumId
        if not name:
            if is_valid_ident(eid) and eid[0].isupper() and eid not in seen:
                name = eid
            else:
                cleaned = clean_identifier(eid)
                if not cleaned[0].isupper():
                    cleaned = cleaned.capitalize()
                name = cleaned

        # Rule 4: description normalized in CamelCase as last resort if needed
        if not name or name in seen:
            desc_camel = description_alias(desc)
            if desc_camel and is_valid_ident(desc_camel) and desc_camel[0].isupper() and desc_camel not in seen:
                name = desc_camel

        if not name:
            name = clean_identifier(eid)
        if name in seen:
            name = f"{name}_{clean_identifier(eid)}"
        seen.add(name)
        const_names.append(name)

        constants.append(f"    {name}('{escape_groovy(eid)}', '{escape_groovy(code)}', '{escape_groovy(desc)}', '{escape_groovy(parent)}')")

    e_lines = []
    e_lines.append("/*\n * Generated domain enum for Moqui Math Metamodel\n * EnumerationType: " + type_id + "\n */")
    e_lines.append("package org.moqui.math.dsl\n")
    e_lines.append("import groovy.transform.CompileStatic\n")
    e_lines.append("@CompileStatic")
    e_lines.append(f"enum {cname} implements DslEnumValue {{")
    
    ret_type = 'DslEnumValue' if cname.startswith('_') else cname
    if constants:
        e_lines.append(",\n".join(constants) + ";\n")
        e_lines.append("    final String id")
        e_lines.append("    final String enumCode")
        e_lines.append("    final String description")
        e_lines.append("    final String parentEnumId\n")
        
        e_lines.append(f"    {cname}(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {{")
        e_lines.append("        this.id = id")
        e_lines.append("        this.enumCode = enumCode")
        e_lines.append("        this.description = description")
        e_lines.append("        this.parentEnumId = parentEnumId")
        e_lines.append("    }\n")

        e_lines.append("    @Override")
        e_lines.append("    String getId() { id }\n")

        e_lines.append("    @Override")
        e_lines.append("    String getEnumCode() { enumCode }\n")

        e_lines.append("    @Override")
        e_lines.append("    String getDescription() { description }\n")

        e_lines.append("    @Override")
        e_lines.append("    String getParentEnumId() { parentEnumId }\n")

        e_lines.append(f"    static {ret_type} fromId(final String id) {{")
        e_lines.append("        if (id == null) return null")
        e_lines.append(f"        for ({cname} val : values()) {{")
        e_lines.append("            if (val.id == id) return val")
        e_lines.append("        }")
        e_lines.append("        null")
        e_lines.append("    }\n")

        e_lines.append(f"    static {ret_type} fromCode(final String code) {{")
        e_lines.append("        if (code == null) return null")
        e_lines.append(f"        for ({cname} val : values()) {{")
        e_lines.append("            if (val.enumCode == code) return val")
        e_lines.append("        }")
        e_lines.append("        null")
        e_lines.append("    }\n")

        e_lines.append(f"    static {ret_type} fromName(final String name) {{")
        e_lines.append("        if (name == null) return null")
        e_lines.append(f"        for ({cname} val : values()) {{")
        e_lines.append("            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val")
        e_lines.append("        }")
        for e, c_name in zip(enum_list, const_names):
            alias = description_alias(e.get('description', ''))
            if alias and alias_counts.get(alias) == 1 and alias != c_name:
                e_lines.append(f"        if ('{escape_groovy(alias)}'.equalsIgnoreCase(name)) return {c_name}")
        e_lines.append("        null")
        e_lines.append("    }")

    e_lines.append("}\n")

    out_file = os.path.join(dsl_dir, f"{cname}.groovy")
    with open(out_file, "w") as fh:
        fh.write("\n".join(e_lines))

enum_count = 0
for tid, tdata in sorted(types.items()):
    generate_enum_class(tid, tdata['enums'])
    enum_count += 1

# Generate compatibility aliases
if 'TensorDataType' in types:
    generate_enum_class('TensorDataType', types['TensorDataType']['enums'], out_class_name='DataType')
if 'TensorDevice' in types:
    generate_enum_class('TensorDevice', types['TensorDevice']['enums'], out_class_name='DeviceType')
if 'EnumGroup' in types:
    # MathSpace maps to EnumGroup's euclidean spaces
    math_space_enums = [e for e in types['EnumGroup']['enums'] if e['enumId'] in ['Eng2DEuclideanSpace', 'Eng3DEuclideanSpace']]
    generate_enum_class('EnumGroup', math_space_enums, out_class_name='MathSpace')

print(f"Successfully generated {enum_count} enum classes and compatibility wrappers.")
