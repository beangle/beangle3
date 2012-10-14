/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.metadata.impl;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
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
  public <T> T newInstance(final Class<T> clazz) {
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
  public <T> T newInstance(final Class<T> clazz, final Serializable id) {
    @SuppressWarnings("unchecked")
    T entity = (T) getEntityType(clazz).newInstance();
    try {
      PropertyUtils.setProperty(entity, "id", id);
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

  /**
   * 将params中的属性([attr(string)->value(object)]，放入到实体类中。<br>
   * 如果引用到了别的实体，那么<br>
   * 如果params中的id为null，则将该实体的置为null.<br>
   * 否则新生成一个实体，将其id设为params中指定的值。 空字符串按照null处理
   * 
   * @param params a {@link java.util.Map} object.
   * @param entity a {@link java.lang.Object} object.
   */
  public void populate(Map<String, Object> params, Object entity) {
    populator.populate(entity, params);
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
