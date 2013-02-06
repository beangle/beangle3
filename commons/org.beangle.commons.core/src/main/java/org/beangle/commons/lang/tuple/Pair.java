/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.lang.tuple;

import java.io.Serializable;
import java.util.Map;

import org.beangle.commons.lang.Objects;

/**
 * <p>
 * A immutable pair consisting of two elements.
 * </p>
 * 
 * @author chaostone
 * @param <L> the left element type
 * @param <R> the right element type
 */
public class Pair<L, R> implements Map.Entry<L, R>, Serializable {

  private static final long serialVersionUID = -7643900124010501814L;

  /** Left object */
  public final L left;
  /** Right object */
  public final R right;

  /**
   * <p>
   * Obtains an immutable pair of from two objects inferring the generic types.
   * </p>
   * <p>
   * This factory allows the pair to be created using inference to obtain the generic types.
   * </p>
   * 
   * @param <L> the left element type
   * @param <R> the right element type
   * @param left the left element, may be null
   * @param right the right element, may be null
   * @return a pair formed from the two parameters, not null
   */
  public static <L, R> Pair<L, R> of(L left, R right) {
    return new Pair<L, R>(left, right);
  }

  /**
   * Create a new pair instance.
   * 
   * @param left the left value, may be null
   * @param right the right value, may be null
   */
  public Pair(L left, R right) {
    super();
    this.left = left;
    this.right = right;
  }

  public L getLeft() {
    return left;
  }

  public R getRight() {
    return right;
  }

  /**
   * <p>
   * Throws {@code UnsupportedOperationException}.
   * </p>
   * <p>
   * This pair is immutable, so this operation is not supported.
   * </p>
   * 
   * @param value the value to set
   * @return never
   * @throws UnsupportedOperationException as this operation is not supported
   */
  public R setValue(R value) {
    throw new UnsupportedOperationException();
  }

  /**
   * <p>
   * Gets the key from this pair.
   * </p>
   * <p>
   * This method implements the {@code Map.Entry} interface returning the left element as the key.
   * </p>
   * 
   * @return the left element as the key, may be null
   */
  public final L getKey() {
    return left;
  }

  /**
   * <p>
   * Gets the value from this pair.
   * </p>
   * <p>
   * This method implements the {@code Map.Entry} interface returning the right element as the
   * value.
   * </p>
   * 
   * @return the right element as the value, may be null
   */
  public R getValue() {
    return right;
  }

  /**
   * <p>
   * Compares this pair to another based on the two elements.
   * </p>
   * 
   * @param obj the object to compare to, null returns false
   * @return true if the elements of the pair are equal
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) { return true; }
    if (obj instanceof Map.Entry<?, ?>) {
      Map.Entry<?, ?> other = (Map.Entry<?, ?>) obj;
      return Objects.equals(getKey(), other.getKey()) && Objects.equals(getValue(), other.getValue());
    }
    return false;
  }

  /**
   * <p>
   * Returns a suitable hash code. The hash code follows the definition in {@code Map.Entry}.
   * </p>
   * 
   * @return the hash code
   */
  @Override
  public int hashCode() {
    // see Map.Entry API specification
    return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
  }

  @Override
  public String toString() {
    return new StringBuilder().append('(').append(getLeft()).append(',').append(getRight()).append(')')
        .toString();
  }

}
