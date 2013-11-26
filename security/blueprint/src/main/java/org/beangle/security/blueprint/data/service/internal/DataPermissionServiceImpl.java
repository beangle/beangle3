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
package org.beangle.security.blueprint.data.service.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.Conditions;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.functor.Predicate;
import org.beangle.security.blueprint.Field;
import org.beangle.security.blueprint.Profile;
import org.beangle.security.blueprint.Property;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserProfile;
import org.beangle.security.blueprint.data.DataPermission;
import org.beangle.security.blueprint.data.model.DataPermissionBean;
import org.beangle.security.blueprint.data.service.DataPermissionService;
import org.beangle.security.blueprint.function.service.FuncPermissionService;
import org.beangle.security.blueprint.service.UserDataResolver;
import org.beangle.security.blueprint.service.UserService;

public class DataPermissionServiceImpl extends BaseServiceImpl implements DataPermissionService {

  protected UserService userService;

  protected UserDataResolver dataResolver;

  protected FuncPermissionService permissionService;

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
    CollectUtils.filter(rs, new Predicate<DataPermissionBean>() {
      public Boolean apply(DataPermissionBean dp) {
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
  public DataPermission getPermission(User user, String dataResourceName, String funcResourceName) {
    for (Role role : user.getRoles()) {
      List<? extends DataPermission> permissions = getPermissions(role, dataResourceName, funcResourceName);
      if (!permissions.isEmpty()) return permissions.get(0);
    }
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

  @Override
  public void apply(OqlBuilder<?> builder, DataPermission permission, UserProfile... profiles) {
    apply(builder, permission, Arrays.asList(profiles));
  }

  public void apply(OqlBuilder<?> query, DataPermission permission, List<UserProfile> profiles) {
    List<Object> paramValues = CollectUtils.newArrayList();
    // 处理限制对应的模式
    if (Strings.isEmpty(permission.getFilters())) return;

    String patternContent = permission.getFilters();
    patternContent = Strings.replace(patternContent, "{alias}", query.getAlias());
    String[] contents = Strings.split(Strings.replace(patternContent, " and ", "$"), "$");

    List<Condition> conditions = CollectUtils.newArrayList();
    for (Profile profile : profiles) {
      StringBuilder conBuilder = new StringBuilder("(");
      for (int i = 0; i < contents.length; i++) {
        String content = contents[i];
        Condition c = new Condition(content);
        List<String> params = c.getParamNames();
        for (final String paramName : params) {

          Property up = profile.getProperty(paramName);
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
        conditions.add(con);
      }
    }
    query.where(Conditions.or(conditions));
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public void setPermissionService(FuncPermissionService permissionService) {
    this.permissionService = permissionService;
  }

  public void setDataResolver(UserDataResolver dataResolver) {
    this.dataResolver = dataResolver;
  }

}
