/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ApproxFuncDataStorage
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ApproxFuncDataStorage implements DslEnumValue {
    PointTable('AfdsPointTable', '', 'Row-per-point Table', ''),
    Tensor('AfdsTensor', '', 'Dense Tensor [N,3] or [N,D]', ''),
    External('AfdsExternal', '', 'External Blob, only for parametric paths and trajectories (CSV, GIS Shapefile, …)', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ApproxFuncDataStorage(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ApproxFuncDataStorage fromId(final String id) {
        if (id == null) return null
        for (ApproxFuncDataStorage val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ApproxFuncDataStorage fromCode(final String code) {
        if (code == null) return null
        for (ApproxFuncDataStorage val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
