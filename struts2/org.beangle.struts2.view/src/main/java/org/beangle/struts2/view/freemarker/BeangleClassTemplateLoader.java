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
package org.beangle.struts2.view.freemarker;

import java.net.URL;

import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;

import freemarker.cache.URLTemplateLoader;

/**
 * 搜索带有固定前缀下的资源，排除/org/和/com/
 * 
 * @author chaostone
 */
public class BeangleClassTemplateLoader extends URLTemplateLoader {

  /** Not starts with /,but end with / */
  String prefix = null;

  public BeangleClassTemplateLoader(String prefix) {
    super();
    setPrefix(prefix);
  }

  protected URL getURL(String name) {
    URL url = ClassLoaders.getResource(name, getClass());
    if (null != prefix && null == url) url = ClassLoaders.getResource(prefix + name, getClass());
    return url;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String pre) {
    if (Strings.isBlank(pre)) this.prefix = null;
    else this.prefix = pre.trim();

    if (null != prefix) {
      if (prefix.equals("/")) {
        prefix = null;
      } else {
        if (!prefix.endsWith("/")) prefix += "/";
        if (prefix.startsWith("/")) prefix = prefix.substring(1);
      }
    }
  }
}
