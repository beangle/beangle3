/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

public final class Chars {

  /**
   * <p>
   * Checks whether the character is ASCII 7 bit alphabetic.
   * </p>
   * 
   * <pre>
   *   isAsciiAlpha('a')  = true
   *   isAsciiAlpha('A')  = true
   *   isAsciiAlpha('3')  = false
   *   isAsciiAlpha('-')  = false
   *   isAsciiAlpha('\n') = false
   *   isAsciiAlpha('&copy;') = false
   * </pre>
   * 
   * @param ch the character to check
   * @return true if between 65 and 90 or 97 and 122 inclusive
   * @since 3.0
   */
  public static boolean isAsciiAlpha(char ch) {
    return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
  }
}
