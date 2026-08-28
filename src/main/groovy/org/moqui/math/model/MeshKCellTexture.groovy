/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCellTexture
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
@EqualsAndHashCode(includes = ['meshKCellTextureId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MeshKCellTexture implements Serializable {
    private static final long serialVersionUID = 1L

    String meshKCellTextureId
    String meshKCellId // Required
    String uvCoordVectorId
    String uvTransformationId
    String textureUrl
    String colorVectorId

    // --- Relationships (In-Memory Navigation) ---
    MeshKCell cell
    Vector uvCoordVector
    Transformation uvTransformation
    Vector colorVector

    MeshKCellTexture() { }

    MeshKCellTexture(String meshKCellTextureId) {
        this.meshKCellTextureId = Objects.requireNonNull(meshKCellTextureId, "MeshKCellTexture.meshKCellTextureId cannot be null")
    }

    MeshKCellTexture(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('meshKCellTextureId')) this.meshKCellTextureId = args.get('meshKCellTextureId') as String
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId') as String
            if (args.containsKey('uvCoordVectorId')) this.uvCoordVectorId = args.get('uvCoordVectorId') as String
            if (args.containsKey('uvTransformationId')) this.uvTransformationId = args.get('uvTransformationId') as String
            if (args.containsKey('textureUrl')) this.textureUrl = args.get('textureUrl') as String
            if (args.containsKey('colorVectorId')) this.colorVectorId = args.get('colorVectorId') as String
            if (args.containsKey('cell')) this.cell = args.get('cell') as MeshKCell
            if (args.containsKey('uvCoordVector')) this.uvCoordVector = args.get('uvCoordVector') as Vector
            if (args.containsKey('uvTransformation')) this.uvTransformation = args.get('uvTransformation') as Transformation
            if (args.containsKey('colorVector')) this.colorVector = args.get('colorVector') as Vector
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.meshKCellId == null) throw new IllegalStateException("Required property missing: MeshKCellTexture.meshKCellId")
    }

    /**
     * Gradle-style closure configurator
     */
    MeshKCellTexture configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCellTexture) Closure<?> action) {
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

    Vector uvCoordVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.uvCoordVector == null) this.uvCoordVector = new Vector()
        this.uvCoordVector.configure(action)
        this.uvCoordVector
    }

    Transformation uvTransformation(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Transformation) Closure<?> action) {
        if (this.uvTransformation == null) this.uvTransformation = new Transformation()
        this.uvTransformation.configure(action)
        this.uvTransformation
    }

    Vector colorVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.colorVector == null) this.colorVector = new Vector()
        this.colorVector.configure(action)
        this.colorVector
    }
}
