/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.ortools

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta

@CompileStatic
final class OrTools {
    private OrTools() { }

    static OrToolsResult solve(final MathMeta mathMeta, final String mathModelId) {
        new OrToolsProvider(mathModelId).run(mathMeta)
    }
}
