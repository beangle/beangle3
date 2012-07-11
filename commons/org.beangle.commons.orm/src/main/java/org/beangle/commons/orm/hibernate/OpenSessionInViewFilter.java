package org.beangle.commons.orm.hibernate;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beangle.commons.orm.hibernate.internal.SessionUtils;
import org.beangle.commons.web.filter.OncePerRequestFilter;
import org.beangle.commons.web.spring.ContextLoader;
import org.beangle.commons.web.spring.LazyInitProcessor;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

public class OpenSessionInViewFilter extends OncePerRequestFilter {

  private SessionFactory sessionFactory;

  @Override
  protected void initFilterBean() throws ServletException {
    super.initFilterBean();
    ApplicationContext context = ContextLoader.getContext(getServletContext());
    if (null != context) {
      setSessionFactory(context.getBean(SessionFactory.class));
    } else {
      ContextLoader.getOrCreateLazyInitProcessors(getServletContext()).add(new LazyInitProcessor() {
        public void init(ApplicationContext context) {
          setSessionFactory(context.getBean(SessionFactory.class));
        }
      });
    }
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    if (enterFilter(request)) {
      SessionUtils.openSession(sessionFactory).setFlushMode(FlushMode.MANUAL);
      try {
        chain.doFilter(request, response);
      } finally {
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
