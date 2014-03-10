/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.orm.hibernate;

import java.sql.Connection;

import javax.sql.DataSource;

import org.beangle.commons.lang.Assert;
import org.beangle.orm.hibernate.internal.SessionHolder;
import org.beangle.orm.hibernate.internal.SessionUtils;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.transaction.spi.TransactionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Simplify HibernateTransactionManager in spring-orm bundle.
 * Just add SessionUtils.isEnableThreadBinding() support in doGetTranscation
 * 
 * @author chaostone
 * @version $Id: HibernateTransactionManager.java Feb 28, 2012 10:32:50 PM chaostone $
 */
public class HibernateTransactionManager extends AbstractPlatformTransactionManager implements
    ResourceTransactionManager, InitializingBean {

  private static final long serialVersionUID = 1L;

  private static final Logger logger = LoggerFactory.getLogger(HibernateTransactionManager.class);

  private SessionFactory sessionFactory;

  private DataSource dataSource;

  private boolean prepareConnection = true;

  private boolean hibernateManagedSession = false;

  /**
   * Create a new HibernateTransactionManager instance.
   * A SessionFactory has to be set to be able to use it.
   * 
   * @see #setSessionFactory
   */
  public HibernateTransactionManager() {
  }

  /**
   * Create a new HibernateTransactionManager instance.
   * 
   * @param sessionFactory SessionFactory to manage transactions for
   */
  public HibernateTransactionManager(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
    afterPropertiesSet();
  }

  /**
   * Set the SessionFactory that this instance should manage transactions for.
   */
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Return the SessionFactory that this instance should manage transactions for.
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }

  /**
   * Return the JDBC DataSource that this instance manages transactions for.
   */
  protected DataSource getDataSource() {
    return this.dataSource;
  }

  /**
   * Set whether to prepare the underlying JDBC Connection of a transactional
   * Hibernate Session, that is, whether to apply a transaction-specific
   * isolation level and/or the transaction's read-only flag to the underlying
   * JDBC Connection.
   * <p>
   * Default is "true". If you turn this flag off, the transaction manager will not support
   * per-transaction isolation levels anymore. It will not call
   * <code>Connection.setReadOnly(true)</code> for read-only transactions anymore either. If this
   * flag is turned off, no cleanup of a JDBC Connection is required after a transaction, since no
   * Connection settings will get modified.
   * 
   * @see java.sql.Connection#setTransactionIsolation
   * @see java.sql.Connection#setReadOnly
   */
  public void setPrepareConnection(boolean prepareConnection) {
    this.prepareConnection = prepareConnection;
  }

  /**
   * Set whether to operate on a Hibernate-managed Session instead of a
   * Spring-managed Session, that is, whether to obtain the Session through
   * Hibernate's {@link org.hibernate.SessionFactory#getCurrentSession()} instead of
   * {@link org.hibernate.SessionFactory#openSession()} (with a Spring
   * {@link org.springframework.transaction.support.TransactionSynchronizationManager} check
   * preceding it).
   * <p>
   * Default is "false", i.e. using a Spring-managed Session: taking the current thread-bound
   * Session if available (e.g. in an Open-Session-in-View scenario), creating a new Session for the
   * current transaction otherwise.
   * <p>
   * Switch this flag to "true" in order to enforce use of a Hibernate-managed Session. Note that
   * this requires {@link org.hibernate.SessionFactory#getCurrentSession()} to always return a
   * proper Session when called for a Spring-managed transaction; transaction begin will fail if the
   * <code>getCurrentSession()</code> call fails.
   * <p>
   * This mode will typically be used in combination with a custom Hibernate
   * {@link org.hibernate.context.CurrentSessionContext} implementation that stores Sessions in a
   * place other than Spring's TransactionSynchronizationManager. It may also be used in combination
   * with Spring's Open-Session-in-View support (using Spring's default
   * {@link BeangleSessionContext}), in which case it subtly differs from the Spring-managed Session
   * mode: The pre-bound Session will <i>not</i> receive a <code>clear()</code> call (on rollback)
   * or a <code>disconnect()</code> call (on transaction completion) in such a scenario; this is
   * rather left up to a custom CurrentSessionContext implementation (if desired).
   */
  public void setHibernateManagedSession(boolean hibernateManagedSession) {
    this.hibernateManagedSession = hibernateManagedSession;
  }

  public void afterPropertiesSet() {
    Assert.isTrue(null != getSessionFactory(), "Property 'sessionFactory' is required");
    dataSource = SessionUtils.getDataSource(getSessionFactory());
  }

  public Object getResourceFactory() {
    return getSessionFactory();
  }

  @Override
  protected Object doGetTransaction() {
    HibernateTransactionObject txObject = new HibernateTransactionObject();
    txObject.setSavepointAllowed(isNestedTransactionAllowed());

    SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
        .getResource(getSessionFactory());
    if (sessionHolder != null) {
      txObject.setSessionHolder(sessionHolder);
    } else if (this.hibernateManagedSession) {
      try {
        Session session = getSessionFactory().getCurrentSession();
        txObject.setExistingSession(session);
      } catch (HibernateException ex) {
        throw new DataAccessResourceFailureException(
            "Could not obtain Hibernate-managed Session for Spring-managed transaction", ex);
      }
    }
    // add by duantihua since 3.0.1
    else {
      if (SessionUtils.isEnableBinding(getSessionFactory()))
        txObject.setSessionHolder(SessionUtils.openSession(getSessionFactory()));
    }

    if (getDataSource() != null) {
      ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager
          .getResource(getDataSource());
      txObject.setConnectionHolder(conHolder);
    }

    return txObject;
  }

  @Override
  protected boolean isExistingTransaction(Object transaction) {
    HibernateTransactionObject txObject = (HibernateTransactionObject) transaction;
    return (txObject.hasSpringManagedTransaction() || (this.hibernateManagedSession && txObject
        .hasHibernateManagedTransaction()));
  }

  @Override
  protected void doBegin(Object transaction, TransactionDefinition definition) {
    HibernateTransactionObject txObject = (HibernateTransactionObject) transaction;

    if (txObject.hasConnectionHolder() && !txObject.getConnectionHolder().isSynchronizedWithTransaction()) { throw new IllegalTransactionStateException(
        "Pre-bound JDBC Connection found! HibernateTransactionManager does not support "
            + "running within DataSourceTransactionManager if told to manage the DataSource itself. "
            + "It is recommended to use a single HibernateTransactionManager for all transactions "
            + "on a single DataSource, no matter whether Hibernate or JDBC access."); }

    Session session = null;
    try {
      if (txObject.getSessionHolder() == null || txObject.getSessionHolder().isSynchronizedWithTransaction()) {
        Session newSession = getSessionFactory().openSession();
        txObject.setSession(newSession);
      }

      session = txObject.getSessionHolder().getSession();

      if (this.prepareConnection && isSameConnectionForEntireSession(session)) {
        // We're allowed to change the transaction settings of the JDBC Connection.
        Connection con = ((SessionImplementor) session).connection();
        Integer previousIsolationLevel = DataSourceUtils.prepareConnectionForTransaction(con, definition);
        txObject.setPreviousIsolationLevel(previousIsolationLevel);
      } else {
        // Not allowed to change the transaction settings of the JDBC Connection.
        if (definition.getIsolationLevel() != TransactionDefinition.ISOLATION_DEFAULT) {
          // We should set a specific isolation level but are not allowed to...
          throw new InvalidIsolationLevelException(
              "HibernateTransactionManager is not allowed to support custom isolation levels: "
                  + "make sure that its 'prepareConnection' flag is on (the default) and that the "
                  + "Hibernate connection release mode is set to 'on_close' (BeangleTransactionFactory's default). "
                  + "Make sure that your SessionFactoryBean actually uses BeangleTransactionFactory: Your "
                  + "Hibernate properties should *not* include a 'hibernate.transaction.factory_class' property!");
        }
      }

      if (definition.isReadOnly() && txObject.isNewSession()) {
        // Just set to NEVER in case of a new Session for this transaction.
        session.setFlushMode(FlushMode.MANUAL);
      }

      if (!definition.isReadOnly() && !txObject.isNewSession()) {
        // We need AUTO or COMMIT for a non-read-only transaction.
        FlushMode flushMode = session.getFlushMode();
        if (FlushMode.isManualFlushMode(session.getFlushMode())) {
          session.setFlushMode(FlushMode.AUTO);
          txObject.getSessionHolder().setPreviousFlushMode(flushMode);
        }
      }

      Transaction hibTx;

      // Register transaction timeout.
      int timeout = determineTimeout(definition);
      if (timeout != TransactionDefinition.TIMEOUT_DEFAULT) {
        // Use Hibernate's own transaction timeout mechanism on Hibernate 3.1+
        // Applies to all statements, also to inserts, updates and deletes!
        hibTx = session.getTransaction();
        hibTx.setTimeout(timeout);
        hibTx.begin();
      } else {
        // Open a plain Hibernate transaction without specified timeout.
        hibTx = session.beginTransaction();
      }

      // Add the Hibernate transaction to the session holder.
      txObject.getSessionHolder().setTransaction(hibTx);

      // Register the Hibernate Session's JDBC Connection for the DataSource, if set.
      if (getDataSource() != null) {
        Connection con = ((SessionImplementor) session).connection();
        ConnectionHolder conHolder = new ConnectionHolder(con);
        if (timeout != TransactionDefinition.TIMEOUT_DEFAULT) {
          conHolder.setTimeoutInSeconds(timeout);
        }
        if (logger.isDebugEnabled()) {
          logger.debug("Exposing Hibernate transaction as JDBC transaction [" + con + "]");
        }
        TransactionSynchronizationManager.bindResource(getDataSource(), conHolder);
        txObject.setConnectionHolder(conHolder);
      }

      // Bind the session holder to the thread.
      if (txObject.isNewSessionHolder()) {
        TransactionSynchronizationManager.bindResource(getSessionFactory(), txObject.getSessionHolder());
      }
      txObject.getSessionHolder().setSynchronizedWithTransaction(true);
    }

    catch (Exception ex) {
      if (txObject.isNewSession()) {
        try {
          if (session.getTransaction().isActive()) session.getTransaction().rollback();
        } catch (Throwable ex2) {
          logger.debug("Could not rollback Session after failed transaction begin", ex);
        } finally {
          SessionUtils.closeSession(session);
        }
      }
      throw new CannotCreateTransactionException("Could not open Hibernate Session for transaction", ex);
    }
  }

  @Override
  protected Object doSuspend(Object transaction) {
    HibernateTransactionObject txObject = (HibernateTransactionObject) transaction;
    txObject.setSessionHolder(null);
    SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
        .unbindResource(getSessionFactory());
    txObject.setConnectionHolder(null);
    ConnectionHolder connectionHolder = null;
    if (getDataSource() != null) {
      connectionHolder = (ConnectionHolder) TransactionSynchronizationManager.unbindResource(getDataSource());
    }
    return new SuspendedResourcesHolder(sessionHolder, connectionHolder);
  }

  @Override
  protected void doResume(Object transaction, Object suspendedResources) {
    SuspendedResourcesHolder resourcesHolder = (SuspendedResourcesHolder) suspendedResources;
    if (TransactionSynchronizationManager.hasResource(getSessionFactory())) {
      // From non-transactional code running in active transaction synchronization
      // -> can be safely removed, will be closed on transaction completion.
      TransactionSynchronizationManager.unbindResource(getSessionFactory());
    }
    TransactionSynchronizationManager.bindResource(getSessionFactory(), resourcesHolder.getSessionHolder());
    if (getDataSource() != null) {
      TransactionSynchronizationManager.bindResource(getDataSource(), resourcesHolder.getConnectionHolder());
    }
  }

  @Override
  protected void doCommit(DefaultTransactionStatus status) {
    HibernateTransactionObject txObject = (HibernateTransactionObject) status.getTransaction();
    try {
      txObject.getSessionHolder().getTransaction().commit();
    } catch (org.hibernate.TransactionException ex) {
      // assumably from commit call to the underlying JDBC connection
      throw new TransactionSystemException("Could not commit Hibernate transaction", ex);
    } catch (HibernateException ex) {
      // assumably failed to flush changes to database
      throw convertHibernateAccessException(ex);
    }
  }

  @Override
  protected void doRollback(DefaultTransactionStatus status) {
    HibernateTransactionObject txObject = (HibernateTransactionObject) status.getTransaction();
    try {
      txObject.getSessionHolder().getTransaction().rollback();
    } catch (org.hibernate.TransactionException ex) {
      throw new TransactionSystemException("Could not roll back Hibernate transaction", ex);
    } catch (HibernateException ex) {
      // Shouldn't really happen, as a rollback doesn't cause a flush.
      throw convertHibernateAccessException(ex);
    } finally {
      if (!txObject.isNewSession() && !this.hibernateManagedSession) {
        // Clear all pending inserts/updates/deletes in the Session.
        // Necessary for pre-bound Sessions, to avoid inconsistent state.
        txObject.getSessionHolder().getSession().clear();
      }
    }
  }

  @Override
  protected void doSetRollbackOnly(DefaultTransactionStatus status) {
    HibernateTransactionObject txObject = (HibernateTransactionObject) status.getTransaction();
    txObject.setRollbackOnly();
  }

  @Override
  protected void doCleanupAfterCompletion(Object transaction) {
    HibernateTransactionObject txObject = (HibernateTransactionObject) transaction;

    // Remove the session holder from the thread.
    if (txObject.isNewSessionHolder()) {
      TransactionSynchronizationManager.unbindResource(getSessionFactory());
    }

    // Remove the JDBC connection holder from the thread, if exposed.
    if (getDataSource() != null) {
      TransactionSynchronizationManager.unbindResource(getDataSource());
    }

    Session session = txObject.getSessionHolder().getSession();
    if (this.prepareConnection && session.isConnected() && isSameConnectionForEntireSession(session)) {
      // We're running with connection release mode "on_close": We're able to reset
      // the isolation level and/or read-only flag of the JDBC Connection here.
      // Else, we need to rely on the connection pool to perform proper cleanup.
      try {
        Connection con = ((SessionImplementor) session).connection();
        DataSourceUtils.resetConnectionAfterTransaction(con, txObject.getPreviousIsolationLevel());
      } catch (HibernateException ex) {
        logger.debug("Could not access JDBC Connection of Hibernate Session", ex);
      }
    }

    if (txObject.isNewSession()) {
      SessionUtils.closeSession(session);
    } else {
      if (txObject.getSessionHolder().getPreviousFlushMode() != null) {
        session.setFlushMode(txObject.getSessionHolder().getPreviousFlushMode());
      }
      if (!this.hibernateManagedSession) session.disconnect();

    }
    txObject.getSessionHolder().clear();
  }

  /**
   * Return whether the given Hibernate Session will always hold the same
   * JDBC Connection. This is used to check whether the transaction manager
   * can safely prepare and clean up the JDBC Connection used for a transaction.
   * <p>
   * Default implementation checks the Session's connection release mode to be "on_close".
   * Unfortunately, this requires casting to SessionImpl, as of Hibernate 3.1. If that cast doesn't
   * work, we'll simply assume we're safe and return <code>true</code>.
   * 
   * @param session the Hibernate Session to check
   * @see org.hibernate.impl.SessionImpl#getConnectionReleaseMode()
   * @see org.hibernate.ConnectionReleaseMode#ON_CLOSE
   */
  protected boolean isSameConnectionForEntireSession(Session session) {
    // The best we can do is to assume we're safe.
    if (!(session instanceof TransactionContext)) return true;
    ConnectionReleaseMode releaseMode = ((TransactionContext) session).getConnectionReleaseMode();
    return ConnectionReleaseMode.ON_CLOSE.equals(releaseMode);
  }

  /**
   * Convert the given HibernateException to an appropriate exception
   * from the <code>org.springframework.dao</code> hierarchy.
   * <p>
   * Will automatically apply a specified SQLExceptionTranslator to a Hibernate JDBCException, else
   * rely on Hibernate's default translation.
   * 
   * @param ex HibernateException that occured
   * @return a corresponding DataAccessException
   */
  protected DataAccessException convertHibernateAccessException(HibernateException ex) {
    return SessionUtils.convertHibernateAccessException(ex);
  }

  /**
   * Hibernate transaction object, representing a SessionHolder.
   * Used as transaction object by HibernateTransactionManager.
   */
  private class HibernateTransactionObject extends JdbcTransactionObjectSupport {

    private SessionHolder sessionHolder;

    private boolean newSessionHolder;

    private boolean newSession;

    public void setSession(Session session) {
      this.sessionHolder = new SessionHolder(session);
      this.newSessionHolder = true;
      this.newSession = true;
    }

    public void setExistingSession(Session session) {
      this.sessionHolder = new SessionHolder(session);
      this.newSessionHolder = true;
      this.newSession = false;
    }

    public void setSessionHolder(SessionHolder sessionHolder) {
      this.sessionHolder = sessionHolder;
      this.newSessionHolder = false;
      this.newSession = false;
    }

    public SessionHolder getSessionHolder() {
      return this.sessionHolder;
    }

    public boolean isNewSessionHolder() {
      return this.newSessionHolder;
    }

    public boolean isNewSession() {
      return this.newSession;
    }

    public boolean hasSpringManagedTransaction() {
      return (this.sessionHolder != null && this.sessionHolder.getTransaction() != null);
    }

    public boolean hasHibernateManagedTransaction() {
      return (this.sessionHolder != null && this.sessionHolder.getSession().getTransaction().isActive());
    }

    public void setRollbackOnly() {
      this.sessionHolder.setRollbackOnly();
      if (hasConnectionHolder()) {
        getConnectionHolder().setRollbackOnly();
      }
    }

    public boolean isRollbackOnly() {
      return this.sessionHolder.isRollbackOnly()
          || (hasConnectionHolder() && getConnectionHolder().isRollbackOnly());
    }

    public void flush() {
      try {
        this.sessionHolder.getSession().flush();
      } catch (HibernateException ex) {
        throw convertHibernateAccessException(ex);
      }
    }
  }

  /**
   * Holder for suspended resources.
   * Used internally by <code>doSuspend</code> and <code>doResume</code>.
   */
  private static class SuspendedResourcesHolder {

    private final SessionHolder sessionHolder;

    private final ConnectionHolder connectionHolder;

    private SuspendedResourcesHolder(SessionHolder sessionHolder, ConnectionHolder conHolder) {
      this.sessionHolder = sessionHolder;
      this.connectionHolder = conHolder;
    }

    private SessionHolder getSessionHolder() {
      return this.sessionHolder;
    }

    private ConnectionHolder getConnectionHolder() {
      return this.connectionHolder;
    }
  }

}
