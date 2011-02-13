/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity.context;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.Entity;
import org.beangle.model.entity.EntityContext;
import org.beangle.model.entity.Type;
import org.beangle.model.entity.types.EntityType;
import org.beangle.model.entity.types.IdentifierType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEntityContext implements EntityContext {
	/** entity-name->entity-type */
	protected Map<String, EntityType> entityTypes = CollectUtils.newHashMap();

	/** class-name->entity-type */
	protected Map<String, EntityType> classEntityTypes = CollectUtils.newHashMap();

	protected static final Logger logger = LoggerFactory.getLogger(AbstractEntityContext.class);

	public String[] getEntityNames(Class<?> clazz) {
		return new String[0];
	}

	public Type getType(String name) {
		Type type = getEntityType(name);
		if (null == type) {
			try {
				return new IdentifierType(Class.forName(name));
			} catch (ClassNotFoundException e) {
				logger.error("system doesn't contains entity {}", name);
			}
			return null;
		} else {
			return type;
		}
	}

	public String getEntityName(Object obj) {
		EntityType type = getEntityType(obj.getClass());
		if (null != type) {
			return type.getEntityName();
		} else {
			return null;
		}
	}

	public EntityType getEntityType(Class<?> entityClass) {
		String className = entityClass.getName();
		EntityType type = entityTypes.get(className);
		if (null != type) { return type; }

		type = classEntityTypes.get(className);
		if (null == type) {
			List<EntityType> matched = CollectUtils.newArrayList();
			for (EntityType entityType : entityTypes.values()) {
				if (className.equals(entityType.getEntityName())
						|| className.equals(entityType.getEntityClass().getName())) {
					matched.add(entityType);
				}
			}
			if (matched.size() > 1) { throw new RuntimeException("multi-entityName for class:" + className); }
			if (matched.isEmpty()) {
				EntityType tmp = new EntityType(entityClass);
				classEntityTypes.put(className, tmp);
				return tmp;
			} else {
				classEntityTypes.put(className, matched.get(0));
				type = (EntityType) matched.get(0);
			}
		}
		return type;
	}

	public EntityType getEntityType(String entityName) {
		EntityType type = entityTypes.get(entityName);
		if (null != type) { return type; }
		type = classEntityTypes.get(entityName);
		// last try by it's interface
		if (null == type) {
			try {
				// FIXME
				Class<?> entityClass = Class.forName(entityName);
				if (Entity.class.isAssignableFrom(entityClass)) {
					type = new EntityType(entityClass);
				} else {
					logger.warn("{} 's is not entity", entityClass);
				}
			} catch (ClassNotFoundException e) {
				logger.error("system doesn't contains entity {}", entityName);
			}
		}
		return type;
	}
}
