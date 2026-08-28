/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphEdgeMorphism
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
@EqualsAndHashCode(includes = ['graphEdgeMorphismId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class GraphEdgeMorphism implements Serializable {
    private static final long serialVersionUID = 1L

    String graphEdgeMorphismId
    String graphCategoryId // Required
    String graphEdgeId // Required
    String morphismId // Required

    // --- Relationships (In-Memory Navigation) ---
    GraphCategory graphCategory
    GraphEdge edge
    Morphism morphism

    GraphEdgeMorphism() { }

    GraphEdgeMorphism(String graphEdgeMorphismId) {
        this.graphEdgeMorphismId = Objects.requireNonNull(graphEdgeMorphismId, "GraphEdgeMorphism.graphEdgeMorphismId cannot be null")
    }

    GraphEdgeMorphism(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('graphEdgeMorphismId')) this.graphEdgeMorphismId = args.get('graphEdgeMorphismId') as String
            if (args.containsKey('graphCategoryId')) this.graphCategoryId = args.get('graphCategoryId') as String
            if (args.containsKey('graphEdgeId')) this.graphEdgeId = args.get('graphEdgeId') as String
            if (args.containsKey('morphismId')) this.morphismId = args.get('morphismId') as String
            if (args.containsKey('graphCategory')) this.graphCategory = args.get('graphCategory') as GraphCategory
            if (args.containsKey('edge')) this.edge = args.get('edge') as GraphEdge
            if (args.containsKey('morphism')) this.morphism = args.get('morphism') as Morphism
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.graphCategoryId == null) throw new IllegalStateException("Required property missing: GraphEdgeMorphism.graphCategoryId")
        if (this.graphEdgeId == null) throw new IllegalStateException("Required property missing: GraphEdgeMorphism.graphEdgeId")
        if (this.morphismId == null) throw new IllegalStateException("Required property missing: GraphEdgeMorphism.morphismId")
    }

    /**
     * Gradle-style closure configurator
     */
    GraphEdgeMorphism configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphEdgeMorphism) Closure<?> action) {
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

    GraphEdge edge(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphEdge) Closure<?> action) {
        if (this.edge == null) this.edge = new GraphEdge()
        this.edge.configure(action)
        this.edge
    }

    Morphism morphism(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Morphism) Closure<?> action) {
        if (this.morphism == null) this.morphism = new Morphism()
        this.morphism.configure(action)
        this.morphism
    }
}
