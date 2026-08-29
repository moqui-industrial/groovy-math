/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphCategory
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
@EqualsAndHashCode(includes = ['graphCategoryId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class GraphCategory implements Serializable {
    private static final long serialVersionUID = 1L

    /** graphCategoryId */
    String graphCategoryId

    /** graphId */
    String graphId

    /** categoryId */
    String categoryId

    /** purposeEnumId */
    String purposeEnumId

    Graph graph

    Category category

    List<GraphVertexCategoryObject> vertexObjectMappings = new ArrayList<>()

    List<GraphEdgeMorphism> edgeMorphismMappings = new ArrayList<>()

    GraphCategory() {}

    GraphCategory(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('graphCategoryId')) this.graphCategoryId = args.get('graphCategoryId')?.toString()
            if (args.containsKey('graphId')) this.graphId = args.get('graphId')?.toString()
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
        }
    }

    GraphCategory graphCategoryId(String value) {
        this.graphCategoryId = value
        return this;
    }

    GraphCategory graphId(String value) {
        this.graphId = value
        return this;
    }

    GraphCategory categoryId(String value) {
        this.categoryId = value
        return this;
    }

    GraphCategory purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    GraphCategory graph(Graph item) {
        this.graph = item;
        return this;
    }

    GraphCategory category(Category item) {
        this.category = item;
        return this;
    }

    GraphCategory vertexObjectMappings(List<GraphVertexCategoryObject> list) {
        this.vertexObjectMappings = list;
        return this;
    }

    GraphCategory edgeMorphismMappings(List<GraphEdgeMorphism> list) {
        this.edgeMorphismMappings = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.graphCategoryId != null) map.put('graphCategoryId', this.graphCategoryId);
        if (this.graphId != null) map.put('graphId', this.graphId);
        if (this.categoryId != null) map.put('categoryId', this.categoryId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        return map;
    }
}