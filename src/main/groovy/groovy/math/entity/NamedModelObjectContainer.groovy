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

package groovy.math.entity

interface NamedModelObjectContainer<T extends ModelValue> extends NamedModelObjectCollection<T> {
    NamedModelObjectContainer<T> configure(Closure<?> action)
    T create(String name)
    T create(String name, Closure<?> action)
    T maybeCreate(String name)
    ModelProvider register(String name)
    ModelProvider register(String name, Closure<?> action)
}
