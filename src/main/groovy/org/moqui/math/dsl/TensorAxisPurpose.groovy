/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorAxisPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorAxisPurpose implements DslEnumValue {
    DateTime('TapDateTime', '', 'Date and Time', ''),
    Device('TapDevice', '', 'Device', ''),
    Measurement('TapMeasurement', '', 'Measurement / Metric', ''),
    Parameter('TapParameter', '', 'Parameter', ''),
    Batch('TapBatch', '', 'Batch / Sample', ''),
    Channel('TapChannel', '', 'Channel / Feature-map', ''),
    Height('TapHeight', '', 'Spatial - Height / Y', ''),
    Width('TapWidth', '', 'Spatial - Width  / X', ''),
    Depth('TapDepth', '', 'Spatial - Depth / Z', ''),
    Time('TapTime', '', 'Temporal / Sequence step', ''),
    Feature('TapFeature', '', 'Generic feature dimension', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorAxisPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorAxisPurpose fromId(final String id) {
        if (id == null) return null
        for (TensorAxisPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorAxisPurpose fromCode(final String code) {
        if (code == null) return null
        for (TensorAxisPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
