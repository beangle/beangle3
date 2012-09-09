/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author chaostone
 * @since 3.0.0
 */
public final class ClassLoaders {

  /**
   * Load a given resource(Cannot start with slash /).
   * <p/>
   * This method will try to load the resource using the following methods (in order):
   * <ul>
   * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
   * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}
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
    if (url != null) return url;

    return url;
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
