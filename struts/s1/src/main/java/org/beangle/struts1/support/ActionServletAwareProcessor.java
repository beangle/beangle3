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
package org.beangle.struts1.support;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionServlet;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor}
 * implementation that passes the ActionServlet to beans that extend
 * the Struts {@link org.apache.struts.action.Action} class.
 * Invokes {@code Action.setServlet} with {@code null} on
 * bean destruction, providing the same lifecycle handling as the
 * native Struts ActionServlet.
 * <p>
 * ContextLoaderPlugIn automatically registers this processor
 * with the underlying bean factory of its WebApplicationContext.
 *
 * @author Juergen Hoeller
 * @since 1.0.1
 * @see ContextLoaderPlugIn
 * @see org.apache.struts.action.Action#setServlet
 * @deprecated as of Spring 3.0
 */
@Deprecated
class ActionServletAwareProcessor implements DestructionAwareBeanPostProcessor {

  private final ActionServlet actionServlet;

  /**
   * Create a new ActionServletAwareProcessor for the given servlet.
   */
  public ActionServletAwareProcessor(ActionServlet actionServlet) {
    this.actionServlet = actionServlet;
  }

  public Object postProcessBeforeInitialization(Object bean, String beanName) {
    if (bean instanceof Action) {
      ((Action) bean).setServlet(this.actionServlet);
    }
    return bean;
  }

  public Object postProcessAfterInitialization(Object bean, String beanName) {
    return bean;
  }

  public void postProcessBeforeDestruction(Object bean, String beanName) {
    if (bean instanceof Action) {
      ((Action) bean).setServlet(null);
    }
  }

  //FIXME
  @Override
  public boolean requiresDestruction(Object bean) {
    return false;
  }

}
