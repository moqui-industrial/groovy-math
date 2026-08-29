/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphEdgeMorphism
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
@EqualsAndHashCode(includes = ['graphEdgeMorphismId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class GraphEdgeMorphism implements Serializable {
    private static final long serialVersionUID = 1L

    /** graphEdgeMorphismId */
    String graphEdgeMorphismId

    /** graphCategoryId */
    String graphCategoryId

    /** graphEdgeId */
    String graphEdgeId

    /** morphismId */
    String morphismId

    GraphCategory graphCategory

    GraphEdge edge

    Morphism morphism

    GraphEdgeMorphism() {}

    GraphEdgeMorphism(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('graphEdgeMorphismId')) this.graphEdgeMorphismId = args.get('graphEdgeMorphismId')?.toString()
            if (args.containsKey('graphCategoryId')) this.graphCategoryId = args.get('graphCategoryId')?.toString()
            if (args.containsKey('graphEdgeId')) this.graphEdgeId = args.get('graphEdgeId')?.toString()
            if (args.containsKey('morphismId')) this.morphismId = args.get('morphismId')?.toString()
        }
    }

    GraphEdgeMorphism graphEdgeMorphismId(String value) {
        this.graphEdgeMorphismId = value
        return this;
    }

    GraphEdgeMorphism graphCategoryId(String value) {
        this.graphCategoryId = value
        return this;
    }

    GraphEdgeMorphism graphEdgeId(String value) {
        this.graphEdgeId = value
        return this;
    }

    GraphEdgeMorphism morphismId(String value) {
        this.morphismId = value
        return this;
    }

    GraphEdgeMorphism graphCategory(GraphCategory item) {
        this.graphCategory = item;
        return this;
    }

    GraphEdgeMorphism edge(GraphEdge item) {
        this.edge = item;
        return this;
    }

    GraphEdgeMorphism morphism(Morphism item) {
        this.morphism = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.graphEdgeMorphismId != null) map.put('graphEdgeMorphismId', this.graphEdgeMorphismId);
        if (this.graphCategoryId != null) map.put('graphCategoryId', this.graphCategoryId);
        if (this.graphEdgeId != null) map.put('graphEdgeId', this.graphEdgeId);
        if (this.morphismId != null) map.put('morphismId', this.morphismId);
        return map;
    }
}