package org.beangle.commons.lang.conversion.impl;

import org.beangle.commons.lang.tuple.Pair;

public interface GenericConverter {

  Pair<Class<?>, Class<?>> getTypeinfo();

  Object convert(Object input, Class<?> sourceType, Class<?> targetType);
}
