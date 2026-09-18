/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: GraphCategoryPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum GraphCategoryPurpose implements DslEnumValue {
    Graph('GcpUnderlyingGraph', '', 'Underlying graph U(C): objects become vertices and morphisms become directed edges', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    GraphCategoryPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static GraphCategoryPurpose fromId(final String id) {
        if (id == null) return null
        for (GraphCategoryPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static GraphCategoryPurpose fromCode(final String code) {
        if (code == null) return null
        for (GraphCategoryPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
