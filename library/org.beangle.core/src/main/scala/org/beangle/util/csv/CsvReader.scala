/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.util.csv
import java.io.{Reader,BufferedReader}
import scala.collection.mutable.ArrayBuffer

/**
 * Csv Reader
 */
class CsvReader(reader:Reader,format:CsvFormat,skipLines:Int){
  private var hasNext=true
  private var lineSkiped=false

  private val br:BufferedReader=new BufferedReader(reader)
  private val parser:CsvParser=new CsvParser(format)

  def readNext:Array[String]={
    val resultBuffer=new ArrayBuffer[String]();
    var line =  next
    while(line!=None){
      var r=parser.parseLineMulti(line.get)
      if (r.length>0) resultBuffer ++= r
      if(parser.pending) line=next
    }
    resultBuffer.toArray
  }

  private def next:Option[String]={
    if(!lineSkiped){
      for(i <- 0 to skipLines) br.readLine
      lineSkiped=true
    }
    val line=br.readLine;
    hasNext = (null!=line)
    if(hasNext) new Some(line) else None;
  }
}
