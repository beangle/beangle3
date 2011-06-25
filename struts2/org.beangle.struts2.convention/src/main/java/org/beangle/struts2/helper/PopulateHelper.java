/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.helper;

import java.util.Map;

import org.beangle.model.entity.Model;
import org.beangle.model.entity.types.EntityType;
import org.beangle.model.util.EntityUtils;

public class PopulateHelper {

	/**
	 * 将request中的参数设置到clazz对应的bean。
	 * 
	 * @param request
	 * @param clazz
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T populate(Class<T> clazz, String name) {
		EntityType type = null;
		if (clazz.isInterface()) {
			type = Model.getEntityType(clazz.getName());
		} else {
			type = Model.getEntityType(clazz);
		}
		return (T) populate(type.newInstance(), type.getEntityName(), name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T populate(Class<T> clazz) {
		EntityType type = null;
		if (clazz.isInterface()) {
			type = Model.getEntityType(clazz.getName());
		} else {
			type = Model.getEntityType(clazz);
		}
		String entityName = type.getEntityName();
		return (T) populate(type.newInstance(), entityName, EntityUtils.getCommandName(entityName));
	}

	public static Object populate(String entityName) {
		EntityType type = Model.getEntityType(entityName);
		return populate(type.newInstance(), type.getEntityName(), EntityUtils.getCommandName(entityName));
	}

	public static Object populate(String entityName, String name) {
		EntityType type = Model.getEntityType(entityName);
		return populate(type.newInstance(), type.getEntityName(), name);
	}

	public static Object populate(Object obj, String entityName, String name) {
		Map<String, Object> params = Params.sub(name);
		return Model.getPopulator().populate(obj, entityName, params);
	}
}
