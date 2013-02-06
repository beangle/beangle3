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

import java.util.LinkedList;
import java.util.List;

public final class Objects {

  /**
   * <p>
   * Compares two objects for equality, where either one or both objects may be {@code null}.
   * </p>
   * 
   * <pre>
   * equals(null, null)                  = true
   * equals(null, "")                    = false
   * equals("", null)                    = false
   * equals("", "")                      = true
   * equals(Boolean.TRUE, null)          = false
   * equals(Boolean.TRUE, "true")        = false
   * equals(Boolean.TRUE, Boolean.TRUE)  = true
   * equals(Boolean.TRUE, Boolean.FALSE) = false
   * </pre>
   * 
   * @param a the first object, may be {@code null}
   * @param b the second object, may be {@code null}
   * @return {@code true} if the values of both objects are the same
   * @since 3.0
   */
  public static boolean equals(Object a, Object b) {
    return (a == b) || (a != null && a.equals(b));
  }

  /**
   * <p>
   * Compares two object array for equality, where either one or both objects may be {@code null}.
   * </p>
   */
  public static boolean equals(Object[] a, Object b[]) {
    if (a == b) return true;
    if (null == a || null == b) return false;

    if (a.length != b.length) return false;
    for (int i = 0; i < a.length; ++i) {
      if (!Objects.equals(a[i], b[i])) return false;
    }
    return true;
  }

  /**
   * <p>
   * Gets the {@code toString} of an {@code Object} returning an empty string ("") if {@code null}
   * input.
   * </p>
   * 
   * <pre>
   * toString(null)         = ""
   * toString("")           = ""
   * toString("bat")        = "bat"
   * toString(Boolean.TRUE) = "true"
   * </pre>
   * 
   * @see String#valueOf(Object)
   * @param obj the Object to {@code toString}, may be null
   * @return the passed in Object's toString, or nullStr if {@code null} input
   * @since 3.0
   */
  public static String toString(Object obj) {
    return obj == null ? "" : obj.toString();
  }

  // -----------------------------------------------------------------------
  /**
   * <p>
   * Returns a default value if the object passed is {@code null}.
   * </p>
   * 
   * <pre>
   * defaultIfNull(null, null)      = null
   * defaultIfNull(null, "")        = ""
   * defaultIfNull(null, "zz")      = "zz"
   * defaultIfNull("abc", *)        = "abc"
   * defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
   * </pre>
   * 
   * @param <T> the type of the object
   * @param object the {@code Object} to test, may be {@code null}
   * @param defaultValue the default value to return, may be {@code null}
   * @return {@code object} if it is not {@code null}, defaultValue otherwise
   * @since 3.0
   */
  public static <T> T defaultIfNull(T object, T defaultValue) {
    return object != null ? object : defaultValue;
  }

  /**
   * Return a hex String form of an object's identity hash code.
   * 
   * @param obj the object
   * @return the object's identity code in hex notation
   */
  public static String getIdentityHexString(Object obj) {
    return Integer.toHexString(System.identityHashCode(obj));
  }

  public static EqualsBuilder equalsBuilder() {
    return new EqualsBuilder();
  }

  /**
   * Creates an instance of {@link ToStringBuilder}.
   * <p>
   * This is helpful for implementing {@link Object#toString()}. Specification by example:
   * 
   * <pre>
   * {@code
   *   // Returns "ClassName{}"
   *   Objects.toStringBuilder(this)
   *       .toString();
   * 
   *   // Returns "ClassName{x=1}"
   *   Objects.toStringBuilder(this)
   *       .add("x", 1)
   *       .toString();
   * 
   *   // Returns "MyObject{x=1}"
   *   Objects.toStringBuilder("MyObject")
   *       .add("x", 1)
   *       .toString();
   * 
   *   // Returns "ClassName{x=1, y=foo}"
   *   Objects.toStringBuilder(this)
   *       .add("x", 1)
   *       .add("y", "foo")
   *       .toString();
   *   }}
   * 
   *   // Returns "ClassName{x=1}"
   *   Objects.toStringBuilder(this)
   *       .omitNullValues()
   *       .add("x", 1)
   *       .add("y", null)
   *       .toString();
   *   }}
   * </pre>
   * 
   * @param self the object to generate the string for (typically {@code this}),
   *          used only for its class name
   * @since 3.1
   */
  public static ToStringBuilder toStringBuilder(Object self) {
    return new ToStringBuilder(simpleName(self.getClass()));
  }

  /**
   * Creates an instance of {@link ToStringBuilder} in the same manner as
   * {@link Objects#toStringBuilder(Object)}, but using the name of {@code clazz} instead of using
   * an
   * instance's {@link Object#getClass()}.
   * <p>
   * 
   * @param clazz the {@link Class} of the instance
   */
  public static ToStringBuilder toStringBuilder(Class<?> clazz) {
    return new ToStringBuilder(simpleName(clazz));
  }

  /**
   * Creates an instance of {@link ToStringBuilder} in the same manner as
   * {@link Objects#toStringBuilder(Object)}, but using {@code className} instead
   * of using an instance's {@link Object#getClass()}.
   * 
   * @param className the name of the instance type
   */
  public static ToStringBuilder toStringBuilder(String className) {
    return new ToStringBuilder(className);
  }

  /**
   * More readable than {@link Class#getSimpleName()}
   */
  private static String simpleName(Class<?> clazz) {
    String name = clazz.getName();

    // the nth anonymous class has a class name ending in "Outer$n"
    // and local inner classes have names ending in "Outer.$1Inner"
    name = name.replaceAll("\\$[0-9]+", "\\$");

    // we want the name of the inner class all by its lonesome
    int start = name.lastIndexOf('$');

    // if this isn't an inner class, just find the start of the
    // top level class name.
    if (start == -1) start = name.lastIndexOf('.');
    return name.substring(start + 1);
  }

  /**
   * Support class for {@link Objects#toStringBuilder}.
   */
  public static final class ToStringBuilder {
    private final String className;
    private final List<ValueHolder> valueHolders = new LinkedList<ValueHolder>();
    private boolean omitNull = false;

    /**
     * Use {@link Objects#toStringBuilder(Object)} to create an instance.
     */
    private ToStringBuilder(String className) {
      this.className = className;
    }

    /**
     * When called, the formatted output returned by {@link #toString()} will
     * ignore {@code null} values.
     */
    public ToStringBuilder omitNull() {
      omitNull = true;
      return this;
    }

    /**
     * Adds a name/value pair to the formatted output in {@code name=value} format. If {@code value}
     * is {@code null}, the string {@code "null"} is used, unless {@link #omitNull()} is
     * called, in which case this
     * name/value pair will not be added.
     */
    public ToStringBuilder add(String name, Object value) {
      addHolder(value).builder.append(name).append('=').append(value);
      return this;
    }

    /**
     * Returns a string in the format specified by {@link Objects#toStringBuilder(Object)}.
     */
    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder(32).append(className).append('{');
      boolean needsSeparator = false;
      for (ValueHolder valueHolder : valueHolders) {
        if (!omitNull || !valueHolder.isNull) {
          if (needsSeparator) builder.append(", ");
          else needsSeparator = true;
          builder.append(valueHolder.builder);
        }
      }
      return builder.append('}').toString();
    }

    private ValueHolder addHolder() {
      ValueHolder valueHolder = new ValueHolder();
      valueHolders.add(valueHolder);
      return valueHolder;
    }

    private ValueHolder addHolder(Object value) {
      ValueHolder valueHolder = addHolder();
      valueHolder.isNull = (value == null);
      return valueHolder;
    }

    private static final class ValueHolder {
      final StringBuilder builder = new StringBuilder();
      boolean isNull;
    }
  }

  /**
   * Equals Builder
   * 
   * @author chaostone
   * @since 3.1.0
   */
  public static final class EqualsBuilder {
    private boolean equals = true;

    public EqualsBuilder add(Object lhs, Object rhs) {
      if (!equals) return this;
      equals &= Objects.equals(lhs, rhs);
      return this;
    }

    public EqualsBuilder add(int lhs, int rhs) {
      if (!equals) return this;
      equals &= (lhs == rhs);
      return this;
    }

    public EqualsBuilder add(long lhs, long rhs) {
      if (!equals) return this;
      equals &= (lhs == rhs);
      return this;
    }

    public EqualsBuilder add(short lhs, short rhs) {
      if (!equals) return this;
      equals &= (lhs == rhs);
      return this;
    }

    public EqualsBuilder add(boolean lhs, boolean rhs) {
      if (!equals) return this;
      equals &= (lhs == rhs);
      return this;
    }

    public boolean isEquals() {
      return equals;
    }

  }
}
