/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.entity

import groovy.transform.CompileStatic

/**
 * One moqui.basic.StatusFlowTransition: a move from one status to another, within a named flow.
 *
 * <p>The flow groups transitions but does not gate them. Moqui's own validity check queries by
 * (statusId, toStatusId) alone, so a move declared in any flow is a legal move; the flow narrows
 * things only for automatic advancement and for what a screen offers.
 */
@CompileStatic
final class StatusTransitionDefinition {
    final String statusFlowId
    final String statusId
    final String toStatusId
    final String transitionName
    final Integer transitionSequence
    final String userPermissionId

    StatusTransitionDefinition(final String statusFlowId, final String statusId, final String toStatusId,
                               final String transitionName, final Integer transitionSequence,
                               final String userPermissionId) {
        if (!statusId || !toStatusId) throw new IllegalArgumentException('statusId and toStatusId are required')
        this.statusFlowId = statusFlowId
        this.statusId = statusId
        this.toStatusId = toStatusId
        this.transitionName = transitionName
        this.transitionSequence = transitionSequence
        this.userPermissionId = userPermissionId
    }

    @Override
    String toString() { "${statusId} -> ${toStatusId} (${transitionName ?: statusFlowId})" }
}
