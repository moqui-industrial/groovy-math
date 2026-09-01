/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.MorphismEquation
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MorphismEquation

@CompileStatic
class MorphismEquation_ {
    public static final String ENTITY_NAME = 'MorphismEquation'
    public static final String FULL_NAME = 'moqui.math.ct.MorphismEquation'

    public static final Attribute<MorphismEquation, String> morphismEquationId = new Attribute<>('morphismEquationId', MorphismEquation.class, String.class, true, true)
    public static final Attribute<MorphismEquation, String> categoryId = new Attribute<>('categoryId', MorphismEquation.class, String.class, false, true)
    public static final Attribute<MorphismEquation, String> categoryConstructionId = new Attribute<>('categoryConstructionId', MorphismEquation.class, String.class, false, false)
    public static final Attribute<MorphismEquation, String> leftMorphismId = new Attribute<>('leftMorphismId', MorphismEquation.class, String.class, false, true)
    public static final Attribute<MorphismEquation, String> rightMorphismId = new Attribute<>('rightMorphismId', MorphismEquation.class, String.class, false, true)
    public static final Attribute<MorphismEquation, String> description = new Attribute<>('description', MorphismEquation.class, String.class, false, false)
}
