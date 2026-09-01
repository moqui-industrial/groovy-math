/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.SubobjectClassification
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
@EqualsAndHashCode(includes = ['subobjectMorphismId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class SubobjectClassification implements Serializable {
    private static final long serialVersionUID = 1L

    /** subobjectMorphismId */
    String subobjectMorphismId

    /** classifierCategoryId */
    String classifierCategoryId

    /** characteristicMorphismId */
    String characteristicMorphismId

    /** pullbackConstructionId */
    String pullbackConstructionId

    Morphism subobjectMorphism

    SubobjectClassifier classifier

    Morphism characteristicMorphism

    UniversalConstruction pullbackConstruction

    SubobjectClassification() {}

    SubobjectClassification(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('subobjectMorphismId')) this.subobjectMorphismId = args.get('subobjectMorphismId')?.toString()
            if (args.containsKey('classifierCategoryId')) this.classifierCategoryId = args.get('classifierCategoryId')?.toString()
            if (args.containsKey('characteristicMorphismId')) this.characteristicMorphismId = args.get('characteristicMorphismId')?.toString()
            if (args.containsKey('pullbackConstructionId')) this.pullbackConstructionId = args.get('pullbackConstructionId')?.toString()
        }
    }

    SubobjectClassification subobjectMorphismId(String value) {
        this.subobjectMorphismId = value
        return this;
    }

    SubobjectClassification classifierCategoryId(String value) {
        this.classifierCategoryId = value
        return this;
    }

    SubobjectClassification characteristicMorphismId(String value) {
        this.characteristicMorphismId = value
        return this;
    }

    SubobjectClassification pullbackConstructionId(String value) {
        this.pullbackConstructionId = value
        return this;
    }

    SubobjectClassification subobjectMorphism(Morphism item) {
        this.subobjectMorphism = item;
        return this;
    }

    SubobjectClassification classifier(SubobjectClassifier item) {
        this.classifier = item;
        return this;
    }

    SubobjectClassification characteristicMorphism(Morphism item) {
        this.characteristicMorphism = item;
        return this;
    }

    SubobjectClassification pullbackConstruction(UniversalConstruction item) {
        this.pullbackConstruction = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.subobjectMorphismId != null) map.put('subobjectMorphismId', this.subobjectMorphismId);
        if (this.classifierCategoryId != null) map.put('classifierCategoryId', this.classifierCategoryId);
        if (this.characteristicMorphismId != null) map.put('characteristicMorphismId', this.characteristicMorphismId);
        if (this.pullbackConstructionId != null) map.put('pullbackConstructionId', this.pullbackConstructionId);
        return map;
    }
}