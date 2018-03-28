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

import java.util.concurrent.TimeUnit;

import org.beangle.commons.cache.AbstractCacheManager;
import org.beangle.commons.cache.Cache;

import com.github.benmanes.caffeine.cache.Caffeine;

public class CaffeineCacheManager extends AbstractCacheManager {

  private int maximumSize = 50000;

  private int ttl = 60 * 60;

  private int tti = 15 * 60;

  @Override
  protected <K, V> Cache<K, V> newCache(String name, Class<K> keyType, Class<V> valueType) {
    com.github.benmanes.caffeine.cache.Cache<K, V> store = Caffeine.newBuilder().maximumSize(maximumSize)
        .expireAfterWrite(ttl, TimeUnit.SECONDS).expireAfterAccess(tti, TimeUnit.SECONDS).build();
    return new CaffeineCache<K, V>(store);
  }
}
