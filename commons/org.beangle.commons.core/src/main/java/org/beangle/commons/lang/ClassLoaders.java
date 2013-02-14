/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.commons.lang;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

/**
 * @author chaostone
 * @since 3.0.0
 */
public final class ClassLoaders {

  /**
   * Return the default ClassLoader to use: typically the thread context
   * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
   * class will be used as fallback.
   * <p>
   * Call this method if you intend to use the thread context ClassLoader in a scenario where you
   * absolutely need a non-null ClassLoader reference: for example, for class path resource loading
   * (but not necessarily for <code>Class.forName</code>, which accepts a <code>null</code>
   * ClassLoader reference as well).
   * 
   * @return the default ClassLoader (never <code>null</code>)
   * @see java.lang.Thread#getContextClassLoader()
   */
  public static ClassLoader getDefaultClassLoader() {
    ClassLoader cl = null;
    try {
      cl = Thread.currentThread().getContextClassLoader();
    } catch (Throwable ex) {
      // Cannot access thread context ClassLoader - falling back to system class loader...
    }
    if (cl == null) {
      // No thread context class loader -> use class loader of this class.
      cl = ClassLoaders.class.getClassLoader();
    }
    return cl;
  }

  /**
   * Load a given resource(Cannot start with slash /).
   * <p/>
   * This method will try to load the resource using the following methods (in order):
   * <ul>
   * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
   * <li>From {@link Class#getClassLoader() ClassLoaders.class.getClassLoader()}
   * <li>From the {@link Class#getClassLoader() callingClass.getClassLoader() }
   * </ul>
   * 
   * @param resourceName The name of the resource to load
   * @param callingClass The Class object of the calling object
   */
  public static URL getResource(String resourceName, Class<?> callingClass) {
    URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
    if (url != null) return url;

    url = ClassLoaders.class.getClassLoader().getResource(resourceName);
    if (url != null) return url;

    ClassLoader cl = callingClass.getClassLoader();
    if (cl != null) url = cl.getResource(resourceName);
    return url;
  }

  /**
   * Load list of resource(Cannot start with slash /).
   * <p/>
   * This method will try to load the resource using the following methods (in order):
   * <ul>
   * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
   * <li>From {@link Class#getClassLoader() ClassLoaders.class.getClassLoader()}
   * <li>From the {@link Class#getClassLoader() callingClass.getClassLoader() }
   * </ul>
   * 
   * @param resourceName
   * @param callingClass
   * @return List of resources url or empty list.
   */
  public static List<URL> getResources(String resourceName, Class<?> callingClass) {
    Enumeration<URL> em = null;
    try {
      em = Thread.currentThread().getContextClassLoader().getResources(resourceName);
      if (!em.hasMoreElements()) {
        em = ClassLoaders.class.getClassLoader().getResources(resourceName);
        if (!em.hasMoreElements()) {
          ClassLoader cl = callingClass.getClassLoader();
          if (cl != null) em = cl.getResources(resourceName);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    List<URL> urls = CollectUtils.newArrayList();
    while (null != em && em.hasMoreElements()) {
      urls.add(em.nextElement());
    }
    return urls;

  }

  /**
   * This is a convenience method to load a resource as a stream.
   * The algorithm used to find the resource is given in getResource()
   * 
   * @param resourceName The name of the resource to load
   * @param callingClass The Class object of the calling object
   */
  public static InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
    URL url = getResource(resourceName, callingClass);
    try {
      return (url != null) ? url.openStream() : null;
    } catch (IOException e) {
      return null;
    }
  }

  public static Class<?> loadClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw Throwables.propagate(e);
    }
  }
}
