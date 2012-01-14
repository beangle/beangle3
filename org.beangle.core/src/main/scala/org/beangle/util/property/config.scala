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
package org.beangle.util.property

/** Proerty Config*/ 
trait PropertyConfig{
  def apply(name:String):Any
  def apply[T](clazz:Class[T],name:String):T
  def update(name:String,value:Any)
  def names:Set[String]
  def addListener(listener:PropertyConfigListener)
  def removeListener(listener:PropertyConfigListener)
  def multicast()
}

//fixme serialize
import java.util.EventObject
class PropertyConfigEvent(config:PropertyConfig) extends EventObject(config){
  override def getSource():PropertyConfig=config
}

/**Handle an config envent*/
import java.util.EventListener
trait PropertyConfigListener extends EventListener{
  def onConfigEvent(event:PropertyConfigEvent)
}
