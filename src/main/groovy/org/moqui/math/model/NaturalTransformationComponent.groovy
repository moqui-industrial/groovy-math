/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.NaturalTransformationComponent
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
@EqualsAndHashCode(includes = ['natTransfId', 'categoryObjectId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class NaturalTransformationComponent implements Serializable {
    private static final long serialVersionUID = 1L

    String natTransfId
    String categoryObjectId
    String componentMorphismId // Required

    // --- Relationships (In-Memory Navigation) ---
    NaturalTransformation natTransf
    CategoryObject categoryObject
    Morphism componentMorphism

    NaturalTransformationComponent() { }

    NaturalTransformationComponent(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('natTransfId')) this.natTransfId = args.get('natTransfId') as String
            if (args.containsKey('categoryObjectId')) this.categoryObjectId = args.get('categoryObjectId') as String
            if (args.containsKey('componentMorphismId')) this.componentMorphismId = args.get('componentMorphismId') as String
            if (args.containsKey('natTransf')) this.natTransf = args.get('natTransf') as NaturalTransformation
            if (args.containsKey('categoryObject')) this.categoryObject = args.get('categoryObject') as CategoryObject
            if (args.containsKey('componentMorphism')) this.componentMorphism = args.get('componentMorphism') as Morphism
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.componentMorphismId == null) throw new IllegalStateException("Required property missing: NaturalTransformationComponent.componentMorphismId")
    }

    /**
     * Gradle-style closure configurator
     */
    NaturalTransformationComponent configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = NaturalTransformationComponent) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    NaturalTransformation natTransf(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = NaturalTransformation) Closure<?> action) {
        if (this.natTransf == null) this.natTransf = new NaturalTransformation()
        this.natTransf.configure(action)
        this.natTransf
    }

    CategoryObject categoryObject(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CategoryObject) Closure<?> action) {
        if (this.categoryObject == null) this.categoryObject = new CategoryObject()
        this.categoryObject.configure(action)
        this.categoryObject
    }

    Morphism componentMorphism(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Morphism) Closure<?> action) {
        if (this.componentMorphism == null) this.componentMorphism = new Morphism()
        this.componentMorphism.configure(action)
        this.componentMorphism
    }
}
