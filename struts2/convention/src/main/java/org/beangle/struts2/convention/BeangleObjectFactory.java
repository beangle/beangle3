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
package org.beangle.struts2.convention;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.StrutsConstants;
import org.beangle.commons.inject.Container;
import org.beangle.commons.inject.ContainerAware;
import org.beangle.commons.inject.Containers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.factory.ResultFactory;
import com.opensymphony.xwork2.inject.Inject;

/**
 * <pre>
 * 1 Dropped autowire feature
 * </pre>
 * 
 * @author chaostone
 * @version $Id: BeangleObjectFactory.java Dec 25, 2011 5:54:57 PM chaostone $
 */
public class BeangleObjectFactory extends ObjectFactory {
  private static final long serialVersionUID = -1733081389212973935L;

  private static final Logger logger = LoggerFactory.getLogger(BeangleObjectFactory.class);

  protected Container context;

  /**
   * Constructs the object factory
   * 
   * @param servletContext The servlet context
   * @since 2.1.3
   */
  @Inject
  public BeangleObjectFactory(@Inject ServletContext servletContext,
      @Inject(StrutsConstants.STRUTS_DEVMODE) String devMode) {
    context = Containers.getRoot();
    if (context == null) logger.error("Cannot find beangle context from ServletContext");
  }

  /**
   * Looks up beans using application context before falling back to the method defined
   * in the {@link ObjectFactory}.
   * 
   * @param beanName The name of the bean to look up in the application context
   * @param extraContext
   * @return A bean from context or the result of calling the overridden
   *         method.
   * @throws Exception
   */
  @Override
  public Object buildBean(String beanName, Map<String, Object> extraContext, boolean injectInternal)
      throws Exception {
    Object bean = null;
    if (context.contains(beanName)) bean = context.getBean(beanName).get();
    else bean = buildBean(getClassInstance(beanName), extraContext);
    return bean;
  }

  /**
   * @see org.beangle.struts2.convention.config.ActionFinder
   */
  @SuppressWarnings("rawtypes")
  @Override
  public Object buildBean(Class clazz, Map<String, Object> extraContext) throws Exception {
    Object bean = clazz.newInstance();
    // for ActionFinder
    if (bean instanceof ContainerAware) ((ContainerAware) bean).setContainer(context);
    bean = injectInternalBeans(bean);
    return bean;
  }

  @Override
  public Class<?> getClassInstance(String className) throws ClassNotFoundException {
    Class<?> clazz = null;
    if (context.contains(className)) clazz = context.getType(className).get();
    else clazz = super.getClassInstance(className);
    return clazz;
  }

  @Inject("beangle")
  public void setResultFactory(ResultFactory resultFactory) {
    super.setResultFactory(resultFactory);
  }
}
