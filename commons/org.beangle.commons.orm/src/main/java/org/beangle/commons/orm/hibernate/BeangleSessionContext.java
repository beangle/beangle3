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
