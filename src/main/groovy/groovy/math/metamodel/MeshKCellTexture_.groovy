/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MeshKCellTexture
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MeshKCellTexture

@CompileStatic
class MeshKCellTexture_ {
    public static final String ENTITY_NAME = 'MeshKCellTexture'
    public static final String FULL_NAME = 'moqui.math.MeshKCellTexture'

    public static final Attribute<MeshKCellTexture, String> meshKCellTextureId = new Attribute<>('meshKCellTextureId', MeshKCellTexture.class, String.class, true, true)
    public static final Attribute<MeshKCellTexture, String> meshKCellId = new Attribute<>('meshKCellId', MeshKCellTexture.class, String.class, false, true)
    public static final Attribute<MeshKCellTexture, String> uvCoordVectorId = new Attribute<>('uvCoordVectorId', MeshKCellTexture.class, String.class, false, false)
    public static final Attribute<MeshKCellTexture, String> uvTransformationId = new Attribute<>('uvTransformationId', MeshKCellTexture.class, String.class, false, false)
    public static final Attribute<MeshKCellTexture, String> textureUrl = new Attribute<>('textureUrl', MeshKCellTexture.class, String.class, false, false)
    public static final Attribute<MeshKCellTexture, String> colorVectorId = new Attribute<>('colorVectorId', MeshKCellTexture.class, String.class, false, false)
}
