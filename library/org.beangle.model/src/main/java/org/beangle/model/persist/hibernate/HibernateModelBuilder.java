/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate;

import org.beangle.model.entity.Model;
import org.beangle.model.entity.ModelBuilder;
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
