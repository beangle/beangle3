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
package org.beangle.commons.i18n

import java.text.MessageFormat

abstract class AbstractTextResource extends TextResource {

  def get(key: String, defaultValue: String, args: Any*): String = {
    val text = getText(key).getOrElse(defaultValue);
    new MessageFormat(text, locale).format(args)
  }

  protected def getText(key: String): Option[String];
}

import java.util.Locale

class NullTextResource extends AbstractTextResource {
  override def locale = Locale.getDefault

  override def getText(key: String) = Some(key)
}

import java.util.ResourceBundle

class BundleTextResource(val locale: Locale, val bundle: ResourceBundle) extends AbstractTextResource {
  override def getText(key: String): Option[String] = {
    val text = bundle.getString(key)
    if (null == text) None else Some(text)
  }
}
