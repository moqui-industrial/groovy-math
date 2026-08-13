/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

MathModelDef('NeuralNetwork') {
    description 'Neural network model definition'
}

MathModel('Classifier') {
    mathModelDefId 'NeuralNetwork'
    description 'Example classifier'
    statusId 'MathModelDraft'
}

Transformation('DenseProduct') {
    transformationTypeEnumId 'TtMatrixProduct'
    name 'Dense matrix product'
}

MathModelData('Classifier.DenseProduct') {
    mathModelId 'Classifier'
    transformationId 'DenseProduct'
}
