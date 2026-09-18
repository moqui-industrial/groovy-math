/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: UniversalConstructionType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum UniversalConstructionType implements DslEnumValue {
    Initial('UcInitial', '', 'Initial object', ''),
    Terminal('UcTerminal', '', 'Terminal object', ''),
    Product('UcProduct', '', 'Product', ''),
    Coproduct('UcCoproduct', '', 'Coproduct', ''),
    Equalizer('UcEqualizer', '', 'Equalizer', ''),
    Coequalizer('UcCoequalizer', '', 'Coequalizer', ''),
    Pullback('UcPullback', '', 'Pullback', ''),
    Pushout('UcPushout', '', 'Pushout', ''),
    Limit('UcLimit', '', 'Limit', ''),
    Colimit('UcColimit', '', 'Colimit', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    UniversalConstructionType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static UniversalConstructionType fromId(final String id) {
        if (id == null) return null
        for (UniversalConstructionType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static UniversalConstructionType fromCode(final String code) {
        if (code == null) return null
        for (UniversalConstructionType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
