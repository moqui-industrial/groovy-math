/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.petsctao

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta

@CompileStatic
final class PetscTao {
    private PetscTao() { }

    static PetscTaoResult minimize(final MathMeta mathMeta, final String mathModelId) {
        new PetscTaoProvider(mathModelId).run(mathMeta)
    }
}
