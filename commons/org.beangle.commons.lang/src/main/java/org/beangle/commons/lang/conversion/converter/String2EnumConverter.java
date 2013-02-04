package org.beangle.commons.lang.conversion.converter;

import org.beangle.commons.lang.conversion.Converter;
import org.beangle.commons.lang.conversion.impl.ConverterFactory;

public class String2EnumConverter extends ConverterFactory<String, Enum<?>> {

  public String2EnumConverter() {
    super();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
    Converter<String, T> converter = getConverter(targetType);
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
