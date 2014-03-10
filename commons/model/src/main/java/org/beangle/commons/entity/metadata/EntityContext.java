/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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

/**
 * <p>
 * EntityContext interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface EntityContext {
  /**
   * 根据实体名查找实体类型
   * 
   * @param name a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.Type} object.
   */
  Type getType(String name);

  /**
   * 根据实体名查找实体类型
   * 
   * @param entityName a {@link java.lang.String} object.
   * @return null, if cannot find entityType
   */
  EntityType getEntityType(String entityName);

  /**
   * 根据类型,查找实体类型<br>
   * 找到实体名或者实体类名和指定类类名相同的entityType
   * 
   * @param entityClass a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.entity.metadata.EntityType} object.
   */
  EntityType getEntityType(Class<?> entityClass);

  /**
   * 一个具体类所对应的实体名数组.
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @return an array of {@link java.lang.String} objects.
   */
  String[] getEntityNames(Class<?> clazz);

  /**
   * 根据对象返回实体名
   * 
   * @param obj a {@link java.lang.Object} object.
   * @return a {@link java.lang.String} object.
   */
  String getEntityName(Object obj);

}
