/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.spring;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author chaostone
 * @since 3.0.0
 */
public class ContextListener implements ServletContextListener {

  public static final String CONTEXT_CLASS_PARAM = "contextClass";

  public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

  private ContextLoader contextLoader = new ContextLoader();

  public void contextInitialized(ServletContextEvent sce) {
    contextLoader.initApplicationContext(sce.getServletContext());
  }

  public void contextDestroyed(ServletContextEvent sce) {
    contextLoader.closeApplicationContext(sce.getServletContext());
  }
}
