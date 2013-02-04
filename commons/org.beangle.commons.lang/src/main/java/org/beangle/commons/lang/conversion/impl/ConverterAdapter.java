package org.beangle.commons.lang.conversion.impl;

import org.beangle.commons.lang.conversion.Converter;
import org.beangle.commons.lang.tuple.Pair;

public class ConverterAdapter implements GenericConverter {

  final Converter<Object, Object> converter;

  final Pair<Class<?>, Class<?>> typeinfo;
  @SuppressWarnings("unchecked")
  public ConverterAdapter(Converter<?, ?> converter,Pair<Class<?>, Class<?>> typeinfo) {
    super();
    this.converter = (Converter<Object, Object>) converter;
    this.typeinfo=typeinfo;
  }

  @Override
  public Object convert(Object input, Class<?> sourceType, Class<?> targetType) {
    return converter.apply(input);
  }

  @Override
  public Pair<Class<?>, Class<?>> getTypeinfo() {
    return typeinfo;
  }
  

}
