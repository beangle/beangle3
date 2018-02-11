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
package org.beangle.struts2.convention.route.impl;

import java.util.HashMap;
import java.util.Map;

import org.beangle.commons.lang.Strings;
import org.beangle.struts2.convention.Constants;
import org.beangle.struts2.convention.route.Profile;
import org.beangle.struts2.convention.route.ProfileService;
import org.beangle.struts2.convention.route.ViewMapper;

import com.opensymphony.xwork2.inject.Inject;

public class DefaultViewMapper implements ViewMapper {

  private Map<String, String> methodViews = new HashMap<String, String>();

  private ProfileService profileServie;

  public DefaultViewMapper() {
    super();
    methodViews.put("search", "list");
    methodViews.put("query", "list");
    methodViews.put("edit", "form");
    methodViews.put("home", "index");
    methodViews.put("execute", "index");
    methodViews.put("add", "new");
  }

  /**
   * 查询control对应的view的名字(没有后缀)
   * 
   * @param className
   * @param methodName
   * @param viewName
   */
  public String getViewPath(String className, String methodName, String viewName) {
    if (Strings.isNotEmpty(viewName)) {
      if (viewName.charAt(0) == Constants.separator) { return viewName; }
    }
    Profile profile = profileServie.getProfile(className);
    if (null == profile) { throw new RuntimeException("no convention profile for " + className); }
    StringBuilder buf = new StringBuilder();
    if (profile.getViewPathStyle().equals(Constants.FULL_VIEWPATH)) {
      buf.append(Constants.separator);
      buf.append(profile.getFullPath(className));
    } else if (profile.getViewPathStyle().equals(Constants.SIMPLE_VIEWPATH)) {
      buf.append(profile.getViewPath());
      // 添加中缀路径
      buf.append(profile.getInfix(className));
    } else if (profile.getViewPathStyle().equals(Constants.SEO_VIEWPATH)) {
      buf.append(profile.getViewPath());
      buf.append(Strings.unCamel(profile.getInfix(className)));
    } else {
      throw new RuntimeException(profile.getViewPathStyle() + " was not supported");
    }
    // add method mapping path
    buf.append(Constants.separator);
    if (Strings.isEmpty(viewName) || viewName.equals("success")) {
      viewName = methodName;
    }

    if (null == methodViews.get(viewName)) {
      buf.append(viewName);
    } else {
      buf.append(methodViews.get(viewName));
    }
    return buf.toString();
  }

  @Inject
  public void setProfileServie(ProfileService profileServie) {
    this.profileServie = profileServie;
  }
}
