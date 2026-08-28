/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphContent
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
@EqualsAndHashCode(includes = ['graphContentId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class GraphContent implements Serializable {
    private static final long serialVersionUID = 1L

    String graphContentId
    String graphId // Required
    String contentLocation
    String contentTypeEnumId
    java.sql.Timestamp contentDate
    String description
    String userId

    // --- Relationships (In-Memory Navigation) ---
    Graph graph
    Object type
    Object userAccount

    GraphContent() { }

    GraphContent(String graphContentId) {
        this.graphContentId = Objects.requireNonNull(graphContentId, "GraphContent.graphContentId cannot be null")
    }

    GraphContent(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('graphContentId')) this.graphContentId = args.get('graphContentId') as String
            if (args.containsKey('graphId')) this.graphId = args.get('graphId') as String
            if (args.containsKey('contentLocation')) this.contentLocation = args.get('contentLocation') as String
            if (args.containsKey('contentTypeEnumId')) this.contentTypeEnumId = args.get('contentTypeEnumId') as String
            if (args.containsKey('contentDate')) this.contentDate = args.get('contentDate') as java.sql.Timestamp
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('userId')) this.userId = args.get('userId') as String
            if (args.containsKey('graph')) this.graph = args.get('graph') as Graph
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('userAccount')) this.userAccount = args.get('userAccount') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.graphId == null) throw new IllegalStateException("Required property missing: GraphContent.graphId")
    }

    /**
     * Gradle-style closure configurator
     */
    GraphContent configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphContent) Closure<?> action) {
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
}
