/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshMaterialType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshMaterialType implements DslEnumValue {
    mtLambert('MmtLambert', '', 'Lambert Material', ''),
    mtStandard('MmtStandard', '', 'Standard (PBR) Material', ''),
    mtPhong('MmtPhong', '', 'Phong Material', ''),
    mtToon('MmtToon', '', 'Toon Material', ''),
    mtShader('MmtShader', '', 'Custom ShaderMaterial', ''),
    atSteel('MatSteel', '', 'Steel', 'MmtStandard'),
    atAluminum('MatAluminum', '', 'Aluminum', 'MmtStandard'),
    atGlass('MatGlass', '', 'Glass', 'MmtStandard'),
    atWood('MatWood', '', 'Wood', 'MmtStandard'),
    atConcrete('MatConcrete', '', 'Concrete', 'MmtStandard'),
    atPlastic('MatPlastic', '', 'Plastic', 'MmtStandard'),
    atRubber('MatRubber', '', 'Rubber', 'MmtStandard'),
    atCopper('MatCopper', '', 'Copper', 'MmtStandard'),
    atTitanium('MatTitanium', '', 'Titanium', 'MmtStandard'),
    atCarbonFiber('MatCarbonFiber', '', 'Carbon fiber', 'MmtStandard');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MeshMaterialType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MeshMaterialType fromId(final String id) {
        if (id == null) return null
        for (MeshMaterialType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MeshMaterialType fromCode(final String code) {
        if (code == null) return null
        for (MeshMaterialType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
