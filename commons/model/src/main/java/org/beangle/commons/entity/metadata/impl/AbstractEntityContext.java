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
package org.beangle.commons.entity.metadata.impl;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.EntityContext;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.IdentifierType;
import org.beangle.commons.entity.metadata.Type;
import org.beangle.commons.lang.ClassLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Abstract AbstractEntityContext class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class AbstractEntityContext implements EntityContext {
  /** entity-name->entity-type */
  protected Map<String, EntityType> entityTypes = CollectUtils.newHashMap();

  /** class-name->entity-type */
  protected Map<String, EntityType> classEntityTypes = CollectUtils.newHashMap();

  /** Constant <code>logger</code> */
  protected static final Logger logger = LoggerFactory.getLogger(AbstractEntityContext.class);

  public String[] getEntityNames(Class<?> clazz) {
    return new String[0];
  }

  public Type getType(String name) {
    Type type = getEntityType(name);
    if (null == type) {
      try {
        return new IdentifierType(Class.forName(name));
      } catch (ClassNotFoundException e) {
        logger.error("system doesn't contains entity {}", name);
      }
      return null;
    } else {
      return type;
    }
  }

  public String getEntityName(Object obj) {
    EntityType type = getEntityType(obj.getClass());
    if (null != type) {
      return type.getEntityName();
    } else {
      return null;
    }
  }

  /**
   * <p>
   * getEntityType.
   * </p>
   * 
   * @param entityClass a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.entity.metadata.EntityType} object.
   */
  public EntityType getEntityType(Class<?> entityClass) {
    String className = entityClass.getName();
    EntityType type = entityTypes.get(className);
    if (null != type) { return type; }

    type = classEntityTypes.get(className);
    if (null == type) {
      List<EntityType> matched = CollectUtils.newArrayList();
      for (EntityType entityType : entityTypes.values()) {
        if (className.equals(entityType.getEntityName())
            || className.equals(entityType.getEntityClass().getName())) {
          matched.add(entityType);
        }
      }
      if (matched.size() > 1) { throw new RuntimeException("multi-entityName for class:" + className); }
      if (matched.isEmpty()) {
        EntityType tmp = new EntityType(entityClass);
        classEntityTypes.put(className, tmp);
        return tmp;
      } else {
        classEntityTypes.put(className, matched.get(0));
        type = (EntityType) matched.get(0);
      }
    }
    return type;
  }

  public EntityType getEntityType(String entityName) {
    EntityType type = entityTypes.get(entityName);
    if (null != type) { return type; }
    type = classEntityTypes.get(entityName);
    // last try by it's interface
    if (null == type) {
      try {
        Class<?> entityClass = ClassLoaders.loadClass(entityName);
        if (Entity.class.isAssignableFrom(entityClass)) type = new EntityType(entityClass);
        else logger.warn("{} 's is not entity", entityClass);
      } catch (Exception e) {
        logger.error("system doesn't contains entity {}", entityName);
      }
    }
    return type;
  }
}
