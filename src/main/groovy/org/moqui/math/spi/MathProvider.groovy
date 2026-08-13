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

package org.moqui.math.spi

import org.moqui.math.dsl.MathMeta

/**
 * Compiles the declarative metadata into a provider-owned plan and executes it.
 * The plan type is deliberately not defined by Groovy Math.
 */
interface MathProvider<P, R> {
    String getProviderId()
    P compile(MathMeta mathMeta)
    R execute(P plan, Map<String, ?> inputs)

    default R run(final MathMeta mathMeta) {
        run(mathMeta, Collections.emptyMap())
    }

    default R run(final MathMeta mathMeta, final Map<String, ?> inputs) {
        P plan = compile(mathMeta)
        try {
            execute(plan, inputs)
        } finally {
            if (plan instanceof AutoCloseable) ((AutoCloseable) plan).close()
        }
    }
}
