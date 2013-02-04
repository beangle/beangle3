package org.beangle.commons.lang.conversion.impl;

import org.beangle.commons.lang.tuple.Pair;

public class NoneConverter implements GenericConverter {

  public final static NoneConverter Instance = new NoneConverter();

  @Override
  public Object convert(Object input, Class<?> sourceType, Class<?> targetType) {
    return null;
  }

  @Override
  public Pair<Class<?>, Class<?>> getTypeinfo() {
    return Pair.<Class<?>, Class<?>> of(Object.class, Object.class);
  }

}
