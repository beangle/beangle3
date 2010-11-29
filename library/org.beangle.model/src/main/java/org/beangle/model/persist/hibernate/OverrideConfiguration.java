/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate;

import org.hibernate.DuplicateMappingException;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Mappings;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OverrideConfiguration extends Configuration {

	private static Logger logger = LoggerFactory.getLogger(OverrideConfiguration.class);

	@Override
	public Mappings createMappings() {
		return new OverrideMappings();
	}

	protected class OverrideMappings extends MappingsImpl {

		@SuppressWarnings("unchecked")
		@Override
		public void addClass(PersistentClass persistentClass) throws DuplicateMappingException {
			PersistentClass old = (PersistentClass) classes.get(persistentClass.getEntityName());
			if (old == null) {
				classes.put(persistentClass.getEntityName(), persistentClass);
				return;
			}
			if (old.getMappedClass().isAssignableFrom(persistentClass.getMappedClass())) {
				classes.put(persistentClass.getEntityName(), persistentClass);
				logger.info("{} override {} for entity configuration", persistentClass
						.getClassName(), old.getClassName());
			}
		}

		@Override
		public void addCollection(Collection collection) throws DuplicateMappingException {
			collections.put(collection.getRole(), collection);
		}

	}
}
