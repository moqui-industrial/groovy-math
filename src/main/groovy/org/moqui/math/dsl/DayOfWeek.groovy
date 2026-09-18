/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: DayOfWeek
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum DayOfWeek implements DslEnumValue {
    Monday('DowMonday', 'MONDAY', 'Monday', ''),
    Tuesday('DowTuesday', 'TUESDAY', 'Tuesday', ''),
    Wednesday('DowWednesday', 'WEDNESDAY', 'Wednesday', ''),
    Thursday('DowThursday', 'THURSDAY', 'Thursday', ''),
    Friday('DowFriday', 'FRIDAY', 'Friday', ''),
    Saturday('DowSaturday', 'SATURDAY', 'Saturday', ''),
    Sunday('DowSunday', 'SUNDAY', 'Sunday', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    DayOfWeek(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static DayOfWeek fromId(final String id) {
        if (id == null) return null
        for (DayOfWeek val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static DayOfWeek fromCode(final String code) {
        if (code == null) return null
        for (DayOfWeek val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static DayOfWeek fromName(final String name) {
        if (name == null) return null
        for (DayOfWeek val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        null
    }
}
