/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshGroup
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
@EqualsAndHashCode(includes = ['meshGroupId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MeshGroup implements Serializable {
    private static final long serialVersionUID = 1L

    String meshGroupId
    String meshId // Required
    String groupTypeEnumId
    String purposeEnumId
    String mathModelId
    String groupName
    String description

    // --- Relationships (In-Memory Navigation) ---
    Mesh mesh
    Object type
    Object purpose
    MathModel mathModel
    List<MeshGroupMember> members = []

    MeshGroup() { }

    MeshGroup(String meshGroupId) {
        this.meshGroupId = Objects.requireNonNull(meshGroupId, "MeshGroup.meshGroupId cannot be null")
    }

    MeshGroup(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('meshGroupId')) this.meshGroupId = args.get('meshGroupId') as String
            if (args.containsKey('meshId')) this.meshId = args.get('meshId') as String
            if (args.containsKey('groupTypeEnumId')) this.groupTypeEnumId = args.get('groupTypeEnumId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('mathModelId')) this.mathModelId = args.get('mathModelId') as String
            if (args.containsKey('groupName')) this.groupName = args.get('groupName') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('mesh')) this.mesh = args.get('mesh') as Mesh
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('mathModel')) this.mathModel = args.get('mathModel') as MathModel
            if (args.containsKey('members')) this.members = args.get('members') as List<MeshGroupMember>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.meshId == null) throw new IllegalStateException("Required property missing: MeshGroup.meshId")
    }

    /**
     * Gradle-style closure configurator
     */
    MeshGroup configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshGroup) Closure<?> action) {
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

    MathModel mathModel(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModel) Closure<?> action) {
        if (this.mathModel == null) this.mathModel = new MathModel()
        this.mathModel.configure(action)
        this.mathModel
    }

    MeshGroupMember members(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshGroupMember) Closure<?> action) {
        MeshGroupMember item = new MeshGroupMember()
        item.configure(action)
        if (this.members == null) this.members = []
        this.members.add(item)
        item
    }
}
