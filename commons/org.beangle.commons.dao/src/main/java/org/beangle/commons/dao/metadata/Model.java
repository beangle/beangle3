/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.metadata;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.dao.metadata.impl.DefaultModelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Model class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public final class Model {

  /** Constant <code>NULL="null"</code> */
  public static final String NULL = "null";

  private static final Logger logger = LoggerFactory.getLogger(Model.class);

  private static EntityContext context;

  private static Populator populator;

  private static Model instance = new Model();

  static {
    new DefaultModelBuilder().build();
  }

  private Model() {
  }

  /**
   * <p>
   * newInstance.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @param <T> a T object.
   * @return a T object.
   */
  @SuppressWarnings("unchecked")
  public static <T> T newInstance(final Class<T> clazz) {
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
  public static <T> T newInstance(final Class<T> clazz, final Serializable id) {
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
   * @return a {@link org.beangle.commons.dao.metadata.EntityType} object.
   */
  public static EntityType getEntityType(String entityName) {
    return context.getEntityType(entityName);
  }

  /**
   * <p>
   * getType.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.metadata.Type} object.
   */
  public static Type getType(String entityName) {
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
  public static String getEntityName(Object obj) {
    return context.getEntityName(obj);
  }

  /**
   * <p>
   * getEntityType.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.dao.metadata.EntityType} object.
   */
  public static EntityType getEntityType(Class<?> clazz) {
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
  public static void populate(Map<String, Object> params, Object entity) {
    populator.populate(entity, params);
  }

  /**
   * <p>
   * Getter for the field <code>context</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.metadata.EntityContext} object.
   */
  public static EntityContext getContext() {
    return context;
  }

  /**
   * <p>
   * Setter for the field <code>context</code>.
   * </p>
   * 
   * @param context a {@link org.beangle.commons.dao.metadata.EntityContext} object.
   */
  public static void setContext(EntityContext context) {
    Model.context = context;
  }

  /**
   * <p>
   * Getter for the field <code>populator</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.metadata.Populator} object.
   */
  public static Populator getPopulator() {
    return populator;
  }

  /**
   * <p>
   * Setter for the field <code>populator</code>.
   * </p>
   * 
   * @param populator a {@link org.beangle.commons.dao.metadata.Populator} object.
   */
  public static void setPopulator(Populator populator) {
    Model.populator = populator;
  }

  /**
   * <p>
   * Getter for the field <code>instance</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.metadata.Model} object.
   */
  public static Model getInstance() {
    return instance;
  }

}
