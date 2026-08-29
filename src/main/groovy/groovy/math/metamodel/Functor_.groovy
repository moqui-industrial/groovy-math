/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.Functor
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Functor

@CompileStatic
class Functor_ {
    public static final String ENTITY_NAME = 'Functor'
    public static final String FULL_NAME = 'moqui.math.ct.Functor'

    public static final Attribute<Functor, String> functorId = new Attribute<>('functorId', Functor.class, String.class, true, true)
    public static final Attribute<Functor, String> parentFunctorId = new Attribute<>('parentFunctorId', Functor.class, String.class, false, false)
    public static final Attribute<Functor, String> functorTypeEnumId = new Attribute<>('functorTypeEnumId', Functor.class, String.class, false, false)
    public static final Attribute<Functor, String> functorName = new Attribute<>('functorName', Functor.class, String.class, false, true)
    public static final Attribute<Functor, String> functorSymbol = new Attribute<>('functorSymbol', Functor.class, String.class, false, false)
    public static final Attribute<Functor, String> description = new Attribute<>('description', Functor.class, String.class, false, false)
    public static final Attribute<Functor, String> sourceCategoryId = new Attribute<>('sourceCategoryId', Functor.class, String.class, false, false)
    public static final Attribute<Functor, String> targetCategoryId = new Attribute<>('targetCategoryId', Functor.class, String.class, false, false)
}
