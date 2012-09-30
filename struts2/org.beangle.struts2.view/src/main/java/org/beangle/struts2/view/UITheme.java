/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

/**
 * UITheme represent css or images resource bundle name.
 * 
 * @author chaostone
 * @since 3.0.0
 */
public class UITheme {

  /**
   * Registe all ui theme
   */
  private final static Map<String, UITheme> themes = CollectUtils.newHashMap();

  final String name;

  final String base;

  public static UITheme getTheme(String name, String base) {
    UITheme theme = themes.get(name);
    if (null == theme) {
      theme = new UITheme(name, base);
      themes.put(name, theme);
    }
    return theme;
  }

  private UITheme(String name, String base) {
    super();
    this.name = name;
    this.base = base;
  }

  public String iconurl(String name) {
    return iconurl(name, "16x16");
  }

  public String iconurl(String name, int size) {
    StringBuilder sb = new StringBuilder();
    sb.append(size).append('x').append(size);
    return iconurl(name, sb.toString());
  }

  public String iconurl(String name, String size) {
    StringBuilder sb = new StringBuilder(80);
    if (base.length() < 2) {
      sb.append("/static/themes/");
    } else {
      sb.append(base).append("/static/themes/");
    }
    sb.append(getName()).append("/icons/").append(size);
    if (!name.startsWith("/")) sb.append('/');
    sb.append(name);
    return sb.toString();
  }

  public String cssurl(String name) {
    StringBuilder sb = new StringBuilder(80);
    if (base.length() < 2) {
      sb.append("/static/themes/");
    } else {
      sb.append(base).append("/static/themes/");
    }
    sb.append(getName());
    if (!name.startsWith("/")) sb.append('/');
    sb.append(name);
    return sb.toString();
  }

  public String getName() {
    return name;
  }

  public String getBase() {
    return base;
  }

}
