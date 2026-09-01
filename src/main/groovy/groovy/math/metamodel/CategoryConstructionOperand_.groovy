/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.CategoryConstructionOperand
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.CategoryConstructionOperand

@CompileStatic
class CategoryConstructionOperand_ {
    public static final String ENTITY_NAME = 'CategoryConstructionOperand'
    public static final String FULL_NAME = 'moqui.math.ct.CategoryConstructionOperand'

    public static final Attribute<CategoryConstructionOperand, String> categoryConstructionId = new Attribute<>('categoryConstructionId', CategoryConstructionOperand.class, String.class, true, true)
    public static final Attribute<CategoryConstructionOperand, Long> sequenceNum = new Attribute<>('sequenceNum', CategoryConstructionOperand.class, Long.class, true, true)
    public static final Attribute<CategoryConstructionOperand, String> operandCategoryId = new Attribute<>('operandCategoryId', CategoryConstructionOperand.class, String.class, false, false)
    public static final Attribute<CategoryConstructionOperand, String> operandObjectId = new Attribute<>('operandObjectId', CategoryConstructionOperand.class, String.class, false, false)
    public static final Attribute<CategoryConstructionOperand, String> operandMorphismId = new Attribute<>('operandMorphismId', CategoryConstructionOperand.class, String.class, false, false)
    public static final Attribute<CategoryConstructionOperand, String> operandFunctorId = new Attribute<>('operandFunctorId', CategoryConstructionOperand.class, String.class, false, false)
    public static final Attribute<CategoryConstructionOperand, String> operandGraphId = new Attribute<>('operandGraphId', CategoryConstructionOperand.class, String.class, false, false)
}
