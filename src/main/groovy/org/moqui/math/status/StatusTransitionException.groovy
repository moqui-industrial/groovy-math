/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.status

import groovy.transform.CompileStatic

/** A status change that the declared StatusFlowTransition rows do not allow. */
@CompileStatic
class StatusTransitionException extends RuntimeException {
    StatusTransitionException(final String message) {
        super(message)
    }
}
