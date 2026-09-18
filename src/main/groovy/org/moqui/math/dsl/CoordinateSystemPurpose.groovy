/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: CoordinateSystemPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum CoordinateSystemPurpose implements DslEnumValue {
    Base('CspBase', 'BASE', 'Base Coordinate System', ''),
    Camera('CspCamera', 'CAMERA', 'Camera Coordinate System', ''),
    Machine('CspMachine', 'MACHINE', 'Machine Coordinate System', ''),
    MechanicalInterface('CspMechanicalInterface', 'MECHANICAL_INTERFACE', 'Mechanical Interface Coordinate System', ''),
    MobilePlatform('CspMobilePlatform', 'MOBILE_PLATFORM', 'Mobile Platform Coordinate System', ''),
    Object('CspObject', 'OBJECT', 'Object Coordinate System', ''),
    Task('CspTask', 'TASK', 'Task Coordinate System', ''),
    Tool('CspTool', 'TOOL', 'Tool Coordinate System', ''),
    World('CspWorld', 'WORLD', 'World Coordinate System', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    CoordinateSystemPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static CoordinateSystemPurpose fromId(final String id) {
        if (id == null) return null
        for (CoordinateSystemPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static CoordinateSystemPurpose fromCode(final String code) {
        if (code == null) return null
        for (CoordinateSystemPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
