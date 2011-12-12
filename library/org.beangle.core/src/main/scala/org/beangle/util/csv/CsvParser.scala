/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.util.csv
import scala.collection.mutable.ListBuffer

class CsvParser(format:CsvFormat){
  var pended:String=_
  private var inField=false
  val ignoreLeadingWhiteSpace=true
  val INITIAL_READ_SIZE=128

  def pending = null!=pended

  def parseLineMulti(nextLine:String) = parseLine(nextLine,true)

  //def parseLine(nextLine:String) = parseLine(nextLine,false)

  def parseLine(nextLine:String,multi:Boolean):Array[String]={
    if(!multi && null!=pended) pended=null
    if(null==nextLine){
      if(null!=pended){
	val s=pended
	pended=null
	return Array(s)
      }else{
	return Array()
      }
    }

    val tokensOnThisLine = new ListBuffer[String]()
    var sb=new StringBuilder(INITIAL_READ_SIZE)
    
    var inQuotes = false
    if(null!=pended){
      sb ++= pended
      pended=null
      inQuotes=true
    }
    var i=0;
//    for(i <- 0 to nextLine.length){
//      val c=nextLine(i)
//      if(format.isEscape(c)){
//	if(isNextCharEscapable(nextLine,inQuotes || inField,i)){
//	  sb += nextLine(i+1)
//	  i=i+1
//	}
//      }else if(format.isDelimiter(c)){
//	if(isNextCharEscapeQuote(nextLine,inQuotes || inField,i)){
//	  sb += nextLine(i+1)
//	  i+=1
//	}else{
//	  inQuotes = !inQuotes
//	  // the tricky case of an embedded quote in the middle: a,bc"d"ef,g
//	  if(!format.isStrictQuotes){
//	    if(i>2 && !format.isSeparator(nextLine(i-1)) && nextLine.length > (i+1) && !format.isSeparator(nextLine(i+1)){
//	      if(ignoreLeadingWhiteSpace && sb.length>0 && isAllWhiteSpace(sb)){
//		sb=new StringBuilder(INITIAL_READ_SIZE)
//	      }else{
//		sb +=c
//	      }
//	    }
//	  }
//	}
//	inField =!inField
//      }else if (format.isSeparator(c) && !inQuotes){
//	tokensOnThisLine += sb.toString
//	// start work on next token
//	sb=new StringBuilder(INITIAL_READ_SIZE)
//	inField = false
//      }else{
//	if(!format.isStrictQuotes || inQuotes){
//	  sb+=c
//	  inField=true
//	}
//      }
//    }
    // line is done -check status
    if(inQuotes){
      if(multi){
	//continuing a quoted section,re-append newline
	sb += '\n'
	pended=sb.toString
	sb=null
      }else{
	throw new RuntimeException("Un-terminated quoted field at end of CSV line")
      }
    }
    if(null!=sb){
      tokensOnThisLine += sb.toString
    }
    return tokensOnThisLine.toArray
  }

}
