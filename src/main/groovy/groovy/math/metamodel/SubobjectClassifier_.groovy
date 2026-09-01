/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.SubobjectClassifier
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.SubobjectClassifier

@CompileStatic
class SubobjectClassifier_ {
    public static final String ENTITY_NAME = 'SubobjectClassifier'
    public static final String FULL_NAME = 'moqui.math.ct.SubobjectClassifier'

    public static final Attribute<SubobjectClassifier, String> categoryId = new Attribute<>('categoryId', SubobjectClassifier.class, String.class, true, true)
    public static final Attribute<SubobjectClassifier, String> terminalObjectId = new Attribute<>('terminalObjectId', SubobjectClassifier.class, String.class, false, true)
    public static final Attribute<SubobjectClassifier, String> classifierObjectId = new Attribute<>('classifierObjectId', SubobjectClassifier.class, String.class, false, true)
    public static final Attribute<SubobjectClassifier, String> truthMorphismId = new Attribute<>('truthMorphismId', SubobjectClassifier.class, String.class, false, true)
}
