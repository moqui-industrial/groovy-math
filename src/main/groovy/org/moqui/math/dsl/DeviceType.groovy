/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorDevice
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum DeviceType implements DslEnumValue {
    CPU('DevCpu', 'cpu', 'Host CPU', ''),
    CUDA('DevCuda', 'cuda', 'NVIDIA CUDA Device', ''),
    ROCM('DevRocm', 'rocm', 'AMD ROCm Device', ''),
    MPS('DevMps', 'mps', 'Apple Metal Performance Shaders', ''),
    Tpu('DevTpu', 'tpu', 'Tensor Processing Unit', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    DeviceType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static DeviceType fromId(final String id) {
        if (id == null) return null
        for (DeviceType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static DeviceType fromCode(final String code) {
        if (code == null) return null
        for (DeviceType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
