/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.commons.entity.orm;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Entity;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;

/**
 * @author chaostone
 * @since 3.1
 */
public final class EntityPersistConfig {

  /**
   * Classname -> EntityDefinition
   */
  final Map<String, EntityDefinition> entities = CollectUtils.newHashMap();

  /**
   * Classname.property -> CollectionDefinition
   */
  final Map<String, CollectionDefinition> collections = CollectUtils.newHashMap();

  final CacheConfig cache = new CacheConfig();

  public Collection<EntityDefinition> getEntites() {
    return entities.values();
  }

  public Collection<CollectionDefinition> getCollections() {
    return collections.values();
  }

  public EntityDefinition getEntity(Class<?> clazz) {
    return entities.get(clazz.getName());
  }

  public EntityPersistConfig addEntity(EntityDefinition definition) {
    entities.put(definition.getClazz().getName(), definition);
    return this;
  }

  public EntityPersistConfig addCollection(CollectionDefinition definition) {
    collections.put(definition.getClazz().getName() + definition.property, definition);
    return this;
  }

  public CacheConfig getCache() {
    return cache;
  }

  public static final class CollectionDefinition {
    final Class<?> clazz;
    final String property;
    String cacheRegion;
    String cacheUsage;

    public CollectionDefinition(Class<?> clazz, String property) {
      super();
      this.clazz = clazz;
      this.property = property;
    }

    public CollectionDefinition cache(String region, String usage) {
      this.cacheRegion = region;
      this.cacheUsage = usage;
      return this;
    }

    public String getProperty() {
      return property;
    }

    public Class<?> getClazz() {
      return clazz;
    }

    public String getCacheUsage() {
      return cacheUsage;
    }

    public String getCacheRegion() {
      return cacheRegion;
    }

  }

  public static final class EntityDefinition {
    final Class<?> clazz;
    final String entityName;
    String cacheUsage;
    String cacheRegion;

    public EntityDefinition(Class<?> clazz) {
      super();
      this.clazz = clazz;
      Entity annotation = clazz.getAnnotation(javax.persistence.Entity.class);
      Assert.notNull(annotation);
      if (Strings.isNotBlank(annotation.name())) {
        this.entityName = annotation.name();
      } else {
        this.entityName = clazz.getName();
      }
    }

    public void cache(String region, String usage) {
      this.cacheRegion = region;
      this.cacheUsage = usage;
    }

    public String getEntityName() {
      return entityName;
    }

    public String getCacheUsage() {
      return cacheUsage;
    }

    public void setCacheUsage(String cacheUsage) {
      this.cacheUsage = cacheUsage;
    }

    public String getCacheRegion() {
      return cacheRegion;
    }

    public void setCacheRegion(String cacheRegion) {
      this.cacheRegion = cacheRegion;
    }

    public Class<?> getClazz() {
      return clazz;
    }

    @Override
    public int hashCode() {
      return clazz.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return clazz.equals(obj);
    }
  }

  public static final class CacheConfig {
    String region;
    String usage;

    public String getRegion() {
      return region;
    }

    public void setRegion(String region) {
      this.region = region;
    }

    public String getUsage() {
      return usage;
    }

    public void setUsage(String usage) {
      this.usage = usage;
    }
  }
}
