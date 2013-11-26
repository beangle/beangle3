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
package org.beangle.security.blueprint.service.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.Field;
import org.beangle.security.blueprint.Permission;
import org.beangle.security.blueprint.Profile;
import org.beangle.security.blueprint.Property;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserProfile;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.service.ProfileService;
import org.beangle.security.blueprint.service.UserDataProvider;
import org.beangle.security.blueprint.service.UserDataResolver;

public class ProfileServiceImpl extends BaseServiceImpl implements ProfileService {

  protected UserDataResolver dataResolver;

  protected Map<String, UserDataProvider> providers = CollectUtils.newHashMap();

  public Map<String, UserDataProvider> getProviders() {
    return providers;
  }

  public void setProviders(Map<String, UserDataProvider> providers) {
    this.providers = providers;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public List<Profile> getUserProfiles(User user) {
    List profiles = entityDao.search(OqlBuilder.from(UserProfile.class, "up").where("up.user=:user", user));
    return profiles;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Collection<Profile> getProfiles(Collection<Role> roles, Resource resource) {
    if (roles.isEmpty()) return Collections.EMPTY_LIST;
    OqlBuilder builder = OqlBuilder.from("from " + Permission.class.getName() + " au");
    builder.where("au.role in (:roles) and au.resource = :resource", roles, resource);
    builder.select("au.role");
    return entityDao.search(builder);
  }

  public List<?> getFieldValues(Field field, Object... keys) {
    if (null == field.getSource()) return Collections.emptyList();
    String source = field.getSource();
    String prefix = Strings.substringBefore(source, ":");
    source = Strings.substringAfter(source, ":");
    UserDataProvider provider = providers.get(prefix);
    if (null != provider) {
      return provider.getData(field, source, keys);
    } else {
      throw new RuntimeException("not support data provider:" + prefix);
    }
  }

  public Object getProperty(Profile profile, Field field) {
    Property property = profile.getProperty(field);
    if (null == property) return null;
    if ("*".equals(property.getValue())) {
      return getFieldValues(property.getField());
    } else {
      return unmarshal(property.getValue(), field);
    }
  }

  @Override
  public List<Profile> getUserProfiles(User user, FuncResource resource) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * 获取数据限制的某个属性的值
   * 
   * @param property
   * @param restriction
   */
  private Object unmarshal(String value, Field property) {
    try {
      List<Object> returned = dataResolver.unmarshal(property, value);
      if (property.isMultiple()) return returned;
      else return (1 != returned.size()) ? null : returned.get(0);
    } catch (Exception e) {
      logger.error("exception with param type:" + property.getTypeName() + " value:" + value, e);
      return null;
    }
  }

  public Field getField(String fieldName) {
    List<Field> fields = entityDao.get(Field.class, "name", fieldName);
    if (1 != fields.size()) { throw new RuntimeException("bad pattern parameter named :" + fieldName); }
    return fields.get(0);
  }

  public void setDataResolver(UserDataResolver dataResolver) {
    this.dataResolver = dataResolver;
  }

}
