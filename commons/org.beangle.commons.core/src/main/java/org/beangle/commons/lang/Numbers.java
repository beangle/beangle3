/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;


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
    if (Strings.isEmpty(str)) { return false; }
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isDigit(str.charAt(i))) { return false; }
    }
    return true;
  }
}
