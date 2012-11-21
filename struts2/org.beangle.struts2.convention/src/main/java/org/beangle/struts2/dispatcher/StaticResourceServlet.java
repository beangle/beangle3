/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.struts2.dispatcher;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StaticContentLoader;
import org.apache.struts2.dispatcher.ng.servlet.ServletHostConfig;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.util.BeangleStaticContentLoader;

/**
 * Server Static Resource
 * 
 * @author chaostone
 */
public class StaticResourceServlet extends HttpServlet {

  private static final long serialVersionUID = -4256846350517851133L;

  private StaticContentLoader staticResourceLoader = new BeangleStaticContentLoader();

  @Override
  public void init(ServletConfig config) throws ServletException {
    staticResourceLoader.setHostConfig(new ServletHostConfig(config));
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    String uri = request.getRequestURI();
    String contextPath = request.getContextPath();
    if (!(contextPath.equals("") || contextPath.equals("/"))) {
      uri = Strings.substringAfter(uri, contextPath);
    }
    staticResourceLoader.findStaticResource(uri, request, response);
  }

}
