/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Vector
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['vectorId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class Vector implements Serializable {
    private static final long serialVersionUID = 1L

    String vectorId
    String parentVectorId
    String vectorTypeEnumId
    String purposeEnumId
    String vectorSpaceEnumId
    String coordinateSystemId
    String name
    String symbol
    String description
    Long dimension
    BigDecimal magnitude
    String componentArray
    byte[] componentBlob

    // --- Relationships (In-Memory Navigation) ---
    Vector parent
    Object type
    Object purpose
    Object vectorSpace
    CoordinateSystem coordSystem
    List<VectorComponent> components = []

    Vector() { }

    Vector(String vectorId) {
        this.vectorId = Objects.requireNonNull(vectorId, "Vector.vectorId cannot be null")
    }

    Vector(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('vectorId')) this.vectorId = args.get('vectorId') as String
            if (args.containsKey('parentVectorId')) this.parentVectorId = args.get('parentVectorId') as String
            if (args.containsKey('vectorTypeEnumId')) this.vectorTypeEnumId = args.get('vectorTypeEnumId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('vectorSpaceEnumId')) this.vectorSpaceEnumId = args.get('vectorSpaceEnumId') as String
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId') as String
            if (args.containsKey('name')) this.name = args.get('name') as String
            if (args.containsKey('symbol')) this.symbol = args.get('symbol') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('dimension')) this.dimension = args.get('dimension') as Long
            if (args.containsKey('magnitude')) this.magnitude = args.get('magnitude') as BigDecimal
            if (args.containsKey('componentArray')) this.componentArray = args.get('componentArray') as String
            if (args.containsKey('componentBlob')) this.componentBlob = args.get('componentBlob') as byte[]
            if (args.containsKey('parent')) this.parent = args.get('parent') as Vector
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('vectorSpace')) this.vectorSpace = args.get('vectorSpace') as Object
            if (args.containsKey('coordSystem')) this.coordSystem = args.get('coordSystem') as CoordinateSystem
            if (args.containsKey('components')) this.components = args.get('components') as List<VectorComponent>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
    }

    /**
     * Gradle-style closure configurator
     */
    Vector configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Vector parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.parent == null) this.parent = new Vector()
        this.parent.configure(action)
        this.parent
    }

    CoordinateSystem coordSystem(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystem) Closure<?> action) {
        if (this.coordSystem == null) this.coordSystem = new CoordinateSystem()
        this.coordSystem.configure(action)
        this.coordSystem
    }

    VectorComponent components(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = VectorComponent) Closure<?> action) {
        VectorComponent item = new VectorComponent()
        item.configure(action)
        if (this.components == null) this.components = []
        this.components.add(item)
        item
    }
}
