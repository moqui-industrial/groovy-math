/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.NaturalTransformationComposition
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['natTransfCompositionId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class NaturalTransformationComposition implements Serializable {
    private static final long serialVersionUID = 1L

    String natTransfCompositionId
    String compositionTypeEnumId
    String resultNatTransfId // Required
    String operandNatTransfId // Required
    Long operandIndex // Required

    // --- Relationships (In-Memory Navigation) ---
    NaturalTransformation resultNatTransf
    NaturalTransformation operandNatTransf

    NaturalTransformationComposition() { }

    NaturalTransformationComposition(String natTransfCompositionId) {
        this.natTransfCompositionId = Objects.requireNonNull(natTransfCompositionId, "NaturalTransformationComposition.natTransfCompositionId cannot be null")
    }

    NaturalTransformationComposition(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('natTransfCompositionId')) this.natTransfCompositionId = args.get('natTransfCompositionId') as String
            if (args.containsKey('compositionTypeEnumId')) this.compositionTypeEnumId = args.get('compositionTypeEnumId') as String
            if (args.containsKey('resultNatTransfId')) this.resultNatTransfId = args.get('resultNatTransfId') as String
            if (args.containsKey('operandNatTransfId')) this.operandNatTransfId = args.get('operandNatTransfId') as String
            if (args.containsKey('operandIndex')) this.operandIndex = args.get('operandIndex') as Long
            if (args.containsKey('resultNatTransf')) this.resultNatTransf = args.get('resultNatTransf') as NaturalTransformation
            if (args.containsKey('operandNatTransf')) this.operandNatTransf = args.get('operandNatTransf') as NaturalTransformation
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.resultNatTransfId == null) throw new IllegalStateException("Required property missing: NaturalTransformationComposition.resultNatTransfId")
        if (this.operandNatTransfId == null) throw new IllegalStateException("Required property missing: NaturalTransformationComposition.operandNatTransfId")
        if (this.operandIndex == null) throw new IllegalStateException("Required property missing: NaturalTransformationComposition.operandIndex")
    }

    /**
     * Gradle-style closure configurator
     */
    NaturalTransformationComposition configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = NaturalTransformationComposition) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    NaturalTransformation resultNatTransf(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = NaturalTransformation) Closure<?> action) {
        if (this.resultNatTransf == null) this.resultNatTransf = new NaturalTransformation()
        this.resultNatTransf.configure(action)
        this.resultNatTransf
    }

    NaturalTransformation operandNatTransf(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = NaturalTransformation) Closure<?> action) {
        if (this.operandNatTransf == null) this.operandNatTransf = new NaturalTransformation()
        this.operandNatTransf.configure(action)
        this.operandNatTransf
    }
}
