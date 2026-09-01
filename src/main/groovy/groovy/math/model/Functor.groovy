/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.Functor
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
@EqualsAndHashCode(includes = ['functorId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Functor implements Serializable {
    private static final long serialVersionUID = 1L

    /** functorId */
    String functorId

    /** parentFunctorId */
    String parentFunctorId

    /** functorTypeEnumId */
    String functorTypeEnumId

    /** functorName */
    String functorName

    /** functorSymbol */
    String functorSymbol

    /** description */
    String description

    /** sourceCategoryId */
    String sourceCategoryId

    /** targetCategoryId */
    String targetCategoryId

    /** categoryMorphismId */
    String categoryMorphismId

    Functor parent

    Category sourceCategory

    Category targetCategory

    Morphism categoryMorphism

    List<FunctorObjectMapping> objMap = new ArrayList<>()

    List<FunctorMorphismMapping> morphismMap = new ArrayList<>()

    Functor() {}

    Functor(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('functorId')) this.functorId = args.get('functorId')?.toString()
            if (args.containsKey('parentFunctorId')) this.parentFunctorId = args.get('parentFunctorId')?.toString()
            if (args.containsKey('functorTypeEnumId')) this.functorTypeEnumId = args.get('functorTypeEnumId')?.toString()
            if (args.containsKey('functorName')) this.functorName = args.get('functorName')?.toString()
            if (args.containsKey('functorSymbol')) this.functorSymbol = args.get('functorSymbol')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('sourceCategoryId')) this.sourceCategoryId = args.get('sourceCategoryId')?.toString()
            if (args.containsKey('targetCategoryId')) this.targetCategoryId = args.get('targetCategoryId')?.toString()
            if (args.containsKey('categoryMorphismId')) this.categoryMorphismId = args.get('categoryMorphismId')?.toString()
        }
    }

    Functor functorId(String value) {
        this.functorId = value
        return this;
    }

    Functor parentFunctorId(String value) {
        this.parentFunctorId = value
        return this;
    }

    Functor functorTypeEnumId(String value) {
        this.functorTypeEnumId = value
        return this;
    }

    Functor functorName(String value) {
        this.functorName = value
        return this;
    }

    Functor functorSymbol(String value) {
        this.functorSymbol = value
        return this;
    }

    Functor description(String value) {
        this.description = value
        return this;
    }

    Functor sourceCategoryId(String value) {
        this.sourceCategoryId = value
        return this;
    }

    Functor targetCategoryId(String value) {
        this.targetCategoryId = value
        return this;
    }

    Functor categoryMorphismId(String value) {
        this.categoryMorphismId = value
        return this;
    }

    Functor parent(Functor item) {
        this.parent = item;
        return this;
    }

    Functor sourceCategory(Category item) {
        this.sourceCategory = item;
        return this;
    }

    Functor targetCategory(Category item) {
        this.targetCategory = item;
        return this;
    }

    Functor categoryMorphism(Morphism item) {
        this.categoryMorphism = item;
        return this;
    }

    Functor objMap(List<FunctorObjectMapping> list) {
        this.objMap = list;
        return this;
    }

    Functor morphismMap(List<FunctorMorphismMapping> list) {
        this.morphismMap = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.functorId != null) map.put('functorId', this.functorId);
        if (this.parentFunctorId != null) map.put('parentFunctorId', this.parentFunctorId);
        if (this.functorTypeEnumId != null) map.put('functorTypeEnumId', this.functorTypeEnumId);
        if (this.functorName != null) map.put('functorName', this.functorName);
        if (this.functorSymbol != null) map.put('functorSymbol', this.functorSymbol);
        if (this.description != null) map.put('description', this.description);
        if (this.sourceCategoryId != null) map.put('sourceCategoryId', this.sourceCategoryId);
        if (this.targetCategoryId != null) map.put('targetCategoryId', this.targetCategoryId);
        if (this.categoryMorphismId != null) map.put('categoryMorphismId', this.categoryMorphismId);
        return map;
    }
}