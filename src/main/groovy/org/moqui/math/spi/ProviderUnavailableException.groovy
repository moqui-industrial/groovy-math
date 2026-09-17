/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.spi

import groovy.transform.CompileStatic

/**
 * Domain exception thrown when a requested math provider is unavailable,
 * typically due to missing native libraries, environment configuration, or hardware dependencies.
 * Includes an actionable remedy suggestion.
 */
@CompileStatic
class ProviderUnavailableException extends RuntimeException {

    final String providerId
    final String remedySuggestion

    ProviderUnavailableException(String providerId, String message, String remedySuggestion = null, Throwable cause = null) {
        super(formatMessage(providerId, message, remedySuggestion), cause)
        this.providerId = providerId
        this.remedySuggestion = remedySuggestion
    }

    private static String formatMessage(String providerId, String message, String remedySuggestion) {
        StringBuilder sb = new StringBuilder()
        sb.append("Provider '").append(providerId).append("' is unavailable: ").append(message)
        if (remedySuggestion) {
            sb.append("\nRemedy: ").append(remedySuggestion)
        }
        sb.toString()
    }
}
