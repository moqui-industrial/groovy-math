/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelDefContentPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelDefContentPurpose implements DslEnumValue {
    MainLogic('MmCnpMainLogic', 'MainLogic', 'Main Executable Logic', ''),
    HelperFunc('MmCnpHelperFunc', 'HelperFunc', 'Helper Fuction', ''),
    ModelDef('MmCnpModelDef', 'ModelDef', 'Model Definition', ''),
    SolverConfig('MmCnpSolverConfig', 'SolverConfig', 'Solver Configuration', ''),
    PreProcessingScript('MmCnpPreProcessingScript', 'PreProcessingScript', 'Pre-processing Script', ''),
    PostProcessingScript('MmCnpPostProcessingScript', 'PostProcessingScript', 'Post-processing Script', ''),
    SymDer('MmCnpSymDer', 'SymbolicDerivation', 'Symbolic Derivation', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelDefContentPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MathModelDefContentPurpose fromId(final String id) {
        if (id == null) return null
        for (MathModelDefContentPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelDefContentPurpose fromCode(final String code) {
        if (code == null) return null
        for (MathModelDefContentPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MathModelDefContentPurpose fromName(final String name) {
        if (name == null) return null
        for (MathModelDefContentPurpose val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('MainExecutableLogic'.equalsIgnoreCase(name)) return MainLogic
        if ('HelperFuction'.equalsIgnoreCase(name)) return HelperFunc
        if ('ModelDefinition'.equalsIgnoreCase(name)) return ModelDef
        if ('SolverConfiguration'.equalsIgnoreCase(name)) return SolverConfig
        if ('SymbolicDerivation'.equalsIgnoreCase(name)) return SymDer
        null
    }
}
