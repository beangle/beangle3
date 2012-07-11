/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.metadata.impl;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.Entity;
import org.beangle.commons.dao.metadata.EntityContext;
import org.beangle.commons.dao.metadata.EntityType;
import org.beangle.commons.dao.metadata.IdentifierType;
import org.beangle.commons.dao.metadata.Type;
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

  /** {@inheritDoc} */
  public String[] getEntityNames(Class<?> clazz) {
    return new String[0];
  }

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
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
   * @return a {@link org.beangle.commons.dao.metadata.EntityType} object.
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

  /** {@inheritDoc} */
  public EntityType getEntityType(String entityName) {
    EntityType type = entityTypes.get(entityName);
    if (null != type) { return type; }
    type = classEntityTypes.get(entityName);
    // last try by it's interface
    if (null == type) {
      try {
        // FIXME
        Class<?> entityClass = Class.forName(entityName);
        if (Entity.class.isAssignableFrom(entityClass)) {
          type = new EntityType(entityClass);
        } else {
          logger.warn("{} 's is not entity", entityClass);
        }
      } catch (ClassNotFoundException e) {
        logger.error("system doesn't contains entity {}", entityName);
      }
    }
    return type;
  }
}
