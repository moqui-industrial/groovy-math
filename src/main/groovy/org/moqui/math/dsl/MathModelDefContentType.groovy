/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelDefContentType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelDefContentType implements DslEnumValue {
    Python('MmCntPython', 'Python', 'Python Script', ''),
    Yaml('MmCntYaml', 'Yaml', 'YAML', ''),
    Json('MmCntJson', 'Json', 'JSON', ''),
    Csv('MmCntCsv', 'Csv', 'CSV', ''),
    SymPy('MmCntSymPy', 'SymPy', 'SymPy Script', ''),
    TorchScript('MmCntTorchScript', 'TorchScript', 'TorchScript Bundle', ''),
    Jax('MmCntJax', 'JAX', 'JAX / XLA Program', ''),
    BinaryModel('MmCntBinaryModel', 'BinaryModel', 'Binary Model Blob', ''),
    Onnx('MmCntOnnx', 'Onnx', 'ONNX exports', ''),
    Docs('MmCntDocs', 'Docs', 'Documentation', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelDefContentType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
        this.id = id
        this.enumCode = enumCode
        this.description = description
        this.parentEnumId = parentEnumId
    }

    @Override
    String getId() { id }

    @Override
    String getEnumCode() { enumCode }

    @Override
    String getDescription() { description }

    @Override
    String getParentEnumId() { parentEnumId }

    static MathModelDefContentType fromId(final String id) {
        if (id == null) return null
        for (MathModelDefContentType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelDefContentType fromCode(final String code) {
        if (code == null) return null
        for (MathModelDefContentType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MathModelDefContentType fromName(final String name) {
        if (name == null) return null
        for (MathModelDefContentType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('PythonScript'.equalsIgnoreCase(name)) return Python
        if ('SympyScript'.equalsIgnoreCase(name)) return SymPy
        if ('TorchscriptBundle'.equalsIgnoreCase(name)) return TorchScript
        if ('JaxXlaProgram'.equalsIgnoreCase(name)) return Jax
        if ('BinaryModelBlob'.equalsIgnoreCase(name)) return BinaryModel
        if ('OnnxExports'.equalsIgnoreCase(name)) return Onnx
        if ('Documentation'.equalsIgnoreCase(name)) return Docs
        null
    }
}
