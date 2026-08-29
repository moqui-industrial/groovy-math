/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MathModelRun
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MathModelRun

@CompileStatic
class MathModelRun_ {
    public static final String ENTITY_NAME = 'MathModelRun'
    public static final String FULL_NAME = 'moqui.math.MathModelRun'

    public static final Attribute<MathModelRun, String> mathModelRunId = new Attribute<>('mathModelRunId', MathModelRun.class, String.class, true, true)
    public static final Attribute<MathModelRun, String> mathModelId = new Attribute<>('mathModelId', MathModelRun.class, String.class, false, true)
    public static final Attribute<MathModelRun, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', MathModelRun.class, String.class, false, false)
    public static final Attribute<MathModelRun, java.sql.Timestamp> startTime = new Attribute<>('startTime', MathModelRun.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<MathModelRun, java.sql.Timestamp> endDate = new Attribute<>('endDate', MathModelRun.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<MathModelRun, Double> runningTimeMillis = new Attribute<>('runningTimeMillis', MathModelRun.class, Double.class, false, false)
    public static final Attribute<MathModelRun, String> isSlowHit = new Attribute<>('isSlowHit', MathModelRun.class, String.class, false, false)
    public static final Attribute<MathModelRun, String> parameters = new Attribute<>('parameters', MathModelRun.class, String.class, false, false)
    public static final Attribute<MathModelRun, String> results = new Attribute<>('results', MathModelRun.class, String.class, false, false)
    public static final Attribute<MathModelRun, String> messages = new Attribute<>('messages', MathModelRun.class, String.class, false, false)
    public static final Attribute<MathModelRun, String> hasError = new Attribute<>('hasError', MathModelRun.class, String.class, false, false)
    public static final Attribute<MathModelRun, String> errors = new Attribute<>('errors', MathModelRun.class, String.class, false, false)
    public static final Attribute<MathModelRun, String> userId = new Attribute<>('userId', MathModelRun.class, String.class, false, false)
}
