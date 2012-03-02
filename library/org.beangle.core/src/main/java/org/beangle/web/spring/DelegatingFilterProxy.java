package org.beangle.web.spring;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.UnhandledException;
import org.beangle.web.filter.GenericHttpFilter;
import org.springframework.context.ApplicationContext;

/**
 * Proxy for a standard Servlet 2.3 Filter, delegating to a Spring-managed
 * bean that implements the Filter interface. Supports a "targetBeanName"
 * filter init-param in {@code web.xml}, specifying the name of the
 * target bean in the Spring application context.
 * 
 * @author chaostone
 */
public class DelegatingFilterProxy extends GenericHttpFilter {

	private Filter delegate;

	private String targetBeanName;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		delegate.doFilter(request, response, chain);
	}

	@Override
	protected void initFilterBean() throws ServletException {
		if (null == targetBeanName) targetBeanName = getFilterName();
		ApplicationContext wac = ContextLoader.getContext(getServletContext());
		if (wac != null) {
			delegate = initDelegate(wac);
		} else {
			ContextLoader.getOrCreateLazyInitProcessors(getServletContext()).add(new LazyInitProcessor() {
				public void init(ApplicationContext context) {
					try {
						setDelegate(initDelegate(context));
					} catch (ServletException e) {
						throw new UnhandledException(e);
					}
				}
			});
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
