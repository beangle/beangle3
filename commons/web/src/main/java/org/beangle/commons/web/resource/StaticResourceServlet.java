package org.beangle.commons.web.resource;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.inject.Containers;
import org.beangle.commons.lang.Strings;

public class StaticResourceServlet extends HttpServlet {

  private static final long serialVersionUID = 6086288505408491256L;

  private ResourceProcessor processor;

  @Override
  public void init(ServletConfig config) throws ServletException {
    processor = Containers.getRoot().getBean(ResourceProcessor.class).get();
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    String uri = request.getRequestURI();
    String contextPath = request.getContextPath();
    if (!(contextPath.equals("") || contextPath.equals("/"))) {
      uri = Strings.substringAfter(uri, contextPath);
    }
    uri = uri.substring("/static".length());
    processor.process(uri, request, response);
  }
}
