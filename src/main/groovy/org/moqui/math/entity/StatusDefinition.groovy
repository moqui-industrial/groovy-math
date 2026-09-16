/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.entity

import groovy.transform.CompileStatic

/** One moqui.basic.StatusItem as declared in the schema's seed data. */
@CompileStatic
final class StatusDefinition {
    final String statusId
    final String statusTypeId
    final String statusCode
    final Integer sequenceNum
    final String description

    StatusDefinition(final String statusId, final String statusTypeId, final String statusCode,
                     final Integer sequenceNum, final String description) {
        if (!statusId) throw new IllegalArgumentException('statusId must not be empty')
        this.statusId = statusId
        this.statusTypeId = statusTypeId
        this.statusCode = statusCode
        this.sequenceNum = sequenceNum
        this.description = description
    }

    @Override
    String toString() { "${statusId} (${statusTypeId})" }
}
