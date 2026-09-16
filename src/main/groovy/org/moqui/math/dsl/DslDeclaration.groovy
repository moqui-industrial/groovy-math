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

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.ModelProvider

@CompileStatic
@PackageScope
final class DslDeclaration {
    final EntityDefinition definition
    final String modelKey
    final LinkedHashMap<String, Object> values
    final ModelProvider provider
    final DslDeclaration parent

    DslDeclaration(final EntityDefinition definition, final String modelKey,
                   final LinkedHashMap<String, Object> values, final ModelProvider provider,
                   final DslDeclaration parent) {
        this.definition = definition
        this.modelKey = modelKey
        this.values = values
        this.provider = provider
        this.parent = parent
    }
}
