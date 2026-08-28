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
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['parameterId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class Parameter implements Serializable {
    private static final long serialVersionUID = 1L

    String parameterId
    String parameterDefId // Required
    String parameterAlias
    Long sequenceNum
    String parameterUomId
    BigDecimal numericValue
    String symbolicValue
    String parameterEnumId
    String mathModelId
    String morphismId
    String graphId
    String graphVertexId
    String graphEdgeId
    String textValue
    String meshKCellId
    String materialEnumId
    String shaderProfileEnumId

    // --- Relationships (In-Memory Navigation) ---
    ParameterDef parameterDef
    Object uom
    MathModel model
    Morphism morphism
    Graph graph
    GraphVertex graphVertex
    GraphEdge graphEdge
    MeshKCell cell

    Parameter() { }

    Parameter(String parameterId) {
        this.parameterId = Objects.requireNonNull(parameterId, "Parameter.parameterId cannot be null")
    }

    Parameter(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('parameterId')) this.parameterId = args.get('parameterId') as String
            if (args.containsKey('parameterDefId')) this.parameterDefId = args.get('parameterDefId') as String
            if (args.containsKey('parameterAlias')) this.parameterAlias = args.get('parameterAlias') as String
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') as Long
            if (args.containsKey('parameterUomId')) this.parameterUomId = args.get('parameterUomId') as String
            if (args.containsKey('numericValue')) this.numericValue = args.get('numericValue') as BigDecimal
            if (args.containsKey('symbolicValue')) this.symbolicValue = args.get('symbolicValue') as String
            if (args.containsKey('parameterEnumId')) this.parameterEnumId = args.get('parameterEnumId') as String
            if (args.containsKey('mathModelId')) this.mathModelId = args.get('mathModelId') as String
            if (args.containsKey('morphismId')) this.morphismId = args.get('morphismId') as String
            if (args.containsKey('graphId')) this.graphId = args.get('graphId') as String
            if (args.containsKey('graphVertexId')) this.graphVertexId = args.get('graphVertexId') as String
            if (args.containsKey('graphEdgeId')) this.graphEdgeId = args.get('graphEdgeId') as String
            if (args.containsKey('textValue')) this.textValue = args.get('textValue') as String
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId') as String
            if (args.containsKey('materialEnumId')) this.materialEnumId = args.get('materialEnumId') as String
            if (args.containsKey('shaderProfileEnumId')) this.shaderProfileEnumId = args.get('shaderProfileEnumId') as String
            if (args.containsKey('parameterDef')) this.parameterDef = args.get('parameterDef') as ParameterDef
            if (args.containsKey('uom')) this.uom = args.get('uom') as Object
            if (args.containsKey('model')) this.model = args.get('model') as MathModel
            if (args.containsKey('morphism')) this.morphism = args.get('morphism') as Morphism
            if (args.containsKey('graph')) this.graph = args.get('graph') as Graph
            if (args.containsKey('graphVertex')) this.graphVertex = args.get('graphVertex') as GraphVertex
            if (args.containsKey('graphEdge')) this.graphEdge = args.get('graphEdge') as GraphEdge
            if (args.containsKey('cell')) this.cell = args.get('cell') as MeshKCell
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.parameterDefId == null) throw new IllegalStateException("Required property missing: Parameter.parameterDefId")
    }

    /**
     * Gradle-style closure configurator
     */
    Parameter configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Parameter) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    ParameterDef parameterDef(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParameterDef) Closure<?> action) {
        if (this.parameterDef == null) this.parameterDef = new ParameterDef()
        this.parameterDef.configure(action)
        this.parameterDef
    }

    MathModel model(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModel) Closure<?> action) {
        if (this.model == null) this.model = new MathModel()
        this.model.configure(action)
        this.model
    }

    Morphism morphism(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Morphism) Closure<?> action) {
        if (this.morphism == null) this.morphism = new Morphism()
        this.morphism.configure(action)
        this.morphism
    }

    Graph graph(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Graph) Closure<?> action) {
        if (this.graph == null) this.graph = new Graph()
        this.graph.configure(action)
        this.graph
    }

    GraphVertex graphVertex(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphVertex) Closure<?> action) {
        if (this.graphVertex == null) this.graphVertex = new GraphVertex()
        this.graphVertex.configure(action)
        this.graphVertex
    }

    GraphEdge graphEdge(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphEdge) Closure<?> action) {
        if (this.graphEdge == null) this.graphEdge = new GraphEdge()
        this.graphEdge.configure(action)
        this.graphEdge
    }

    MeshKCell cell(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCell) Closure<?> action) {
        if (this.cell == null) this.cell = new MeshKCell()
        this.cell.configure(action)
        this.cell
    }
}
