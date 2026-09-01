/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.CategoryObject
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
@EqualsAndHashCode(includes = ['categoryObjectId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class CategoryObject implements Serializable {
    private static final long serialVersionUID = 1L

    /** categoryObjectId */
    String categoryObjectId

    /** parentObjectId */
    String parentObjectId

    /** categoryId */
    String categoryId

    /** objectEntityName */
    String objectEntityName

    /** objectPkPrimaryValue */
    String objectPkPrimaryValue

    /** objectPkSecondaryValue */
    String objectPkSecondaryValue

    /** objectPkRestCombinedValue */
    String objectPkRestCombinedValue

    /** objectTypeEnumId */
    String objectTypeEnumId

    /** objectName */
    String objectName

    /** objectSymbol */
    String objectSymbol

    /** description */
    String description

    CategoryObject parent

    List<CategoryObject> children = new ArrayList<>()

    Category category

    List<Morphism> outgoingMorphisms = new ArrayList<>()

    List<Morphism> incomingMorphisms = new ArrayList<>()

    List<Parameter> parameters = new ArrayList<>()

    CategoryObject() {}

    CategoryObject(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('categoryObjectId')) this.categoryObjectId = args.get('categoryObjectId')?.toString()
            if (args.containsKey('parentObjectId')) this.parentObjectId = args.get('parentObjectId')?.toString()
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId')?.toString()
            if (args.containsKey('objectEntityName')) this.objectEntityName = args.get('objectEntityName')?.toString()
            if (args.containsKey('objectPkPrimaryValue')) this.objectPkPrimaryValue = args.get('objectPkPrimaryValue')?.toString()
            if (args.containsKey('objectPkSecondaryValue')) this.objectPkSecondaryValue = args.get('objectPkSecondaryValue')?.toString()
            if (args.containsKey('objectPkRestCombinedValue')) this.objectPkRestCombinedValue = args.get('objectPkRestCombinedValue')?.toString()
            if (args.containsKey('objectTypeEnumId')) this.objectTypeEnumId = args.get('objectTypeEnumId')?.toString()
            if (args.containsKey('objectName')) this.objectName = args.get('objectName')?.toString()
            if (args.containsKey('objectSymbol')) this.objectSymbol = args.get('objectSymbol')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    CategoryObject categoryObjectId(String value) {
        this.categoryObjectId = value
        return this;
    }

    CategoryObject parentObjectId(String value) {
        this.parentObjectId = value
        return this;
    }

    CategoryObject categoryId(String value) {
        this.categoryId = value
        return this;
    }

    CategoryObject objectEntityName(String value) {
        this.objectEntityName = value
        return this;
    }

    CategoryObject objectPkPrimaryValue(String value) {
        this.objectPkPrimaryValue = value
        return this;
    }

    CategoryObject objectPkSecondaryValue(String value) {
        this.objectPkSecondaryValue = value
        return this;
    }

    CategoryObject objectPkRestCombinedValue(String value) {
        this.objectPkRestCombinedValue = value
        return this;
    }

    CategoryObject objectTypeEnumId(String value) {
        this.objectTypeEnumId = value
        return this;
    }

    CategoryObject objectName(String value) {
        this.objectName = value
        return this;
    }

    CategoryObject objectSymbol(String value) {
        this.objectSymbol = value
        return this;
    }

    CategoryObject description(String value) {
        this.description = value
        return this;
    }

    CategoryObject parent(CategoryObject item) {
        this.parent = item;
        return this;
    }

    CategoryObject children(List<CategoryObject> list) {
        this.children = list;
        return this;
    }

    CategoryObject category(Category item) {
        this.category = item;
        return this;
    }

    CategoryObject outgoingMorphisms(List<Morphism> list) {
        this.outgoingMorphisms = list;
        return this;
    }

    CategoryObject incomingMorphisms(List<Morphism> list) {
        this.incomingMorphisms = list;
        return this;
    }

    CategoryObject parameters(List<Parameter> list) {
        this.parameters = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.categoryObjectId != null) map.put('categoryObjectId', this.categoryObjectId);
        if (this.parentObjectId != null) map.put('parentObjectId', this.parentObjectId);
        if (this.categoryId != null) map.put('categoryId', this.categoryId);
        if (this.objectEntityName != null) map.put('objectEntityName', this.objectEntityName);
        if (this.objectPkPrimaryValue != null) map.put('objectPkPrimaryValue', this.objectPkPrimaryValue);
        if (this.objectPkSecondaryValue != null) map.put('objectPkSecondaryValue', this.objectPkSecondaryValue);
        if (this.objectPkRestCombinedValue != null) map.put('objectPkRestCombinedValue', this.objectPkRestCombinedValue);
        if (this.objectTypeEnumId != null) map.put('objectTypeEnumId', this.objectTypeEnumId);
        if (this.objectName != null) map.put('objectName', this.objectName);
        if (this.objectSymbol != null) map.put('objectSymbol', this.objectSymbol);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}