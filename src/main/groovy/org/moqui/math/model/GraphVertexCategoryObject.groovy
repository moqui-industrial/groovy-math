/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphVertexCategoryObject
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
@EqualsAndHashCode(includes = ['graphVertexCategoryObjectId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class GraphVertexCategoryObject implements Serializable {
    private static final long serialVersionUID = 1L

    String graphVertexCategoryObjectId
    String graphCategoryId // Required
    String graphVertexId // Required
    String categoryObjectId // Required

    // --- Relationships (In-Memory Navigation) ---
    GraphCategory graphCategory
    GraphVertex vertex
    CategoryObject categoryObject

    GraphVertexCategoryObject() { }

    GraphVertexCategoryObject(String graphVertexCategoryObjectId) {
        this.graphVertexCategoryObjectId = Objects.requireNonNull(graphVertexCategoryObjectId, "GraphVertexCategoryObject.graphVertexCategoryObjectId cannot be null")
    }

    GraphVertexCategoryObject(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('graphVertexCategoryObjectId')) this.graphVertexCategoryObjectId = args.get('graphVertexCategoryObjectId') as String
            if (args.containsKey('graphCategoryId')) this.graphCategoryId = args.get('graphCategoryId') as String
            if (args.containsKey('graphVertexId')) this.graphVertexId = args.get('graphVertexId') as String
            if (args.containsKey('categoryObjectId')) this.categoryObjectId = args.get('categoryObjectId') as String
            if (args.containsKey('graphCategory')) this.graphCategory = args.get('graphCategory') as GraphCategory
            if (args.containsKey('vertex')) this.vertex = args.get('vertex') as GraphVertex
            if (args.containsKey('categoryObject')) this.categoryObject = args.get('categoryObject') as CategoryObject
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.graphCategoryId == null) throw new IllegalStateException("Required property missing: GraphVertexCategoryObject.graphCategoryId")
        if (this.graphVertexId == null) throw new IllegalStateException("Required property missing: GraphVertexCategoryObject.graphVertexId")
        if (this.categoryObjectId == null) throw new IllegalStateException("Required property missing: GraphVertexCategoryObject.categoryObjectId")
    }

    /**
     * Gradle-style closure configurator
     */
    GraphVertexCategoryObject configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphVertexCategoryObject) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    GraphCategory graphCategory(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphCategory) Closure<?> action) {
        if (this.graphCategory == null) this.graphCategory = new GraphCategory()
        this.graphCategory.configure(action)
        this.graphCategory
    }

    GraphVertex vertex(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphVertex) Closure<?> action) {
        if (this.vertex == null) this.vertex = new GraphVertex()
        this.vertex.configure(action)
        this.vertex
    }

    CategoryObject categoryObject(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CategoryObject) Closure<?> action) {
        if (this.categoryObject == null) this.categoryObject = new CategoryObject()
        this.categoryObject.configure(action)
        this.categoryObject
    }
}
