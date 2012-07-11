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
package org.beangle.commons.lang.context

import java.net.URL
import reflect.BeanProperty
import collection.mutable.ListBuffer

/**ConfigLocations
 * User: chaostone
 * Date: 7/3/12 11:36 PM
 */

class ConfigLocations {
  @BeanProperty
  var global: URL = _
  @BeanProperty
  var locals: List[URL] = List()
  @BeanProperty
  var user: URL = _

  def all: List[URL] = {
    val all = new ListBuffer[URL]()
    if (null != global) all += global
    if (null != locals) all ++= locals
    if (null != user) all += user
    return all.result;
  }

  def isEmpty: Boolean = (null == global && null == user && (null == locals || locals.isEmpty))

}