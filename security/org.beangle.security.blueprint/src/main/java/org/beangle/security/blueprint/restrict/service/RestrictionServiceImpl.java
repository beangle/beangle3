/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.Permission;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.profile.PropertyMeta;
import org.beangle.security.blueprint.profile.RoleProfile;
import org.beangle.security.blueprint.profile.UserProfile;
import org.beangle.security.blueprint.profile.UserProperty;
import org.beangle.security.blueprint.profile.service.UserDataProvider;
import org.beangle.security.blueprint.profile.service.UserDataResolver;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.service.AuthorityService;
import org.beangle.security.blueprint.service.UserService;

public class RestrictionServiceImpl extends BaseServiceImpl implements RestrictionService {

  protected UserService userService;

  protected Map<String, UserDataProvider> providers = CollectUtils.newHashMap();

  protected UserDataResolver dataResolver;

  protected AuthorityService authorityService;

  /**
   * 查询用户在指定模块的数据权限
   */
  public List<Restriction> getRestrictions(final UserProfile profile, final Resource resource) {
    // // 权限上的限制
    // if (null != resource) {
    // List<RestrictionHolder> authHolders = getAuthorityRestrictions(profile.getUser(), resource);
    // if (!authHolders.isEmpty()) return authHolders;
    // else return Collections.emptyList();
    // } else {
    // List<RestrictionHolder> roleHolders = CollectUtils.newArrayList();
    // // 角色自身限制
    // for (RoleProfile roleProfile : getProfiles(profile.getUser().getRoles(), resource)) {
    // if (!roleProfile.getRestrictions().isEmpty()) {
    // roleHolders.add(roleProfile);
    // }
    // }
    // return roleHolders;
    // }
    return Collections.emptyList();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Collection<RoleProfile> getProfiles(Collection<Role> roles, Resource resource) {
    if (roles.isEmpty()) return Collections.EMPTY_LIST;
    OqlBuilder builder = OqlBuilder.from("from " + Permission.class.getName() + " au,"
        + RoleProfile.class.getName() + " gp");
    builder.where("au.role in (:roles) and au.resource = :resource and au.role=gp.role", roles, resource);
    builder.select("gp");
    return entityDao.search(builder);
  }

  // private List<RestrictionHolder> getAuthorityRestrictions(User user, Resource resource) {
  // OqlBuilder<RestrictionHolder> query = OqlBuilder.hql("from " + Authority.class.getName() +
  // " r "
  // + "join r.role.members as gmember join r.restrictions as restriction"
  // + " where gmember.user=:user and gmember.member=true and r.resource=:resource"
  // + " and restriction.enabled=true");
  // Map<String, Object> params = CollectUtils.newHashMap();
  // params.put("user", user);
  // params.put("resource", resource);
  // query.params(params);
  // return entityDao.search(query);
  // }

  private List<?> getPropertyValues(PropertyMeta field) {
    if (null == field.getSource()) return Collections.emptyList();
    String source = field.getSource();
    String prefix = Strings.substringBefore(source, ":");
    source = Strings.substringAfter(source, ":");
    UserDataProvider provider = providers.get(prefix);
    if (null != provider) {
      return provider.getData(field, source);
    } else {
      throw new RuntimeException("not support data provider:" + prefix);
    }
  }

  public List<?> getPropertyValues(String propertyName) {
    return getPropertyValues(getPropertyMeta(propertyName));
  }

  public Object getPropertyValue(String propertyName, UserProfile profile) {
    PropertyMeta prop = getPropertyMeta(propertyName);
    UserProperty property = profile.getProperty(prop);
    if (null == property) return null;
    return unmarshal(property.getValue(), prop);
  }

  /**
   * 获取数据限制的某个属性的值
   * 
   * @param property
   * @param restriction
   * @return
   */
  private Object unmarshal(String value, PropertyMeta property) {
    try {
      List<Object> returned = dataResolver.unmarshal(property, value);
      if (property.isMultiple()) {
        return returned;
      } else {
        return (1 != returned.size()) ? null : returned.get(0);
      }
    } catch (Exception e) {
      logger.error("exception with param type:" + property.getValueType() + " value:" + value, e);
      return null;
    }
  }

  public void apply(OqlBuilder<?> query, Collection<? extends Restriction> restrictions, UserProfile profile) {
    StringBuilder conBuffer = new StringBuilder();
    List<Object> paramValues = CollectUtils.newArrayList();
    int index = 0;
    for (final Restriction restriction : restrictions) {
      // 处理限制对应的模式
      if (Strings.isEmpty(restriction.getContent())) {
        continue;
      }
      String patternContent = restriction.getContent();
      patternContent = Strings.replace(patternContent, "{alias}", query.getAlias());
      String[] contents = Strings.split(Strings.replace(patternContent, " and ", "$"), "$");

      StringBuilder singleConBuf = new StringBuilder("(");
      for (int i = 0; i < contents.length; i++) {
        String content = contents[i];
        Condition c = new Condition(content);
        List<String> params = c.getParamNames();
        for (final String paramName : params) {
          UserProperty up = profile.getProperty(paramName);
          PropertyMeta prop = up.getMeta();
          String value = null == up ? null : up.getValue();
          if (Strings.isNotEmpty(value)) {
            if (value.equals(Restriction.ALL)) {
              content = "";
            } else {
              content = Strings.replace(content, ":" + prop.getName(), ":" + prop.getName() + index);
              paramValues.add(unmarshal(value, prop));
            }
          } else {
            throw new RuntimeException(paramName + " had not been initialized");
          }
        }
        if (singleConBuf.length() > 1 && content.length() > 0) {
          singleConBuf.append(" and ");
        }
        singleConBuf.append(content);
      }

      if (singleConBuf.length() > 1) {
        singleConBuf.append(')');
        if (conBuffer.length() > 0) {
          conBuffer.append(" or ");
        }
        conBuffer.append(singleConBuf.toString());
      }
      index++;
    }
    if (conBuffer.length() > 0) {
      Condition con = new Condition(conBuffer.toString());
      con.params(paramValues);
      query.where(con);
    }
  }

  private PropertyMeta getPropertyMeta(String fieldName) {
    List<PropertyMeta> fields = entityDao.get(PropertyMeta.class, "name", fieldName);
    if (1 != fields.size()) { throw new RuntimeException("bad pattern parameter named :" + fieldName); }
    return fields.get(0);
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public void setAuthorityService(AuthorityService authorityService) {
    this.authorityService = authorityService;
  }

  public Map<String, UserDataProvider> getProviders() {
    return providers;
  }

  public void setProviders(Map<String, UserDataProvider> providers) {
    this.providers = providers;
  }

  public void setDataResolver(UserDataResolver dataResolver) {
    this.dataResolver = dataResolver;
  }

}
