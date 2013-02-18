/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.cache.concurrent;

import java.util.Collection;
import java.util.Map;

import org.beangle.commons.cache.Cache;
import org.beangle.commons.cache.CacheManager;
import org.beangle.commons.collection.CollectUtils;

/**
 * Concurrent Map Cache Manager.
 * 
 * @author chaostone
 * @since 3.2.0
 */
public class ConcurrentMapCacheManager implements CacheManager {

  private final Map<String, Cache<?, ?>> caches = CollectUtils.newConcurrentHashMap();

  @SuppressWarnings("unchecked")
  @Override
  public <K, V> Cache<K, V> getCache(String name) {
    Cache<?, ?> cache = caches.get(name);
    if (cache == null) {
      synchronized (caches) {
        cache = caches.get(name);
        if (cache == null) {
          cache = new ConcurrentMapCache<K, V>(name);
          caches.put(name, cache);
        }
      }
    }
    return (Cache<K, V>) cache;
  }

  @Override
  public Collection<String> getCacheNames() {
    return null;
  }

}
