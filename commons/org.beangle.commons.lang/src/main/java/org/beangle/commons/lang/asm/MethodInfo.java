package org.beangle.commons.lang.asm;

import static java.lang.Character.isUpperCase;

import static org.beangle.commons.lang.Strings.*;
import org.beangle.commons.lang.tuple.Pair;

class MethodInfo implements Comparable<MethodInfo> {

  final Class<?> returnType;
  final String name;
  final Class<?>[] paramTypes;
  final int index;

  public MethodInfo(int index, String name, Class<?> returnType, Class<?>[] paramTypes) {
    super();
    this.index = index;
    this.name = name;
    this.returnType = returnType;
    this.paramTypes = paramTypes;
  }

  public Class<?> getReturnType() {
    return returnType;
  }

  public String getName() {
    return name;
  }

  public Class<?>[] getParamTypes() {
    return paramTypes;
  }

  /**
   * Return thid method is property read method (0) or write method(1) or none(-1).
   */
  public Pair<Boolean, String> property() {
    if (name.length() > 3 && name.startsWith("get") && isUpperCase(name.charAt(3)) && paramTypes.length == 0) {
      return Pair.of(Boolean.TRUE, uncapitalize(substringAfter(name, "get")));
    } else if (name.length() > 2 && name.startsWith("is") && isUpperCase(name.charAt(2))
        && paramTypes.length == 0) {
      return Pair.of(Boolean.TRUE, uncapitalize(substringAfter(name, "is")));
    } else if (name.length() > 3 && name.startsWith("set") && isUpperCase(name.charAt(3))
        && paramTypes.length == 1) { return Pair.of(Boolean.FALSE, uncapitalize(substringAfter(name, "set"))); }
    return null;
  }

  @Override
  public int compareTo(MethodInfo o) {
    return this.index - o.index;
  }

  public boolean matches(Object[] args) {
    if (paramTypes.length != args.length) return false;
    for (int i = 0; i < args.length; i++) {
      if (null != args[i] && !paramTypes[i].isInstance(args[i])) return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append((null == returnType) ? "void" : returnType.getSimpleName());
    sb.append(' ').append(name);
    if (paramTypes.length == 0) {
      sb.append("()");
    } else {
      sb.append('(');
      for (Class<?> type : paramTypes) {
        sb.append(type.getSimpleName()).append(",");
      }
      sb.deleteCharAt(sb.length() - 1);
      sb.append(')');
    }
    return sb.toString();
  }

}
