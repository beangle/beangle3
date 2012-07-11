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
package org.beangle.commons.lang.context.property

/**Proerty Config*/
trait PropertyConfig {
  def apply(name: String): Any

  def apply[T](clazz: Class[T], name: String): T

  def update(name: String, value: Any)

  def names: Set[String]

  def addListener(listener: PropertyConfigListener)

  def removeListener(listener: PropertyConfigListener)

  def multicast()
}

//fixme serialize

import java.util.EventObject
import collection.mutable

class PropertyConfigEvent(config: PropertyConfig) extends EventObject(config) {
  override def getSource(): PropertyConfig = config
}

/**Handle an config envent*/

import java.util.EventListener

trait PropertyConfigListener extends EventListener {
  def onConfigEvent(event: PropertyConfigEvent)
}

/**
 * Default implementation for PropertyConfig
 *
 */

import java.util.Properties

class PropertyConfigBean extends PropertyConfig {

  val properties = new mutable.HashMap[String, Any]
  val listeners = new mutable.ListBuffer[PropertyConfigListener]

  def this(p: Properties) {
    this()
    val nameEnum = p.propertyNames
    while (nameEnum.hasMoreElements) {
      val name = nameEnum.nextElement.asInstanceOf[String]
      properties += name -> p.get(name)
    }
  }

  def get(name: String): Any = properties.get(name).getOrElse(null)

  def apply(name: String): Any = properties.get(name).getOrElse(null)

  def apply[T](clazz: Class[T], name: String): T = {
    val value = get(name);
    if (null == value) {
     None.asInstanceOf[T]
    } else {
      //FIXME beanutils.convertUtils
      //      return (T) ConvertUtils.convert(value, clazz);
      value.asInstanceOf[T];
    }
  }

  def update(name: String, value: Any) {
    properties += name -> value;
  }

  def addListener(listener: PropertyConfigListener) {
    listeners += listener
  }

  def removeListener(listener: PropertyConfigListener) {
    listeners -= listener
  }

  /**
   * <p>
   * multicast.
   * </p>
   */
  def multicast() {
    val e = new PropertyConfigEvent(this);
    for (listener <- listeners) listener.onConfigEvent(e);
  }

  def names:Set[String] = properties.keySet.toSet
}
