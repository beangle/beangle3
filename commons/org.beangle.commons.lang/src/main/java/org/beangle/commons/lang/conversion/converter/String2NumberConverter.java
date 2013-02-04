package org.beangle.commons.lang.conversion.converter;

import org.beangle.commons.lang.conversion.Converter;
import org.beangle.commons.lang.conversion.impl.ConverterFactory;


/**
 * Convert string to number.
 * 
 * @author chaostone
 */
public class String2NumberConverter extends ConverterFactory<String, Number> {

  @Override
  public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
    return null;
  }

}
