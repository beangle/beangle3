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
package org.beangle.commons.cache;

import java.util.Set;

import org.beangle.commons.lang.Option;

/**
 * Common interface of Cache
 * 
 * @author chaostone
 * @since 3.2.0
 */
public interface Cache<K, V> {

  /**
   * Return the cache name.
   */
  String name();

  /**
   * Get Some(T) or None
   */
  Option<V> get(K key);

  /**
   * Put a new Value
   */
  void put(K key, V value);

  /**
   * Evict specified key
   */
  void evict(K key);

  /**
   * Return cached keys
   */
  Set<K> keys();

  /**
   * Remove all mappings from the cache.
   */
  void clear();
}
