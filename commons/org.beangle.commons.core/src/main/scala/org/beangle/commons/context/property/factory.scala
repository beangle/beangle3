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

import java.util.Properties
import org.beangle.commons.lang.context.ConfigLocations

trait PropertyConfigProvider {
  def config: Properties;
}

trait PropertyConfigFactory {
  def config: PropertyConfig

  def reload(): Unit

  def addProvider(provider: PropertyConfigProvider)
}

import java.util.Properties
import java.net.URL
import org.slf4j.LoggerFactory

class UrlPropertyConfigProvider extends PropertyConfigProvider {
  var locations: ConfigLocations = _
  val logger = LoggerFactory.getLogger(classOf[UrlPropertyConfigProvider])

  def config(): Properties = {
    try {
      val properties = new Properties
      if (null != locations.global) populateItems(properties, locations.global)
      if (null != locations.locals) {
        locations.locals.foreach(url => populateItems(properties, url))
      }
      if (null != locations.user) populateItems(properties, locations.user)
      properties
    } catch {
      case e: Exception => throw new RuntimeException(e)
    }
  }

  def populateItems(properties: Properties, url: URL) {
    logger.debug("loading {}", url)
    try {
      val is = url.openStream()
      properties.load(is)
      is.close()
    } catch {
      case e: Exception => logger.error("populate config items error", e)
    }
  }
}