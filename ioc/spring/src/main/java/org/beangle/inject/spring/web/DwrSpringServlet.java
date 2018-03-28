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

package org.beangle.inject.spring.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.Container;
import org.directwebremoting.WebContextFactory.WebContextBuilder;
import org.directwebremoting.extend.Configurator;
import org.directwebremoting.extend.ContainerUtil;
import org.directwebremoting.impl.StartupUtil;
import org.directwebremoting.servlet.DwrServlet;
import org.directwebremoting.servlet.UrlProcessor;
import org.directwebremoting.spring.SpringContainer;
import org.directwebremoting.spring.namespace.ConfigurationParser;
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
public class DwrSpringServlet extends DwrServlet {
  private static final long serialVersionUID = 1843603170043922633L;

  /**
   * Do we prefix the list of Configurators with a default to read the system
   * dwr.xml file?
   */
  private boolean includeDefaultConfig = true;

  /**
   * What Configurators exist for us to configure ourselves.
   */
  private List<Configurator> configurators = new ArrayList<Configurator>();

  /**
   * The WebContext that keeps http objects local to a thread
   */
  protected WebContextBuilder webContextBuilder = null;

  @Override
  protected SpringContainer createContainer(ServletConfig servletConfig) {
    ApplicationContext appContext = getApplicationContext(servletConfig.getServletContext());

    SpringContainer springContainer = new SpringContainer();
    springContainer.setBeanFactory(appContext);
    StartupUtil.setupDefaultContainer(springContainer, servletConfig);
    return springContainer;
  }

  @Override
  protected void configureContainer(Container container, ServletConfig servletConfig)
      throws ServletException, IOException {
    try {
      ApplicationContext appContext = getApplicationContext(servletConfig.getServletContext());
      configurators
          .add((Configurator) appContext.getBean(ConfigurationParser.DEFAULT_SPRING_CONFIGURATOR_ID));
    } catch (NoSuchBeanDefinitionException ex) {
      throw new ServletException(
          "No DWR configuration was found in your application context, make sure to define one", ex);
    }

    try {
      if (includeDefaultConfig) {
        StartupUtil.configureFromSystemDwrXml(container);
      }

      StartupUtil.configureFromInitParams(container, servletConfig);
      StartupUtil.configure(container, configurators);
    } catch (IOException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
  }

  protected ApplicationContext getApplicationContext(ServletContext servletContext) {
    return ContextLoader.getContext(servletContext);
  }

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

}
