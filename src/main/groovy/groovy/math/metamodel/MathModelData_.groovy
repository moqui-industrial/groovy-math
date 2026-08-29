/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MathModelData
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MathModelData

@CompileStatic
class MathModelData_ {
    public static final String ENTITY_NAME = 'MathModelData'
    public static final String FULL_NAME = 'moqui.math.MathModelData'

    public static final Attribute<MathModelData, String> mathModelDataId = new Attribute<>('mathModelDataId', MathModelData.class, String.class, true, true)
    public static final Attribute<MathModelData, String> mathModelId = new Attribute<>('mathModelId', MathModelData.class, String.class, false, true)
    public static final Attribute<MathModelData, String> dataTypeEnumId = new Attribute<>('dataTypeEnumId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, String> purposeEnumId = new Attribute<>('purposeEnumId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, String> generatedByRunId = new Attribute<>('generatedByRunId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, String> vectorId = new Attribute<>('vectorId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, String> matrixId = new Attribute<>('matrixId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, String> tensorId = new Attribute<>('tensorId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, String> transformationId = new Attribute<>('transformationId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, String> graphVertexId = new Attribute<>('graphVertexId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, String> graphEdgeId = new Attribute<>('graphEdgeId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, String> meshKCellId = new Attribute<>('meshKCellId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, String> meshGroupId = new Attribute<>('meshGroupId', MathModelData.class, String.class, false, false)
    public static final Attribute<MathModelData, java.sql.Timestamp> fromDate = new Attribute<>('fromDate', MathModelData.class, java.sql.Timestamp.class, false, true)
    public static final Attribute<MathModelData, java.sql.Timestamp> thruDate = new Attribute<>('thruDate', MathModelData.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<MathModelData, Long> sequenceNum = new Attribute<>('sequenceNum', MathModelData.class, Long.class, false, false)
    public static final Attribute<MathModelData, String> uomId = new Attribute<>('uomId', MathModelData.class, String.class, false, false)
}
