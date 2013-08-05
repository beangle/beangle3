/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.commons.conversion.converter;

import org.beangle.commons.conversion.Converter;

/**
 * Convert String to Enumeration.
 * 
 * @author chaostone
 * @since 3.2.0
 */
public class String2EnumConverter extends StringConverterFactory<String, Enum<?>> {

  public String2EnumConverter() {
    super();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
    Converter<String, T> converter = super.getConverter(targetType);
    if (null == converter) {
      converter = new EnumConverter(targetType);
      register(targetType, converter);
    }
    return converter;
  }

  private static class EnumConverter<T extends Enum<T>> implements Converter<String, T> {

    private final Class<T> enumType;

    public EnumConverter(Class<T> enumType) {
      this.enumType = enumType;
    }

    @Override
    public T apply(String input) {
      return (T) Enum.valueOf(enumType, input);
    }
  }
}
