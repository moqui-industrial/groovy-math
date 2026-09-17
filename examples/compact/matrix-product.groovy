MathModelDef('MatrixAlgebra',
    modelType: LinearAlgebra,
    usageContext: Inference,
    modelName: 'Matrix algebra transformations',
    description: 'Provider-neutral mathematical declarations') {

    pipeline('ProductStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'MultiplyAB', stepName: 'Matrix product A x B') {
        Transformation('MultiplyAB', transformationType: MatrixProduct,
            name: 'Matrix product A x B', resultMatrixId: 'C') {
            leftMatrix 'A'
            rightMatrix 'B'
        }
    }

    MathModel('MatrixProduct',
        modelAlias: 'matrix_product',
        source: Manual,
        description: 'C = A x B, where A is supplied at execution time',
        statusId: 'MathModelDraft') {

        A = matrix(matrixType: MatrixType.Dense, purpose: MatrixPurpose.Original,
            domainSpace: R3, codomainSpace: R2,
            rows: 2, cols: 3)

        B = matrix(matrixType: MatrixType.Dense, purpose: MatrixPurpose.Original,
            domainSpace: R2, codomainSpace: R3,
            rows: 3, cols: 2,
            componentArray: '[[7,8],[9,10],[11,12]]')

        C = matrix(matrixType: MatrixType.Dense,
            domainSpace: R2, codomainSpace: R2,
            rows: 2, cols: 2)
    }
}
