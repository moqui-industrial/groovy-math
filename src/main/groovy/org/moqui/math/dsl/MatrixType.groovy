/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MatrixType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MatrixType implements DslEnumValue {
    Zero('MtZero', '', 'Zero Matrix', ''),
    Rectangular('MtRectangular', '', 'Rectangular Matrix', ''),
    Square('MtSquare', '', 'Square Matrix', ''),
    Diagonal('MtDiagonal', '', 'Diagonal Matrix', ''),
    Identity('MtIdentity', '', 'Identity Matrix', ''),
    Symmetric('MtSymmetric', '', 'Symmetric Matrix', ''),
    Orthogonal('MtOrthogonal', '', 'Orthogonal Matrix', ''),
    Sparse('MtSparse', '', 'Sparse Matrix', ''),
    Triangular('MtTriangular', '', 'Triangular Matrix', ''),
    Permutation('MtPermutation', '', 'Permutation Matrix', ''),
    Hermitian('MtHermitian', '', 'Hermitian Matrix', ''),
    Dense('MtDense', 'DENSE', 'Dense Matrix', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MatrixType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MatrixType fromId(final String id) {
        if (id == null) return null
        for (MatrixType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MatrixType fromCode(final String code) {
        if (code == null) return null
        for (MatrixType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MatrixType fromName(final String name) {
        if (name == null) return null
        for (MatrixType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('ZeroMatrix'.equalsIgnoreCase(name)) return Zero
        if ('RectangularMatrix'.equalsIgnoreCase(name)) return Rectangular
        if ('SquareMatrix'.equalsIgnoreCase(name)) return Square
        if ('DiagonalMatrix'.equalsIgnoreCase(name)) return Diagonal
        if ('IdentityMatrix'.equalsIgnoreCase(name)) return Identity
        if ('SymmetricMatrix'.equalsIgnoreCase(name)) return Symmetric
        if ('OrthogonalMatrix'.equalsIgnoreCase(name)) return Orthogonal
        if ('SparseMatrix'.equalsIgnoreCase(name)) return Sparse
        if ('TriangularMatrix'.equalsIgnoreCase(name)) return Triangular
        if ('PermutationMatrix'.equalsIgnoreCase(name)) return Permutation
        if ('HermitianMatrix'.equalsIgnoreCase(name)) return Hermitian
        if ('DenseMatrix'.equalsIgnoreCase(name)) return Dense
        null
    }
}
