/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.CategoryConstruction
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
@EqualsAndHashCode(includes = ['categoryConstructionId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class CategoryConstruction implements Serializable {
    private static final long serialVersionUID = 1L

    /** categoryConstructionId */
    String categoryConstructionId

    /** resultCategoryId */
    String resultCategoryId

    /** constructionTypeEnumId */
    String constructionTypeEnumId

    /** baseObjectId */
    String baseObjectId

    /** description */
    String description

    Category resultCategory

    CategoryObject baseObject

    List<CategoryConstructionOperand> operands = new ArrayList<>()

    List<MorphismEquation> equations = new ArrayList<>()

    CategoryConstruction() {}

    CategoryConstruction(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('categoryConstructionId')) this.categoryConstructionId = args.get('categoryConstructionId')?.toString()
            if (args.containsKey('resultCategoryId')) this.resultCategoryId = args.get('resultCategoryId')?.toString()
            if (args.containsKey('constructionTypeEnumId')) this.constructionTypeEnumId = args.get('constructionTypeEnumId')?.toString()
            if (args.containsKey('baseObjectId')) this.baseObjectId = args.get('baseObjectId')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    CategoryConstruction categoryConstructionId(String value) {
        this.categoryConstructionId = value
        return this;
    }

    CategoryConstruction resultCategoryId(String value) {
        this.resultCategoryId = value
        return this;
    }

    CategoryConstruction constructionTypeEnumId(String value) {
        this.constructionTypeEnumId = value
        return this;
    }

    CategoryConstruction baseObjectId(String value) {
        this.baseObjectId = value
        return this;
    }

    CategoryConstruction description(String value) {
        this.description = value
        return this;
    }

    CategoryConstruction resultCategory(Category item) {
        this.resultCategory = item;
        return this;
    }

    CategoryConstruction baseObject(CategoryObject item) {
        this.baseObject = item;
        return this;
    }

    CategoryConstruction operands(List<CategoryConstructionOperand> list) {
        this.operands = list;
        return this;
    }

    CategoryConstruction equations(List<MorphismEquation> list) {
        this.equations = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.categoryConstructionId != null) map.put('categoryConstructionId', this.categoryConstructionId);
        if (this.resultCategoryId != null) map.put('resultCategoryId', this.resultCategoryId);
        if (this.constructionTypeEnumId != null) map.put('constructionTypeEnumId', this.constructionTypeEnumId);
        if (this.baseObjectId != null) map.put('baseObjectId', this.baseObjectId);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}