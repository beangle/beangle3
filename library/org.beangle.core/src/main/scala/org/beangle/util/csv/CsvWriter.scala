/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.util.csv


object CsvWrite{
  val NoQuote='\u0000'
  val NoEscape='\u0000'
}

import CsvWrite._
import java.io.PrintWriter
import java.io.Writer

class CsvWriter(writer:Writer,format:CsvFormat){
  val INITIAL_STRING_SIZE = 128
  var pw=new PrintWriter(writer)
  
  def this(writer:Writer)=this(writer,CsvFormat.Default)

  def write(lines:List[Array[String]]){
    lines.foreach(line=>write(line))
  }
  def write(line:Array[String]){
    var sb=new StringBuilder( INITIAL_STRING_SIZE)
    for(i <- 0 until line.length){
      if(i != 0) sb += format.defaultSeparator
      val nextEle = line(i)
      if(null!=nextEle){
	if(!format.isDelimiter(NoQuote)) sb += format.delimiter
	sb ++= process(nextEle)
	if(!format.isDelimiter(NoQuote)) sb += format.delimiter
      }
      sb += '\n'
      pw.write(sb.toString)
    }
  }

  def process(str:String):String={
    if(str.indexOf(format.delimiter) == -1){
      val sb = new StringBuilder(INITIAL_STRING_SIZE)
      for(nextChar <- str.elements){
	if(format.escape != NoEscape && (nextChar == format.delimiter ||nextChar == format.escape)){
	  sb += format.escape
	}
	sb += nextChar
      }
      sb.toString
    }else{
      str
    }
  }
  
  def flush (){ pw.flush()}
  def close(){flush();pw.close();}
  def checkError:Boolean=pw.checkError
}
