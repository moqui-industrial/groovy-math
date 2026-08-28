/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphCategory
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
@EqualsAndHashCode(includes = ['graphCategoryId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class GraphCategory implements Serializable {
    private static final long serialVersionUID = 1L

    String graphCategoryId
    String graphId // Required
    String categoryId // Required
    String purposeEnumId // Required

    // --- Relationships (In-Memory Navigation) ---
    Graph graph
    Category category
    Object purpose
    List<GraphVertexCategoryObject> vertexObjectMappings = []
    List<GraphEdgeMorphism> edgeMorphismMappings = []

    GraphCategory() { }

    GraphCategory(String graphCategoryId) {
        this.graphCategoryId = Objects.requireNonNull(graphCategoryId, "GraphCategory.graphCategoryId cannot be null")
    }

    GraphCategory(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('graphCategoryId')) this.graphCategoryId = args.get('graphCategoryId') as String
            if (args.containsKey('graphId')) this.graphId = args.get('graphId') as String
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('graph')) this.graph = args.get('graph') as Graph
            if (args.containsKey('category')) this.category = args.get('category') as Category
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('vertexObjectMappings')) this.vertexObjectMappings = args.get('vertexObjectMappings') as List<GraphVertexCategoryObject>
            if (args.containsKey('edgeMorphismMappings')) this.edgeMorphismMappings = args.get('edgeMorphismMappings') as List<GraphEdgeMorphism>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.graphId == null) throw new IllegalStateException("Required property missing: GraphCategory.graphId")
        if (this.categoryId == null) throw new IllegalStateException("Required property missing: GraphCategory.categoryId")
        if (this.purposeEnumId == null) throw new IllegalStateException("Required property missing: GraphCategory.purposeEnumId")
    }

    /**
     * Gradle-style closure configurator
     */
    GraphCategory configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphCategory) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Graph graph(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Graph) Closure<?> action) {
        if (this.graph == null) this.graph = new Graph()
        this.graph.configure(action)
        this.graph
    }

    Category category(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Category) Closure<?> action) {
        if (this.category == null) this.category = new Category()
        this.category.configure(action)
        this.category
    }

    GraphVertexCategoryObject vertexObjectMappings(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphVertexCategoryObject) Closure<?> action) {
        GraphVertexCategoryObject item = new GraphVertexCategoryObject()
        item.configure(action)
        if (this.vertexObjectMappings == null) this.vertexObjectMappings = []
        this.vertexObjectMappings.add(item)
        item
    }

    GraphEdgeMorphism edgeMorphismMappings(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphEdgeMorphism) Closure<?> action) {
        GraphEdgeMorphism item = new GraphEdgeMorphism()
        item.configure(action)
        if (this.edgeMorphismMappings == null) this.edgeMorphismMappings = []
        this.edgeMorphismMappings.add(item)
        item
    }
}
