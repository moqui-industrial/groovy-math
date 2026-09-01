/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.UniversalConstruction
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
@EqualsAndHashCode(includes = ['universalConstructionId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class UniversalConstruction implements Serializable {
    private static final long serialVersionUID = 1L

    /** universalConstructionId */
    String universalConstructionId

    /** categoryId */
    String categoryId

    /** constructionTypeEnumId */
    String constructionTypeEnumId

    /** diagramFunctorId */
    String diagramFunctorId

    /** universalObjectId */
    String universalObjectId

    /** universalNaturalTransformationId */
    String universalNaturalTransformationId

    /** description */
    String description

    Category category

    Functor diagram

    CategoryObject universalObject

    NaturalTransformation universalNaturalTransformation

    UniversalConstruction() {}

    UniversalConstruction(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('universalConstructionId')) this.universalConstructionId = args.get('universalConstructionId')?.toString()
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId')?.toString()
            if (args.containsKey('constructionTypeEnumId')) this.constructionTypeEnumId = args.get('constructionTypeEnumId')?.toString()
            if (args.containsKey('diagramFunctorId')) this.diagramFunctorId = args.get('diagramFunctorId')?.toString()
            if (args.containsKey('universalObjectId')) this.universalObjectId = args.get('universalObjectId')?.toString()
            if (args.containsKey('universalNaturalTransformationId')) this.universalNaturalTransformationId = args.get('universalNaturalTransformationId')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    UniversalConstruction universalConstructionId(String value) {
        this.universalConstructionId = value
        return this;
    }

    UniversalConstruction categoryId(String value) {
        this.categoryId = value
        return this;
    }

    UniversalConstruction constructionTypeEnumId(String value) {
        this.constructionTypeEnumId = value
        return this;
    }

    UniversalConstruction diagramFunctorId(String value) {
        this.diagramFunctorId = value
        return this;
    }

    UniversalConstruction universalObjectId(String value) {
        this.universalObjectId = value
        return this;
    }

    UniversalConstruction universalNaturalTransformationId(String value) {
        this.universalNaturalTransformationId = value
        return this;
    }

    UniversalConstruction description(String value) {
        this.description = value
        return this;
    }

    UniversalConstruction category(Category item) {
        this.category = item;
        return this;
    }

    UniversalConstruction diagram(Functor item) {
        this.diagram = item;
        return this;
    }

    UniversalConstruction universalObject(CategoryObject item) {
        this.universalObject = item;
        return this;
    }

    UniversalConstruction universalNaturalTransformation(NaturalTransformation item) {
        this.universalNaturalTransformation = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.universalConstructionId != null) map.put('universalConstructionId', this.universalConstructionId);
        if (this.categoryId != null) map.put('categoryId', this.categoryId);
        if (this.constructionTypeEnumId != null) map.put('constructionTypeEnumId', this.constructionTypeEnumId);
        if (this.diagramFunctorId != null) map.put('diagramFunctorId', this.diagramFunctorId);
        if (this.universalObjectId != null) map.put('universalObjectId', this.universalObjectId);
        if (this.universalNaturalTransformationId != null) map.put('universalNaturalTransformationId', this.universalNaturalTransformationId);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}