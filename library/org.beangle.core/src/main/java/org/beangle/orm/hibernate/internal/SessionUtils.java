/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.orm.hibernate.internal;

import javax.sql.DataSource;

import org.beangle.orm.hibernate.DataSourceConnectionProvider;
import org.hibernate.HibernateException;
import org.hibernate.ObjectDeletedException;
import org.hibernate.PersistentObjectException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransientObjectException;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Open or Close Hibernate Session
 * 
 * @author chaostone
 * @version $Id: SessionFactoryUtils.java Feb 27, 2012 10:34:08 PM chaostone $
 */
public final class SessionUtils {

	private static Logger logger = LoggerFactory.getLogger(SessionUtils.class);

	public static DataSource getDataSource(SessionFactory sessionFactory) {
		if (sessionFactory instanceof SessionFactoryImplementor) {
			ConnectionProvider cp = ((SessionFactoryImplementor) sessionFactory).getConnectionProvider();
			if (cp instanceof DataSourceConnectionProvider) { return ((DataSourceConnectionProvider) cp)
					.getDataSource(); }
		}
		return null;
	}

	public static Session openSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException, IllegalStateException {
		try {
			SessionHolder holder = (SessionHolder) TransactionSynchronizationManager
					.getResource(sessionFactory);
			Session session = null;
			if (null == holder) {
				session = sessionFactory.openSession();
				TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
			}
			return session;
		} catch (HibernateException ex) {
			throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
		}
	}

	public static Session currentSession(SessionFactory sessionFactory) {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		return null != holder ? holder.getSession() : null;
	}

	public static void closeSession(SessionFactory sessionFactory) {
		try {
			SessionHolder holder = (SessionHolder) TransactionSynchronizationManager
					.getResource(sessionFactory);
			if (null != holder) {
				TransactionSynchronizationManager.unbindResource(sessionFactory);
				holder.getSession().close();
			}
		} catch (HibernateException ex) {
			logger.debug("Could not close Hibernate Session", ex);
		} catch (Throwable ex) {
			logger.debug("Unexpected exception on closing Hibernate Session", ex);
		}
	}

	public static void closeSession(Session session) {
		try {
			SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(session
					.getSessionFactory());
			if (null != holder) {
				TransactionSynchronizationManager.unbindResource(session.getSessionFactory());
			}
			session.close();
		} catch (HibernateException ex) {
			logger.debug("Could not close Hibernate Session", ex);
		} catch (Throwable ex) {
			logger.debug("Unexpected exception on closing Hibernate Session", ex);
		}
	}

	public static String toString(Session session) {
		return session.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(session));
	}

	@SuppressWarnings("serial")
	public static DataAccessException convertHibernateAccessException(HibernateException ex) {
		if (ex instanceof JDBCConnectionException) { return new DataAccessResourceFailureException(
				ex.getMessage(), ex); }
		if (ex instanceof SQLGrammarException) {
			SQLGrammarException jdbcEx = (SQLGrammarException) ex;
			return new InvalidDataAccessResourceUsageException(ex.getMessage() + "; SQL [" + jdbcEx.getSQL()
					+ "]", ex);
		}
		if (ex instanceof LockAcquisitionException) {
			LockAcquisitionException jdbcEx = (LockAcquisitionException) ex;
			return new CannotAcquireLockException(ex.getMessage() + "; SQL [" + jdbcEx.getSQL() + "]", ex);
		}
		if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException jdbcEx = (ConstraintViolationException) ex;
			return new DataIntegrityViolationException(ex.getMessage() + "; SQL [" + jdbcEx.getSQL()
					+ "]; constraint [" + jdbcEx.getConstraintName() + "]", ex);
		}
		if (ex instanceof DataException) {
			DataException jdbcEx = (DataException) ex;
			return new DataIntegrityViolationException(ex.getMessage() + "; SQL [" + jdbcEx.getSQL() + "]",
					ex);
		}
		if (ex instanceof PropertyValueException) { return new DataIntegrityViolationException(
				ex.getMessage(), ex); }
		if (ex instanceof PersistentObjectException) { return new InvalidDataAccessApiUsageException(
				ex.getMessage(), ex); }
		if (ex instanceof TransientObjectException) { return new InvalidDataAccessApiUsageException(
				ex.getMessage(), ex); }
		if (ex instanceof ObjectDeletedException) { return new InvalidDataAccessApiUsageException(
				ex.getMessage(), ex); }
		return new UncategorizedDataAccessException(ex.getMessage(), ex){};
	}
}
