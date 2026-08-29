/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.MorphismCompositionComponent
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
@EqualsAndHashCode(includes = ['morphismId', 'componentMorphismId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class MorphismCompositionComponent implements Serializable {
    private static final long serialVersionUID = 1L

    /** morphismId */
    String morphismId

    /** componentMorphismId */
    String componentMorphismId

    /** sequenceNum */
    Long sequenceNum

    /** description */
    String description

    MorphismComposition composition

    Morphism component

    MorphismCompositionComponent() {}

    MorphismCompositionComponent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('morphismId')) this.morphismId = args.get('morphismId')?.toString()
            if (args.containsKey('componentMorphismId')) this.componentMorphismId = args.get('componentMorphismId')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    MorphismCompositionComponent morphismId(String value) {
        this.morphismId = value
        return this;
    }

    MorphismCompositionComponent componentMorphismId(String value) {
        this.componentMorphismId = value
        return this;
    }

    MorphismCompositionComponent sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    MorphismCompositionComponent description(String value) {
        this.description = value
        return this;
    }

    MorphismCompositionComponent composition(MorphismComposition item) {
        this.composition = item;
        return this;
    }

    MorphismCompositionComponent component(Morphism item) {
        this.component = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.morphismId != null) map.put('morphismId', this.morphismId);
        if (this.componentMorphismId != null) map.put('componentMorphismId', this.componentMorphismId);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}