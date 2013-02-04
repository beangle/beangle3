package org.beangle.commons.lang.asm;

import java.lang.reflect.Method;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

/**
 * 
 * @author chaostone
 *
 */
class ProxyClassLoader extends ClassLoader {
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
