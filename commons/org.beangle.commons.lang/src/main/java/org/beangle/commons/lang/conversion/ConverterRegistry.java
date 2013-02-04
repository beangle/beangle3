package org.beangle.commons.lang.conversion;

/**
 * @author chaostone
 * @since 3.2.0
 */
public interface ConverterRegistry {

  void addConverter(Converter<?, ?> converter);

}
