/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshPurpose implements DslEnumValue {
    FEM('MpFEM', '', 'Finite Element Mesh', ''),
    FVM('MpFVM', '', 'Finite Volume Mesh', ''),
    CFD('MpCFD', '', 'Computational Fluid Dynamics Mesh', ''),
    TopologicalMesh('MpTopologicalMesh', '', 'Topological Analysis Mesh', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MeshPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MeshPurpose fromId(final String id) {
        if (id == null) return null
        for (MeshPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MeshPurpose fromCode(final String code) {
        if (code == null) return null
        for (MeshPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MeshPurpose fromName(final String name) {
        if (name == null) return null
        for (MeshPurpose val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('FiniteElementMesh'.equalsIgnoreCase(name)) return FEM
        if ('FiniteVolumeMesh'.equalsIgnoreCase(name)) return FVM
        if ('ComputationalFluidDynamicsMesh'.equalsIgnoreCase(name)) return CFD
        if ('TopologicalAnalysisMesh'.equalsIgnoreCase(name)) return TopologicalMesh
        null
    }
}
