/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.Category
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
@EqualsAndHashCode(includes = ['categoryId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Category implements Serializable {
    private static final long serialVersionUID = 1L

    /** categoryId */
    String categoryId

    /** parentCategoryId */
    String parentCategoryId

    /** categoryTypeEnumId */
    String categoryTypeEnumId

    /** categoryName */
    String categoryName

    /** description */
    String description

    /** fromDate */
    java.sql.Timestamp fromDate

    /** thruDate */
    java.sql.Timestamp thruDate

    Category parent

    List<Category> children = new ArrayList<>()

    List<CategoryObject> objects = new ArrayList<>()

    List<Morphism> morphisms = new ArrayList<>()

    List<MorphismEquation> equations = new ArrayList<>()

    List<CategoryConstruction> constructions = new ArrayList<>()

    List<UniversalConstruction> universalConstructions = new ArrayList<>()

    List<ExponentialObject> exponentials = new ArrayList<>()

    Category() {}

    Category(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId')?.toString()
            if (args.containsKey('parentCategoryId')) this.parentCategoryId = args.get('parentCategoryId')?.toString()
            if (args.containsKey('categoryTypeEnumId')) this.categoryTypeEnumId = args.get('categoryTypeEnumId')?.toString()
            if (args.containsKey('categoryName')) this.categoryName = args.get('categoryName')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('fromDate')) this.fromDate = (java.sql.Timestamp) args.get('fromDate')
            if (args.containsKey('thruDate')) this.thruDate = (java.sql.Timestamp) args.get('thruDate')
        }
    }

    Category categoryId(String value) {
        this.categoryId = value
        return this;
    }

    Category parentCategoryId(String value) {
        this.parentCategoryId = value
        return this;
    }

    Category categoryTypeEnumId(String value) {
        this.categoryTypeEnumId = value
        return this;
    }

    Category categoryName(String value) {
        this.categoryName = value
        return this;
    }

    Category description(String value) {
        this.description = value
        return this;
    }

    Category fromDate(java.sql.Timestamp value) {
        this.fromDate = value
        return this;
    }

    Category thruDate(java.sql.Timestamp value) {
        this.thruDate = value
        return this;
    }

    Category parent(Category item) {
        this.parent = item;
        return this;
    }

    Category children(List<Category> list) {
        this.children = list;
        return this;
    }

    Category objects(List<CategoryObject> list) {
        this.objects = list;
        return this;
    }

    Category morphisms(List<Morphism> list) {
        this.morphisms = list;
        return this;
    }

    Category equations(List<MorphismEquation> list) {
        this.equations = list;
        return this;
    }

    Category constructions(List<CategoryConstruction> list) {
        this.constructions = list;
        return this;
    }

    Category universalConstructions(List<UniversalConstruction> list) {
        this.universalConstructions = list;
        return this;
    }

    Category exponentials(List<ExponentialObject> list) {
        this.exponentials = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.categoryId != null) map.put('categoryId', this.categoryId);
        if (this.parentCategoryId != null) map.put('parentCategoryId', this.parentCategoryId);
        if (this.categoryTypeEnumId != null) map.put('categoryTypeEnumId', this.categoryTypeEnumId);
        if (this.categoryName != null) map.put('categoryName', this.categoryName);
        if (this.description != null) map.put('description', this.description);
        if (this.fromDate != null) map.put('fromDate', this.fromDate);
        if (this.thruDate != null) map.put('thruDate', this.thruDate);
        return map;
    }
}