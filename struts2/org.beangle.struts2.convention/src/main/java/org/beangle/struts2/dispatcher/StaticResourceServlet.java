/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
