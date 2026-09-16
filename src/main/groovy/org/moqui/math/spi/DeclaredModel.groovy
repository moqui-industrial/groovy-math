/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.spi

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.ModelValue

/**
 * The declared facts a backend is allowed to select on, resolved once.
 *
 * <p>The reason this type exists: modelTypeEnumId is a field of MathModelDef, not of MathModel.
 * The dispatcher used to read it straight off the MathModel, where it does not exist, so every
 * selection rule that depended on the model type silently never fired and the models fell
 * through to the general-purpose backend. Resolving the relationship in one place makes that
 * mistake impossible to repeat in each factory.
 */
@CompileStatic
final class DeclaredModel {
    final String mathModelId
    /** MathModel.solvingMethodEnumId, or null. */
    final String solvingMethod
    /** MathModelDef.modelTypeEnumId reached through MathModel.mathModelDefId, or null. */
    final String modelType
    /** The MathModel itself, for selection rules that need more than the two enums above. */
    final ModelValue value

    private DeclaredModel(final String mathModelId, final String solvingMethod,
                          final String modelType, final ModelValue value) {
        this.mathModelId = mathModelId
        this.solvingMethod = solvingMethod
        this.modelType = modelType
        this.value = value
    }

    static DeclaredModel of(final MathMeta mathMeta, final ModelValue model) {
        Objects.requireNonNull(model, 'MathModel value must not be null')
        String definitionId = model.get('mathModelDefId') as String
        ModelValue definition = definitionId == null ? null :
            mathMeta.entity('MathModelDef').findByName(definitionId)
        String solvingMethod = null
        if (definitionId != null && mathMeta.hasEntity('MathModelDefPipeline')) {
            for (ModelValue step : mathMeta.entity('MathModelDefPipeline')) {
                if (step.get('mathModelDefId') == definitionId && step.get('solvingMethodEnumId') != null) {
                    solvingMethod = step.get('solvingMethodEnumId') as String
                    break
                }
            }
        }
        if (solvingMethod == null) {
            solvingMethod = model.get('solvingMethodEnumId') as String
        }
        new DeclaredModel(
            model.get('mathModelId') as String,
            solvingMethod,
            definition?.get('modelTypeEnumId') as String,
            model)
    }

    @Override
    String toString() {
        "DeclaredModel[${mathModelId}, solvingMethod=${solvingMethod}, modelType=${modelType}]"
    }
}
