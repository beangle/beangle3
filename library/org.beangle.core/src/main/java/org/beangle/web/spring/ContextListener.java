package org.beangle.web.spring;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

	public static final String CONTEXT_CLASS_PARAM = "contextClass";

	public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

	private ContextLoader contextLoader=new ContextLoader();
	
	public void contextInitialized(ServletContextEvent sce) {
		contextLoader.initApplicationContext(sce.getServletContext());
	}

	public void contextDestroyed(ServletContextEvent sce) {
		contextLoader.closeApplicationContext(sce.getServletContext());
	}
}
