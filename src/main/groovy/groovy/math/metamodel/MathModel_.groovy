/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MathModel
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MathModel

@CompileStatic
class MathModel_ {
    public static final String ENTITY_NAME = 'MathModel'
    public static final String FULL_NAME = 'moqui.math.MathModel'

    public static final Attribute<MathModel, String> mathModelId = new Attribute<>('mathModelId', MathModel.class, String.class, true, true)
    public static final Attribute<MathModel, String> mathModelDefId = new Attribute<>('mathModelDefId', MathModel.class, String.class, false, true)
    public static final Attribute<MathModel, String> graphId = new Attribute<>('graphId', MathModel.class, String.class, false, false)
    public static final Attribute<MathModel, String> meshId = new Attribute<>('meshId', MathModel.class, String.class, false, false)
    public static final Attribute<MathModel, String> solvingMethodEnumId = new Attribute<>('solvingMethodEnumId', MathModel.class, String.class, false, false)
    public static final Attribute<MathModel, String> interpolationEnumId = new Attribute<>('interpolationEnumId', MathModel.class, String.class, false, false)
    public static final Attribute<MathModel, String> basisFunctionEnumId = new Attribute<>('basisFunctionEnumId', MathModel.class, String.class, false, false)
    public static final Attribute<MathModel, Long> basisOrder = new Attribute<>('basisOrder', MathModel.class, Long.class, false, false)
    public static final Attribute<MathModel, String> sourceEnumId = new Attribute<>('sourceEnumId', MathModel.class, String.class, false, false)
    public static final Attribute<MathModel, String> modelAlias = new Attribute<>('modelAlias', MathModel.class, String.class, false, false)
    public static final Attribute<MathModel, String> description = new Attribute<>('description', MathModel.class, String.class, false, false)
    public static final Attribute<MathModel, String> statusId = new Attribute<>('statusId', MathModel.class, String.class, false, true)
    public static final Attribute<MathModel, String> statusFlowId = new Attribute<>('statusFlowId', MathModel.class, String.class, false, false)
}
