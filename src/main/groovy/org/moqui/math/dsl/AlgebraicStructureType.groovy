/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: AlgebraicStructureType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum AlgebraicStructureType implements DslEnumValue {
    AsMagma('AsMagma', '', 'Magma: carrier set equipped with a closed binary operation', ''),
    AsSemigroup('AsSemigroup', '', 'Semigroup: associative magma', 'AsMagma'),
    AsMonoid('AsMonoid', '', 'Monoid: semigroup with a two-sided identity element', 'AsSemigroup'),
    AsQuasigroup('AsQuasigroup', '', 'Quasigroup: magma in which every left and right division equation has a unique solution', 'AsMagma'),
    AsLoop('AsLoop', '', 'Loop: quasigroup with a two-sided identity element', 'AsQuasigroup'),
    AsGroup('AsGroup', '', 'Group: monoid in which every element has a two-sided inverse', 'AsMonoid'),
    AsAbelianGroup('AsAbelianGroup', '', 'Abelian group: group with a commutative operation', 'AsGroup'),
    AsAdditiveReal('AsAdditiveReal', '', 'Additive Abelian group of real numbers (R, +)', 'AsAbelianGroup'),
    AsAdditiveInteger('AsAdditiveInteger', '', 'Additive Abelian group of integers (Z, +)', 'AsAbelianGroup'),
    AsZ3Group('AsZ3Group', '', 'Cyclic Abelian group of integers modulo 3 under addition (Z/3Z, +)', 'AsAbelianGroup'),
    AsRing('AsRing', '', 'Unital ring: additive Abelian group, multiplicative monoid, and distributive laws', ''),
    AsField('AsField', '', 'Field: commutative unital ring with 0 != 1 whose nonzero elements form an Abelian group under multiplication', 'AsRing'),
    AsIntegers('AsIntegers', '', 'Ring of Integers', 'AsRing'),
    AsZ5Field('AsZ5Field', '', 'Finite field of integers modulo 5 (Z/5Z)', 'AsField'),
    AsRationals('AsRationals', '', 'Field of Rational Numbers', 'AsField'),
    AsReals('AsReals', '', 'Field of Real Numbers', 'AsField'),
    AsZ7Field('AsZ7Field', '', 'Field Z/7Z (Finite Field of Order 7)', 'AsField'),
    AsModule('AsModule', '', 'Module: additive Abelian group equipped with scalar multiplication over a specified ring', ''),
    AsVectorSpace('AsVectorSpace', '', 'Vector space: module whose scalar ring is a specified field', 'AsModule'),
    AsRn3('AsRn3', '', 'Three-dimensional vector space R^3 over the field R', 'AsVectorSpace'),
    AsC2('AsC2', '', 'Two-dimensional vector space C^2 over the field C', 'AsVectorSpace'),
    StateSpace('VsStateSpace', 'StateSpace', 'State space; its mathematical structure is defined by the selected model', ''),
    EuclideanSpace('VsEuclideanSpace', 'EuclideanSpace', 'Finite-dimensional real inner-product vector space', 'AsVectorSpace'),
    Euclidean2DSpace('EuclideanSpace2D', 'Euclidean2DSpace', '2D Euclidean Space', 'VsEuclideanSpace'),
    Euclidean3DSpace('EuclideanSpace3D', 'Euclidean3DSpace', '3D Euclidean Space', 'VsEuclideanSpace'),
    EuclideanQuaternionSpace('QuaternionEuclideanSpace', 'EuclideanQuaternionSpace', 'Quaternion coordinates represented as the four-dimensional real Euclidean space R^4', 'VsEuclideanSpace'),
    EuclideanNDSpace('EuclideanSpaceND', 'EuclideanNDSpace', 'N-dimensional real Euclidean space', 'VsEuclideanSpace'),
    ThermodynamicSpace('VsThermodynamicSpace', 'ThermodynamicSpace', 'Thermodynamic state space; dimensionality and constraints are defined by the selected state variables and model', 'VsStateSpace');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    AlgebraicStructureType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
        this.id = id
        this.enumCode = enumCode
        this.description = description
        this.parentEnumId = parentEnumId
    }

    @Override
    String getId() { id }

    @Override
    String getEnumCode() { enumCode }

    @Override
    String getDescription() { description }

    @Override
    String getParentEnumId() { parentEnumId }

    static AlgebraicStructureType fromId(final String id) {
        if (id == null) return null
        for (AlgebraicStructureType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static AlgebraicStructureType fromCode(final String code) {
        if (code == null) return null
        for (AlgebraicStructureType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static AlgebraicStructureType fromName(final String name) {
        if (name == null) return null
        for (AlgebraicStructureType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('MagmaCarrierSetEquippedWithAClosedBinaryOperation'.equalsIgnoreCase(name)) return AsMagma
        if ('SemigroupAssociativeMagma'.equalsIgnoreCase(name)) return AsSemigroup
        if ('MonoidSemigroupWithATwoSidedIdentityElement'.equalsIgnoreCase(name)) return AsMonoid
        if ('QuasigroupMagmaInWhichEveryLeftAndRightDivisionEquationHasAUniqueSolution'.equalsIgnoreCase(name)) return AsQuasigroup
        if ('LoopQuasigroupWithATwoSidedIdentityElement'.equalsIgnoreCase(name)) return AsLoop
        if ('GroupMonoidInWhichEveryElementHasATwoSidedInverse'.equalsIgnoreCase(name)) return AsGroup
        if ('AbelianGroupGroupWithACommutativeOperation'.equalsIgnoreCase(name)) return AsAbelianGroup
        if ('AdditiveAbelianGroupOfRealNumbers'.equalsIgnoreCase(name)) return AsAdditiveReal
        if ('AdditiveAbelianGroupOfIntegers'.equalsIgnoreCase(name)) return AsAdditiveInteger
        if ('CyclicAbelianGroupOfIntegersModulo3UnderAddition'.equalsIgnoreCase(name)) return AsZ3Group
        if ('UnitalRingAdditiveAbelianGroupMultiplicativeMonoidAndDistributiveLaws'.equalsIgnoreCase(name)) return AsRing
        if ('FieldCommutativeUnitalRingWith01WhoseNonzeroElementsFormAnAbelianGroupUnderMultiplication'.equalsIgnoreCase(name)) return AsField
        if ('RingOfIntegers'.equalsIgnoreCase(name)) return AsIntegers
        if ('FiniteFieldOfIntegersModulo5'.equalsIgnoreCase(name)) return AsZ5Field
        if ('FieldOfRationalNumbers'.equalsIgnoreCase(name)) return AsRationals
        if ('FieldOfRealNumbers'.equalsIgnoreCase(name)) return AsReals
        if ('FieldZ7z'.equalsIgnoreCase(name)) return AsZ7Field
        if ('ModuleAdditiveAbelianGroupEquippedWithScalarMultiplicationOverASpecifiedRing'.equalsIgnoreCase(name)) return AsModule
        if ('VectorSpaceModuleWhoseScalarRingIsASpecifiedField'.equalsIgnoreCase(name)) return AsVectorSpace
        if ('ThreeDimensionalVectorSpaceR3OverTheFieldR'.equalsIgnoreCase(name)) return AsRn3
        if ('TwoDimensionalVectorSpaceC2OverTheFieldC'.equalsIgnoreCase(name)) return AsC2
        if ('StateSpaceItsMathematicalStructureIsDefinedByTheSelectedModel'.equalsIgnoreCase(name)) return StateSpace
        if ('FiniteDimensionalRealInnerProductVectorSpace'.equalsIgnoreCase(name)) return EuclideanSpace
        if ('QuaternionCoordinatesRepresentedAsTheFourDimensionalRealEuclideanSpaceR4'.equalsIgnoreCase(name)) return EuclideanQuaternionSpace
        if ('NDimensionalRealEuclideanSpace'.equalsIgnoreCase(name)) return EuclideanNDSpace
        if ('ThermodynamicStateSpaceDimensionalityAndConstraintsAreDefinedByTheSelectedStateVariablesAndModel'.equalsIgnoreCase(name)) return ThermodynamicSpace
        null
    }
}
