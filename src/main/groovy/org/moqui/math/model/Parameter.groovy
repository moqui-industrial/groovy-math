/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Parameter
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['parameterId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class Parameter implements Serializable {
    private static final long serialVersionUID = 1L

    /** parameterId */
    String parameterId

    /** parameterDefId */
    String parameterDefId

    /** parameterAlias */
    String parameterAlias

    /** sequenceNum */
    Long sequenceNum

    /** parameterUomId */
    String parameterUomId

    /** numericValue */
    BigDecimal numericValue

    /** symbolicValue */
    String symbolicValue

    /** parameterEnumId */
    String parameterEnumId

    /** textValue */
    String textValue

    /** mathModelId */
    String mathModelId

    /** categoryId */
    String categoryId

    /** categoryObjectId */
    String categoryObjectId

    /** morphismId */
    String morphismId

    /** functorId */
    String functorId

    /** graphId */
    String graphId

    /** graphVertexId */
    String graphVertexId

    /** graphEdgeId */
    String graphEdgeId

    /** meshKCellId */
    String meshKCellId

    /** deviceId */
    String deviceId

    /** deviceConfigId */
    String deviceConfigId

    ParameterDef parameterDef

    MathModel model

    Category category

    CategoryObject categoryObject

    Morphism morphism

    Functor functor

    Graph graph

    GraphVertex graphVertex

    GraphEdge graphEdge

    MeshKCell cell

    Parameter() {}

    Parameter(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('parameterId')) this.parameterId = args.get('parameterId')?.toString()
            if (args.containsKey('parameterDefId')) this.parameterDefId = args.get('parameterDefId')?.toString()
            if (args.containsKey('parameterAlias')) this.parameterAlias = args.get('parameterAlias')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('parameterUomId')) this.parameterUomId = args.get('parameterUomId')?.toString()
            if (args.containsKey('numericValue')) this.numericValue = args.get('numericValue') != null ? (args.get('numericValue') instanceof BigDecimal ? (BigDecimal) args.get('numericValue') : new BigDecimal(args.get('numericValue').toString())) : null
            if (args.containsKey('symbolicValue')) this.symbolicValue = args.get('symbolicValue')?.toString()
            if (args.containsKey('parameterEnumId')) this.parameterEnumId = args.get('parameterEnumId')?.toString()
            if (args.containsKey('textValue')) this.textValue = args.get('textValue')?.toString()
            if (args.containsKey('mathModelId')) this.mathModelId = args.get('mathModelId')?.toString()
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId')?.toString()
            if (args.containsKey('categoryObjectId')) this.categoryObjectId = args.get('categoryObjectId')?.toString()
            if (args.containsKey('morphismId')) this.morphismId = args.get('morphismId')?.toString()
            if (args.containsKey('functorId')) this.functorId = args.get('functorId')?.toString()
            if (args.containsKey('graphId')) this.graphId = args.get('graphId')?.toString()
            if (args.containsKey('graphVertexId')) this.graphVertexId = args.get('graphVertexId')?.toString()
            if (args.containsKey('graphEdgeId')) this.graphEdgeId = args.get('graphEdgeId')?.toString()
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId')?.toString()
            if (args.containsKey('deviceId')) this.deviceId = args.get('deviceId')?.toString()
            if (args.containsKey('deviceConfigId')) this.deviceConfigId = args.get('deviceConfigId')?.toString()
        }
    }

    Parameter parameterId(String value) {
        this.parameterId = value
        return this;
    }

    Parameter parameterDefId(String value) {
        this.parameterDefId = value
        return this;
    }

    Parameter parameterAlias(String value) {
        this.parameterAlias = value
        return this;
    }

    Parameter sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    Parameter parameterUomId(String value) {
        this.parameterUomId = value
        return this;
    }

    Parameter numericValue(BigDecimal value) {
        this.numericValue = value
        return this;
    }

    Parameter symbolicValue(String value) {
        this.symbolicValue = value
        return this;
    }

    Parameter parameterEnumId(String value) {
        this.parameterEnumId = value
        return this;
    }

    Parameter textValue(String value) {
        this.textValue = value
        return this;
    }

    Parameter mathModelId(String value) {
        this.mathModelId = value
        return this;
    }

    Parameter categoryId(String value) {
        this.categoryId = value
        return this;
    }

    Parameter categoryObjectId(String value) {
        this.categoryObjectId = value
        return this;
    }

    Parameter morphismId(String value) {
        this.morphismId = value
        return this;
    }

    Parameter functorId(String value) {
        this.functorId = value
        return this;
    }

    Parameter graphId(String value) {
        this.graphId = value
        return this;
    }

    Parameter graphVertexId(String value) {
        this.graphVertexId = value
        return this;
    }

    Parameter graphEdgeId(String value) {
        this.graphEdgeId = value
        return this;
    }

    Parameter meshKCellId(String value) {
        this.meshKCellId = value
        return this;
    }

    Parameter deviceId(String value) {
        this.deviceId = value
        return this;
    }

    Parameter deviceConfigId(String value) {
        this.deviceConfigId = value
        return this;
    }

    Parameter parameterDef(ParameterDef item) {
        this.parameterDef = item;
        return this;
    }

    Parameter model(MathModel item) {
        this.model = item;
        return this;
    }

    Parameter category(Category item) {
        this.category = item;
        return this;
    }

    Parameter categoryObject(CategoryObject item) {
        this.categoryObject = item;
        return this;
    }

    Parameter morphism(Morphism item) {
        this.morphism = item;
        return this;
    }

    Parameter functor(Functor item) {
        this.functor = item;
        return this;
    }

    Parameter graph(Graph item) {
        this.graph = item;
        return this;
    }

    Parameter graphVertex(GraphVertex item) {
        this.graphVertex = item;
        return this;
    }

    Parameter graphEdge(GraphEdge item) {
        this.graphEdge = item;
        return this;
    }

    Parameter cell(MeshKCell item) {
        this.cell = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.parameterId != null) map.put('parameterId', this.parameterId);
        if (this.parameterDefId != null) map.put('parameterDefId', this.parameterDefId);
        if (this.parameterAlias != null) map.put('parameterAlias', this.parameterAlias);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.parameterUomId != null) map.put('parameterUomId', this.parameterUomId);
        if (this.numericValue != null) map.put('numericValue', this.numericValue);
        if (this.symbolicValue != null) map.put('symbolicValue', this.symbolicValue);
        if (this.parameterEnumId != null) map.put('parameterEnumId', this.parameterEnumId);
        if (this.textValue != null) map.put('textValue', this.textValue);
        if (this.mathModelId != null) map.put('mathModelId', this.mathModelId);
        if (this.categoryId != null) map.put('categoryId', this.categoryId);
        if (this.categoryObjectId != null) map.put('categoryObjectId', this.categoryObjectId);
        if (this.morphismId != null) map.put('morphismId', this.morphismId);
        if (this.functorId != null) map.put('functorId', this.functorId);
        if (this.graphId != null) map.put('graphId', this.graphId);
        if (this.graphVertexId != null) map.put('graphVertexId', this.graphVertexId);
        if (this.graphEdgeId != null) map.put('graphEdgeId', this.graphEdgeId);
        if (this.meshKCellId != null) map.put('meshKCellId', this.meshKCellId);
        if (this.deviceId != null) map.put('deviceId', this.deviceId);
        if (this.deviceConfigId != null) map.put('deviceConfigId', this.deviceConfigId);
        return map;
    }
}