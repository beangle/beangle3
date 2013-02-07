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
package org.beangle.commons.lang.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.FastHashMap;
import org.beangle.commons.lang.tuple.Pair;

/**
 * Class meta information.It contains method signature,property names
 * 
 * @author chaostone
 * @since 3.2.0
 */
public final class ClassInfo {

  /** class info cache */
  public static Map<Class<?>, ClassInfo> cache = CollectUtils.newHashMap();

  /** unqiue method indexes,without any override */
  private final FastHashMap<String, Integer> methodIndexs = CollectUtils.newFastMap(64);

  /** all method indexes */
  private final FastHashMap<String, MethodInfo[]> methods = CollectUtils.newFastMap(64);

  /** property read method indexes */
  private final FastHashMap<String, MethodInfo> propertyReadMethods = CollectUtils.newFastMap(64);

  /** property write method indexes */
  private final FastHashMap<String, MethodInfo> propertyWriteMethods = CollectUtils.newFastMap(64);

  /**
   * Construct Classinfo by method list.
   */
  public ClassInfo(List<MethodInfo> methodinfos) {
    super();
    Map<String, List<MethodInfo>> tmpMethods = CollectUtils.newHashMap();
    for (MethodInfo info : methodinfos) {
      List<MethodInfo> named = tmpMethods.get(info.method.getName());
      if (null == named) {
        named = CollectUtils.newArrayList();
        tmpMethods.put(info.method.getName(), named);
      }
      named.add(info);
      Pair<Boolean, String> propertyInfo = info.property();
      if (null != propertyInfo) {
        if (propertyInfo.getLeft()) propertyReadMethods.put(propertyInfo.getRight(), info);
        else propertyWriteMethods.put(propertyInfo.getRight(), info);
      }
    }
    for (Map.Entry<String, List<MethodInfo>> entry : tmpMethods.entrySet()) {
      methods.put(entry.getKey(), entry.getValue().toArray(new MethodInfo[entry.getValue().size()]));
      if (entry.getValue().size() == 1)
        methodIndexs.put(entry.getKey(), Integer.valueOf(entry.getValue().get(0).index));
    }
  }

  /**
   * Return property read index,return -1 when not found.
   */
  public final int getReadIndex(String property) {
    MethodInfo method = propertyReadMethods.get(property);
    return (null == method) ? -1 : method.index;
  }

  /**
   * Return property read index,return -1 when not found.
   */
  public final MethodInfo getReader(String property) {
    return propertyReadMethods.get(property);
  }

  /**
   * Return property type,return null when not found.
   */
  public final Class<?> getPropertyType(String property) {
    MethodInfo info = propertyWriteMethods.get(property);
    if (null == info) return null;
    else return info.method.getParameterTypes()[0];
  }

  /**
   * Return property write index,return -1 if not found.
   */
  public final int getWriteIndex(String property) {
    MethodInfo method = propertyWriteMethods.get(property);
    return (null == method) ? -1 : method.index;
  }

  /**
   * Return property write method,return null if not found.
   */
  public final MethodInfo getWriter(String property) {
    return propertyWriteMethods.get(property);
  }

  /**
   * Return method index,return -1 if not found.
   */
  public final int getIndex(String name, Object... args) {
    Integer defaultIndex = methodIndexs.get(name);
    if (null != defaultIndex) return defaultIndex.intValue();
    else {
      final MethodInfo[] exists = methods.get(name);
      if (null != exists) {
        for (MethodInfo info : exists)
          if (info.matches(args)) return info.index;
      }
      return -1;
    }
  }

  /**
   * Return public metheds according to given name
   */
  final List<MethodInfo> getMethods(String name) {
    MethodInfo[] namedMethod = methods.get(name);
    if (null == namedMethod) return Collections.emptyList();
    else return Arrays.asList(namedMethod);
  }

  /**
   * Return all public methods.
   */
  public final List<MethodInfo> getMethods() {
    List<MethodInfo> methodInfos = CollectUtils.newArrayList();
    for (Map.Entry<String, MethodInfo[]> entry : methods.entrySet()) {
      for (MethodInfo info : entry.getValue())
        methodInfos.add(info);
    }
    Collections.sort(methodInfos);
    return methodInfos;
  }

  /**
   * Get ClassInfo by type.
   * It search from cache, when failure build it and put it into cache.
   */
  public static final ClassInfo get(Class<?> type) {
    ClassInfo exist = cache.get(type);
    if (null == exist) {
      synchronized (cache) {
        exist = cache.get(type);
        if (null == exist) {
          ArrayList<MethodInfo> methods = new ArrayList<MethodInfo>();
          Class<?> nextClass = type;
          int index = -1;
          while (nextClass != Object.class) {
            Method[] declaredMethods = nextClass.getDeclaredMethods();
            for (int i = 0, n = declaredMethods.length; i < n; i++) {
              Method method = declaredMethods[i];
              int modifiers = method.getModifiers();
              if (Modifier.isStatic(modifiers)) continue;
              if (Modifier.isPrivate(modifiers)) continue;
              index++;
              methods.add(new MethodInfo(index, method));
            }
            nextClass = nextClass.getSuperclass();
          }
          exist = new ClassInfo(methods);
          cache.put(type, exist);
        }
      }
    }
    return exist;
  }
}
