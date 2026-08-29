/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.MorphismComposition
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
@EqualsAndHashCode(includes = ['morphismId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MorphismComposition implements Serializable {
    private static final long serialVersionUID = 1L

    /** morphismId */
    String morphismId

    /** compositionTypeEnumId */
    String compositionTypeEnumId

    Morphism morphism

    List<MorphismCompositionComponent> components = new ArrayList<>()

    MorphismComposition() {}

    MorphismComposition(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('morphismId')) this.morphismId = args.get('morphismId')?.toString()
            if (args.containsKey('compositionTypeEnumId')) this.compositionTypeEnumId = args.get('compositionTypeEnumId')?.toString()
        }
    }

    MorphismComposition morphismId(String value) {
        this.morphismId = value
        return this;
    }

    MorphismComposition compositionTypeEnumId(String value) {
        this.compositionTypeEnumId = value
        return this;
    }

    MorphismComposition morphism(Morphism item) {
        this.morphism = item;
        return this;
    }

    MorphismComposition components(List<MorphismCompositionComponent> list) {
        this.components = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.morphismId != null) map.put('morphismId', this.morphismId);
        if (this.compositionTypeEnumId != null) map.put('compositionTypeEnumId', this.compositionTypeEnumId);
        return map;
    }
}