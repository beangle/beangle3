/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.lang.functor;

import org.beangle.commons.lang.Objects;

/**
 * Binary Function Object
 * 
 * @author chaostone
 * @since 3.2.0
 * @param <I1> first input type
 * @param <I2> second input type
 * @param <R> returned output type
 */
public interface BinaryFunction<I1, I2, R> {
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
  R apply(I1 arg1, I2 arg2);
}
