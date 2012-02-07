package org.beangle.web.spring;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.web.filter.OncePerRequestFilter;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		boolean participate = false;
		if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
			participate = true;
		} else {
			logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
			Session session = openSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}
		try {
			filterChain.doFilter(request, response);
		} finally {
			if (!participate) {
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
						.unbindResource(sessionFactory);
				logger.debug("Closing Hibernate Session in OpenSessionInViewFilter");
				SessionFactoryUtils.closeSession(sessionHolder.getSession());
			}
		}
	}

	/**
	 * Open a Session for the SessionFactory that this filter uses.
	 * <p>
	 * The default implementation delegates to the <code>SessionFactory.openSession</code> method
	 * and sets the <code>Session</code>'s flush mode to "MANUAL".
	 * 
	 * @param sessionFactory the SessionFactory that this filter uses
	 * @return the Session to use
	 */
	protected Session openSession(SessionFactory sessionFactory) {
		Session session = SessionFactoryUtils.openSession(sessionFactory);
		session.setFlushMode(FlushMode.MANUAL);
		return session;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
