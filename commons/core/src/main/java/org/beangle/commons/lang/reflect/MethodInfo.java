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
package org.beangle.commons.lang.reflect;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;
import static org.beangle.commons.lang.Strings.substringAfter;
import static org.beangle.commons.lang.tuple.Pair.of;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.tuple.Pair;

/**
 * Method name and return type and parameters type
 * 
 * @author chaostone
 * @since 3.2.0
 */
public final class MethodInfo implements Comparable<MethodInfo> {

  public final int index;
  public final Method method;
  public final Class<?>[] parameterTypes;

  public MethodInfo(int index, Method method, Class<?>[] parameterTypes) {
    super();
    this.index = index;
    this.method = method;
    this.parameterTypes = parameterTypes;
  }

  /**
   * Return thid method is property read method (0) or write method(1) or none(-1).
   */
  public Pair<Boolean, String> property() {
    String name = method.getName();
    if (Modifier.isStatic(method.getModifiers())) return null;
    if (0 == parameterTypes.length) {
      if (name.startsWith("get")) {
        if (name.length() > 3) return of(TRUE, uncapitalize(substringAfter(name, "get")));
      } else if (name.startsWith("is")) {
        if (name.length() > 2) return of(TRUE, uncapitalize(substringAfter(name, "is")));
      }
    } else if (1 == parameterTypes.length) {
      if (name.startsWith("set") && name.length() > 3) return of(FALSE,
          uncapitalize(substringAfter(name, "set")));
    }
    return null;
  }

  /**
   * Change Ux. to ux format
   * <ul>
   * Do not change such formats
   * <li>URL</li>
   * <li>WWw</li>
   * </ul>
   * 
   * @param name
   * @return
   */
  private final String uncapitalize(String name) {
    if (name.length() > 1 && isUpperCase(name.charAt(1)) && isUpperCase(name.charAt(0))) return name;
    char chars[] = name.toCharArray();
    chars[0] = toLowerCase(chars[0]);
    return new String(chars);
  }

  @Override
  public int compareTo(MethodInfo o) {
    return this.index - o.index;
  }

  public boolean matches(Object[] args) {
    if (parameterTypes.length != args.length) return false;
    for (int i = 0; i < args.length; i++) {
      if (null != args[i] && !parameterTypes[i].isInstance(args[i])) return false;
    }
    return true;
  }

  @Override
  public String toString() {
    final Class<?> returnType = method.getReturnType();
    StringBuilder sb = new StringBuilder();
    sb.append((null == returnType) ? "void" : returnType.getSimpleName());
    sb.append(' ').append(method.getName());
    if (parameterTypes.length == 0) {
      sb.append("()");
    } else {
      sb.append('(');
      for (Class<?> type : parameterTypes) {
        sb.append(type.getSimpleName()).append(",");
      }
      sb.deleteCharAt(sb.length() - 1);
      sb.append(')');
    }
    return sb.toString();
  }

  @Override
  public int hashCode() {
    int hash = 0;
    for (Class<?> t : parameterTypes)
      hash += t.hashCode();
    return method.getName().hashCode() + hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MethodInfo) {
      MethodInfo other = (MethodInfo) obj;
      return Objects.equalsBuilder().add(method.getName(), other.method.getName())
          .add(parameterTypes, other.parameterTypes).isEquals();
    } else {
      return false;
    }
  }

}
