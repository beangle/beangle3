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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;

public class Action {

  public Action() {
    redirectUseParams = true;
  }

  public Action(Class clazz, String method) {
    redirectUseParams = true;
    if (null != clazz) controller = DispatchUtils.getControllerName(clazz);
    this.method = method;
  }

  public Action(Object ctlObj, String method) {
    redirectUseParams = true;
    if (null != ctlObj) controller = DispatchUtils.getControllerName(ctlObj.getClass());
    this.method = method;
  }

  public Action(Class clazz, String method, String params) {
    redirectUseParams = true;
    if (null != clazz) controller = DispatchUtils.getControllerName(clazz);
    this.method = method;
    this.params = new StringBuffer(params);
  }

  public Action(String controller, String method) {
    redirectUseParams = true;
    this.controller = controller;
    this.method = method;
  }

  public Action(String controller, String method, String params) {
    redirectUseParams = true;
    this.controller = controller;
    this.method = method;
    this.params = new StringBuffer(params);
  }

  public String getController() {
    return controller;
  }

  public void setController(String controller) {
    this.controller = controller;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public StringBuffer getParams() {
    return params;
  }

  public void setParams(StringBuffer params) {
    this.params = params;
  }

  public void addParams(Map paramMap) {
    if (null == params) params = new StringBuffer();
    if (null != paramMap && !paramMap.isEmpty()) {
      Iterator iter = paramMap.keySet().iterator();
      do {
        if (!iter.hasNext()) break;
        String key = (String) iter.next();
        String value = (String) paramMap.get(key);
        if (Strings.isNotEmpty(value)) try {
          params.append("&").append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
        } catch (Exception e) {
          throw new RuntimeException(e.getMessage());
        }
      } while (true);
    }
  }

  public void addParams(String paramStr) {
    if (null == params) params = new StringBuffer();
    try {
      params.append(encodeParams(paramStr));
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public String toString() {
    return Objects.toStringBuilder(this).add("params", params).add("controller", controller)
        .add("method", method).toString();
  }

  public StringBuffer getURL(HttpServletRequest request) {
    StringBuffer buf = new StringBuffer();
    if (Strings.isBlank(getController())) {
      if (null != request) buf.append(request.getServletPath());
    } else {
      buf.append("/").append(getController()).append(Conventions.urlSuffix);
    }
    if (Strings.isNotEmpty(getMethod())) buf.append("?method=").append(getMethod()).toString();
    if (null != getParams() && getParams().length() > 0) buf.append(getParams().toString());
    if (null != request && redirectUseParams) {
      String params = request.getParameter(redirectParamName);
      if (Strings.isNotEmpty(params)) try {
        buf.append(encodeParams(params));
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }
    return buf;
  }

  public static String encodeParams(String parameters) throws UnsupportedEncodingException {
    StringBuffer buf = new StringBuffer();
    if (Strings.isNotEmpty(parameters)) {
      String params[] = Strings.split(parameters, "&");
      for (int i = 0; i < params.length; i++)
        if (-1 != params[i].indexOf('=')) buf.append("&" + params[i].substring(0, params[i].indexOf('=')))
            .append("=").append(URLEncoder.encode(params[i].substring(params[i].indexOf('=') + 1), "utf-8"));
        else buf.append("&" + params[i]);

    }
    return buf.toString();
  }

  String controller;
  String method;
  StringBuffer params;
  boolean redirectUseParams;
  public static String redirectParamName = "_params";

}
