/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModel
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['mathModelId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MathModel implements Serializable {
    private static final long serialVersionUID = 1L

    /** mathModelId */
    String mathModelId

    /** mathModelDefId */
    String mathModelDefId

    /** graphId */
    String graphId

    /** meshId */
    String meshId

    /** solvingMethodEnumId */
    String solvingMethodEnumId

    /** interpolationEnumId */
    String interpolationEnumId

    /** basisFunctionEnumId */
    String basisFunctionEnumId

    /** basisOrder */
    Long basisOrder

    /** sourceEnumId */
    String sourceEnumId

    /** modelAlias */
    String modelAlias

    /** description */
    String description

    /** statusId */
    String statusId

    /** statusFlowId */
    String statusFlowId

    MathModelDef modelDef

    Graph graph

    Mesh mesh

    List<MathModelData> data = new ArrayList<>()

    List<Parameter> parameters = new ArrayList<>()

    MathModel() {}

    MathModel(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('mathModelId')) this.mathModelId = args.get('mathModelId')?.toString()
            if (args.containsKey('mathModelDefId')) this.mathModelDefId = args.get('mathModelDefId')?.toString()
            if (args.containsKey('graphId')) this.graphId = args.get('graphId')?.toString()
            if (args.containsKey('meshId')) this.meshId = args.get('meshId')?.toString()
            if (args.containsKey('solvingMethodEnumId')) this.solvingMethodEnumId = args.get('solvingMethodEnumId')?.toString()
            if (args.containsKey('interpolationEnumId')) this.interpolationEnumId = args.get('interpolationEnumId')?.toString()
            if (args.containsKey('basisFunctionEnumId')) this.basisFunctionEnumId = args.get('basisFunctionEnumId')?.toString()
            if (args.containsKey('basisOrder')) this.basisOrder = args.get('basisOrder') != null ? ((Number) args.get('basisOrder')).longValue() : null
            if (args.containsKey('sourceEnumId')) this.sourceEnumId = args.get('sourceEnumId')?.toString()
            if (args.containsKey('modelAlias')) this.modelAlias = args.get('modelAlias')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('statusId')) this.statusId = args.get('statusId')?.toString()
            if (args.containsKey('statusFlowId')) this.statusFlowId = args.get('statusFlowId')?.toString()
        }
    }

    MathModel mathModelId(String value) {
        this.mathModelId = value
        return this;
    }

    MathModel mathModelDefId(String value) {
        this.mathModelDefId = value
        return this;
    }

    MathModel graphId(String value) {
        this.graphId = value
        return this;
    }

    MathModel meshId(String value) {
        this.meshId = value
        return this;
    }

    MathModel solvingMethodEnumId(String value) {
        this.solvingMethodEnumId = value
        return this;
    }

    MathModel interpolationEnumId(String value) {
        this.interpolationEnumId = value
        return this;
    }

    MathModel basisFunctionEnumId(String value) {
        this.basisFunctionEnumId = value
        return this;
    }

    MathModel basisOrder(Long value) {
        this.basisOrder = value
        return this;
    }

    MathModel sourceEnumId(String value) {
        this.sourceEnumId = value
        return this;
    }

    MathModel modelAlias(String value) {
        this.modelAlias = value
        return this;
    }

    MathModel description(String value) {
        this.description = value
        return this;
    }

    MathModel statusId(String value) {
        this.statusId = value
        return this;
    }

    MathModel statusFlowId(String value) {
        this.statusFlowId = value
        return this;
    }

    MathModel modelDef(MathModelDef item) {
        this.modelDef = item;
        return this;
    }

    MathModel graph(Graph item) {
        this.graph = item;
        return this;
    }

    MathModel mesh(Mesh item) {
        this.mesh = item;
        return this;
    }

    MathModel data(List<MathModelData> list) {
        this.data = list;
        return this;
    }

    MathModel parameters(List<Parameter> list) {
        this.parameters = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.mathModelId != null) map.put('mathModelId', this.mathModelId);
        if (this.mathModelDefId != null) map.put('mathModelDefId', this.mathModelDefId);
        if (this.graphId != null) map.put('graphId', this.graphId);
        if (this.meshId != null) map.put('meshId', this.meshId);
        if (this.solvingMethodEnumId != null) map.put('solvingMethodEnumId', this.solvingMethodEnumId);
        if (this.interpolationEnumId != null) map.put('interpolationEnumId', this.interpolationEnumId);
        if (this.basisFunctionEnumId != null) map.put('basisFunctionEnumId', this.basisFunctionEnumId);
        if (this.basisOrder != null) map.put('basisOrder', this.basisOrder);
        if (this.sourceEnumId != null) map.put('sourceEnumId', this.sourceEnumId);
        if (this.modelAlias != null) map.put('modelAlias', this.modelAlias);
        if (this.description != null) map.put('description', this.description);
        if (this.statusId != null) map.put('statusId', this.statusId);
        if (this.statusFlowId != null) map.put('statusFlowId', this.statusFlowId);
        return map;
    }
}