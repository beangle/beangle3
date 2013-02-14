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
package org.beangle.commons.lang;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

/**
 * Wrap or Unwrap primitive
 * 
 * @author chaostone
 * @since 3.2.0
 */
public final class Primitives {
  private Primitives() {
  }

  /** Primitive types to their corresponding wrapper types. */
  private static final Map<Class<?>, Class<?>> PrimitiveToWrappers = CollectUtils.newFastMap(16);

  /** Wrapper types to their corresponding primitive types. */
  private static final Map<Class<?>, Class<?>> WrapperToPrimitives = CollectUtils.newFastMap(16);

  static {
    add(boolean.class, Boolean.class);
    add(byte.class, Byte.class);
    add(char.class, Character.class);
    add(double.class, Double.class);
    add(float.class, Float.class);
    add(int.class, Integer.class);
    add(long.class, Long.class);
    add(short.class, Short.class);
    add(void.class, Void.class);
  }

  private static void add(Class<?> key, Class<?> value) {
    PrimitiveToWrappers.put(key, value);
    WrapperToPrimitives.put(value, key);
  }

  /**
   * Returns {@code true} if {@code type} is one of the nine
   * primitive-wrapper types, such as {@link Integer}.
   * 
   * @see Class#isPrimitive
   */
  public static boolean isWrapperType(Class<?> type) {
    return WrapperToPrimitives.containsKey(type);
  }

  /**
   * Returns the corresponding wrapper type of {@code type} if it is a primitive
   * type; otherwise returns {@code type} itself. Idempotent.
   * 
   * <pre>
   *     wrap(int.class) == Integer.class
   *     wrap(Integer.class) == Integer.class
   *     wrap(String.class) == String.class
   * </pre>
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> wrap(Class<T> type) {
    return (type.isPrimitive() || type == void.class) ? (Class<T>) PrimitiveToWrappers.get(type) : type;
  }

  /**
   * Returns the corresponding primitive type of {@code type} if it is a
   * wrapper type; otherwise returns {@code type} itself. Idempotent.
   * 
   * <pre>
   *     unwrap(Integer.class) == int.class
   *     unwrap(int.class) == int.class
   *     unwrap(String.class) == String.class
   * </pre>
   */
  public static <T> Class<T> unwrap(Class<T> type) {
    @SuppressWarnings("unchecked")
    Class<T> unwrapped = (Class<T>) WrapperToPrimitives.get(type);
    return (unwrapped == null) ? type : unwrapped;
  }
}
