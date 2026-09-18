/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: GraphEdgeType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum GraphEdgeType implements DslEnumValue {
    Undirected('GetUndirected', '', 'Undirected Edge', ''),
    Directed('GetDirected', '', 'Directed Edge', ''),
    Boundary('GetBoundary', '', 'Boundary Edge', ''),
    Sharp('GetSharp', '', 'Sharp Edge', ''),
    Smooth('GetSmooth', '', 'Smooth Edge', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    GraphEdgeType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static GraphEdgeType fromId(final String id) {
        if (id == null) return null
        for (GraphEdgeType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static GraphEdgeType fromCode(final String code) {
        if (code == null) return null
        for (GraphEdgeType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
