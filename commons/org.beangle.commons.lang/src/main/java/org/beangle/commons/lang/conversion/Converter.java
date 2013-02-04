package org.beangle.commons.lang.conversion;

import org.beangle.commons.lang.functor.UnaryFunction;

/**
 * Convert source to target
 * 
 * @author chaostone
 * @param <S> source
 * @param <T> target
 */
public interface Converter<S, T> extends UnaryFunction<S, T> {

}
