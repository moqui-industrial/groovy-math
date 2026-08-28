/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCellQuality
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
@EqualsAndHashCode(includes = ['meshKCellQualityId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MeshKCellQuality implements Serializable {
    private static final long serialVersionUID = 1L

    String meshKCellQualityId
    String meshKCellId // Required
    String description
    BigDecimal shapeAspectRatio
    BigDecimal edgeLengthRatio
    BigDecimal cellMeasureRatio
    BigDecimal cellNonOrthogonality
    BigDecimal skewness

    // --- Relationships (In-Memory Navigation) ---
    MeshKCell cell

    MeshKCellQuality() { }

    MeshKCellQuality(String meshKCellQualityId) {
        this.meshKCellQualityId = Objects.requireNonNull(meshKCellQualityId, "MeshKCellQuality.meshKCellQualityId cannot be null")
    }

    MeshKCellQuality(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('meshKCellQualityId')) this.meshKCellQualityId = args.get('meshKCellQualityId') as String
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('shapeAspectRatio')) this.shapeAspectRatio = args.get('shapeAspectRatio') as BigDecimal
            if (args.containsKey('edgeLengthRatio')) this.edgeLengthRatio = args.get('edgeLengthRatio') as BigDecimal
            if (args.containsKey('cellMeasureRatio')) this.cellMeasureRatio = args.get('cellMeasureRatio') as BigDecimal
            if (args.containsKey('cellNonOrthogonality')) this.cellNonOrthogonality = args.get('cellNonOrthogonality') as BigDecimal
            if (args.containsKey('skewness')) this.skewness = args.get('skewness') as BigDecimal
            if (args.containsKey('cell')) this.cell = args.get('cell') as MeshKCell
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.meshKCellId == null) throw new IllegalStateException("Required property missing: MeshKCellQuality.meshKCellId")
    }

    /**
     * Gradle-style closure configurator
     */
    MeshKCellQuality configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCellQuality) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MeshKCell cell(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCell) Closure<?> action) {
        if (this.cell == null) this.cell = new MeshKCell()
        this.cell.configure(action)
        this.cell
    }
}
