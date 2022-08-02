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
import org.beangle.commons.lang.Strings;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class WeekState implements Serializable, Comparable<WeekState> {

  private static final long serialVersionUID = 1L;

  public static final WeekState Zero = new WeekState(0l);

  public final long value;

  public WeekState(long value) {
    super();
    this.value = value;
  }

  public static WeekState of(int weekIndex) {
    return new WeekState(1l << (weekIndex));
  }

  public static WeekState of(int[] weekIndecies) {
    long v = 0;
    for (int index : weekIndecies) {
      v |= (1l << (index));
    }
    return new WeekState(v);
  }

  public static WeekState of(List<Integer> weekIndecies) {
    long v = 0;
    for (int index : weekIndecies) {
      v |= (1l << (index));
    }
    return new WeekState(v);
  }

  public int compareTo(WeekState o) {
    if (this.value > o.value) return -1;
    else if (this.value == o.value) return 0;
    else return 1;
  }

  public WeekState(String value) {
    this(Long.valueOf(new StringBuilder(value).toString(), 2));
  }

  public int getFirst() {
    String str = toString();
    return str.length() - str.lastIndexOf('1') - 1;
  }

  public int getLast() {
    String str = toString();
    return str.length() - str.indexOf('1') - 1;
  }

  public int getWeeks() {
    return Strings.count(toString(), "1");
  }

  public List<Integer> getWeekList() {
    String weekstr = toString();
    List<Integer> weekList = CollectUtils.newArrayList();
    if (value > 0) {
      for (int i = weekstr.length() - 1; i >= 0; i--) {
        if (weekstr.charAt(i) == '1') weekList.add(weekstr.length() - i - 1);
      }
      return weekList;
    } else {
      return Collections.emptyList();
    }
  }

  public boolean isOccupied(int week) {
    return (value & 1l << week) > 0;
  }

  public long getValue() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    return this.value == ((WeekState) obj).value;
  }

  public WeekState bitand(WeekState other) {
    return new WeekState(this.value & other.value);
  }

  public WeekState bitor(WeekState other) {
    return new WeekState(this.value | other.value);
  }

  public WeekState bitxor(WeekState other) {
    return new WeekState(this.value ^ other.value);
  }

  /**
   * @return
   */
  public String toString() {
    return new StringBuilder(Long.toBinaryString(value)).toString();
  }

  public String toReverseString() {
    return new StringBuilder(Long.toBinaryString(value)).reverse().toString();
  }
}
