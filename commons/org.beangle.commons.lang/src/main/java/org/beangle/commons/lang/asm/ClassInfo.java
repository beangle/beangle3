package org.beangle.commons.lang.asm;

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
 * @author chaostone
 */
public final class ClassInfo {

  public static Map<Class<?>, ClassInfo> cache = CollectUtils.newHashMap();

  private final FastHashMap<String, Integer> methodIndexs = CollectUtils.newFastMap(64);

  private final FastHashMap<String, MethodInfo[]> methods = CollectUtils.newFastMap(64);

  private final FastHashMap<String, MethodInfo> propertyReadMethods = CollectUtils.newFastMap(64);

  private final FastHashMap<String, MethodInfo> propertyWriteMethods = CollectUtils.newFastMap(64);

  public ClassInfo(List<MethodInfo> methodinfos) {
    super();
    Map<String, List<MethodInfo>> tmpMethods = CollectUtils.newHashMap();
    for (MethodInfo info : methodinfos) {
      List<MethodInfo> named = tmpMethods.get(info.getName());
      if (null == named) {
        named = CollectUtils.newArrayList();
        tmpMethods.put(info.getName(), named);
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

  public final int getReadMethodIndex(String property) {
    MethodInfo method = propertyReadMethods.get(property);
    return (null == method) ? -1 : method.index;
  }

  public final Class<?> getPropertyType(String property) {
    MethodInfo method = propertyWriteMethods.get(property);
    if (null == method) return null;
    else return method.getParamTypes()[0];
  }

  public final int getWriteMethodIndex(String property) {
    MethodInfo method = propertyWriteMethods.get(property);
    return (null == method) ? -1 : method.index;
  }

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

  final List<MethodInfo> getMethods(String name) {
    MethodInfo[] namedMethod = methods.get(name);
    if (null == namedMethod) return Collections.emptyList();
    else return Arrays.asList(namedMethod);
  }

  final List<MethodInfo> getMethods() {
    List<MethodInfo> methodInfos = CollectUtils.newArrayList();
    for (Map.Entry<String, MethodInfo[]> entry : methods.entrySet()) {
      for (MethodInfo info : entry.getValue())
        methodInfos.add(info);
    }
    Collections.sort(methodInfos);
    return methodInfos;
  }

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
              methods.add(new MethodInfo(index, method.getName(), method.getReturnType(), method
                  .getParameterTypes()));
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
