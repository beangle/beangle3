/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity.types;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.entity.Type;

public class ComponentType extends AbstractType {

	private Class<?> componentClass;

	private final Map<String, Type> propertyTypes = CollectUtils.newHashMap();

	public boolean isComponentType() {
		return true;
	}

	public String getName() {
		return componentClass.toString();
	}

	public Class<?> getReturnedClass() {
		return componentClass;
	}

	public ComponentType() {
		super();
	}

	public ComponentType(Class<?> componentClass) {
		super();
		this.componentClass = componentClass;
	}

	/**
	 * Get the type of a particular (named) property
	 */
	public Type getPropertyType(String propertyName) {
		Type type = (Type) propertyTypes.get(propertyName);
		if (null == type) {
			Method getMethod = MethodUtils.getAccessibleMethod(componentClass,
					"get" + StringUtils.capitalize(propertyName), (Class[]) null);
			if (null != getMethod) { return new IdentifierType(getMethod.getReturnType()); }
		}
		return type;
	}

	public Map<String, Type> getPropertyTypes() {
		return propertyTypes;
	}

}
