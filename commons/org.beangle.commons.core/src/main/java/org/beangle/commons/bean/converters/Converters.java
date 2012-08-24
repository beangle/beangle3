/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.bean.converters;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;

/**
 * <p>
 * Converter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public final class Converters {

  static final public ConvertUtilsBean Instance = new ConvertUtilsBean();
  static {
    Instance.register(new SqlDateConverter(), java.sql.Date.class);
    Instance.register(new DateConverter(), java.util.Date.class);
    Instance.register(new BooleanConverter(null), Boolean.class);
    Instance.register(new IntegerConverter(null), Integer.class);
    Instance.register(new LongConverter(null), Long.class);
    Instance.register(new FloatConverter(null), Float.class);
    Instance.register(new DoubleConverter(null), Double.class);
  }

  public static void registerEnum(Class<? extends Enum<?>> clazz) {
    Instance.register(EnumConverter.Instance, clazz);
  }
}
