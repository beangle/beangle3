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

 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.lang

class RichString(s: String) {

  def substringBetween(open: String, close: String): String = {
    if (s == null || open == null || close == null) {
      return null;
    }
    val start = s.indexOf(open);
    if (start != -1) {
      val end = s.indexOf(close, start + open.length());
      if (end != -1) {
        return s.substring(start + open.length(), end);
      }
    }
    return null;
  }

  def substringAfter(separator: String): String = {
    if (isEmpty)
      return s
    if (separator == null)
      return ""
    val pos = s.indexOf(separator);
    if (pos == -1)
      ""
    else s.substring(pos + separator.length());
  }

  def isEmpty: Boolean = (null == s || s.length == 0)

  def isDigits: Boolean = {
    if (isEmpty) false
    else !(s.exists(e => !e.isDigit))
  }
}
