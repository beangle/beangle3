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

package org.beangle.util.i18n

import java.util.Locale

trait TextResource{

  def locale:Locale;

  /** Gets a message based on a key, or supplied value if not found. 
   * @param key the resource bundle key
   * @param defaultValue the default value if no message is found
   * @param args a list args to be used in a {@link java.text.MessageFormat} messag
   * @return the message found or defaultValue
   */
  def get(key:String,defaultValue:String,args:List[Any]):String = get(key,defaultValue,args:_*)

  /** Gets a message based on a key, or supplied value if not found. 
   * @param key the resource bundle key
   * @param defaultValue the default value if no message is found
   * @param args array args to be used in a {@link java.text.MessageFormat} messag
   * @return the message found or defaultValue
   */
  def get(key:String,defaultValue:String,args:Any*):String;

}

class Message(val key:String,val  args:List[Any]){
  def this(key:String,args:Any*) =this(key,args.toList)
}
