package org.beangle.commons.lang.conversion.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.conversion.Converter;
import org.beangle.commons.lang.tuple.Pair;

/**
 * A converter factory that can convert objects from S to subtypes of R.
 * 
 * @author chaostone
 * @since 3.2.0
 * @param <S>
 * @param <R> The target base
 */
public abstract class ConverterFactory<S, R> implements GenericConverter {
  protected Map<Class<?>, Converter<S, ? extends R>> converters = CollectUtils.newHashMap();

  /**
   * Return convert from S to T
   */
  @SuppressWarnings("unchecked")
  public <T extends R> Converter<S, T> getConverter(Class<T> targetType) {
    return (Converter<S, T>) converters.get(targetType);
  }

  @Override
  public Pair<Class<?>, Class<?>> getTypeinfo() {
    Type superType = getClass().getGenericSuperclass();
    if ((superType instanceof ParameterizedType)) {
      ParameterizedType ptype = (ParameterizedType) superType;
      return Pair.<Class<?>, Class<?>> of((Class<?>) ptype.getActualTypeArguments()[0],
          (Class<?>) ptype.getActualTypeArguments()[1]);
    } else {
      throw new RuntimeException("Cannot identify typeif of " + getClass());
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object convert(Object input, Class<?> sourceType, Class<?> targetType) {
    Converter<S, R> converter = getConverter((Class<R>) targetType);
    return (null == converter) ? null : converter.apply((S) input);
  }

  protected void register(Class<?> targetType, Converter<S, ? extends R> converter) {
    converters.put(targetType, converter);
  }
}
