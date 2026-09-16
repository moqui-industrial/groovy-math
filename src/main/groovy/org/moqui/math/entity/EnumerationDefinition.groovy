/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.entity

import groovy.transform.CompileStatic

/**
 * One moqui.basic.Enumeration as declared in the schema's seed data.
 *
 * <p>Enumerations are global data, not entity-scoped: the seed-data block they happen to sit in
 * is a documentation convenience, so they are collected from every source into one catalogue.
 *
 * <p>{@code enumCode} is the human-facing name of a value; {@code enumId} is its key. Code that
 * reads well says {@code MatrixPurpose 'ORIGINAL'} rather than {@code purposeEnumId 'MpOriginal'},
 * but only the id is stored. Codes are unique within an enumeration type, never globally.
 */
@CompileStatic
final class EnumerationDefinition {
    final String enumId
    final String enumTypeId
    final String enumCode
    final String parentEnumId
    final String description

    EnumerationDefinition(final String enumId, final String enumTypeId, final String enumCode,
                          final String parentEnumId, final String description) {
        if (!enumId) throw new IllegalArgumentException('enumId must not be empty')
        this.enumId = enumId
        this.enumTypeId = enumTypeId
        this.enumCode = enumCode
        this.parentEnumId = parentEnumId
        this.description = description
    }

    @Override
    String toString() {
        enumCode ? "${enumId} (${enumTypeId}:${enumCode})" : "${enumId} (${enumTypeId})"
    }
}
