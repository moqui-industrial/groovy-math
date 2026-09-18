/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelContentType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelContentType implements DslEnumValue {
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

    MathModelContentType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MathModelContentType fromId(final String id) {
        if (id == null) return null
        for (MathModelContentType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelContentType fromCode(final String code) {
        if (code == null) return null
        for (MathModelContentType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
