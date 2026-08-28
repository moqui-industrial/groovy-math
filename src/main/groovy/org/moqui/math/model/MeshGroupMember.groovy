/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshGroupMember
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['meshGroupId', 'meshKCellId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MeshGroupMember implements Serializable {
    private static final long serialVersionUID = 1L

    String meshGroupId
    String meshKCellId
    String purposeEnumId
    Long sequenceNum
    String description

    // --- Relationships (In-Memory Navigation) ---
    MeshGroup group
    MeshKCell cell
    Object purpose

    MeshGroupMember() { }

    MeshGroupMember(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('meshGroupId')) this.meshGroupId = args.get('meshGroupId') as String
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') as Long
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('group')) this.group = args.get('group') as MeshGroup
            if (args.containsKey('cell')) this.cell = args.get('cell') as MeshKCell
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
    }

    /**
     * Gradle-style closure configurator
     */
    MeshGroupMember configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshGroupMember) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MeshGroup group(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshGroup) Closure<?> action) {
        if (this.group == null) this.group = new MeshGroup()
        this.group.configure(action)
        this.group
    }

    MeshKCell cell(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCell) Closure<?> action) {
        if (this.cell == null) this.cell = new MeshKCell()
        this.cell.configure(action)
        this.cell
    }
}
