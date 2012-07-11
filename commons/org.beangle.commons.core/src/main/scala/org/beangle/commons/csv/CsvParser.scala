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
package org.beangle.commons.csv

import scala.collection.mutable.ListBuffer

/**A very simple CSV parser released under a commercial-friendly license.
 * This just implements splitting a single line into fields.
 */
class CsvParser(format: CsvFormat) {
  var pended: String = _
  private var inField = false
  val ignoreLeadingWhiteSpace = true
  val INITIAL_READ_SIZE = 128

  def pending = null != pended

  def parseLineMulti(line: String) = parseLine(line, true)

  def parseLine(line: String): Array[String] = parseLine(line, false)

  def parseLine(line: String, multi: Boolean): Array[String] = {
    if (!multi && null != pended) pended = null
    if (null == line) {
      if (null != pended) {
        val s = pended
        pended = null
        return Array(s)
      } else {
        return Array()
      }
    }

    val tokensOnThisLine = new ListBuffer[String]()
    var sb = new StringBuilder(INITIAL_READ_SIZE)

    var inQuotes = false
    if (null != pended) {
      sb ++= pended
      pended = null
      inQuotes = true
    }
    var i = 0;
    while (i < line.length) {
      val c = line(i)
      if (format.isEscape(c)) {
        if (isNextCharEscapable(line, inQuotes || inField, i)) {
          sb += line(i + 1)
          i = i + 1
        }
      } else if (format.isDelimiter(c)) {
        if (isNextCharEscapedQuote(line, inQuotes || inField, i)) {
          sb += line(i + 1)
          i += 1
        } else {
          inQuotes = !inQuotes
          // the tricky case of an embedded quote in the middle: a,bc"d"ef,g
          if (!format.strictQuotes) {
            if (i > 2 && !format.isSeparator(line(i - 1)) && line.length > (i + 1) && !format.isSeparator(line(i + 1))) {
              if (ignoreLeadingWhiteSpace && sb.length > 0 && isAllWhiteSpace(sb)) {
                sb = new StringBuilder(INITIAL_READ_SIZE)
              } else {
                sb += c
              }
            }
          }
        }
        inField = !inField
      } else if (format.isSeparator(c) && !inQuotes) {
        tokensOnThisLine += sb.toString
        // start work on next token
        sb = new StringBuilder(INITIAL_READ_SIZE)
        inField = false
      } else {
        if (!format.strictQuotes || inQuotes) {
          sb += c
          inField = true
        }
      }
      i = i + 1
    }
    // line is done -check status
    if (inQuotes) {
      if (multi) {
        //continuing a quoted section,re-append newline
        sb += '\n'
        pended = sb.toString
        sb = null
      } else {
        throw new RuntimeException("Un-terminated quoted field at end of CSV line")
      }
    }
    if (null != sb) {
      tokensOnThisLine += sb.toString
    }
    return tokensOnThisLine.toArray
  }

  private def isNextCharEscapedQuote(line: String, inQuote: Boolean, i: Int) = inQuote && line.length > (i + 1) && format.isDelimiter(line(i + 1))

  private def isNextCharEscapable(line: String, inQuote: Boolean, i: Int) = inQuote && line.length > (i + 1) && (format.isDelimiter(line(i + 1)) || format.isEscape(line(i + 1)))

  private def isAllWhiteSpace(line: StringBuilder): Boolean = line.forall(c => c.isWhitespace)
}
