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

import java.io.{Reader, BufferedReader}
import scala.collection.mutable.ArrayBuffer

/**Csv Reader
 */
class CsvReader(reader: Reader, format: CsvFormat, skipLines: Int) {
  private var hasNext = true
  private var lineSkiped = false

  private val br: BufferedReader = new BufferedReader(reader)
  private val parser: CsvParser = new CsvParser(format)

  def this(reader: Reader) = this(reader, CsvFormat.Default, 0)

  def readNext(): Array[String] = {
    val resultBuffer = new ArrayBuffer[String]();
    var line = next()
    while (line != None) {
      var r = parser.parseLineMulti(line.get)
      if (r.length > 0) resultBuffer ++= r
      line = if (parser.pending) next() else None
    }
    resultBuffer.toArray
  }

  private def next(): Option[String] = {
    if (!lineSkiped) {
      for (i <- 0 until skipLines) br.readLine
      lineSkiped = true
    }
    val line = br.readLine
    hasNext = (null != line)
    if (hasNext) new Some(line) else None;
  }
}
