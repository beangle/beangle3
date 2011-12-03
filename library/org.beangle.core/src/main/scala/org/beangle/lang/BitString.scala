/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.lang;

object BitString{
}

class BitString(val value:String) {
 
  def and(other:BitString):BitString={
    val builder= new StringBuilder(value.length)
    for(i<-0 to value.length){
      builder += (if('0'==value.charAt(i) || '0'==other.value.charAt(i)) '0' else '1')
    }
    new BitString(builder.toString)
  }

}
