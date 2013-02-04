package org.beangle.commons.lang.conversion;

/**
 * 
 * @author chaostone
 * @since 3.2.0
 */
public interface Conversion {

  <T> T convert(Object source, Class<T> targetType);
}
