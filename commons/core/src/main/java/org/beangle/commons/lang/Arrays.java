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

import java.lang.reflect.Array;
import java.util.List;

/**
 * <p>
 * Operations on arrays, primitive arrays (like {@code int[]}) and primitive wrapper arrays (like
 * {@code Integer[]}).
 * </p>
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception will not be thrown for a
 * {@code null} array input.
 * </p>
 * 
 * @author chaostone
 * @since 3.0.0
 */
public final class Arrays {

  /**
   * <p>
   * Checks if an array of Objects is empty or {@code null}.
   * </p>
   * 
   * @param array the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(Object[] array) {
    return array == null || array.length == 0;
  }

  /**
   * <p>
   * Produces a new array containing the elements between the start and end indices.
   * </p>
   * <p>
   * The start index is inclusive, the end index exclusive. Null array input produces null output.
   * </p>
   * <p>
   * The component type of the subarray is always the same as that of the input array. Thus, if the
   * input is an array of type {@code Date}, the following usage is envisaged:
   * </p>
   * 
   * <pre>
   * Date[] someDates = (Date[]) Arrays.subarray(allDates, 2, 5);
   * </pre>
   * 
   * @param <T> the component type of the array
   * @param array the array
   * @param startIndexInclusive the starting index. Undervalue (&lt;0)
   *          is promoted to 0, overvalue (&gt;array.length) results
   *          in an empty array.
   * @param endIndexExclusive elements up to endIndex-1 are present in the
   *          returned subarray. Undervalue (&lt; startIndex) produces
   *          empty array, overvalue (&gt;array.length) is demoted to
   *          array length.
   * @return a new array containing the elements between
   *         the start and end indices.
   */
  public static <T> T[] subarray(T[] array, int startIndexInclusive, int endIndexExclusive) {
    if (array == null) { return null; }
    if (startIndexInclusive < 0) {
      startIndexInclusive = 0;
    }
    if (endIndexExclusive > array.length) {
      endIndexExclusive = array.length;
    }
    int newSize = endIndexExclusive - startIndexInclusive;
    Class<?> type = array.getClass().getComponentType();
    if (newSize <= 0) {
      @SuppressWarnings("unchecked")
      // OK, because array is of type T
      final T[] emptyArray = (T[]) Array.newInstance(type, 0);
      return emptyArray;
    }
    @SuppressWarnings("unchecked")
    // OK, because array is of type T
    T[] subarray = (T[]) Array.newInstance(type, newSize);
    System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
    return subarray;
  }

  /**
   * join multi array
   * 
   * @param arrays
   * @return
   */
  public static byte[] join(List<byte[]> arrays) {
    int maxlength = 0;
    for (byte[] array : arrays) {
      maxlength += array.length;
    }
    byte[] rs = new byte[maxlength];
    int pos = 0;
    for (byte[] array : arrays) {
      System.arraycopy(array, 0, rs, pos, array.length);
      pos += array.length;
    }
    return rs;
  }

  /**
   * join two array
   * 
   * @param array1
   * @param array2
   * @return
   */
  public static byte[] join(byte[] array1, byte[] array2) {
    if (array1 == null) return clone(array2);
    else if (array2 == null) return clone(array1);
    byte[] joinedArray = new byte[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  private static byte[] clone(byte[] array) {
    if (array == null) return null;
    return (byte[]) array.clone();
  }
}
