/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.service.internal;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.bean.converters.Converters;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.reflect.Reflections;
import org.beangle.security.blueprint.data.ProfileField;
import org.beangle.security.blueprint.data.service.UserDataProvider;

public class OqlDataProvider extends BaseServiceImpl implements UserDataProvider {

  @SuppressWarnings("unchecked")
  public <T> List<T> getData(ProfileField field, String source, Object... keys) {
    Map<String, Object> params = CollectUtils.newHashMap();
    if (keys.length > 0) {
      String lowerSourse = source.toLowerCase();
      int index = lowerSourse.indexOf("order ");
      if (-1 == index) index = source.length();
      boolean hasCondition = lowerSourse.contains(" where ");
      source = source.substring(0, index) + (hasCondition ? " and " : " where ")
          + field.getType().getKeyName() + " in (:ids)";
      Set<Object> newIds = CollectUtils.newHashSet(keys);
      if (null != field.getType().getKeyName()) {
        Class<?> keyType = Reflections.getPropertyType(ClassLoaders.loadClass(field.getType().getTypeName()),
            field.getType().getKeyName());
        if (!keys[0].getClass().equals(keyType)) {
          newIds = CollectUtils.newHashSet();
          for (Object key : keys) {
            newIds.add(Converters.Instance.convert(key, keyType));
          }
        }
      }
      params.put("ids", newIds);
    }
    return (List<T>) entityDao.searchHQLQuery(source, params);
  }

  public String getName() {
    return "oql";
  }

}
