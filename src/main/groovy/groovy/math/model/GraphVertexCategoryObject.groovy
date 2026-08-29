/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphVertexCategoryObject
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
@EqualsAndHashCode(includes = ['graphVertexCategoryObjectId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class GraphVertexCategoryObject implements Serializable {
    private static final long serialVersionUID = 1L

    /** graphVertexCategoryObjectId */
    String graphVertexCategoryObjectId

    /** graphCategoryId */
    String graphCategoryId

    /** graphVertexId */
    String graphVertexId

    /** categoryObjectId */
    String categoryObjectId

    GraphCategory graphCategory

    GraphVertex vertex

    CategoryObject categoryObject

    GraphVertexCategoryObject() {}

    GraphVertexCategoryObject(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('graphVertexCategoryObjectId')) this.graphVertexCategoryObjectId = args.get('graphVertexCategoryObjectId')?.toString()
            if (args.containsKey('graphCategoryId')) this.graphCategoryId = args.get('graphCategoryId')?.toString()
            if (args.containsKey('graphVertexId')) this.graphVertexId = args.get('graphVertexId')?.toString()
            if (args.containsKey('categoryObjectId')) this.categoryObjectId = args.get('categoryObjectId')?.toString()
        }
    }

    GraphVertexCategoryObject graphVertexCategoryObjectId(String value) {
        this.graphVertexCategoryObjectId = value
        return this;
    }

    GraphVertexCategoryObject graphCategoryId(String value) {
        this.graphCategoryId = value
        return this;
    }

    GraphVertexCategoryObject graphVertexId(String value) {
        this.graphVertexId = value
        return this;
    }

    GraphVertexCategoryObject categoryObjectId(String value) {
        this.categoryObjectId = value
        return this;
    }

    GraphVertexCategoryObject graphCategory(GraphCategory item) {
        this.graphCategory = item;
        return this;
    }

    GraphVertexCategoryObject vertex(GraphVertex item) {
        this.vertex = item;
        return this;
    }

    GraphVertexCategoryObject categoryObject(CategoryObject item) {
        this.categoryObject = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.graphVertexCategoryObjectId != null) map.put('graphVertexCategoryObjectId', this.graphVertexCategoryObjectId);
        if (this.graphCategoryId != null) map.put('graphCategoryId', this.graphCategoryId);
        if (this.graphVertexId != null) map.put('graphVertexId', this.graphVertexId);
        if (this.categoryObjectId != null) map.put('categoryObjectId', this.categoryObjectId);
        return map;
    }
}