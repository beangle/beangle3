package org.beangle.util.csv
import scala.collection.mutable._

object Csv{
  val Comma=','
  val Semicolon=';'
  val Quote='"'
  val Escape='\\'
}


import Csv._
class CsvFormat(separators:Set[Char],delimiter:Char,escape:Char){
  val strictQuotes:Boolean=false

  def this(separators:Set[Char])=this(separators,Quote,Escape)
  def isSeparator(ch:Char) = separators.contains(ch)
  def isDelimiter(ch:Char) = ch==delimiter
  def isEscape(ch:Char)= ch==escape
}

private[csv] class FormatBuilder{
  private var del=Quote
  private var separators=new HashSet[Char]();
  private var esc = Escape
    
  def separator(sep: Char):this.type= {separators+=sep;this}
  def escape(esc:Char):this.type={this.esc=esc;this}
  def delimiter(del:Char):this.type={this.del=del;this}

  def result={if(separators.isEmpty) separators+=Comma;new CsvFormat(separators.result,del,esc)}
}

object CsvFormat{
  val Default=new CsvFormat(Set(Comma),Quote,Escape);
  def builder=new FormatBuilder();
}

class Csv(val format:CsvFormat) {
  
  val contents= new ListBuffer[Array[String]]()

  def this()=this(CsvFormat.builder.result)
  
}

