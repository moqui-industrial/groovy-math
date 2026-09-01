/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.ExponentialObject
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
@EqualsAndHashCode(includes = ['categoryId', 'argumentObjectId', 'valueObjectId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class ExponentialObject implements Serializable {
    private static final long serialVersionUID = 1L

    /** categoryId */
    String categoryId

    /** argumentObjectId */
    String argumentObjectId

    /** valueObjectId */
    String valueObjectId

    /** exponentialObjectId */
    String exponentialObjectId

    /** productObjectId */
    String productObjectId

    /** evaluationMorphismId */
    String evaluationMorphismId

    Category category

    CategoryObject argumentObject

    CategoryObject valueObject

    CategoryObject exponentialObject

    CategoryObject productObject

    Morphism evaluationMorphism

    ExponentialObject() {}

    ExponentialObject(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId')?.toString()
            if (args.containsKey('argumentObjectId')) this.argumentObjectId = args.get('argumentObjectId')?.toString()
            if (args.containsKey('valueObjectId')) this.valueObjectId = args.get('valueObjectId')?.toString()
            if (args.containsKey('exponentialObjectId')) this.exponentialObjectId = args.get('exponentialObjectId')?.toString()
            if (args.containsKey('productObjectId')) this.productObjectId = args.get('productObjectId')?.toString()
            if (args.containsKey('evaluationMorphismId')) this.evaluationMorphismId = args.get('evaluationMorphismId')?.toString()
        }
    }

    ExponentialObject categoryId(String value) {
        this.categoryId = value
        return this;
    }

    ExponentialObject argumentObjectId(String value) {
        this.argumentObjectId = value
        return this;
    }

    ExponentialObject valueObjectId(String value) {
        this.valueObjectId = value
        return this;
    }

    ExponentialObject exponentialObjectId(String value) {
        this.exponentialObjectId = value
        return this;
    }

    ExponentialObject productObjectId(String value) {
        this.productObjectId = value
        return this;
    }

    ExponentialObject evaluationMorphismId(String value) {
        this.evaluationMorphismId = value
        return this;
    }

    ExponentialObject category(Category item) {
        this.category = item;
        return this;
    }

    ExponentialObject argumentObject(CategoryObject item) {
        this.argumentObject = item;
        return this;
    }

    ExponentialObject valueObject(CategoryObject item) {
        this.valueObject = item;
        return this;
    }

    ExponentialObject exponentialObject(CategoryObject item) {
        this.exponentialObject = item;
        return this;
    }

    ExponentialObject productObject(CategoryObject item) {
        this.productObject = item;
        return this;
    }

    ExponentialObject evaluationMorphism(Morphism item) {
        this.evaluationMorphism = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.categoryId != null) map.put('categoryId', this.categoryId);
        if (this.argumentObjectId != null) map.put('argumentObjectId', this.argumentObjectId);
        if (this.valueObjectId != null) map.put('valueObjectId', this.valueObjectId);
        if (this.exponentialObjectId != null) map.put('exponentialObjectId', this.exponentialObjectId);
        if (this.productObjectId != null) map.put('productObjectId', this.productObjectId);
        if (this.evaluationMorphismId != null) map.put('evaluationMorphismId', this.evaluationMorphismId);
        return map;
    }
}