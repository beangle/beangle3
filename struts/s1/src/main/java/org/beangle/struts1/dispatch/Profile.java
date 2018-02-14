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

import java.util.HashMap;
import java.util.Map;

import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;

public class Profile implements Comparable {

  public Profile() {
    ctlPrefix = "";
    ctlPostfix = "";
    pagePrefix = "";
    pagePostfix = "";
    defaultMethod = "index";
    simpleURIStyle = Boolean.TRUE;
    useCache = true;
    reserveMatched = true;
    cache = new HashMap();
  }

  public MatchInfo getCtlMatchInfo(String className) {
    if (useCache) {
      MatchInfo match = (MatchInfo) cache.get(className);
      if (null == match) {
        match = getMatchInfo(ctlPrefixSegs, className);
        if (-1 != match.startIndex) synchronized (cache) {
          cache.put(className, match);
        }
      }
      return match;
    } else {
      return getMatchInfo(ctlPrefixSegs, className);
    }
  }

  public boolean isMatch(String className) {
    if (useCache) {
      MatchInfo match = (MatchInfo) cache.get(className);
      if (null == match) {
        match = getMatchInfo(packageSegs, className);
        if (-1 != match.startIndex) {
          synchronized (cache) {
            cache.put(className, getMatchInfo(ctlPrefixSegs, className));
          }
          return true;
        } else {
          return false;
        }
      } else {
        return -1 != match.startIndex;
      }
    } else {
      return -1 != getMatchInfo(packageSegs, className).startIndex;
    }
  }

  public MatchInfo getMatchInfo(String pattens[], String className) {
    String sub = className;
    int index = 0;
    MatchInfo match = new MatchInfo(-1);
    for (int i = 0; i < pattens.length; i++) {
      int subIndex = sub.indexOf(pattens[i]);
      if (-1 == subIndex) return match;
      if (0 != subIndex && reserveMatched) match.reserved.append(sub.substring(0, subIndex)).append(".");
      index += subIndex + pattens[i].length();
      if (i == pattens.length - 1) continue;
      sub = sub.substring(subIndex + pattens[i].length());
      if (Strings.isEmpty(sub)) {
        match.startIndex = className.length() - 1;
        return match;
      }
    }

    match.startIndex = index - 1;
    return match;
  }

  public int compareTo(Object object) {
    Profile myClass = (Profile) object;
    return myClass.packageName.compareTo(packageName);
  }

  public String toString() {
    return Objects.toStringBuilder(this).add("name", name).add("packageName", packageName)
        .add("ctlPrefix", ctlPrefix).add("ctlPostfix", ctlPostfix).add("pagePrefix", pagePrefix)
        .add("pagePostfix", pagePostfix).add("defaultMethod", defaultMethod)
        .add("simpleURIStyle", simpleURIStyle).toString();
  }

  public String getCtlPrefix() {
    return ctlPrefix;
  }

  public void setCtlPrefix(String ctlPrefix) {
    this.ctlPrefix = ctlPrefix;
    ctlPrefixSegs = Strings.split(ctlPrefix, '*');
  }

  public String getPagePrefix() {
    return pagePrefix;
  }

  public void setPagePrefix(String pagePrefix) {
    this.pagePrefix = pagePrefix;
  }

  public String getCtlPostfix() {
    return ctlPostfix;
  }

  public void setCtlPostfix(String ctlPostfix) {
    this.ctlPostfix = ctlPostfix;
  }

  public String getPagePostfix() {
    return pagePostfix;
  }

  public void setPagePostfix(String pagePostfix) {
    this.pagePostfix = pagePostfix;
  }

  public String getDefaultMethod() {
    return defaultMethod;
  }

  public void setDefaultMethod(String defaultMethod) {
    this.defaultMethod = defaultMethod;
  }

  public Boolean getSimpleURIStyle() {
    return simpleURIStyle;
  }

  public void setSimpleURIStyle(Boolean simpleURIStyle) {
    this.simpleURIStyle = simpleURIStyle;
  }

  public String getPackageName() {
    return new String(packageName);
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
    packageSegs = Strings.split(packageName, '*');
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getIsUseCache() {
    return useCache;
  }

  public void setUseCache(boolean useCache) {
    this.useCache = useCache;
  }

  public boolean isUseCache() {
    return useCache;
  }

  public boolean isReserveMatched() {
    return reserveMatched;
  }

  public void setReserveMatched(boolean reserveMatched) {
    this.reserveMatched = reserveMatched;
  }

  String name;
  String packageName;
  private String packageSegs[];
  String ctlPrefix;
  private String ctlPrefixSegs[];
  String ctlPostfix;
  String pagePrefix;
  String pagePostfix;
  String defaultMethod;
  Boolean simpleURIStyle;
  private boolean useCache;
  private boolean reserveMatched;
  private Map cache;
}
