/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.lang;

import java.math.BigDecimal;

public final class Numbers {

  /**
   * <p>
   * Convert a <code>String</code> to an <code>int</code>, returning <code>zero</code> if the
   * conversion fails.
   * </p>
   * <p>
   * If the string is <code>null</code>, <code>zero</code> is returned.
   * </p>
   * 
   * <pre>
   * toInt(null) = 0
   * toInt("")   = 0
   * toInt("1")  = 1
   * </pre>
   * 
   * @param str the string to convert, may be null
   * @return the int represented by the string, or <code>zero</code> if
   *         conversion fails
   * @since 3.0
   */
  public static int toInt(String str) {
    return toInt(str, 0);
  }

  /**
   * <p>
   * Convert a <code>String</code> to an <code>int</code>, returning a default value if the
   * conversion fails.
   * </p>
   * <p>
   * If the string is <code>null</code>, the default value is returned.
   * </p>
   * 
   * <pre>
   * toInt(null, 1) = 1
   * toInt("", 1)   = 1
   * toInt("1", 0)  = 1
   * </pre>
   * 
   * @param str the string to convert, may be null
   * @param defaultValue the default value
   * @return the int represented by the string, or the default if conversion fails
   * @since 3.0
   */
  public static int toInt(String str, int defaultValue) {
    if (str == null) { return defaultValue; }
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException nfe) {
      return defaultValue;
    }
  }

  public static float toFloat(String str) {
    return toFloat(str, 0.0f);
  }

  public static float toFloat(String str, float defaultValue) {
    if (str == null) return defaultValue;
    try {
      return Float.parseFloat(str);
    } catch (NumberFormatException nfe) {
      return defaultValue;
    }
  }

  /**
   * <p>
   * Checks whether the <code>String</code> contains only digit characters.
   * </p>
   * <p>
   * <code>Null</code> and empty String will return <code>false</code>.
   * </p>
   * 
   * @param str the <code>String</code> to check
   * @return <code>true</code> if str contains only Unicode numeric
   */
  public static boolean isDigits(String str) {
    if (Strings.isEmpty(str)) return false;
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isDigit(str.charAt(i))) return false;
    }
    return true;
  }

  public static boolean isNumber(String str) {
    if (Strings.isEmpty(str)) return false;
    try {
      new BigDecimal(str);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}
