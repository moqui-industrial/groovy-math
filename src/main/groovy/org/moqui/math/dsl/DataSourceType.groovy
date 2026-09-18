/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: DataSourceType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum DataSourceType implements DslEnumValue {
    PURCHASED_DATA('DST_PURCHASED_DATA', '', 'Purchased Data', ''),
    CUSTOMER_ENTRY('DST_CUSTOMER_ENTRY', '', 'Customer Data Entry', ''),
    INTERNAL_ENTRY('DST_INTERNAL_ENTRY', '', 'Internal Data Entry (employees, etc)', ''),
    MAILING_SIGNUP('DST_MAILING_SIGNUP', '', 'Mailing List Sign-up', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    DataSourceType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static DataSourceType fromId(final String id) {
        if (id == null) return null
        for (DataSourceType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static DataSourceType fromCode(final String code) {
        if (code == null) return null
        for (DataSourceType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
