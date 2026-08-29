/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCellTexture
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['meshKCellTextureId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MeshKCellTexture implements Serializable {
    private static final long serialVersionUID = 1L

    /** meshKCellTextureId */
    String meshKCellTextureId

    /** meshKCellId */
    String meshKCellId

    /** uvCoordVectorId */
    String uvCoordVectorId

    /** uvTransformationId */
    String uvTransformationId

    /** textureUrl */
    String textureUrl

    /** colorVectorId */
    String colorVectorId

    MeshKCell cell

    Vector uvCoordVector

    Transformation uvTransformation

    Vector colorVector

    MeshKCellTexture() {}

    MeshKCellTexture(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('meshKCellTextureId')) this.meshKCellTextureId = args.get('meshKCellTextureId')?.toString()
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId')?.toString()
            if (args.containsKey('uvCoordVectorId')) this.uvCoordVectorId = args.get('uvCoordVectorId')?.toString()
            if (args.containsKey('uvTransformationId')) this.uvTransformationId = args.get('uvTransformationId')?.toString()
            if (args.containsKey('textureUrl')) this.textureUrl = args.get('textureUrl')?.toString()
            if (args.containsKey('colorVectorId')) this.colorVectorId = args.get('colorVectorId')?.toString()
        }
    }

    MeshKCellTexture meshKCellTextureId(String value) {
        this.meshKCellTextureId = value
        return this;
    }

    MeshKCellTexture meshKCellId(String value) {
        this.meshKCellId = value
        return this;
    }

    MeshKCellTexture uvCoordVectorId(String value) {
        this.uvCoordVectorId = value
        return this;
    }

    MeshKCellTexture uvTransformationId(String value) {
        this.uvTransformationId = value
        return this;
    }

    MeshKCellTexture textureUrl(String value) {
        this.textureUrl = value
        return this;
    }

    MeshKCellTexture colorVectorId(String value) {
        this.colorVectorId = value
        return this;
    }

    MeshKCellTexture cell(MeshKCell item) {
        this.cell = item;
        return this;
    }

    MeshKCellTexture uvCoordVector(Vector item) {
        this.uvCoordVector = item;
        return this;
    }

    MeshKCellTexture uvTransformation(Transformation item) {
        this.uvTransformation = item;
        return this;
    }

    MeshKCellTexture colorVector(Vector item) {
        this.colorVector = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.meshKCellTextureId != null) map.put('meshKCellTextureId', this.meshKCellTextureId);
        if (this.meshKCellId != null) map.put('meshKCellId', this.meshKCellId);
        if (this.uvCoordVectorId != null) map.put('uvCoordVectorId', this.uvCoordVectorId);
        if (this.uvTransformationId != null) map.put('uvTransformationId', this.uvTransformationId);
        if (this.textureUrl != null) map.put('textureUrl', this.textureUrl);
        if (this.colorVectorId != null) map.put('colorVectorId', this.colorVectorId);
        return map;
    }
}