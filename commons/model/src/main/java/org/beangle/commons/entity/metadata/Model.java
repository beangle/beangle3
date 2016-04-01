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
package org.beangle.commons.entity.metadata;

import java.io.Serializable;
import java.util.Map;

import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.impl.ConvertPopulatorBean;
import org.beangle.commons.entity.metadata.impl.DefaultModelMeta;
import org.beangle.commons.entity.metadata.impl.SimpleEntityContext;

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

  public static DefaultModelMeta meta = new DefaultModelMeta();

  static {
    meta.setContext(new SimpleEntityContext());
    meta.setPopulator(new ConvertPopulatorBean());
  }

  private Model() {
  }

  /**
   * <p>
   * newInstance.
   * </p>
   * 
   * @param clazz
   * @param <T> a T object.
   * @return a T object.
   */
  public static <T extends Entity<?>> T newInstance(final Class<T> clazz) {
    return meta.newInstance(clazz);
  }

  /**
   * <p>
   * newInstance.
   * </p>
   */
  public static <T extends Entity<ID>, ID extends Serializable> T newInstance(Class<T> clazz, ID id) {
    return meta.newInstance(clazz, id);
  }

  /**
   * <p>
   * getEntityType.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.EntityType} object.
   */
  public static EntityType getType(String entityName) {
    return meta.getEntityType(entityName);
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
    return meta.getEntityName(obj);
  }

  /**
   * <p>
   * getEntityType.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.entity.metadata.EntityType} object.
   */
  public static EntityType getType(Class<?> clazz) {
    return meta.getEntityType(clazz);
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
  public static void populate(Object entity, Map<String, Object> params) {
    meta.getPopulator().populate(entity, meta.getEntityType(entity.getClass()), params);
  }

  public static Populator getPopulator() {
    return meta.getPopulator();
  }

  public static void setMeta(DefaultModelMeta meta) {
    Model.meta = meta;
  }

}
