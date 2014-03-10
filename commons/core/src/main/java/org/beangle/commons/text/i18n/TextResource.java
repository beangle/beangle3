/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.text.i18n;

import java.util.Locale;

/**
 * <p>
 * TextResource interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface TextResource {

  /**
   * Gets a message based on a message key, or null if no message is found.
   * 
   * @param key
   *          the resource bundle key that is to be searched for
   * @return the message as found in the resource bundle, or null if none is
   *         found.
   */
  String getText(String key);

  /**
   * Gets a message based on a key using the supplied obj, as defined in
   * {@link java.text.MessageFormat}, or, if the message is not found, a
   * supplied default value is returned.
   * 
   * @param key
   *          the resource bundle key that is to be searched for
   * @param defaultValue
   *          the default value which will be returned if no message is
   *          found
   * @param obj
   *          obj to be used in a {@link java.text.MessageFormat} message
   * @return the message as found in the resource bundle, or defaultValue if
   *         none is found
   */
  String getText(String key, String defaultValue, Object... obj);

  /**
   * @return locale
   */
  Locale getLocale();

}
