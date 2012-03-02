/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.orm.hibernate.internal;

import org.beangle.dao.metadata.Model;
import org.beangle.dao.metadata.ModelBuilder;
import org.hibernate.SessionFactory;

public class HibernateModelBuilder implements ModelBuilder {

	private SessionFactory sessionFactory;

	public void build() {
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
