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
package org.beangle.commons.bean;

/**
 * Default Property Name Resolver .
 * <p>
 * This class assists in resolving property names in the following four formats, with the layout of
 * an identifying String in parentheses:
 * <ul>
 * <li><strong>Simple (<code>name</code>)</strong> - The specified <code>name</code> identifies an
 * individual property of a particular JavaBean. The name of the actual getter or setter method to
 * be used is determined using standard JavaBeans instrospection, a property named "xyz" will have a
 * getter method named <code>getXyz()</code> or (for boolean properties only) <code>isXyz()</code>,
 * and a setter method named <code>setXyz()</code>.</li>
 * <li><strong>Indexed (<code>name[index]</code>)</strong> - The underlying property value is
 * assumed to be an array. The appropriate (zero-relative) entry in the array is selected. <code>List</code>
 * objects are now also supported for read/write.</li>
 * <li><strong>Mapped (<code>name(key)</code>)</strong> - The JavaBean is assumed to have an
 * property getter and setter methods with an additional attribute of type
 * <code>java.lang.String</code>.</li>
 * <li><strong>Nested (<code>name1.name2[index].name3(key)</code>)</strong> - Combining mapped,
 * nested, and indexed references is also supported.</li>
 * </ul>
 * 
 * @author chaostone
 * @since 3.2.0
 */
public class PropertyNameResolver {

  private static final char Nested = '.';
  private static final char MappedStart = '(';
  private static final char MappedEnd = ')';
  private static final char IndexedStart = '[';
  private static final char IndexedEnd = ']';

  /**
   * Return the index value from the property expression or -1.
   * 
   * @param expression The property expression
   * @return The index value or -1 if the property is not indexed
   * @throws IllegalArgumentException If the indexed property is illegally
   *           formed or has an invalid (non-numeric) value.
   */
  public int getIndex(String expression) {
    if (expression == null || expression.length() == 0) { return -1; }
    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);
      if (c == Nested || c == MappedStart) {
        return -1;
      } else if (c == IndexedStart) {
        int end = expression.indexOf(IndexedEnd, i);
        if (end < 0) { throw new IllegalArgumentException("Missing End Delimiter"); }
        String value = expression.substring(i + 1, end);
        if (value.length() == 0) { throw new IllegalArgumentException("No Index Value"); }
        int index = 0;
        try {
          index = Integer.parseInt(value, 10);
        } catch (Exception e) {
          throw new IllegalArgumentException("Invalid index value '" + value + "'");
        }
        return index;
      }
    }
    return -1;
  }

  /**
   * Return the map key from the property expression or <code>null</code>.
   * 
   * @param expression The property expression
   * @return The index value
   * @throws IllegalArgumentException If the mapped property is illegally formed.
   */
  public String getKey(String expression) {
    if (expression == null || expression.length() == 0) { return null; }
    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);
      if (c == Nested || c == IndexedStart) {
        return null;
      } else if (c == MappedStart) {
        int end = expression.indexOf(MappedEnd, i);
        if (end < 0) { throw new IllegalArgumentException("Missing End Delimiter"); }
        return expression.substring(i + 1, end);
      }
    }
    return null;
  }

  /**
   * Return the property name from the property expression.
   * 
   * @param expression The property expression
   * @return The property name
   */
  public String getProperty(String expression) {
    if (expression == null || expression.length() == 0) { return expression; }
    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);
      if (c == Nested) {
        return expression.substring(0, i);
      } else if (c == MappedStart || c == IndexedStart) { return expression.substring(0, i); }
    }
    return expression;
  }

  /**
   * Indicates whether or not the expression contains nested property expressions or not.
   * 
   * @param expression The property expression
   * @return The next property expression
   */
  public boolean hasNested(String expression) {
    if (expression == null || expression.length() == 0) return false;
    else return remove(expression) != null;

  }

  /**
   * Indicate whether the expression is for an indexed property or not.
   * 
   * @param expression The property expression
   * @return <code>true</code> if the expresion is indexed,
   *         otherwise <code>false</code>
   */
  public boolean isIndexed(String expression) {
    if (expression == null || expression.length() == 0) { return false; }
    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);
      if (c == Nested || c == MappedStart) {
        return false;
      } else if (c == IndexedStart) { return true; }
    }
    return false;
  }

  /**
   * Indicate whether the expression is for a mapped property or not.
   * 
   * @param expression The property expression
   * @return <code>true</code> if the expresion is mapped,
   *         otherwise <code>false</code>
   */
  public boolean isMapped(String expression) {
    if (expression == null || expression.length() == 0) { return false; }
    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);
      if (c == Nested || c == IndexedStart) {
        return false;
      } else if (c == MappedStart) { return true; }
    }
    return false;
  }

  /**
   * Extract the next property expression from the current expression.
   * 
   * @param expression The property expression
   * @return The next property expression
   */
  public String next(String expression) {
    if (expression == null || expression.length() == 0) { return null; }
    boolean indexed = false;
    boolean mapped = false;
    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);
      if (indexed) {
        if (c == IndexedEnd) { return expression.substring(0, i + 1); }
      } else if (mapped) {
        if (c == MappedEnd) { return expression.substring(0, i + 1); }
      } else {
        if (c == Nested) {
          return expression.substring(0, i);
        } else if (c == MappedStart) {
          mapped = true;
        } else if (c == IndexedStart) {
          indexed = true;
        }
      }
    }
    return expression;
  }

  /**
   * Remove the last property expresson from the current expression.
   * 
   * @param expression The property expression
   * @return The new expression value, with first property
   *         expression removed - null if there are no more expressions
   */
  public String remove(String expression) {
    if (expression == null || expression.length() == 0) { return null; }
    String property = next(expression);
    if (expression.length() == property.length()) { return null; }
    int start = property.length();
    if (expression.charAt(start) == Nested) start++;
    return expression.substring(start);
  }
}
