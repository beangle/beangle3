/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.entity.metadata.impl;

import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.EntityContext;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.ModelMeta;
import org.beangle.commons.entity.metadata.Populator;
import org.beangle.commons.entity.metadata.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 * @since 3.0.0
 */

public class DefaultModelMeta implements ModelMeta {

  private static final Logger logger = LoggerFactory.getLogger(DefaultModelMeta.class);

  protected EntityContext context;

  protected Populator populator;

  /**
   * <p>
   * newInstance.
   * </p>
   * 
   * @param clazz
   * @param <T> a T object.
   * @return a T object.
   */
  @SuppressWarnings("unchecked")
  public <T extends Entity<?>> T newInstance(final Class<T> clazz) {
    return (T) getEntityType(clazz).newInstance();
  }

  /**
   * <p>
   * newInstance.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @param id a {@link java.io.Serializable} object.
   * @param <T> a T object.
   * @return a T object.
   */
  public <T extends Entity<ID>, ID extends Serializable> T newInstance(final Class<T> clazz, final ID id) {
    EntityType type = getEntityType(clazz);
    @SuppressWarnings("unchecked")
    T entity = (T) type.newInstance();
    try {
      PropertyUtils.setProperty(entity, type.getIdName(), id);
    } catch (Exception e) {
      logger.error("initialize {} with id {} error", clazz, id);
    }
    return entity;
  }

  /**
   * <p>
   * getEntityType.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.EntityType} object.
   */
  public EntityType getEntityType(String entityName) {
    return context.getEntityType(entityName);
  }

  /**
   * <p>
   * getType.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.Type} object.
   */
  public Type getType(String entityName) {
    return context.getType(entityName);
  }

  /**
   * <p>
   * getEntityName.
   * </p>
   * 
   * @param obj a {@link java.lang.Object} object.
   * @return a {@link java.lang.String} object.
   */
  public String getEntityName(Object obj) {
    return context.getEntityName(obj);
  }

  /**
   * <p>
   * getEntityType.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.entity.metadata.EntityType} object.
   */
  public EntityType getEntityType(Class<?> clazz) {
    EntityType type = context.getEntityType(clazz);
    if (null == type) {
      type = new EntityType(clazz);
    }
    return type;
  }

  public void setContext(EntityContext context) {
    this.context = context;
  }

  public void setPopulator(Populator populator) {
    this.populator = populator;
  }

  public Populator getPopulator() {
    return populator;
  }

}
