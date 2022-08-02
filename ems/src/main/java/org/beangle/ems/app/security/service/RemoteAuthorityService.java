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

import com.google.gson.Gson;
import org.beangle.commons.bean.Initializing;
import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.cache.Cache;
import org.beangle.commons.cache.caffeine.CaffeineCacheManager;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Option;
import org.beangle.commons.web.util.HttpUtils;
import org.beangle.ems.app.Ems;
import org.beangle.ems.app.EmsApp;
import org.beangle.ems.app.security.DataPermission;
import org.beangle.ems.app.security.Dimension;
import org.beangle.security.Securities;
import org.beangle.security.core.userdetail.DefaultAccount;
import org.beangle.security.core.userdetail.Profile;
import org.beangle.security.data.Permission;
import org.beangle.security.data.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * http://localhost:8080/platform/user/dimensions/departments.json
 * http://localhost:8080/platform/security/func/edu-lesson-adminapp/permissions/role/1.json
 * http://localhost:8080/platform/security/data/permissions/user/root.json?data=org.openurp.base.edu
 * .model.Student&app=edu-lesson-adminapp
 *
 * @author chaostone
 */
public class RemoteAuthorityService implements ProfileService, Initializing {
  private static final Logger logger = LoggerFactory.getLogger(RemoteAuthorityService.class);

  private Cache<String,DataPermission> sysCache = null;

  private Map<String, Dimension> dimensionMap = CollectUtils.newHashMap();

  private UserDataResolver dataResolver;

  @Override
  public void init() throws Exception {
    sysCache = new CaffeineCacheManager().getCache("user_data_permission", String.class, DataPermission.class);
  }

  public Dimension getDimension(String fieldName) {
    Dimension dimension = dimensionMap.get(fieldName);
    if (null == dimension) {
      String url = Ems.Instance.getApi() + "/platform/user/dimensions/" + fieldName + ".json";
      String resources = HttpUtils.getResponseText(url);
      Map rs = new Gson().fromJson(resources, Map.class);
      if (rs.isEmpty()) return null;
      dimension = toDimension(rs);
      dimensionMap.put(fieldName, dimension);
    }
    return dimension;
  }

  private Dimension toDimension(Map data) {
    Dimension dimension = new Dimension();
    for (Object o : data.entrySet()) {
      Map.Entry<String, Object> entry = (Map.Entry) o;
      PropertyUtils.copyProperty(dimension, entry.getKey(), entry.getValue());
    }
    dimension.setMultiple(true);
    return dimension;
  }

  public List<Profile> getProfiles(String userCode, String function) {
    DefaultAccount account = (DefaultAccount) Securities.getSession().getPrincipal();
    if (null == account.getProfiles()) {
      return Collections.emptyList();
    } else {
      return Arrays.asList(account.getProfiles());
    }
  }

  @SuppressWarnings("rawtypes")
  public Permission getPermission(String user, String dataResource, String functionResource) {
    String key = user + "_" + dataResource;

    Option<DataPermission> ele = sysCache.get(key);
    if (ele.isEmpty()) {
      String url = Ems.Instance.getApi() + "/platform/security/data/permissions/user/" + user + ".json?data="
          + dataResource + "&app=" + EmsApp.getName();
      String resources = HttpUtils.getResponseText(url);
      Map rs = new Gson().fromJson(resources, Map.class);
      if (rs.isEmpty()) return null;
      else {
        DataPermission dp = new DataPermission();
        dp.setFilters((String) rs.get("filters"));
        dp.setActions((String) rs.get("actions"));
        sysCache.put(key, dp);
        return dp;
      }
    } else {
      return ele.get();
    }
  }

  /**
   * 获取数据限制的某个属性的值
   *
   * @param value
   * @param property
   */
  private Object unmarshal(String value, Dimension property) {
    try {
      List<Object> returned = dataResolver.unmarshal(property, value);
      if (property.isMultiple()) return returned;
      else return (1 != returned.size()) ? null : returned.get(0);
    } catch (Exception e) {
      logger.error("exception with param type:" + property.getTypeName() + " value:" + value, e);
      return null;
    }
  }

  public String extractResource(String uri) {
    int lastDot = -1;
    for (int i = 0; i < uri.length(); i++) {
      char a = uri.charAt(i);
      if (a == '.' || a == '!') {
        lastDot = i;
        break;
      }
    }
    if (lastDot < 0) {
      lastDot = uri.length();
    }
    return uri.substring(0, lastDot);
  }

  public void setDataResolver(UserDataResolver dataResolver) {
    this.dataResolver = dataResolver;
  }

}
