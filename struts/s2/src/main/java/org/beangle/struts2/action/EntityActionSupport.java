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
package org.beangle.struts2.action;

import java.lang.reflect.Array;
import java.util.Map;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.lang.Arrays;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.PopulateHelper;
import org.beangle.struts2.helper.QueryHelper;

/**
 * @author chaostone
 * @since 3.0.0
 */
public class EntityActionSupport extends ActionSupport {

  /**
   * Get entity's id from shortname.id,shortnameId,id
   *
   * @param name
   * @param clazz
   */
  protected final <T> T getId(String name, Class<T> clazz) {
    Object[] entityIds = getAll(name + ".id");
    if (Arrays.isEmpty(entityIds)) entityIds = getAll(name + "Id");
    if (Arrays.isEmpty(entityIds)) entityIds = getAll("id");
    if (Arrays.isEmpty(entityIds)) return null;
    else {
      String entityId = entityIds[0].toString();
      int commaIndex = entityId.indexOf(',');
      if (commaIndex != -1) entityId = entityId.substring(0, commaIndex);
      return Params.converter.convert(entityId, clazz);
    }
  }

  protected final Integer getIntId(String shortName) {
    return getId(shortName, Integer.class);
  }

  protected final Long getLongId(String shortName) {
    return getId(shortName, Long.class);
  }

  /**
   * Get entity's long id array from parameters shortname.id,shortname.ids,shortnameIds
   *
   * @param shortName
   */
  protected final Long[] getLongIds(String shortName) {
    return getIds(shortName, Long.class);
  }

  /**
   * Get entity's long id array from parameters shortname.id,shortname.ids,shortnameIds
   *
   * @param shortName
   */
  protected final Integer[] getIntIds(String shortName) {
    return getIds(shortName, Integer.class);
  }

  /**
   * Get entity's id array from parameters shortname.id,shortname.ids,shortnameIds
   *
   * @param name
   * @param clazz
   * @return empty array if not found
   */
  protected final <T> T[] getIds(String name, Class<T> clazz) {
    T[] datas = Params.getAll(name + ".id", clazz);
    if (null == datas) {
      String datastring = Params.get(name + ".ids");
      if (null == datastring) datastring = Params.get(name + "Ids");
      if (null == datastring) Array.newInstance(clazz, 0);
      else return Params.converter.convert(Strings.split(datastring, ","), clazz);
    }
    return datas;
  }

  // populate------------------------------------------------------------------
  /**
   * 将request中的参数设置到clazz对应的bean。
   *
   * @param clazz
   * @param shortName
   */
  protected final <T> T populate(Class<T> clazz, String shortName) {
    return PopulateHelper.populate(clazz, shortName);
  }

  protected final void populate(Object obj, String shortName) {
    Model.populate(obj, Params.sub(shortName));
  }

  protected final Object populate(Class<?> clazz) {
    return PopulateHelper.populate(clazz);
  }

  protected final Object populate(String entityName) {
    return PopulateHelper.populate(entityName);
  }

  protected final Object populate(String entityName, String shortName) {
    return PopulateHelper.populate(entityName, shortName);
  }

  protected final Object populate(Object obj, String entityName, String shortName) {
    return PopulateHelper.populate(obj, entityName, shortName);
  }

  protected final void populate(Entity<?> entity, String entityName, Map<String, Object> params) {
    Assert.notNull(entity, "Cannot populate to null.");
    Model.getPopulator().populate(entity, Model.getType(entityName), params);
  }

  protected final void populate(Entity<?> entity, Map<String, Object> params) {
    Assert.notNull(entity, "Cannot populate to null.");
    Model.populate(entity, params);
  }

  // query------------------------------------------------------
  protected final int getPageIndex() {
    return QueryHelper.getPageIndex();
  }

  protected final int getPageSize() {
    return QueryHelper.getPageSize();
  }

  /**
   * 从request的参数或者cookie中(参数优先)取得分页信息
   */
  protected final PageLimit getPageLimit() {
    return QueryHelper.getPageLimit();
  }

  protected final void populateConditions(OqlBuilder<?> builder) {
    QueryHelper.populateConditions(builder);
  }

  protected final void populateConditions(OqlBuilder<?> builder, String exclusiveAttrNames) {
    QueryHelper.populateConditions(builder, exclusiveAttrNames);
  }

}
