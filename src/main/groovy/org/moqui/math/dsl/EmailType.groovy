/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: EmailType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum EmailType implements DslEnumValue {
    SYSTEM('EMT_SYSTEM', '', 'System', ''),
    PWD_RESET('EMT_PWD_RESET', '', 'Password Reset', 'EMT_SYSTEM'),
    SINGLE_USE_CODE('EMT_SINGLE_USE_CODE', '', 'Single Use Code', 'EMT_SYSTEM'),
    ADDED_EMAIL_AUTHC_FACTOR('EMT_ADDED_EMAIL_AUTHC_FACTOR', '', 'Added Email Authentication Type', 'EMT_SYSTEM'),
    EMAIL_AUTHC_FACTOR_SENT('EMT_EMAIL_AUTHC_FACTOR_SENT', '', 'Email Authentication Code Sent', 'EMT_SYSTEM'),
    NOTIFICATION('EMT_NOTIFICATION', '', 'Notification', 'EMT_SYSTEM'),
    SCREEN_RENDER('EMT_SCREEN_RENDER', '', 'Screen Render', 'EMT_SYSTEM'),
    REG_CONFIRM('EMT_REG_CONFIRM', '', 'Registration Confirmation', ''),
    UPD_INFO_CONFIRM('EMT_UPD_INFO_CONFIRM', '', 'Update Personal Info Confirmation', ''),
    EMAIL_VERIFY('EMT_EMAIL_VERIFY', '', 'Email Address Verification', ''),
    ACCOUNT_INVITE('EMT_ACCOUNT_INVITE', '', 'Account Invitation', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    EmailType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static EmailType fromId(final String id) {
        if (id == null) return null
        for (EmailType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static EmailType fromCode(final String code) {
        if (code == null) return null
        for (EmailType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static EmailType fromName(final String name) {
        if (name == null) return null
        for (EmailType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('System'.equalsIgnoreCase(name)) return SYSTEM
        if ('PasswordReset'.equalsIgnoreCase(name)) return PWD_RESET
        if ('SingleUseCode'.equalsIgnoreCase(name)) return SINGLE_USE_CODE
        if ('AddedEmailAuthenticationType'.equalsIgnoreCase(name)) return ADDED_EMAIL_AUTHC_FACTOR
        if ('EmailAuthenticationCodeSent'.equalsIgnoreCase(name)) return EMAIL_AUTHC_FACTOR_SENT
        if ('Notification'.equalsIgnoreCase(name)) return NOTIFICATION
        if ('ScreenRender'.equalsIgnoreCase(name)) return SCREEN_RENDER
        if ('RegistrationConfirmation'.equalsIgnoreCase(name)) return REG_CONFIRM
        if ('UpdatePersonalInfoConfirmation'.equalsIgnoreCase(name)) return UPD_INFO_CONFIRM
        if ('EmailAddressVerification'.equalsIgnoreCase(name)) return EMAIL_VERIFY
        if ('AccountInvitation'.equalsIgnoreCase(name)) return ACCOUNT_INVITE
        null
    }
}
