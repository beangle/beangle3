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
package org.beangle.inject.spring.web;

import javax.servlet.ServletContext;

import org.beangle.commons.inject.Container;
import org.beangle.commons.inject.ContainerHook;
import org.beangle.commons.inject.Containers;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.reflect.Reflections;
import org.beangle.inject.spring.SpringContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.util.ClassUtils;

/**
 * Spring context loader
 * 
 * @author chaostone
 * @since 3.0
 */
public class ContextLoader {

  private static final Logger logger = LoggerFactory.getLogger(ContextLoader.class);

  public static final String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = "org.springframework.web.context.WebApplicationContext.ROOT";

  public static final String APPLICATION_CONTEXT_ID_PREFIX = "WebApplicationContext:";

  public static final String CONTEXT_CLASS_PARAM = "contextClass";

  public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

  protected ConfigurableApplicationContext createApplicationContext(ServletContext sc) {
    Class<?> contextClass = determineContextClass(sc);
    if (!ConfigurableApplicationContext.class.isAssignableFrom(contextClass)) { throw new ApplicationContextException(
        "Custom context class [" + contextClass.getName() + "] is not of type ["
            + ConfigurableApplicationContext.class.getName() + "]"); }
    ConfigurableApplicationContext wac = (ConfigurableApplicationContext) Reflections
        .newInstance(contextClass);
    return wac;
  }

  public ApplicationContext initApplicationContext(ServletContext servletContext) {
    if (null != getContext(servletContext)) { throw new IllegalStateException(
        "Cannot initialize context because there is already a root application context present - "
            + "check whether you have multiple ContextListener* definitions in your web.xml!"); }

    logger.info("Root ApplicationContext: initialization started");
    long startTime = System.currentTimeMillis();
    try {
      ConfigurableApplicationContext context = createApplicationContext(servletContext);
      configureAndRefreshApplicationContext((ConfigurableApplicationContext) context, servletContext);
      servletContext.setAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
      Container container = new SpringContainer(context);
      Containers.setRoot(container);
      for (ContainerHook hook : Containers.getHooks())
        hook.notify(container);
      
      logger.info("Root ApplicationContext: initialization completed in {} ms", System.currentTimeMillis()
          - startTime);
      return context;
    } catch (RuntimeException ex) {
      logger.error("Context initialization failed", ex);
      throw ex;
    } catch (Error err) {
      logger.error("Context initialization failed", err);
      throw err;
    }
  }

  protected void configureAndRefreshApplicationContext(ConfigurableApplicationContext wac, ServletContext sc) {
    wac.setId(APPLICATION_CONTEXT_ID_PREFIX + Objects.toString(sc.getServletContextName()));
    String initParameter = sc.getInitParameter(CONFIG_LOCATION_PARAM);
    if (initParameter != null) {
      if (wac instanceof AbstractRefreshableConfigApplicationContext) {
        ((AbstractRefreshableConfigApplicationContext) wac).setConfigLocation(initParameter);
      }
    }
    customizeContext(sc, wac);
    wac.refresh();
  }

  protected void customizeContext(ServletContext servletContext,
      ConfigurableApplicationContext applicationContext) {
  }

  protected Class<?> determineContextClass(ServletContext servletContext) {
    String contextClassName = servletContext.getInitParameter(CONTEXT_CLASS_PARAM);
    if (contextClassName != null) {
      try {
        return ClassUtils.forName(contextClassName, ClassUtils.getDefaultClassLoader());
      } catch (ClassNotFoundException ex) {
        throw new ApplicationContextException("Failed to load custom context class [" + contextClassName
            + "]", ex);
      }
    } else {
      return XmlWebApplicationContext.class;
    }
  }

  public void closeApplicationContext(ServletContext servletContext) {
    logger.info("Closing Spring root ApplicationContext");
    ConfigurableApplicationContext context = getContext(servletContext);
    if (null != context) context.close();
    servletContext.removeAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
  }

  public static ConfigurableApplicationContext getContext(ServletContext servletContext) {
    Object context = servletContext.getAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    if (null == context) {
      context = servletContext.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
    }
    if (!(context instanceof Exception)) return (ConfigurableApplicationContext) context;
    return null;
  }

}
