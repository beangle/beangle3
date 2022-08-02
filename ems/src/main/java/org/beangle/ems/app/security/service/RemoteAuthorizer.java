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
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.HttpUtils;
import org.beangle.security.authz.AbstractRoleBasedAuthorizer;
import org.beangle.security.authz.Authority;
import org.beangle.security.authz.AuthorityDomain;
import org.beangle.ems.app.Ems;
import org.beangle.ems.app.EmsApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 根据REST接口查找root用户和资源
 * http://localhost:8080/platform/user/accounts/root.json
 * http://localhost:8080/platform/security/func/edu-lesson-adminapp/resources.json
 */
public class RemoteAuthorizer extends AbstractRoleBasedAuthorizer {
  private static final Logger logger = LoggerFactory.getLogger(RemoteAuthorizer.class);

  @Override
  protected AuthorityDomain fetchDomain() {
    Set<String> roots = getRoots();
    List<Authority> authorityList = getResources();
    Map<String, Authority> authorities = CollectUtils.newHashMap();
    for (Authority a : authorityList) {
      authorities.put(a.getResourceName(), a);
    }
    return new AuthorityDomain(roots, authorities);
  }

  public static Set<String> getRoots() {
    String url = Ems.Instance.getApi() + "/platform/user/roots.json?app=" + EmsApp.getName();
    try {
      String resources = HttpUtils.getResponseText(url);
      List rs = new Gson().fromJson(resources, List.class);
      return new HashSet<String>(rs);
    } catch (Exception e) {
      logger.error("Cannot access {}", url);
      return Collections.emptySet();
    }
  }

  public static List<Authority> getResources() {
    String url = Ems.Instance.getApi() + "/platform/security/func/" + EmsApp.getName() + "/resources.json";
    try {
      String resources = HttpUtils.getResponseText(url);
      return toAuthorities(resources);
    } catch (Exception e) {
      logger.error("Cannot access {}", url);
      return Collections.emptyList();
    }
  }

  public static List<Authority> toAuthorities(String resources) {
    if(Strings.isEmpty(resources)){
      return Collections.emptyList();
    }
    List rs = new Gson().fromJson(resources, List.class);
    Set<String> s = CollectUtils.newHashSet();
    List<Authority> authorities = CollectUtils.newArrayList();
    for (Object o : rs) {
      Map<String, Object> m = (Map<String, Object>) o;
      s.add(m.get("name").toString());
      List roleList = (List) m.get("roles");
      Set<String> roles = Collections.emptySet();
      if (null != roleList) {
        roles = (Set<String>) roleList.stream().map(x -> String.valueOf(((Number) x).intValue())).collect(Collectors.toSet());
      }
      Authority a = new Authority(m.get("name").toString(), m.get("scope").toString(), roles);
      authorities.add(a);
    }
    return authorities;
  }

}
