/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCellIncidence
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
@EqualsAndHashCode(includes = ['higherCellId', 'lowerCellId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MeshKCellIncidence implements Serializable {
    private static final long serialVersionUID = 1L

    String higherCellId
    String lowerCellId
    Long sequenceNum
    String orientation

    // --- Relationships (In-Memory Navigation) ---
    MeshKCell higherCell
    MeshKCell lowerCell

    MeshKCellIncidence() { }

    MeshKCellIncidence(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('higherCellId')) this.higherCellId = args.get('higherCellId') as String
            if (args.containsKey('lowerCellId')) this.lowerCellId = args.get('lowerCellId') as String
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') as Long
            if (args.containsKey('orientation')) this.orientation = args.get('orientation') as String
            if (args.containsKey('higherCell')) this.higherCell = args.get('higherCell') as MeshKCell
            if (args.containsKey('lowerCell')) this.lowerCell = args.get('lowerCell') as MeshKCell
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
    MeshKCellIncidence configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCellIncidence) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MeshKCell higherCell(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCell) Closure<?> action) {
        if (this.higherCell == null) this.higherCell = new MeshKCell()
        this.higherCell.configure(action)
        this.higherCell
    }

    MeshKCell lowerCell(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCell) Closure<?> action) {
        if (this.lowerCell == null) this.lowerCell = new MeshKCell()
        this.lowerCell.configure(action)
        this.lowerCell
    }
}
