/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelSource
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelSource implements DslEnumValue {
    Measured('MmsMeasured', '', 'Measured / Experimental', ''),
    Simulated('MmsSimulated', '', 'Synthetic / Simulation', ''),
    Manual('MmsManual', '', 'Manually Defined', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelSource(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MathModelSource fromId(final String id) {
        if (id == null) return null
        for (MathModelSource val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelSource fromCode(final String code) {
        if (code == null) return null
        for (MathModelSource val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MathModelSource fromName(final String name) {
        if (name == null) return null
        for (MathModelSource val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('MeasuredExperimental'.equalsIgnoreCase(name)) return Measured
        if ('SyntheticSimulation'.equalsIgnoreCase(name)) return Simulated
        if ('ManuallyDefined'.equalsIgnoreCase(name)) return Manual
        null
    }
}
