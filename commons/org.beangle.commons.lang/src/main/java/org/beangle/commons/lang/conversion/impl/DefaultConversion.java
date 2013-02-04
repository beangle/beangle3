package org.beangle.commons.lang.conversion.impl;

import org.beangle.commons.lang.conversion.converter.*;

/**
 * @author chaostone
 * @since 3.2.0
 */
public class DefaultConversion extends AbstractGenericConversion {

  static final public DefaultConversion Instance = new DefaultConversion();

  public DefaultConversion() {
    super();
    addConverter(new String2BooleanConverter());
    addConverter(new String2NumberConverter());
    addConverter(new String2DateConverter());
    addConverter(new String2EnumConverter());
    addConverter(new String2LocaleConverter());
    addConverter(new Number2NumberConverter());
    addConverter(new Object2StringConverter());
  }

  // static {
  // Instance.register(new SqlDateConverter(), java.sql.Date.class);
  // Instance.register(new DateConverter(), java.util.Date.class);
  // Instance.register(new BooleanConverter(null), Boolean.class);
  // Instance.register(new IntegerConverter(null), Integer.class);
  // Instance.register(new LongConverter(null), Long.class);
  // Instance.register(new FloatConverter(null), Float.class);
  // Instance.register(new DoubleConverter(null), Double.class);
  // }
  //
  // public static void registerEnum(Class<? extends Enum<?>> clazz) {
  // Instance.register(EnumConverter.Instance, clazz);
  // }

}
