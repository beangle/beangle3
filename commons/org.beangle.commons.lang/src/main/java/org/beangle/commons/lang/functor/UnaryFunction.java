package org.beangle.commons.lang.functor;

import org.beangle.commons.lang.Objects;

/**
 * Unary Function Object
 * 
 * @param <I> input type
 * @param <R> returned output type
 * @author chaostone
 * @since 3.2.0
 */
public interface UnaryFunction<I, R> {

  /**
   * Returns the result of applying this function to {@code input}. This method is <i>generally
   * expected</i>, but not absolutely required, to have the following properties:
   * <ul>
   * <li>Its execution does not cause any observable side effects.
   * <li>The computation is <i>consistent with equals</i>; that is, {@link Objects#equal
   * Objects.equal}{@code (a, b)} implies that {@code Objects.equal(function.apply(a),
   * function.apply(b))}.
   * </ul>
   * 
   * @throws NullPointerException if {@code input} is null
   */
  R apply(I input);
}
