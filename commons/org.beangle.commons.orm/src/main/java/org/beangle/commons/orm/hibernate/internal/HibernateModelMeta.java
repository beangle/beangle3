/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
