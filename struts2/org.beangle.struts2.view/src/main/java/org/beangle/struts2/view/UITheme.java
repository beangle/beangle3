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
