/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.metadata;

import java.io.Serializable;
import java.util.Map;

/**
 * Model meta data
 * 
 * @author chaostone
 * @since 3.0.0
 */
public interface ModelMeta {

  /**
   * <p>
   * newInstance.
   * </p>
   * 
   * @param clazz
   * @param <T> a T object.
   * @return a T object.
   */
  <T> T newInstance(final Class<T> clazz);

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
  <T> T newInstance(final Class<T> clazz, final Serializable id);

  /**
   * <p>
   * getEntityType.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.EntityType} object.
   */
  EntityType getEntityType(String entityName);

  /**
   * <p>
   * getType.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.Type} object.
   */
  Type getType(String entityName);

  /**
   * <p>
   * getEntityName.
   * </p>
   * 
   * @param obj a {@link java.lang.Object} object.
   * @return a {@link java.lang.String} object.
   */
  String getEntityName(Object obj);

  /**
   * <p>
   * getEntityType.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.entity.metadata.EntityType} object.
   */
  EntityType getEntityType(Class<?> clazz);

  /**
   * 将params中的属性([attr(string)->value(object)]，放入到实体类中。<br>
   * 如果引用到了别的实体，那么<br>
   * 如果params中的id为null，则将该实体的置为null.<br>
   * 否则新生成一个实体，将其id设为params中指定的值。 空字符串按照null处理
   * 
   * @param params a {@link java.util.Map} object.
   * @param entity a {@link java.lang.Object} object.
   */
  void populate(Map<String, Object> params, Object entity);

  /**
   * Returns populator
   */
  Populator getPopulator();
}
