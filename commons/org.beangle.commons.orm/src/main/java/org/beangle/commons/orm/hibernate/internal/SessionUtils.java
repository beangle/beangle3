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
package org.beangle.commons.orm.hibernate.internal;

import static org.springframework.transaction.support.TransactionSynchronizationManager.bindResource;
import static org.springframework.transaction.support.TransactionSynchronizationManager.getResource;
import static org.springframework.transaction.support.TransactionSynchronizationManager.unbindResource;

import javax.sql.DataSource;

import org.hibernate.*;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.*;

/**
 * Open or Close Hibernate Session
 * 
 * @author chaostone
 * @version $Id: SessionFactoryUtils.java Feb 27, 2012 10:34:08 PM chaostone $
 */
public final class SessionUtils {

  private static Logger logger = LoggerFactory.getLogger(SessionUtils.class);

  private static final ThreadLocal<Boolean> threadBinding = new ThreadLocal<Boolean>();

  public static DataSource getDataSource(SessionFactory sessionFactory) {
    if (sessionFactory instanceof SessionFactoryImplementor) {
      ConnectionProvider cp = ((SessionFactoryImplementor) sessionFactory).getConnectionProvider();
      return cp.unwrap(DataSource.class);
    }
    return null;
  }

  public static void enableThreadBinding() {
    threadBinding.set(Boolean.TRUE);
  }

  public static boolean isEnableThreadBinding() {
    return null != threadBinding.get();
  }

  public static void disableThreadBinding() {
    threadBinding.remove();
  }

  public static SessionHolder openSession(SessionFactory sessionFactory)
      throws DataAccessResourceFailureException, IllegalStateException {
    try {
      SessionHolder holder = (SessionHolder) getResource(sessionFactory);
      Session session = null;
      if (null == holder) {
        session = sessionFactory.openSession();
        session.setFlushMode(FlushMode.MANUAL);
        holder = new SessionHolder(session);
        if (null != threadBinding.get()) bindResource(sessionFactory, holder);
      }
      return holder;
    } catch (HibernateException ex) {
      throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
    }
  }

  public static SessionHolder currentSession(SessionFactory sessionFactory) {
    return (SessionHolder) getResource(sessionFactory);
  }

  public static void closeSession(SessionFactory sessionFactory) {
    try {
      SessionHolder holder = (SessionHolder) getResource(sessionFactory);
      if (null != holder) {
        unbindResource(sessionFactory);
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
      SessionHolder holder = (SessionHolder) getResource(session.getSessionFactory());
      if (null != holder) unbindResource(session.getSessionFactory());
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
      return new InvalidDataAccessResourceUsageException(ex.getMessage() + "; SQL [" + jdbcEx.getSQL() + "]",
          ex);
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
      return new DataIntegrityViolationException(ex.getMessage() + "; SQL [" + jdbcEx.getSQL() + "]", ex);
    }
    if (ex instanceof NonUniqueResultException) { return new IncorrectResultSizeDataAccessException(
        ex.getMessage(), 1, ex); }
    if (ex instanceof NonUniqueObjectException) { return new DuplicateKeyException(ex.getMessage(), ex); }
    if (ex instanceof PropertyValueException) { return new DataIntegrityViolationException(ex.getMessage(),
        ex); }
    if (ex instanceof PersistentObjectException) { return new InvalidDataAccessApiUsageException(
        ex.getMessage(), ex); }
    if (ex instanceof TransientObjectException) { return new InvalidDataAccessApiUsageException(
        ex.getMessage(), ex); }
    if (ex instanceof ObjectDeletedException) { return new InvalidDataAccessApiUsageException(
        ex.getMessage(), ex); }
    return new UncategorizedDataAccessException(ex.getMessage(), ex) {
    };
  }

}
