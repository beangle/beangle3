/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.model.entity.context.DefaultModelBuilder;
import org.beangle.model.entity.types.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Model {

	public static final String NULL = "null";

	private static final Logger logger = LoggerFactory.getLogger(Model.class);

	private static EntityContext context;

	private static Populator populator;

	private static Model instance = new Model();

	static {
		new DefaultModelBuilder().build();
	}

	private Model() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(final Class<T> clazz) {
		return (T) getEntityType(clazz).newInstance();
	}

	public static <T> T newInstance(final Class<T> clazz, final Serializable id) {
		@SuppressWarnings("unchecked")
		T entity = (T) getEntityType(clazz).newInstance();
		try {
			PropertyUtils.setProperty(entity, "id", id);
		} catch (Exception e) {
			logger.error("initialize {} with id {} error", clazz, id);
		}
		return entity;
	}

	public static EntityType getEntityType(String entityName) {
		return context.getEntityType(entityName);
	}

	public static Type getType(String entityName) {
		return context.getType(entityName);
	}

	public static String getEntityName(Object obj) {
		return context.getEntityName(obj);
	}

	public static EntityType getEntityType(Class<?> clazz) {
		EntityType type = context.getEntityType(clazz);
		if (null == type) {
			type = new EntityType(clazz);
		}
		return type;
	}

	/**
	 * 将params中的属性([attr(string)->value(object)]，放入到实体类中。<br>
	 * 如果引用到了别的实体，那么<br>
	 * 如果params中的id为null，则将该实体的置为null.<br>
	 * 否则新生成一个实体，将其id设为params中指定的值。 空字符串按照null处理
	 * 
	 * @param params
	 * @param entity
	 */
	public static void populate(Map<String, Object> params, Object entity) {
		populator.populate(entity, params);
	}

	public static EntityContext getContext() {
		return context;
	}

	public static void setContext(EntityContext context) {
		Model.context = context;
	}

	public static Populator getPopulator() {
		return populator;
	}

	public static void setPopulator(Populator populator) {
		Model.populator = populator;
	}

	public static Model getInstance() {
		return instance;
	}

}
