/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.TransformationOperand
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.TransformationOperand

@CompileStatic
class TransformationOperand_ {
    public static final String ENTITY_NAME = 'TransformationOperand'
    public static final String FULL_NAME = 'moqui.math.TransformationOperand'

    public static final Attribute<TransformationOperand, String> transformationId = new Attribute<>('transformationId', TransformationOperand.class, String.class, true, true)
    public static final Attribute<TransformationOperand, Long> operandIndex = new Attribute<>('operandIndex', TransformationOperand.class, Long.class, true, true)
    public static final Attribute<TransformationOperand, String> operandTypeEnumId = new Attribute<>('operandTypeEnumId', TransformationOperand.class, String.class, false, false)
    public static final Attribute<TransformationOperand, String> operandVectorId = new Attribute<>('operandVectorId', TransformationOperand.class, String.class, false, false)
    public static final Attribute<TransformationOperand, String> operandMatrixId = new Attribute<>('operandMatrixId', TransformationOperand.class, String.class, false, false)
    public static final Attribute<TransformationOperand, String> operandTensorId = new Attribute<>('operandTensorId', TransformationOperand.class, String.class, false, false)
    public static final Attribute<TransformationOperand, String> operandEnumId = new Attribute<>('operandEnumId', TransformationOperand.class, String.class, false, false)
    public static final Attribute<TransformationOperand, String> operandTransformationId = new Attribute<>('operandTransformationId', TransformationOperand.class, String.class, false, false)
    public static final Attribute<TransformationOperand, String> operandParameterId = new Attribute<>('operandParameterId', TransformationOperand.class, String.class, false, false)
}
