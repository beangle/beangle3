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
