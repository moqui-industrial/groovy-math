/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshQuality
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
@EqualsAndHashCode(includes = ['meshQualityId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MeshQuality implements Serializable {
    private static final long serialVersionUID = 1L

    String meshQualityId
    String meshId // Required
    String description
    Long totalKCells
    BigDecimal shapeAspectRatioMin
    BigDecimal shapeAspectRatioMax
    BigDecimal shapeAspectRatioAvg
    BigDecimal shapeAspectRatioStdDev
    BigDecimal shapeAspectRatioVariance
    BigDecimal edgeLengthRatioMin
    BigDecimal edgeLengthRatioMax
    BigDecimal edgeLengthRatioAvg
    BigDecimal edgeLengthRatioStdDev
    BigDecimal edgeLengthRatioVariance
    BigDecimal cellMeasureRatioMin
    BigDecimal cellMeasureRatioMax
    BigDecimal cellMeasureRatioAvg
    BigDecimal cellMeasureRatioStdDev
    BigDecimal cellMeasureRatioVariance
    BigDecimal cellNonOrthogonalityMin
    BigDecimal cellNonOrthogonalityMax
    BigDecimal cellNonOrthogonalityAvg
    BigDecimal cellNonOrthogonalityStdDev
    BigDecimal cellNonOrthogonalityVariance
    BigDecimal skewnessMin
    BigDecimal skewnessMax
    BigDecimal skewnessAvg
    BigDecimal skewnessStdDev
    BigDecimal skewnessVariance

    // --- Relationships (In-Memory Navigation) ---
    Mesh mesh

    MeshQuality() { }

    MeshQuality(String meshQualityId) {
        this.meshQualityId = Objects.requireNonNull(meshQualityId, "MeshQuality.meshQualityId cannot be null")
    }

    MeshQuality(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('meshQualityId')) this.meshQualityId = args.get('meshQualityId') as String
            if (args.containsKey('meshId')) this.meshId = args.get('meshId') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('totalKCells')) this.totalKCells = args.get('totalKCells') as Long
            if (args.containsKey('shapeAspectRatioMin')) this.shapeAspectRatioMin = args.get('shapeAspectRatioMin') as BigDecimal
            if (args.containsKey('shapeAspectRatioMax')) this.shapeAspectRatioMax = args.get('shapeAspectRatioMax') as BigDecimal
            if (args.containsKey('shapeAspectRatioAvg')) this.shapeAspectRatioAvg = args.get('shapeAspectRatioAvg') as BigDecimal
            if (args.containsKey('shapeAspectRatioStdDev')) this.shapeAspectRatioStdDev = args.get('shapeAspectRatioStdDev') as BigDecimal
            if (args.containsKey('shapeAspectRatioVariance')) this.shapeAspectRatioVariance = args.get('shapeAspectRatioVariance') as BigDecimal
            if (args.containsKey('edgeLengthRatioMin')) this.edgeLengthRatioMin = args.get('edgeLengthRatioMin') as BigDecimal
            if (args.containsKey('edgeLengthRatioMax')) this.edgeLengthRatioMax = args.get('edgeLengthRatioMax') as BigDecimal
            if (args.containsKey('edgeLengthRatioAvg')) this.edgeLengthRatioAvg = args.get('edgeLengthRatioAvg') as BigDecimal
            if (args.containsKey('edgeLengthRatioStdDev')) this.edgeLengthRatioStdDev = args.get('edgeLengthRatioStdDev') as BigDecimal
            if (args.containsKey('edgeLengthRatioVariance')) this.edgeLengthRatioVariance = args.get('edgeLengthRatioVariance') as BigDecimal
            if (args.containsKey('cellMeasureRatioMin')) this.cellMeasureRatioMin = args.get('cellMeasureRatioMin') as BigDecimal
            if (args.containsKey('cellMeasureRatioMax')) this.cellMeasureRatioMax = args.get('cellMeasureRatioMax') as BigDecimal
            if (args.containsKey('cellMeasureRatioAvg')) this.cellMeasureRatioAvg = args.get('cellMeasureRatioAvg') as BigDecimal
            if (args.containsKey('cellMeasureRatioStdDev')) this.cellMeasureRatioStdDev = args.get('cellMeasureRatioStdDev') as BigDecimal
            if (args.containsKey('cellMeasureRatioVariance')) this.cellMeasureRatioVariance = args.get('cellMeasureRatioVariance') as BigDecimal
            if (args.containsKey('cellNonOrthogonalityMin')) this.cellNonOrthogonalityMin = args.get('cellNonOrthogonalityMin') as BigDecimal
            if (args.containsKey('cellNonOrthogonalityMax')) this.cellNonOrthogonalityMax = args.get('cellNonOrthogonalityMax') as BigDecimal
            if (args.containsKey('cellNonOrthogonalityAvg')) this.cellNonOrthogonalityAvg = args.get('cellNonOrthogonalityAvg') as BigDecimal
            if (args.containsKey('cellNonOrthogonalityStdDev')) this.cellNonOrthogonalityStdDev = args.get('cellNonOrthogonalityStdDev') as BigDecimal
            if (args.containsKey('cellNonOrthogonalityVariance')) this.cellNonOrthogonalityVariance = args.get('cellNonOrthogonalityVariance') as BigDecimal
            if (args.containsKey('skewnessMin')) this.skewnessMin = args.get('skewnessMin') as BigDecimal
            if (args.containsKey('skewnessMax')) this.skewnessMax = args.get('skewnessMax') as BigDecimal
            if (args.containsKey('skewnessAvg')) this.skewnessAvg = args.get('skewnessAvg') as BigDecimal
            if (args.containsKey('skewnessStdDev')) this.skewnessStdDev = args.get('skewnessStdDev') as BigDecimal
            if (args.containsKey('skewnessVariance')) this.skewnessVariance = args.get('skewnessVariance') as BigDecimal
            if (args.containsKey('mesh')) this.mesh = args.get('mesh') as Mesh
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.meshId == null) throw new IllegalStateException("Required property missing: MeshQuality.meshId")
    }

    /**
     * Gradle-style closure configurator
     */
    MeshQuality configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshQuality) Closure<?> action) {
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
