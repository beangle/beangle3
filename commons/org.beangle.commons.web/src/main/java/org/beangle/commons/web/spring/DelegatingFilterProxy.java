/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.web.spring;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beangle.commons.lang.Throwables;
import org.beangle.commons.web.filter.GenericHttpFilter;
import org.springframework.context.ApplicationContext;

/**
 * Proxy for a standard Servlet 2.3 Filter, delegating to a Spring-managed
 * bean that implements the Filter interface. Supports a "targetBeanName"
 * filter init-param in {@code web.xml}, specifying the name of the
 * target bean in the Spring application context.
 * 
 * @author chaostone
 */
public class DelegatingFilterProxy extends GenericHttpFilter implements LazyInitializingHook {

  private Filter delegate;

  private String targetBeanName;

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    delegate.doFilter(request, response, chain);
  }

  public void lazyInit(ApplicationContext context) {
    try {
      setDelegate(initDelegate(context));
    } catch (ServletException e) {
      Throwables.propagate(e);
    }
  }

  @Override
  protected void initFilterBean() throws ServletException {
    if (null == targetBeanName) targetBeanName = getFilterName();
    ApplicationContext wac = ContextLoader.getContext(getServletContext());
    if (wac != null) {
      delegate = initDelegate(wac);
    } else {
      ContextLoader.getLazyInitialHooks(getServletContext()).add(this);
    }
  }

  @Override
  public void destroy() {
    if (delegate != null) delegate.destroy();
  }

  protected Filter initDelegate(ApplicationContext wac) throws ServletException {
    Filter delegate = wac.getBean(targetBeanName, Filter.class);
    delegate.init(getFilterConfig());
    return delegate;
  }

  public void setTargetBeanName(String targetBeanName) {
    this.targetBeanName = targetBeanName;
  }

  /**
   * Return the name of the target bean in the Spring application context.
   */
  protected String getTargetBeanName() {
    return this.targetBeanName;
  }

  public void setDelegate(Filter delegate) {
    this.delegate = delegate;
  }
}
