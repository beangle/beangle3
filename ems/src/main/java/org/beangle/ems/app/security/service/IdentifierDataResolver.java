/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.app.security.service;

import java.util.Collection;
import java.util.List;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.conversion.impl.ConvertUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.app.security.Dimension;

public class IdentifierDataResolver implements UserDataResolver {

  protected EntityDao entityDao;

  public String marshal(Dimension field, Collection<?> items) {
    StringBuilder sb = new StringBuilder();
    for (Object obj : items) {
      try {
        Object value = obj;
        if (null != field.getKeyName()) value = PropertyUtils.getProperty(obj, field.getKeyName());
        sb.append(String.valueOf(value)).append(',');
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (sb.length() > 0) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> unmarshal(Dimension field, String text) {
    if (null == field.getTypeName()) {
      return (List<T>) CollectUtils.newArrayList(Strings.split(text, ","));
    } else {
      Class<?> clazz = null;
      try {
        clazz = Class.forName(field.getTypeName());
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
      EntityType myType = Model.getType(clazz);
      OqlBuilder<T> builder = OqlBuilder.from(myType.getEntityName(), "field");

      String[] ids = Strings.split(text, ",");
      Class<?> propertyType = PropertyUtils.getPropertyType(clazz, field.getKeyName());
      List<Object> realIds = CollectUtils.newArrayList(ids.length);
      for (String id : ids) {
        Object realId = ConvertUtils.convert(id, propertyType);
        realIds.add(realId);
      }
      builder.where("field." + field.getKeyName() + " in (:ids)", realIds).cacheable();
      return entityDao.search(builder);
    }
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

}
