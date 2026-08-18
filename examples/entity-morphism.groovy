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

ParameterDef('AgMorphSourceDialectDef',
    parameterTypeEnum: ParameterType.TextShort,
    purposeEnum: ParameterPurpose.MathModel,
    parameterCode: 'agMorphSourceDialect',
    parameterName: 'Source Dialect')

Category('AgentEntityModel',
    categoryTypeEnum: CategoryType.Small,
    categoryName: 'Moqui Entity Model',
    description: 'Entity objects and structural morphisms extracted from entity-definition XML.') {

    objects('AgEntObj_BillingAccount',
        objectEntityName: 'mantle.account.billing.BillingAccount',
        objectPkValue: 'mantle.account.billing.BillingAccount',
        objectTypeEnum: CategoryObjectType.Generic,
        objectName: 'BillingAccount',
        objectSymbol: 'BillingAccount')

    morphisms('AgEntSchema_BillingAccount',
        morphismTypeEnum: MorphismType.Endo,
        sourceObjectId: 'AgEntObj_BillingAccount',
        targetObjectId: 'AgEntObj_BillingAccount',
        morphismName: 'schema::BillingAccount',
        morphismSymbol: 'BillingAccount') {

        parameters('AgEntSchema_BillingAccount_001',
            parameterDefId: 'AgMorphSourceDialectDef',
            sequenceNum: 1,
            symbolicValue: 'entity-definition')

        Morphism('AgEntRel_BillingAccountParty',
            morphismTypeEnum: MorphismType.General,
            sourceObjectId: 'AgEntObj_BillingAccount',
            targetObjectId: 'AgEntObj_BillingAccount',
            morphismName: 'rel::BillingAccountParty',
            morphismSymbol: 'BillingAccountParty')
    }
}
