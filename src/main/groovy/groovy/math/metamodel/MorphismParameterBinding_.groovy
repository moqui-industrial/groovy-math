/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.MorphismParameterBinding
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MorphismParameterBinding

@CompileStatic
class MorphismParameterBinding_ {
    public static final String ENTITY_NAME = 'MorphismParameterBinding'
    public static final String FULL_NAME = 'moqui.math.ct.MorphismParameterBinding'

    public static final Attribute<MorphismParameterBinding, String> morphismId = new Attribute<>('morphismId', MorphismParameterBinding.class, String.class, true, true)
    public static final Attribute<MorphismParameterBinding, String> parameterName = new Attribute<>('parameterName', MorphismParameterBinding.class, String.class, true, true)
    public static final Attribute<MorphismParameterBinding, String> literalValue = new Attribute<>('literalValue', MorphismParameterBinding.class, String.class, false, false)
    public static final Attribute<MorphismParameterBinding, String> contextPath = new Attribute<>('contextPath', MorphismParameterBinding.class, String.class, false, false)
    public static final Attribute<MorphismParameterBinding, String> sourceMorphismId = new Attribute<>('sourceMorphismId', MorphismParameterBinding.class, String.class, false, false)
    public static final Attribute<MorphismParameterBinding, String> sourceParameterName = new Attribute<>('sourceParameterName', MorphismParameterBinding.class, String.class, false, false)
    public static final Attribute<MorphismParameterBinding, String> description = new Attribute<>('description', MorphismParameterBinding.class, String.class, false, false)
}
