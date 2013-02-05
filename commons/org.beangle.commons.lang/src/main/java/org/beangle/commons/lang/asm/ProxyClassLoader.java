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
package org.beangle.commons.lang.asm;

import java.lang.reflect.Method;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

/**
 * ProxyClassLoader using bean's original classLoader define class
 * 
 * @author chaostone
 * @since 3.2.0
 */
final class ProxyClassLoader extends ClassLoader {
  static private final Map<ClassLoader, ProxyClassLoader> proxyClassLoaders = CollectUtils.newHashMap();

  public static ProxyClassLoader get(Class<?> type) {
    ClassLoader parent = type.getClassLoader();
    ProxyClassLoader loader = proxyClassLoaders.get(parent);
    if (null == loader) {
      synchronized (proxyClassLoaders) {
        loader = proxyClassLoaders.get(parent);
        if (null == loader) {
          loader = new ProxyClassLoader(parent);
          proxyClassLoaders.put(parent, loader);
        }
      }
    }
    return loader;
  }

  private ProxyClassLoader(ClassLoader parent) {
    super(parent);
  }

  Class<?> defineClass(String name, byte[] bytes) throws ClassFormatError {
    try {
      // Attempt to load the access class in the same loader, which makes protected and default
      // access members accessible.
      Method method = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class,
          byte[].class, int.class, int.class });
      method.setAccessible(true);
      return (Class<?>) method.invoke(getParent(),
          new Object[] { name, bytes, Integer.valueOf(0), Integer.valueOf(bytes.length) });
    } catch (Exception ignored) {
    }
    return defineClass(name, bytes, 0, bytes.length);
  }
}
