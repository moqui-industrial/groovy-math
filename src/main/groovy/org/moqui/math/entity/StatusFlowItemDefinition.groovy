/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.entity

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@CompileStatic
@EqualsAndHashCode(includes = ['statusFlowId', 'statusId'])
@ToString(includePackage = false, includeNames = true)
final class StatusFlowItemDefinition implements Serializable {
    private static final long serialVersionUID = 1L

    final String statusFlowId
    final String statusId
    final String isInitial
    final Integer sequenceNum

    StatusFlowItemDefinition(final String statusFlowId,
                             final String statusId,
                             final String isInitial = null,
                             final Integer sequenceNum = null) {
        this.statusFlowId = statusFlowId
        this.statusId = statusId
        this.isInitial = isInitial
        this.sequenceNum = sequenceNum
    }
}
