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

import org.scalatest.Spec
import java.io.{StringReader,StringWriter,File,FileWriter}
import org.scalatest.FunSuite
import org.beangle.util.csv.CsvFormat._
import org.beangle.util.csv.CsvWriter._

class CsvWriterTest extends FunSuite{
  private def invokeWriter(args:Array[String]):String={
    val sw= new StringWriter()
    val csvw = new CsvWriter(sw,CsvFormat.builder.delimiter('\'').result)
    csvw.write(args)
    sw.toString
  }

  private def invokeNoEscapeWriter(args:Array[String]):String={
    val sw= new StringWriter()
    val csvw = new CsvWriter(sw,CsvFormat.builder.delimiter('\'').escape(CsvWriter.NoEscape).result)
    csvw.write(args)
    sw.toString
  }

  test("Write Empty String"){
    val sw= new StringWriter()
    val csvw = new CsvWriter(sw,CsvFormat.builder.escape('\'').result)
    csvw.write(null.asInstanceOf[Array[String]])
    assert (0 == sw.toString.length)
  }

  test("Write Lines") {
    // normal case
    val normal = Array("a","b","c")
    assert( invokeWriter(normal) == "'a','b','c'\n")

    // quoted camms
    val quoted= Array("a", "b,b,b", "c")
    assert(invokeWriter(quoted)=="'a','b,b,b','c'\n")

    // empty elements
    val empty=Array[String]()
    assert(invokeWriter(empty)=="\n")

    // multiline quoted
    val multiline=Array("This is a \n multiline entry", "so is \n this")
    assert(invokeWriter(multiline)=="'This is a \n multiline entry','so is \n this'\n")

    val quoteLine = Array("This is a \" multiline entry", "so is \n this")
    assert(invokeWriter(quoteLine)=="'This is a \" multiline entry','so is \n this'\n")
  }

  test("Write Line with Both Escape and Quote"){
    val quotedLine=Array("This is a 'multiline' entry", "so is \n this")
    assert(invokeWriter(quotedLine)=="'This is a \\'multiline\\' entry','so is \n this'\n")
  }

  test("Write Line without Escape"){
    val normal=Array("a","b","c")
    assert(invokeNoEscapeWriter(normal)=="'a','b','c'\n")
    
    val quoted=Array("a","b,b,b","c")
    assert(invokeNoEscapeWriter(quoted)=="'a','b,b,b','c'\n")

    val empty=Array[String]()
    assert(invokeNoEscapeWriter(empty)=="\n")
    
    val multiline=Array("This is a \n multiline entry", "so is \n this")
    assert(invokeNoEscapeWriter(multiline)=="'This is a \n multiline entry','so is \n this'\n")

  }
  test("Write Line without Escape and Quote"){
    val quoteLine = Array("This is a \" 'multiline' entry", "so is \n this")
    assert(invokeNoEscapeWriter(quoteLine)=="'This is a \" 'multiline' entry','so is \n this'\n")
  }

  test("Write Multilines"){
    val sw=new StringWriter()
    val csw= new CsvWriter(sw)
    csw.write(List("Name#Phone#Email".split("#"),"Glen#1234#glen@abcd.com".split("#"),"John#5678#john@efgh.com".split("#")))
    assert(sw.toString.split("\n").length==3)
  }

  test("Write No Quote"){
    val line=Array("Foo","Bar","Baz")
    val sw=new StringWriter()
    val csvw=new CsvWriter(sw,CsvFormat.builder.delimiter(NoQuote).result)
    csvw.write(line)
    assert(sw.toString=="Foo,Bar,Baz\n")
  }

  test("Write Null Value"){
    val line = Array("Foo",null,"Bar","baz")
    val sw = new StringWriter()
    val csvw = new CsvWriter(sw);
    csvw.write(line)
    assert("\"Foo\",,\"Bar\",\"baz\"\n"== sw.toString)
  }
  test("Stream Flushing"){
    val file="mycsv.csv"
    val csvw=new CsvWriter( new FileWriter(file))
    csvw.write(Array("aaaa", "bbbb", "cccc", "dddd" ))
    csvw.close()
  }
  test("Alternate Escape"){
    val line=Array("Foo", "bar's")
    val sw= new StringWriter()
    val csvw= new CsvWriter(sw)
    csvw.write(line)
    assert(sw.toString=="\"Foo\",\"bar's\"\n")
  }

  test("No Quote and No Escape"){
    val line=Array("\"Foo\",\"Bar\"")
    val sw= new StringWriter()
    val csvw= new CsvWriter(sw,CsvFormat.builder.delimiter(NoQuote).escape(NoEscape).result)
    csvw.write(line)
    assert("\"Foo\",\"Bar\"\n" == sw.toString)
  }

  test("Nested Quote"){
    val data = Array("\"\"", "test")
    val result= "\"\"\"\",\"test\""
    
    val tempFile = File.createTempFile("csvWriterTest", ".csv")
    tempFile.deleteOnExit()
    val writer = new CsvWriter(new FileWriter(tempFile))
    writer.write(data)
    writer.close()
    assert(result==scala.io.Source.fromFile(tempFile.getPath).getLine(0))
  }
}
