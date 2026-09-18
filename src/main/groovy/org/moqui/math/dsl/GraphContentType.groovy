/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: GraphContentType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum GraphContentType implements DslEnumValue {
    Custom('GrCntCustom', 'Custom', 'Custom', ''),
    Protobuf('GrCntProtobuf', 'Protobuf', 'Protobuf Encoded Adjacency List', ''),
    CsvEdge('GrCntCsvEdge', 'Csv', 'Csv Edge List Compressed (gzip)', ''),
    GraphML('GrCntGraphML', 'GraphML', 'GraphML Compressed (gzip)', ''),
    ArrowIPC('GrCntArrowIPC', 'ArrowIPC', 'Apache Arrow IPC Stream', ''),
    GraphSON('GrCntGraphSON', 'GraphSON', 'GraphSON (TinkerPop JSON) Compressed (gzip)', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    GraphContentType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static GraphContentType fromId(final String id) {
        if (id == null) return null
        for (GraphContentType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static GraphContentType fromCode(final String code) {
        if (code == null) return null
        for (GraphContentType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static GraphContentType fromName(final String name) {
        if (name == null) return null
        for (GraphContentType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('ProtobufEncodedAdjacencyList'.equalsIgnoreCase(name)) return Protobuf
        if ('CsvEdgeListCompressed'.equalsIgnoreCase(name)) return CsvEdge
        if ('GraphmlCompressed'.equalsIgnoreCase(name)) return GraphML
        if ('ApacheArrowIpcStream'.equalsIgnoreCase(name)) return ArrowIPC
        if ('GraphsonCompressed'.equalsIgnoreCase(name)) return GraphSON
        null
    }
}
