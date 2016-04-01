/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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

import java.io.Serializable;
import java.util.Comparator;

public final class Range<T> implements Serializable {
  private static final long serialVersionUID = -8873028230069440769L;
  /**
   * The ordering scheme used in this range.
   */
  public final Comparator<T> comparator;
  /**
   * The minimum value in this range (inclusive).
   */
  public final T min;
  /**
   * The maximum value in this range (inclusive).
   */
  public final T max;
  /**
   * Cached output hashCode (class is immutable).
   */
  private transient int hashCode;
  /**
   * Cached output toString (class is immutable).
   */
  private transient String toString;

  /**
   * <p>
   * Obtains a range using the specified element as both the minimum and maximum in this range. The
   * range uses the natural ordering of the elements to determine where values lie in the range.
   * </p>
   */
  public static <T extends Comparable<T>> Range<T> is(T element) {
    return between(element, element, null);
  }

  /**
   * <p>
   * Obtains a range using the specified element as both the minimum and maximum in this range. The
   * range uses the specified {@code Comparator} to determine where values lie in the range.
   * </p>
   */
  public static <T> Range<T> is(T element, Comparator<T> comparator) {
    return between(element, element, comparator);
  }

  /**
   * <p>
   * Obtains a range with the specified minimum and maximum values (both inclusive). The range uses
   * the natural ordering of the elements to determine where values lie in the range. The arguments
   * may be passed in the order (min,max) or (max,min). The getMinimum and getMaximum methods will
   * return the correct values.
   * </p>
   */
  public static <T extends Comparable<T>> Range<T> between(T fromInclusive, T toInclusive) {
    return between(fromInclusive, toInclusive, null);
  }

  /**
   * <p>
   * Obtains a range with the specified minimum and maximum values (both inclusive). The range uses
   * the specified {@code Comparator} to determine where values lie in the range. The arguments may
   * be passed in the order (min,max) or (max,min). The getMinimum and getMaximum methods will
   * return the correct values.
   * </p>
   */
  public static <T> Range<T> between(T fromInclusive, T toInclusive, Comparator<T> comparator) {
    return new Range<T>(fromInclusive, toInclusive, comparator);
  }

  /**
   * Creates an instance.
   * 
   * @param element1 the first element, not null
   * @param element2 the second element, not null
   * @param comparator the comparator to be used, null for natural ordering
   */
  @SuppressWarnings("unchecked")
  private Range(T element1, T element2, Comparator<T> comparator) {
    if (element1 == null || element2 == null) { throw new IllegalArgumentException(
        "Elements in a range must not be null: element1=" + element1 + ", element2=" + element2); }
    if (comparator == null) {
      comparator = ComparableComparator.INSTANCE;
    }
    if (comparator.compare(element1, element2) < 1) {
      this.min = element1;
      this.max = element2;
    } else {
      this.min = element2;
      this.max = element1;
    }
    this.comparator = comparator;
  }

  /**
   * <p>
   * Whether or not the Range is using the natural ordering of the elements.
   * Natural ordering uses an internal comparator implementation, thus this method is the only way
   * to check if a null comparator was specified.
   * </p>
   * 
   * @return true if using natural ordering
   */
  public boolean isNaturalOrdering() {
    return comparator == ComparableComparator.INSTANCE;
  }

  /**
   * <p>
   * Checks whether the specified element occurs within this range.
   * </p>
   * 
   * @param element the element to check for, null returns false
   * @return true if the specified element occurs within this range
   */
  public boolean contains(T element) {
    if (element == null) { return false; }
    return comparator.compare(element, min) > -1 && comparator.compare(element, max) < 1;
  }

  /**
   * <p>
   * Checks whether this range is after the specified element.
   * </p>
   * 
   * @param element the element to check for, null returns false
   * @return true if this range is entirely after the specified element
   */
  public boolean isAfter(T element) {
    if (element == null) { return false; }
    return comparator.compare(element, min) < 0;
  }

  /**
   * <p>
   * Checks whether this range starts with the specified element.
   * </p>
   * 
   * @param element the element to check for, null returns false
   * @return true if the specified element occurs within this range
   */
  public boolean isStartedBy(T element) {
    if (element == null) { return false; }
    return comparator.compare(element, min) == 0;
  }

  /**
   * <p>
   * Checks whether this range starts with the specified element.
   * </p>
   * 
   * @param element the element to check for, null returns false
   * @return true if the specified element occurs within this range
   */
  public boolean isEndedBy(T element) {
    if (element == null) { return false; }
    return comparator.compare(element, max) == 0;
  }

  /**
   * <p>
   * Checks whether this range is before the specified element.
   * </p>
   * 
   * @param element the element to check for, null returns false
   * @return true if this range is entirely before the specified element
   */
  public boolean isBefore(T element) {
    if (element == null) { return false; }
    return comparator.compare(element, max) > 0;
  }

  /**
   * <p>
   * Checks where the specified element occurs relative to this range. The API is reminiscent of the
   * Comparable interface returning {@code -1} if the element is before the range, {@code 0} if
   * contained within the range and {@code 1} if the element is after the range.
   * </p>
   * 
   * @param element the element to check for, not null
   * @return -1, 0 or +1 depending on the element's location relative to the range
   */
  public int elementCompareTo(T element) {
    if (element == null) {
      // Comparable API says throw NPE on null
      throw new NullPointerException("Element is null");
    }
    if (isAfter(element)) {
      return -1;
    } else if (isBefore(element)) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * <p>
   * Checks whether this range contains all the elements of the specified range. This method may
   * fail if the ranges have two different comparators or element types.
   * </p>
   */
  public boolean containsRange(Range<T> otherRange) {
    if (otherRange == null) { return false; }
    return contains(otherRange.min) && contains(otherRange.max);
  }

  /**
   * <p>
   * Checks whether this range is completely after the specified range. This method may fail if the
   * ranges have two different comparators or element types.
   * </p>
   */
  public boolean isAfterRange(Range<T> otherRange) {
    if (otherRange == null) { return false; }
    return isAfter(otherRange.max);
  }

  /**
   * <p>
   * Checks whether this range is overlapped by the specified range. Two ranges overlap if there is
   * at least one element in common. This method may fail if the ranges have two different
   * comparators or element types.
   * </p>
   */
  public boolean isOverlappedBy(Range<T> otherRange) {
    if (otherRange == null) { return false; }
    return otherRange.contains(min) || otherRange.contains(max) || contains(otherRange.min);
  }

  /**
   * <p>
   * Checks whether this range is completely before the specified range. This method may fail if the
   * ranges have two different comparators or element types.
   * </p>
   */
  public boolean isBeforeRange(Range<T> otherRange) {
    if (otherRange == null) { return false; }
    return isBefore(otherRange.min);
  }

  /**
   * Calculate the intersection of {@code this} and an overlapping Range.
   */
  public Range<T> intersectionWith(Range<T> other) {
    if (!this.isOverlappedBy(other)) { throw new IllegalArgumentException(String.format(
        "Cannot calculate intersection with non-overlapping range %s", other)); }
    if (this.equals(other)) { return this; }
    T min = this.comparator.compare(this.min, other.min) < 0 ? other.min : this.min;
    T max = this.comparator.compare(this.max, other.max) < 0 ? this.max : other.max;
    return between(min, max, this.comparator);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    } else if (obj == null || obj.getClass() != getClass()) {
      return false;
    } else {
      @SuppressWarnings("unchecked")
      Range<T> range = (Range<T>) obj;
      return min.equals(range.min) && max.equals(range.max);
    }
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    if (hashCode == 0) {
      result = 17;
      result = 37 * result + getClass().hashCode();
      result = 37 * result + min.hashCode();
      result = 37 * result + max.hashCode();
      hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    String result = toString;
    if (result == null) {
      StringBuilder buf = new StringBuilder(32);
      buf.append('[');
      buf.append(min);
      buf.append("..");
      buf.append(max);
      buf.append(']');
      result = buf.toString();
      toString = result;
    }
    return result;
  }

  /**
   * <p>
   * Formats the receiver using the given format.
   * </p>
   * <p>
   * This uses {@link java.util.Formattable} to perform the formatting. Three variables may be used
   * to embed the minimum, maximum and comparator. Use {@code %1$s} for the minimum element,
   * {@code %2$s} for the maximum element and {@code %3$s} for the comparator. The default format
   * used by {@code toString()} is {@code [%1$s..%2$s]}.
   * </p>
   * 
   * @param format the format string, optionally containing {@code %1$s}, {@code %2$s} and
   *          {@code %3$s}, not null
   * @return the formatted string, not null
   */
  public String toString(String format) {
    return String.format(format, min, max, comparator);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private enum ComparableComparator implements Comparator {
    INSTANCE;
    /**
     * Comparable based compare implementation.
     * 
     * @param obj1 left hand side of comparison
     * @param obj2 right hand side of comparison
     * @return negative, 0, positive comparison value
     */
    public int compare(Object obj1, Object obj2) {
      return ((Comparable) obj1).compareTo(obj2);
    }
  }

}
