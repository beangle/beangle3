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

import scala.collection.mutable._

object Csv {
  val Comma = ','
  val Semicolon = ';'
  val Quote = '"'
  val Escape = '\\'
}

import Csv._

class CsvFormat(val separators: Set[Char], val delimiter: Char, val escape: Char) {
  val strictQuotes: Boolean = false
  if (separators.isEmpty) separators += Comma

  def this(separators: Set[Char]) = this(separators, Quote, Escape)

  def isSeparator(ch: Char) = separators.contains(ch)

  def isDelimiter(ch: Char) = ch == delimiter

  def isEscape(ch: Char) = ch == escape

  def defaultSeparator = separators.head
}

object CsvFormat {
  val Default = new CsvFormat(Set(Comma), Quote, Escape);

  class Builder {
    private var del = Quote
    private var separators = new HashSet[Char]();
    private var esc = Escape

    def separator(sep: Char): this.type = {
      separators += sep;
      this
    }

    def escape(esc: Char): this.type = {
      this.esc = esc;
      this
    }

    def delimiter(del: Char): this.type = {
      this.del = del;
      this
    }

    def result = {
      if (separators.isEmpty) separators += Comma;
      new CsvFormat(separators.result, del, esc)
    }
  }

  def builder = new Builder();
}

class Csv(val format: CsvFormat) {
  val contents = new ListBuffer[Array[String]]()

  def this() = this(CsvFormat.builder.result)
}

