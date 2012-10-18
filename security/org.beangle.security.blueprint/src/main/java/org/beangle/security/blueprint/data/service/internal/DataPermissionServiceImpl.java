/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.service.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.Permission;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.data.*;
import org.beangle.security.blueprint.data.model.DataPermissionBean;
import org.beangle.security.blueprint.data.service.DataPermissionService;
import org.beangle.security.blueprint.data.service.UserDataProvider;
import org.beangle.security.blueprint.data.service.UserDataResolver;
import org.beangle.security.blueprint.function.service.FuncPermissionService;
import org.beangle.security.blueprint.service.UserService;

public class DataPermissionServiceImpl extends BaseServiceImpl implements DataPermissionService {

  protected UserService userService;

  protected Map<String, UserDataProvider> providers = CollectUtils.newHashMap();

  protected UserDataResolver dataResolver;

  protected FuncPermissionService permissionService;

  public List<UserProfile> getUserProfiles(User user) {
    return entityDao.search(OqlBuilder.from(UserProfile.class, "up").where("up.user=:user", user));
  }

  public RoleProfile getRoleProfile(Role role) {
    return entityDao.uniqueResult(OqlBuilder.from(RoleProfile.class, "rp").where("rp.role=:role", role)
        .cacheable());
  }

  /**
   * 查找数据资源和功能资源对应的数据权限。
   * <p>
   * 默认按照声明资源的数据权限为先。
   * 
   * @param role
   * @param dataResource
   */
  private List<? extends DataPermission> getPermissions(Role role, String dataResourceName,
      String funcResourceName) {
    OqlBuilder<DataPermissionBean> builder = OqlBuilder.from(DataPermissionBean.class, "dp")
        .where("dp.resource.name=:name", dataResourceName).cacheable();
    List<DataPermissionBean> rs = entityDao.search(builder);

    final String roleName = role.getName();
    final String funcResourceName1 = funcResourceName;
    final Date now = new Date();
    CollectionUtils.filter(rs, new Predicate() {
      public boolean evaluate(Object object) {
        DataPermissionBean dp = (DataPermissionBean) object;
        if (null != dp.getEffectiveAt() && now.before(dp.getEffectiveAt())) return false;
        if (null != dp.getInvalidAt() && now.after(dp.getInvalidAt())) return false;
        if (dp.getRole() == null || dp.getRole().getName().equals(roleName)) {
          if (dp.getFuncResource() == null || dp.getFuncResource().getName().equals(funcResourceName1)) { return true; }
        }
        return false;
      }
    });

    Collections.sort(rs, new Comparator<DataPermissionBean>() {
      static final int general = 4;
      static final int onlyRoleMatch = 3;
      static final int onlyFuncMatch = 2;
      static final int matchAll = 1;

      public int compare(DataPermissionBean lhs, DataPermissionBean rhs) {
        return getWeight(lhs) - getWeight(rhs);
      }

      private int getWeight(DataPermissionBean dp) {
        if (dp.getRole() == null && dp.getFuncResource() == null) { return general; }
        if (dp.getRole() != null && dp.getFuncResource() == null) { return onlyRoleMatch; }
        if (dp.getRole() == null && dp.getFuncResource() != null) { return onlyFuncMatch; }
        return matchAll;
      }
    });
    return rs;
  }

  /**
   * 查询用户在指定模块的数据权限
   */
  public DataPermission getPermission(Long userId, String dataResourceName, String funcResourceName) {
    List<Role> roles = userService.getRoles(userId);
    for (Role role : roles) {
      List<? extends DataPermission> permissions = getPermissions(role, dataResourceName, funcResourceName);
      if (!permissions.isEmpty()) { return permissions.get(0); }
    }
    return null;
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

  public List<?> getFieldValues(ProfileField field, Object... keys) {
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

  public Object getPropertyValue(ProfileField field, Profile profile) {
    Property property = profile.getProperty(field);
    if (null == property) return null;
    return unmarshal(property.getValue(), field);
  }

  /**
   * 获取数据限制的某个属性的值
   * 
   * @param property
   * @param restriction
   */
  private Object unmarshal(String value, ProfileField property) {
    try {
      List<Object> returned = dataResolver.unmarshal(property, value);
      if (property.isMultiple()) {
        return returned;
      } else {
        return (1 != returned.size()) ? null : returned.get(0);
      }
    } catch (Exception e) {
      logger.error("exception with param type:" + property.getType().getTypeName() + " value:" + value, e);
      return null;
    }
  }

  public void apply(OqlBuilder<?> query, DataPermission permission, UserProfile profile) {
    List<Object> paramValues = CollectUtils.newArrayList();
    // 处理限制对应的模式
    if (Strings.isEmpty(permission.getFilters())) return;

    String patternContent = permission.getFilters();
    patternContent = Strings.replace(patternContent, "{alias}", query.getAlias());
    String[] contents = Strings.split(Strings.replace(patternContent, " and ", "$"), "$");

    StringBuilder conBuilder = new StringBuilder("(");
    for (int i = 0; i < contents.length; i++) {
      String content = contents[i];
      Condition c = new Condition(content);
      List<String> params = c.getParamNames();
      for (final String paramName : params) {
        UserProperty up = profile.getProperty(paramName);
        String value = null == up ? null : up.getValue();
        if (Strings.isNotEmpty(value)) {
          if (value.equals(Property.AllValue)) {
            content = "";
          } else {
            paramValues.add(unmarshal(value, up.getField()));
          }
        } else {
          throw new RuntimeException(paramName + " had not been initialized");
        }
      }
      if (conBuilder.length() > 1 && content.length() > 0) conBuilder.append(" and ");
      conBuilder.append(content);
    }

    if (conBuilder.length() > 1) {
      conBuilder.append(')');
      Condition con = new Condition(conBuilder.toString());
      con.params(paramValues);
      query.where(con);
    }
  }

  public ProfileField getProfileField(String fieldName) {
    List<ProfileField> fields = entityDao.get(ProfileField.class, "name", fieldName);
    if (1 != fields.size()) { throw new RuntimeException("bad pattern parameter named :" + fieldName); }
    return fields.get(0);
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public void setPermissionService(FuncPermissionService permissionService) {
    this.permissionService = permissionService;
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
