/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ParameterGroup
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ParameterGroup implements DslEnumValue {
    Control('PgControl', '', 'Control Parameter', ''),
    MainControl('PgMainControl', '', 'Main Control Parameter', 'PgControl'),
    AdvancedControl('PgAdvancedControl', '', 'Advanced Control Parameter', 'PgControl'),
    Monitoring('PgMonitoring', '', 'Monitoring Parameter', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ParameterGroup(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ParameterGroup fromId(final String id) {
        if (id == null) return null
        for (ParameterGroup val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ParameterGroup fromCode(final String code) {
        if (code == null) return null
        for (ParameterGroup val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
