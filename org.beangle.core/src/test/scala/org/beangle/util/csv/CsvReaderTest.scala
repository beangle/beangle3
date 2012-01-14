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
 * but WITHOUT ANY WARRANTY without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.util.csv

import java.io.StringReader
import org.scalatest.FunSuite

class CsvReaderTest extends FunSuite {

  val sb = new StringBuilder()
  sb ++="a,b,c" += '\n' // standard case
  sb ++="a,\"b,b,b\",c" += '\n' // quoted elements
  sb ++= ",,"  += '\n' // empty elements
  sb ++= "a,\"PO Box 123,\nKippax,ACT. 2615.\nAustralia\",d.\n"
  // Test quoted quote chars
  sb ++= "\"Glen \"\"The Man\"\" Smith\",Athlete,Developer\n"
  // """""","test" representing: "",test
  sb ++= "\"\"\"\"\"\",\"test\"\n"
  sb ++= "\"a\nb\",b,\"\nd\",e\n"
  val reader = new CsvReader(new StringReader(sb.toString))
  // test normal case
  test("Test reader sample") {
    var nextLine = reader.readNext()
    assert("a" == nextLine(0))
    assert("b" == nextLine(1))
    assert("c" == nextLine(2))

    // test quoted commas
    nextLine = reader.readNext()
    assert("a" == nextLine(0))
    assert("b,b,b" == nextLine(1))
    assert("c" == nextLine(2))

    // test empty elements
    nextLine = reader.readNext()
    assert(3 == nextLine.length)

    // test multiline quoted
    nextLine = reader.readNext()
    assert(3 == nextLine.length)

    // test quoted quote chars
    nextLine = reader.readNext()
    assert("Glen \"The Man\" Smith" == nextLine(0))

    nextLine = reader.readNext()
    assert("\"\"" == nextLine(0)) // check the tricky situation
    assert("test" == nextLine(1)) // make sure we didn't ruin the next
    // field..

    nextLine = reader.readNext()
    assert(4 == nextLine.length)

    // test end of stream
    assert(reader.readNext().size==0)
  }
}
