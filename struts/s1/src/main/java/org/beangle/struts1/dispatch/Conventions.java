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
package org.beangle.struts1.dispatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package com.ekingstar.commons.web.dispatch:
//            Profile, DispatchUtils

public class Conventions {

  public Conventions() {
  }

  public static void initProfile() {
    try {
      Properties props = new Properties();
      props.load((org.beangle.struts1.dispatch.Conventions.class)
          .getResourceAsStream("/convention_dispatch.properties"));
      methodParam = getProperty(props, "Conventions.methodParam", "method");
      urlSuffix = getProperty(props, "Conventions.urlSuffix", ".do");
      int profileIndex = 0;
      do {
        Profile profile = new Profile();
        String packageName = props.getProperty("profile" + profileIndex + ".packageName");
        if (Strings.isEmpty(packageName)) break;
        profile.setPackageName(packageName);
        String ctlPrefix = props.getProperty("profile" + profileIndex + ".ctlPrefix", packageName + ".");
        if (!ctlPrefix.endsWith(".")) ctlPrefix = ctlPrefix + ".";
        profile.setCtlPrefix(ctlPrefix);
        profile.ctlPostfix = props.getProperty("profile" + profileIndex + ".ctlPostfix");
        if (Strings.isEmpty(profile.ctlPostfix))
          throw new RuntimeException("empty ctlPostfix in profile" + profileIndex);
        profile.pagePrefix = props.getProperty("profile" + profileIndex + ".pagePrefix", "");
        if (!profile.pagePrefix.endsWith(separator + "")) profile.pagePrefix += separator + "";
        profile.pagePostfix = getProperty(props, "profile" + profileIndex + ".pagePostfix", "ftl");
        profile.defaultMethod = getProperty(props, "profile" + profileIndex + ".defaultMethod", "index");
        String simpleURIStyle = props.getProperty("profile" + profileIndex + ".simpleURIStyle", "true");
        if (null != simpleURIStyle && "false".equals(simpleURIStyle)) profile.simpleURIStyle = Boolean.FALSE;
        else profile.simpleURIStyle = Boolean.TRUE;
        profile.setName("profile" + profileIndex);
        profiles.add(profile);
        profileIndex++;
      } while (true);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public static Profile getProfile(Class controllerClass) {
    String className = controllerClass.getName();
    for (Iterator iterator = profiles.iterator(); iterator.hasNext();) {
      Profile profile = (Profile) iterator.next();
      if (profile.isMatch(className)) {
        logger.debug("{} match profile:{}", controllerClass.getName(), profile);
        return profile;
      }
    }

    return null;
  }

  public static StringBuffer getViewName(HttpServletRequest request, Class controllerClass,
      String relativePath) {
    if (Strings.isNotEmpty(relativePath) && relativePath.startsWith(separator + ""))
      return new StringBuffer(relativePath);
    Profile profile = getProfile(controllerClass);
    if (null == profile) throw new RuntimeException("no convention profile for " + controllerClass);
    StringBuffer buf = new StringBuffer();
    buf.append(profile.pagePrefix);
    buf.append(DispatchUtils.getInfix(controllerClass, profile));
    buf.append(separator);
    if (Strings.isEmpty(relativePath)) {
      String method = request.getParameter(methodParam);
      if (Strings.isEmpty(method)) method = profile.defaultMethod;
      if (null == commonMapping.get(method)) buf.append(method);
      else buf.append(commonMapping.get(method));
    } else {
      buf.append(relativePath);
    }
    return buf;
  }

  private static String getProperty(Properties pros, String key, String defaultValue) {
    String value = pros.getProperty(key);
    if (Strings.isEmpty(value)) value = defaultValue;
    return value;
  }

  private static final Logger logger;
  public static Map commonMapping;
  public static List profiles = new ArrayList();
  public static char separator = '/';
  public static String methodParam = "method";
  public static String urlSuffix = ".do";

  static {
    logger = LoggerFactory.getLogger(org.beangle.struts1.dispatch.Conventions.class);
    initProfile();
    commonMapping = new HashMap();
    commonMapping.put("search", "list");
    commonMapping.put("query", "list");
    commonMapping.put("edit", "form");
    commonMapping.put("home", "index");
    commonMapping.put("execute", "index");
    commonMapping.put("add", "new");
  }
}
