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
package org.beangle.orm.hibernate.udt;

import org.beangle.commons.collection.CollectUtils;

import java.util.List;

public class NumberSequence {

  public static enum Pattern {
    CONTINUE, EVEN, ODD
  }

  /**
   * 获得数组
   *
   * @param start 闭区间的开始
   * @param end 闭区间的结束
   * @param pattern 单、双、连续模式
   * @return
   */
  public static int[] build(int start, int end, Pattern pattern) {
    Integer[] integers = buildInteger(start, end, pattern);
    int[] ints = new int[integers.length];
    for (int i = 0; i < integers.length; i++) {
      ints[i] = integers[i];
    }
    return ints;
  }

  public static Integer[] buildInteger(int start, int end, Pattern pattern) {
    if (start > end) { return buildInteger(end, start, pattern); }
    List<Integer> integers = CollectUtils.newArrayList();
    for (int i = start; i <= end; i++) {
      if (pattern == Pattern.CONTINUE) {
        integers.add(i);
      } else if (pattern == Pattern.EVEN) {
        if (i % 2 == 0) {
          integers.add(i);
        }
      } else {
        if (i % 2 != 0) {
          integers.add(i);
        }
      }
    }
    return integers.toArray(new Integer[0]);
  }

}
