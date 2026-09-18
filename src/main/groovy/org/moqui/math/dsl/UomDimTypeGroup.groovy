/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: UomDimTypeGroup
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum UomDimTypeGroup implements DslEnumValue {
    Product('UdtgProduct', '', 'Product', ''),
    Party('UdtgParty', '', 'Party', ''),
    Person('UdtgPerson', '', 'Person', 'UdtgParty'),
    Org('UdtgOrg', '', 'Organization', 'UdtgParty');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    UomDimTypeGroup(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static UomDimTypeGroup fromId(final String id) {
        if (id == null) return null
        for (UomDimTypeGroup val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static UomDimTypeGroup fromCode(final String code) {
        if (code == null) return null
        for (UomDimTypeGroup val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
