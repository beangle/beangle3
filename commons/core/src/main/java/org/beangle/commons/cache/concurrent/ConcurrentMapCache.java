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
package org.beangle.commons.cache.concurrent;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.beangle.commons.cache.Cache;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Option;

/**
 * Cache based Concurrent Map.
 * 
 * @author chaostone
 * @since 3.2.0
 */
public class ConcurrentMapCache<K, V> implements Cache<K, V> {

  private final ConcurrentMap<K, V> store;

  private final String name;

  public ConcurrentMapCache(String name) {
    super();
    this.name = name;
    store = CollectUtils.newConcurrentHashMap();
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public Option<V> get(K key) {
    V v = store.get(key);
    if (null == v) return Option.none();
    else return Option.some(v);
  }

  @Override
  public void put(K key, V value) {
    store.put(key, value);
  }

  @Override
  public void evict(K key) {
    store.remove(key);
  }

  @Override
  public Set<K> keys() {
    return store.keySet();
  }

  @Override
  public void clear() {
    store.clear();
  }

}
