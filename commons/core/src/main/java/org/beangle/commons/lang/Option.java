/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.commons.lang;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

/**
 * Represents optional values. Instances of Option are either an instance of Some or the object
 * None.
 * 
 * @author chaostone
 * @since 3.0.0
 */
public abstract class Option<T> {

  private static final None None = new None();

  Option() {
    super();
  }

  /**
   * Returns an {@code Option} instance containing the given non-null reference.
   */
  public static <T> Option<T> some(T reference) {
    return new Some<T>(Assert.notNull(reference));
  }

  /**
   * Returns an {@code Option} instance with no contained reference.
   */
  @SuppressWarnings("unchecked")
  public static <T> Option<T> none() {
    return (Option<T>) None;
  }

  /**
   * If {@code nullable} is non-null, returns an {@code Option} instance containing that
   * reference; otherwise returns {@link Option#none}.
   */
  public static <T> Option<T> from(T nullable) {
    return (null == nullable) ? Option.<T> none() : new Some<T>(nullable);
  }

  /**
   * Returns the contained instance, which must be present. If the instance might be
   * absent, use {@link #getOrElse(Object)} or {@link #orNull} instead.
   * 
   * @throws IllegalStateException if the instance is absent
   */
  public abstract T get();

  /**
   * Returns the contained instance if it is present; {@code defaultValue} otherwise. If
   * no default value should be required because the instance is known to be present, use
   * {@link #get()} instead.
   * 
   * @param defaultValue
   */
  public abstract T getOrElse(T defaultValue);

  /**
   * Returns the contained instance if it is present; {@code null} otherwise. If the
   * instance is known to be present, use {@link #get()} instead.
   */
  public abstract T orNull();

  /**
   * Returns a singleton list containing the Option's value if it is nonempty, or the empty list if
   * the Option is empty.
   */
  public abstract List<T> toList();

  /**
   * Returns {@code false} if this holder contains a (non-null) instance.
   */
  public abstract boolean isEmpty();

  /**
   * Returns {@code true} if this holder contains a (non-null) instance.
   */
  public abstract boolean isDefined();

  /**
   * Returns {@code true} if {@code object} is an {@code Option} instance, and either
   * the contained references are {@linkplain Object#equals equal} to each other or both
   * are absent. Note that {@code Option} instances of differing parameterized types can
   * be equal.
   */
  @Override
  public abstract boolean equals(Object object);

  /**
   * Return all value from Option Collection
   * 
   * @param values
   */
  public static <T> List<T> getAll(Collection<Option<T>> values) {
    List<T> results = CollectUtils.newArrayList(values.size());
    for (Option<T> op : values) {
      if (op.isDefined()) results.add(op.get());
    }
    return results;
  }
}

/**
 * Represents existing values of type T.
 * 
 * @author chaostone
 * @param <T>
 * @since 3.0.0
 */
class Some<T> extends Option<T> {
  final T value;

  public Some(T v) {
    this.value = v;
  }

  @Override
  public T get() {
    return value;
  }

  @Override
  public T getOrElse(T defaultValue) {
    return value;
  }

  @Override
  public T orNull() {
    return value;
  }

  @Override
  public List<T> toList() {
    return Collections.singletonList(value);
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean isDefined() {
    return true;
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Some) {
      Some<?> other = (Some<?>) object;
      return value.equals(other.value);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return 0x598df91c + value.hashCode();
  }

  @Override
  public String toString() {
    return "Option.some(" + value + ")";
  }
}

class None extends Option<Object> {

  None() {
  }

  @Override
  public Object get() {
    throw new IllegalStateException("value is absent");
  }

  @Override
  public Object getOrElse(Object defaultValue) {
    return defaultValue;
  }

  @Override
  public Object orNull() {
    return null;
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public boolean isDefined() {
    return false;
  }

  @Override
  public List<Object> toList() {
    return Collections.emptyList();
  }

  @Override
  public boolean equals(Object object) {
    return object == this;
  }

  @Override
  public int hashCode() {
    return 0x598df91c;
  }

  @Override
  public String toString() {
    return "Option.none()";
  }

}
