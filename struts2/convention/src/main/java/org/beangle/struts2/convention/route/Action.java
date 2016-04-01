/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;

/**
 * web路由的系统结点
 * 
 * @author chaostone
 */
public class Action {

  private String namespace;

  private String name;

  private String method;

  private Class<?> clazz;

  private String extention;

  private Map<String, String> params = CollectUtils.newHashMap();

  // constructors
  public Action() {
    super();
  }

  public Action(String method) {
    super();
    this.method = method;
  }

  public Action(Object ctlObj, String method) {
    this(ctlObj.getClass(), method, null);
  }

  public Action(Class<?> clazz, String method) {
    this(clazz, method, null);
  }

  public Action(Class<?> clazz, String method, String params) {
    this.clazz = clazz;
    this.method = method;
    params(params);
  }

  public Action(String actionName, String method) {
    this(actionName, method, null);
  }

  public Action(String actionName, String method, String params) {
    if (null != actionName) {
      path(actionName);
    }
    method(method).params(params);
  }

  // Convenience methods
  public static Action to(Class<?> clazz) {
    return new Action(clazz, null);
  }

  public static Action to(Object obj) {
    return new Action(obj, null);
  }

  public Action name(String name) {
    this.name = name;
    return this;
  }

  public Action namespace(String namespace) {
    this.namespace = namespace;
    return this;
  }

  public Action method(String m) {
    this.method = m;
    return this;
  }

  public Action extention(String extention) {
    this.extention = extention;
    return this;
  }

  public Action param(String key, String value) {
    params.put(key, value);
    return this;
  }

  public Action param(String key, Object obj) {
    params.put(key, String.valueOf(obj));
    return this;
  }

  public Action params(Map<String, String> newParams) {
    params.putAll(newParams);
    return this;
  }

  public Action path(String path) {
    String[] data = parse(path);
    namespace = data[0];
    name = data[1];
    return this;
  }

  public static String[] parse(String path) {
    int endIndex = path.length();
    int i = endIndex - 1;
    int actionIndex = 0;
    while (i > -1) {
      char c = path.charAt(i);
      if (c == '.' || c == '!') {
        endIndex = i;
      } else if (c == '/') {
        actionIndex = i + 1;
        break;
      }
      i--;
    }
    String namespace = null;
    if (actionIndex < 2) {
      namespace = "/";
    } else {
      namespace = path.substring(0, actionIndex - 1);
      if (namespace.charAt(0) != '/') {
        namespace = "/" + namespace;
      }
    }
    String actionName = path.substring(actionIndex, endIndex);
    return new String[] { namespace, actionName };
  }

  public Action params(String paramStr) {
    if (Strings.isNotEmpty(paramStr)) {
      String[] paramPairs = Strings.split(paramStr, "&");
      for (int i = 0; i < paramPairs.length; i++) {
        String key = Strings.substringBefore(paramPairs[i], "=");
        String value = Strings.substringAfter(paramPairs[i], "=");
        if (Strings.isNotEmpty(key) && Strings.isNotEmpty(value)) {
          params.put(key, value);
        }
      }
    }
    return this;
  }

  public String getUri() {
    StringBuilder buf = new StringBuilder(25);
    if (null == namespace || namespace.length() == 1) {
      buf.append('/');
    } else {
      buf.append(namespace).append('/');
    }
    if (null != name) {
      buf.append(name);
    }
    if (Strings.isNotEmpty(method)) {
      buf.append('!').append(method);
    }
    if (null != extention) {
      buf.append('.').append(extention);
    }
    if (null != getParams() && getParams().size() > 0) {
      boolean first = true;
      for (Iterator<String> iter = getParams().keySet().iterator(); iter.hasNext();) {
        String key = iter.next();
        String value = getParams().get(key);
        try {
          if (first) {
            buf.append('?');
            first = false;
          } else {
            buf.append('&');
          }
          buf.append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
        } catch (Exception e) {
          throw new RuntimeException(e.getMessage());
        }
      }
    }
    return buf.toString();
  }

  public String getNamespace() {
    return namespace;
  }

  public String getName() {
    return name;
  }

  public String getMethod() {
    return method;
  }

  public Map<String, String> getParams() {
    return params;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public String getExtention() {
    return extention;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return Objects.toStringBuilder(this).add("namespace", namespace).add("name", name).add("method", method)
        .add("params", params).toString();
  }
}
