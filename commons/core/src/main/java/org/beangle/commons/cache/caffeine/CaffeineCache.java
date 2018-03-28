/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.cache.caffeine;

import org.beangle.commons.cache.Cache;
import org.beangle.commons.lang.Option;

public class CaffeineCache<K, V> implements Cache<K, V> {

  private final com.github.benmanes.caffeine.cache.Cache<K, V> store;

  public CaffeineCache(com.github.benmanes.caffeine.cache.Cache<K, V> store) {
    this.store = store;
  }

  @Override
  public Option<V> get(K key) {
    return Option.from((V) store.getIfPresent(key));
  }

  /**
   * Put a new Value
   */
  @Override
  public void put(K key, V value) {
    store.put(key, value);
  }

  /**
   * Evict specified key
   */
  @Override
  public void evict(K key) {
    Object existed = store.getIfPresent(key);
    if (null != existed) store.invalidate(key);
  }

  /**
   * Remove all mappings from the cache.
   */
  @Override
  public void clear() {
    store.cleanUp();
  }

}
