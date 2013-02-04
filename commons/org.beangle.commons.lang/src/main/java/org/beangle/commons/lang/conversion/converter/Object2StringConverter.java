package org.beangle.commons.lang.conversion.converter;

import org.beangle.commons.lang.conversion.Converter;

/**
 * @author chaostone
 */
public class Object2StringConverter implements Converter<Object, String> {

  @Override
  public String apply(Object input) {
    return (input == null) ? null : input.toString();
  }

}
