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

package org.moqui.math.model

interface ModelObjectCollection<T> extends Collection<T> {
    ModelObjectCollection<T> matching(Closure<Boolean> predicate)
    <S extends T> ModelObjectCollection<S> withType(Class<S> type)
    void all(Closure<?> action)
    void configureEach(Closure<?> action)
    void whenObjectAdded(Closure<?> action)
    void whenObjectRemoved(Closure<?> action)
    void disallowChanges()
}
