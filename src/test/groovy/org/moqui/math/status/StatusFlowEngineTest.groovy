/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.status

import org.junit.jupiter.api.Test
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.entity.ModelValue
import org.moqui.math.entity.StatusDefinition
import org.moqui.math.entity.StatusTransitionDefinition
import org.moqui.math.moqui.MoquiSchemaInspector

import static org.junit.jupiter.api.Assertions.assertThrows

/**
 * The three modes a stateful record may be in, as WorkEffort has them: no state machine, a status
 * checked against the declared transitions, or that plus a flow that can advance on its own.
 */
class StatusFlowEngineTest {

    private static StatusFlowEngine engine() {
        new StatusFlowEngine(MoquiSchemaInspector.embedded())
    }

    @Test
    void readsTheDeclaredStatusesAndTransitions() {
        StatusFlowEngine flow = engine()

        // sequenceNum 0 is a real sequence, not an absent one: Draft must come first.
        assert flow.statusesOfType('MathModelDef')*.statusId == ['MathModelDraft', 'MathModelInReview',
            'MathModelTested', 'MathModelApproved', 'MathModelProduction', 'MathModelDeprecated',
            'MathModelRetired', 'MathModelCancelled']

        assert flow.allowedTransitions('MathModelDraft')*.toStatusId as Set ==
            ['MathModelInReview', 'MathModelTested', 'MathModelCancelled'] as Set
        assert flow.isTerminal('MathModelStatusFlow', 'MathModelRetired')
        assert !flow.isTerminal('MathModelStatusFlow', 'MathModelDraft')
    }

    @Test
    void aModelWithNoStatusRunsNoMachineAtAll() {
        ModelValue subject = statefulModel(null, null)
        assert engine().modeOf(subject) == StatusFlowEngine.Mode.NONE

        // Taking a first status is never checked: the framework validates only a change away
        // from an existing one, which is what makes an initial assignment possible.
        assert engine().transition(subject, 'MathModelDraft') == null
        assert subject.get('statusId') == 'MathModelDraft'
        assert engine().modeOf(subject) == StatusFlowEngine.Mode.MANUAL
    }

    @Test
    void aStatusWithoutAFlowIsStillCheckedAgainstDeclaredTransitions() {
        StatusFlowEngine flow = engine()
        ModelValue subject = statefulModel('MathModelDraft', null)
        assert flow.modeOf(subject) == StatusFlowEngine.Mode.MANUAL

        assert flow.transition(subject, 'MathModelInReview') == 'MathModelDraft'
        assert subject.get('statusId') == 'MathModelInReview'

        // Draft to Production is not declared, in any flow.
        StatusTransitionException refused = assertThrows(StatusTransitionException) {
            flow.transition(statefulModel('MathModelDraft', null), 'MathModelProduction')
        }
        assert refused.message.contains("cannot move from 'MathModelDraft' to 'MathModelProduction'")
        assert refused.message.contains('MathModelInReview')

        // A status that was never declared is refused before the transition search.
        assert assertThrows(StatusTransitionException) {
            flow.transition(statefulModel('MathModelDraft', null), 'MathModelInvented')
        }.message.contains('is not a declared StatusItem')

        // Setting the same status again is a no-op, as in Moqui.
        ModelValue unchanged = statefulModel('MathModelRetired', null)
        flow.transition(unchanged, 'MathModelRetired')
        assert unchanged.get('statusId') == 'MathModelRetired'
    }

    @Test
    void theModelLifecycleIsADecisionAtEveryStep() {
        StatusFlowEngine flow = engine()
        ModelValue subject = statefulModel('MathModelApproved', 'MathModelStatusFlow')
        assert flow.modeOf(subject) == StatusFlowEngine.Mode.FLOW

        // Not one status in MathModelStatusFlow declares a single move: every non-terminal one
        // is a choice, so nothing on this flow ever advances by itself. That is a property of
        // how the lifecycle was seeded, and worth pinning down rather than discovering later.
        ['MathModelDraft', 'MathModelInReview', 'MathModelTested', 'MathModelApproved',
         'MathModelProduction', 'MathModelDeprecated'].each { String statusId ->
            assert flow.definition.transitionsFrom(statusId, 'MathModelStatusFlow').size() > 1
            assert flow.nextInFlow('MathModelStatusFlow', statusId) == null
            assert !flow.isTerminal('MathModelStatusFlow', statusId)
        }

        assert assertThrows(StatusTransitionException) { flow.advance(subject) }
            .message.contains('declares several moves')

        // Retired is the end of the line, and reports a different reason for not advancing.
        assert flow.isTerminal('MathModelStatusFlow', 'MathModelRetired')
        assert assertThrows(StatusTransitionException) {
            flow.advance(statefulModel('MathModelRetired', 'MathModelStatusFlow'))
        }.message.contains('it is terminal in this flow')
    }

    @Test
    void aSingleDeclaredMoveAdvancesOnItsOwn() {
        StatusFlowEngine flow = engine()

        // The run lifecycle does have such a step: MmrQueued declares only MmrRunning.
        ModelValue run = statefulModel('MmrQueued', 'MathModelRun')
        assert flow.nextInFlow('MathModelRun', 'MmrQueued') == 'MmrRunning'
        assert flow.advance(run) == 'MmrRunning'
        assert run.get('statusId') == 'MmrRunning'

        // And once running, how it ends is a decision again.
        assert flow.nextInFlow('MathModelRun', 'MmrRunning') == null
        assert flow.allowedTransitions('MmrRunning', 'MathModelRun')*.toStatusId as Set ==
            ['MmrSucceeded', 'MmrFailed', 'MmrCancelled'] as Set
    }

    @Test
    void advancingNeedsAFlowToAdvanceAlong() {
        assert assertThrows(StatusTransitionException) {
            engine().advance(statefulModel('MathModelDraft', null))
        }.message.contains('declares no statusFlowId')
    }

    @Test
    void aTransitionNeedingAPermissionIsRefusedUntilSomethingCanGrantIt() {
        // Moqui counts a transition as valid only if the actor holds its userPermissionId. With
        // no user facade there is no honest answer, so the default refuses rather than waving it
        // through, and a caller with an authorization model supplies one.
        ModelDefinition guarded = new ModelDefinition()
        guarded.addStatus(new StatusDefinition('GateOpen', 'Gate', null, 0, 'Open'))
        guarded.addStatus(new StatusDefinition('GateShut', 'Gate', null, 1, 'Shut'))
        guarded.addStatusTransition(new StatusTransitionDefinition(
            'GateFlow', 'GateOpen', 'GateShut', 'Shut It', 1, 'GATE_ADMIN'))

        StatusFlowEngine noAuthority = new StatusFlowEngine(guarded)
        assert noAuthority.allowedTransitions('GateOpen').isEmpty()
        assert !noAuthority.isTransitionAllowed('GateOpen', 'GateShut')
        assert assertThrows(StatusTransitionException) {
            noAuthority.assertTransitionAllowed('gate', 'GateOpen', 'GateShut')
        }.message.contains('none are permitted')

        StatusFlowEngine granting = new StatusFlowEngine(guarded, { String permissionId ->
            permissionId == 'GATE_ADMIN'
        })
        assert granting.allowedTransitions('GateOpen')*.toStatusId == ['GateShut']
        assert granting.isTransitionAllowed('GateOpen', 'GateShut')
    }

    /** A MathModel carrying the given status fields, built through the real DSL. */
    private static ModelValue statefulModel(final String statusId, final String statusFlowId) {
        MathMeta mathMeta = MathDsl.math {
            MathModelDef('StatusDef', description: 'Status modes')
            Map<String, Object> values = [mathModelDefId: 'StatusDef']
            if (statusId != null) values.put('statusId', statusId)
            if (statusFlowId != null) values.put('statusFlowId', statusFlowId)
            MathModel('StatusModel', values)
        }.validate()
        mathMeta.entity('MathModel').findByName('StatusModel')
    }
}
