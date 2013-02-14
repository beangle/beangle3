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
package org.beangle.struts2.convention.route;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 路由调转配置
 * 
 * @author chaostone <br>
 *         /:controller:ext =>:method=index||get("method")
 *         /:controller/:method:ext
 */
public class Profile implements Comparable<Profile> {
  private static final Logger logger = LoggerFactory.getLogger(Profile.class);

  // 配置名
  private String name;

  // action所在的包,匹配action的唯一条件
  private String actionPattern;

  private String[] patternSegs;

  // action类名后缀
  private String actionSuffix;

  // 扫描action
  private boolean actionScan;

  // 路径前缀
  private String viewPath;

  // 路径模式
  private String viewPathStyle = "simple";

  // 路径后缀
  private String viewExtension;

  // 缺省的action中的方法
  private String defaultMethod = "index";

  // URI ROOT
  private String uriPath = "/";

  // URI style
  private String uriPathStyle = "simple";

  /** URI的后缀 */
  private String uriExtension;

  // 匹配缓存[className,matchInfo]
  private Map<String, MatchInfo> cache = new ConcurrentHashMap<String, MatchInfo>();

  /**
   * 得到控制器的起始位置
   * 
   * @param className
   */
  public MatchInfo getCtlMatchInfo(final String className) {
    MatchInfo match = cache.get(className);
    if (null == match) {
      match = getMatchInfo(patternSegs, className);
      if (-1 != match.startIndex) {
        cache.put(className, match);
      }
    }
    return match;
  }

  /**
   * 给定action是否符合该配置文件
   * 
   * @param className
   */
  public boolean isMatch(final String className) {
    return -1 != getMatchInfo(patternSegs, className).startIndex;
  }

  public int matchedIndex(final String className) {
    return getMatchInfo(patternSegs, className).startIndex;
  }

  public static boolean isInPackage(String packageName, String className) {
    return -1 != getMatchInfo(Strings.split(packageName, '*'), className).startIndex;
  }

  public static MatchInfo getMatchInfo(final String[] pattens, final String className) {
    String sub = className;
    int index = 0;
    MatchInfo match = new MatchInfo(-1);
    for (int i = 0; i < pattens.length; i++) {
      int subIndex = sub.indexOf(pattens[i]);
      if (-1 == subIndex) { return match; }
      // 串接所有匹配项保留部分
      if (0 != subIndex) {
        if (match.reserved.length() > 0) {
          match.reserved.append('.');
        }
        match.reserved.append(sub.substring(0, subIndex));
      }
      index += (subIndex + pattens[i].length());
      if (i != pattens.length - 1) {
        sub = sub.substring(subIndex + pattens[i].length());
        if (Strings.isEmpty(sub)) {
          match.startIndex = className.length() - 1;
          return match;
        }
      }
    }
    match.startIndex = index - 1;
    return match;
  }

  /**
   * 子包优先
   */
  public int compareTo(Profile other) {
    return other.actionPattern.compareTo(this.actionPattern);
  }

  /**
   * 取得类名称对应的全路经，仅仅把类名第一个字母小写。
   * 
   * @param className
   */
  public String getFullPath(String className) {
    String postfix = getActionSuffix();
    String simpleName = className.substring(className.lastIndexOf('.') + 1);
    if (Strings.contains(simpleName, postfix)) {
      simpleName = Strings.uncapitalize(simpleName.substring(0, simpleName.length() - postfix.length()));
    } else {
      simpleName = Strings.uncapitalize(simpleName);
    }

    StringBuilder infix = new StringBuilder();
    infix.append(Strings.substringBeforeLast(className, "."));
    if (infix.length() == 0) return simpleName;
    infix.append('.');
    infix.append(simpleName);
    // 将.替换成/
    for (int i = 0; i < infix.length(); i++) {
      if (infix.charAt(i) == '.') {
        infix.setCharAt(i, '/');
      }
    }
    return infix.toString();
  }

  /**
   * 将前后缀去除后，中间的.替换为/<br>
   * 不以/开始。
   * 
   * @param className
   */
  public String getInfix(String className) {
    String postfix = getActionSuffix();
    String simpleName = className.substring(className.lastIndexOf('.') + 1);
    if (Strings.contains(simpleName, postfix)) {
      simpleName = Strings.uncapitalize(simpleName.substring(0, simpleName.length() - postfix.length()));
    } else {
      simpleName = Strings.uncapitalize(simpleName);
    }

    MatchInfo match = getCtlMatchInfo(className);
    StringBuilder infix = new StringBuilder(match.getReserved().toString());
    if (infix.length() > 0) infix.append('.');

    String remainder = Strings.substringBeforeLast(className, ".").substring(match.getStartIndex() + 1);
    if (remainder.length() > 0) {
      if ('.' == remainder.charAt(0)) remainder = remainder.substring(1);
      infix.append(remainder).append('.');
    }

    if (infix.length() == 0) return simpleName;
    infix.append(simpleName);

    // 将.替换成/
    for (int i = 0; i < infix.length(); i++) {
      if (infix.charAt(i) == '.') infix.setCharAt(i, '/');
    }
    return infix.toString();
  }

  public String getViewPath() {
    return viewPath;
  }

  public void setViewPath(String pagePath) {
    this.viewPath = pagePath;
  }

  public String getActionSuffix() {
    return actionSuffix;
  }

  public void setActionSuffix(String ctlPostfix) {
    this.actionSuffix = ctlPostfix;
  }

  public String getViewExtension() {
    return viewExtension;
  }

  public void setViewExtension(String pagePostfix) {
    this.viewExtension = pagePostfix;
  }

  public String getDefaultMethod() {
    return defaultMethod;
  }

  public void setDefaultMethod(String defaultMethod) {
    this.defaultMethod = defaultMethod;
  }

  public String getUriPathStyle() {
    return uriPathStyle;
  }

  public void setUriPathStyle(String uriStyle) {
    this.uriPathStyle = uriStyle;
  }

  public String getActionPattern() {
    return actionPattern;
  }

  public void setActionPattern(String actionPattern) {
    this.actionPattern = actionPattern;
    this.patternSegs = Strings.split(actionPattern, '*');
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUriExtension() {
    return uriExtension;
  }

  public void setUriExtension(String uriExtension) {
    this.uriExtension = uriExtension;
  }

  public String getViewPathStyle() {
    return viewPathStyle;
  }

  public void setViewPathStyle(String pathStyle) {
    this.viewPathStyle = pathStyle;
  }

  public String getUriPath() {
    return uriPath;
  }

  public void setUriPath(String uriRoot) {
    this.uriPath = uriRoot;
  }

  public boolean isActionScan() {
    return actionScan;
  }

  public void setActionScan(boolean actionScan) {
    this.actionScan = actionScan;
  }

  public static Logger getLogger() {
    return logger;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return Objects.toStringBuilder(this).add("name", name).add("actionPattern", actionPattern)
        .add("actionSuffix", actionSuffix).add("actionScan", actionScan).add("viewPath", viewPath)
        .add("viewPathStyle", viewPathStyle).add("viewExtension", viewExtension).add("uriPath", uriPath)
        .add("uriPathStyle", uriPathStyle).add("uriExtension", uriExtension)
        .add("defaultMethod", defaultMethod).toString();
  }

}
