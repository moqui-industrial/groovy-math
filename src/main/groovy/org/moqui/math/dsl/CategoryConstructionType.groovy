/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: CategoryConstructionType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum CategoryConstructionType implements DslEnumValue {
    Product('CcProduct', '', 'Product category', ''),
    Opposite('CcOpposite', '', 'Opposite category', ''),
    Arrow('CcArrow', '', 'Arrow category', ''),
    Slice('CcSlice', '', 'Slice category', ''),
    Coslice('CcCoslice', '', 'Coslice category', ''),
    Free('CcFree', '', 'Free category on a graph', ''),
    Quotient('CcQuotient', '', 'Quotient category by a congruence', ''),
    FunctorCategory('CcFunctorCategory', '', 'Functor category [C,D]', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    CategoryConstructionType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static CategoryConstructionType fromId(final String id) {
        if (id == null) return null
        for (CategoryConstructionType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static CategoryConstructionType fromCode(final String code) {
        if (code == null) return null
        for (CategoryConstructionType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static CategoryConstructionType fromName(final String name) {
        if (name == null) return null
        for (CategoryConstructionType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('ProductCategory'.equalsIgnoreCase(name)) return Product
        if ('OppositeCategory'.equalsIgnoreCase(name)) return Opposite
        if ('ArrowCategory'.equalsIgnoreCase(name)) return Arrow
        if ('SliceCategory'.equalsIgnoreCase(name)) return Slice
        if ('CosliceCategory'.equalsIgnoreCase(name)) return Coslice
        if ('FreeCategoryOnAGraph'.equalsIgnoreCase(name)) return Free
        if ('QuotientCategoryByACongruence'.equalsIgnoreCase(name)) return Quotient
        if ('FunctorCategoryCD'.equalsIgnoreCase(name)) return FunctorCategory
        null
    }
}
