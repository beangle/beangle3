/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint;

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.lang.reflect.Reflections;
import org.beangle.security.blueprint.model.RoleBean;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ReflectorTest {

  public void testGet() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    //Method a = getterMethod(RoleBean.class, "parent");
    Assert.assertTrue(Integer.class.equals(Reflections.getPropertyType(RoleBean.class,"id")));
    Assert.assertTrue(Role.class.equals(Reflections.getPropertyType(RoleBean.class,"parent")));
    PropertyUtils.setProperty(new RoleBean(), "id", 1);
  }

  public static Method getterMethod(Class<?> theClass, String propertyName) {
    Method[] methods = theClass.getMethods();
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
