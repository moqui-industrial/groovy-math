/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.NaturalTransformationComponent
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
@EqualsAndHashCode(includes = ['natTransfId', 'categoryObjectId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class NaturalTransformationComponent implements Serializable {
    private static final long serialVersionUID = 1L

    /** natTransfId */
    String natTransfId

    /** categoryObjectId */
    String categoryObjectId

    /** componentMorphismId */
    String componentMorphismId

    NaturalTransformation natTransf

    CategoryObject categoryObject

    Morphism componentMorphism

    NaturalTransformationComponent() {}

    NaturalTransformationComponent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('natTransfId')) this.natTransfId = args.get('natTransfId')?.toString()
            if (args.containsKey('categoryObjectId')) this.categoryObjectId = args.get('categoryObjectId')?.toString()
            if (args.containsKey('componentMorphismId')) this.componentMorphismId = args.get('componentMorphismId')?.toString()
        }
    }

    NaturalTransformationComponent natTransfId(String value) {
        this.natTransfId = value
        return this;
    }

    NaturalTransformationComponent categoryObjectId(String value) {
        this.categoryObjectId = value
        return this;
    }

    NaturalTransformationComponent componentMorphismId(String value) {
        this.componentMorphismId = value
        return this;
    }

    NaturalTransformationComponent natTransf(NaturalTransformation item) {
        this.natTransf = item;
        return this;
    }

    NaturalTransformationComponent categoryObject(CategoryObject item) {
        this.categoryObject = item;
        return this;
    }

    NaturalTransformationComponent componentMorphism(Morphism item) {
        this.componentMorphism = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.natTransfId != null) map.put('natTransfId', this.natTransfId);
        if (this.categoryObjectId != null) map.put('categoryObjectId', this.categoryObjectId);
        if (this.componentMorphismId != null) map.put('componentMorphismId', this.componentMorphismId);
        return map;
    }
}