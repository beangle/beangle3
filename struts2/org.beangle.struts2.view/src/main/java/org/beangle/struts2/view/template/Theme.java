/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.template;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

/**
 * Template Theme
 * 
 * @author chaostone
 * @since 2.4
 */
public class Theme {

  public static final String Theme = ".beangle_theme";

  public static final String ThemeStack = ".beangle_theme_stack";

  public static final String DefaultTheme = "xml";

  /**
   * Default tagName corresponding TagClass
   * 
   * @see getTemplateName
   */
  private final static Map<Class<?>, String> defaultNames = CollectUtils.newHashMap();

  /**
   * Registe all theme
   */
  private final static Map<String, Theme> themes = CollectUtils.newHashMap();

  /**
   * Theme's name ,xml,list,xhtml etc.
   */
  private final String name;

  public static Theme getTheme(String name) {
    Theme theme = themes.get(name);
    if (null == theme) {
      theme = new Theme(name);
      themes.put(name, theme);
    }
    return theme;
  }

  private Theme(String name) {
    super();
    this.name = name;
  }

  public String getTemplatePath(Class<?> clazz, String suffix) {
    StringBuilder sb = new StringBuilder(20);
    sb.append("/template/").append(name).append('/').append(getTemplateName(clazz)).append(suffix);
    return sb.toString();
  }

  public static String getTemplateName(Class<?> clazz) {
    String name = defaultNames.get(clazz);
    if (null == name) {
      name = Strings.uncapitalize(clazz.getSimpleName());
      defaultNames.put(clazz, name);
    }
    return name;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    return name.equals(obj.toString());
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
