/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.Parameter
 * JPA Criteria-style Metamodel Descriptor
 */
package org.moqui.math.metamodel

import groovy.transform.CompileStatic
import org.moqui.math.model.Parameter

@CompileStatic
class Parameter_ {
    public static final String ENTITY_NAME = 'Parameter'
    public static final String FULL_NAME = 'moqui.math.Parameter'

    public static final Attribute<Parameter, String> parameterId = new Attribute<>('parameterId', Parameter.class, String.class, true, true)
    public static final Attribute<Parameter, String> parameterDefId = new Attribute<>('parameterDefId', Parameter.class, String.class, false, true)
    public static final Attribute<Parameter, String> parameterAlias = new Attribute<>('parameterAlias', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, Long> sequenceNum = new Attribute<>('sequenceNum', Parameter.class, Long.class, false, false)
    public static final Attribute<Parameter, String> parameterUomId = new Attribute<>('parameterUomId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, BigDecimal> numericValue = new Attribute<>('numericValue', Parameter.class, BigDecimal.class, false, false)
    public static final Attribute<Parameter, String> symbolicValue = new Attribute<>('symbolicValue', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> parameterEnumId = new Attribute<>('parameterEnumId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> textValue = new Attribute<>('textValue', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> mathModelId = new Attribute<>('mathModelId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> categoryId = new Attribute<>('categoryId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> categoryObjectId = new Attribute<>('categoryObjectId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> morphismId = new Attribute<>('morphismId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> functorId = new Attribute<>('functorId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> graphId = new Attribute<>('graphId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> graphVertexId = new Attribute<>('graphVertexId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> graphEdgeId = new Attribute<>('graphEdgeId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> meshKCellId = new Attribute<>('meshKCellId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> deviceId = new Attribute<>('deviceId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> deviceConfigId = new Attribute<>('deviceConfigId', Parameter.class, String.class, false, false)
}
