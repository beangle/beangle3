package org.beangle.commons.web.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public interface StartupInitializer {
  /**
   * Configure the given {@link ServletContext} with any servlets, filters, listeners
   * context-params and attributes necessary for initializing this web application.
   * 
   * @param servletContext the {@code ServletContext} to initialize
   * @throws ServletException if any call against the given {@code ServletContext} throws a
   *           {@code ServletException}
   */
  void onStartup(ServletContext servletContext) throws ServletException;
}
