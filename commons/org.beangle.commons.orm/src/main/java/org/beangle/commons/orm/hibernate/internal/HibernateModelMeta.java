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

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.entity.metadata.impl.DefaultModelMeta;
import org.hibernate.SessionFactory;

/**
 * Building model from Hibernate.
 * 
 * @author chaostone
 * @since 2.0
 */
public class HibernateModelMeta extends DefaultModelMeta implements Initializing {

  private SessionFactory sessionFactory;

  public void init() throws Exception {
    HibernateEntityContext entityContext = new HibernateEntityContext();
    entityContext.initFrom(sessionFactory);
    setContext(entityContext);
    Model.setMeta(this);
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

}
