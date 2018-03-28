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
package org.beangle.commons.cache;

import java.util.Collection;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

public abstract class AbstractCacheManager implements CacheManager {

  private final Map<String, Cache<?, ?>> caches = CollectUtils.newConcurrentHashMap();

  @SuppressWarnings("unchecked")
  @Override
  public <K, V> Cache<K, V> getCache(String name, Class<K> keyClazz, Class<V> valueClass) {
    Cache<?, ?> cache = caches.get(name);
    if (cache == null) {
      synchronized (caches) {
        cache = caches.get(name);
        if (cache == null) {
          cache = newCache(name, keyClazz, valueClass);
          caches.put(name, cache);
        }
      }
    }
    return (Cache<K, V>) cache;
  }

  @Override
  public Collection<String> getCacheNames() {
    return caches.keySet();
  }

  protected abstract <K, V> Cache<K, V> newCache(String name, Class<K> keyType, Class<V> valueType);
}
