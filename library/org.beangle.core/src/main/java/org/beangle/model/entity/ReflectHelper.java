/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;

public final class ReflectHelper {

	private ReflectHelper() {
	}

	public static Class<?> getProperty(Class<?> clazz, String property) {
		Method getMethod = MethodUtils.getAccessibleMethod(clazz, "get" + StringUtils.capitalize(property),
				(Class[]) null);
		if (null == getMethod) {
			return null;
		} else {
			return getMethod.getReturnType();
		}
	}
}
