/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm.hibernate.internal;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.dao.metadata.Model;
import org.beangle.commons.dao.metadata.ModelBuilder;
import org.hibernate.SessionFactory;

public class HibernateModelBuilder implements ModelBuilder, Initializing {

  private SessionFactory sessionFactory;

  public void build() {
    try {
      init();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void init() throws Exception {
    HibernateEntityContext entityContext = new HibernateEntityContext();
    entityContext.initFrom(sessionFactory);
    Model.setContext(entityContext);
  }

  public Model getModel() {
    return Model.getInstance();
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

}
