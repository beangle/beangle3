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
    var nextLine =  next
    while(nextLine!=None){
      var r=parser.parseLineMulti(nextLine.get)
      if (r.length>0) resultBuffer ++=r
      if(parser.pending) nextLine=next
    }
    resultBuffer.toArray
  }

  private def next:Option[String]={
    if(!lineSkiped){
      for(i <- 0 to skipLines) br.readLine
      lineSkiped=true
    }
    val nextLine=br.readLine;
    hasNext=(null!=nextLine)
    if(hasNext) new Some(nextLine) else None;
  }
}
