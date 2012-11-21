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
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.bean.converters;

import org.apache.commons.beanutils.Converter;

/**
 * @author chaostone
 * @since 3.0.0
 */
public class EnumConverter implements Converter {

  public static EnumConverter Instance= new EnumConverter();
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Object convert(Class type, Object value) {
    if (value == null) {
      return null;
    } else if (Enum.class.isAssignableFrom(type)) {
      return Enum.valueOf(type, value.toString());
    } else if (type == String.class) { return value.toString(); }
    return null;
  }

}
