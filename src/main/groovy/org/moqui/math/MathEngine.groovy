/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.ModelValue
import org.moqui.math.spi.MathProviderFactory
import org.moqui.math.spi.MathProviderRegistry

/**
 * Dispatches a declared MathModel to the backend its metadata selects.
 *
 * <p>Named MathEngine rather than Math: a class called Math in this package shadows
 * java.lang.Math for every other class in it, and callers were already importing it under
 * an alias to get around that.
 *
 * <p>This class names no backend. Which one runs a model is decided by the
 * MathProviderFactory implementations on the classpath, so adding a backend is adding a jar.
 */
@CompileStatic
final class MathEngine {
    private MathEngine() { }

    static Object execute(final MathMeta mathMeta, final String targetId = null,
                          @DelegatesTo(value = ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> request = null) {
        ExecutionRequest execution = new ExecutionRequest()
        if (request != null) execution.configure(request)

        Objects.requireNonNull(mathMeta, 'Math metadata must not be null').freeze()
        String effectiveId = targetId
        ModelValue model = effectiveId != null ? mathMeta.entity('MathModel').findByName(effectiveId) : null
        if (model == null && effectiveId == null) {
            List<ModelValue> models = []
            for (ModelValue m : mathMeta.entity('MathModel')) models.add(m)
            if (models.size() == 1) {
                model = models[0]
                effectiveId = model.get('mathModelId') as String
            }
        }

        if (model != null) {
            MathProviderFactory factory = MathProviderRegistry.select(mathMeta, model)
            if (factory == null) {
                throw new IllegalStateException(MathProviderRegistry.unclaimedMessage(mathMeta, model))
            }
            return factory.create(effectiveId).run(mathMeta, execution.inputs)
        }

        // Standalone Transformation or Plan execution!
        ModelValue transformation = effectiveId != null ? mathMeta.entity('Transformation').findByName(effectiveId) : null
        if (transformation == null && effectiveId == null) {
            for (ModelValue tf : mathMeta.entity('Transformation')) {
                transformation = tf
                effectiveId = tf.get('transformationId') as String
                break
            }
        }

        if (transformation != null || mathMeta.entity('Transformation').iterator().hasNext()) {
            String dispatchId = effectiveId ?: 'Plan'
            // Default to LibTorch provider for general linear algebra and tensor plans
            org.moqui.math.libtorch.LibTorchProvider provider = new org.moqui.math.libtorch.LibTorchProvider(dispatchId)
            return provider.run(mathMeta, execution.inputs)
        }

        String notFoundMsg = (effectiveId != null) ?
            "Unknown MathModel or Transformation '${effectiveId}'" :
            "No MathModel or Transformation found in MathMeta to execute"
        throw new IllegalArgumentException(notFoundMsg)
    }

    static Object execute(final MathMeta mathMeta,
                          @DelegatesTo(value = ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> request) {
        execute(mathMeta, null, request)
    }

    @CompileStatic
    public static final class ExecutionRequest {
        final LinkedHashMap<String, Object> inputs = new LinkedHashMap<>()

        void input(final String name, final Object value) {
            if (!name) throw new IllegalArgumentException('Input name must not be empty')
            inputs.put(name, value)
        }

        void configure(@DelegatesTo(value = ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure) {
            if (closure == null) return
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = this
            copy.call()
        }
    }
}
