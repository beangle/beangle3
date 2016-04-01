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
package org.beangle.commons.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beangle.commons.inject.Container;
import org.beangle.commons.inject.ContainerHook;
import org.beangle.commons.inject.Containers;
import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.Throwables;

/**
 * Proxy for a standard Servlet 2.3 Filter, delegating to a managed
 * bean that implements the Filter interface. Supports a "targetBeanName"
 * filter init-param in {@code web.xml}, specifying the name of the
 * target bean in the application context.
 * 
 * @author chaostone
 */
public class DelegatingFilterProxy extends GenericHttpFilter implements ContainerHook {

  private Filter delegate;

  private String targetBeanName;

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    delegate.doFilter(request, response, chain);
  }

  public void notify(Container container) {
    try {
      setDelegate(initDelegate(container));
    } catch (ServletException e) {
      Throwables.propagate(e);
    }
  }

  @Override
  protected void initFilterBean() throws ServletException {
    if (null == targetBeanName) targetBeanName = getFilterName();
    Container wac = Containers.getRoot();
    if (wac != null) delegate = initDelegate(wac);
    else Containers.getHooks().add(this);
  }

  @Override
  public void destroy() {
    if (delegate != null) delegate.destroy();
  }

  protected Filter initDelegate(Container container) throws ServletException {
    Option<Filter> delegate = container.getBean(targetBeanName);
    delegate.get().init(getFilterConfig());
    return delegate.get();
  }

  public void setTargetBeanName(String targetBeanName) {
    this.targetBeanName = targetBeanName;
  }

  /**
   * Return the name of the target bean in the application context.
   */
  protected String getTargetBeanName() {
    return this.targetBeanName;
  }

  public void setDelegate(Filter delegate) {
    this.delegate = delegate;
  }
}
