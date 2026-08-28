/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModel
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['mathModelId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MathModel implements Serializable {
    private static final long serialVersionUID = 1L

    String mathModelId
    String mathModelDefId // Required
    String graphId
    String meshId
    String solvingMethodEnumId
    String interpolationEnumId
    String basisFunctionEnumId
    Long basisOrder
    String sourceEnumId
    String modelAlias
    String description
    String statusId // Required
    String statusFlowId

    // --- Relationships (In-Memory Navigation) ---
    MathModelDef modelDef
    Graph graph
    Mesh mesh
    Object solvingMethod
    Object interpolation
    Object basisFunc
    Object source
    Object status
    Object flow
    List<MathModelData> data = []
    List<Parameter> parameters = []

    MathModel() { }

    MathModel(String mathModelId) {
        this.mathModelId = Objects.requireNonNull(mathModelId, "MathModel.mathModelId cannot be null")
    }

    MathModel(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('mathModelId')) this.mathModelId = args.get('mathModelId') as String
            if (args.containsKey('mathModelDefId')) this.mathModelDefId = args.get('mathModelDefId') as String
            if (args.containsKey('graphId')) this.graphId = args.get('graphId') as String
            if (args.containsKey('meshId')) this.meshId = args.get('meshId') as String
            if (args.containsKey('solvingMethodEnumId')) this.solvingMethodEnumId = args.get('solvingMethodEnumId') as String
            if (args.containsKey('interpolationEnumId')) this.interpolationEnumId = args.get('interpolationEnumId') as String
            if (args.containsKey('basisFunctionEnumId')) this.basisFunctionEnumId = args.get('basisFunctionEnumId') as String
            if (args.containsKey('basisOrder')) this.basisOrder = args.get('basisOrder') as Long
            if (args.containsKey('sourceEnumId')) this.sourceEnumId = args.get('sourceEnumId') as String
            if (args.containsKey('modelAlias')) this.modelAlias = args.get('modelAlias') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('statusId')) this.statusId = args.get('statusId') as String
            if (args.containsKey('statusFlowId')) this.statusFlowId = args.get('statusFlowId') as String
            if (args.containsKey('modelDef')) this.modelDef = args.get('modelDef') as MathModelDef
            if (args.containsKey('graph')) this.graph = args.get('graph') as Graph
            if (args.containsKey('mesh')) this.mesh = args.get('mesh') as Mesh
            if (args.containsKey('solvingMethod')) this.solvingMethod = args.get('solvingMethod') as Object
            if (args.containsKey('interpolation')) this.interpolation = args.get('interpolation') as Object
            if (args.containsKey('basisFunc')) this.basisFunc = args.get('basisFunc') as Object
            if (args.containsKey('source')) this.source = args.get('source') as Object
            if (args.containsKey('status')) this.status = args.get('status') as Object
            if (args.containsKey('flow')) this.flow = args.get('flow') as Object
            if (args.containsKey('data')) this.data = args.get('data') as List<MathModelData>
            if (args.containsKey('parameters')) this.parameters = args.get('parameters') as List<Parameter>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.mathModelDefId == null) throw new IllegalStateException("Required property missing: MathModel.mathModelDefId")
        if (this.statusId == null) throw new IllegalStateException("Required property missing: MathModel.statusId")
    }

    /**
     * Gradle-style closure configurator
     */
    MathModel configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModel) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MathModelDef modelDef(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelDef) Closure<?> action) {
        if (this.modelDef == null) this.modelDef = new MathModelDef()
        this.modelDef.configure(action)
        this.modelDef
    }

    Graph graph(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Graph) Closure<?> action) {
        if (this.graph == null) this.graph = new Graph()
        this.graph.configure(action)
        this.graph
    }

    Mesh mesh(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Mesh) Closure<?> action) {
        if (this.mesh == null) this.mesh = new Mesh()
        this.mesh.configure(action)
        this.mesh
    }

    MathModelData data(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelData) Closure<?> action) {
        MathModelData item = new MathModelData()
        item.configure(action)
        if (this.data == null) this.data = []
        this.data.add(item)
        item
    }

    Parameter parameters(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Parameter) Closure<?> action) {
        Parameter item = new Parameter()
        item.configure(action)
        if (this.parameters == null) this.parameters = []
        this.parameters.add(item)
        item
    }
}
