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
package org.beangle.inject.spring.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.WebContextFactory.WebContextBuilder;
import org.directwebremoting.impl.ContainerUtil;
import org.directwebremoting.impl.StartupUtil;
import org.directwebremoting.servlet.UrlProcessor;
import org.directwebremoting.spring.SpringContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * The servlet that handles all calls to DWR. <br>
 * It retrieves its configuration from the Spring IoC container. This is done in two ways:
 * <ol>
 * <li>Use the Spring namespace. When using the Spring namespace for DWR, the confgiuration for DWR
 * is automatically picked up by this servlet.</li>
 * <li>Explicitly specify which configurations to pick up. When explicitly defining the DWR
 * configuration in Spring yourself, you can explicitely specify them in the init parameters.</li>
 * </ol>
 * Same as with the <code>DwrServlet</code>, you can specify a <code>debug</code> init parameter on
 * this servlet
 * to put DWR in debug mode (allowing access to the very handy debug pages).
 *
 * @see org.directwebremoting.servlet.DwrServlet
 * @author chaostone
 */
public class DwrSpringServlet extends HttpServlet {
  private static final long serialVersionUID = 1843603170043922633L;

  static String DEFAULT_SPRING_CONFIGURATOR_ID = "__dwrConfiguration";

  /**
   * Setter for use by the Spring IoC container to tell us what Configurators
   * exist for us to configure ourselves.
   * 
   * @param configurators
   */
  public void setConfigurators(List configurators) {
    this.configurators = configurators;
  }

  /**
   * Do we prefix the list of Configurators with a default to read the system
   * dwr.xml file?
   * 
   * @param includeDefaultConfig the includeDefaultConfig to set
   */
  public void setIncludeDefaultConfig(boolean includeDefaultConfig) {
    this.includeDefaultConfig = includeDefaultConfig;
  }

  /*
   * (non-Javadoc)
   * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
   */
  public void init(ServletConfig servletConfig) throws ServletException {
    super.init(servletConfig);
    ServletContext servletContext = servletConfig.getServletContext();

    try {
      ApplicationContext webappContext = ContextLoader.getContext(servletContext);

      container = new SpringContainer();
      container.setBeanFactory(webappContext);
      ContainerUtil.setupDefaults(container, servletConfig);
      ContainerUtil.setupFromServletConfig(container, servletConfig);

      container.setupFinished();

      webContextBuilder = StartupUtil.initWebContext(servletConfig, servletContext, container);
      StartupUtil.initServerContext(servletConfig, servletContext, container);

      ContainerUtil.prepareForWebContextFilter(servletContext, servletConfig, container, webContextBuilder,
          this);
      // retrieve the configurators from Spring (loaded by the ContextLoaderListener)
      try {
        configurators.add(webappContext.getBean(DEFAULT_SPRING_CONFIGURATOR_ID));
      } catch (NoSuchBeanDefinitionException ex) {
        throw new ServletException(
            "No DWR configuration was found in your application context, make sure to define one", ex);
      }

      if (includeDefaultConfig) {
        ContainerUtil.configureFromSystemDwrXml(container);
      }

      ContainerUtil.configureFromInitParams(container, servletConfig);
      ContainerUtil.configure(container, configurators);

      ContainerUtil.publishContainer(container, servletConfig);
    } catch (InstantiationException ex) {
      throw new BeanCreationException("Failed to instansiate", ex);
    } catch (IllegalAccessException ex) {
      throw new BeanCreationException("Access error", ex);
    } catch (Exception ex) {
      log.error("init failed", ex);
      throw new ServletException(ex);
    } finally {
      webContextBuilder.unset();
    }
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    doPost(req, resp);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    try {
      webContextBuilder.set(request, response, getServletConfig(), getServletContext(), container);

      UrlProcessor processor = (UrlProcessor) container.getBean(UrlProcessor.class.getName());
      processor.handle(request, response);
    } finally {
      webContextBuilder.unset();
    }
  }

  /**
   * DWRs IoC container (that passes stuff to Spring in this case)
   */
  private SpringContainer container;

  /**
   * The WebContext that keeps http objects local to a thread
   */
  protected WebContextBuilder webContextBuilder;

  /**
   * Do we prefix the list of Configurators with a default to read the system
   * dwr.xml file?
   */
  private boolean includeDefaultConfig = true;

  /**
   * What Configurators exist for us to configure ourselves.
   */
  private List configurators = new ArrayList();

  /**
   * The log stream
   */
  private static final Logger log = LoggerFactory.getLogger(DwrSpringServlet.class);
}
