/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.Trajectory
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Trajectory

@CompileStatic
class Trajectory_ {
    public static final String ENTITY_NAME = 'Trajectory'
    public static final String FULL_NAME = 'moqui.math.Trajectory'

    public static final Attribute<Trajectory, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', Trajectory.class, String.class, true, true)
    public static final Attribute<Trajectory, String> actuationTypeEnumId = new Attribute<>('actuationTypeEnumId', Trajectory.class, String.class, false, false)
    public static final Attribute<Trajectory, String> actuatorTypeEnumId = new Attribute<>('actuatorTypeEnumId', Trajectory.class, String.class, false, false)
    public static final Attribute<Trajectory, String> controlMethodEnumId = new Attribute<>('controlMethodEnumId', Trajectory.class, String.class, false, false)
}
