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

interface NamedModelObjectCollection<T extends ModelValue> extends ModelObjectSet<T> {
    SortedSet<String> getNames()
    SortedMap<String, T> getAsMap()
    T findByName(String name)
    T getByName(String name)
    T getAt(String name)
    ModelProvider named(String name)
    ModelProvider named(String name, Closure<?> action)
    NamedModelObjectCollection<T> named(Closure<Boolean> namePredicate)

    @Override
    NamedModelObjectCollection<T> matching(Closure<Boolean> predicate)
}
