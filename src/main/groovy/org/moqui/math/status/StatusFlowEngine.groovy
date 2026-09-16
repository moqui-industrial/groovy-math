/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.status

import groovy.transform.CompileStatic
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.entity.ModelValue
import org.moqui.math.entity.StatusDefinition
import org.moqui.math.entity.StatusTransitionDefinition

/**
 * Applies the status semantics of Moqui to declared models, without a Moqui runtime.
 *
 * <p>The rules are taken from the framework rather than invented. Two of them are easy to get
 * wrong from the outside:
 *
 * <ul>
 * <li>It is not statusFlowId that switches checking on. Moqui's EntityAutoServiceRunner.checkStatus
 *     validates any change to a statusId field, and it searches StatusFlowTransition by
 *     (statusId, toStatusId) <em>across every flow</em>. A move declared in any flow is a legal
 *     move. statusFlowId narrows things only for automatic advancement and for what a screen
 *     offers as the next step.</li>
 * <li>Setting a status where there was none is never checked: the framework only validates when
 *     an old status exists and differs. That is what makes an initial status assignable.</li>
 * </ul>
 *
 * <p>A record with no status at all runs no machine, exactly as WorkEffort allows. That is a
 * choice the model author makes by leaving the field empty, not something to be defaulted away.
 */
@CompileStatic
final class StatusFlowEngine {

    /** How much of a state machine a record has asked for. */
    enum Mode {
        /** No status: nothing is enforced, nothing advances. */
        NONE,
        /** A status but no flow: changes are checked against the declared transitions. */
        MANUAL,
        /** A status and a flow: as MANUAL, and the flow can also advance the record itself. */
        FLOW
    }

    final ModelDefinition definition
    private final Closure<Boolean> permissionCheck

    /**
     * @param permissionCheck decides whether the current actor holds a userPermissionId that a
     *        transition requires. Moqui asks its user facade; with no user facade there is no
     *        honest answer, so the default refuses permission-gated transitions rather than
     *        waving them through, and a caller that has an authorization model supplies its own.
     */
    StatusFlowEngine(final ModelDefinition definition, final Closure<Boolean> permissionCheck = null) {
        this.definition = Objects.requireNonNull(definition, 'Model definition must not be null')
        this.permissionCheck = permissionCheck
    }

    /** Which mode this record is in, from the fields it declares and the values it carries. */
    Mode modeOf(final ModelValue value) {
        Objects.requireNonNull(value, 'Model value must not be null')
        if (!value.definition.fields.containsKey('statusId')) return Mode.NONE
        if (value.get('statusId') == null) return Mode.NONE
        boolean hasFlowField = value.definition.fields.containsKey('statusFlowId')
        (hasFlowField && value.get('statusFlowId') != null) ? Mode.FLOW : Mode.MANUAL
    }

    /**
     * The moves declared out of a status. With no flow the search spans every flow, matching the
     * validity check; naming one narrows it the way automatic advancement does.
     */
    List<StatusTransitionDefinition> allowedTransitions(final String fromStatusId, final String statusFlowId = null) {
        definition.transitionsFrom(fromStatusId, statusFlowId)
            .findAll { StatusTransitionDefinition transition -> permitted(transition) }
    }

    /** Whether this move is declared and permitted. An unchanged status is always allowed. */
    boolean isTransitionAllowed(final String fromStatusId, final String toStatusId) {
        if (fromStatusId == null || fromStatusId == toStatusId) return true
        allowedTransitions(fromStatusId).any { StatusTransitionDefinition t -> t.toStatusId == toStatusId }
    }

    /** Refuses an undeclared move, naming what was declared instead. */
    void assertTransitionAllowed(final String context, final String fromStatusId, final String toStatusId) {
        if (toStatusId != null && definition.status(toStatusId) == null) {
            throw new StatusTransitionException(
                "${context}: '${toStatusId}' is not a declared StatusItem".toString())
        }
        if (isTransitionAllowed(fromStatusId, toStatusId)) return

        List<String> declared = definition.transitionsFrom(fromStatusId)*.toStatusId
        List<String> permittedTargets = allowedTransitions(fromStatusId)*.toStatusId
        String detail = declared.isEmpty() ? 'no transition is declared out of it' :
            (permittedTargets == declared ? "declared moves are ${declared.join(', ')}"
                : "declared moves are ${declared.join(', ')}, of which ${permittedTargets.join(', ') ?: 'none'} are permitted")
        throw new StatusTransitionException(
            "${context}: cannot move from '${fromStatusId}' to '${toStatusId}'; ${detail}".toString())
    }

    /**
     * The next status when the flow leaves no choice, or null.
     *
     * <p>Mirrors run#StatusFlow, which is scoped to the record's own flow. A status with several
     * declared moves is ambiguous without the conditions and events that only a Moqui runtime
     * evaluates, so this answers null rather than guessing; a terminal status answers null too,
     * and the two are told apart by {@link #isTerminal}.
     */
    String nextInFlow(final String statusFlowId, final String fromStatusId) {
        List<StatusTransitionDefinition> moves = allowedTransitions(fromStatusId, statusFlowId)
        moves.size() == 1 ? moves.first().toStatusId : null
    }

    /** Whether no move at all is declared out of this status within the flow. */
    boolean isTerminal(final String statusFlowId, final String fromStatusId) {
        definition.transitionsFrom(fromStatusId, statusFlowId).isEmpty()
    }

    /**
     * Moves a record to a status, honouring the mode it asked for.
     *
     * @return the status it was in before, or null when it had none
     */
    String transition(final ModelValue value, final String toStatusId) {
        Objects.requireNonNull(value, 'Model value must not be null')
        if (!value.definition.fields.containsKey('statusId')) {
            throw new StatusTransitionException(
                "${value.definition.fullName} has no statusId field and cannot hold a status".toString())
        }
        String fromStatusId = value.get('statusId') as String
        String context = "${value.definition.fullName}[${value.modelKey}]"
        assertTransitionAllowed(context, fromStatusId, toStatusId)
        value.put('statusId', toStatusId)
        fromStatusId
    }

    /** Advances a record one step, when its flow leaves exactly one move. Returns the new status. */
    String advance(final ModelValue value) {
        if (modeOf(value) != Mode.FLOW) {
            throw new StatusTransitionException(
                "${value.definition.fullName}[${value.modelKey}] declares no statusFlowId; " +
                    'automatic advancement needs one'.toString())
        }
        String statusFlowId = value.get('statusFlowId') as String
        String fromStatusId = value.get('statusId') as String
        String next = nextInFlow(statusFlowId, fromStatusId)
        if (next == null) {
            String reason = isTerminal(statusFlowId, fromStatusId) ? 'it is terminal in this flow'
                : "it declares several moves (${definition.transitionsFrom(fromStatusId, statusFlowId)*.toStatusId.join(', ')})"
            throw new StatusTransitionException(
                "${value.definition.fullName}[${value.modelKey}] cannot advance from '${fromStatusId}': ${reason}".toString())
        }
        transition(value, next)
        next
    }

    /** The declared statuses of a type, in sequence order. */
    List<StatusDefinition> statusesOfType(final String statusTypeId) {
        definition.statusesOfType(statusTypeId)
    }

    private boolean permitted(final StatusTransitionDefinition transition) {
        if (!transition.userPermissionId) return true
        permissionCheck != null && permissionCheck.call(transition.userPermissionId)
    }
}
