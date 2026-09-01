/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.CategoryConstructionOperand
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['categoryConstructionId', 'sequenceNum'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class CategoryConstructionOperand implements Serializable {
    private static final long serialVersionUID = 1L

    /** categoryConstructionId */
    String categoryConstructionId

    /** sequenceNum */
    Long sequenceNum

    /** operandCategoryId */
    String operandCategoryId

    /** operandObjectId */
    String operandObjectId

    /** operandMorphismId */
    String operandMorphismId

    /** operandFunctorId */
    String operandFunctorId

    /** operandGraphId */
    String operandGraphId

    CategoryConstruction construction

    Category operandCategory

    CategoryObject operandObject

    Morphism operandMorphism

    Functor operandFunctor

    Graph operandGraph

    CategoryConstructionOperand() {}

    CategoryConstructionOperand(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('categoryConstructionId')) this.categoryConstructionId = args.get('categoryConstructionId')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('operandCategoryId')) this.operandCategoryId = args.get('operandCategoryId')?.toString()
            if (args.containsKey('operandObjectId')) this.operandObjectId = args.get('operandObjectId')?.toString()
            if (args.containsKey('operandMorphismId')) this.operandMorphismId = args.get('operandMorphismId')?.toString()
            if (args.containsKey('operandFunctorId')) this.operandFunctorId = args.get('operandFunctorId')?.toString()
            if (args.containsKey('operandGraphId')) this.operandGraphId = args.get('operandGraphId')?.toString()
        }
    }

    CategoryConstructionOperand categoryConstructionId(String value) {
        this.categoryConstructionId = value
        return this;
    }

    CategoryConstructionOperand sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    CategoryConstructionOperand operandCategoryId(String value) {
        this.operandCategoryId = value
        return this;
    }

    CategoryConstructionOperand operandObjectId(String value) {
        this.operandObjectId = value
        return this;
    }

    CategoryConstructionOperand operandMorphismId(String value) {
        this.operandMorphismId = value
        return this;
    }

    CategoryConstructionOperand operandFunctorId(String value) {
        this.operandFunctorId = value
        return this;
    }

    CategoryConstructionOperand operandGraphId(String value) {
        this.operandGraphId = value
        return this;
    }

    CategoryConstructionOperand construction(CategoryConstruction item) {
        this.construction = item;
        return this;
    }

    CategoryConstructionOperand operandCategory(Category item) {
        this.operandCategory = item;
        return this;
    }

    CategoryConstructionOperand operandObject(CategoryObject item) {
        this.operandObject = item;
        return this;
    }

    CategoryConstructionOperand operandMorphism(Morphism item) {
        this.operandMorphism = item;
        return this;
    }

    CategoryConstructionOperand operandFunctor(Functor item) {
        this.operandFunctor = item;
        return this;
    }

    CategoryConstructionOperand operandGraph(Graph item) {
        this.operandGraph = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.categoryConstructionId != null) map.put('categoryConstructionId', this.categoryConstructionId);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.operandCategoryId != null) map.put('operandCategoryId', this.operandCategoryId);
        if (this.operandObjectId != null) map.put('operandObjectId', this.operandObjectId);
        if (this.operandMorphismId != null) map.put('operandMorphismId', this.operandMorphismId);
        if (this.operandFunctorId != null) map.put('operandFunctorId', this.operandFunctorId);
        if (this.operandGraphId != null) map.put('operandGraphId', this.operandGraphId);
        return map;
    }
}