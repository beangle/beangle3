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
package org.beangle.struts2.helper;

import java.util.Map;

import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.entity.util.EntityUtils;

public class PopulateHelper {

  /**
   * 将request中的参数设置到clazz对应的bean。
   * 
   * @param clazz
   * @param name
   */
  @SuppressWarnings("unchecked")
  public static <T> T populate(Class<T> clazz, String name) {
    EntityType type = null;
    if (clazz.isInterface()) {
      type = Model.getEntityType(clazz.getName());
    } else {
      type = Model.getEntityType(clazz);
    }
    return (T) populate(type.newInstance(), type.getEntityName(), name);
  }

  @SuppressWarnings("unchecked")
  public static <T> T populate(Class<T> clazz) {
    EntityType type = null;
    if (clazz.isInterface()) {
      type = Model.getEntityType(clazz.getName());
    } else {
      type = Model.getEntityType(clazz);
    }
    String entityName = type.getEntityName();
    return (T) populate(type.newInstance(), entityName, EntityUtils.getCommandName(entityName));
  }

  public static Object populate(String entityName) {
    EntityType type = Model.getEntityType(entityName);
    return populate(type.newInstance(), type.getEntityName(), EntityUtils.getCommandName(entityName));
  }

  public static Object populate(String entityName, String name) {
    EntityType type = Model.getEntityType(entityName);
    return populate(type.newInstance(), type.getEntityName(), name);
  }

  public static Object populate(Object obj, String entityName, String name) {
    Map<String, Object> params = Params.sub(name);
    return Model.getPopulator().populate(obj, entityName, params);
  }
}
