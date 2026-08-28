/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshContent
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
@EqualsAndHashCode(includes = ['meshContentId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MeshContent implements Serializable {
    private static final long serialVersionUID = 1L

    String meshContentId
    String meshId // Required
    String contentLocation
    String contentTypeEnumId
    java.sql.Timestamp contentDate
    String description
    String userId

    // --- Relationships (In-Memory Navigation) ---
    Mesh mesh
    Object type
    Object userAccount

    MeshContent() { }

    MeshContent(String meshContentId) {
        this.meshContentId = Objects.requireNonNull(meshContentId, "MeshContent.meshContentId cannot be null")
    }

    MeshContent(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('meshContentId')) this.meshContentId = args.get('meshContentId') as String
            if (args.containsKey('meshId')) this.meshId = args.get('meshId') as String
            if (args.containsKey('contentLocation')) this.contentLocation = args.get('contentLocation') as String
            if (args.containsKey('contentTypeEnumId')) this.contentTypeEnumId = args.get('contentTypeEnumId') as String
            if (args.containsKey('contentDate')) this.contentDate = args.get('contentDate') as java.sql.Timestamp
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('userId')) this.userId = args.get('userId') as String
            if (args.containsKey('mesh')) this.mesh = args.get('mesh') as Mesh
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('userAccount')) this.userAccount = args.get('userAccount') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.meshId == null) throw new IllegalStateException("Required property missing: MeshContent.meshId")
    }

    /**
     * Gradle-style closure configurator
     */
    MeshContent configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshContent) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Mesh mesh(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Mesh) Closure<?> action) {
        if (this.mesh == null) this.mesh = new Mesh()
        this.mesh.configure(action)
        this.mesh
    }
}
