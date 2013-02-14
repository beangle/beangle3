/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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

import org.beangle.commons.entity.Entity;

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
  <T extends Entity<?>> T newInstance(final Class<T> clazz);

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
  <T extends Entity<ID>, ID extends Serializable> T newInstance(final Class<T> clazz, final ID id);

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
   * @param name collectionname or entityname
   */
  Type getType(String name);

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
   * Returns populator
   */
  Populator getPopulator();
}
