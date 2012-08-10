/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm.hibernate;

import org.beangle.commons.orm.hibernate.internal.SessionUtils;
import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.hibernate.context.CurrentSessionContext;
import org.hibernate.engine.SessionFactoryImplementor;

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
    return (org.hibernate.classic.Session) SessionUtils.currentSession(this.sessionFactory);
  }
}
