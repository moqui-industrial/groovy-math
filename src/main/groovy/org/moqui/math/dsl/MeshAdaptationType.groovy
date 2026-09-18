/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshAdaptationType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshAdaptationType implements DslEnumValue {
    None('MatNone', '', 'No Adaptation', ''),
    HRefinement('MatHRefinement', '', 'h-type Refinement (refine by adding smaller cells)', ''),
    HDerefinement('MatHDerefinement', '', 'h-type Derefinement (remove cells in low-error regions)', ''),
    RRefinement('MatRRefinement', '', 'r-type Refinement (move/displace nodes without adding cells)', ''),
    PRefinement('MatPRefinement', '', 'p-type Refinement (increase polynomial order, FEM)', ''),
    Remeshing('MatRemeshing', '', 'Adaptive Remeshing (recreate mesh in selected region)', ''),
    RhRefinement('MatRhRefinement', '', 'rh-type Refinement (combine h- and r- refinement)', ''),
    Automatic('MatAutomatic', '', 'Automatic Adaptation Based on Error Estimation', ''),
    Custom('MatCustom', '', 'Custom/Manual Mesh Adaptation Strategy', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MeshAdaptationType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MeshAdaptationType fromId(final String id) {
        if (id == null) return null
        for (MeshAdaptationType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MeshAdaptationType fromCode(final String code) {
        if (code == null) return null
        for (MeshAdaptationType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
