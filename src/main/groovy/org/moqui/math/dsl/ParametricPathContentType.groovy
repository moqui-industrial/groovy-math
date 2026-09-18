/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ParametricPathContentType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ParametricPathContentType implements DslEnumValue {
    Csv('PpCntCsv', '', 'CSV Parametric Path Data', ''),
    Json('PpCntJson', '', 'JSON Parametric Path Data', ''),
    Xml('PpCntXml', '', 'XML  Parametric Path Data', ''),
    Yaml('PpCntYaml', '', 'YAML Parametric Path Data', ''),
    GCode('PpCntGCode', '', 'G-code (Numerical Control Programming Language) Parametric Path Data', ''),
    Dxf('PpCntDxf', '', 'DXF (Drawing Exchange Format) Geometric Path', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ParametricPathContentType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ParametricPathContentType fromId(final String id) {
        if (id == null) return null
        for (ParametricPathContentType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ParametricPathContentType fromCode(final String code) {
        if (code == null) return null
        for (ParametricPathContentType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
