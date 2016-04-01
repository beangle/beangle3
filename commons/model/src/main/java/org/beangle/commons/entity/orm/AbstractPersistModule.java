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

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Entity;

public abstract class AbstractPersistModule {

  private EntityPersistConfig config = null;

  abstract protected void doConfig();

  protected final EntityHolder add(Class<? extends Entity<?>>... classes) {
    for (Class<? extends Entity<?>> cls : classes)
      config.addEntity(new EntityPersistConfig.EntityDefinition(cls));
    return new EntityHolder(classes, config);
  }

  protected final CacheHolder cache(String region) {
    return new CacheHolder(config).cache(region).usage(config.getCache().getUsage());
  }

  protected final CacheHolder cache() {
    return new CacheHolder(config).cache(config.getCache().region).usage(config.getCache().getUsage());
  }

  protected final List<EntityPersistConfig.CollectionDefinition> collection(Class<?> clazz,
      String... properties) {
    List<EntityPersistConfig.CollectionDefinition> definitions = CollectUtils.newArrayList(properties.length);
    for (String property : properties) {
      definitions.add(new EntityPersistConfig.CollectionDefinition(clazz, property));
    }
    return definitions;
  }

  protected final void defaultCache(String region, String usage) {
    config.cache.setRegion(region);
    config.cache.setUsage(usage);
  }

  public final EntityPersistConfig getConfig() {
    config = new EntityPersistConfig();
    doConfig();
    return config;
  }

  public static final class CacheHolder {
    final EntityPersistConfig config;
    String cacheUsage;
    String cacheRegion;

    public CacheHolder(EntityPersistConfig config) {
      super();
      this.config = config;
    }

    public CacheHolder add(List<EntityPersistConfig.CollectionDefinition>... definitionLists) {
      for (List<EntityPersistConfig.CollectionDefinition> definitions : definitionLists) {
        for (EntityPersistConfig.CollectionDefinition definition : definitions) {
          config.addCollection(definition.cache(cacheRegion, cacheUsage));
        }
      }
      return this;
    }

    public CacheHolder add(Class<? extends Entity<?>>... classes) {
      for (Class<? extends Entity<?>> clazz : classes)
        config.getEntity(clazz).cache(cacheRegion, cacheUsage);
      return this;
    }

    public CacheHolder usage(String cacheUsage) {
      this.cacheUsage = cacheUsage;
      return this;
    }

    public CacheHolder cache(String cacheRegion) {
      this.cacheRegion = cacheRegion;
      return this;
    }

  }

  public static final class EntityHolder {

    final Class<?>[] classes;

    final EntityPersistConfig config;

    public EntityHolder(Class<?>[] classes, EntityPersistConfig config) {
      super();
      this.classes = classes;
      this.config = config;
    }

    public EntityHolder cacheable() {
      for (Class<?> clazz : classes) {
        config.getEntity(clazz).cache(config.getCache().getRegion(), config.getCache().getUsage());
      }
      return this;
    }

    public EntityHolder cache(String region) {
      for (Class<?> clazz : classes) {
        config.getEntity(clazz).setCacheRegion(region);
      }
      return this;
    }

    public EntityHolder usage(String usage) {
      for (Class<?> clazz : classes) {
        config.getEntity(clazz).setCacheUsage(usage);
      }
      return this;
    }

  }
}
