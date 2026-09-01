/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.SubobjectClassifier
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
class SubobjectClassifier implements Serializable {
    private static final long serialVersionUID = 1L

    /** categoryId */
    String categoryId

    /** terminalObjectId */
    String terminalObjectId

    /** classifierObjectId */
    String classifierObjectId

    /** truthMorphismId */
    String truthMorphismId

    Category category

    CategoryObject terminalObject

    CategoryObject classifierObject

    Morphism truthMorphism

    SubobjectClassifier() {}

    SubobjectClassifier(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId')?.toString()
            if (args.containsKey('terminalObjectId')) this.terminalObjectId = args.get('terminalObjectId')?.toString()
            if (args.containsKey('classifierObjectId')) this.classifierObjectId = args.get('classifierObjectId')?.toString()
            if (args.containsKey('truthMorphismId')) this.truthMorphismId = args.get('truthMorphismId')?.toString()
        }
    }

    SubobjectClassifier categoryId(String value) {
        this.categoryId = value
        return this;
    }

    SubobjectClassifier terminalObjectId(String value) {
        this.terminalObjectId = value
        return this;
    }

    SubobjectClassifier classifierObjectId(String value) {
        this.classifierObjectId = value
        return this;
    }

    SubobjectClassifier truthMorphismId(String value) {
        this.truthMorphismId = value
        return this;
    }

    SubobjectClassifier category(Category item) {
        this.category = item;
        return this;
    }

    SubobjectClassifier terminalObject(CategoryObject item) {
        this.terminalObject = item;
        return this;
    }

    SubobjectClassifier classifierObject(CategoryObject item) {
        this.classifierObject = item;
        return this;
    }

    SubobjectClassifier truthMorphism(Morphism item) {
        this.truthMorphism = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.categoryId != null) map.put('categoryId', this.categoryId);
        if (this.terminalObjectId != null) map.put('terminalObjectId', this.terminalObjectId);
        if (this.classifierObjectId != null) map.put('classifierObjectId', this.classifierObjectId);
        if (this.truthMorphismId != null) map.put('truthMorphismId', this.truthMorphismId);
        return map;
    }
}