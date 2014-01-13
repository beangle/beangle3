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
package org.beangle.orm.hibernate.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beangle.commons.inject.Container;
import org.beangle.commons.inject.ContainerHook;
import org.beangle.commons.inject.Containers;
import org.beangle.commons.web.filter.OncePerRequestFilter;
import org.beangle.orm.hibernate.internal.SessionUtils;
import org.hibernate.SessionFactory;

public class OpenSessionInViewFilter extends OncePerRequestFilter implements ContainerHook {

  public static final String DEFAULT_SESSION_FACTORY_BEAN_NAME = "sessionFactory";

  private String sessionFactoryBeanName = DEFAULT_SESSION_FACTORY_BEAN_NAME;

  private SessionFactory sessionFactory;

  /**
   * Set the bean name of the SessionFactory to fetch from Spring's
   * root application context. Default is "sessionFactory".
   * 
   * @see #DEFAULT_SESSION_FACTORY_BEAN_NAME
   */
  public void setSessionFactoryBeanName(String sessionFactoryBeanName) {
    this.sessionFactoryBeanName = sessionFactoryBeanName;
  }

  @Override
  protected void initFilterBean() throws ServletException {
    super.initFilterBean();
    Container context = Containers.getRoot();
    if (null != context) {
      setSessionFactory(context.<SessionFactory> getBean(sessionFactoryBeanName).get());
    } else {
      Containers.getHooks().add(this);
    }
  }

  public void notify(Container context) {
    setSessionFactory(context.getBean(SessionFactory.class).get());
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    if (firstEnter(request)) {
      SessionUtils.enableBinding(sessionFactory);
      // SessionUtils.openSession(sessionFactory);
      try {
        chain.doFilter(request, response);
      } finally {
        SessionUtils.disableBinding(sessionFactory);
        SessionUtils.closeSession(sessionFactory);
      }
    } else {
      chain.doFilter(request, response);
    }
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

}
