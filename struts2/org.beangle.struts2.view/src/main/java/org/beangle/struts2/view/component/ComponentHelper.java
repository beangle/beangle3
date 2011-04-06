/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;

public class ComponentHelper {

	protected static Map<Class<?>, Map<String, Method>> writables = CollectUtils.newHashMap();

	public static final void registe(Class<?> clazz) {
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
		Map<String, Method> keys = CollectUtils.newHashMap();
		for (PropertyDescriptor a : descriptors) {
			if (null != a.getWriteMethod()) {
				keys.put(a.getName(), a.getWriteMethod());
			}
		}
		writables.put(clazz, keys);
	}

	public static Method getWriteMethod(Object bean, String property) {
		Class<?> clazz = bean.getClass();
		Map<String, Method> keys = writables.get(clazz);
		if (null == keys) {
			registe(clazz);
			return writables.get(clazz).get(property);
		} else return keys.get(property);
	}

	public static boolean set(Object bean, String property, Object value) {
		Class<?> clazz = bean.getClass();
		Map<String, Method> keys = writables.get(clazz);
		if (null == keys) {
			registe(clazz);
			keys = writables.get(clazz);
		}
		Method m = keys.get(property);
		if (null == m) return false;
		else {
			try {
				m.invoke(bean, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}
}
