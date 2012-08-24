/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
