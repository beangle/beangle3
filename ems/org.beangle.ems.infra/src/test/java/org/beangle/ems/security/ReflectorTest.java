/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import java.beans.Introspector;
import java.lang.reflect.Method;

import org.apache.commons.lang.ArrayUtils;
import org.beangle.ems.security.model.GroupBean;
import org.testng.annotations.Test;

@Test
public class ReflectorTest {

	public void testGet() {
		Method a = getterMethod(GroupBean.class, "parent");
		System.out.println(a.getReturnType());
	}

	private static Method getterMethod(Class<?> theClass, String propertyName) {
		Method[] methods = theClass.getDeclaredMethods();
		ArrayUtils.reverse(methods);
		for (Method method : methods) {
			if ("getParent".equals(method.getName())) {
				System.out.println(method.getReturnType());
			}
			// if the method has parameters, skip it
			if (method.getParameterTypes().length != 0) {
				continue;
			}
			// if the method is a "bridge", skip it
			if (method.isBridge()) {
				continue;
			}

			final String methodName = method.getName();

			// try "get"
			if (methodName.startsWith("get")) {
				String testStdMethod = Introspector.decapitalize(methodName.substring(3));
				String testOldMethod = methodName.substring(3);
				if (testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName)) { return method; }
			}

			// if not "get", then try "is"
			if (methodName.startsWith("is")) {
				String testStdMethod = Introspector.decapitalize(methodName.substring(2));
				String testOldMethod = methodName.substring(2);
				if (testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName)) { return method; }
			}
		}

		return null;
	}
}
