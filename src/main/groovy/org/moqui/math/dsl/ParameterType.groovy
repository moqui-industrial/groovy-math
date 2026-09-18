/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ParameterType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ParameterType implements DslEnumValue {
    Enumeration('PtEnumeration', '', 'Enumeration', ''),
    Byte('PtByte', '', 'Byte', 'PtEnumeration'),
    BitSet('PtBitSet', '', 'BitSet', 'PtEnumeration'),
    Time('PtTime', '', 'Time', ''),
    Date('PtDate', '', 'Date', ''),
    DateTime('PtDateTime', '', 'Date Time', ''),
    Text('PtText', '', 'Text', ''),
    TextIndicator('PtTextIndicator', '', 'Text Indicator', 'PtText'),
    TextShort('PtTextShort', '', 'Text Short', 'PtText'),
    TextLong('PtTextLong', '', 'Text Long', 'PtText'),
    Number('PtNumber', '', 'Number', ''),
    NumberInteger('PtNumberInteger', '', 'Number Integer', 'PtNumber'),
    NumberDecimal('PtNumberDecimal', '', 'Number Decimal', 'PtNumber'),
    NumberFloat('PtNumberFloat', '', 'Number Float', 'PtNumber'),
    CurrencyAmount('PtCurrencyAmount', '', 'Currency Amount', 'PtNumber'),
    CurrencyPrecise('PtCurrencyPrecise', '', 'Currency Precise', 'PtNumber'),
    Vector('PtVector', 'VECTOR', 'Vector Parameter', ''),
    Matrix('PtMatrix', 'MATRIX', 'Matrix Parameter', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ParameterType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ParameterType fromId(final String id) {
        if (id == null) return null
        for (ParameterType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ParameterType fromCode(final String code) {
        if (code == null) return null
        for (ParameterType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
