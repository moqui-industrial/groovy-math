/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshMaterialType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshMaterialType implements DslEnumValue {
    MmtLambert('MmtLambert', '', 'Lambert Material', ''),
    MmtStandard('MmtStandard', '', 'Standard (PBR) Material', ''),
    MmtPhong('MmtPhong', '', 'Phong Material', ''),
    MmtToon('MmtToon', '', 'Toon Material', ''),
    MmtShader('MmtShader', '', 'Custom ShaderMaterial', ''),
    MatSteel('MatSteel', '', 'Steel', 'MmtStandard'),
    MatAluminum('MatAluminum', '', 'Aluminum', 'MmtStandard'),
    MatGlass('MatGlass', '', 'Glass', 'MmtStandard'),
    MatWood('MatWood', '', 'Wood', 'MmtStandard'),
    MatConcrete('MatConcrete', '', 'Concrete', 'MmtStandard'),
    MatPlastic('MatPlastic', '', 'Plastic', 'MmtStandard'),
    MatRubber('MatRubber', '', 'Rubber', 'MmtStandard'),
    MatCopper('MatCopper', '', 'Copper', 'MmtStandard'),
    MatTitanium('MatTitanium', '', 'Titanium', 'MmtStandard'),
    MatCarbonFiber('MatCarbonFiber', '', 'Carbon fiber', 'MmtStandard');

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

    static MeshMaterialType fromName(final String name) {
        if (name == null) return null
        for (MeshMaterialType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('LambertMaterial'.equalsIgnoreCase(name)) return MmtLambert
        if ('StandardMaterial'.equalsIgnoreCase(name)) return MmtStandard
        if ('PhongMaterial'.equalsIgnoreCase(name)) return MmtPhong
        if ('ToonMaterial'.equalsIgnoreCase(name)) return MmtToon
        if ('CustomShadermaterial'.equalsIgnoreCase(name)) return MmtShader
        if ('Steel'.equalsIgnoreCase(name)) return MatSteel
        if ('Aluminum'.equalsIgnoreCase(name)) return MatAluminum
        if ('Glass'.equalsIgnoreCase(name)) return MatGlass
        if ('Wood'.equalsIgnoreCase(name)) return MatWood
        if ('Concrete'.equalsIgnoreCase(name)) return MatConcrete
        if ('Plastic'.equalsIgnoreCase(name)) return MatPlastic
        if ('Rubber'.equalsIgnoreCase(name)) return MatRubber
        if ('Copper'.equalsIgnoreCase(name)) return MatCopper
        if ('Titanium'.equalsIgnoreCase(name)) return MatTitanium
        if ('CarbonFiber'.equalsIgnoreCase(name)) return MatCarbonFiber
        null
    }
}
