package org.beangle.commons.lang.asm;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.tuple.Pair;

/**
 * @author chaostone
 */
public class ClassInfo {

  public static Map<Class<?>, ClassInfo> cache = CollectUtils.newHashMap();

  private Map<String, MethodInfo[]> methods = CollectUtils.newHashMap();

  private Map<String, MethodInfo> propertyReadMethods = CollectUtils.newHashMap();

  private Map<String, MethodInfo> propertyWriteMethods = CollectUtils.newHashMap();

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
    }
  }

  public int getReadMethodIndex(String property) {
    MethodInfo method = propertyReadMethods.get(property);
    return (null == method) ? -1 : method.index;
  }

  public Class<?> getPropertyType(String property) {
    MethodInfo method = propertyWriteMethods.get(property);
    if (null == method) return null;
    else return method.getParamTypes()[0];
  }

  public int getWriteMethodIndex(String property) {
    MethodInfo method = propertyWriteMethods.get(property);
    return (null == method) ? -1 : method.index;
  }

  public int getMethodIndex(String name, Object... args) {
    MethodInfo[] exists = methods.get(name);
    if (null == exists) throw new RuntimeException("NoSuchMethodException:" + name);
    if (1 == exists.length) return exists[0].index;
    for (MethodInfo info : exists)
      if (info.matches(args)) return info.index;
    throw new RuntimeException("NoSuchMethodException:" + name);
    // for (int i = 0, n = methodArrays.length; i < n; i++)
    // if (methodArrays[i].name.hashCode() == name.hashCode()) return i;
    // throw new IllegalArgumentException("Unable to find public method: ");
  }

  List<MethodInfo> getMethods(String name) {
    MethodInfo[] namedMethod = methods.get(name);
    if (null == namedMethod) return Collections.emptyList();
    else return Arrays.asList(namedMethod);
  }

  List<MethodInfo> getMethods() {
    List<MethodInfo> methodInfos = CollectUtils.newArrayList();
    for (Map.Entry<String, MethodInfo[]> entry : methods.entrySet()) {
      for (MethodInfo info : entry.getValue())
        methodInfos.add(info);
    }
    Collections.sort(methodInfos);
    return methodInfos;
  }

  public static ClassInfo get(Class<?> type) {
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
