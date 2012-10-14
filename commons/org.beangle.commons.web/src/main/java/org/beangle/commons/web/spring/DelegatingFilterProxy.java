/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
public class DelegatingFilterProxy extends GenericHttpFilter implements InitializingContextAware {

  private Filter delegate;

  private String targetBeanName;

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    delegate.doFilter(request, response, chain);
  }

  public void init(ApplicationContext context) {
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
    String fliterName = getFilterConfig().getFilterName();
    Filter delegate = wac.getBean(fliterName, Filter.class);
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
