/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.service.internal;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.data.DataField;
import org.beangle.security.blueprint.data.service.UserDataResolver;
import org.springframework.beans.BeanUtils;

public class IdentifierDataResolver implements UserDataResolver {

  protected EntityDao entityDao;

  public String marshal(DataField field, Collection<?> items) {
    StringBuilder sb = new StringBuilder();
    for (Object obj : items) {
      try {
        Object value = obj;
        if (null != field.getKeyName()) {
          value = PropertyUtils.getProperty(obj, field.getKeyName());
        }
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
  public <T> List<T> unmarshal(DataField field, String text) {
    if (null == field.getValueType()) {
      return (List<T>) CollectUtils.newArrayList(Strings.split(text, ","));
    } else {
      Class<?> clazz = null;
      try {
        clazz = Class.forName(field.getValueType());
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
      EntityType myType = Model.getEntityType(clazz);
      OqlBuilder<T> builder = OqlBuilder.from(myType.getEntityName(), "restrictField");

      String[] ids = Strings.split(text, ",");
      PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, field.getKeyName());
      Class<?> propertyType = pd.getReadMethod().getReturnType();
      List<Object> realIds = CollectUtils.newArrayList(ids.length);
      for (String id : ids) {
        Object realId = ConvertUtils.convert(id, propertyType);
        realIds.add(realId);
      }
      builder.where("restrictField." + field.getKeyName() + " in (:ids)", realIds).cacheable();
      return entityDao.search(builder);
    }
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

}
