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
