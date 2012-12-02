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
package org.beangle.commons.orm.hibernate;

import org.beangle.commons.orm.hibernate.internal.SessionHolder;
import org.beangle.commons.orm.hibernate.internal.SessionUtils;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.spi.CurrentSessionContext;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.core.Ordered;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author chaostone
 * @version $Id: BeangleSessionContext.java Feb 27, 2012 11:30:56 PM chaostone $
 */
@SuppressWarnings("serial")
public class BeangleSessionContext implements CurrentSessionContext {
  private final SessionFactoryImplementor sessionFactory;

  /**
   * Create a new SpringSessionContext for the given Hibernate SessionFactory.
   * 
   * @param sessionFactory the SessionFactory to provide current Sessions for
   */
  public BeangleSessionContext(SessionFactoryImplementor sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Retrieve the Spring-managed Session for the current thread, if any.
   */
  public Session currentSession() throws HibernateException {
    SessionHolder sessionHolder = SessionUtils.currentSession(this.sessionFactory);
    Session session = sessionHolder.getSession();
    // FIXME what time enter into the code?
    if (TransactionSynchronizationManager.isSynchronizationActive()
        && !sessionHolder.isSynchronizedWithTransaction()) {
      TransactionSynchronizationManager.registerSynchronization(new SessionSynchronization(sessionHolder,
          this.sessionFactory));
      sessionHolder.setSynchronizedWithTransaction(true);
      // Switch to FlushMode.AUTO, as we have to assume a thread-bound Session
      // with FlushMode.MANUAL, which needs to allow flushing within the transaction.
      FlushMode flushMode = session.getFlushMode();
      if (FlushMode.isManualFlushMode(flushMode)
          && !TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
        session.setFlushMode(FlushMode.AUTO);
        sessionHolder.setPreviousFlushMode(flushMode);
      }
    }
    return session;
  }
}

/**
 * Borrow from Spring Session Synchronization
 * 
 * @author chaostone
 */
class SessionSynchronization implements TransactionSynchronization, Ordered {

  private final SessionHolder sessionHolder;

  private final SessionFactory sessionFactory;

  private boolean holderActive = true;

  public SessionSynchronization(SessionHolder sessionHolder, SessionFactory sessionFactory) {
    this.sessionHolder = sessionHolder;
    this.sessionFactory = sessionFactory;
  }

  private Session getCurrentSession() {
    return this.sessionHolder.getSession();
  }

  public int getOrder() {
    // return SessionFactoryUtils.SESSION_SYNCHRONIZATION_ORDER;
    return 1000 - 100;
  }

  public void suspend() {
    if (this.holderActive) {
      TransactionSynchronizationManager.unbindResource(this.sessionFactory);
      // Eagerly disconnect the Session here, to make release mode "on_close" work on JBoss.
      getCurrentSession().disconnect();
    }
  }

  public void resume() {
    if (this.holderActive) {
      TransactionSynchronizationManager.bindResource(this.sessionFactory, this.sessionHolder);
    }
  }

  public void flush() {
    try {
      getCurrentSession().flush();
    } catch (HibernateException ex) {
      throw SessionUtils.convertHibernateAccessException(ex);
    }
  }

  public void beforeCommit(boolean readOnly) throws DataAccessException {
    if (!readOnly) {
      Session session = getCurrentSession();
      // Read-write transaction -> flush the Hibernate Session.
      // Further check: only flush when not FlushMode.MANUAL.
      if (!FlushMode.isManualFlushMode(session.getFlushMode())) {
        try {
          session.flush();
        } catch (HibernateException ex) {
          throw SessionUtils.convertHibernateAccessException(ex);
        }
      }
    }
  }

  public void beforeCompletion() {
    Session session = this.sessionHolder.getSession();
    if (this.sessionHolder.getPreviousFlushMode() != null) {
      // In case of pre-bound Session, restore previous flush mode.
      session.setFlushMode(this.sessionHolder.getPreviousFlushMode());
    }
    // Eagerly disconnect the Session here, to make release mode "on_close" work nicely.
    session.disconnect();
  }

  public void afterCommit() {
  }

  public void afterCompletion(int status) {
    try {
      if (status != STATUS_COMMITTED) {
        // Clear all pending inserts/updates/deletes in the Session.
        // Necessary for pre-bound Sessions, to avoid inconsistent state.
        this.sessionHolder.getSession().clear();
      }
    } finally {
      this.sessionHolder.setSynchronizedWithTransaction(false);
    }
  }

}
